package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.hr.core.entity.HrTransRestExt;
import com.redxun.hr.core.manager.HrTransRestExtManager;
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
 * [HrTransRestExt]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrTransRestExt/")
public class HrTransRestExtFormController extends BaseFormController {

    @Resource
    private HrTransRestExtManager hrTransRestExtManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("hrTransRestExt")
    public HrTransRestExt processForm(HttpServletRequest request) {
        String trId = request.getParameter("trId");
        HrTransRestExt hrTransRestExt = null;
        if (StringUtils.isNotEmpty(trId)) {
            hrTransRestExt = hrTransRestExtManager.get(trId);
        } else {
            hrTransRestExt = new HrTransRestExt();
        }

        return hrTransRestExt;
    }
    /**
     * 保存实体数据
     * @param request
     * @param hrTransRestExt
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("hrTransRestExt") @Valid HrTransRestExt hrTransRestExt, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(hrTransRestExt.getTrId())) {
            hrTransRestExt.setTrId(idGenerator.getSID());
            hrTransRestExtManager.create(hrTransRestExt);
            msg = getMessage("hrTransRestExt.created", new Object[]{hrTransRestExt.getIdentifyLabel()}, "[HrTransRestExt]成功创建!");
        } else {
            hrTransRestExtManager.update(hrTransRestExt);
            msg = getMessage("hrTransRestExt.updated", new Object[]{hrTransRestExt.getIdentifyLabel()}, "[HrTransRestExt]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

