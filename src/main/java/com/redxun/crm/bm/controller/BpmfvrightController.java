package com.redxun.crm.bm.controller;

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
import com.redxun.crm.bm.entity.Bpmfvright;
import com.redxun.crm.bm.manager.BpmfvrightManager;

/**
 * [Bpmfvright]管理
 * @author csx
 */
@Controller
@RequestMapping("/crm/bm/bpmfvright/")
public class BpmfvrightController extends BaseListController{
    @Resource
    BpmfvrightManager bpmfvrightManager;
   
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
                bpmfvrightManager.delete(id);
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
        Bpmfvright bpmfvright=null;
        if(StringUtils.isNotEmpty(pkId)){
           bpmfvright=bpmfvrightManager.get(pkId);
        }else{
        	bpmfvright=new Bpmfvright();
        }
        return getPathView(request).addObject("bpmfvright",bpmfvright);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	Bpmfvright bpmfvright=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		bpmfvright=bpmfvrightManager.get(pkId);
    		if("true".equals(forCopy)){
    			bpmfvright.setRightId(null);
    		}
    	}else{
    		bpmfvright=new Bpmfvright();
    	}
    	return getPathView(request).addObject("bpmfvright",bpmfvright);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return bpmfvrightManager;
	}

}
