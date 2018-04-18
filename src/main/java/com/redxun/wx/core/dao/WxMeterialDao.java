
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

import com.redxun.wx.core.entity.WxMeterial;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class WxMeterialDao extends BaseJpaDao<WxMeterial> {


	@Override
	protected Class getEntityClass() {
		return WxMeterial.class;
	}

}

