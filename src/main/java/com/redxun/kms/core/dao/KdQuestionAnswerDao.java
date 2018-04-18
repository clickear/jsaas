package com.redxun.kms.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.kms.core.entity.KdQuestionAnswer;
/**
 * <pre> 
 * 描述：KdQuestionAnswer数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdQuestionAnswerDao extends BaseJpaDao<KdQuestionAnswer> {

	/**
	 * 根据问题Id 获取最佳答案
	 * @param queId 问题Id
	 * @return
	 */
	public KdQuestionAnswer getBestAnswerByqueId(String queId){
		String ql="from KdQuestionAnswer a where a.kdQuestion.queId=? and a.isBest=?";
		return (KdQuestionAnswer)this.getUnique(ql, new Object[]{queId,MBoolean.YES.name()});
	}
	
	/**
	 * 根据问题Id和回答人Id获取问题答案
	 * @param queId 问题Id
	 * @param authorId 回答人Id
	 * @return
	 */
	public KdQuestionAnswer getByQueIdAndAuthorId(String queId,String authorId){
		String ql="from KdQuestionAnswer a where a.kdQuestion.queId=? and a.authorId=?";
		return (KdQuestionAnswer)this.getUnique(ql, new Object[]{queId,authorId});
	}
	
	/**
	 * 根据回答者Id获取问题答案
	 * @param authorId 回答者Id
	 * @return
	 */
	public List<KdQuestionAnswer> getByAuthorId(String authorId){
		String ql="from KdQuestionAnswer a where a.authorId=? order by a.createTime desc";
		return this.getByJpql(ql, new Object[]{authorId});
	}
	
	/**
	 * 根据回答者Id和是否最佳获取问题答案
	 * @param authorId 回答者Id
	 * @param isBest 是否最佳
	 * @return
	 */
	public List<KdQuestionAnswer> getByAuthorIdAndBest(String authorId,String isBest){
		String ql="";
		if("ALL".equals(isBest)){
			ql="from KdQuestionAnswer a where a.authorId=? order by a.createTime desc";
			return this.getByJpql(ql, new Object[]{authorId});
		}else{
			ql="from KdQuestionAnswer a where a.authorId=? and a.isBest=? order by a.createTime desc";
			return this.getByJpql(ql, new Object[]{authorId,isBest});
		}
	}
	
    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdQuestionAnswer.class;
    }
    
}
