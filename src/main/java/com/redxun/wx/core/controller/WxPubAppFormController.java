
package com.redxun.wx.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.wx.core.entity.WxPubApp;
import com.redxun.wx.core.manager.WxPubAppManager;
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
 * 公众号管理 管理
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxPubApp/")
public class WxPubAppFormController extends BaseFormController {

    @Resource
    private WxPubAppManager wxPubAppManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("wxPubApp")
    public WxPubApp processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        WxPubApp wxPubApp = null;
        if (StringUtils.isNotEmpty(id)) {
            wxPubApp = wxPubAppManager.get(id);
        } else {
            wxPubApp = new WxPubApp();
        }

        return wxPubApp;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("wxPubApp") @Valid WxPubApp wxPubApp, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxPubApp.getId())) {
            wxPubApp.setId(IdUtil.getId());
            wxPubAppManager.create(wxPubApp);
            msg = getMessage("wxPubApp.created", new Object[]{wxPubApp.getIdentifyLabel()}, "[公众号管理]成功创建!");
        } else {
            wxPubAppManager.update(wxPubApp);
            msg = getMessage("wxPubApp.updated", new Object[]{wxPubApp.getIdentifyLabel()}, "[公众号管理]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

