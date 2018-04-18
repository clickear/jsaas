
package com.redxun.wx.core.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.wx.core.dao.WxTicketDao;
import com.redxun.wx.core.dao.WxTicketQueryDao;
import com.redxun.wx.core.entity.WxTicket;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：微信卡券 处理接口
 * 作者:陈茂昌
 * 日期:2017-08-22 10:23:23
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class WxTicketManager extends ExtBaseManager<WxTicket>{
	@Resource
	private WxTicketDao wxTicketDao;
	@Resource
	private WxTicketQueryDao wxTicketQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return wxTicketQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return wxTicketQueryDao;
	}
	
	public WxTicket getWxTicket(String uId){
		WxTicket wxTicket = get(uId);
		return wxTicket;
	}
}
