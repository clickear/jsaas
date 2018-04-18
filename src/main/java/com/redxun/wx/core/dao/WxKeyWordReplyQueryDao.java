
/**
 * 
 * <pre> 
 * 描述：公众号关键字回复 DAO接口
 * 作者:陈茂昌
 * 日期:2017-08-30 11:39:20
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.core.dao;

import com.redxun.wx.core.entity.WxKeyWordReply;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class WxKeyWordReplyQueryDao extends BaseMybatisDao<WxKeyWordReply> {

	@Override
	public String getNamespace() {
		return WxKeyWordReply.class.getName();
	}

}

