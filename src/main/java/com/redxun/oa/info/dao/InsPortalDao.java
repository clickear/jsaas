package com.redxun.oa.info.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.info.entity.InsPortCol;
import com.redxun.oa.info.entity.InsPortal;
/**
 * <pre> 
 * 描述：InsPortal数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class InsPortalDao extends BaseJpaDao<InsPortal> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return InsPortal.class;
    }
   
    /**
     * 根据门户Key，租户Id查找是否有这个门户
     * @param key
     * @param tenantId
     * @return 如果有则为这个门户portal，如果没则为空
     */
    public InsPortal getByKey(String key, String tenantId){
    	String ql="from InsPortal insp where insp.key=? and insp.tenantId=? order by insp.createTime desc";
    	return (InsPortal)this.getUnique(ql, new Object[]{key, tenantId});
    }
    /**
     * 根据门户key，租户Id，用户Id查找是否有这个门户
     * @param key
     * @param tenantId
     * @param userId
     * @return 如果有则为这个门户portal，如果没则为空
     */
    public InsPortal getByIdKey(String key, String tenantId,String userId){
    	String ql="from InsPortal insp where insp.key=? and insp.tenantId=? and insp.userId=? order by insp.createTime desc";
    	return (InsPortal)this.getUnique(ql, new Object[]{key, tenantId,userId});
    }
}
