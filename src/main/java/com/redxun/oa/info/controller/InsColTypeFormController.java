package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsColType;
import com.redxun.oa.info.manager.InsColTypeManager;
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
 * [InsColType]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insColType/")
public class InsColTypeFormController extends BaseFormController {

    @Resource
    private InsColTypeManager insColTypeManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insColType")
    public InsColType processForm(HttpServletRequest request) {
        String typeId = request.getParameter("typeId");
        InsColType insColType = null;
        if (StringUtils.isNotEmpty(typeId)) {
            insColType = insColTypeManager.get(typeId);
        } else {
            insColType = new InsColType();
        }

        return insColType;
    }
    /**
     * 保存实体数据
     * @param request
     * @param insColType
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insColType") @Valid InsColType insColType, BindingResult result) {
    	
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        String templateName=request.getParameter("tempId_name");
        insColType.setTempName(templateName);
        if (StringUtils.isEmpty(insColType.getTypeId())) {
            insColType.setTypeId(idGenerator.getSID());
            insColTypeManager.create(insColType);
            msg = getMessage("insColType.created", new Object[]{insColType.getIdentifyLabel()}, "栏目分类成功创建!");
        } else {
            insColTypeManager.update(insColType);
            msg = getMessage("insColType.updated", new Object[]{insColType.getIdentifyLabel()}, "栏目分类成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
    
    
}

