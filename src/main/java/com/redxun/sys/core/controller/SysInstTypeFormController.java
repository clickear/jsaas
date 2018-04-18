
package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysInstType;
import com.redxun.sys.core.manager.SysInstTypeManager;
import com.redxun.sys.log.LogEnt;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.redxun.saweb.security.provider.SecurityDataSourceProvider;
import com.redxun.saweb.util.IdUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 机构类型 管理
 * @author mansan
 */
@Controller
@RequestMapping("/sys/core/sysInstType/")
public class SysInstTypeFormController extends BaseFormController {

    @Resource
    private SysInstTypeManager sysInstTypeManager;
    
    @Resource
    SecurityDataSourceProvider securityDataSourceProvider;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysInstType")
    public SysInstType processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysInstType sysInstType = null;
        if (StringUtils.isNotEmpty(id)) {
            sysInstType = sysInstTypeManager.get(id);
        } else {
            sysInstType = new SysInstType();
        }

        return sysInstType;
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
    @LogEnt(action = "save", module = "系统内核", submodule = "注册机构")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysInstType") @Valid SysInstType sysInstType, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysInstType.getTypeId())) {
            sysInstType.setTypeId(IdUtil.getId());
            sysInstTypeManager.create(sysInstType);
            msg = getMessage("sysInstType.created", new Object[]{sysInstType.getIdentifyLabel()}, "[机构类型]成功创建!");
        } else {
            sysInstTypeManager.update(sysInstType);
            msg = getMessage("sysInstType.updated", new Object[]{sysInstType.getIdentifyLabel()}, "[机构类型]成功更新!");
        }
        //saveOpMessage(request, msg);
        securityDataSourceProvider.reloadSecurityDataCache();
        return new JsonResult(true, msg);
    }
}

