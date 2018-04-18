package com.redxun.kms.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.kms.core.entity.KdUser;
import com.redxun.saweb.context.ContextUtil;

/**
 * <pre>
 * 描述：KdUser数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdUserDao extends BaseJpaDao<KdUser> {

	public List<KdUser> getBySn(String tenantId) {
		String ql = "from KdUser u where u.sn>? and u.tenantId=?";
		return this.getByJpql(ql, new Object[] { 100 ,tenantId});
	}

	public KdUser getByUserId(String userId) {
		String ql = "from KdUser u where u.user.userId=?";
		return (KdUser) this.getUnique(ql, new Object[] { userId });
	}

	/**
	 * 判断是否已经关联用户
	 * 
	 * @param userId
	 * @return true 已经关联
	 * 			false 尚未关联
	 */
	public boolean getIsByUserId(String userId) {
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql = "select count(*) from KdUser u where u.user.userId=? and u.tenantId = ?";
		Long a = (Long) this.getUnique(ql, new Object[] { userId, tenantId });
		if (a > 0L) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkKdUser(String uId) {
		String ql = "select count(*) from KdUser u where u.user.userId=?";
		Long num = (Long) this.getUnique(ql, new Object[] { uId });
		if (num > 0)
			return true;
		else
			return false;
	}

	/**
	 * 获得积分榜的List
	 * 
	 * @return
	 */
	public List<KdUser> getScoreUser(Page page,String tenantId) {
		String ql = "from KdUser u where u.tenantId=? order by u.docScore desc";
		return this.getByJpql(ql, new Object[] {tenantId}, page);
	}

	/**
	 * 获得财富榜的List
	 * 
	 * @return
	 */
	public List<KdUser> getPointUser(Page page,String tenantId) {
		String ql = "from KdUser u where u.tenantId=? order by u.point desc";
		return this.getByJpql(ql, new Object[] {tenantId}, page);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return KdUser.class;
	}

}
