
package com.redxun.sys.core.manager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.activiti.util.ProcessHandleHelper;
import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.sys.core.dao.SysDataSourceDao;
import com.redxun.sys.core.dao.SysDataSourceQueryDao;
import com.redxun.sys.core.entity.SysDataSource;

/**
 * 
 * <pre> 
 * 描述：数据源定义管理 处理接口
 * 作者:ray
 * 日期:2017-02-07 09:03:54
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysDataSourceManager extends ExtBaseManager<SysDataSource>{
	
	private static Log logger = LogFactory.getLog(SysDataSourceManager.class);
	
	@Resource
	private SysDataSourceDao sysDataSourceDao;
	@Resource
	private SysDataSourceQueryDao sysDataSourceQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysDataSourceDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return sysDataSourceQueryDao;
	}
	
	/**
	 * 判断数据源是否存在。
	 * @param dataSource
	 * @return
	 */
	public boolean isExist(SysDataSource dataSource){
		Integer rtn=sysDataSourceQueryDao.isExist(dataSource);
		return rtn>0;
	}
	
	/**
	 * 从数据源配置加载生成数据源。
	 * @param sysDataSource
	 * @return
	 */
	public DruidDataSource getDsBySysSource(SysDataSource sysDataSource) {
		ProcessHandleHelper.initProcessMessage();
		JsonResult rtn= checkConnection(sysDataSource);
		if(!rtn.isSuccess()){
			ProcessHandleHelper.addErrorMsg(rtn.getMessage());
			return null;
		}
		
		try {
			// 获取对象
			DruidDataSource dataSource = new DruidDataSource();// 初始化对象
			// 开始set它的属性
			String settingJson = sysDataSource.getSetting();
			JSONArray ja = JSONArray.parseArray(settingJson);

			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Object value = BeanUtil.convertByActType(jo.getString("type"), jo.getString("val"));
				String name=jo.getString("name");
				BeanUtil.setFieldValue(dataSource,name, value);
			}
			dataSource.setName("druid-" + sysDataSource.getAlias());
			dataSource.init(); 
			return dataSource;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			ProcessHandleHelper.addErrorMsg(e.getMessage());
		}

		return null;
	}
	
	/**
	 * 判断连接是否有效。
	 * @param sysDataSource
	 * @return
	 */
	public JsonResult checkConnection(SysDataSource sysDataSource) {
		String dbType=sysDataSource.getDbType();
		String json=sysDataSource.getSetting();
		JSONArray jsonAry=JSONArray.parseArray(json);
		String url=getValByName(jsonAry, "url");
		String username=getValByName(jsonAry, "username");
		String password=getValByName(jsonAry, "password");
		return  DataSourceUtil.validConn(dbType, url, username, password);
	}
	
	private String getValByName(JSONArray jsonAry,String inName){
		for(int i=0;i<jsonAry.size();i++){
			JSONObject obj=jsonAry.getJSONObject(i);
			String name=obj.getString("name");
			if(inName.equals(name)){
				return obj.getString("val");
			}
		}
		return "";
	}
	
	/**
	 * 初始化系统数据源。
	 * @return
	 */
	public Map<String, DataSource> getDataSource(){
		List<SysDataSource> list=sysDataSourceQueryDao.getInitStart();
		Map<String, DataSource> map=new HashMap<String, DataSource>();
		for(SysDataSource source:list){
			//校验数据连接是否有有效。
			JsonResult result=checkConnection(source);
			if(!result.isSuccess()) continue;
			
			DruidDataSource dataSource=getDsBySysSource(source);
			if(dataSource==null) continue;
			map.put(source.getAlias(), dataSource);
		}
		return map;
	}
	
	public SysDataSource getByAlias(String alias){
		 return sysDataSourceQueryDao.getByAlias(alias);
	}

	
}
