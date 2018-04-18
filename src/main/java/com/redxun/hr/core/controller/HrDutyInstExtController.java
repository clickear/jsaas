package com.redxun.hr.core.controller;

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
import com.redxun.hr.core.entity.HrDutyInstExt;
import com.redxun.hr.core.manager.HrDutyInstExtManager;

/**
 * [HrDutyInstExt]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutyInstExt/")
public class HrDutyInstExtController extends BaseListController{
    @Resource
    HrDutyInstExtManager hrDutyInstExtManager;
   
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
                hrDutyInstExtManager.delete(id);
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
        HrDutyInstExt hrDutyInstExt=null;
        if(StringUtils.isNotEmpty(pkId)){
           hrDutyInstExt=hrDutyInstExtManager.get(pkId);
        }else{
        	hrDutyInstExt=new HrDutyInstExt();
        }
        return getPathView(request).addObject("hrDutyInstExt",hrDutyInstExt);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	HrDutyInstExt hrDutyInstExt=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		hrDutyInstExt=hrDutyInstExtManager.get(pkId);
    		if("true".equals(forCopy)){
    			hrDutyInstExt.setExtId(null);
    		}
    	}else{
    		hrDutyInstExt=new HrDutyInstExt();
    	}
    	return getPathView(request).addObject("hrDutyInstExt",hrDutyInstExt);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return hrDutyInstExtManager;
	}

}
