package com.redxun.kms.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.kms.core.dao.KdUserDao;
import com.redxun.kms.core.entity.KdUser;
/**
 * <pre> 
 * 描述：KdUser业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class KdUserManager extends BaseManager<KdUser>{
	@Resource
	private KdUserDao kdUserDao;
	
	public List<KdUser> getBySn(String tenantId){
		return kdUserDao.getBySn(tenantId);
	}
	
	public KdUser getByUserId(String userId){
		return kdUserDao.getByUserId(userId);
	}
	
	public boolean checkKdUser(String uId){
		return kdUserDao.checkKdUser(uId);
	}
	
	/**
	 * 获得积分榜的List
	 * @return
	 */
	public List<KdUser> getScoreUser(Page page,String tenantId){
		return kdUserDao.getScoreUser(page,tenantId);
	}
	/**
	 * 获得财富榜的List
	 * @return
	 */
	public List<KdUser> getPointUser(Page page,String tenantId){
		return kdUserDao.getPointUser(page,tenantId);
	}
	/**
	 * 判断是否已经关联过
	 * 
	 * @param userId
	 * @return true 已经关联
	 * 			false 尚未关联
	 */
	public boolean getIsByUserId(String userId) {
		return kdUserDao.getIsByUserId(userId);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return kdUserDao;
	}
}