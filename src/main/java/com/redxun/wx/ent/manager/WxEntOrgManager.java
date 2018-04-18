
package com.redxun.wx.ent.manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.ExceptionUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.org.dao.OsGroupDao;
import com.redxun.sys.org.dao.OsGroupQueryDao;
import com.redxun.sys.org.dao.OsUserQueryDao;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.wx.ent.dao.WxEntAgentDao;
import com.redxun.wx.ent.dao.WxEntAgentQueryDao;
import com.redxun.wx.ent.dao.WxEntCorpQueryDao;
import com.redxun.wx.ent.entity.WxEntAgent;
import com.redxun.wx.ent.entity.WxEntCorp;
import com.redxun.wx.ent.util.OrgUtil;
import com.redxun.wx.ent.util.model.WxOrg;
import com.redxun.wx.ent.util.model.WxUser;
import com.redxun.wx.util.TokenModel;
import com.redxun.wx.util.TokenUtil;

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
public class WxEntOrgManager extends ExtBaseManager<WxEntAgent>{
	@Resource
	private WxEntAgentDao wxEntAgentDao;
	@Resource
	private WxEntCorpQueryDao wxEntCorpQueryDao;
	@Resource
	private WxEntAgentQueryDao wxEntAgentQueryDao;
	@Resource
	private OsUserQueryDao osUserQueryDao;
	@Resource
	SysInstManager sysInstManager;
	
