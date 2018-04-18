
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
import com.redxun.wx.core.entity.WxKeyWordReply;
import com.redxun.wx.core.manager.WxKeyWordReplyManager;

/**
 * 公众号关键字回复控制器
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/wx/core/wxKeyWordReply/")
public class WxKeyWordReplyController extends BaseMybatisListController{
    @Resource
    WxKeyWordReplyManager wxKeyWordReplyManager;
   
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
                wxKeyWordReplyManager.delete(id);
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
        WxKeyWordReply wxKeyWordReply=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxKeyWordReply=wxKeyWordReplyManager.get(pkId);
        }else{
        	wxKeyWordReply=new WxKeyWordReply();
        }
        return getPathView(request).addObject("wxKeyWordReply",wxKeyWordReply);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	String pubId=RequestUtil.getString(request, "pubId");
    	WxKeyWordReply wxKeyWordReply=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		wxKeyWordReply=wxKeyWordReplyManager.get(pkId);
    	}else{
    		wxKeyWordReply=new WxKeyWordReply();
    		wxKeyWordReply.setPubId(pubId);
    	}
    	
    	return getPathView(request).addObject("wxKeyWordReply",wxKeyWordReply);
    }
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
    	String pubId=RequestUtil.getString(request, "pubId");
    	return getPathView(request).addObject("pubId", pubId);
    }
    @RequestMapping("myList")
    @ResponseBody
    public JsonPageResult<WxKeyWordReply> myList(HttpServletRequest request,HttpServletResponse response){
    	String pubId=RequestUtil.getString(request, "pubId");
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("PUB_ID_", pubId);
    	List<WxKeyWordReply> wxKeyWordReplies=wxKeyWordReplyManager.getAll(queryFilter);
    	JsonPageResult<WxKeyWordReply> jsonPageResult=new JsonPageResult<WxKeyWordReply>(wxKeyWordReplies, queryFilter.getPage().getTotalItems());
    	return jsonPageResult;
    	
    }


	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxKeyWordReplyManager;
	}

}
