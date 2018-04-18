package com.redxun.oa.doc.controller;

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
import com.redxun.oa.doc.entity.DocRight;
import com.redxun.oa.doc.manager.DocRightManager;
import com.redxun.saweb.controller.BaseFormController;

/**
 * [DocRight]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/doc/docRight/")
public class DocRightFormController extends BaseFormController {

    @Resource
    private DocRightManager docRightManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("docRight")
    public DocRight processForm(HttpServletRequest request) {
        String rightId = request.getParameter("rightId");
        DocRight docRight = null;
        if (StringUtils.isNotEmpty(rightId)) {
            docRight = docRightManager.get(rightId);
        } else {
            docRight = new DocRight();
        }

        return docRight;
    }
    /**
     * 保存实体数据
     * @param request
     * @param docRight
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("docRight") @Valid DocRight docRight, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(docRight.getRightId())) {
            docRight.setRightId(idGenerator.getSID());
            docRightManager.create(docRight);
            msg = getMessage("docRight.created", new Object[]{docRight.getIdentifyLabel()}, "文件权限成功创建!");
        } else {
            docRightManager.update(docRight);
            msg = getMessage("docRight.updated", new Object[]{docRight.getIdentifyLabel()}, "文件权限成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

