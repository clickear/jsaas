
package com.redxun.sys.log.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.log.entity.LogModule;
import com.redxun.sys.log.manager.LogModuleManager;
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
 * 日志模块 管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/sys/log/logModule/")
public class LogModuleFormController extends BaseFormController {

    @Resource
    private LogModuleManager logModuleManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("logModule")
    public LogModule processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        LogModule logModule = null;
        if (StringUtils.isNotEmpty(id)) {
            logModule = logModuleManager.get(id);
        } else {
            logModule = new LogModule();
        }

        return logModule;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("logModule") @Valid LogModule logModule, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(logModule.getId())) {
            logModule.setId(IdUtil.getId());
            logModuleManager.create(logModule);
            msg = getMessage("logModule.created", new Object[]{logModule.getIdentifyLabel()}, "[日志模块]成功创建!");
        } else {
            logModuleManager.update(logModule);
            msg = getMessage("logModule.updated", new Object[]{logModule.getIdentifyLabel()}, "[日志模块]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

