package com.redxun.sys.ldap.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.ldap.entity.SysLdapLog;
import com.redxun.sys.ldap.manager.SysLdapLogManager;
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
 * LDAP日志管理
 * @author csx
 */
@Controller
@RequestMapping("/sys/ldap/sysLdapLog/")
public class SysLdapLogFormController extends BaseFormController {

    @Resource
    private SysLdapLogManager sysLdapLogManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysLdapLog")
    public SysLdapLog processForm(HttpServletRequest request) {
        String logId = request.getParameter("logId");
        SysLdapLog sysLdapLog = null;
        if (StringUtils.isNotEmpty(logId)) {
            sysLdapLog = sysLdapLogManager.get(logId);
        } else {
            sysLdapLog = new SysLdapLog();
        }

        return sysLdapLog;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysLdapLog
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysLdapLog") @Valid SysLdapLog sysLdapLog, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysLdapLog.getLogId())) {
            sysLdapLog.setLogId(idGenerator.getSID());
            sysLdapLogManager.create(sysLdapLog);
            msg = getMessage("sysLdapLog.created", new Object[]{sysLdapLog.getIdentifyLabel()}, "LDAP日志成功创建!");
        } else {
            sysLdapLogManager.update(sysLdapLog);
            msg = getMessage("sysLdapLog.updated", new Object[]{sysLdapLog.getIdentifyLabel()}, "LDAP日志成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

