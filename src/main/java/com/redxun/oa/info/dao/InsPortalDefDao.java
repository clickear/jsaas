
/**
 * 
 * <pre> 
 * 描述：ins_portal_def DAO接口
 * 作者:mansan
 * 日期:2017-08-15 16:07:14
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import com.redxun.oa.info.entity.InsPortal;
import com.redxun.oa.info.entity.InsPortalDef;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class InsPortalDefDao extends BaseJpaDao<InsPortalDef> {


	@Override
	protected Class getEntityClass() {
		return InsPortalDef.class;
	}
	
    /**
     * 根据门户key，租户Id，用户Id查找是否有这个门户
     * @param key
     * @param tenantId
     * @param userId
     * @return 如果有则为这个门户portal，如果没则为空
     */
    public InsPortalDef getByIdKey(String key, String tenantId,String userId){
    	String ql="from InsPortalDef insp where insp.key=? and insp.tenantId=? and insp.userId=? ";
    	return (InsPortalDef)this.getUnique(ql, new Object[]{key, tenantId,userId});
    }
    
    /**
     * 根据门户Key，租户Id查找是否有这个门户
     * @param key
     * @param tenantId
     * @return 如果有则为这个门户portal，如果没则为空
     */
    public InsPortalDef getByKey(String key, String tenantId){
    	String ql="from InsPortalDef insp where insp.key=? and insp.tenantId=? ";
    	return (InsPortalDef)this.getUnique(ql, new Object[]{key, tenantId});
    }
    
    public InsPortalDef getDefaultPortal(){
    	String ql="from InsPortalDef insp where insp.isDefault='YES'";
    	return (InsPortalDef)this.getUnique(ql, new Object[]{});
    }


}

