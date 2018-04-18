package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.kms.core.manager.KdDocRightManager;
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
 * [KdDocRight]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocRight/")
public class KdDocRightFormController extends BaseFormController {

    @Resource
    private KdDocRightManager kdDocRightManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocRight")
    public KdDocRight processForm(HttpServletRequest request) {
        String rightId = request.getParameter("rightId");
        KdDocRight kdDocRight = null;
        if (StringUtils.isNotEmpty(rightId)) {
            kdDocRight = kdDocRightManager.get(rightId);
        } else {
            kdDocRight = new KdDocRight();
        }

        return kdDocRight;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocRight
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocRight") @Valid KdDocRight kdDocRight, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocRight.getRightId())) {
            kdDocRight.setRightId(idGenerator.getSID());
            kdDocRightManager.create(kdDocRight);
            msg = getMessage("kdDocRight.created", new Object[]{kdDocRight.getIdentifyLabel()}, "[KdDocRight]成功创建!");
        } else {
            kdDocRightManager.update(kdDocRight);
            msg = getMessage("kdDocRight.updated", new Object[]{kdDocRight.getIdentifyLabel()}, "[KdDocRight]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

