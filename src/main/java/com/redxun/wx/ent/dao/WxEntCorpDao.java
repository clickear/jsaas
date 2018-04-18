
/**
 * 
 * <pre> 
 * 描述：微信企业配置 DAO接口
 * 作者:mansan
 * 日期:2017-06-04 12:27:35
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.ent.dao;

import com.redxun.wx.ent.entity.WxEntCorp;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class WxEntCorpDao extends BaseJpaDao<WxEntCorp> {


	@Override
	protected Class getEntityClass() {
		return WxEntCorp.class;
	}

}

