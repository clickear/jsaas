package com.redxun.oa.product.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.product.entity.OaProductDefParaValue;
import com.redxun.oa.product.manager.OaProductDefParaValueManager;
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
 * [OaProductDefParaValue]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaProductDefParaValue/")
public class OaProductDefParaValueFormController extends BaseFormController {

    @Resource
    private OaProductDefParaValueManager oaProductDefParaValueManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaProductDefParaValue")
    public OaProductDefParaValue processForm(HttpServletRequest request) {
        String valueId = request.getParameter("valueId");
        OaProductDefParaValue oaProductDefParaValue = null;
        if (StringUtils.isNotEmpty(valueId)) {
            oaProductDefParaValue = oaProductDefParaValueManager.get(valueId);
        } else {
            oaProductDefParaValue = new OaProductDefParaValue();
        }

        return oaProductDefParaValue;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaProductDefParaValue
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaProductDefParaValue") @Valid OaProductDefParaValue oaProductDefParaValue, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(oaProductDefParaValue.getValueId())) {
            oaProductDefParaValue.setValueId(idGenerator.getSID());
            oaProductDefParaValueManager.create(oaProductDefParaValue);
            msg = getMessage("oaProductDefParaValue.created", new Object[]{oaProductDefParaValue.getName()}, "产品属性成功创建!");
        } else {
            oaProductDefParaValueManager.update(oaProductDefParaValue);
            msg = getMessage("oaProductDefParaValue.updated", new Object[]{oaProductDefParaValue.getName()}, "产品属性成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

