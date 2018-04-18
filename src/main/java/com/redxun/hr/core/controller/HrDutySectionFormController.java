package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrDutySection;
import com.redxun.hr.core.manager.HrDutySectionManager;
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
 * [HrDutySection]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutySection/")
public class HrDutySectionFormController extends BaseFormController {

	@Resource
	private HrDutySectionManager hrDutySectionManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("hrDutySection")
	public HrDutySection processForm(HttpServletRequest request) {
		String sectionId = request.getParameter("sectionId");
		HrDutySection hrDutySection = null;
		if (StringUtils.isNotEmpty(sectionId)) {
			hrDutySection = hrDutySectionManager.get(sectionId);
		} else {
			hrDutySection = new HrDutySection();
		}

		return hrDutySection;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param hrDutySection
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request,
			@ModelAttribute("hrDutySection") @Valid HrDutySection hrDutySection, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(hrDutySection.getSectionId())) {
			hrDutySection.setSectionId(idGenerator.getSID());
			hrDutySectionManager.create(hrDutySection);
			msg = getMessage("hrDutySection.created", new Object[] { hrDutySection.getIdentifyLabel() },
					"班次成功创建!");
		} else {
			hrDutySectionManager.update(hrDutySection);
			msg = getMessage("hrDutySection.updated", new Object[] { hrDutySection.getIdentifyLabel() },
					"班次成功更新!");
		}
		
		
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}
}
