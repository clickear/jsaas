
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

import java.util.List;

import com.redxun.wx.core.entity.WxTagUser;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class WxTagUserDao extends BaseJpaDao<WxTagUser> {


	@Override
	protected Class getEntityClass() {
		return WxTagUser.class;
	}

	/**
	 * 通过pubId和tagId查找
	 * @param tagId
	 * @param pubId
	 * @return
	 */
	public List<WxTagUser> getByTagId(String tagId,String pubId){
		String hql="from WxTagUser wtu where wtu.tagId=? and wtu.pubId=?";
		return this.getByJpql(hql, tagId,pubId);
	}
	public WxTagUser getByTagIdAndUserId(String tagId,String userId){
		String hql="from WxTagUser wtu where wtu.tagId=? and wtu.userId=?";
		return (WxTagUser) this.getUnique(hql, tagId,userId);
	}
}

