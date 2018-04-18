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
import com.redxun.kms.core.entity.KdUserLevel;
import com.redxun.kms.core.manager.KdUserLevelManager;
import com.redxun.saweb.controller.TenantListController;

/**
 * [KdUserLevel]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdUserLevel/")
public class KdUserLevelController extends TenantListController{
    @Resource
    KdUserLevelManager kdUserLevelManager;
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                kdUserLevelManager.delete(id);
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
        KdUserLevel kdUserLevel=null;
        if(StringUtils.isNotEmpty(pkId)){
           kdUserLevel=kdUserLevelManager.get(pkId);
        }else{
        	kdUserLevel=new KdUserLevel();
        }
        return getPathView(request).addObject("kdUserLevel",kdUserLevel);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	KdUserLevel kdUserLevel=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		kdUserLevel=kdUserLevelManager.get(pkId);
    		if("true".equals(forCopy)){
    			kdUserLevel.setConfId(null);
    		}
    	}else{
    		kdUserLevel=new KdUserLevel();
    	}
    	return getPathView(request).addObject("kdUserLevel",kdUserLevel);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return kdUserLevelManager;
	}

}
