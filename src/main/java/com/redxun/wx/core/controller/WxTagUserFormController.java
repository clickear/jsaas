
package com.redxun.wx.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.wx.core.entity.WxTagUser;
import com.redxun.wx.core.manager.WxTagUserManager;
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
 * 微信用户标签 管理
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxTagUser/")
public class WxTagUserFormController extends BaseFormController {

    @Resource
    private WxTagUserManager wxTagUserManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("wxTagUser")
    public WxTagUser processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        WxTagUser wxTagUser = null;
        if (StringUtils.isNotEmpty(id)) {
            wxTagUser = wxTagUserManager.get(id);
        } else {
            wxTagUser = new WxTagUser();
        }

        return wxTagUser;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("wxTagUser") @Valid WxTagUser wxTagUser, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxTagUser.getId())) {
            wxTagUser.setId(IdUtil.getId());
            wxTagUserManager.create(wxTagUser);
            msg = getMessage("wxTagUser.created", new Object[]{wxTagUser.getIdentifyLabel()}, "[微信用户标签]成功创建!");
        } else {
            wxTagUserManager.update(wxTagUser);
            msg = getMessage("wxTagUser.updated", new Object[]{wxTagUser.getIdentifyLabel()}, "[微信用户标签]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

