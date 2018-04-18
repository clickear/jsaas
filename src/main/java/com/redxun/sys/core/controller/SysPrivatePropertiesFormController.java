
package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysPrivateProperties;
import com.redxun.sys.core.manager.SysPrivatePropertiesManager;
import com.redxun.sys.log.LogEnt;

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

/**
 * 私有参数 管理
 * @author ray
 */
@Controller
@RequestMapping("/sys/core/sysPrivateProperties/")
public class SysPrivatePropertiesFormController extends BaseFormController {

    @Resource
    private SysPrivatePropertiesManager sysPrivatePropertiesManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysPrivateProperties")
    public SysPrivateProperties processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysPrivateProperties sysPrivateProperties = null;
        if (StringUtils.isNotEmpty(id)) {
            sysPrivateProperties = sysPrivatePropertiesManager.get(id);
        } else {
            sysPrivateProperties = new SysPrivateProperties();
        }

        return sysPrivateProperties;
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
    @LogEnt(action = "save", module = "系统内核", submodule = "系统私有参数")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysPrivateProperties") @Valid SysPrivateProperties sysPrivateProperties, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysPrivateProperties.getId())) {
            sysPrivateProperties.setId(IdUtil.getId());
            sysPrivatePropertiesManager.create(sysPrivateProperties);
            msg = getMessage("sysPrivateProperties.created", new Object[]{sysPrivateProperties.getIdentifyLabel()}, "[私有参数]成功创建!");
        } else {
            sysPrivatePropertiesManager.update(sysPrivateProperties);
            msg = getMessage("sysPrivateProperties.updated", new Object[]{sysPrivateProperties.getIdentifyLabel()}, "[私有参数]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

