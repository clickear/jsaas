package com.redxun.kms.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.kms.core.entity.KdDocCmmt;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：KdDocCmmt数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdDocCmmtDao extends BaseJpaDao<KdDocCmmt> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdDocCmmt.class;
    }
    
    /**
     * 判断是否已经点评过
     * @param docId
     * @param identityType
     * @param identityId
     * @param right
     * @return
     */
    public boolean getIsByDocId(String docId,String userId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "select count(*) from KdDocCmmt r where r.kdDoc.docId = ? and r.createBy = ? and r.tenantId = ?";
    	Long a =(Long)this.getUnique(ql, new Object[]{docId,userId,tenantId});
    	if(a>0L){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public List<KdDocCmmt> getByDocId(String docId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "from KdDocCmmt r where r.kdDoc.docId = ? and r.tenantId = ?";
    	return this.getByJpql(ql, new Object[]{docId,tenantId});
    }
}
