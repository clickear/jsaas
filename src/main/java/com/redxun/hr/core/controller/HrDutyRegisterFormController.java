package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrDutyRegister;
import com.redxun.hr.core.manager.HrDutyRegisterManager;
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
 * [HrDutyRegister]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutyRegister/")
public class HrDutyRegisterFormController extends BaseFormController {

	@Resource
	private HrDutyRegisterManager hrDutyRegisterManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("hrDutyRegister")
	public HrDutyRegister processForm(HttpServletRequest request) {
		String registerId = request.getParameter("registerId");
		HrDutyRegister hrDutyRegister = null;
		if (StringUtils.isNotEmpty(registerId)) {
			hrDutyRegister = hrDutyRegisterManager.get(registerId);
		} else {
			hrDutyRegister = new HrDutyRegister();
		}

		return hrDutyRegister;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param hrDutyRegister
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request,
			@ModelAttribute("hrDutyRegister") @Valid HrDutyRegister hrDutyRegister, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(hrDutyRegister.getRegisterId())) {
			hrDutyRegister.setRegisterId(idGenerator.getSID());
			hrDutyRegisterManager.create(hrDutyRegister);
			msg = getMessage("hrDutyRegister.created", new Object[] { hrDutyRegister.getIdentifyLabel() },
					"[HrDutyRegister]成功创建!");
		} else {
			hrDutyRegisterManager.update(hrDutyRegister);
			msg = getMessage("hrDutyRegister.updated", new Object[] { hrDutyRegister.getIdentifyLabel() },
					"[HrDutyRegister]成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}
}
