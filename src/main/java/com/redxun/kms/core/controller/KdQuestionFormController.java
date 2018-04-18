package com.redxun.kms.core.controller;

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

import com.redxun.core.json.JsonResult;
import com.redxun.kms.core.entity.KdQuestion;
import com.redxun.kms.core.manager.KdQuestionManager;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * [KdQuestion]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdQuestion/")
public class KdQuestionFormController extends BaseFormController {

    @Resource
    private KdQuestionManager kdQuestionManager;
    
    @Resource
    SysTreeManager sysTreeManager;
    
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdQuestion")
    public KdQuestion processForm(HttpServletRequest request) {
        String queId = request.getParameter("queId");
        KdQuestion kdQuestion = null;
        if (StringUtils.isNotEmpty(queId)) {
            kdQuestion = kdQuestionManager.get(queId);
        } else {
            kdQuestion = new KdQuestion();
        }

        return kdQuestion;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdQuestion
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdQuestion") @Valid KdQuestion kdQuestion, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdQuestion.getQueId())) {
            kdQuestion.setQueId(idGenerator.getSID());
            kdQuestion.setStatus(KdQuestion.STATUS_UNSOLVED);
            kdQuestion.setReplyCounts(0);
            kdQuestion.setViewTimes(0);
            if(StringUtils.isEmpty(kdQuestion.getFileIds()))
            	kdQuestion.setFileIds("0");
            kdQuestionManager.create(kdQuestion);
            msg = getMessage("kdQuestion.created", new Object[]{kdQuestion.getIdentifyLabel()}, "[KdQuestion]成功创建!");
        } else {
        	String treeId=request.getParameter("treeId");
        	kdQuestionManager.detach(kdQuestion.getSysTree());
        	kdQuestion.setSysTree(sysTreeManager.get(treeId));
            kdQuestionManager.update(kdQuestion);
            msg = getMessage("kdQuestion.updated", new Object[]{kdQuestion.getIdentifyLabel()}, "[KdQuestion]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

