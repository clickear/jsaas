
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
import com.redxun.sys.bo.entity.SysBoDef;
import com.redxun.sys.bo.manager.SysBoDefManager;

/**
 * BO定义 管理
 * @author ray
 */
@Controller
@RequestMapping("/sys/bo/sysBoDef/")
public class SysBoDefFormController extends BaseFormController {

    @Resource
    private SysBoDefManager sysBoDefManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysBoDef")
    public SysBoDef processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysBoDef sysBoDef = null;
        if (StringUtils.isNotEmpty(id)) {
            sysBoDef = sysBoDefManager.get(id);
        } else {
            sysBoDef = new SysBoDef();
        }

        return sysBoDef;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysBoDef") @Valid SysBoDef sysBoDef, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysBoDef.getId())) {
            sysBoDef.setId(IdUtil.getId());
            sysBoDefManager.create(sysBoDef);
            msg = getMessage("sysBoDef.created", new Object[]{sysBoDef.getIdentifyLabel()}, "[BO定义]成功创建!");
        } else {
            sysBoDefManager.update(sysBoDef);
            msg = getMessage("sysBoDef.updated", new Object[]{sysBoDef.getIdentifyLabel()}, "[BO定义]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

