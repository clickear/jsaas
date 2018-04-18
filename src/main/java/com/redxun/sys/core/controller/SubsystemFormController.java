package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.Subsystem;
import com.redxun.sys.core.manager.SubsystemManager;
import com.redxun.sys.log.LogEnt;

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
 * 子系统管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/subsystem/")
public class SubsystemFormController extends BaseFormController {

    @Resource
    private SubsystemManager subsystemManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("subsystem")
    public Subsystem processForm(HttpServletRequest request) {
        String sysId = request.getParameter("sysId");
        Subsystem subsystem = null;
        if (StringUtils.isNotEmpty(sysId)) {
            subsystem = subsystemManager.get(sysId);
        } else {
            subsystem = new Subsystem();
        }

        return subsystem;
    }
    /**
     * 保存实体数据
     * @param request
     * @param subsystem
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "系统内核", submodule = "子系统")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("subsystem") @Valid Subsystem subsystem, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(subsystem.getSysId())) {
            subsystem.setSysId(idGenerator.getSID());
            subsystemManager.create(subsystem);
            msg = getMessage("subsystem.created", new Object[]{subsystem.getIdentifyLabel()}, "子系统成功创建!");
        } else {
            subsystemManager.update(subsystem);
            msg = getMessage("subsystem.updated", new Object[]{subsystem.getIdentifyLabel()}, "子系统成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

