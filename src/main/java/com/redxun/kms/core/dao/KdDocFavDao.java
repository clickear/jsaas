package com.redxun.kms.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.kms.core.entity.KdDocFav;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：KdDocFav数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdDocFavDao extends BaseJpaDao<KdDocFav> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdDocFav.class;
    }
    
    /**
     * 判断是否已经收藏过了
     * @param userId
     * @param docId
     * @return
     */
    public boolean getIsFav(String userId,String docId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "select count(*) from KdDocFav f where f.kdDoc.docId = ? and f.createBy = ? and f.tenantId = ?";
    	Long a =(Long)this.getUnique(ql, new Object[]{docId,userId,tenantId});
    	if(a>0){
    		return true;
    	}else {
    		return false;
    	}
    }
    
    /**
     * 判断问题是否已经收藏过了
     * @param userId
     * @param docId
     * @return
     */
    public boolean getQuestionIsFav(String userId,String queId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "select count(*) from KdDocFav f where f.kdQuestion.queId = ? and f.createBy = ? and f.tenantId = ?";
    	Long a =(Long)this.getUnique(ql, new Object[]{queId,userId,tenantId});
    	if(a>0){
    		return true;
    	}else {
    		return false;
    	}
    }
    
}
