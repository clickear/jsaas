
/**
 * 
 * <pre> 
 * 描述：微信卡券 DAO接口
 * 作者:陈茂昌
 * 日期:2017-08-22 10:23:23
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.core.dao;

import com.redxun.wx.core.entity.WxTicket;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class WxTicketQueryDao extends BaseMybatisDao<WxTicket> {

	@Override
	public String getNamespace() {
		return WxTicket.class.getName();
	}

}

