package com.redxun.oa.mail.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.mail.entity.InnerMail;
import com.redxun.oa.mail.manager.InnerMailManager;
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

/**
 * 内部邮件FormController
 * 
 * @author zwj 
 * 用途：处理对内部邮件相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/mail/innerMail/")
public class InnerMailFormController extends BaseFormController {

	@Resource
	private InnerMailManager innerMailManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("innerMail")
	public InnerMail processForm(HttpServletRequest request) {
		String mailId = request.getParameter("mailId");
		InnerMail innerMail = null;
		if (StringUtils.isNotEmpty(mailId)) {
			innerMail = innerMailManager.get(mailId);
		} else {
			innerMail = new InnerMail();
		}

		return innerMail;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param innerMail
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("innerMail") @Valid InnerMail innerMail, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(innerMail.getMailId())) {
			innerMail.setMailId(idGenerator.getSID());
			innerMailManager.create(innerMail);
			msg = getMessage("innerMail.created", new Object[] { innerMail.getIdentifyLabel() }, "内部邮件成功创建!");
		} else {
			innerMailManager.update(innerMail);
			msg = getMessage("innerMail.updated", new Object[] { innerMail.getIdentifyLabel() }, "内部邮件成功更新!");
		}
		return new JsonResult(true, msg);
	}
}
