package com.redxun.oa.crm.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.crm.entity.CrmProvider;
import com.redxun.oa.crm.manager.CrmProviderManager;

/**
 * [CrmProvider]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/crm/crmProvider/")
public class CrmProviderController extends BaseListController{
    @Resource
    CrmProviderManager crmProviderManager;
    
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                crmProviderManager.deleteCasecade(id);
            }
        }
        return new JsonResult(true,"成功删除！");
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
        String pkId=request.getParameter("pkId");
        CrmProvider crmProvider=null;
        if(StringUtils.isNotEmpty(pkId)){
           crmProvider=crmProviderManager.get(pkId);
        }else{
        	crmProvider=new CrmProvider();
        }
        return getPathView(request).addObject("crmProvider",crmProvider);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	CrmProvider crmProvider=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		crmProvider=crmProviderManager.get(pkId);
    		if("true".equals(forCopy)){
    			crmProvider.setProId(null);
    		}
    	}else{
    		crmProvider=new CrmProvider();
    	}
    	return getPathView(request).addObject("crmProvider",crmProvider);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return crmProviderManager;
	}

}
