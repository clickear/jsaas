package com.redxun.sys.ldap.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.ldap.entity.SysLdapOu;
import com.redxun.sys.ldap.manager.SysLdapOuManager;
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
 * LDAP部门管理
 * @author csx
 */
@Controller
@RequestMapping("/sys/ldap/sysLdapOu/")
public class SysLdapOuFormController extends BaseFormController {

    @Resource
    private SysLdapOuManager sysLdapOuManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysLdapOu")
    public SysLdapOu processForm(HttpServletRequest request) {
        String sysLdapOuId = request.getParameter("sysLdapOuId");
        SysLdapOu sysLdapOu = null;
        if (StringUtils.isNotEmpty(sysLdapOuId)) {
            sysLdapOu = sysLdapOuManager.get(sysLdapOuId);
        } else {
            sysLdapOu = new SysLdapOu();
        }

        return sysLdapOu;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysLdapOu
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysLdapOu") @Valid SysLdapOu sysLdapOu, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysLdapOu.getSysLdapOuId())) {
            sysLdapOu.setSysLdapOuId(idGenerator.getSID());
            sysLdapOuManager.create(sysLdapOu);
            msg = getMessage("sysLdapOu.created", new Object[]{sysLdapOu.getIdentifyLabel()}, "LDAP部门成功创建!");
        } else {
            sysLdapOuManager.update(sysLdapOu);
            msg = getMessage("sysLdapOu.updated", new Object[]{sysLdapOu.getIdentifyLabel()}, "LDAP部门成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

