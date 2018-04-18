
/**
 * 
 * <pre> 
 * 描述：微信应用 DAO接口
 * 作者:mansan
 * 日期:2017-06-04 12:27:36
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.ent.dao;

import com.redxun.wx.ent.entity.WxEntAgent;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class WxEntAgentDao extends BaseJpaDao<WxEntAgent> {
	@Override
	protected Class getEntityClass() {
		return WxEntAgent.class;
	}

}

