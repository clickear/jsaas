package com.redxun.sys.org.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.mybatis.CommonDao;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysAccountManager;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsAttributeValue;
import com.redxun.sys.org.entity.OsCustomAttribute;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsAttributeValueManager;
import com.redxun.sys.org.manager.OsCustomAttributeManager;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRelInstManager;
import com.redxun.sys.org.manager.OsRelTypeManager;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.wx.ent.manager.WxEntCorpManager;

/**
 * [OsUser]管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/org/osUser/")
public class OsUserController extends TenantListController{
    @Resource
    private OsUserManager osUserManager;
    @Resource
    OsGroupManager osGroupManager;
    @Resource
    private OsDimensionManager osDimensionManager;
    @Resource
    private OsRelTypeManager osRelTypeManager;
    @Resource
    private OsRelInstManager osRelInstManager;
    @Resource
    private SysAccountManager sysAaccountManager;
    @Resource
    private SysInstManager sysInstManager;
    @Resource
    private WxEntCorpManager wxEntCorpManager;
    @Resource
    private OsCustomAttributeManager osCustomAttributeManager;
	@Resource
	private OsAttributeValueManager osAttributeValueManager;
	@Resource
	private CommonDao commonDao;
	
    @Override
    protected QueryFilter getQueryFilter(HttpServletRequest request) {
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	String tenantId=getCurTenantId(request);
    	queryFilter.addFieldParam("tenantId", tenantId);
    	queryFilter.addFieldParam("status", OsUser.STATUS_IN_JOB);
    	return queryFilter;
    }
    
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "组织结构", submodule = "系统用户")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	//超级管理员下不能更新为删除状态
            	if(!OsUser.ADMIN_USER_ID_.equals(id)){
	            	OsUser user = osUserManager.get(id);
	            	user.setStatus(OsUser.STATUS_DEL_JOB);
	            	osUserManager.update(user);
            	}
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    @RequestMapping("getContactUserByType")
    @ResponseBody
    public JsonPageResult<OsUser> getContactUserByType(HttpServletRequest request,HttpServletResponse response){
    	String typeId=RequestUtil.getString(request, "typeId");
    	Integer pageSize=RequestUtil.getInt(request, "pageSize");
    	Integer pageIndex=RequestUtil.getInt(request, "pageIndex");
    	String sql="";
    	if(StringUtils.isNotBlank(typeId)){
    		sql="select F_LXR from w_topcontacts where F_LXRFL='"+typeId+"'";
    	}else{
    		sql="select F_LXR from w_topcontacts where CREATE_USER_ID_="+ContextUtil.getCurrentUserId();
    	}
    	
    	List<Map<String, Object>> users=commonDao.query(sql);
    	List<String> userIds=new ArrayList<String>();
    	
    	int listEdge=((pageSize*(pageIndex+1))  >   (users.size()))  ?  users.size():(pageSize*(pageIndex+1));//此页边界
    	for (int i = pageSize*pageIndex; i < listEdge; i++) {
			Map<String, Object> map=users.get(i);
			String userId=(String) map.get("F_LXR");
			userIds.add(userId);
		}
    	List<OsUser> osUsers=osUserManager.getUsersByIds(userIds);
    	return new JsonPageResult<OsUser>(osUsers, osUsers.size());
    }
    
    @RequestMapping("contactEdit")
    public ModelAndView contactEdit(HttpServletRequest request,HttpServletResponse response){
    	String tenantId=ContextUtil.getCurrentTenantId();
    	return this.getPathView(request).addObject("tenantId", tenantId);
    }
    
    @RequestMapping("contactList")
    public ModelAndView contactList(HttpServletRequest request,HttpServletResponse response){
    	return this.getPathView(request);
    }
    
    @RequestMapping("getContactType")
    @ResponseBody
    public List<JSONObject> getContactType(HttpServletRequest request,HttpServletResponse response){
    	String userId=ContextUtil.getCurrentUserId();
    	List<JSONObject> contactTypes=new ArrayList<JSONObject>();
    	List<Map<String, Object>> list=commonDao.query("select F_LXRFL from w_topcontacts where CREATE_USER_ID_="+userId+ " GROUP BY  F_LXRFL");
    	for (int i = 0; i < list.size(); i++) {
			Map<String , Object> map=list.get(i);
			String contactType=(String) map.get("F_LXRFL");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("contactType", contactType);
			contactTypes.add(jsonObject);
		}
    	return contactTypes;
    	
    }
    
    @RequestMapping("delContextUser")
    @ResponseBody
    public JSONObject delContextUser(HttpServletRequest request,HttpServletResponse response){
    	String userId=RequestUtil.getString(request, "userId");
    	String sql="delete from w_topcontacts where  F_LXR='"+userId+"'";
    	JSONObject jsonObject=new JSONObject();
    	try {
    		commonDao.execute(sql);
    		jsonObject.put("success", true);
    		return jsonObject;
		} catch (Exception e) {
			jsonObject.put("success", false);
    		return jsonObject;
		}
    }
    
    @RequestMapping("saveContact")
    @ResponseBody
    public JSONObject saveContact(HttpServletRequest request,HttpServletResponse response){
    	JSONObject jsonObject=new JSONObject();
    	String lxr=RequestUtil.getString(request, "lxr");
    	String lxr_name=RequestUtil.getString(request, "lxr_name");
    	String lxrfl=RequestUtil.getString(request, "lxrfl");
    	String id=IdUtil.getId();
    	String userId=ContextUtil.getCurrentUserId();
    	String tenantId=ContextUtil.getCurrentTenantId();
    	Date createTime=new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String checkUnique="SELECT * FROM W_TOPCONTACTS WHERE F_LXRFL='"+lxrfl+"' AND F_LXR='"+lxr+"';";
    	List<?> listNum=commonDao.query(checkUnique);
    	if(listNum.size()==0){
    		String sql="INSERT INTO W_TOPCONTACTS (ID_,F_LXRFL,F_LXRFL_NAME,F_LXR,F_LXR_NAME,CREATE_USER_ID_,TENANT_ID_,CREATE_TIME_) VALUES ('"+id+"','"+lxrfl+"','"+lxrfl+"','"+lxr+"','"+lxr_name+"','"+userId+"','"+tenantId+"','"+formatter.format(createTime)+"')";
        	commonDao.execute(sql);
        	jsonObject.put("success", true);
    	}else{
    		jsonObject.put("success", false);
    	}
    	return jsonObject;
    	
    }
    
    /**
     * 对话框的用户搜索
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("search")
    @ResponseBody
    public JsonPageResult<OsUser> search(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String groupId=request.getParameter("groupId");
    	String fullname=request.getParameter("fullname");
    	String email=request.getParameter("email");
    	String userNo=request.getParameter("userNo");
    	String tenantId=getCurTenantId(request);
    	//用户组Id 需要关联用户关系实例来查找用户
    	if(StringUtils.isNotEmpty(groupId)){
    		SqlQueryFilter queryFilter=QueryFilterBuilder.createSqlQueryFilter(request);
    		//取得从属关系
    		OsRelType belongRelType=osRelTypeManager.getBelongRelType();
    		queryFilter.getParams().put("relTypeId",belongRelType.getId());
    		
    		OsGroup osGroup=osGroupManager.get(groupId);
    		if(osGroup!=null){
    			queryFilter.addFieldParam("path", osGroup.getPath()+"%");
    		}
    		if(StringUtils.isNotEmpty(fullname)){
    			queryFilter.addFieldParam("fullname", "%" + fullname + "%");
    		}
    		if(StringUtils.isNotEmpty(email)){
    			queryFilter.addFieldParam("email", "%" +email + "%");
    		}
    		if(StringUtils.isNotEmpty(userNo)){
    			queryFilter.addFieldParam("userNo", "%" +userNo + "%");
    		}
    		queryFilter.addFieldParam("tenantId", tenantId);
    		
    		List<OsUser> list=osUserManager.getByGroupPathRelTypeId(queryFilter);
    		return new JsonPageResult<OsUser>(list, queryFilter.getPage().getTotalItems());
    	}else{
    		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    		
    		queryFilter.getOrderByList().clear();
    		
    		String sortField = request.getParameter("sortField");
    	    String sortOrder = request.getParameter("sortOrder");
    		
    	    queryFilter.addSortParam(sortField.toLowerCase().replace("_",""), sortOrder);
    	    
    		queryFilter.addFieldParam("status", OsUser.STATUS_IN_JOB);
    		if(StringUtils.isNotEmpty(fullname)){
    			queryFilter.addLikeFieldParam("fullname", "%" +fullname + "%");
    		}
    		if(StringUtils.isNotEmpty(email)){
    			queryFilter.addLikeFieldParam("email", "%" +email + "%");
    		}
    		if(StringUtils.isNotEmpty(userNo)){
    			queryFilter.addLikeFieldParam("userNo", "%" +userNo+ "%");
    		}
    		queryFilter.addFieldParam("tenantId", tenantId);
    		List list=osUserManager.getAll(queryFilter);
    		return new JsonPageResult<OsUser>(list, queryFilter.getPage().getTotalItems());
    	}
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        OsUser osUser=null;
        List<SysAccount> accountList=null;
        if(StringUtils.isNotBlank(pkId)){
           osUser=osUserManager.get(pkId);
           accountList=sysAaccountManager.getByUserId(pkId);
        }else{
        	osUser=new OsUser();
        	accountList=new ArrayList<SysAccount>();
        }
        return getPathView(request).addObject("osUser",osUser).addObject("accountList", accountList);
    }
    
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	ModelAndView mv=getPathView(request);
    	boolean isWxEanble= wxEntCorpManager.isWxEnable();
    	mv.addObject("isWxEanble",isWxEanble);
    	return mv;
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String groupId=request.getParameter("groupId");
 //   	String relTypeId=request.getParameter("relTypeId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	//绑定的用户账号列表
    	List<SysAccount> accountList=new ArrayList<SysAccount>();
    	String tenantId=request.getParameter("tenantId");
    	OsGroup mainDep=null;
    	List<OsGroup> canDeps=null;
    	List<OsGroup> canGroups=null;
    	ModelAndView mv=getPathView(request);
    	osCustomAttributeManager.addCustomAttribute(mv);
    	OsUser osUser=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		osUser=osUserManager.get(pkId);
    		tenantId=osUser.getTenantId();
    		if("true".equals(forCopy)){
    			osUser.setUserId(null);
    		}else{
    			accountList=sysAaccountManager.getByUserId(pkId);
    		}
    		//获得主部门
    		mainDep=osGroupManager.getMainDeps(pkId);
    		if(mainDep!=null){
        		mv.addObject("mainDepId", mainDep.getGroupId());
        		mv.addObject("mainDepName",mainDep.getName());
        	}
    		
    		//获得其他用户组
    		canDeps=osGroupManager.getCanDeps(pkId);
    		if(canDeps!=null){
        		StringBuffer canDepIds=new StringBuffer();
        		StringBuffer canDepNames=new StringBuffer();
        		for(OsGroup g:canDeps){
        			canDepIds.append(g.getGroupId()).append(",");
        			canDepNames.append(g.getName()).append(",");
        		}
        		if(canDepIds.length()>0){
        			canDepIds.deleteCharAt(canDepIds.length()-1);
        			canDepNames.deleteCharAt(canDepNames.length()-1);
        		}
        		//构建其他部门选项
        		mv.addObject("canDepIds",canDepIds.toString()).addObject("canDepNames",canDepNames.toString());
        	}
    		
    		canGroups=osGroupManager.getCanGroups(pkId);
    	}else{
    		osUser=new OsUser();
    		osUser.setSex("Male");
    		osUser.setStatus(OsUser.STATUS_IN_JOB);
    	}
    	
    	
    	//传入了用户组
    	if(StringUtils.isNotEmpty(groupId) ){
    		mainDep=osGroupManager.get(groupId);
    		//为行程维度
    		if(mainDep!=null && mainDep.getOsDimension()!=null){
	    		if(OsDimension.DIM_ADMIN.equals(mainDep.getOsDimension().getDimKey())){
	    			//主部门
    	    		mv.addObject("mainDepId", mainDep.getGroupId());
    	    		mv.addObject("mainDepName",mainDep.getName());
	    	    
	    		}else{//为其他
	    			canGroups=new ArrayList<OsGroup>();
	    			canGroups.add(mainDep);
	    		}
    		}
    	}
    	//
    	if(canGroups!=null){
    		StringBuffer canGroupIds=new StringBuffer();
    		StringBuffer canGroupNames=new StringBuffer();
    		for(OsGroup g:canGroups){
    			canGroupIds.append(g.getGroupId()).append(",");
    			canGroupNames.append(g.getName()).append(",");
    		}
    		if(canGroupIds.length()>0){
    			canGroupIds.deleteCharAt(canGroupIds.length()-1);
    			canGroupNames.deleteCharAt(canGroupNames.length()-1);
    		}
    		//构建其他组选项
    		mv.addObject("canGroupIds",canGroupIds.toString()).addObject("canGroupNames",canGroupNames.toString());
    	}
    	
    	
    	String domain=null;
    	if(StringUtils.isNotEmpty(tenantId)){
    		SysInst sysInst=sysInstManager.get(tenantId);
    		if(sysInst!=null){
    			domain=sysInst.getDomain();
    		}
    	}else{
    		domain=ContextUtil.getTenant().getDomain();
    	}
    	buildCustomAttribute(mv, osUser);
    	
    	return mv.addObject("osUser",osUser).addObject("domain",domain).addObject("isSaasMode",WebAppUtil.getIsSaasMode())
    			.addObject("tenantId",tenantId)
    			.addObject("accountList",accountList);
    }
    
	/**
	 * 构建自定义属性到edit页面
	 * @param modelAndView
	 * @param osUser
	 */
	private void  buildCustomAttribute(ModelAndView modelAndView,OsUser osUser){
		String tenantId=ContextUtil.getCurrentTenantId();
		String userId=osUser.getUserId();
		List<OsCustomAttribute> osCustomAttributes=osCustomAttributeManager.getUserTypeAttributeByTenantId(tenantId);
		for (int i = 0; i < osCustomAttributes.size(); i++) {
			OsCustomAttribute osCustomAttribute=osCustomAttributes.get(i);
			String attributeId=osCustomAttribute.getID();
			OsAttributeValue osAttributeValue=osAttributeValueManager.getSpecialValueByUser(attributeId,userId);//根据userId获取属性值
			if(osAttributeValue!=null){
				osCustomAttribute.setValue(osAttributeValue.getValue());
			}
		}
		modelAndView.addObject("osCustomAttributes", osCustomAttributes);
	}    
    /**
     * 按用户组及关系类型查找用户
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("listByGroupIdRelTypeId")
    @ResponseBody
    public JsonPageResult listByGroupIdRelTypeId(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String groupId=request.getParameter("groupId");
    	String relTypeId=request.getParameter("relTypeId");
    	String userNo=request.getParameter("userNo");
    	String fullname=request.getParameter("fullname");
    	String instId=getCurTenantId(request);
    	SqlQueryFilter queryFilter=QueryFilterBuilder.createSqlQueryFilter(request);
    	queryFilter.addFieldParam("groupId", groupId);
    	queryFilter.addFieldParam("relTypeId", relTypeId);
    	if(StringUtils.isNotEmpty("instId")){
    		queryFilter.addFieldParam("instId", instId);
    	}
    	if(StringUtils.isNotEmpty(fullname)){
    		queryFilter.addFieldParam("fullname", fullname);
    	}
    	if(StringUtils.isNotEmpty(userNo)){
    		queryFilter.addFieldParam("userNo", userNo);
    	}
    	List list=osUserManager.getByGroupIdRelTypeId(queryFilter);
    	return new JsonPageResult (list, queryFilter.getPage().getTotalItems());
    }
    
    /**
     * 取得用户下的用户组列表
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("listByUserId")
    @ResponseBody
    public JsonPageResult listByUserId(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String userId=request.getParameter("userId");
    	List<OsGroup> osGroups=osGroupManager.getByUserId(userId);
    	JsonPageResult result=new JsonPageResult(osGroups,osGroups.size());
    	return result;
    }
    
    /**
     * 取得用户下所属的用户组
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getBelongGroups")
    @ResponseBody
    public JsonPageResult<OsGroup> getBelongGroups(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String userId=request.getParameter("userId");
    	List<OsGroup> osGroups=osGroupManager.getBelongGroups(userId);
    	JsonPageResult<OsGroup> result=new JsonPageResult<OsGroup>(osGroups,osGroups.size());
    	return result;
    }
    
    /**
     * 加入用户
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("joinUser")
    @ResponseBody
    @LogEnt(action = "joinUser", module = "组织结构", submodule = "系统用户")
    public JsonResult joinUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	String groupId=request.getParameter("groupId");
    	String relTypeId=request.getParameter("relTypeId");
    	String userIds=request.getParameter("userIds");
    	
    	 if(StringUtils.isNotEmpty(groupId)&& StringUtils.isNotEmpty(relTypeId)){
         	OsRelType osRelType=osRelTypeManager.get(relTypeId);
         	String[] uIds=userIds.split("[,]");
         	for(String userId:uIds){
         		OsRelInst inst1=osRelInstManager.getByParty1Party2RelTypeId(groupId, userId, relTypeId);
         		
         		if(inst1!=null) continue;
         		
         		String isMain=MBoolean.NO.name();
         		List<OsRelInst> instList=  osRelInstManager.getByRelTypeIdParty2(relTypeId, userId);
         		if(instList.size()==0){
         			isMain=MBoolean.YES.name();
         		}
         		
	         	OsRelInst inst=new OsRelInst();
	         	inst.setDim1(osGroupManager.get(groupId).getOsDimension().getDimId());
	         	inst.setParty1(groupId);
	         	inst.setParty2(userId);
	         	inst.setRelTypeKey(osRelType.getKey());
	         	inst.setRelType(osRelType.getRelType());
	         	inst.setOsRelType(osRelType);
	         	inst.setIsMain(isMain);
	         	inst.setStatus(MBoolean.ENABLED.toString());
	         	inst.setInstId(idGenerator.getSID());
	         	osRelInstManager.create(inst);
         	}
         }
    	 
    	 return new JsonResult(true,"成功加入!");
    }
    
    
    /**
     * 添加用户组
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("addGroup")
    @ResponseBody
    @LogEnt(action = "addGroup", module = "组织结构", submodule = "系统用户")
    public JsonResult addGroup(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String userId=request.getParameter("userId");
    	String groupIds=request.getParameter("groupIds");
    	String[]gIds=groupIds.split("[,]");
    	
    	//取得从属关系
    	OsRelType osRelType=osRelTypeManager.getBelongRelType();
    	for(String groupId:gIds){
    		OsRelInst inst1=osRelInstManager.getByParty1Party2RelTypeId(groupId, userId, osRelType.getId());
     		if(inst1!=null) continue;
     		
         	OsRelInst inst=new OsRelInst();
         	inst.setParty1(groupId);
         	inst.setParty2(userId);
         	inst.setRelTypeKey(osRelType.getKey());
         	inst.setDim1(OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
         	inst.setOsRelType(osRelType);

         	inst.setStatus(MBoolean.ENABLED.toString());
         	inst.setInstId(idGenerator.getSID());
         	osRelInstManager.create(inst);
    	}
    	
    	return new JsonResult(true,"成功加入!");
    }
    
    /**
     * 移除关系的用户
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("unjoinUser")
    @ResponseBody
    @LogEnt(action = "unjoinUser", module = "组织结构", submodule = "系统用户")
    public JsonResult unjoinUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String groupId=request.getParameter("groupId");
    	String relTypeId=request.getParameter("relTypeId");
    	String userIds=request.getParameter("userIds");
    	 if(StringUtils.isNotEmpty(groupId)&& StringUtils.isNotEmpty(relTypeId)){
         	String[] uIds=userIds.split("[,]");
         	for(String userId:uIds){
         		OsRelInst inst1=osRelInstManager.getByParty1Party2RelTypeId(groupId, userId, relTypeId);
	         	osRelInstManager.delete(inst1.getInstId());
         	}
         }
    	 return new JsonResult(true,"成功移除!");
    }
    /**
     * 移除用户的从属关系类型
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("removeBelongRelType")
    @ResponseBody
    @LogEnt(action = "removeBelongRelType", module = "组织结构", submodule = "系统用户")
    public JsonResult removeBelongRelType(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String groupId=request.getParameter("groupId");
    	String userId=request.getParameter("userId");
    	OsRelInst inst1=osRelInstManager.getByParty1Party2RelTypeId(groupId, userId, OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
    	if(inst1!=null){
    		osRelInstManager.delete(inst1.getInstId());
    	}
    	return new JsonResult(true, "成功删除用户的从属关系");
    }
    
    /**
     * 用户对话框
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("dialog")
    @ResponseBody
    public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String tenantId=getCurTenantId(request);
    	//取得默认的行政维度
    	OsDimension adminDim=osDimensionManager.getByDimKeyTenantId(OsDimension.DIM_ADMIN, tenantId);
    	return getPathView(request).addObject("adminDim", adminDim);
    }
    
    /**
     * 用户编辑个人信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("infoEdit")
    @ResponseBody
    public ModelAndView infoEdit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=ContextUtil.getCurrentUserId();
    	String groupId=request.getParameter("groupId");
    	String relTypeId=request.getParameter("relTypeId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	//绑定的用户账号列表
    	List<SysAccount> accountList=new ArrayList<SysAccount>();
    	
    	OsGroup mainDep=null;
    	List<OsGroup> canDeps=null;
    	List<OsGroup> canGroups=null;
    	ModelAndView mv=getPathView(request);
    	OsUser osUser=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		osUser=osUserManager.get(pkId);
    		if("true".equals(forCopy)){
    			osUser.setUserId(null);
    		}else{
    			accountList=sysAaccountManager.getByUserId(pkId);
    		}
    		//获得主部门
    		mainDep=osGroupManager.getMainDeps(pkId);
    		if(mainDep!=null){
        		mv.addObject("mainDepId", mainDep.getGroupId());
        		mv.addObject("mainDepName",mainDep.getName());
        	}
    		
    		//获得其他用户组
    		canDeps=osGroupManager.getCanDeps(pkId);
    		if(canDeps!=null){
        		StringBuffer canDepIds=new StringBuffer();
        		StringBuffer canDepNames=new StringBuffer();
        		for(OsGroup g:canDeps){
        			canDepIds.append(g.getGroupId()).append(",");
        			canDepNames.append(g.getName()).append(",");
        		}
        		if(canDepIds.length()>0){
        			canDepIds.deleteCharAt(canDepIds.length()-1);
        			canDepNames.deleteCharAt(canDepNames.length()-1);
        		}
        		//构建其他部门选项
        		mv.addObject("canDepIds",canDepIds.toString()).addObject("canDepNames",canDepNames.toString());
        	}
    		
    		canGroups=osGroupManager.getCanGroups(pkId);
    	}else{
    		osUser=new OsUser();
    		osUser.setSex("Male");
    		osUser.setStatus(OsUser.STATUS_IN_JOB);
    	}
    	//传入了用户组
    	if(StringUtils.isNotEmpty(groupId) ){
    		mainDep=osGroupManager.get(groupId);
    		//为行程维度
    		if(mainDep.getOsDimension()!=null){
	    		if(OsDimension.DIM_ADMIN.equals(mainDep.getOsDimension().getDimKey())){
	    			//主部门
    	    		mv.addObject("mainDepId", mainDep.getGroupId());
    	    		mv.addObject("mainDepName",mainDep.getName());
	    	    
	    		}else{//为其他
	    			canGroups=new ArrayList<OsGroup>();
	    			canGroups.add(mainDep);
	    		}
    		}
    	}
    	//
    	if(canGroups!=null){
    		StringBuffer canGroupIds=new StringBuffer();
    		StringBuffer canGroupNames=new StringBuffer();
    		for(OsGroup g:canGroups){
    			canGroupIds.append(g.getGroupId()).append(",");
    			canGroupNames.append(g.getName()).append(",");
    		}
    		if(canGroupIds.length()>0){
    			canGroupIds.deleteCharAt(canGroupIds.length()-1);
    			canGroupNames.deleteCharAt(canGroupNames.length()-1);
    		}
    		//构建其他组选项
    		mv.addObject("canGroupIds",canGroupIds.toString()).addObject("canGroupNames",canGroupNames.toString());
    	}
    	
    	String tenantId=request.getParameter("tenantId");
    	String domain=null;
    	if(StringUtils.isNotEmpty(tenantId)){
    		SysInst sysInst=sysInstManager.get(tenantId);
    		if(sysInst!=null){
    			domain=sysInst.getDomain();
    		}
    	}else{
    		domain=ContextUtil.getTenant().getDomain();
    	}
    	
    	return mv.addObject("osUser",osUser).addObject("domain",domain)
//    			.addObject("groupId",groupId).addObject("relTypeId",relTypeId)
    			.addObject("accountList",accountList);
    }
    
    /**
     * 用户编辑个人信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("info")
    @ResponseBody
    public ModelAndView info(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=ContextUtil.getCurrentUserId();
    	String groupId=request.getParameter("groupId");
    	String relTypeId=request.getParameter("relTypeId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	//绑定的用户账号列表
    	List<SysAccount> accountList=new ArrayList<SysAccount>();
    	
    	OsGroup mainDep=null;
    	List<OsGroup> canDeps=null;
    	List<OsGroup> canGroups=null;
    	ModelAndView mv=getPathView(request);
    	OsUser osUser=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		osUser=osUserManager.get(pkId);
    		if("true".equals(forCopy)){
    			osUser.setUserId(null);
    		}else{
    			accountList=sysAaccountManager.getByUserId(pkId);
    		}
    		//获得主部门
    		mainDep=osGroupManager.getMainDeps(pkId);
    		if(mainDep!=null){
        		mv.addObject("mainDepId", mainDep.getGroupId());
        		mv.addObject("mainDepName",mainDep.getName());
        	}
    		
    		//获得其他用户组
    		canDeps=osGroupManager.getCanDeps(pkId);
    		if(canDeps!=null){
        		StringBuffer canDepIds=new StringBuffer();
        		StringBuffer canDepNames=new StringBuffer();
        		for(OsGroup g:canDeps){
        			canDepIds.append(g.getGroupId()).append(",");
        			canDepNames.append(g.getName()).append(",");
        		}
        		if(canDepIds.length()>0){
        			canDepIds.deleteCharAt(canDepIds.length()-1);
        			canDepNames.deleteCharAt(canDepNames.length()-1);
        		}
        		//构建其他部门选项
        		mv.addObject("canDepIds",canDepIds.toString()).addObject("canDepNames",canDepNames.toString());
        	}
    		
    		canGroups=osGroupManager.getCanGroups(pkId);
    	}else{
    		osUser=new OsUser();
    		osUser.setSex("Male");
    		osUser.setStatus(OsUser.STATUS_IN_JOB);
    	}
    	//传入了用户组
    	if(StringUtils.isNotEmpty(groupId) ){
    		mainDep=osGroupManager.get(groupId);
    		//为行程维度
    		if(mainDep.getOsDimension()!=null){
	    		if(OsDimension.DIM_ADMIN.equals(mainDep.getOsDimension().getDimKey())){
	    			//主部门
    	    		mv.addObject("mainDepId", mainDep.getGroupId());
    	    		mv.addObject("mainDepName",mainDep.getName());
	    	    
	    		}else{//为其他
	    			canGroups=new ArrayList<OsGroup>();
	    			canGroups.add(mainDep);
	    		}
    		}
    	}
    	//
    	if(canGroups!=null){
    		StringBuffer canGroupIds=new StringBuffer();
    		StringBuffer canGroupNames=new StringBuffer();
    		for(OsGroup g:canGroups){
    			canGroupIds.append(g.getGroupId()).append(",");
    			canGroupNames.append(g.getName()).append(",");
    		}
    		if(canGroupIds.length()>0){
    			canGroupIds.deleteCharAt(canGroupIds.length()-1);
    			canGroupNames.deleteCharAt(canGroupNames.length()-1);
    		}
    		//构建其他组选项
    		mv.addObject("canGroupIds",canGroupIds.toString()).addObject("canGroupNames",canGroupNames.toString());
    	}
    	
    	String tenantId=request.getParameter("tenantId");
    	String domain=null;
    	if(StringUtils.isNotEmpty(tenantId)){
    		SysInst sysInst=sysInstManager.get(tenantId);
    		if(sysInst!=null){
    			domain=sysInst.getDomain();
    		}
    	}else{
    		domain=ContextUtil.getTenant().getDomain();
    	}
    	
    	return mv.addObject("osUser",osUser).addObject("domain",domain)
//    			.addObject("groupId",groupId).addObject("relTypeId",relTypeId)
    			.addObject("accountList",accountList);
    }
    
	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return osUserManager;
	}
	
	@RequestMapping("listTenantUser")
	@ResponseBody
	public List<OsUser> listTenantUser(HttpServletRequest request,HttpServletResponse response){
		String tenantId=ContextUtil.getCurrentTenantId();
		List<OsUser> osUsers=osUserManager.getAllByTenantId(tenantId);
		return osUsers;
	}

}
