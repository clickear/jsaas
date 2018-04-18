
package com.redxun.wx.core.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.wx.core.dao.WxMeterialDao;
import com.redxun.wx.core.dao.WxMeterialQueryDao;
import com.redxun.wx.core.entity.WxMeterial;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：微信素材 处理接口
 * 作者:ray
 * 日期:2017-07-11 16:03:13
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class WxMeterialManager extends ExtBaseManager<WxMeterial>{
	@Resource
	private WxMeterialDao wxMeterialDao;
	@Resource
	private WxMeterialQueryDao wxMeterialQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return wxMeterialQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return wxMeterialQueryDao;
	}
	public WxMeterial getByMediaId(String mediaId){
		return wxMeterialQueryDao.getByMediaId(mediaId);
	}
}
