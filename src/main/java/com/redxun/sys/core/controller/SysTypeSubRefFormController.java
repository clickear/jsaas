
package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysTypeSubRef;
import com.redxun.sys.core.manager.SysTypeSubRefManager;
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
 * 机构--子系统关系 管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/sys/core/sysTypeSubRef/")
public class SysTypeSubRefFormController extends BaseFormController {

    @Resource
    private SysTypeSubRefManager sysTypeSubRefManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysTypeSubRef")
    public SysTypeSubRef processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysTypeSubRef sysTypeSubRef = null;
        if (StringUtils.isNotEmpty(id)) {
            sysTypeSubRef = sysTypeSubRefManager.get(id);
        } else {
            sysTypeSubRef = new SysTypeSubRef();
        }

        return sysTypeSubRef;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysTypeSubRef") @Valid SysTypeSubRef sysTypeSubRef, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysTypeSubRef.getId())) {
            sysTypeSubRef.setId(IdUtil.getId());
            sysTypeSubRefManager.create(sysTypeSubRef);
            msg = getMessage("sysTypeSubRef.created", new Object[]{sysTypeSubRef.getIdentifyLabel()}, "[机构--子系统关系]成功创建!");
        } else {
            sysTypeSubRefManager.update(sysTypeSubRef);
            msg = getMessage("sysTypeSubRef.updated", new Object[]{sysTypeSubRef.getIdentifyLabel()}, "[机构--子系统关系]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

