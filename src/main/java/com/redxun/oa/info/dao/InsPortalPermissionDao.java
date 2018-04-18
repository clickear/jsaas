
/**
 * 
 * <pre> 
 * 描述：布局权限设置 DAO接口
 * 作者:mansan
 * 日期:2017-08-28 15:58:17
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.info.entity.InsPortalDef;
import com.redxun.oa.info.entity.InsPortalPermission;

@Repository
public class InsPortalPermissionDao extends BaseJpaDao<InsPortalPermission> {


	@Override
	protected Class getEntityClass() {
		return InsPortalPermission.class;
	}
	
	public void delByLayoutId(String layoutId){
		String ql = "delete from InsPortalPermission p where p.layoutId=?";
		this.delete(ql, new Object[]{layoutId});
	}
	
	public List<InsPortalPermission> getByLayoutId(String layoutId){
		String ql = "from InsPortalPermission p where p.layoutId=?";
		return this.getByJpql(ql, new Object[]{layoutId});
	}
	
    public List<InsPortalPermission> getByUserId(String userId){
    	String ql="from InsPortalPermission p where p.type='user' and p.ownerId=?";
    	return this.getByJpql(ql, new Object[]{userId});
    }
    
    public List<InsPortalPermission> getByGroupId(String groupId){
    	String ql="from InsPortalPermission p where p.type='group' and p.ownerId=?";
    	return this.getByJpql(ql, new Object[]{groupId});
    }
    
    public List<InsPortalPermission> getBySubGroupId(String subGroupId){
    	String ql="from InsPortalPermission p where p.type='subGroup' and p.ownerId=?";
    	return this.getByJpql(ql, new Object[]{subGroupId});
    }
}

