package com.redxun.oa.pro.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.pro.entity.ProWorkAtt;
import com.redxun.oa.pro.manager.ProWorkAttManager;

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
 * 动态关注管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/pro/proWorkAtt/")
public class ProWorkAttFormController extends BaseFormController {

    @Resource
    private ProWorkAttManager proWorkAttManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("proWorkAtt")
    public ProWorkAtt processForm(HttpServletRequest request) {
        String attId = request.getParameter("attId");
        ProWorkAtt proWorkAtt = null;
        if (StringUtils.isNotEmpty(attId)) {
            proWorkAtt = proWorkAttManager.get(attId);
        } else {
            proWorkAtt = new ProWorkAtt();
        }

        return proWorkAtt;
    }
    /**
     * 保存实体数据
     * @param request
     * @param proWorkAtt
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("proWorkAtt") @Valid ProWorkAtt proWorkAtt, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(proWorkAtt.getAttId())) {
            proWorkAtt.setAttId(idGenerator.getSID());
            proWorkAttManager.create(proWorkAtt);
            msg = getMessage("proWorkAtt.created", new Object[]{proWorkAtt.getIdentifyLabel()}, "动态关注成功创建!");
        } else {
            proWorkAttManager.update(proWorkAtt);
            msg = getMessage("proWorkAtt.updated", new Object[]{proWorkAtt.getIdentifyLabel()}, "动态关注成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

