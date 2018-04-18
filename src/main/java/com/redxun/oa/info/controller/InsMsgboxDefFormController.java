
package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsMsgboxDef;
import com.redxun.oa.info.manager.InsMsgboxDefManager;
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
 * 栏目消息盒子表 管理
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insMsgboxDef/")
public class InsMsgboxDefFormController extends BaseFormController {

    @Resource
    private InsMsgboxDefManager insMsgboxDefManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insMsgboxDef")
    public InsMsgboxDef processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsMsgboxDef insMsgboxDef = null;
        if (StringUtils.isNotEmpty(id)) {
            insMsgboxDef = insMsgboxDefManager.get(id);
        } else {
            insMsgboxDef = new InsMsgboxDef();
        }

        return insMsgboxDef;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insMsgboxDef") @Valid InsMsgboxDef insMsgboxDef, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insMsgboxDef.getBoxId())) {
            insMsgboxDef.setBoxId(IdUtil.getId());
            insMsgboxDefManager.create(insMsgboxDef);
            msg = getMessage("insMsgboxDef.created", new Object[]{insMsgboxDef.getIdentifyLabel()}, "[栏目消息盒子表]成功创建!");
        } else {
            insMsgboxDefManager.update(insMsgboxDef);
            msg = getMessage("insMsgboxDef.updated", new Object[]{insMsgboxDef.getIdentifyLabel()}, "[栏目消息盒子表]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

