package com.redxun.oa.product.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.product.entity.OaProductDefPara;
import com.redxun.oa.product.manager.OaProductDefParaManager;

/**
 * [OaProductDefPara]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaProductDefPara/")
public class OaProductDefParaController extends BaseListController{
    @Resource
    OaProductDefParaManager oaProductDefParaManager;
   
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
                oaProductDefParaManager.delete(id);
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
        OaProductDefPara oaProductDefPara=null;
        if(StringUtils.isNotEmpty(pkId)){
           oaProductDefPara=oaProductDefParaManager.get(pkId);
        }else{
        	oaProductDefPara=new OaProductDefPara();
        }
        return getPathView(request).addObject("oaProductDefPara",oaProductDefPara);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaProductDefPara oaProductDefPara=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaProductDefPara=oaProductDefParaManager.get(pkId);
    		if("true".equals(forCopy)){
    			oaProductDefPara.setId(null);
    		}
    	}else{
    		oaProductDefPara=new OaProductDefPara();
    	}
    	return getPathView(request).addObject("oaProductDefPara",oaProductDefPara);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaProductDefParaManager;
	}

}
