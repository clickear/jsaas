package com.redxun.oa.company.controller;

import java.util.HashMap;
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

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.kms.core.entity.KdDoc;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.oa.company.entity.OaComBook;
import com.redxun.oa.company.entity.OaComRight;
import com.redxun.oa.company.manager.OaComBookManager;
import com.redxun.oa.company.manager.OaComRightManager;
import com.redxun.oa.doc.entity.DocRight;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;



/**
 * [OaComBook]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/company/oaComBook/")
public class OaComBookController extends BaseListController {
	@Resource
	OaComBookManager oaComBookManager;
	@Resource
	SysTreeManager sysTreeManager;
	@Resource
	OsGroupManager osGroupManager;
	@Resource
	OsUserManager osUserManager;
	@Resource
	OaComRightManager oaComRightManager;

	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				oaComBookManager.delete(id);
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
		OaComBook oaComBook = null;
		if (StringUtils.isNotEmpty(pkId)) {
			oaComBook = oaComBookManager.get(pkId);
		} else {
			oaComBook = new OaComBook();
		}
		return getPathView(request).addObject("oaComBook", oaComBook);
	}

	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String catId = request.getParameter("catId");
		OaComBook oaComBook = null;
		String treeName = null;
		if (StringUtils.isNotEmpty(pkId)) {
			oaComBook = oaComBookManager.get(pkId);
		} else {
			oaComBook = new OaComBook();
			treeName = sysTreeManager.get(catId).getName();
		}
		return getPathView(request).addObject("oaComBook", oaComBook).addObject("treeName", treeName).addObject("catId", catId);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		OaComBook oaComBook = null;
		String treeName = null;
		String groupId = null;
		String catId = null;
		if (StringUtils.isNotEmpty(pkId)) {
			oaComBook = oaComBookManager.get(pkId);
			catId = oaComBook.getComgroupId();
			treeName = sysTreeManager.get(catId).getName();
			if (null != oaComBook.getOsGroup()) {
				groupId = oaComBook.getOsGroup().getGroupId();
			}
		} else {
			oaComBook = new OaComBook();
			treeName = sysTreeManager.get(catId).getName();
		}
		return getPathView(request).addObject("oaComBook", oaComBook).addObject("treeName", treeName).addObject("catId", catId).addObject("groupId", groupId);
	}
	
	/**
	 * 权限的页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("right")
	public ModelAndView right(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");//通讯录ID
		ModelAndView mv = getPathView(request);
		OaComBook oaComBook = null;
		String treeName = null;
		String groupId = null;
		String catId = null;
		StringBuffer readName = new StringBuffer();// 用于权限的显示
		StringBuffer readId = new StringBuffer();// 用于权限的Id
		if (StringUtils.isNotEmpty(pkId)) {
			oaComBook = oaComBookManager.get(pkId);
			catId = oaComBook.getComgroupId();
			treeName = sysTreeManager.get(catId).getName();
			if (null != oaComBook.getOsGroup()) {
				groupId = oaComBook.getOsGroup().getGroupId();
			}
			
			// 拼接权限的回显
			// 取出所有个人阅读权限
			List<OaComRight> readUserRights = oaComRightManager.getAllByBookIdRight(pkId, OaComRight.IDENTITYTYPE_USER);
			for (OaComRight readUserRight : readUserRights) {
				readId.append(readUserRight.getIdentityId() + "_user");
				readId.append(",");
				readName.append(osUserManager.get(readUserRight.getIdentityId()).getFullname());
				readName.append(",");
			}
			// 取出所有组阅读权限
			List<OaComRight> readGroupRights = oaComRightManager.getAllByBookIdRight(pkId, OaComRight.IDENTITYTYPE_GROUP);
			for (OaComRight readGroupRight : readGroupRights) {
				readId.append(readGroupRight.getIdentityId() + "_group");
				readId.append(",");
				readName.append(osGroupManager.get(readGroupRight.getIdentityId()).getName());
				readName.append(",");
			}
			// 对阅读权限进行处理然后传去页面输出
			if (readId.length() > 0) {
				readName.deleteCharAt(readName.length() - 1);
				readId.deleteCharAt(readId.length() - 1);
			}
			mv.addObject("readName", readName.toString()).addObject("readId", readId.toString());
		}
		return mv.addObject("oaComBook", oaComBook).addObject("treeName", treeName).addObject("catId", catId).addObject("groupId", groupId);
	}

	@RequestMapping("multiAdd")
	public ModelAndView multiAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("catId");
		String treeName = sysTreeManager.get(catId).getName();
		return getPathView(request).addObject("treeName", treeName).addObject("catId", catId);
	}

	@RequestMapping("getMainDep")
	@ResponseBody
	public JsonResult getMainDep(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		OsUser user = osUserManager.get(userId);
		String mainDep = osGroupManager.getMainDeps(userId).getName();
		String depId = osGroupManager.getMainDeps(userId).getGroupId();
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("mainDep", mainDep);
		userMap.put("depId", depId);
		userMap.put("user", user);
		return new JsonResult(true,"", userMap);
	}

	@RequestMapping("getByPath")
	@ResponseBody
	public JsonPageResult<OaComBook> getByPath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catId = request.getParameter("catId");
		String userId = ContextUtil.getCurrentUserId();
		QueryFilter qf = QueryFilterBuilder.createQueryFilter(request);
		List<OaComBook> list = oaComBookManager.getAllByRgiht(userId, catId, qf);
		JsonPageResult<OaComBook> result = new JsonPageResult<OaComBook>(list, qf.getPage().getTotalItems());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaComBookManager;
	}

}
