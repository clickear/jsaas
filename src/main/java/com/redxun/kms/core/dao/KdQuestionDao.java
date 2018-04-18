package com.redxun.kms.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.kms.core.entity.KdQuestion;
/**
 * <pre> 
 * 描述：KdQuestion数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdQuestionDao extends BaseJpaDao<KdQuestion> {

	/**
	 * 获取最新的问答
	 * @return
	 */
	public List<KdQuestion> getLatestQuestion(String tenantId){
		String ql="from KdQuestion q where q.tenantId=? order by q.createTime desc";
		Page page=new Page(0, 8);
		return this.getByJpql(ql, new Object[]{tenantId},page);
	}
	
	/**
	 * 获取最热的问答
	 * @return
	 */
	public List<KdQuestion> getHostestQuestion(String tenantId){
		String ql="from KdQuestion q where q.tenantId=? order by q.replyCounts desc,q.createTime desc";
		Page page=new Page(0, 8);
		return this.getByJpql(ql, new Object[]{tenantId},page);
	}
	
	/**
	 * 获取高分问答
	 * @return
	 */
	public List<KdQuestion> getHighRewardQuestion(String tenantId){
		String ql="from KdQuestion q where q.tenantId=? order by q.rewardScore desc,q.replyCounts,q.createTime desc";
		Page page=new Page(0, 8);
		return this.getByJpql(ql, new Object[]{tenantId},page);
	}
	
	/**
	 * 获取最佳问答
	 * @return
	 */
	public List<KdQuestion> getBestQuestion(String tenantId){
		String ql="from KdQuestion q where q.status=? and q.tenantId=? order by q.rewardScore desc,q.replyCounts,q.createTime desc";
		Page page=new Page(0, 5);
		return this.getByJpql(ql, new Object[]{KdQuestion.STATUS_SOLVED,tenantId},page);
	}
	
	/**
	 * 根据问答的状态和用户Id获取问答
	 * @param userId 用户ID
	 * @param status 问答的状态
	 * @return
	 */
	public List<KdQuestion> getByUserIdAndStatus(String userId,String status){
		String ql="";
		if("ALL".equals(status)){
			ql="from KdQuestion q where q.createBy=? order by createTime desc";
			return this.getByJpql(ql, new Object[]{userId});
		}
		else{
			ql="from KdQuestion q where q.createBy=? and status=? order by createTime desc";
			return this.getByJpql(ql, new Object[]{userId,status});
		}
	}
	
	/**
	 * 获取已解决问题或待解决问题
	 * @param status 问题状态
	 * @return
	 */
	public List<KdQuestion> getByStatus(String status,String tenantId){
		String ql="from KdQuestion q where q.status=? and q.tenantId=? order by createTime desc";
		Page page=new Page(0, 5);
		return this.getByJpql(ql, new Object[]{status,tenantId},page);
	}
	
	/**
	 * 根据回答者Id获取问题
	 * @param rId 回答者ID
	 * @return
	 */
	public List<KdQuestion> getByReplierId(String rId){
		String ql="from KdQuestion q where q.replierId=?";
		return this.getByJpql(ql, new Object[]{rId});
	}
	
	/**
	 * 根据分类的路径获取问题
	 * @param path
	 * @param page
	 * @return
	 */
	public List<KdQuestion> getByPath(String path,QueryFilter queryFilter,String tenantId){
		String ql="select q  from KdQuestion q where q.sysTree.path like ? and q.tenantId=?";
		return this.getByJpql(ql,new Object[]{path+"%",tenantId},queryFilter.getPage());
	}
	
    /**
     * 根据用户Id获取收藏的问题
     * @param userId
     * @return
     */
    public List<KdQuestion> getCollectQuestionByUserId(String userId){
    	String ql="select k from KdQuestion k left join k.kdDocFavs f where f.createBy=?";
    	return this.getByJpql(ql, new Object[]{userId});
    }
	
    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdQuestion.class;
    }
    
}
