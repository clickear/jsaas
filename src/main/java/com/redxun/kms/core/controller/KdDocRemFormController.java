package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocRem;
import com.redxun.kms.core.manager.KdDocRemManager;
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
 * [KdDocRem]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocRem/")
public class KdDocRemFormController extends BaseFormController {

    @Resource
    private KdDocRemManager kdDocRemManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocRem")
    public KdDocRem processForm(HttpServletRequest request) {
        String remId = request.getParameter("remId");
        KdDocRem kdDocRem = null;
        if (StringUtils.isNotEmpty(remId)) {
            kdDocRem = kdDocRemManager.get(remId);
        } else {
            kdDocRem = new KdDocRem();
        }

        return kdDocRem;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocRem
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocRem") @Valid KdDocRem kdDocRem, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocRem.getRemId())) {
            kdDocRem.setRemId(idGenerator.getSID());
            kdDocRemManager.create(kdDocRem);
            msg = getMessage("kdDocRem.created", new Object[]{kdDocRem.getIdentifyLabel()}, "[KdDocRem]成功创建!");
        } else {
            kdDocRemManager.update(kdDocRem);
            msg = getMessage("kdDocRem.updated", new Object[]{kdDocRem.getIdentifyLabel()}, "[KdDocRem]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

