
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
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.wx.ent.entity.WxEntCorp;
import com.redxun.wx.ent.manager.WxEntCorpManager;

/**
 * 微信企业配置控制器
 * @author mansan
 */
@Controller
@RequestMapping("/wx/ent/wxEntCorp/")
public class WxEntCorpController extends BaseMybatisListController{
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
                wxEntCorpManager.delete(id);
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
        WxEntCorp wxEntCorp=null;
        if(StringUtils.isNotEmpty(pkId)){
           wxEntCorp=wxEntCorpManager.get(pkId);
        }else{
        	wxEntCorp=new WxEntCorp();
        }
        return getPathView(request).addObject("wxEntCorp",wxEntCorp);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String tenantId=ContextUtil.getCurrentTenantId();
    	//复制添加
    	WxEntCorp wxEntCorp=wxEntCorpManager.getByTenantId(tenantId);
    	if(wxEntCorp==null){
    		wxEntCorp=new WxEntCorp();
    	}
    	return getPathView(request).addObject("wxEntCorp",wxEntCorp);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return wxEntCorpManager;
	}

}
