
package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysInstTypeMenu;
import com.redxun.sys.core.manager.SysInstTypeMenuManager;
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
import com.redxun.sys.log.LogEnt;

/**
 * 机构类型授权菜单 管理
 * @author mansan
 */
@Controller
@RequestMapping("/sys/core/sysInstTypeMenu/")
public class SysInstTypeMenuFormController extends BaseFormController {

    @Resource
    private SysInstTypeMenuManager sysInstTypeMenuManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysInstTypeMenu")
    public SysInstTypeMenu processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysInstTypeMenu sysInstTypeMenu = null;
        if (StringUtils.isNotEmpty(id)) {
            sysInstTypeMenu = sysInstTypeMenuManager.get(id);
        } else {
            sysInstTypeMenu = new SysInstTypeMenu();
        }

        return sysInstTypeMenu;
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
    @LogEnt(action = "save", module = "sys", submodule = "机构类型授权菜单")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysInstTypeMenu") @Valid SysInstTypeMenu sysInstTypeMenu, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysInstTypeMenu.getId())) {
            sysInstTypeMenu.setId(IdUtil.getId());
            sysInstTypeMenuManager.create(sysInstTypeMenu);
            msg = getMessage("sysInstTypeMenu.created", new Object[]{sysInstTypeMenu.getIdentifyLabel()}, "[机构类型授权菜单]成功创建!");
        } else {
            sysInstTypeMenuManager.update(sysInstTypeMenu);
            msg = getMessage("sysInstTypeMenu.updated", new Object[]{sysInstTypeMenu.getIdentifyLabel()}, "[机构类型授权菜单]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

