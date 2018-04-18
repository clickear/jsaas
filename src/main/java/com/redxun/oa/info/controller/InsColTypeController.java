package com.redxun.oa.info.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.info.entity.InsColType;
import com.redxun.oa.info.manager.InsColTypeManager;

/**
 * [InsColType]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insColType/")
public class InsColTypeController extends TenantListController{
    @Resource
    InsColTypeManager insColTypeManager;
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                insColTypeManager.delete(id);
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
        InsColType insColType=null;
        if(StringUtils.isNotEmpty(pkId)){
           insColType=insColTypeManager.get(pkId);
        }else{
        	insColType=new InsColType();
        }
        return getPathView(request).addObject("insColType",insColType);
    }
    
    /**
     * 在新增Column和编辑Column时,显示所有的类型以便选择
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getAll")
    @ResponseBody
    public List<InsColType> getAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
    		List<InsColType> insColTypes = insColTypeManager.getAll();
    		return insColTypes;
    }
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	InsColType insColType=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		insColType=insColTypeManager.get(pkId);
    		if("true".equals(forCopy)){
    			insColType.setTypeId(null);
    		}
    	}else{
    		insColType=new InsColType();
    	}
    	return getPathView(request).addObject("insColType",insColType);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return insColTypeManager;
	}

}
