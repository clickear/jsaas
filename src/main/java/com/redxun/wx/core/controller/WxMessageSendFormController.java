
package com.redxun.wx.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.wx.core.entity.WxMessageSend;
import com.redxun.wx.core.manager.WxMessageSendManager;
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
 * WX_MESSAGE_SEND 管理
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxMessageSend/")
public class WxMessageSendFormController extends BaseFormController {

    @Resource
    private WxMessageSendManager wxMessageSendManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("wxMessageSend")
    public WxMessageSend processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        WxMessageSend wxMessageSend = null;
        if (StringUtils.isNotEmpty(id)) {
            wxMessageSend = wxMessageSendManager.get(id);
        } else {
            wxMessageSend = new WxMessageSend();
        }

        return wxMessageSend;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("wxMessageSend") @Valid WxMessageSend wxMessageSend, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxMessageSend.getID())) {
            wxMessageSend.setID(IdUtil.getId());
            wxMessageSendManager.create(wxMessageSend);
            msg = getMessage("wxMessageSend.created", new Object[]{wxMessageSend.getIdentifyLabel()}, "[WX_MESSAGE_SEND]成功创建!");
        } else {
            wxMessageSendManager.update(wxMessageSend);
            msg = getMessage("wxMessageSend.updated", new Object[]{wxMessageSend.getIdentifyLabel()}, "[WX_MESSAGE_SEND]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

