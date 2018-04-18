package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdQuestionAnswer;
import com.redxun.kms.core.manager.KdQuestionAnswerManager;
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
 * [KdQuestionAnswer]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdQuestionAnswer/")
public class KdQuestionAnswerFormController extends BaseFormController {

    @Resource
    private KdQuestionAnswerManager kdQuestionAnswerManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdQuestionAnswer")
    public KdQuestionAnswer processForm(HttpServletRequest request) {
        String answerId = request.getParameter("answerId");
        KdQuestionAnswer kdQuestionAnswer = null;
        if (StringUtils.isNotEmpty(answerId)) {
            kdQuestionAnswer = kdQuestionAnswerManager.get(answerId);
        } else {
            kdQuestionAnswer = new KdQuestionAnswer();
        }

        return kdQuestionAnswer;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdQuestionAnswer
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdQuestionAnswer") @Valid KdQuestionAnswer kdQuestionAnswer, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdQuestionAnswer.getAnswerId())) {
            kdQuestionAnswer.setAnswerId(idGenerator.getSID());
            kdQuestionAnswerManager.create(kdQuestionAnswer);
            msg = getMessage("kdQuestionAnswer.created", new Object[]{kdQuestionAnswer.getIdentifyLabel()}, "[KdQuestionAnswer]成功创建!");
        } else {
            kdQuestionAnswerManager.update(kdQuestionAnswer);
            msg = getMessage("kdQuestionAnswer.updated", new Object[]{kdQuestionAnswer.getIdentifyLabel()}, "[KdQuestionAnswer]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

