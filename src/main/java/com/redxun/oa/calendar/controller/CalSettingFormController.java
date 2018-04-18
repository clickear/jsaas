package com.redxun.oa.calendar.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.calendar.entity.CalSetting;
import com.redxun.oa.calendar.manager.CalSettingManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * [CalSetting]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/calendar/calSetting/")
public class CalSettingFormController extends BaseFormController {

    @Resource
    private CalSettingManager calSettingManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("calSetting")
    public CalSetting processForm(HttpServletRequest request) {
        String settingId = request.getParameter("settingId");
        CalSetting calSetting = null;
        if (StringUtils.isNotEmpty(settingId)) {
            calSetting = calSettingManager.get(settingId);
        } else {
            calSetting = new CalSetting();
        }

        return calSetting;
    }
    /**
     * 保存实体数据
     * @param request
     * @param calSetting
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("calSetting") @Valid CalSetting calSetting, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(calSetting.getSettingId())) {
            calSetting.setSettingId(idGenerator.getSID());
            if(calSettingManager.getByName(calSetting.getCalName(), ContextUtil.getCurrentTenantId())==null){
            	CalSetting calSetting2=calSettingManager.getDefault();
            	if("YES".equals(calSetting.getIsCommon())){
            		if(calSetting2==null){
                    	calSettingManager.create(calSetting);	
                       	 msg = "日历设定创建成功";
                    	}else{
                    		 msg = "日历设定创建失败,已经存在默认";
                    	}
            	}else{
            		calSettingManager.create(calSetting);	
                  	 msg = "日历设定创建成功";
            	}
            }else{
            	 msg = "日历设定创建失败,名字不能重复";
            }
        } else {
        	CalSetting calSetting2=calSettingManager.getDefault();
        	if("YES".equals(calSetting.getIsCommon())){
        		if(calSetting2==null){
        			calSettingManager.update(calSetting);
        			msg = "日历设定更新成功";
                	}else if(calSetting.getSettingId().equals(calSetting2.getSettingId())){
                			calSettingManager.update(calSetting);
                			msg = "日历设定更新成功";
                		}else{
                			msg = "日历设定创建失败,已经存在默认";
                		}
        	}else{
        		calSettingManager.update(calSetting);
              	 msg = "日历设定更新成功";
        	}
        	
            
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

