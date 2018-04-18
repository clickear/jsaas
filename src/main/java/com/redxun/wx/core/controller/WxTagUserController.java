
package com.redxun.wx.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.wx.core.entity.WxTag;
import com.redxun.wx.core.entity.WxTagUser;
import com.redxun.wx.core.manager.WxPubAppManager;
import com.redxun.wx.core.manager.WxTagUserManager;
import com.redxun.wx.pub.util.TagUtil;

/**
 * 微信用户标签控制器
 * @author ray
 */
@Controller
@RequestMapping("/wx/core/wxTagUser/")
public class WxTagUserController extends BaseMybatisListController{
    @Resource
    WxTagUserManager wxTagUserManager;
    @Resource
    WxPubAppManager wxPubAppManager;
   
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
                wxTagUserManager.delete(id);
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
        WxTagUser wxTagUser=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxTagUser=wxTagUserManager.get(pkId);
        }else{
        	wxTagUser=new WxTagUser();
        }
        return getPathView(request).addObject("wxTagUser",wxTagUser);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String id=RequestUtil.getString(request, "id");
    	String pubId=RequestUtil.getString(request, "pubId");
    	WxTag wxTag=null;
    	if(StringUtils.isNotEmpty(id)){
    		String access_token=wxPubAppManager.getAccessToken(pubId);
    		String tagStr=TagUtil.getTag(access_token);
    		JSONObject jsonObject=JSONObject.fromObject(tagStr);//
    		JSONArray tags=(JSONArray) jsonObject.get("tags");
    		List<WxTag> wxTags=(List<WxTag>) JSONArray.toCollection(tags, WxTag.class);
    		int wxTagSize=wxTags.size();
			for (int i = 0; i < wxTagSize; i++) {
				if(wxTags.get(i).getId().equals(id)){
					wxTag=wxTags.get(i);
				}
			}
    	}else{
    		wxTag=new WxTag();
    	}
    	return getPathView(request).addObject("wxTag",wxTag).addObject("pubId", pubId);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxTagUserManager;
	}

}
