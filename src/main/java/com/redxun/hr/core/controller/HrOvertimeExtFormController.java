package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrOvertimeExt;
import com.redxun.hr.core.manager.HrOvertimeExtManager;
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
 * [HrOvertimeExt]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrOvertimeExt/")
public class HrOvertimeExtFormController extends BaseFormController {

    @Resource
    private HrOvertimeExtManager hrOvertimeExtManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("hrOvertimeExt")
    public HrOvertimeExt processForm(HttpServletRequest request) {
        String otId = request.getParameter("otId");
        HrOvertimeExt hrOvertimeExt = null;
        if (StringUtils.isNotEmpty(otId)) {
            hrOvertimeExt = hrOvertimeExtManager.get(otId);
        } else {
            hrOvertimeExt = new HrOvertimeExt();
        }

        return hrOvertimeExt;
    }
    /**
     * 保存实体数据
     * @param request
     * @param hrOvertimeExt
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("hrOvertimeExt") @Valid HrOvertimeExt hrOvertimeExt, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(hrOvertimeExt.getOtId())) {
            hrOvertimeExt.setOtId(idGenerator.getSID());
            hrOvertimeExtManager.create(hrOvertimeExt);
            msg = getMessage("hrOvertimeExt.created", new Object[]{hrOvertimeExt.getIdentifyLabel()}, "[HrOvertimeExt]成功创建!");
        } else {
            hrOvertimeExtManager.update(hrOvertimeExt);
            msg = getMessage("hrOvertimeExt.updated", new Object[]{hrOvertimeExt.getIdentifyLabel()}, "[HrOvertimeExt]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

