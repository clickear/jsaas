package com.redxun.oa.mail.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.mail.entity.OutMail;
import com.redxun.oa.mail.manager.OutMailManager;
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
 * 外部邮件Controller
 * 
 * @author zwj 
 * 用途：处理对外部邮件相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/mail/outMail/")
public class OutMailFormController extends BaseFormController {

	@Resource
	private OutMailManager outMailManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("mail")
	public OutMail processForm(HttpServletRequest request) {
		String mailId = request.getParameter("mailId");
		OutMail mail = null;
		if (StringUtils.isNotEmpty(mailId)) {
			mail = outMailManager.get(mailId);
		} else {
			mail = new OutMail();
		}

		return mail;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param mail
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("mail") @Valid OutMail mail, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(mail.getMailId())) {
			mail.setMailId(idGenerator.getSID());
			outMailManager.create(mail);
			msg = getMessage("mail.created", new Object[] { mail.getIdentifyLabel() }, "外部邮件成功创建!");
		} else {
			outMailManager.update(mail);
			msg = getMessage("mail.updated", new Object[] { mail.getIdentifyLabel() }, "外部邮件成功更新!");
		}
		return new JsonResult(true, msg);
	}
}
