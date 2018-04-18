package com.redxun.offdoc.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.offdoc.core.entity.OdDocFlow;
import com.redxun.offdoc.core.manager.OdDocFlowManager;
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
 * [OdDocFlow]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/offdoc/core/odDocFlow/")
public class OdDocFlowFormController extends BaseFormController {

    @Resource
    private OdDocFlowManager odDocFlowManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("odDocFlow")
    public OdDocFlow processForm(HttpServletRequest request) {
        String schemeId = request.getParameter("schemeId");
        OdDocFlow odDocFlow = null;
        if (StringUtils.isNotEmpty(schemeId)) {
            odDocFlow = odDocFlowManager.get(schemeId);
        } else {
            odDocFlow = new OdDocFlow();
        }

        return odDocFlow;
    }
    /**
     * 保存实体数据
     * @param request
     * @param odDocFlow
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("odDocFlow") @Valid OdDocFlow odDocFlow, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(odDocFlow.getSchemeId())) {
            odDocFlow.setSchemeId(idGenerator.getSID());
            odDocFlowManager.create(odDocFlow);
            msg = getMessage("odDocFlow.created", new Object[]{odDocFlow.getIdentifyLabel()}, "[OdDocFlow]成功创建!");
        } else {
            odDocFlowManager.update(odDocFlow);
            msg = getMessage("odDocFlow.updated", new Object[]{odDocFlow.getIdentifyLabel()}, "[OdDocFlow]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

