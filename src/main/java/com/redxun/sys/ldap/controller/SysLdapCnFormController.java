package com.redxun.sys.ldap.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.ldap.entity.SysLdapCn;
import com.redxun.sys.ldap.manager.SysLdapCnManager;
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
 * LDAP用户管理
 * @author csx
 */
@Controller
@RequestMapping("/sys/ldap/sysLdapCn/")
public class SysLdapCnFormController extends BaseFormController {

    @Resource
    private SysLdapCnManager sysLdapCnManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysLdapCn")
    public SysLdapCn processForm(HttpServletRequest request) {
        String sysLdapUserId = request.getParameter("sysLdapUserId");
        SysLdapCn sysLdapCn = null;
        if (StringUtils.isNotEmpty(sysLdapUserId)) {
            sysLdapCn = sysLdapCnManager.get(sysLdapUserId);
        } else {
            sysLdapCn = new SysLdapCn();
        }

        return sysLdapCn;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysLdapCn
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysLdapCn") @Valid SysLdapCn sysLdapCn, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysLdapCn.getSysLdapUserId())) {
            sysLdapCn.setSysLdapUserId(idGenerator.getSID());
            sysLdapCnManager.create(sysLdapCn);
            msg = getMessage("sysLdapCn.created", new Object[]{sysLdapCn.getIdentifyLabel()}, "LDAP用户成功创建!");
        } else {
            sysLdapCnManager.update(sysLdapCn);
            msg = getMessage("sysLdapCn.updated", new Object[]{sysLdapCn.getIdentifyLabel()}, "LDAP用户成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

