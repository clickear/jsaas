package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrDutyInst;
import com.redxun.hr.core.manager.HrDutyInstManager;
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
 * [HrDutyInst]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutyInst/")
public class HrDutyInstFormController extends BaseFormController {

    @Resource
    private HrDutyInstManager hrDutyInstManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("hrDutyInst")
    public HrDutyInst processForm(HttpServletRequest request) {
        String dutyInstId = request.getParameter("dutyInstId");
        HrDutyInst hrDutyInst = null;
        if (StringUtils.isNotEmpty(dutyInstId)) {
            hrDutyInst = hrDutyInstManager.get(dutyInstId);
        } else {
            hrDutyInst = new HrDutyInst();
        }

        return hrDutyInst;
    }
    /**
     * 保存实体数据
     * @param request
     * @param hrDutyInst
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("hrDutyInst") @Valid HrDutyInst hrDutyInst, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(hrDutyInst.getDutyInstId())) {
            hrDutyInst.setDutyInstId(idGenerator.getSID());
            hrDutyInstManager.create(hrDutyInst);
            msg = getMessage("hrDutyInst.created", new Object[]{hrDutyInst.getIdentifyLabel()}, "[HrDutyInst]成功创建!");
        } else {
            hrDutyInstManager.update(hrDutyInst);
            msg = getMessage("hrDutyInst.updated", new Object[]{hrDutyInst.getIdentifyLabel()}, "[HrDutyInst]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

