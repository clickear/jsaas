package com.redxun.oa.mail.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JsonResult;
import com.redxun.oa.mail.entity.MailFolder;
import com.redxun.oa.mail.manager.MailConfigManager;
import com.redxun.oa.mail.manager.MailFolderManager;
import com.redxun.saweb.controller.BaseFormController;

/**
 * 邮件文件夹Controller
 * 
 * @author zwj 
 * 用途：处理对邮件文件夹相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/mail/mailFolder/")
public class MailFolderFormController extends BaseFormController {

	@Resource
	private MailConfigManager mailConfigManager;

	@Resource
	private MailFolderManager mailFolderManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("mailFolder")
	public MailFolder processForm(HttpServletRequest request) {
		String folderId = request.getParameter("folderId");

		MailFolder mailFolder = null;
		if (StringUtils.isNotEmpty(folderId)) {
			mailFolder = mailFolderManager.get(folderId);
		} else {
			mailFolder = new MailFolder();
		}

		return mailFolder;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param mailFolder
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("mailFolder") @Valid MailFolder mailFolder, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(mailFolder.getFolderId())) {
			mailFolder.setFolderId(idGenerator.getSID());
			mailFolder.setPath(mailFolderManager.get(mailFolder.getParentId()).getPath() + mailFolder.getFolderId() + ".");
			mailFolderManager.create(mailFolder);
			msg = getMessage("mailFolder.created", new Object[] { mailFolder.getIdentifyLabel() }, "文件夹成功创建!");
		} else {
			mailFolderManager.update(mailFolder);
			msg = getMessage("mailFolder.updated", new Object[] { mailFolder.getIdentifyLabel() }, "文件夹成功更新!");
		}
		return new JsonResult(true, msg);
	}
}
