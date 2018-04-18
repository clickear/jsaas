package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrErrandsRegister;
import com.redxun.hr.core.manager.HrErrandsRegisterManager;
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
 * [HrErrandsRegister]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrErrandsRegister/")
public class HrErrandsRegisterFormController extends BaseFormController {

	@Resource
	private HrErrandsRegisterManager hrErrandsRegisterManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("hrErrandsRegister")
	public HrErrandsRegister processForm(HttpServletRequest request) {
		String erId = request.getParameter("erId");
		HrErrandsRegister hrErrandsRegister = null;
		if (StringUtils.isNotEmpty(erId)) {
			hrErrandsRegister = hrErrandsRegisterManager.get(erId);
		} else {
			hrErrandsRegister = new HrErrandsRegister();
		}

		return hrErrandsRegister;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param hrErrandsRegister
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request,
			@ModelAttribute("hrErrandsRegister") @Valid HrErrandsRegister hrErrandsRegister, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(hrErrandsRegister.getErId())) {
			hrErrandsRegister.setErId(idGenerator.getSID());
			hrErrandsRegisterManager.create(hrErrandsRegister);
			msg = getMessage("hrErrandsRegister.created", new Object[] { hrErrandsRegister.getIdentifyLabel() },
					"[HrErrandsRegister]成功创建!");
		} else {
			hrErrandsRegisterManager.update(hrErrandsRegister);
			msg = getMessage("hrErrandsRegister.updated", new Object[] { hrErrandsRegister.getIdentifyLabel() },
					"[HrErrandsRegister]成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}
}
