
/**
 * 
 * <pre> 
 * 描述：WX_MESSAGE_SEND DAO接口
 * 作者:ray
 * 日期:2017-07-17 17:58:08
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.core.dao;

import com.redxun.wx.core.entity.WxMessageSend;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class WxMessageSendQueryDao extends BaseMybatisDao<WxMessageSend> {

	@Override
	public String getNamespace() {
		return WxMessageSend.class.getName();
	}

}

