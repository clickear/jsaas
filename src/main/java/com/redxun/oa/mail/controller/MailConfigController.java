package com.redxun.oa.mail.controller;

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

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.mail.entity.MailConfig;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.oa.mail.manager.MailConfigManager;
import com.redxun.oa.mail.manager.MailFolderManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 外部邮件账号配置Controller
 * 
 * @author zwj 
 * 用途：处理对内部邮件相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/mail/mailConfig/")
public class MailConfigController extends TenantListController {

	@Resource
	OsUserManager osUserManager;

	@Resource
	MailConfigManager mailConfigManager;
	
	@Resource
	MailFolderManager mailFolderManager;

	/**
	 * 根据主键删除外部邮箱账号配置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		String delTabs=request.getParameter("delTabs");
		List<MailFolder> mailFolders=new ArrayList<MailFolder>();
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				if(MBoolean.YES.name().equals(delTabs)){
					mailFolders=mailFolderManager.getOutMailFolderByConfigId(id);
				}
				mailConfigManager.delete(id);
			}
		}
		if(mailFolders.size()>0)
			return new JsonResult(true, "成功删除！",mailFolders);
		else
			return new JsonResult(true, "成功删除！");
	}

	/**
	 * 根据主键查看外部邮件账号配置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		MailConfig mailConfig = null;
		if (StringUtils.isNotEmpty(pkId)) {
			mailConfig = mailConfigManager.get(pkId);
		} else {
			mailConfig = new MailConfig();
		}
		return getPathView(request).addObject("mailConfig", mailConfig);
	}

	/**
	 * 获取当前用户所有外部邮件账号配置（生成outlook-tree的账号配置树）
	 * 
	 * @param request
	 * @throws Exception
	 */

	@RequestMapping("getAllConfig")
	public ModelAndView getAllConfig(HttpServletRequest request) throws Exception {
		List<MailConfig> mailConfigs = mailConfigManager.getAllByUserId(ContextUtil.getCurrentUserId()); // 获取当前用户的所有外部邮箱账号配置
		String defaultConfig = "";
		if (!mailConfigs.isEmpty()) { // 如果当前用户有外部邮箱账号配置的话
			for (int i = 0; i < mailConfigs.size(); i++) {
				MailConfig mailConfig = mailConfigs.get(i);
				if (MBoolean.YES.name().equals(mailConfigs.get(i).getIsDefault())) {
					defaultConfig = mailConfig.getAccount();
					// System.out.println(defaultConfig);
				}
				mailConfig.setAccount(mailConfig.getAccount() + "(" + mailConfig.getMailAccount() + ")"); // 设置收邮件账号列表
			}
			if (StringUtils.isNotEmpty(defaultConfig))
				request.setAttribute("defaultConfig", defaultConfig);
			request.setAttribute("allConfigs", mailConfigs);
			return new ModelAndView("/oa/mail/mailConfigList.jsp");
		} else
			// 如果用户没有外部邮箱配置
			return new ModelAndView("/oa/mail/noMailConfig.jsp");
	}

	/**
	 * 编辑外部邮箱账号配置
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
		MailConfig mailConfig = null;
		if (StringUtils.isNotEmpty(pkId)) {
			mailConfig = mailConfigManager.get(pkId);
			if ("true".equals(forCopy)) {
				mailConfig.setConfigId(null);
			}
		} else {
			mailConfig = new MailConfig();
			mailConfig.setUserId(ContextUtil.getCurrentUserId());
			mailConfig.setUserName(osUserManager.get(mailConfig.getUserId()).getFullname());     //设置为当前用户名
		}
		return getPathView(request).addObject("mailConfig", mailConfig);
	}

	/**
	 * 获取当前用户所有外部邮箱账号列表（用于List页面的grid展示）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllMailConfig")
	@ResponseBody
	public JsonPageResult<MailConfig> getAllMailConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("userId", ContextUtil.getCurrentUserId());
		List<MailConfig> mailConfigs = mailConfigManager.getAll(queryFilter);    //获取当前用户的所有外部邮箱账号配置
		JsonPageResult<MailConfig> result = new JsonPageResult<MailConfig>(mailConfigs, queryFilter.getPage().getTotalItems());
		return result;
	}
	
	/**
	 * 刷新收邮件账号列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("refreshAccountList")
	@ResponseBody
	public List<MailConfig> refreshAccountList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<MailConfig> mailConfigs = mailConfigManager.getAllByUserId(ContextUtil.getCurrentUserId());
		for (int i = 0; i < mailConfigs.size(); i++) {
			MailConfig mailConfig = mailConfigs.get(i);
			mailConfig.setAccount(mailConfig.getAccount() + "(" + mailConfig.getMailAccount() + ")"); // 设置收邮件账号列表
		}
		return mailConfigs;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return mailConfigManager;
	}

}
