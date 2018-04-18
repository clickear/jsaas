
package com.redxun.wx.core.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.wx.core.dao.WxMessageSendDao;
import com.redxun.wx.core.dao.WxMessageSendQueryDao;
import com.redxun.wx.core.entity.WxMessageSend;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：WX_MESSAGE_SEND 处理接口
 * 作者:ray
 * 日期:2017-07-17 17:58:08
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class WxMessageSendManager extends ExtBaseManager<WxMessageSend>{
	@Resource
	private WxMessageSendDao wxMessageSendDao;
	@Resource
	private WxMessageSendQueryDao wxMessageSendQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return wxMessageSendQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return wxMessageSendQueryDao;
	}
}
