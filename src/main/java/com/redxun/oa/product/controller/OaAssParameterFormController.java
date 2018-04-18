package com.redxun.oa.product.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.product.entity.OaAssParameter;
import com.redxun.oa.product.manager.OaAssParameterManager;
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
 * [OaAssParameter]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaAssParameter/")
public class OaAssParameterFormController extends BaseFormController {

    @Resource
    private OaAssParameterManager oaAssParameterManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaAssParameter")
    public OaAssParameter processForm(HttpServletRequest request) {
        String paraId = request.getParameter("paraId");
        OaAssParameter oaAssParameter = null;
        if (StringUtils.isNotEmpty(paraId)) {
            oaAssParameter = oaAssParameterManager.get(paraId);
        } else {
            oaAssParameter = new OaAssParameter();
        }

        return oaAssParameter;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaAssParameter
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaAssParameter") @Valid OaAssParameter oaAssParameter, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(oaAssParameter.getParaId())) {
            oaAssParameter.setParaId(idGenerator.getSID());
            oaAssParameterManager.create(oaAssParameter);
            msg = getMessage("oaAssParameter.created", new Object[]{oaAssParameter.getIdentifyLabel()}, "[OaAssParameter]成功创建!");
        } else {
            oaAssParameterManager.update(oaAssParameter);
            msg = getMessage("oaAssParameter.updated", new Object[]{oaAssParameter.getIdentifyLabel()}, "[OaAssParameter]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

