package com.redxun.oa.calendar.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.calendar.entity.CalGrant;
import com.redxun.oa.calendar.entity.CalSetting;
import com.redxun.oa.calendar.manager.CalGrantManager;
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
 * [CalGrant]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/calendar/calGrant/")
public class CalGrantFormController extends BaseFormController {

    @Resource
    private CalGrantManager calGrantManager;
    @Resource
    private CalSettingManager calSettingManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("calGrant")
    public CalGrant processForm(HttpServletRequest request) {
        String grantId = request.getParameter("grantId");
        CalGrant calGrant = null;
        if (StringUtils.isNotEmpty(grantId)) {
            calGrant = calGrantManager.get(grantId);
        } else {
            calGrant = new CalGrant();
        }

        return calGrant;
    }
    /**
     * 保存实体数据
     * @param request
     * @param calGrant
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("calGrant") @Valid CalGrant calGrant, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String settingId=request.getParameter("setting");//calSetting的主键
        CalSetting calSetting=calSettingManager.get(settingId);
        
        
        String msg = null;
        if (StringUtils.isEmpty(calGrant.getGrantId())) {
        	String belongs=calGrant.getBelongWho();
        	String[] belongArray=belongs.split(",");
        	for (int i = 0; i < belongArray.length; i++) {
        		CalGrant grant=new CalGrant();
        		grant.setGrantId(idGenerator.getSID());
                grant.setCalSetting(calSetting);
                grant.setBelongWho(belongArray[i]);
                String type=calGrant.getGrantType();
                grant.setGrantType(type);
                
                if(!calGrantManager.checkIsExist(type,grant.getBelongWho())){
                	calSetting.getCalGrants().add(grant);}
               
			}
        	 msg = "成功分配";
        	calSettingManager.saveOrUpdate(calSetting);	
           
        } else {
        	 String userORgroupId=calGrant.getBelongWho();
             String type=calGrant.getGrantType();
             if(!calGrantManager.checkIsExist(type,userORgroupId)){
           // calSettingManager.saveOrUpdate(calSetting);
             calGrantManager.update(calGrant);
             msg = "日历分配成功更新";
             }else{
            	 msg = "被分配者已经分配过日历";
             }
           
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

