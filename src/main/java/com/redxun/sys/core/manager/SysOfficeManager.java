
package com.redxun.sys.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.dao.IDao;
import com.redxun.sys.core.dao.SysOfficeDao;
import com.redxun.sys.core.dao.SysOfficeQueryDao;
import com.redxun.sys.core.dao.SysOfficeVerQueryDao;
import com.redxun.sys.core.entity.SysOffice;
import com.redxun.sys.core.entity.SysOfficeVer;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：OFFICE附件 处理接口
 * 作者:ray
 * 日期:2018-01-15 15:34:18
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysOfficeManager extends ExtBaseManager<SysOffice>{
	@Resource
	private SysOfficeDao sysOfficeDao;
	@Resource
	private SysOfficeQueryDao sysOfficeQueryDao;
	@Resource
	private SysOfficeVerQueryDao sysOfficeVerQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysOfficeDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return sysOfficeQueryDao;
	}
	
	public SysOffice getSysOffice(String uId){
		SysOffice sysOffice = get(uId);
		return sysOffice;
	}
	
	/**
	 * 根据officeId 获取文件ID。
	 * @param officeId
	 * @return
	 */
	public String getFileIdByOfficeId(String officeId){
		List<SysOfficeVer> list=sysOfficeVerQueryDao.getByOfficeId(officeId);
		return list.get(0).getFileId();
	}
	
	/**
	 * 获取版本。
	 * @param officeId
	 * @return
	 */
	public JSONArray getVersByOfficeId(String officeId){
		List<SysOfficeVer> list=sysOfficeVerQueryDao.getByOfficeId(officeId);
		JSONArray ary=new JSONArray();
		for(SysOfficeVer ver:list){
			JSONObject obj=new JSONObject();
			obj.put("fileId", ver.getFileId());
			obj.put("version", ver.getVersion());
			ary.add(obj);
		}
		return ary; 
	}
	
	/**
	 * 根据office ID获取最大的版本。
	 * @param officeId
	 * @return
	 */
	public Integer getVersionByOfficeId(String officeId){
		Integer rtn=sysOfficeVerQueryDao.getVersionByOfficeId(officeId);
		return rtn;
		
	}
}
