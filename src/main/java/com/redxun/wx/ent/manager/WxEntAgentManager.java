
package com.redxun.wx.ent.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.wx.ent.dao.WxEntAgentDao;
import com.redxun.wx.ent.dao.WxEntAgentQueryDao;
import com.redxun.wx.ent.dao.WxEntCorpQueryDao;
import com.redxun.wx.ent.entity.WxEntAgent;
import com.redxun.wx.ent.entity.WxEntCorp;
import com.redxun.wx.ent.util.WeixinUtil;

/**
 * 
 * <pre> 
 * 描述：微信应用 处理接口
 * 作者:mansan
 * 日期:2017-06-04 12:27:36
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class WxEntAgentManager extends ExtBaseManager<WxEntAgent>{
	@Resource
	private WxEntAgentDao wxEntAgentDao;
	@Resource
	private WxEntCorpQueryDao wxEntCorpQueryDao;
	@Resource
	private WxEntAgentQueryDao wxEntAgentQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return wxEntAgentDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return wxEntAgentQueryDao;
	}

	@Override
	public void create(WxEntAgent entity) {
		String tenantId=ContextUtil.getCurrentTenantId();
		
		WxEntCorp corp= wxEntCorpQueryDao.getByTenantId(tenantId);
		//添加
		if(corp==null){
			corp=entity.getWxEntCorp();
			corp.setId(IdUtil.getId());
			wxEntCorpQueryDao.create(entity.getWxEntCorp());
		}
		//更新
		else{
			WxEntCorp tmpCorp= entity.getWxEntCorp();
			corp.setCorpId(tmpCorp.getCorpId());
			corp.setSecret(tmpCorp.getSecret());
			corp.setEnable(tmpCorp.getEnable());
			wxEntCorpQueryDao.update(corp);
		}
		entity.setEntId(corp.getId());
		entity.setCorpId(corp.getCorpId());
		//将其他更新为非默认
		if(1==entity.getDefaultAgent().intValue()){
			wxEntAgentQueryDao.updNotDefault(tenantId);
		}
		wxEntAgentQueryDao.create(entity);
	}

	@Override
	public void update(WxEntAgent entity) {
		String tenantId=entity.getTenantId();
		WxEntCorp corp= wxEntCorpQueryDao.getByTenantId(tenantId);
		WxEntCorp tmpCorp= entity.getWxEntCorp();
		corp.setCorpId(tmpCorp.getCorpId());
		corp.setSecret(tmpCorp.getSecret());
		corp.setEnable(tmpCorp.getEnable());
		wxEntCorpQueryDao.update(corp);
		//将其他更新为非默认
		if(1==entity.getDefaultAgent().intValue()){
			wxEntAgentQueryDao.updNotDefault(tenantId);
		}
		entity.setCorpId(entity.getCorpId());
		wxEntAgentQueryDao.update(entity);
	}
	
	
	/**
	 * 取得更新微信应用信息的方法。 
	 * @param corpId
	 * @param secret
	 * @param agent
	 * @return
	 * @throws Exception
	 */
	public JsonResult<JSONObject> getAppInfo(WxEntAgent agent) throws Exception{
		JsonResult<Void> result= WeixinUtil.getAppInfoById(agent.getCorpId(), agent.getSecret());
		JsonResult<JSONObject> rtn=new JsonResult<JSONObject>(true);
    	if(result.isSuccess()){
    		String json=result.getMessage();
    		JSONObject jsonObj=JSONObject.parseObject(json);
    		JSONObject newJson=new JSONObject();
    		newJson.put("agentid", agent.getAgentId());
    		newJson.put("report_location_flag",jsonObj.getInteger("report_location_flag"));
    		
    		newJson.put("name", agent.getName());
    		newJson.put("description", agent.getDescription());
    		newJson.put("redirect_domain", agent.getDomain());
    		newJson.put("isreportenter", jsonObj.getInteger("isreportenter"));
    		newJson.put("home_url", agent.getHomeUrl());
    		
    		rtn.setData(newJson);
    	}
    	else{
    		rtn.setSuccess(false);
    		rtn.setMessage(result.getMessage());
    	}
		return rtn;
	}
	
	/**
	 * 根据应用获取应用。
	 * @param agentId
	 * @return
	 */
	public WxEntAgent getByAgent(String agentId){
		String tenantId=ContextUtil.getCurrentTenantId();
		WxEntAgent agent= this.wxEntAgentQueryDao.getByAgent(tenantId, agentId);
		return agent;
	}
	
	/**
	 * 获取默认的WxEntAgent
	 * @param tenantId
	 * @return
	 */
	public WxEntAgent getByTenantId(String tenantId){
		List<WxEntAgent> agents= this.wxEntAgentQueryDao.getByTenantId(tenantId);
		if(BeanUtil.isEmpty(agents)) return null;
		WxEntAgent entAgent=null;
		for(WxEntAgent agent:agents){
			if(agent.getDefaultAgent()==1){
				entAgent=agent;
				break;
			}
		}
		if(entAgent!=null) return entAgent;
		return agents.get(0);
	}
	
	
}
