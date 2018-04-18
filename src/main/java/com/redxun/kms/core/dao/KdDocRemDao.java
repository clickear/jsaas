package com.redxun.kms.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.kms.core.entity.KdDocRem;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：KdDocRem数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdDocRemDao extends BaseJpaDao<KdDocRem> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdDocRem.class;
    }
    
    /**
     * 判断是否有这个推荐
     * @param docId
     * @param treeId
     * @return
     */
    public boolean getByIsRem(String docId,String treeId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql=" select count(*) from KdDocRem rem where rem.kdDoc.docId=? and rem.recTreeId = ? and rem.tenantId = ?";
    	Long a = (Long)this.getUnique(ql, new Object[]{docId,treeId,tenantId});
    	if(a>0L){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 获得该文档的所有推荐
     * @param docId
     * @return
     */
    public List<KdDocRem> getByDocId(String docId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql=" from KdDocRem rem where rem.kdDoc.docId = ? and rem.tenantId = ? and rem.recTreeId is null";
    	return this.getByJpql(ql, new Object[]{docId,tenantId});
    }
    
    /**
     * 删除这个docId的文档的首页推荐
     * @param docId
     */
    public void delByDocId(String docId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql=" delete KdDocRem rem where rem.kdDoc.docId =? and rem.recTreeId = 'root' and rem.tenantId = ?";
    	this.delete(ql, new Object[]{docId,tenantId});    	
    }
}
