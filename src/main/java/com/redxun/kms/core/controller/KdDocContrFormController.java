package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocContr;
import com.redxun.kms.core.manager.KdDocContrManager;
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
 * [KdDocContr]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocContr/")
public class KdDocContrFormController extends BaseFormController {

    @Resource
    private KdDocContrManager kdDocContrManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocContr")
    public KdDocContr processForm(HttpServletRequest request) {
        String contId = request.getParameter("contId");
        KdDocContr kdDocContr = null;
        if (StringUtils.isNotEmpty(contId)) {
            kdDocContr = kdDocContrManager.get(contId);
        } else {
            kdDocContr = new KdDocContr();
        }

        return kdDocContr;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocContr
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocContr") @Valid KdDocContr kdDocContr, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocContr.getContId())) {
            kdDocContr.setContId(idGenerator.getSID());
            kdDocContrManager.create(kdDocContr);
            msg = getMessage("kdDocContr.created", new Object[]{kdDocContr.getIdentifyLabel()}, "[KdDocContr]成功创建!");
        } else {
            kdDocContrManager.update(kdDocContr);
            msg = getMessage("kdDocContr.updated", new Object[]{kdDocContr.getIdentifyLabel()}, "[KdDocContr]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

