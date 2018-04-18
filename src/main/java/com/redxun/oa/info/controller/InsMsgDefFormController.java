
package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsMsgDef;
import com.redxun.oa.info.manager.InsMsgDefManager;
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
 * INS_MSG_DEF 管理
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insMsgDef/")
public class InsMsgDefFormController extends BaseFormController {

    @Resource
    private InsMsgDefManager insMsgDefManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insMsgDef")
    public InsMsgDef processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsMsgDef insMsgDef = null;
        if (StringUtils.isNotEmpty(id)) {
            insMsgDef = insMsgDefManager.get(id);
        } else {
            insMsgDef = new InsMsgDef();
        }

        return insMsgDef;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insMsgDef") @Valid InsMsgDef insMsgDef, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        String path = request.getContextPath();
        if (StringUtils.isEmpty(insMsgDef.getMsgId())) {
            insMsgDef.setMsgId(IdUtil.getId());
            insMsgDefManager.create(insMsgDef);
            msg = getMessage("insMsgDef.created", new Object[]{insMsgDef.getIdentifyLabel()}, "消息盒子成功创建!");
        } else {
            insMsgDefManager.update(insMsgDef);
            msg = getMessage("insMsgDef.updated", new Object[]{insMsgDef.getIdentifyLabel()}, "消息盒子成功更新!");
        }
        return new JsonResult(true, msg);
    }
}

