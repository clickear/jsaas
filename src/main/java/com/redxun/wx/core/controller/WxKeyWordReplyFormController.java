
package com.redxun.wx.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.wx.core.entity.WxKeyWordReply;
import com.redxun.wx.core.manager.WxKeyWordReplyManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.RequestUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公众号关键字回复 管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/wx/core/wxKeyWordReply/")
public class WxKeyWordReplyFormController extends BaseFormController {

    @Resource
    private WxKeyWordReplyManager wxKeyWordReplyManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("wxKeyWordReply")
    public WxKeyWordReply processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        WxKeyWordReply wxKeyWordReply = null;
        if (StringUtils.isNotEmpty(id)) {
            wxKeyWordReply = wxKeyWordReplyManager.get(id);
        } else {
            wxKeyWordReply = new WxKeyWordReply();
        }

        return wxKeyWordReply;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("wxKeyWordReply") @Valid WxKeyWordReply wxKeyWordReply, BindingResult result) {
    	String meterialText=RequestUtil.getString(request, "meterialText");
    	String meterial=RequestUtil.getString(request, "meterial");
    	String replyMusic=RequestUtil.getString(request, "replyMusic");
    	String replyText=RequestUtil.getString(request, "replyText");
    	String replyType=RequestUtil.getString(request, "replyType");
    	String pubId=RequestUtil.getString(request, "pubId");
    	String replyConfig="";
    	JSONObject replyJson=new JSONObject();
    	replyJson.put("meterialText", meterialText);
    	replyJson.put("meterial", meterial);
    	replyJson.put("replyMusic", replyMusic);
    	replyJson.put("replyText", replyText);
    	
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxKeyWordReply.getId())) {
            wxKeyWordReply.setId(IdUtil.getId());
            wxKeyWordReply.setReplyContent(replyJson.toString());
            wxKeyWordReplyManager.create(wxKeyWordReply);
            msg = getMessage("wxKeyWordReply.created", new Object[]{wxKeyWordReply.getIdentifyLabel()}, "成功创建");
        } else {
        	wxKeyWordReply.setReplyContent(replyJson.toString());
            wxKeyWordReplyManager.update(wxKeyWordReply);
            msg = getMessage("wxKeyWordReply.updated", new Object[]{wxKeyWordReply.getIdentifyLabel()}, "成功更新");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

