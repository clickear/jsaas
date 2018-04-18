package com.redxun.kms.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.kms.core.dao.KdQuestionDao;
import com.redxun.kms.core.entity.KdQuestion;
/**
 * <pre> 
 * 描述：KdQuestion业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdQuestionManager extends BaseManager<KdQuestion>{
	@Resource
	private KdQuestionDao kdQuestionDao;
	
	/**
	 * 获取最新的问答
	 * @return
	 */
	public List<KdQuestion> getLatestQuestion(String tenantId){
		return kdQuestionDao.getLatestQuestion(tenantId);
	}
	
	/**
	 * 获取最热的问答
	 * @return
	 */
	public List<KdQuestion> getHostestQuestion(String tenantId){
		return kdQuestionDao.getHostestQuestion(tenantId);
	}
	
	/**
	 * 获取高分问答
	 * @return
	 */
	public List<KdQuestion> getHighRewardQuestion(String tenantId){
		return kdQuestionDao.getHighRewardQuestion(tenantId);
	}
	
	/**
	 * 获取最佳问答
	 * @return
	 */
	public List<KdQuestion> getBestQuestion(String tenantId){
		return kdQuestionDao.getBestQuestion(tenantId);
	}
	
	/**
	 * 根据问答的状态和用户Id获取问答
	 * @param userId 用户Id
	 * @param status 问答的状态
	 * @return
	 */
	public List<KdQuestion> getByUserIdAndStatus(String userId,String status){
		return kdQuestionDao.getByUserIdAndStatus(userId, status);
	}
	
	/**
	 * 获取已解决问题或待解决问题
	 * @param status 问题状态
	 * @return
	 */
	public List<KdQuestion> getByStatus(String status,String tenantId){
		return kdQuestionDao.getByStatus(status,tenantId);
	}
	
	/**
	 * 根据回答者Id获取问题
	 * @param rId 回答者Id
	 * @return
	 */
	public List<KdQuestion> getByReplierId(String rId){
		return kdQuestionDao.getByReplierId(rId);
	}
	
	/**
	 * 根据分类的路径获取问题
	 * @param path
	 * @param page
	 * @return
	 */
	public List<KdQuestion> getByPath(String path,QueryFilter queryFilter,String tenantId){
		return kdQuestionDao.getByPath(path, queryFilter,tenantId);
	}
	
	 /**
     * 根据用户Id获取收藏的问题
     * @param userId
     * @return
     */
    public List<KdQuestion> getCollectQuestionByUserId(String userId){
    	return kdQuestionDao.getCollectQuestionByUserId(userId);
    }
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdQuestionDao;
	}
}