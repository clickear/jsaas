package com.redxun.sys.core.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.bpm.core.entity.BpmAuthDef;
import com.redxun.bpm.core.entity.BpmAuthRights;
import com.redxun.bpm.core.entity.BpmAuthSetting;
import com.redxun.bpm.core.manager.BpmAuthDefManager;
import com.redxun.bpm.core.manager.BpmAuthRightsManager;
import com.redxun.bpm.core.manager.BpmAuthSettingManager;
import com.redxun.bpm.core.manager.BpmSolutionManager;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.context.ProfileUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.entity.SysTreeCat;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.log.LogEnt;

/**
 * 系统树管理
 * 
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 *            本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysTree/")
public class SysTreeController extends BaseListController {
	@Resource
	SysTreeManager sysTreeManager;
	@Resource
	BpmSolutionManager bpmSolutionManager;
	@Resource
	BpmAuthSettingManager bpmAuthSettingManager;
	@Resource
	BpmAuthDefManager bpmAuthDefManager;
	@Resource
	BpmAuthRightsManager bpmAuthRightsManager;

	/**
	 * 查找某分类下有权限的树的列表数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByCatKey")
	@ResponseBody
	public List<SysTree> listByCatKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catKey=request.getParameter("catKey");
		List<SysTree> treeList=sysTreeManager.getByCatKeyTenantId(catKey, ContextUtil.getCurrentTenantId());
		return treeList;
	}
	
	/**
	 * 查找某分类下的树的列表数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listAllByCatKey")
	@ResponseBody
	public List<SysTree> listAllByCatKey(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String catKey = request.getParameter("catKey");
		Map<String, Set<String>> proFileMap = ProfileUtil.getCurrentProfile();

		List<SysTree> treeList = sysTreeManager.getByCatKeyTenantId(catKey, ContextUtil.getCurrentTenantId());
		IUser user=ContextUtil.getCurrentUser();
		if(user.isSuperAdmin()){//超管不过滤
			return treeList;
		}
		for (int i = 0; i < treeList.size(); i++) {
			SysTree sysTree = treeList.get(i);
			BpmAuthSetting bpmAuthSetting = bpmAuthSettingManager.getSettingByDefTreeId(sysTree.getTreeId());
			Boolean leftOrNot = false;
			if (bpmAuthSetting != null) {
				String settingId = bpmAuthSetting.getId();
				BpmAuthDef bpmAuthDef = bpmAuthDefManager.getUniqueByTreeIdAndSettingId(sysTree.getTreeId(), settingId);
				sysTree.setRight(bpmAuthDef.getRightJson());// 这里不需要判断bpmAuthDef非空,因为前面判断了setting非空,本质上setting不为空,def就不为空,因为是同步save的
				List<BpmAuthRights> bpmAuthRights = bpmAuthRightsManager.getBySettingId(settingId);
				AuthRightLoop: for (BpmAuthRights bpmAuthRights2 : bpmAuthRights) {
					String authType = bpmAuthRights2.getType();
					String authId = bpmAuthRights2.getAuthId();
					if (proFileMap.get(authType).contains(authId)) {
						leftOrNot = true;
						break AuthRightLoop;// 一旦存在则说明至少有身份在内,则不过滤,跳出此处循环
					}
				}
				if (!leftOrNot) {
					treeList.remove(i);
					i--;
				}
			}
		}
		return treeList;
	}
	

	@RequestMapping("listBpmSolution")
	@ResponseBody
	public List<SysTree> listBpmSolution(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysTree> treeList = sysTreeManager.getByCatKeyTenantId("CAT_BPM_SOLUTION", ContextUtil.getCurrentTenantId());
		return treeList;
	}

	/**
	 * 查找某分类下的某个treeId下的列表数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByCatKeyTreeId")
	@ResponseBody
	public List<SysTree> listByCatKeyTreeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catKey = request.getParameter("catKey");
		String treeId = request.getParameter("treeId");
		List<SysTree> treeList = sysTreeManager.getByCatKeyTreeId(catKey, treeId, ContextUtil.getCurrentTenantId());
		return treeList;
	}

	/**
	 * 显示数据字典下的分类
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listDicTree")
	@ResponseBody
	public List<SysTree> listDicTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysTree> treeList = sysTreeManager.getByCatKeyTenantId(SysTreeCat.CAT_DIM, ITenant.ADMIN_TENANT_ID);
		return treeList;
	}

	/**
	 * 显示个人文件夹
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("myFileFolder")
	@ResponseBody
	public List<SysTree> myFileFolder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = ContextUtil.getCurrentUserId();
		List<SysTree> sysTreeList = sysTreeManager.getPersonFileTree(userId);
		SysTree rootFolder = new SysTree();
		rootFolder.setTreeId("0");
		rootFolder.setName("我的附件");
		rootFolder.setParentId("-1");
		sysTreeList.add(rootFolder);
		return sysTreeList;
	}

	/**
	 * 保存个人附件的文件目录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveFolder")
	@ResponseBody
	@LogEnt(action = "saveFolder", module = "系统内核", submodule = "系统树")
	public JsonResult saveFolder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		String sn = request.getParameter("sn");
		String treeId = request.getParameter("treeId");
		String parentId = request.getParameter("parentId");
		SysTree parent = null;
		SysTree sysTree = null;

		boolean isCreate = false;

		if (StringUtils.isNotEmpty(treeId)) {
			sysTree = sysTreeManager.get(treeId);
		} else {
			sysTree = new SysTree();
			treeId = idGenerator.getSID();
			sysTree.setTreeId(treeId);
			// 用主键作为主键
			sysTree.setKey(treeId);
			sysTree.setCatKey(SysTree.CAT_PERSON_FILE);
			sysTree.setDataShowType(SysTree.SHOW_TYPE_FLAT);
			sysTree.setUserId(ContextUtil.getCurrentUserId());
			isCreate = true;
		}
		if ("0".equals(parentId) || StringUtils.isEmpty(parentId)) {
			parentId = "0";
			sysTree.setPath(parentId + "." + sysTree.getTreeId() + ".");
			sysTree.setDepth(1);
		} else {
			parent = sysTreeManager.get(parentId);
			sysTree.setPath(parent.getPath() + sysTree.getTreeId() + ".");
			sysTree.setDepth(parent.getDepth() + 1);
		}
		sysTree.setParentId(parentId);
		sysTree.setName(name);
		sysTree.setSn(new Integer(sn));

		if (isCreate) {
			sysTreeManager.update(sysTree);
		} else {
			sysTreeManager.create(sysTree);
		}

		return new JsonResult(true, "成功保存！");
	}

	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}

	@RequestMapping("del")
	@ResponseBody
	@LogEnt(action = "del", module = "系统内核", submodule = "系统树")
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				sysTreeManager.deleteCascade(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 查看明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		SysTree sysTree = null;
		if (StringUtils.isNotBlank(pkId)) {
			sysTree = sysTreeManager.get(pkId);
		} else {
			sysTree = new SysTree();
		}
		return getPathView(request).addObject("sysTree", sysTree);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String parentId = request.getParameter("parentId");
		String catKey = request.getParameter("catKey");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		SysTree sysTree = null;
		if (StringUtils.isNotEmpty(pkId)) {
			sysTree = sysTreeManager.get(pkId);
			if ("true".equals(forCopy)) {
				sysTree.setTreeId(null);
			}
		} else {
			sysTree = new SysTree();
			sysTree.setCatKey(catKey);
			if (!"0".equals(parentId)) {
				sysTree.setParentId(parentId);
			}
			sysTree.setDataShowType(SysTree.SHOW_TYPE_FLAT);
		}

		return getPathView(request).addObject("sysTree", sysTree);
	}

	@RequestMapping("edit2")
	public ModelAndView edit2(HttpServletRequest request) throws Exception {
		String treeId = request.getParameter("treeId");
		String parentId = request.getParameter("parentId");

		SysTree sysTree = null;
		if (StringUtils.isNotEmpty(treeId)) {
			sysTree = sysTreeManager.get(treeId);
		} else {
			sysTree = new SysTree();
			sysTree.setParentId(parentId);
		}

		return getPathView(request).addObject("sysTree", sysTree);
	}

	@RequestMapping("editKmsType")
	public ModelAndView edit3(HttpServletRequest request) throws Exception {
		String nodetype = request.getParameter("nodetype");
		String treeId = request.getParameter("treeId");
		String parentId = request.getParameter("parentId");

		SysTree sysTree = null;
		if (StringUtils.isNotEmpty(treeId)) {
			sysTree = sysTreeManager.get(treeId);
		} else {
			sysTree = new SysTree();
			if ("parent".equals(nodetype))
				sysTree.setParentId(null);
			else if ("sub".equals(nodetype))
				sysTree.setParentId(parentId);
		}
		return getPathView(request).addObject("sysTree", sysTree);
	}

	@RequestMapping("getNameByPath")
	@ResponseBody
	public JsonResult getNameByPath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getParameter("path");
		String[] paths = {};
		if (StringUtils.isNotEmpty(path)) {
			paths = path.split("\\.");
		}
		String name = "";
		for (int i = 0; i < paths.length; i++) {
			if (i == 0) {
				name = sysTreeManager.get(paths[i]).getName();
				continue;
			}
			name = name + "/" + sysTreeManager.get(paths[i]).getName();
		}
		// Map<String, String> map=new HashMap<String, String>();
		return new JsonResult(true, "success", name);
	}

	@RequestMapping("saveKmsType")
	@ResponseBody
	public JsonResult saveKmsType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		String sn = request.getParameter("sn");
		String key = request.getParameter("key");
		String treeId = request.getParameter("treeId");
		String parentId = request.getParameter("parentId");
		String catKey = request.getParameter("catKey");
		SysTree sysTree = null;
		boolean isCreate = false;
		if (StringUtils.isEmpty(treeId)) {
			sysTree = new SysTree();
			sysTree.setTreeId(idGenerator.getSID());
			if (StringUtils.isEmpty(parentId)) {
				sysTree.setParentId(null);
				sysTree.setPath(sysTree.getTreeId() + ".");
				sysTree.setDepth(0);
			} else {
				SysTree parent = sysTreeManager.get(parentId);
				sysTree.setParentId(parentId);
				sysTree.setPath(parent.getPath() + sysTree.getTreeId() + ".");
				sysTree.setDepth(parent.getDepth() + 1);
			}
			sysTree.setCatKey(catKey);
			isCreate = true;
		} else {
			sysTree = sysTreeManager.get(treeId);
		}
		sysTree.setSn(new Integer(sn));
		sysTree.setKey(key);
		sysTree.setName(name);
		if (isCreate)
			sysTreeManager.create(sysTree);
		else
			sysTreeManager.update(sysTree);
		return new JsonResult(true, "成功保存分类");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByCatKeyAndKey")
	@ResponseBody
	public JsonResult getByCatKeyAndKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catKey = request.getParameter("catKey");
		if (StringUtil.isEmpty(catKey))
			catKey = SysTreeCat.CAT_DIM;
		String key = request.getParameter("key");
		SysTree tree = sysTreeManager.getByKey(key, catKey, ContextUtil.getCurrentTenantId());
		return new JsonResult(true, "", tree);
	}

	/*
	 * private void genKmsType(String gridData,SysTree parentSysTree){ JSONArray
	 * jsonArray=JSONArray.fromObject(gridData); for (int i = 0; i <
	 * jsonArray.size(); i++) { JSONObject jsonObj = jsonArray.getJSONObject(i);
	 * Object treeId = jsonObj.get("treeId"); SysTree sysTree = null; boolean
	 * isCreated = false; if (treeId == null) { sysTree=new SysTree();
	 * sysTree.setTreeId(idGenerator.getSID()); isCreated=true; }else{
	 * sysTree=sysTreeManager.get(treeId.toString()); }
	 * sysTree.setName(JSONUtil.getString(jsonObj, "name"));
	 * if(parentSysTree==null){ sysTree.setParentId(null);
	 * sysTree.setPath(sysTree.getTreeId()+"."); sysTree.setDepth(0); } else{
	 * sysTree.setParentId(parentSysTree.getTreeId());
	 * sysTree.setPath(parentSysTree.getPath()+sysTree.getTreeId()+".");
	 * sysTree.setDepth(parentSysTree.getDepth()+1); }
	 * sysTree.setKey(JSONUtil.getString(jsonObj, "key"));
	 * sysTree.setCatKey("CAT_KMS"); sysTree.setSn(1); String children =
	 * JSONUtil.getString(jsonObj,"children");
	 * if(StringUtils.isNotEmpty(children)){ genKmsType(children,sysTree); }
	 * if(isCreated){ sysTreeManager.create(sysTree); }else{
	 * sysTreeManager.update(sysTree); } } }
	 */

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysTreeManager;
	}
	
	/**
	 * 在缺少树的时候提供创建树的方法
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("createTree/{paramKey}.do")
	@ResponseBody
	public JSONObject createFormSolutionTree(@PathVariable("paramKey")String paramKey,HttpServletResponse response,HttpServletRequest request){
		JSONObject jsonObject=new JSONObject();
		String tenantId=ContextUtil.getCurrentTenantId();
		String userId=ContextUtil.getCurrentUserId();
		String name=RequestUtil.getString(request, "name");
		String pingyin=StringUtil.getPingYin(name);
		SysTree sysTree=new SysTree();
		sysTree.setKey(pingyin);
		sysTree.setName(name);
		sysTree.setTreeId(IdUtil.getId());
		sysTree.setPath("0."+sysTree.getTreeId()+".");
		sysTree.setCreateTime(new Date());
		sysTree.setDepth(1);
		sysTree.setSn(1);
		sysTree.setCatKey(paramKey);
		sysTree.setCreateBy(userId);
		sysTree.setTenantId(tenantId);
		sysTreeManager.create(sysTree);
		jsonObject.put("success", true);
		return jsonObject;
		//SysTree.CAT_FORMSOLUTION
	}

}
