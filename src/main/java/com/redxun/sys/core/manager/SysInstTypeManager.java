
package com.redxun.sys.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.sys.core.dao.SysInstTypeDao;
import com.redxun.sys.core.dao.SysInstTypeQueryDao;
import com.redxun.sys.core.entity.SysInstType;

/**
 * 
 * <pre> 
 * 描述：机构类型 处理接口
 * 作者:mansan
 * 日期:2017-07-10 18:35:31
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class SysInstTypeManager extends ExtBaseManager<SysInstType>{
	@Resource
	private SysInstTypeDao sysInstTypeDao;
	@Resource
	private SysInstTypeQueryDao sysInstTypeQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysInstTypeDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return sysInstTypeQueryDao;
	}
	
	public List<SysInstType> getValidExludePlatform(){
		return sysInstTypeDao.getValidExludePlatform();
	}
	
	public List<SysInstType> getValidAll(){
		return sysInstTypeDao.getValidAll();
	}
	
	public SysInstType getByCode(String typeCode){
		return sysInstTypeDao.getByCode(typeCode);
	}
}
