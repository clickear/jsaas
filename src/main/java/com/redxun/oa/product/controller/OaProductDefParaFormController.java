package com.redxun.oa.product.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.product.entity.OaProductDefPara;
import com.redxun.oa.product.manager.OaProductDefParaManager;
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
 * [OaProductDefPara]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaProductDefPara/")
public class OaProductDefParaFormController extends BaseFormController {

    @Resource
    private OaProductDefParaManager oaProductDefParaManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaProductDefPara")
    public OaProductDefPara processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        OaProductDefPara oaProductDefPara = null;
        if (StringUtils.isNotEmpty(id)) {
            oaProductDefPara = oaProductDefParaManager.get(id);
        } else {
            oaProductDefPara = new OaProductDefPara();
        }

        return oaProductDefPara;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaProductDefPara
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaProductDefPara") @Valid OaProductDefPara oaProductDefPara, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(oaProductDefPara.getId())) {
            oaProductDefPara.setId(idGenerator.getSID());
            oaProductDefParaManager.create(oaProductDefPara);
            msg = getMessage("oaProductDefPara.created", new Object[]{oaProductDefPara.getIdentifyLabel()}, "[OaProductDefPara]成功创建!");
        } else {
            oaProductDefParaManager.update(oaProductDefPara);
            msg = getMessage("oaProductDefPara.updated", new Object[]{oaProductDefPara.getIdentifyLabel()}, "[OaProductDefPara]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

