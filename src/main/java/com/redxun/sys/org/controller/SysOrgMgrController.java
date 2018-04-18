package com.redxun.sys.org.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.mybatis.CommonDao;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.manager.OrgDataImportManager;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRelInstManager;
import com.redxun.sys.org.manager.OsRelTypeManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 组织用户实例管理
 * 
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn） 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/org/sysOrg/")
public class SysOrgMgrController extends BaseController {
	@Resource
	private OsGroupManager osGroupManager;
	@Resource
	private OsDimensionManager osDimensionManager;
	@Resource
	private OsRelTypeManager osRelTypeManager;
	@Resource
	private OsRelInstManager osRelInstManager;
	@Resource
	private SysInstManager sysInstManager;
	@Resource
	private CommonDao commonDao;
	@Autowired
	OrgDataImportManager orgDataImportManager;

	/**
	 * 进入组织架构管理页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mgr")
	public ModelAndView mgr(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String instId = request.getParameter("tenantId");
		SysInst sysInst = null;
		if (StringUtils.isNotEmpty(instId) && !"null".equals(instId)) {
			// 当前用户为指定的管理机构下的用户才可以查询到指定的租用机构下的数据
			if (WebAppUtil.getOrgMgrDomain().equals(
					ContextUtil.getTenant().getDomain())) {
				sysInst = sysInstManager.get(instId);
			}
		}
		if (sysInst == null) {
			sysInst = sysInstManager.get("1");
		}
		
		return getPathView(request).addObject("sysInst", sysInst);
	}

	@RequestMapping("delGroup")
	@ResponseBody
	@LogEnt(action = "delGroup", module = "组织结构", submodule = "系统用户组")
	public JsonResult delGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String groupId = request.getParameter("groupId");
		OsGroup osGroup = osGroupManager.get(groupId);
		if (osGroup != null) {
			osGroupManager.delAndUpdateChilds(groupId);
			return new JsonResult(true, "成功删除用户组-" + osGroup.getName());
		}
		return new JsonResult(false, "删除失败！");
	}

	@RequestMapping("saveGroup")
	@ResponseBody
	@LogEnt(action = "savfeGroup", module = "组织结构", submodule = "系统用户组")
	public JsonResult saveGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dimId = request.getParameter("dimId");
		OsDimension osDimension = osDimensionManager.get(dimId);
		String data = request.getParameter("data");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "children" });
		JSONObject jsonObj = JSONObject.fromObject(data, jsonConfig);
		OsGroup osGroup = (OsGroup) JSONObject.toBean(jsonObj, OsGroup.class);
		String tenantId = getCurTenantId(request);
		if (StringUtils.isEmpty(osGroup.getGroupId())) {
			osGroup.setGroupId(idGenerator.getSID());
			osGroup.setStatus(MBoolean.ENABLED.toString());
			if (StringUtils.isNotEmpty(osGroup.getParentId())) {
				OsGroup parentGroup = osGroupManager.get(osGroup.getParentId());
				osGroup.setPath(parentGroup.getPath() + osGroup.getGroupId()
						+ ".");
				// osGroup.setParentGroup(parentGroup);
				// parentGroup.setChilds(parentGroup.getChilds()+1);
				osGroupManager.update(parentGroup);
			} else {
				osGroup.setParentId("0");
				osGroup.setPath("0." + osGroup.getGroupId() + ".");
			}
			osGroup.setChilds(0);
			osGroup.setOsDimension(osDimension);
			osGroup.setTenantId(tenantId);
			osGroupManager.create(osGroup);
		} else {
			OsGroup tmpGroup = osGroupManager.get(osGroup.getGroupId());
			BeanUtil.copyNotNullProperties(tmpGroup, osGroup);
			// osGroup.setOsDimension(osDimension);
			osGroupManager.update(tmpGroup);
		}
		return new JsonResult(true, "成功保存用户组-" + osGroup.getName(), osGroup);
	}

	/**
	 * 按维度查找用户组
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByDimId")
	@ResponseBody
	public List<OsGroup> listByDimId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 默认进入行政维度
		String dimId = request.getParameter("dimId");
		if("topContacts".equals(dimId)){//如果是展示常用联系人则采用自定义查询返回常用联系人
			List<?> contactTypes=commonDao.query("select F_LXRFL from w_topcontacts where CREATE_USER_ID_="+ContextUtil.getCurrentUserId()+" group by F_LXRFL");
			List<OsGroup> osGroups=new ArrayList<OsGroup>();
			for (int i = 0; i < contactTypes.size(); i++) {
				Map<String, Object> map=(Map<String, Object>) contactTypes.get(i);
				String contactType=(String) map.get("F_LXRFL");
				OsGroup osGroup=new OsGroup();
				osGroup.setGroupId(contactType);
				osGroup.setName(contactType);
				osGroups.add(osGroup);
			}
			return osGroups;
		}else{//不显示常用联系人时
			String parentId = request.getParameter("parentId");
			if (StringUtils.isEmpty(parentId)) {
				parentId = "0";
			}
			String tenantId = getCurTenantId(request);
			if (StringUtils.isEmpty(dimId)) {
				OsDimension osDimension = osDimensionManager.getByDimKeyTenantId(
						OsDimension.DIM_ADMIN, ITenant.PUBLIC_TENANT_ID);
				dimId = osDimension.getDimId();
			}

			List<OsGroup> osGroups = null;

			if ("0".equals(parentId)) {
				osGroups = osGroupManager.getByDimIdGroupIdTenantId(dimId,
						parentId, tenantId);
			} else {
				osGroups = osGroupManager.getByParentId(parentId);
			}

			return osGroups;
		}
		
	}

	/**
	 * 用户组对话框的搜索
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("search")
	@ResponseBody
	public List<OsGroup> search(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dimId = request.getParameter("dimId");
		String showDimId = request.getParameter("showDimId");
		
		
		//打开窗口初始化参数
		String config = request.getParameter("config");
		com.alibaba.fastjson.JSONObject configJson = null;
		String type = "";
		if(StringUtils.isNotBlank(config)){
			configJson = com.alibaba.fastjson.JSONObject.parseObject(config);
			type = configJson.getString("type");
		}		
		
		
		
		//定义窗口初始化数据类型
		JSONObject typeJson = new JSONObject();		
		typeJson.put("specific", "specific");
		typeJson.put("current", "current");
		typeJson.put("all", "all");
		typeJson.put("level", "level");
		
		String searchType = "";
		if(StringUtil.isNotEmpty(type)){
			searchType = typeJson.getString(type);
		}
		
		
		String tempDimId = null;
		String tenantId = getCurTenantId(request);
		// 排队了行政维度
		String excludeAdmin = request.getParameter("excludeAdmin");
		// 优先使用页面参数中指定显示的维度下的用户组
		if ("true".equals(excludeAdmin)) {// 显示角色维度
			OsDimension osDimension = osDimensionManager
					.get(OsDimension.DIM_ROLE_ID);
			tempDimId = osDimension.getDimId();
		} else if (StringUtils.isNotEmpty(showDimId)) {
			tempDimId = showDimId;
		} else if (StringUtils.isNotEmpty(dimId)) {// 当点击维度树时显示的用户组
			tempDimId = dimId;
		} else {// 默认显示行政维度下的用户组
			OsDimension osDimension = osDimensionManager
					.get(OsDimension.DIM_ADMIN_ID);
			tempDimId = osDimension.getDimId();
		}
		String name = request.getParameter("name");
		String key = request.getParameter("key");
		String parentId = request.getParameter("parentId");

		List list = null;		
		
		//窗口打开初始数据
		if(StringUtils.isNotBlank(searchType)&& !("all".equals(searchType))){
			QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
			String rankLevel = configJson.getString("grouplevel");
			String groupIds = configJson.getString("groupids");
			
			//当前用户所属部门
			if("current".equals(searchType)){
				String userId = ContextUtil.getCurrentUserId();
				list = osGroupManager.getBelongDeps(userId);								
			}else if("level".equals(searchType)&&StringUtils.isNotBlank(rankLevel)){
				//根据指定级别查找用户组				
				list = osGroupManager.getByDimAndLevel(OsDimension.DIM_ADMIN_ID,rankLevel);				
			}else if(StringUtils.isNotBlank(groupIds)){				
				queryFilter.addFieldParam("groupId", QueryParam.OP_IN, groupIds.split(","));
				list = osGroupManager.getAll(queryFilter);
			}			
			return list;
			
		}
		
		if ((StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(key))
				&& StringUtils.isEmpty(parentId)) {
			list = osGroupManager.getByDimIdNameKey(tenantId, tempDimId, name,
					key);
		} else if (StringUtils.isEmpty(parentId)) {
			list = osGroupManager.getByDimIdGroupIdTenantId(tempDimId, "0",
					tenantId);
		} 
		else{			
			list = osGroupManager.getByParentId(parentId);
			return list;
		}
		
		
		
		
		return list;
		
		
	}

	/**
	 * 查找有某种的关系的用户组列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByGroupIdRelTypeId")
	@ResponseBody
	public JsonPageResult listByGroupIdRelTypeId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String groupId = request.getParameter("groupId");
		String relTypeId = request.getParameter("relTypeId");
		SqlQueryFilter filter = QueryFilterBuilder
				.createSqlQueryFilter(request);
		List<OsRelInst> list = osRelInstManager.getByGroupIdRelTypeId(groupId,
				relTypeId, filter);
		return new JsonPageResult(list, filter.getPage().getTotalItems());
	}

	/**
	 * 保存用户组实例数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveGroupRelInst")
	@ResponseBody
	@LogEnt(action = "saveGroupRelInst", module = "组织结构", submodule = "系统用户组")
	public JsonResult saveGroupRelInst(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String insts = request.getParameter("insts");
		JSONArray arrs = JSONArray.fromObject(insts);
		for (int i = 0; i < arrs.size(); i++) {
			JSONObject obj = arrs.getJSONObject(i);
			OsRelInst inst = (OsRelInst) JSONObject
					.toBean(obj, OsRelInst.class);
			OsRelInst tempInst = osRelInstManager.get(inst.getInstId());
			tempInst.setAlias(inst.getAlias());
			osRelInstManager.update(tempInst);
		}
		return new JsonResult(true, "成功保存！");
	}

	/**
	 * 批量删除用户组间的某种关系
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("removeOsRelInst")
	@ResponseBody
	@LogEnt(action = "removeOsRelInst", module = "组织结构", submodule = "系统用户组")
	public JsonResult removeOsRelInst(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String instIds = request.getParameter("instIds");

		String[] ids = instIds.split("[,]");
		for (String id : ids) {
			osRelInstManager.delete(id);
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 为用户组按关系关联用户组
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("joinGroups")
	@ResponseBody
	@LogEnt(action = "joinGroups", module = "组织结构", submodule = "系统用户组")
	public JsonResult joinGroups(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String groupId = request.getParameter("groupId");
		String groupIds = request.getParameter("groupIds");
		String relTypeId = request.getParameter("relTypeId");
		String[] gIds = groupIds.split("[,]");
		OsRelType osRelType = osRelTypeManager.get(relTypeId);
		for (String gId : gIds) {
			OsRelInst inst1 = osRelInstManager.getByParty1Party2RelTypeId(
					groupId, gId, relTypeId);
			if (inst1 != null)
				continue;
			OsRelInst inst = new OsRelInst();
			inst.setParty1(groupId);
			inst.setParty2(gId);
			inst.setRelTypeKey(osRelType.getKey());
			inst.setOsRelType(osRelType);
			// TODO加入维度配置信息
			inst.setStatus(MBoolean.ENABLED.toString());
			inst.setInstId(idGenerator.getSID());
			inst.setIsMain(MBoolean.NO.name());

			OsGroup mainGroup = osGroupManager.get(groupId);
			OsGroup subGroup = osGroupManager.get(gId);

			if (mainGroup != null && subGroup != null) {
				inst.setAlias(mainGroup.getName() + "-" + subGroup.getName());
			}

			osRelInstManager.create(inst);
		}
		return new JsonResult(true, "成功加入！");
	}

	/**
	 * 批量保存用户组
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("batchSaveGroup")
	@ResponseBody
	@LogEnt(action = "batchSaveGroup", module = "组织结构", submodule = "系统用户组")
	public JsonResult batchSaveGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dimId = request.getParameter("dimId");
		OsDimension osDimension = osDimensionManager.get(dimId);
		String gridData = request.getParameter("gridData");
		String tenantId = getCurTenantId(request);
		genGroups(gridData, null, tenantId, osDimension);
		return new JsonResult(true, "成功保存用户组！");
	}

	/**
	 * 产生用户组的上下级
	 * 
	 * @param menuJson
	 * @param parentMenu
	 * @param subsystem
	 */
	private void genGroups(String groupJson, OsGroup parentGroup,
			String tenantId, OsDimension osDimension) {
		JSONArray jsonArray = JSONArray.fromObject(groupJson);
		if (parentGroup != null) {
			parentGroup.setChilds(jsonArray.size());
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			Object groupId = jsonObj.get("groupId");
			OsGroup osGroup = null;
			// 是否为创建
			boolean isCreated = false;
			if (groupId == null) {
				osGroup = new OsGroup();
				osGroup.setStatus(MBoolean.ENABLED.toString());
				osGroup.setGroupId(idGenerator.getSID());
				if (StringUtils.isNotEmpty(tenantId)) {
					osGroup.setTenantId(tenantId);
				}
				osGroup.setOsDimension(osDimension);
				isCreated = true;
			} else {
				osGroup = osGroupManager.get(groupId.toString());
			}

			String name = jsonObj.getString("name");
			String key = jsonObj.getString("key");
			Integer rankLevel = JSONUtil.getInt(jsonObj, "rankLevel");
			int sn = JSONUtil.getInt(jsonObj, "sn");

			osGroup.setName(name);
			osGroup.setKey(key);
			osGroup.setRankLevel(rankLevel);
			osGroup.setSn(sn);

			if (parentGroup == null) {
				osGroup.setParentId("0");
				osGroup.setPath("0." + osGroup.getGroupId() + ".");
			} else {
				osGroup.setParentId(parentGroup.getGroupId());
				osGroup.setPath(parentGroup.getPath() + osGroup.getGroupId()
						+ ".");
			}

			String children = JSONUtil.getString(jsonObj, "children");
			if (StringUtils.isNotEmpty(children)) {
				genGroups(children, osGroup, tenantId, osDimension);
			}
			if (isCreated) {
				osGroupManager.create(osGroup);
			} else {
				osGroupManager.update(osGroup);
			}
		}

	}
	
	
	/**
	 * 同步组织数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*
	@RequestMapping("syncOrg")
	@ResponseBody
	@LogEnt(action = "syncOrg", module = "同步组织用户数据", submodule = "系统用户组")
	public JsonResult syncOrg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String instId=request.getParameter("instId");
		
		if(StringUtils.isNotEmpty(instId)){
			instId=ITenant.ADMIN_TENANT_ID;
		}
		//清空旧的数据
		orgDataImportManager.delDepAndUsers();
		
		SysInst sysInst=sysInstManager.get(instId);
		//同步部门
		orgDataImportManager.doSynDepartment(instId);
		//同步部门用户
		orgDataImportManager.doSyncDepUser(instId,sysInst.getDomain());
		
		return new JsonResult(true,"同步成功！");
	}*/

}
