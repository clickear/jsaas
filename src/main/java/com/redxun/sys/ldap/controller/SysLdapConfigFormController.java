package com.redxun.sys.ldap.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.ldap.entity.SysLdapConfig;
import com.redxun.sys.ldap.manager.SysLdapConfigManager;
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
 * Ldap配置管理
 * @author csx
 */
@Controller
@RequestMapping("/sys/ldap/sysLdapConfig/")
public class SysLdapConfigFormController extends BaseFormController {

    @Resource
    private SysLdapConfigManager sysLdapConfigManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysLdapConfig")
    public SysLdapConfig processForm(HttpServletRequest request) {
        String sysLdapConfigId = request.getParameter("sysLdapConfigId");
        SysLdapConfig sysLdapConfig = null;
        if (StringUtils.isNotEmpty(sysLdapConfigId)) {
            sysLdapConfig = sysLdapConfigManager.get(sysLdapConfigId);
        } else {
            sysLdapConfig = new SysLdapConfig();
        }

        return sysLdapConfig;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysLdapConfig
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysLdapConfig") @Valid SysLdapConfig sysLdapConfig, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysLdapConfig.getSysLdapConfigId())) {
            sysLdapConfig.setSysLdapConfigId(idGenerator.getSID());
            sysLdapConfigManager.create(sysLdapConfig);
            msg = getMessage("sysLdapConfig.created", new Object[]{sysLdapConfig.getIdentifyLabel()}, "Ldap配置成功创建!");
        } else {
            sysLdapConfigManager.update(sysLdapConfig);
            msg = getMessage("sysLdapConfig.updated", new Object[]{sysLdapConfig.getIdentifyLabel()}, "Ldap配置成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

