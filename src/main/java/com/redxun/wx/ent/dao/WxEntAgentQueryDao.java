
/**
 * 
 * <pre> 
 * 描述：微信应用 DAO接口
 * 作者:mansan
 * 日期:2017-06-04 12:27:36
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.wx.ent.dao;

import com.redxun.wx.ent.entity.WxEntAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class WxEntAgentQueryDao extends BaseMybatisDao<WxEntAgent> {

	@Override
	public String getNamespace() {
		return WxEntAgent.class.getName();
	}

	
	public void updNotDefault(String tenantId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("tenantId", tenantId);
		this.updateBySqlKey("updNotDefault", params);
	}
	
	/**
	 * 根据应用ID获取应用配置。
	 * @param tenantId
	 * @param agentId
	 * @return
	 */
	public WxEntAgent getByAgent(String tenantId,String agentId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("tenantId", tenantId);
		params.put("agentId", agentId);
		return this.getUnique("getByAgent", params);
	}

	/**
	 * 获取租户的应用。
	 * @param tenantId
	 * @return
	 */
	public List<WxEntAgent> getByTenantId(String tenantId){
		return this.getBySqlKey("getByTenantId", tenantId);
	}
}

