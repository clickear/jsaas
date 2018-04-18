package com.redxun.oa.calendar.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.oa.calendar.entity.WorkTimeBlock;
import com.redxun.oa.calendar.manager.WorkTimeBlockManager;

/**
 * [WorkTimeBlock]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/calendar/workTimeBlock/")
public class WorkTimeBlockController extends BaseListController{
    @Resource
    WorkTimeBlockManager workTimeBlockManager;
   
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
                workTimeBlockManager.delete(id);
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
        WorkTimeBlock workTimeBlock=null;
        if(StringUtils.isNotEmpty(pkId)){
           workTimeBlock=workTimeBlockManager.get(pkId);
        }else{
        	workTimeBlock=new WorkTimeBlock();
        }
        return getPathView(request).addObject("workTimeBlock",workTimeBlock);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	WorkTimeBlock workTimeBlock=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		workTimeBlock=workTimeBlockManager.get(pkId);
    		if("true".equals(forCopy)){
    			workTimeBlock.setSettingId(null);
    		}
    	}else{
    		workTimeBlock=new WorkTimeBlock();
    	}
    	return getPathView(request).addObject("workTimeBlock",workTimeBlock);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return workTimeBlockManager;
	}
	
	@RequestMapping("getAllWorkTimeBlock")
	@ResponseBody
	public List<WorkTimeBlock> getAllWorkTimeBlock(HttpServletRequest request,HttpServletResponse response){
		String tenantId=ContextUtil.getCurrentTenantId();
		List<WorkTimeBlock> workTimeBlocks=workTimeBlockManager.getAllByTenantId(tenantId);
		return workTimeBlocks;
	}

	
	@RequestMapping("getTimeBlock")
	@ResponseBody
	public JSONObject getTimeBlock(HttpServletRequest request,HttpServletResponse response){
		String blockId=request.getParameter("blockId");
		JSONObject jsonObject=new JSONObject();
		if(StringUtils.isNotBlank(blockId)){
			WorkTimeBlock workTimeBlock=workTimeBlockManager.get(blockId);
			 jsonObject.put("json", workTimeBlock.getTimeIntervals());
			 jsonObject.put("name", workTimeBlock.getSettingName());	
			 jsonObject.put("blockId", blockId);
			 jsonObject.put("success", true);
		}else{
			jsonObject.put("success", false);
		}
		 return jsonObject;
		
	}
}
