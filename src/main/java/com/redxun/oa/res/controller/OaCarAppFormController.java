package com.redxun.oa.res.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.res.entity.OaCar;
import com.redxun.oa.res.entity.OaCarApp;
import com.redxun.oa.res.manager.OaCarAppManager;
import com.redxun.oa.res.manager.OaCarManager;

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
 * [OaCarApp]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaCarApp/")
public class OaCarAppFormController extends BaseFormController {

    @Resource
    private OaCarAppManager oaCarAppManager;
    @Resource
    OaCarManager oaCarManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaCarApp")
    public OaCarApp processForm(HttpServletRequest request) {
        String appId = request.getParameter("appId");
        OaCarApp oaCarApp = null;
        if (StringUtils.isNotEmpty(appId)) {
            oaCarApp = oaCarAppManager.get(appId);
        } else {
            oaCarApp = new OaCarApp();
        }

        return oaCarApp;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaCarApp
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaCarApp") @Valid OaCarApp oaCarApp, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String carId=request.getParameter("carName");
        String msg = null;
        if (StringUtils.isEmpty(oaCarApp.getAppId())) {
        	OaCar oaCar=oaCarManager.get(carId);
            oaCarApp.setAppId(idGenerator.getSID());
            oaCarApp.setCarName(oaCar.getName());
            oaCarApp.setOaCar(oaCar);
            
            oaCarAppManager.create(oaCarApp);
            msg = getMessage("oaCarApp.created", new Object[]{oaCarApp.getIdentifyLabel()}, "车辆申请成功创建!");
        } else {
        	OaCar oaCar=oaCarManager.get(carId);
        	oaCarApp.setOaCar(oaCar);
        	oaCarApp.setCarName(oaCar.getName());
            oaCarAppManager.update(oaCarApp);
            msg = getMessage("oaCarApp.updated", new Object[]{oaCarApp.getIdentifyLabel()}, "车辆申请成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

