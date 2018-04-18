
/**
 * 
 * <pre> 
 * 描述：微信关注者 DAO接口
 * 作者:ray
 * 日期:2017-06-30 08:51:08
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.core.dao;

import java.util.List;

import com.redxun.wx.core.entity.WxSubscribe;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.QueryFilter;

@Repository
public class WxSubscribeQueryDao extends BaseMybatisDao<WxSubscribe> {

	@Override
	public String getNamespace() {
		return WxSubscribe.class.getName();
	}
	
	public List<WxSubscribe> getByTagId(QueryFilter queryFilter){
		
		return this.getBySqlKey("getByTagId",queryFilter.getParams());
	}

}