	@Resource
	private OsGroupQueryDao osGroupQueryDao;
	@Resource
	private OsGroupDao osGroupDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return wxEntAgentDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return wxEntAgentQueryDao;
	}
	
	public Map<String,Integer> pidMap = new HashMap<String, Integer>();
	
	
	
	
	/**
	 * 获取token。
	 * @param tenantId
	 * @return
	 */
	private JsonResult<TokenModel>  getToken(String tenantId){
		JsonResult<TokenModel> result=new JsonResult<TokenModel>(true);
		WxEntCorp corp= wxEntCorpQueryDao.getByTenantId(tenantId);
		try{
			TokenModel token= TokenUtil.getEntToken(corp.getCorpId(), corp.getSecret());
			result.setData(token);
			return result;
		}
		catch(Exception ex){
			result.setSuccess(false);
			result.setMessage(ExceptionUtil.getExceptionMessage(ex));
			return result;
		}
		
	}
	
	/**
	 * 同步组织和用户。
	 * <pre>
	 * 	1.获取token。
	 * 	2.同步组织。
	 * 	3.同步用户。
	 * </pre>
	 * @param tenantId	租户ID
	 * @return
	 * @throws Exception
	 */
	public JsonResult<List<String>> syncOrg(String tenantId,List<String> userIds) throws Exception{
		JsonResult<List<String>> result=new JsonResult<List<String>>(true);
		JsonResult<TokenModel> tokenResult=  getToken(  tenantId);
		if(!tokenResult.isSuccess()){
			result.setSuccess(false);
			result.setMessage(tokenResult.getMessage());
			return result;
		}
		TokenModel token= tokenResult.getData();
		
		List<String> errorMsgs=new ArrayList<String>();
		//同步组织
		List<WxOrg> orgList= buidOrgs( tenantId);
		for(WxOrg org:orgList){
			JsonResult<Void> jsonResult= OrgUtil.createOrg(org, token.getToken());
			if(jsonResult.isSuccess()){
				osGroupQueryDao.updWxFlag(org.getOriginId());
			}
			else{
				errorMsgs.add(jsonResult.getMessage());
			}
		}

		//同步用户。
		List<WxUser> userList= buidUsers( tenantId,userIds);
		for(WxUser user:userList){
			JsonResult<Void> jsonResult= OrgUtil.createUser(user, token.getToken());
			if(jsonResult.isSuccess()){
				osUserQueryDao.updWxFlag(user.getOriginId());
			}
			else{
				
				errorMsgs.add(jsonResult.getMessage());
			}
		}
		if(errorMsgs.size()>0){
			result.setSuccess(false);
		}
		result.setData(errorMsgs);
		return result;
	}
	
	/**
	 * 构建微信企业组织数据。
	 * @param tenantId
	 * @return
	 */
	private List<WxOrg> buidOrgs(String tenantId){
		List<OsGroup> orgs=osGroupQueryDao.getSyncWx(tenantId);		
		List<OsGroup> roots=getByPid("0", orgs);
		List<OsGroup> rtnOrgs=new ArrayList<OsGroup>();
		rtnOrgs.addAll(roots);
		for(OsGroup org:roots){
			String groupId=org.getGroupId();
			buildOrgs(groupId,orgs,rtnOrgs);
		}
		Map<String,OsGroup> groupMap=convertOsMap(rtnOrgs);
		
		//处理企业微信ID
		handWxId(rtnOrgs,groupMap);
		
		
		List<WxOrg> rtnList=new ArrayList<WxOrg>();
		
		for(OsGroup group:rtnOrgs){
			if(group.getSyncWx()==1) continue;
			rtnList.add(new WxOrg(group));
		}
		return rtnList;
	}
	
	/**
	 * 构建企业微信部门关系ID
	 * @param rtnOrgs
	 * @param groupMap
	 */
	private void handWxId(List<OsGroup> rtnOrgs,Map<String,OsGroup> groupMap){			
		List<OsGroup> tmpOrgs=new ArrayList<OsGroup>();		
		Integer wxParentPid = null;
		for(OsGroup group:rtnOrgs){				
			if(group.getWxPid()!=null) continue; 
			Integer maxWxpid = osGroupQueryDao.getMaxPid();
			if(maxWxpid==null){
				maxWxpid = 100;
			}
			
			Integer wxPid=maxWxpid + 1;
			group.setWxPid(wxPid);	
			String parentId = group.getParentId();
			if("0".equals(parentId)){
				wxParentPid = 1;
			}else{
				OsGroup parentGroup = groupMap.get(group.getParentId());
				wxParentPid = parentGroup.getWxPid();	
			}				
			group.setWxParentPid(wxParentPid);	
			osGroupQueryDao.updateWxPid(group.getGroupId(), wxParentPid, wxPid);
			tmpOrgs.add(group);
						
		}
	}
	
	private Map<String,OsGroup> convertOsMap(List<OsGroup> orgList){
		Map<String,OsGroup> map=new HashMap<String, OsGroup>();
		for(OsGroup group:orgList){
			map.put(group.getGroupId(), group);
		}
		return map;
	}
	
	
	/**
	 * 递归构建组织。
	 * @param pid
	 * @param orgs
	 * @param rtnOrgs
	 */
	private void buildOrgs(String pid,List<OsGroup> orgs,List<OsGroup> rtnOrgs){
		List<OsGroup> list= getByPid(pid, orgs);
		if(BeanUtil.isEmpty(list)) return;
		rtnOrgs.addAll(list);
		for(OsGroup org:list){
			String groupId=org.getGroupId();
			buildOrgs(groupId, orgs, rtnOrgs);
		}
	}
	
	/**
	 * 根据父ID获取组织。
	 * @param pid
	 * @param orgs
	 * @return
	 */
	private List<OsGroup> getByPid(String pid,List<OsGroup> orgs){
		List<OsGroup> list=new ArrayList<OsGroup>();
		for(OsGroup group:orgs){
			if(!pid.equals(group.getParentId())) continue;			
			list.add(group);
		}
		orgs.removeAll(list);
		return list;
	}

	/**
	 * 构建微信用户。
	 * @param tenantId
	 * @return
	 */
	private List<WxUser> buidUsers(String tenantId,List<String> userIds){
		ITenant tenant=  sysInstManager.get(tenantId);
		String domain="@" + tenant.getDomain();
		List<OsUser> userList=osUserQueryDao.getSyncWx(tenantId);
		
		List<WxUser> rtnList=new ArrayList<WxUser>();
		for(OsUser osUser:userList){
			if(userIds!=null&&userIds.size()>0){
				if(userIds.indexOf(osUser.getUserId())==-1) continue;
			}			
			SysAccount account= osUser.getSysAccount();			
			account.setName(account.getName().replace(domain, ""));
			rtnList.add(new WxUser(osUser));
		}
		return rtnList;
	}
	
	
	
}
