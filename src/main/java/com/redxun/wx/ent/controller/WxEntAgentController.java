
package com.redxun.wx.ent.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.wx.ent.entity.WxEntAgent;
import com.redxun.wx.ent.entity.WxEntCorp;
import com.redxun.wx.ent.manager.WxEntAgentManager;
import com.redxun.wx.ent.manager.WxEntCorpManager;
import com.redxun.wx.ent.util.WeixinUtil;

/**
 * 微信应用控制器
 * @author mansan
 */
@Controller
@RequestMapping("/wx/ent/wxEntAgent/")
public class WxEntAgentController extends BaseMybatisListController{
    @Resource
    WxEntAgentManager wxEntAgentManager;
    @Resource
    WxEntCorpManager wxEntCorpManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                wxEntAgentManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=RequestUtil.getString(request, "pkId");
        WxEntAgent wxEntAgent=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxEntAgent=wxEntAgentManager.get(pkId);
        }else{
        	wxEntAgent=new WxEntAgent();
        }
        return getPathView(request).addObject("wxEntAgent",wxEntAgent);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	String tenantId=ContextUtil.getCurrentTenantId();
    	//复制添加
    	WxEntCorp wxEntCorp=wxEntCorpManager.getByTenantId(tenantId);
    	
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	WxEntAgent wxEntAgent=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		wxEntAgent=wxEntAgentManager.get(pkId);
    		if("true".equals(forCopy)){
    			wxEntAgent.setId(null);
    		}
    	}else{
    		wxEntAgent=new WxEntAgent();
    	}
    	return getPathView(request)
    			.addObject("wxEntAgent",wxEntAgent)
    			.addObject("wxEntCorp",wxEntCorp);
    	
    }
    
    
    @RequestMapping("getAppInfo")
    @ResponseBody
    public JsonResult<Void> getAppInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String corpId=RequestUtil.getString(request, "corpId");
    	String secret=RequestUtil.getString(request, "secret");
    	JsonResult<Void> result= WeixinUtil.getAppInfoById(corpId, secret);
    	return result;
    }
    

    

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxEntAgentManager;
	}

}
