package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocRead;
import com.redxun.kms.core.manager.KdDocReadManager;
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
 * [KdDocRead]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocRead/")
public class KdDocReadFormController extends BaseFormController {

    @Resource
    private KdDocReadManager kdDocReadManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocRead")
    public KdDocRead processForm(HttpServletRequest request) {
        String readId = request.getParameter("readId");
        KdDocRead kdDocRead = null;
        if (StringUtils.isNotEmpty(readId)) {
            kdDocRead = kdDocReadManager.get(readId);
        } else {
            kdDocRead = new KdDocRead();
        }

        return kdDocRead;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocRead
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocRead") @Valid KdDocRead kdDocRead, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocRead.getReadId())) {
            kdDocRead.setReadId(idGenerator.getSID());
            kdDocReadManager.create(kdDocRead);
            msg = getMessage("kdDocRead.created", new Object[]{kdDocRead.getIdentifyLabel()}, "[KdDocRead]成功创建!");
        } else {
            kdDocReadManager.update(kdDocRead);
            msg = getMessage("kdDocRead.updated", new Object[]{kdDocRead.getIdentifyLabel()}, "[KdDocRead]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

