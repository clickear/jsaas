package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocCmmt;
import com.redxun.kms.core.manager.KdDocCmmtManager;
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
 * [KdDocCmmt]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocCmmt/")
public class KdDocCmmtFormController extends BaseFormController {

    @Resource
    private KdDocCmmtManager kdDocCmmtManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocCmmt")
    public KdDocCmmt processForm(HttpServletRequest request) {
        String commentId = request.getParameter("commentId");
        KdDocCmmt kdDocCmmt = null;
        if (StringUtils.isNotEmpty(commentId)) {
            kdDocCmmt = kdDocCmmtManager.get(commentId);
        } else {
            kdDocCmmt = new KdDocCmmt();
        }

        return kdDocCmmt;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocCmmt
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocCmmt") @Valid KdDocCmmt kdDocCmmt, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocCmmt.getCommentId())) {
            kdDocCmmt.setCommentId(idGenerator.getSID());
            kdDocCmmtManager.create(kdDocCmmt);
            msg = getMessage("kdDocCmmt.created", new Object[]{kdDocCmmt.getIdentifyLabel()}, "[KdDocCmmt]成功创建!");
        } else {
            kdDocCmmtManager.update(kdDocCmmt);
            msg = getMessage("kdDocCmmt.updated", new Object[]{kdDocCmmt.getIdentifyLabel()}, "[KdDocCmmt]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

