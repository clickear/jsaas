package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrSectionConfig;
import com.redxun.hr.core.manager.HrSectionConfigManager;
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
 * [HrSectionConfig]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrSectionConfig/")
public class HrSectionConfigFormController extends BaseFormController {

	@Resource
	private HrSectionConfigManager hrSectionConfigManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("hrSectionConfig")
	public HrSectionConfig processForm(HttpServletRequest request) {
		String configId = request.getParameter("configId");
		HrSectionConfig hrSectionConfig = null;
		if (StringUtils.isNotEmpty(configId)) {
			hrSectionConfig = hrSectionConfigManager.get(configId);
		} else {
			hrSectionConfig = new HrSectionConfig();
		}

		return hrSectionConfig;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param hrSectionConfig
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request,
			@ModelAttribute("hrSectionConfig") @Valid HrSectionConfig hrSectionConfig, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(hrSectionConfig.getConfigId())) {
			hrSectionConfig.setConfigId(idGenerator.getSID());
			hrSectionConfigManager.create(hrSectionConfig);
			msg = getMessage("hrSectionConfig.created", new Object[] { hrSectionConfig.getIdentifyLabel() },
					"[HrSectionConfig]成功创建!");
		} else {
			hrSectionConfigManager.update(hrSectionConfig);
			msg = getMessage("hrSectionConfig.updated", new Object[] { hrSectionConfig.getIdentifyLabel() },
					"[HrSectionConfig]成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}
}
