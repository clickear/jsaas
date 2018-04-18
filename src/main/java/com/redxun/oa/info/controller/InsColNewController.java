package com.redxun.oa.info.controller;

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
import com.redxun.oa.info.entity.InsColNew;
import com.redxun.oa.info.manager.InsColNewManager;

/**
 * [InsColNew]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insColNew/")
public class InsColNewController extends BaseListController{
    @Resource
    InsColNewManager insColNewManager;
   
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
                insColNewManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 删除某个新闻和栏目的关联,即删除在该栏目下这新闻的显示
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("delNew")
    @ResponseBody
    public JsonResult delNew(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        String colId =request.getParameter("colId");
        if(StringUtils.isNotEmpty(uId)){
        	insColNewManager.delByColIdNewId(colId, uId);
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
        InsColNew insColNew=null;
        if(StringUtils.isNotBlank(pkId)){
           insColNew=insColNewManager.get(pkId);
        }else{
        	insColNew=new InsColNew();
        }
        return getPathView(request).addObject("insColNew",insColNew);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String newId=request.getParameter("pkId");
    	String colId=request.getParameter("colId");
    	//复制添加
    	InsColNew insColNew=null;
    	if(StringUtils.isNotEmpty(newId)){
    		insColNew=insColNewManager.getByColIdNewId(colId, newId);
    	}
    	return getPathView(request).addObject("insColNew",insColNew).addObject("newId", newId).addObject("colId", colId);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return insColNewManager;
	}

}
