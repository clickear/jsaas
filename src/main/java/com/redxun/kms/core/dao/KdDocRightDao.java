package com.redxun.kms.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：KdDocRight数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdDocRightDao extends BaseJpaDao<KdDocRight> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdDocRight.class;
    }
    
    /**
	 * 根据文档名和用户名查询是否已有这个权限关联
	 * @param docId
	 * @param identityType 
	 * @param identityId
	 * @param right
	 * @return true 已存在
	 *         false 未存在		
	 */
    public boolean getByDocIdIdentityIdRgiht(String docId,String identityType,String identityId,String right){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "select count(*) from KdDocRight r where r.kdDoc.docId = ? and r.identityType=? and r.identityId = ? and r.right=? and r.tenantId = ?";
    	Long a =(Long)this.getUnique(ql, new Object[]{docId,identityType, identityId,right,tenantId});
    	if(a>0L){
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     * 根据right类别和user（group）组别查询这个文档的所有权限
     * @param docId
     * @param identityType
     * @param right
     * @return
     */
    public List<KdDocRight> getAllByDocIdRight(String docId,String identityType,String right){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "from KdDocRight r where r.kdDoc.docId = ? and r.identityType=? and r.right=? and r.tenantId = ?";
    	return this.getByJpql(ql, new Object[]{docId,identityType,right,tenantId});
    }
    
    /**
     * 删除所有docId为此的权限
     * @param docId
     */
    public void delByDocId(String docId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "delete KdDocRight r where r.kdDoc.docId = ? and r.tenantId = ?";
    	this.delete(ql, new Object[]{docId,tenantId});
    }
    
    /**
     * 判断是否全部人可读
     * @param docId
     * @return
     */
    public boolean getIsAll(String docId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "select count(*) from KdDocRight r where r.kdDoc.docId = ? and r.identityType = 'ALL' and r.right = 'READ' and r.tenantId = ? ";
    	Long a =(Long)this.getUnique(ql, new Object[]{docId,tenantId});
    	if(a>0L){
    		return true;
    	}else{
    		return false;
    	}
    }
    
}
