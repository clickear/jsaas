package com.redxun.kms.core.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.kms.core.dao.KdDocFavDao;
import com.redxun.kms.core.entity.KdDocFav;
/**
 * <pre> 
 * 描述：KdDocFav业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdDocFavManager extends BaseManager<KdDocFav>{
	@Resource
	private KdDocFavDao kdDocFavDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdDocFavDao;
	}
	
	/**
     * 判断是否已经收藏过了
     * @param userId
     * @param docId
     * @return
     */
    public boolean getIsFav(String userId,String docId){
    	return kdDocFavDao.getIsFav(userId, docId);
    }
    
    /**
     * 判断问题是否已经收藏过了
     * @param userId
     * @param docId
     * @return
     */
    public boolean getQuestionIsFav(String userId,String queId){
    	return kdDocFavDao.getQuestionIsFav(userId, queId);
    }
    
}