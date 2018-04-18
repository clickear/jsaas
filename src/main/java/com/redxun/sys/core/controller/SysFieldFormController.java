package com.redxun.sys.core.controller;

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

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysField;
import com.redxun.sys.core.manager.SysFieldManager;
import com.redxun.sys.core.manager.SysModuleManager;

/**
 * 系统模块字段管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysField/")
public class SysFieldFormController extends BaseFormController {

    @Resource
    private SysFieldManager sysFieldManager;
    
    @Resource
    private SysModuleManager sysModuleManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysField")
    public SysField processForm(HttpServletRequest request) {
        String fieldId = request.getParameter("fieldId");
        SysField sysField = null;
        if (StringUtils.isNotEmpty(fieldId)) {
        	sysField = sysFieldManager.get(fieldId);
        } else {
        	sysField = new SysField();
        }

        return sysField;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysField
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysField") @Valid SysField sysField, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        String linkModId=request.getParameter("linkModId");
        sysField.setLinkSysModule(sysModuleManager.get(linkModId));
        if (StringUtils.isEmpty(sysField.getFieldId())) {
        	sysField.setModuleId(idGenerator.getSID());
            sysFieldManager.create(sysField);
            msg = getMessage("sysField.created", new Object[]{sysField.getTitle()}, "系统模块字段成功创建!");
        } else {
        	sysField.setLinkSysModule(sysModuleManager.get(linkModId));
            sysFieldManager.update(sysField);
            msg = getMessage("sysField.updated", new Object[]{sysField.getTitle()}, "系统模块字段成功更新!");
        }
        return new JsonResult(true, msg);
    }
}


