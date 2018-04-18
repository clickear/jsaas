package com.redxun.oa.company.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.company.entity.OaComRight;
import com.redxun.oa.company.manager.OaComRightManager;
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
 * [OaComRight]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/company/oaComRight/")
public class OaComRightFormController extends BaseFormController {

    @Resource
    private OaComRightManager oaComRightManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaComRight")
    public OaComRight processForm(HttpServletRequest request) {
        String rightId = request.getParameter("rightId");
        OaComRight oaComRight = null;
        if (StringUtils.isNotEmpty(rightId)) {
            oaComRight = oaComRightManager.get(rightId);
        } else {
            oaComRight = new OaComRight();
        }

        return oaComRight;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaComRight
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaComRight") @Valid OaComRight oaComRight, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(oaComRight.getRightId())) {
            oaComRight.setRightId(idGenerator.getSID());
            oaComRightManager.create(oaComRight);
            msg = getMessage("oaComRight.created", new Object[]{oaComRight.getIdentifyLabel()}, "[OaComRight]成功创建!");
        } else {
            oaComRightManager.update(oaComRight);
            msg = getMessage("oaComRight.updated", new Object[]{oaComRight.getIdentifyLabel()}, "[OaComRight]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

