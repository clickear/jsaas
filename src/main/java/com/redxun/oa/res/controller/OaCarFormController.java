package com.redxun.oa.res.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.res.entity.OaCar;
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
 * [OaCar]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaCar/")
public class OaCarFormController extends BaseFormController {

    @Resource
    private OaCarManager oaCarManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaCar")
    public OaCar processForm(HttpServletRequest request) {
        String carId = request.getParameter("carId");
        OaCar oaCar = null;
        if (StringUtils.isNotEmpty(carId)) {
            oaCar = oaCarManager.get(carId);
        } else {
            oaCar = new OaCar();
        }

        return oaCar;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaCar
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaCar") @Valid OaCar oaCar, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(oaCar.getCarId())) {
            oaCar.setCarId(idGenerator.getSID());
            oaCarManager.create(oaCar);
            msg = getMessage("oaCar.created", new Object[]{oaCar.getName()}, "车辆成功创建!");
        } else {
            oaCarManager.update(oaCar);
            msg = getMessage("oaCar.updated", new Object[]{oaCar.getName()}, "车辆成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

