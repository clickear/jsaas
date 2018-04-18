
package com.redxun.oa.info.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.oa.info.dao.InsPortalPermissionDao;
import com.redxun.oa.info.dao.InsPortalPermissionQueryDao;
import com.redxun.oa.info.entity.InsPortalPermission;

/**
 * 
 * <pre> 
 * 描述：布局权限设置 处理接口
 * 作者:mansan
 * 日期:2017-08-28 15:58:17
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class InsPortalPermissionManager extends ExtBaseManager<InsPortalPermission>{
	@Resource
	private InsPortalPermissionDao insPortalPermissionDao;
	@Resource
	private InsPortalPermissionQueryDao insPortalPermissionQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insPortalPermissionDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return insPortalPermissionQueryDao;
	}
	
	public InsPortalPermission getInsPortalPermission(String uId){
		InsPortalPermission insPortalPermission = get(uId);
		return insPortalPermission;
	}
	
	public void delByLayoutId(String layoutId){
		insPortalPermissionQueryDao.delByLayoutId(layoutId);
	}
	
	public List<InsPortalPermission> getByLayoutId(String layoutId){
		return insPortalPermissionDao.getByLayoutId(layoutId);
	}
}
