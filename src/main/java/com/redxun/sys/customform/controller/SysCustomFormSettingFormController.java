
package com.redxun.sys.customform.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.customform.entity.SysCustomFormSetting;
import com.redxun.sys.customform.manager.SysCustomFormSettingManager;

/**
 * 自定义表单配置设定 管理
 * @author mansan
 */
@Controller
@RequestMapping("/sys/customform/sysCustomFormSetting/")
public class SysCustomFormSettingFormController extends BaseFormController {

    @Resource
    private SysCustomFormSettingManager sysCustomFormSettingManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysCustomFormSetting")
    public SysCustomFormSetting processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        SysCustomFormSetting sysCustomFormSetting = null;
        if (StringUtils.isNotEmpty(id)) {
            sysCustomFormSetting = sysCustomFormSettingManager.get(id);
        } else {
            sysCustomFormSetting = new SysCustomFormSetting();
        }

        return sysCustomFormSetting;
    }
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request,@RequestBody   SysCustomFormSetting sysCustomFormSetting, BindingResult result) {
    	boolean rtn= sysCustomFormSettingManager.isAliasExist(sysCustomFormSetting);
    	if(rtn){
    		return new JsonResult(false, "表单设定已经存在!");
    	}
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysCustomFormSetting.getId())) {
            sysCustomFormSetting.setId(IdUtil.getId());
            sysCustomFormSettingManager.create(sysCustomFormSetting);
            msg = getMessage("sysCustomFormSetting.created", new Object[]{sysCustomFormSetting.getIdentifyLabel()}, "[自定义表单配置设定]成功创建!");
        } else {
            sysCustomFormSettingManager.update(sysCustomFormSetting);
            msg = getMessage("sysCustomFormSetting.updated", new Object[]{sysCustomFormSetting.getIdentifyLabel()}, "[自定义表单配置设定]成功更新!");
        }
        return new JsonResult(true, msg);
    }
}

