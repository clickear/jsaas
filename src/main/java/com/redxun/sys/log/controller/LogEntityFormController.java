
package com.redxun.sys.log.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.log.entity.LogEntity;
import com.redxun.sys.log.manager.LogEntityManager;
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
 * 日志实体 管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/sys/log/logEntity/")
public class LogEntityFormController extends BaseFormController {

    @Resource
    private LogEntityManager logEntityManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("logEntity")
    public LogEntity processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        LogEntity logEntity = null;
        if (StringUtils.isNotEmpty(id)) {
            logEntity = logEntityManager.get(id);
        } else {
            logEntity = new LogEntity();
        }

        return logEntity;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("logEntity") @Valid LogEntity logEntity, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(logEntity.getId())) {
            logEntity.setId(IdUtil.getId());
            logEntityManager.create(logEntity);
            msg = getMessage("logEntity.created", new Object[]{logEntity.getIdentifyLabel()}, "[日志实体]成功创建!");
        } else {
            logEntityManager.update(logEntity);
            msg = getMessage("logEntity.updated", new Object[]{logEntity.getIdentifyLabel()}, "[日志实体]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

