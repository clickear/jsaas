package com.redxun.oa.doc.controller;

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
import com.redxun.oa.doc.entity.DocRight;
import com.redxun.oa.doc.manager.DocRightManager;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * 文件权限管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/doc/docRight/")
public class DocRightController extends BaseListController{
    @Resource
    DocRightManager docRightManager;
   
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
                docRightManager.delete(id);
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
        DocRight docRight=null;
        if(StringUtils.isNotEmpty(pkId)){
           docRight=docRightManager.get(pkId);
        }else{
        	docRight=new DocRight();
        }
        return getPathView(request).addObject("docRight",docRight);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	DocRight docRight=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		docRight=docRightManager.get(pkId);
    		if("true".equals(forCopy)){
    			docRight.setRightId(null);
    		}
    	}else{
    		docRight=new DocRight();
    	}
    	return getPathView(request).addObject("docRight",docRight);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return docRightManager;
	}

}
