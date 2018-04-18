package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrDutySystem;
import com.redxun.hr.core.manager.HrDutySystemManager;
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
 * [HrDutySystem]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutySystem/")
public class HrDutySystemFormController extends BaseFormController {

	@Resource
	private HrDutySystemManager hrDutySystemManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("hrDutySystem")
	public HrDutySystem processForm(HttpServletRequest request) {
		String systemId = request.getParameter("systemId");
		HrDutySystem hrDutySystem = null;
		if (StringUtils.isNotEmpty(systemId)) {
			hrDutySystem = hrDutySystemManager.get(systemId);
		} else {
			hrDutySystem = new HrDutySystem();
		}

		return hrDutySystem;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param hrDutySystem
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("hrDutySystem") @Valid HrDutySystem hrDutySystem,
			BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(hrDutySystem.getSystemId())) {
			hrDutySystem.setSystemId(idGenerator.getSID());
			hrDutySystemManager.create(hrDutySystem);
			msg = getMessage("hrDutySystem.created", new Object[] { hrDutySystem.getIdentifyLabel() },
					"班制成功创建!");
		} else {
			hrDutySystemManager.update(hrDutySystem);
			msg = getMessage("hrDutySystem.updated", new Object[] { hrDutySystem.getIdentifyLabel() },
					"班制成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}
}
