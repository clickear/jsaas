package com.redxun.kms.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.kms.core.entity.KdUser;
import com.redxun.kms.core.entity.KdUserLevel;
import com.redxun.kms.core.manager.KdUserLevelManager;
import com.redxun.kms.core.manager.KdUserManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * [KdUser]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdUser/")
public class KdUserController extends TenantListController {
	@Resource
	KdUserManager kdUserManager;

	@Resource
	SysTreeManager sysTreeManager;

	@Resource
	OsUserManager osUserManager;

	@Resource
	KdUserLevelManager kdUserLevelManager;

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				kdUserManager.delete(id);
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
		KdUser kdUser = null;
		if (StringUtils.isNotEmpty(pkId)) {
			kdUser = kdUserManager.get(pkId);
		} else {
			kdUser = new KdUser();
		}
		return getPathView(request).addObject("kdUser", kdUser);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		KdUser kdUser = null;
		if (StringUtils.isNotEmpty(pkId)) {
			kdUser = kdUserManager.get(pkId);
			if ("true".equals(forCopy)) {
				kdUser.setKuserId(null);
			}
		} else {
			kdUser = new KdUser();
		}
		return getPathView(request).addObject("kdUser", kdUser);
	}

	/**
	 * 在打开知识门户时，如果用户未关联账号时的注册页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("connect")
	public ModelAndView connect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		String userId = ContextUtil.getCurrentUserId();
		OsUser user = osUserManager.get(userId);
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		KdUser kdUser = null;
		if (StringUtils.isNotEmpty(pkId)) {
			kdUser = kdUserManager.get(pkId);
			if ("true".equals(forCopy)) {
				kdUser.setKuserId(null);
			}
		} else {
			kdUser = new KdUser();
		}
		return getPathView(request).addObject("kdUser", kdUser).addObject("user", user);
	}

	@RequestMapping("personalEdit")
	public ModelAndView personalEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		KdUser kdUser = kdUserManager.getByUserId(userId);
		return getPathView(request).addObject("kdUser", kdUser);
	}

	@RequestMapping("personalGet")
	public ModelAndView personalGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		KdUser kdUser = kdUserManager.getByUserId(userId);
		return getPathView(request).addObject("kdUser", kdUser).addObject("userId", userId);
	}

	@RequestMapping("saveKdUser")
	@ResponseBody
	public JsonResult saveKdUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("kuserId");
		String formData = request.getParameter("formData");
		String knSysId = request.getParameter("knTreeId");
		String reqSysId = request.getParameter("reqTreeId");
		KdUser kdUser = (KdUser) JSONUtil.json2Bean(formData, KdUser.class);
		KdUser o_KdUser = kdUserManager.get(userId);
		if (StringUtils.isNotEmpty(reqSysId))
			o_KdUser.setReqSysTree(sysTreeManager.get(reqSysId));
		else
			o_KdUser.setReqSysTree(null);

		if (StringUtils.isNotEmpty(knSysId))
			o_KdUser.setKnSysTree(sysTreeManager.get(knSysId));
		else
			o_KdUser.setReqSysTree(null);

		o_KdUser.setFullname(kdUser.getFullname());
		o_KdUser.setSex(kdUser.getSex());
		o_KdUser.setSign(kdUser.getSign());
		o_KdUser.setProfile(kdUser.getProfile());
		o_KdUser.setHeadId(kdUser.getHeadId());
		o_KdUser.setOfficePhone(kdUser.getOfficePhone());
		o_KdUser.setMobile(kdUser.getMobile());
		o_KdUser.setEmail(kdUser.getEmail());
		kdUserManager.update(o_KdUser);
		return new JsonResult(true, "保存成功！");
	}

	@RequestMapping("checkKdUser")
	@ResponseBody
	public JsonResult checkKdUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("uId");
		boolean isExist = kdUserManager.checkKdUser(uId);
		if (!isExist)
			return new JsonResult(true, "该用户没有被绑定！", "false");
		else
			return new JsonResult(true, "该用户已经绑定了其他用户！", "true");
	}

	@RequestMapping("saveKdUserMgr")
	@ResponseBody
	public JsonResult saveKdUserMgr(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String formData = request.getParameter("formData");
		String knSysId = request.getParameter("knTreeId");
		String reqSysId = request.getParameter("reqTreeId");
		String uId = request.getParameter("uId");
		KdUser kdUser = (KdUser) JSONUtil.json2Bean(formData, KdUser.class);
		if (StringUtils.isEmpty(kdUser.getKuserId())) {
			kdUser.setKuserId(idGenerator.getSID());
			if (StringUtils.isNotEmpty(reqSysId))
				kdUser.setReqSysTree(sysTreeManager.get(reqSysId));
			else
				kdUser.setReqSysTree(null);

			if (StringUtils.isNotEmpty(knSysId))
				kdUser.setKnSysTree(sysTreeManager.get(knSysId));
			else
				kdUser.setReqSysTree(null);
			kdUser.setUser(osUserManager.get(uId));
			kdUser.setSn(1);
			kdUser.setPoint(0);
			kdUser.setDocScore(0);
			KdUserLevel kdUserLevel = kdUserLevelManager.getByPoint(0,ContextUtil.getCurrentTenantId());
			if (kdUserLevel != null) {
				kdUser.setGrade(kdUserLevel.getLevelName());
			}else{
				kdUser.setGrade("无等级");
			}
			kdUserManager.create(kdUser);
			return new JsonResult(true, "专家用户创建成功！");
		} else {
			KdUser o_kdUser = kdUserManager.get(kdUser.getKuserId());
			BeanUtil.copyProperties(o_kdUser, kdUser);
			o_kdUser.setKnSysTree(sysTreeManager.get(knSysId));
			o_kdUser.setReqSysTree(sysTreeManager.get(reqSysId));
			o_kdUser.setUser(osUserManager.get(uId));
			kdUserManager.update(o_kdUser);
			return new JsonResult(true, "专家用户编辑成功！");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return kdUserManager;
	}

}
