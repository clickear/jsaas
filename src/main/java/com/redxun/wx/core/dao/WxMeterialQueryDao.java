
/**
 * 
 * <pre> 
 * 描述：微信素材 DAO接口
 * 作者:ray
 * 日期:2017-07-11 16:03:13
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.core.dao;

import java.util.HashMap;
import java.util.Map;

import com.redxun.wx.core.entity.WxMeterial;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class WxMeterialQueryDao extends BaseMybatisDao<WxMeterial> {

	@Override
	public String getNamespace() {
		return WxMeterial.class.getName();
	}

	public WxMeterial getByMediaId(String mediaId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("mediaId", mediaId);
		return this.getUnique("getByMediaId", params);
	}
}

