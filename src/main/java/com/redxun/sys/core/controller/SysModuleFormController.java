package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysModule;
import com.redxun.sys.core.manager.SysModuleManager;
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
 * 系统功能模块管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysModule/")
public class SysModuleFormController extends BaseFormController {

    @Resource
    private SysModuleManager sysModuleManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysModule")
    public SysModule processForm(HttpServletRequest request) {
        String moduleId = request.getParameter("moduleId");
        SysModule sysModule = null;
        if (StringUtils.isNotEmpty(moduleId)) {
            sysModule = sysModuleManager.get(moduleId);
        } else {
            sysModule = new SysModule();
        }

        return sysModule;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysModule
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysModule") @Valid SysModule sysModule, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysModule.getModuleId())) {
            sysModule.setModuleId(idGenerator.getSID());
            sysModuleManager.create(sysModule);
            msg = getMessage("sysModule.created", new Object[]{sysModule.getIdentifyLabel()}, "系统功能模块成功创建!");
        } else {
            sysModuleManager.update(sysModule);
            msg = getMessage("sysModule.updated", new Object[]{sysModule.getIdentifyLabel()}, "系统功能模块成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

