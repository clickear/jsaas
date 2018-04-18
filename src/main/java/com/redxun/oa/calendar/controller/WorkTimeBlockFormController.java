package com.redxun.oa.calendar.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.calendar.entity.CalSetting;
import com.redxun.oa.calendar.entity.WorkTimeBlock;
import com.redxun.oa.calendar.manager.CalSettingManager;
import com.redxun.oa.calendar.manager.WorkTimeBlockManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * [WorkTimeBlock]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/calendar/workTimeBlock/")
public class WorkTimeBlockFormController extends BaseFormController {

    @Resource
    private WorkTimeBlockManager workTimeBlockManager;
    @Resource
    private CalSettingManager calSettingManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("workTimeBlock")
    public WorkTimeBlock processForm(HttpServletRequest request) {
        String settingId = request.getParameter("settingId");
        WorkTimeBlock workTimeBlock = null;
        if (StringUtils.isNotEmpty(settingId)) {
            workTimeBlock = workTimeBlockManager.get(settingId);
        } else {
            workTimeBlock = new WorkTimeBlock();
        }

        return workTimeBlock;
    }
    /**
     * 保存实体数据
     * @param request
     * @param workTimeBlock
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("workTimeBlock") @Valid WorkTimeBlock workTimeBlock, BindingResult result) {
    	Enumeration pNames=request.getParameterNames();
    	Map<String, String> map=new HashMap<String, String>();
    	while(pNames.hasMoreElements()){
    	    String name=(String)pNames.nextElement();
    	    String value=request.getParameter(name);
    	    if(!"settingName".equals(name)&&!"isUpdate".equals(name)){
    	    	 map.put(name, value);
    	    }else if(workTimeBlockManager.getWorkTimeBlockByName(name)!=null){
	    	return new JsonResult(false,"重复的名字");	
	    	}
    	}
    	String isUpdate=request.getParameter("isUpdate");
    	JSONObject jsonObject=JSONObject.fromObject(map);
    	String timeIntervals=jsonObject.toString();
    	workTimeBlock.setTimeIntervals(timeIntervals);;
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isBlank(isUpdate)) {//isUpdate
            workTimeBlock.setSettingId(idGenerator.getSID());
            if(workTimeBlockManager.getWorkTimeBlockByName(workTimeBlock.getSettingName())==null){
            	workTimeBlockManager.create(workTimeBlock);
            	 msg = "日历成功创建";
            }else{
            	 msg = "日历名字已经重复";
            }
            
           
        } else {
        	WorkTimeBlock updateTimeBlock=workTimeBlockManager.get(isUpdate);
        	updateTimeBlock.setSettingName(workTimeBlock.getSettingName());
        	updateTimeBlock.setTimeIntervals(workTimeBlock.getTimeIntervals());
        	updateTimeBlock.setUpdateTime(new Date());
        	updateTimeBlock.setUpdateBy(ContextUtil.getCurrentUserId());
            workTimeBlockManager.update(updateTimeBlock);
            msg = "日历成功更新";
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

