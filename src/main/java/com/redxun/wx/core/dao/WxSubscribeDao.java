
/**
 * 
 * <pre> 
 * 描述：微信关注者 DAO接口
 * 作者:ray
 * 日期:2017-06-30 08:51:08
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.core.dao;

import com.redxun.wx.core.entity.WxSubscribe;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class WxSubscribeDao extends BaseJpaDao<WxSubscribe> {


	@Override
	protected Class getEntityClass() {
		return WxSubscribe.class;
	}
	
	public WxSubscribe getByOpenId(String openId){
		String hql="from WxSubscribe ws where ws.openId=?";
		return (WxSubscribe) this.getUnique(hql, openId);
	}

}

