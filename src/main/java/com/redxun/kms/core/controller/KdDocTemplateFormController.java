package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocTemplate;
import com.redxun.kms.core.manager.KdDocTemplateManager;
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
 * [KdDocTemplate]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocTemplate/")
public class KdDocTemplateFormController extends BaseFormController {

    @Resource
    private KdDocTemplateManager kdDocTemplateManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocTemplate")
    public KdDocTemplate processForm(HttpServletRequest request) {
        String tempId = request.getParameter("tempId");
        KdDocTemplate kdDocTemplate = null;
        if (StringUtils.isNotEmpty(tempId)) {
            kdDocTemplate = kdDocTemplateManager.get(tempId);
        } else {
            kdDocTemplate = new KdDocTemplate();
        }

        return kdDocTemplate;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocTemplate
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocTemplate") @Valid KdDocTemplate kdDocTemplate, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocTemplate.getTempId())) {
            kdDocTemplate.setTempId(idGenerator.getSID());
            kdDocTemplateManager.create(kdDocTemplate);
            msg = getMessage("kdDocTemplate.created", new Object[]{kdDocTemplate.getIdentifyLabel()}, "[KdDocTemplate]成功创建!");
        } else {
            kdDocTemplateManager.update(kdDocTemplate);
            msg = getMessage("kdDocTemplate.updated", new Object[]{kdDocTemplate.getIdentifyLabel()}, "[KdDocTemplate]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

