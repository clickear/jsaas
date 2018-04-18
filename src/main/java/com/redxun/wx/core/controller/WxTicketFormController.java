
package com.redxun.wx.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.wx.core.entity.WxTicket;
import com.redxun.wx.core.manager.WxTicketManager;
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
 * 微信卡券 管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/wx/core/wxTicket/")
public class WxTicketFormController extends BaseFormController {

    @Resource
    private WxTicketManager wxTicketManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("wxTicket")
    public WxTicket processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        WxTicket wxTicket = null;
        if (StringUtils.isNotEmpty(id)) {
            wxTicket = wxTicketManager.get(id);
        } else {
            wxTicket = new WxTicket();
        }

        return wxTicket;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("wxTicket") @Valid WxTicket wxTicket, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxTicket.getId())) {
            wxTicket.setId(IdUtil.getId());
            wxTicketManager.create(wxTicket);
            msg = getMessage("wxTicket.created", new Object[]{wxTicket.getIdentifyLabel()}, "[微信卡券]成功创建!");
        } else {
            wxTicketManager.update(wxTicket);
            msg = getMessage("wxTicket.updated", new Object[]{wxTicket.getIdentifyLabel()}, "[微信卡券]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

