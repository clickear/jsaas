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

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.sys.core.entity.SysTemplate;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.manager.SysTemplateManager;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * 系统模板管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysTemplate/")
public class SysTemplateController extends TenantListController{
    @Resource
    SysTemplateManager sysTemplateManager;
    @Resource
    SysTreeManager sysTreeManager;
    
    /**
     * 取得分类下的模板列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getByCatKey")
    @ResponseBody
    public List<SysTemplate> getByCatKey(HttpServletRequest request,HttpServletResponse response){
    	String catKey=request.getParameter("catKey");
    	return sysTemplateManager.getByCatKey(catKey,ContextUtil.getCurrentTenantId());
    }
    
    @Override
    protected QueryFilter getQueryFilter(HttpServletRequest request) {
    	QueryFilter queryFilter=super.getQueryFilter(request);
		//查找分类下的模型
		String treeId=request.getParameter("treeId");
		if(StringUtils.isNotEmpty(treeId)){
			SysTree sysTree=sysTreeManager.get(treeId);
			if(sysTree!=null){
				queryFilter.addLeftLikeFieldParam("sysTree.path", sysTree.getPath());
			}
		}
		return queryFilter;
    }
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
            	SysTemplate sysTemplate=sysTemplateManager.get(id);
            	//系统缺省的不允许删除
            	if(sysTemplate!=null && !MBoolean.YES.name().equals(sysTemplate.getIsDefault())){
            		sysTemplateManager.delete(id);
            	}
                
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
        SysTemplate sysTemplate=null;
        if(StringUtils.isNotEmpty(pkId)){
           sysTemplate=sysTemplateManager.get(pkId);
        }else{
        	sysTemplate=new SysTemplate();
        }
        return getPathView(request).addObject("sysTemplate",sysTemplate);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysTemplate sysTemplate=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysTemplate=sysTemplateManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysTemplate.setTempId(null);
    		}
    	}else{
    		sysTemplate=new SysTemplate();
    	}
    	return getPathView(request).addObject("sysTemplate",sysTemplate);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysTemplateManager;
	}

}
