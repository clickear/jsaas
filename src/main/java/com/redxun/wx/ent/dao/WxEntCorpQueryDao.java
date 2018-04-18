
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
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class WxEntCorpQueryDao extends BaseMybatisDao<WxEntCorp> {

	@Override
	public String getNamespace() {
		return WxEntCorp.class.getName();
	}

	/**
	 * 根据租户ID获取配置。
	 * @param tenantId
	 * @return
	 */
	public WxEntCorp getByTenantId(String tenantId){
		WxEntCorp corp=(WxEntCorp) this.getOne("getByTenantId", tenantId);
		return corp;
	}
}

