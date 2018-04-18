package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrHoliday;
import com.redxun.hr.core.manager.HrHolidayManager;
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
 * [HrHoliday]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrHoliday/")
public class HrHolidayFormController extends BaseFormController {

	@Resource
	private HrHolidayManager hrHolidayManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("hrHoliday")
	public HrHoliday processForm(HttpServletRequest request) {
		String holidayId = request.getParameter("holidayId");
		HrHoliday hrHoliday = null;
		if (StringUtils.isNotEmpty(holidayId)) {
			hrHoliday = hrHolidayManager.get(holidayId);
		} else {
			hrHoliday = new HrHoliday();
		}

		return hrHoliday;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param hrHoliday
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("hrHoliday") @Valid HrHoliday hrHoliday,
			BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(hrHoliday.getHolidayId())) {
			hrHoliday.setHolidayId(idGenerator.getSID());
			hrHolidayManager.create(hrHoliday);
			msg = getMessage("hrHoliday.created", new Object[] { hrHoliday.getIdentifyLabel() }, "[HrHoliday]成功创建!");
		} else {
			hrHolidayManager.update(hrHoliday);
			msg = getMessage("hrHoliday.updated", new Object[] { hrHoliday.getIdentifyLabel() }, "[HrHoliday]成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}
}
