
package com.redxun.wx.core.controller;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.wx.core.entity.WxPubApp;
import com.redxun.wx.core.entity.WxWebGrant;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.core.manager.WxWebGrantManager;
import com.redxun.wx.pub.util.PublicApiUrl;
import com.redxun.wx.pub.util.WxCode;

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
 * 微信网页授权 管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/wx/core/wxWebGrant/")
public class WxWebGrantFormController extends BaseFormController {

    @Resource
    private WxWebGrantManager wxWebGrantManager;
    @Resource
    private WxPubAppManager wxPubAppManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("wxWebGrant")
    public WxWebGrant processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        WxWebGrant wxWebGrant = null;
        if (StringUtils.isNotEmpty(id)) {
            wxWebGrant = wxWebGrantManager.get(id);
        } else {
            wxWebGrant = new WxWebGrant();
        }

        return wxWebGrant;
    }
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("wxWebGrant") @Valid WxWebGrant wxWebGrant, BindingResult result) throws UnsupportedEncodingException {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxWebGrant.getId())) {
            wxWebGrant.setId(IdUtil.getId());
            String url=wxWebGrant.getUrl();
            String pubId=wxWebGrant.getPubId();
            WxPubApp wxPubApp=wxPubAppManager.get(pubId);
            String appId=wxPubApp.getAppId();
            String transformUrl=PublicApiUrl.getWebCodeUrl(appId, url, "snsapi_userinfo");
            wxWebGrant.setTransformUrl(transformUrl);
            wxWebGrantManager.create(wxWebGrant);
            msg = getMessage("wxWebGrant.created", new Object[]{wxWebGrant.getIdentifyLabel()}, "成功创建授权url");
        } else {
        	String url=wxWebGrant.getUrl();
            String pubId=wxWebGrant.getPubId();
            WxPubApp wxPubApp=wxPubAppManager.get(pubId);
            String appId=wxPubApp.getAppId();
            String transformUrl=PublicApiUrl.getWebCodeUrl(appId, url, "snsapi_userinfo");
            wxWebGrant.setTransformUrl(transformUrl);
            wxWebGrantManager.update(wxWebGrant);
            msg = getMessage("wxWebGrant.updated", new Object[]{wxWebGrant.getIdentifyLabel()}, "授权url已更新");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

