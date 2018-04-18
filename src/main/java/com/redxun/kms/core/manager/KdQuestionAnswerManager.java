package com.redxun.kms.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.kms.core.dao.KdQuestionAnswerDao;
import com.redxun.kms.core.entity.KdQuestion;
import com.redxun.kms.core.entity.KdQuestionAnswer;
/**
 * <pre> 
 * 描述：KdQuestionAnswer业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdQuestionAnswerManager extends BaseManager<KdQuestionAnswer>{
	@Resource
	private KdQuestionAnswerDao kdQuestionAnswerDao;
	
	@Resource
	KdQuestionManager kdQuestionManager;
	
	/**
	 * 根据问题Id 获取最佳答案
	 * @param queId 问题Id
	 * @return
	 */
	public KdQuestionAnswer getBestAnswerByqueId(String queId){
		return kdQuestionAnswerDao.getBestAnswerByqueId(queId);
	}
	
	/**
	 * 根据问题Id和回答人Id获取问题答案
	 * @param queId 问题Id
	 * @param authorId 回答人Id
	 * @return
	 */
	public KdQuestionAnswer getByQueIdAndAuthorId(String queId,String authorId){
		return kdQuestionAnswerDao.getByQueIdAndAuthorId(queId, authorId);
	}
	
	public void delAnswer(KdQuestionAnswer answer){
        KdQuestion question=kdQuestionManager.get(answer.getKdQuestion().getQueId());
        question.setReplyCounts(question.getReplyCounts()-1);
        question.getKdQuestionAnswers().remove(answer);
        kdQuestionManager.update(question);
        this.flush();
        this.detach(question);
        super.delete(answer.getAnswerId());
	}
	
	/**
	 * 根据回答者Id获取问题答案
	 * @param authorId 回答者Id
	 * @return
	 */
	public List<KdQuestionAnswer> getByAuthorId(String authorId){
		return kdQuestionAnswerDao.getByAuthorId(authorId);
	}
	
	/**
	 * 根据回答者Id和是否最佳获取问题答案
	 * @param authorId 回答者Id
	 * @param isBest 是否最佳
	 * @return
	 */
	public List<KdQuestionAnswer> getByAuthorIdAndBest(String authorId,String isBest){
		return kdQuestionAnswerDao.getByAuthorIdAndBest(authorId, isBest);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdQuestionAnswerDao;
	}
}