
package com.redxun.wx.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.wx.core.entity.WxSubscribe;
import com.redxun.wx.core.manager.WxSubscribeManager;
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
 * 微信关注者 管理
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxSubscribe/")
public class WxSubscribeFormController extends BaseFormController {

    @Resource
    private WxSubscribeManager wxSubscribeManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("wxSubscribe")
    public WxSubscribe processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        WxSubscribe wxSubscribe = null;
        if (StringUtils.isNotEmpty(id)) {
            wxSubscribe = wxSubscribeManager.get(id);
        } else {
            wxSubscribe = new WxSubscribe();
        }

        return wxSubscribe;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("wxSubscribe") @Valid WxSubscribe wxSubscribe, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxSubscribe.getId())) {
            wxSubscribe.setId(IdUtil.getId());
            wxSubscribeManager.create(wxSubscribe);
            msg = getMessage("wxSubscribe.created", new Object[]{wxSubscribe.getIdentifyLabel()}, "[微信关注者]成功创建!");
        } else {
            wxSubscribeManager.update(wxSubscribe);
            msg = getMessage("wxSubscribe.updated", new Object[]{wxSubscribe.getIdentifyLabel()}, "[微信关注者]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

