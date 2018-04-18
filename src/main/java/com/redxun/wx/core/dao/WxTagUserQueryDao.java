
/**
 * 
 * <pre> 
 * 描述：微信用户标签 DAO接口
 * 作者:ray
 * 日期:2017-06-29 17:55:30
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.core.dao;

import com.redxun.wx.core.entity.WxTagUser;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class WxTagUserQueryDao extends BaseMybatisDao<WxTagUser> {

	@Override
	public String getNamespace() {
		return WxTagUser.class.getName();
	}

}

