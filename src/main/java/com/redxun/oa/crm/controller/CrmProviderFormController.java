package com.redxun.oa.crm.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.crm.entity.CrmProvider;
import com.redxun.oa.crm.manager.CrmProviderManager;
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
 * [CrmProvider]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/crm/crmProvider/")
public class CrmProviderFormController extends BaseFormController {

    @Resource
    private CrmProviderManager crmProviderManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("crmProvider")
    public CrmProvider processForm(HttpServletRequest request) {
        String proId = request.getParameter("proId");
        CrmProvider crmProvider = null;
        if (StringUtils.isNotEmpty(proId)) {
            crmProvider = crmProviderManager.get(proId);
        } else {
            crmProvider = new CrmProvider();
        }

        return crmProvider;
    }
    /**
     * 保存实体数据
     * @param request
     * @param crmProvider
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("crmProvider") @Valid CrmProvider crmProvider, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(crmProvider.getProId())) {
            crmProvider.setProId(idGenerator.getSID());
            crmProviderManager.create(crmProvider);
            msg = getMessage("crmProvider.created", new Object[]{crmProvider.getIdentifyLabel()}, "[CrmProvider]成功创建!");
        } else {
            crmProviderManager.update(crmProvider);
            msg = getMessage("crmProvider.updated", new Object[]{crmProvider.getIdentifyLabel()}, "[CrmProvider]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

