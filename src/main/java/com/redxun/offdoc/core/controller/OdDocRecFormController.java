package com.redxun.offdoc.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.offdoc.core.entity.OdDocRec;
import com.redxun.offdoc.core.manager.OdDocRecManager;
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
 * [OdDocRec]管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/offdoc/core/odDocRec/")
public class OdDocRecFormController extends BaseFormController {

    @Resource
    private OdDocRecManager odDocRecManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("odDocRec")
    public OdDocRec processForm(HttpServletRequest request) {
        String recId = request.getParameter("recId");
        OdDocRec odDocRec = null;
        if (StringUtils.isNotEmpty(recId)) {
            odDocRec = odDocRecManager.get(recId);
        } else {
            odDocRec = new OdDocRec();
        }

        return odDocRec;
    }
    /**
     * 保存实体数据
     * @param request
     * @param odDocRec
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("odDocRec") @Valid OdDocRec odDocRec, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(odDocRec.getRecId())) {
            odDocRec.setRecId(idGenerator.getSID());
            odDocRecManager.create(odDocRec);
            msg = getMessage("odDocRec.created", new Object[]{odDocRec.getDocId()}, "收文成功!");
        } else {
            odDocRecManager.update(odDocRec);
            msg = getMessage("odDocRec.updated", new Object[]{odDocRec.getDocId()}, "收文成功!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

