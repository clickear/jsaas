
package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysListSol;
import com.redxun.sys.core.manager.SysListSolManager;
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
 * 系统列表方案 管理
 * @author mansan
 */
@Controller
@RequestMapping("/sys/core/sysListSol/")
public class SysListSolFormController extends BaseFormController {

    @Resource
    private SysListSolManager sysListSolManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysListSol")
    public SysListSol processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysListSol sysListSol = null;
        if (StringUtils.isNotEmpty(id)) {
            sysListSol = sysListSolManager.get(id);
        } else {
            sysListSol = new SysListSol();
        }

        return sysListSol;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysListSol") @Valid SysListSol sysListSol, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysListSol.getSolId())) {
            sysListSol.setSolId(IdUtil.getId());
            sysListSolManager.create(sysListSol);
            msg = getMessage("sysListSol.created", new Object[]{sysListSol.getIdentifyLabel()}, "[系统列表方案]成功创建!");
        } else {
            sysListSolManager.update(sysListSol);
            msg = getMessage("sysListSol.updated", new Object[]{sysListSol.getIdentifyLabel()}, "[系统列表方案]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

