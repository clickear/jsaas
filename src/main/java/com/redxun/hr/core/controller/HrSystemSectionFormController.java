package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrSystemSection;
import com.redxun.hr.core.manager.HrSystemSectionManager;
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
 * [HrSystemSection]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrSystemSection/")
public class HrSystemSectionFormController extends BaseFormController {

    @Resource
    private HrSystemSectionManager hrSystemSectionManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("hrSystemSection")
    public HrSystemSection processForm(HttpServletRequest request) {
        String systemSectionId = request.getParameter("systemSectionId");
        HrSystemSection hrSystemSection = null;
        if (StringUtils.isNotEmpty(systemSectionId)) {
            hrSystemSection = hrSystemSectionManager.get(systemSectionId);
        } else {
            hrSystemSection = new HrSystemSection();
        }

        return hrSystemSection;
    }
    /**
     * 保存实体数据
     * @param request
     * @param hrSystemSection
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("hrSystemSection") @Valid HrSystemSection hrSystemSection, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(hrSystemSection.getSystemSectionId())) {
            hrSystemSection.setSystemSectionId(idGenerator.getSID());
            hrSystemSectionManager.create(hrSystemSection);
            msg = getMessage("hrSystemSection.created", new Object[]{hrSystemSection.getIdentifyLabel()}, "[HrSystemSection]成功创建!");
        } else {
            hrSystemSectionManager.update(hrSystemSection);
            msg = getMessage("hrSystemSection.updated", new Object[]{hrSystemSection.getIdentifyLabel()}, "[HrSystemSection]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

