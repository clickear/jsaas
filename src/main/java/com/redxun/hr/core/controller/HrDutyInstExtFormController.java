package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrDutyInstExt;
import com.redxun.hr.core.manager.HrDutyInstExtManager;
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
 * [HrDutyInstExt]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutyInstExt/")
public class HrDutyInstExtFormController extends BaseFormController {

    @Resource
    private HrDutyInstExtManager hrDutyInstExtManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("hrDutyInstExt")
    public HrDutyInstExt processForm(HttpServletRequest request) {
        String extId = request.getParameter("extId");
        HrDutyInstExt hrDutyInstExt = null;
        if (StringUtils.isNotEmpty(extId)) {
            hrDutyInstExt = hrDutyInstExtManager.get(extId);
        } else {
            hrDutyInstExt = new HrDutyInstExt();
        }

        return hrDutyInstExt;
    }
    /**
     * 保存实体数据
     * @param request
     * @param hrDutyInstExt
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("hrDutyInstExt") @Valid HrDutyInstExt hrDutyInstExt, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(hrDutyInstExt.getExtId())) {
            hrDutyInstExt.setExtId(idGenerator.getSID());
            hrDutyInstExtManager.create(hrDutyInstExt);
            msg = getMessage("hrDutyInstExt.created", new Object[]{hrDutyInstExt.getIdentifyLabel()}, "[HrDutyInstExt]成功创建!");
        } else {
            hrDutyInstExtManager.update(hrDutyInstExt);
            msg = getMessage("hrDutyInstExt.updated", new Object[]{hrDutyInstExt.getIdentifyLabel()}, "[HrDutyInstExt]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

