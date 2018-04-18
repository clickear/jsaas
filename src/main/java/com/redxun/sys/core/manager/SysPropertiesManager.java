
package com.redxun.sys.core.manager;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.sys.core.dao.SysPropertiesDao;
import com.redxun.sys.core.dao.SysPropertiesQueryDao;
import com.redxun.sys.core.entity.SysProperties;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：系统参数 处理接口
 * 作者:ray
 * 日期:2017-06-21 11:22:36
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysPropertiesManager extends ExtBaseManager<SysProperties>{
	@Resource
	private SysPropertiesDao sysPropertiesDao;
	@Resource
	private SysPropertiesQueryDao sysPropertiesQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysPropertiesQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return sysPropertiesQueryDao;
	}
	
	public List<String> getCategory(Map<String, Object> params){
		return sysPropertiesQueryDao.getCategory(params);
	}
	/**
	 * 通过别名获取
	 * @param name
	 * @return
	 */
	public SysProperties getPropertiesByName(String name){
		return sysPropertiesDao.getPropertiesByName(name);
	}
	
	public List<SysProperties> getGlobalPropertiesByTenantId(String tenantId){
		return sysPropertiesQueryDao.getGlobalPropertiesByTenantId(tenantId);
	}

	public String getByAlias(String alias){
		String rtn="";
		try {
			rtn = getPropertiesByName(alias).getVal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	public int getIntByAlias(String alias){
		int rtn=0;
		try {
			rtn = Integer.parseInt(getPropertiesByName(alias).getVal());
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
}
