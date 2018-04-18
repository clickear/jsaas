package com.redxun.offdoc.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTreeCatManager;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.offdoc.core.entity.OdDocTemplate;
import com.redxun.offdoc.core.manager.OdDocTemplateManager;

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
 * [OdDocTemplate]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/offdoc/core/odDocTemplate/")
public class OdDocTemplateFormController extends BaseFormController {

    @Resource
    private OdDocTemplateManager odDocTemplateManager;
    @Resource
    private SysTreeManager sysTreeManager;
    @Resource
    private SysTreeCatManager sysTreeCatManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("odDocTemplate")
    public OdDocTemplate processForm(HttpServletRequest request) {
        String tempId = request.getParameter("tempId");
        OdDocTemplate odDocTemplate = null;
        if (StringUtils.isNotEmpty(tempId)) {
            odDocTemplate = odDocTemplateManager.get(tempId);
        } else {
            odDocTemplate = new OdDocTemplate();
        }

        return odDocTemplate;
    }
    /**
     * 保存实体数据
     * @param request
     * @param odDocTemplate
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("odDocTemplate") @Valid OdDocTemplate odDocTemplate, BindingResult result) {
        String tenantId=ContextUtil.getCurrentTenantId();
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(odDocTemplate.getTempId())) {
            odDocTemplate.setTempId(idGenerator.getSID());try {
            	odDocTemplate.setTempType(sysTreeCatManager.getByKey(sysTreeManager.get(odDocTemplate.getTreeId()).getCatKey(), tenantId).getName());
			} catch (Exception e) {
				return 	new JsonResult(false,"请新建分类");
			}
            
            odDocTemplateManager.create(odDocTemplate);
            msg = getMessage("odDocTemplate.created", new Object[]{odDocTemplate.getName()}, "公文模板成功创建!");
        } else {
            odDocTemplateManager.update(odDocTemplate);
            msg = getMessage("odDocTemplate.updated", new Object[]{odDocTemplate.getName()}, "公文模板成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

