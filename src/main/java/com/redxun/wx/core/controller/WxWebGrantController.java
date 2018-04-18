
package com.redxun.wx.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.wx.core.entity.WxWebGrant;
import com.redxun.wx.core.manager.WxWebGrantManager;

/**
 * 微信网页授权控制器
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/wx/core/wxWebGrant/")
public class WxWebGrantController extends BaseMybatisListController{
    @Resource
    WxWebGrantManager wxWebGrantManager;
   
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
                wxWebGrantManager.delete(id);
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
        WxWebGrant wxWebGrant=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxWebGrant=wxWebGrantManager.get(pkId);
        }else{
        	wxWebGrant=new WxWebGrant();
        }
        return getPathView(request).addObject("wxWebGrant",wxWebGrant);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	String pubId=RequestUtil.getString(request, "pubId");
    	WxWebGrant wxWebGrant=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		wxWebGrant=wxWebGrantManager.get(pkId);
    	}else{
    		wxWebGrant=new WxWebGrant();
    		wxWebGrant.setPubId(pubId);
    	}
    	return getPathView(request).addObject("wxWebGrant",wxWebGrant);
    }
    
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
    	String pubId=RequestUtil.getString(request, "pubId");
    	return getPathView(request).addObject("pubId", pubId);
    }

    
    @RequestMapping("getPubList")
    @ResponseBody
    public JsonPageResult<WxWebGrant> getPubList(HttpServletRequest request,HttpServletResponse response){
    	String pubId=RequestUtil.getString(request, "pubId");
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("PUB_ID_",pubId);
    	List<WxWebGrant> wxWebGrants=wxWebGrantManager.getAll(queryFilter);
    	JsonPageResult<WxWebGrant> jsonPageResult=new JsonPageResult<WxWebGrant>(wxWebGrants, queryFilter.getPage().getTotalItems());
    	return jsonPageResult;
    }

    /**
     * 有子表的情况下编辑明细的json
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("getJson")
    @ResponseBody
    public String getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String uId=RequestUtil.getString(request, "ids");    	
        WxWebGrant wxWebGrant = wxWebGrantManager.getWxWebGrant(uId);
        String json = JSONObject.toJSONString(wxWebGrant);
        return json;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxWebGrantManager;
	}

}
