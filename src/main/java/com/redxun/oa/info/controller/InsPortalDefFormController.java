
package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsPortalDef;
import com.redxun.oa.info.manager.InsPortalDefManager;
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
 * ins_portal_def 管理
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insPortalDef/")
public class InsPortalDefFormController extends BaseFormController {

    @Resource
    private InsPortalDefManager insPortalDefManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insPortalDef")
    public InsPortalDef processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsPortalDef insPortalDef = null;
        if (StringUtils.isNotEmpty(id)) {
            insPortalDef = insPortalDefManager.get(id);
        } else {
            insPortalDef = new InsPortalDef();
        }

        return insPortalDef;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insPortalDef") @Valid InsPortalDef insPortalDef, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insPortalDef.getPortId())) {
            insPortalDef.setPortId(IdUtil.getId());
            insPortalDefManager.create(insPortalDef);
            msg = getMessage("insPortalDef.created", new Object[]{insPortalDef.getIdentifyLabel()}, "门户成功创建!");
        } else {
            insPortalDefManager.update(insPortalDef);
            msg = getMessage("insPortalDef.updated", new Object[]{insPortalDef.getIdentifyLabel()}, "门户成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

