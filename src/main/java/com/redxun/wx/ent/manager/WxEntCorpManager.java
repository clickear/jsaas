
package com.redxun.wx.ent.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.wx.ent.dao.WxEntCorpDao;
import com.redxun.wx.ent.dao.WxEntCorpQueryDao;
import com.redxun.wx.ent.entity.WxEntAgent;
import com.redxun.wx.ent.entity.WxEntCorp;

/**
 * 
 * <pre> 
 * 描述：微信企业配置 处理接口
 * 作者:mansan
 * 日期:2017-06-04 12:27:36
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class WxEntCorpManager extends ExtBaseManager<WxEntCorp>{
	@Resource
	private WxEntCorpDao wxEntCorpDao;
	@Resource
	private WxEntCorpQueryDao wxEntCorpQueryDao;
	@Resource
	private WxEntAgentManager wxEntAgentManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return wxEntCorpDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return wxEntCorpQueryDao;
	}
	
	/**
	 * 根据租户获取配置。
	 * @param tenantId
	 * @return
	 */
	public WxEntCorp getByTenantId(String tenantId){
		return wxEntCorpQueryDao.getByTenantId(tenantId);
	}
	
	/**
	 * 检查当前企业微信是否可用。
	 * @return
	 */
	public boolean isWxEnable(){
		String tenantId=ContextUtil.getCurrentTenantId();
		WxEntCorp ent= getByTenantId(  tenantId);
		if(ent==null) return false;
		return ent.getEnable().intValue()==1;
	}
	
	/**
	 * 根据agentId 获取企业微信信息。
	 * @param agentId
	 * @return
	 */
	public WxEntCorp getByAgentId(String agentId){
		WxEntAgent agent= wxEntAgentManager.get(agentId);
		WxEntCorp corp=wxEntCorpQueryDao.get(agent.getEntId());
		return corp;
	}
	
	
	
}
