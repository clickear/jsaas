package com.redxun.oa.mail.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.mail.entity.MailBox;
import com.redxun.oa.mail.manager.MailBoxManager;
import com.redxun.saweb.controller.TenantListController;

/**
 * 内部邮件收件箱Controller
 * 
 * @author zwj 
 * 用途：处理对内部邮件收件箱相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/mail/mailBox/")
public class MailBoxController extends TenantListController {
	@Resource
	MailBoxManager mailBoxManager;

	/**
	 * 根据内部邮件Id和文件夹Id删除内部邮件收件箱记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String folderId = request.getParameter("folderId"); // 获取文件夹Id
		String uId = request.getParameter("ids"); // 邮件Id列表
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				MailBox mailBox = mailBoxManager.getMailBoxByInnerMailIdAndFolderId(id, folderId); // 根据邮件Id和文件夹Id获取内部邮件收件箱实体
				mailBoxManager.delete(mailBox.getBoxId());
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
		MailBox mailBox = null;
		if (StringUtils.isNotEmpty(pkId)) {
			mailBox = mailBoxManager.get(pkId);
		} else {
			mailBox = new MailBox();
		}
		return getPathView(request).addObject("mailBox", mailBox);
	}

	/**
	 * 根据主键获取内部邮件收件箱记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		MailBox mailBox = null;
		if (StringUtils.isNotEmpty(pkId)) {
			mailBox = mailBoxManager.get(pkId);
			if ("true".equals(forCopy)) {
				mailBox.setBoxId(null);
			}
		} else {
			mailBox = new MailBox();
		}
		return getPathView(request).addObject("mailBox", mailBox);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return mailBoxManager;
	}

}
