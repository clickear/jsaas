package com.redxun.oa.info.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.oa.info.entity.InfInbox;
import com.redxun.oa.info.entity.InfInnerMsg;
import com.redxun.oa.info.entity.InsNews;
import com.redxun.oa.info.manager.InfInboxManager;
import com.redxun.oa.info.manager.InfInnerMsgManager;

/**
 * 内部短消息管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/infInnerMsg/")
public class InfInnerMsgController extends BaseListController {
	@Resource
	InfInnerMsgManager infInnerMsgManager;
	@Resource
	private OsDimensionManager osDimensionManager;
	@Resource
	InfInboxManager infInboxManager;

	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				infInnerMsgManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}
	
	/**
	 * 获得新消息的数量,index页面的新消息显示
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("count")
	@ResponseBody
	public JsonResult count(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String recId = ContextUtil.getCurrentUserId();
    	int newMsgCount = infInnerMsgManager.getNewMsgCountByRecId(recId);
		return new JsonResult(true,"", newMsgCount);
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
	public ModelAndView get(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		InfInnerMsg infInnerMsg = null;
		if (StringUtils.isNotEmpty(pkId)) {
			infInnerMsg = infInnerMsgManager.get(pkId);
		} else {
			infInnerMsg = new InfInnerMsg();
		}
		return getPathView(request).addObject("infInnerMsg", infInnerMsg);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		InfInnerMsg infInnerMsg = null;
		if (StringUtils.isNotEmpty(pkId)) {
			infInnerMsg = infInnerMsgManager.get(pkId);
			if ("true".equals(forCopy)) {
				infInnerMsg.setMsgId(null);
			}
		} else {
			infInnerMsg = new InfInnerMsg();
		}
		return getPathView(request).addObject("infInnerMsg", infInnerMsg);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return infInnerMsgManager;
	}

}
