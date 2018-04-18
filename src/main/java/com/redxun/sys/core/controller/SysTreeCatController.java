package com.redxun.sys.core.controller;

import java.util.List;

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
import com.redxun.sys.core.entity.SysTreeCat;
import com.redxun.sys.core.manager.SysTreeCatManager;
import com.redxun.sys.log.LogEnt;

/**
 *系统树分类管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysTreeCat/")
public class SysTreeCatController extends BaseListController{
    @Resource
    SysTreeCatManager sysTreeCatManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
	
	@RequestMapping("allJson")
	@ResponseBody
	public List<SysTreeCat> allJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List catList=sysTreeCatManager.getAll();
		return catList;
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "系统内核", submodule = "系统分类树")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                sysTreeCatManager.delete(id);
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
        SysTreeCat sysTreeCat=null;
        if(StringUtils.isNotBlank(pkId)){
           sysTreeCat=sysTreeCatManager.get(pkId);
        }else{
        	sysTreeCat=new SysTreeCat();
        }
        return getPathView(request).addObject("sysTreeCat",sysTreeCat);
    }
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysTreeCat sysTreeCat=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysTreeCat=sysTreeCatManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysTreeCat.setCatId(null);
    		}
    	}else{
    		sysTreeCat=new SysTreeCat();
    	}
    	return getPathView(request).addObject("sysTreeCat",sysTreeCat);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysTreeCatManager;
	}

}
