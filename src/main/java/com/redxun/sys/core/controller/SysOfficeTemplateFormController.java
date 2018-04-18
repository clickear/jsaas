
package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysOfficeTemplate;
import com.redxun.sys.core.manager.SysOfficeTemplateManager;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.redxun.saweb.util.IdUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.redxun.sys.log.LogEnt;

/**
 * office模板 管理
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysOfficeTemplate/")
public class SysOfficeTemplateFormController extends BaseFormController {

    @Resource
    private SysOfficeTemplateManager sysOfficeTemplateManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysOfficeTemplate")
    public SysOfficeTemplate processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysOfficeTemplate sysOfficeTemplate = null;
        if (StringUtils.isNotEmpty(id)) {
            sysOfficeTemplate = sysOfficeTemplateManager.get(id);
        } else {
            sysOfficeTemplate = new SysOfficeTemplate();
        }

        return sysOfficeTemplate;
    }
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "sys", submodule = "office模板")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysOfficeTemplate") @Valid SysOfficeTemplate sysOfficeTemplate, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysOfficeTemplate.getId())) {
            sysOfficeTemplate.setId(IdUtil.getId());
            sysOfficeTemplateManager.create(sysOfficeTemplate);
            msg = getMessage("sysOfficeTemplate.created", new Object[]{sysOfficeTemplate.getIdentifyLabel()}, "[office模板]成功创建!");
        } else {
            sysOfficeTemplateManager.update(sysOfficeTemplate);
            msg = getMessage("sysOfficeTemplate.updated", new Object[]{sysOfficeTemplate.getIdentifyLabel()}, "[office模板]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

