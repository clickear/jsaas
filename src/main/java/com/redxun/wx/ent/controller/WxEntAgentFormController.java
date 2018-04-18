
package com.redxun.wx.ent.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.wx.ent.entity.WxEntAgent;
import com.redxun.wx.ent.manager.WxEntAgentManager;
import com.redxun.wx.ent.util.WeixinUtil;

/**
 * 微信应用 管理
 * @author mansan
 */
@Controller
@RequestMapping("/wx/ent/wxEntAgent/")
public class WxEntAgentFormController extends BaseFormController {

    @Resource
    private WxEntAgentManager wxEntAgentManager;
 
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @RequestBody   WxEntAgent wxEntAgent, BindingResult result) throws Exception {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxEntAgent.getId())) {
            wxEntAgent.setId(IdUtil.getId());
            wxEntAgentManager.create(wxEntAgent);
           
            msg = getMessage("wxEntAgent.created", new Object[]{wxEntAgent.getIdentifyLabel()}, "[微信应用]成功创建!");
        } else {
        	WxEntAgent originAgent=wxEntAgentManager.get(wxEntAgent.getId());
        	wxEntAgent.setEntId(originAgent.getEntId());
        	wxEntAgent.setCorpId(wxEntAgent.getWxEntCorp().getCorpId());
        	wxEntAgent.setTenantId(originAgent.getTenantId());
        	wxEntAgent.setCreateBy(originAgent.getCreateBy());
        	wxEntAgent.setCreateTime(originAgent.getCreateTime());
            wxEntAgentManager.update(wxEntAgent);
            msg = getMessage("wxEntAgent.updated", new Object[]{wxEntAgent.getIdentifyLabel()}, "[微信应用]成功更新!");
        }
        //当选择发布的情况。
        if("saveDeploy".equals(wxEntAgent.getAction())){
        	//先获取应用，修改json，再提交。
        	JsonResult<JSONObject> rtn=	wxEntAgentManager.getAppInfo( wxEntAgent);
        	if(rtn.isSuccess()){
        		JSONObject rtnObj= WeixinUtil.setAppInfo(wxEntAgent.getCorpId(), wxEntAgent.getSecret(), rtn.getData().toJSONString());
        		if(rtnObj.getInteger("errcode")==0){
        			msg = "发布应用信息成功!";
        		}
        		else{
        			msg = "发布应用信息失败:" + rtnObj.getString("errmsg");
        		}
        	}
        	else{
        		msg="获取应用信息失败:" + rtn.getMessage();
        	}
        }
        
        return new JsonResult(true, msg);
    }
}

