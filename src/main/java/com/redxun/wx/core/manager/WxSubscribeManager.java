
package com.redxun.wx.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.wx.core.dao.WxSubscribeDao;
import com.redxun.wx.core.dao.WxSubscribeQueryDao;
import com.redxun.wx.core.entity.WxSubscribe;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;

/**
 * 
 * <pre> 
 * 描述：微信关注者 处理接口
 * 作者:ray
 * 日期:2017-06-30 08:51:08
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class WxSubscribeManager extends ExtBaseManager<WxSubscribe>{
	@Resource
	private WxSubscribeDao wxSubscribeDao;
	@Resource
	private WxSubscribeQueryDao wxSubscribeQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return wxSubscribeDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return wxSubscribeQueryDao;
	}
	
	public WxSubscribe getByOpenId(String openId){
		return wxSubscribeDao.getByOpenId(openId);
	}
	
	public List<WxSubscribe> getByTagId(QueryFilter queryFilter){
		return wxSubscribeQueryDao.getByTagId(queryFilter);
	}
	
}
