package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrVacationExt;
import com.redxun.hr.core.manager.HrVacationExtManager;
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
 * [HrVacationExt]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrVacationExt/")
public class HrVacationExtFormController extends BaseFormController {

    @Resource
    private HrVacationExtManager hrVacationExtManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("hrVacationExt")
    public HrVacationExt processForm(HttpServletRequest request) {
        String vacId = request.getParameter("vacId");
        HrVacationExt hrVacationExt = null;
        if (StringUtils.isNotEmpty(vacId)) {
            hrVacationExt = hrVacationExtManager.get(vacId);
        } else {
            hrVacationExt = new HrVacationExt();
        }

        return hrVacationExt;
    }
    /**
     * 保存实体数据
     * @param request
     * @param hrVacationExt
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("hrVacationExt") @Valid HrVacationExt hrVacationExt, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(hrVacationExt.getVacId())) {
            hrVacationExt.setVacId(idGenerator.getSID());
            hrVacationExtManager.create(hrVacationExt);
            msg = getMessage("hrVacationExt.created", new Object[]{hrVacationExt.getIdentifyLabel()}, "[HrVacationExt]成功创建!");
        } else {
            hrVacationExtManager.update(hrVacationExt);
            msg = getMessage("hrVacationExt.updated", new Object[]{hrVacationExt.getIdentifyLabel()}, "[HrVacationExt]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

