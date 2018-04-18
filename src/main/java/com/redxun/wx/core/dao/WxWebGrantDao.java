
/**
 * 
 * <pre> 
 * 描述：微信网页授权 DAO接口
 * 作者:陈茂昌
 * 日期:2017-08-18 17:05:42
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.core.dao;

import com.redxun.wx.core.entity.WxWebGrant;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class WxWebGrantDao extends BaseJpaDao<WxWebGrant> {


	@Override
	protected Class getEntityClass() {
		return WxWebGrant.class;
	}

}

