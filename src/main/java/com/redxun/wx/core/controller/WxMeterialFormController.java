
package com.redxun.wx.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.wx.core.entity.WxMeterial;
import com.redxun.wx.core.manager.WxMeterialManager;
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
 * 微信素材 管理
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxMeterial/")
public class WxMeterialFormController extends BaseFormController {

    @Resource
    private WxMeterialManager wxMeterialManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("wxMeterial")
    public WxMeterial processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        WxMeterial wxMeterial = null;
        if (StringUtils.isNotEmpty(id)) {
            wxMeterial = wxMeterialManager.get(id);
        } else {
            wxMeterial = new WxMeterial();
        }

        return wxMeterial;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("wxMeterial") @Valid WxMeterial wxMeterial, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxMeterial.getId())) {
            wxMeterial.setId(IdUtil.getId());
            wxMeterialManager.create(wxMeterial);
            msg = getMessage("wxMeterial.created", new Object[]{wxMeterial.getIdentifyLabel()}, "[微信素材]成功创建!");
        } else {
            wxMeterialManager.update(wxMeterial);
            msg = getMessage("wxMeterial.updated", new Object[]{wxMeterial.getIdentifyLabel()}, "[微信素材]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

