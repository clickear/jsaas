
package com.redxun.sys.bo.controller;

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

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.bo.entity.SysBoEnt;
import com.redxun.sys.bo.manager.SysBoEntManager;

/**
 * 表单实体对象 管理
 * @author ray
 */
@Controller
@RequestMapping("/sys/bo/sysBoEnt/")
public class SysBoEntFormController extends BaseFormController {

    @Resource
    private SysBoEntManager sysBoEntManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysBoEnt")
    public SysBoEnt processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysBoEnt sysBoEnt = null;
        if (StringUtils.isNotEmpty(id)) {
            sysBoEnt = sysBoEntManager.get(id);
        } else {
            sysBoEnt = new SysBoEnt();
        }

        return sysBoEnt;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysBoEnt") @Valid SysBoEnt sysBoEnt, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysBoEnt.getId())) {
            sysBoEnt.setId(IdUtil.getId());
            sysBoEntManager.create(sysBoEnt);
            msg = getMessage("sysBoEnt.created", new Object[]{sysBoEnt.getIdentifyLabel()}, "[表单实体对象]成功创建!");
        } else {
            sysBoEntManager.update(sysBoEnt);
            msg = getMessage("sysBoEnt.updated", new Object[]{sysBoEnt.getIdentifyLabel()}, "[表单实体对象]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

