package com.redxun.kms.core.controller;

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
import com.redxun.kms.core.entity.KdDocFav;
import com.redxun.kms.core.manager.KdDocFavManager;

/**
 * [KdDocFav]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocFav/")
public class KdDocFavController extends BaseListController{
    @Resource
    KdDocFavManager kdDocFavManager;
   
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
                kdDocFavManager.delete(id);
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
        KdDocFav kdDocFav=null;
        if(StringUtils.isNotEmpty(pkId)){
           kdDocFav=kdDocFavManager.get(pkId);
        }else{
        	kdDocFav=new KdDocFav();
        }
        return getPathView(request).addObject("kdDocFav",kdDocFav);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	KdDocFav kdDocFav=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		kdDocFav=kdDocFavManager.get(pkId);
    		if("true".equals(forCopy)){
    			kdDocFav.setFavId(null);
    		}
    	}else{
    		kdDocFav=new KdDocFav();
    	}
    	return getPathView(request).addObject("kdDocFav",kdDocFav);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return kdDocFavManager;
	}

}
