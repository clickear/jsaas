
/**
 * 
 * <pre> 
 * 描述：公众号管理 DAO接口
 * 作者:ray
 * 日期:2017-06-29 16:57:29
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.core.dao;

import com.redxun.wx.core.entity.WxPubApp;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class WxPubAppDao extends BaseJpaDao<WxPubApp> {


	@Override
	protected Class getEntityClass() {
		return WxPubApp.class;
	}

	public WxPubApp getByAppId(String appId){
		String hql="from WxPubApp wpa where wpa.appId=?";
		return (WxPubApp) this.getUnique(hql, appId);
	}
}

