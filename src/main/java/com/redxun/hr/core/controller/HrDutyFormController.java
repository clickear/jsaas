package com.redxun.hr.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.hr.core.entity.HrDuty;
import com.redxun.hr.core.manager.HrDutyManager;

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
 * [HrDuty]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDuty/")
public class HrDutyFormController extends BaseFormController {

    @Resource
    private HrDutyManager hrDutyManager;
    @Resource
    OsUserManager osUserManager;
    @Resource
    OsGroupManager osGroupManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("hrDuty")
    public HrDuty processForm(HttpServletRequest request) {
        String dutyId = request.getParameter("dutyId");
        HrDuty hrDuty = null;
        if (StringUtils.isNotEmpty(dutyId)) {
            hrDuty = hrDutyManager.get(dutyId);
        } else {
            hrDuty = new HrDuty();
        }

        return hrDuty;
    }
    /**
     * 保存实体数据
     * @param request
     * @param hrDuty
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("hrDuty") @Valid HrDuty hrDuty, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String userId=null;
        String groupId=null;

        String userType=request.getParameter("userType");
        if("user".equals(userType)){
        	hrDuty.setGroupId(null);
        	hrDuty.setGroupName(null);
        	userId=request.getParameter("userId");
        }
        if ("department".equals(userType)) {
        	hrDuty.setUserId(null);
        	hrDuty.setUserName(null);
			groupId=request.getParameter("groupId");
		}
        if(StringUtils.isNotEmpty(userId)){
        	String[] userIds=userId.split(",");
        	String tmpUserName="";
        	for (int i = 0; i < userIds.length; i++) {
        		if(i==0){
        			tmpUserName+=osUserManager.get(userIds[i]).getFullname();
        			continue;
        		}
				tmpUserName=tmpUserName+","+osUserManager.get(userIds[i]).getFullname();
			}
        	hrDuty.setUserName(tmpUserName);
        }
        if(StringUtils.isNotEmpty(groupId)){
        	String[] groupIds=groupId.split(",");
        	String tmpGroupName="";
        	for (int i = 0; i < groupIds.length; i++) {
        		if(i==0){
        			tmpGroupName+=osGroupManager.get(groupIds[i]).getName();
        			continue;
        		}
        		tmpGroupName=tmpGroupName+","+osGroupManager.get(groupIds[i]).getName();
			}
        	hrDuty.setGroupName(tmpGroupName);
        }
        String msg = null;
        if (StringUtils.isEmpty(hrDuty.getDutyId())) {
            hrDuty.setDutyId(idGenerator.getSID());
            hrDutyManager.create(hrDuty);
            msg = getMessage("hrDuty.created", new Object[]{""}, "排班成功创建!");
        } else {
            hrDutyManager.update(hrDuty);
            msg = getMessage("hrDuty.updated", new Object[]{""}, "排班成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

