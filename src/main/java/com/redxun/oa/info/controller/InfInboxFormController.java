package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InfInbox;
import com.redxun.oa.info.manager.InfInboxManager;
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
 * 内部短消息收件箱管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/infInbox/")
public class InfInboxFormController extends BaseFormController {

	@Resource
	private InfInboxManager infInboxManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("infInbox")
	public InfInbox processForm(HttpServletRequest request) {
		String recId = request.getParameter("recId");
		InfInbox infInbox = null;
		if (StringUtils.isNotEmpty(recId)) {
			infInbox = infInboxManager.get(recId);
		} else {
			infInbox = new InfInbox();
		}

		return infInbox;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param infInbox
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request,
			@ModelAttribute("infInbox") @Valid InfInbox infInbox,
			BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(infInbox.getRecId())) {
			infInbox.setRecId(idGenerator.getSID());
			infInboxManager.create(infInbox);
			msg = getMessage("infInbox.created",
					new Object[] { infInbox.getIdentifyLabel() },
					"[InfInbox]成功创建!");
		} else {
			infInboxManager.update(infInbox);
			msg = getMessage("infInbox.updated",
					new Object[] { infInbox.getIdentifyLabel() },
					"[InfInbox]成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}
}
