package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsPortCol;
import com.redxun.oa.info.manager.InsPortColManager;
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
 * [InsPortCol]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insPortCol/")
public class InsPortColFormController extends BaseFormController {

    @Resource
    private InsPortColManager insPortColManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insPortCol")
    public InsPortCol processForm(HttpServletRequest request) {
        String confId = request.getParameter("confId");
        InsPortCol insPortCol = null;
        if (StringUtils.isNotEmpty(confId)) {
            insPortCol = insPortColManager.get(confId);
        } else {
            insPortCol = new InsPortCol();
        }

        return insPortCol;
    }
    /**
     * 保存实体数据
     * @param request
     * @param insPortCol
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insPortCol") @Valid InsPortCol insPortCol, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insPortCol.getConfId())) {
            insPortCol.setConfId(idGenerator.getSID());
            insPortColManager.create(insPortCol);
            msg = getMessage("insPortCol.created", new Object[]{insPortCol.getIdentifyLabel()}, "成功创建!");
        } else {
            insPortColManager.update(insPortCol);
            msg = getMessage("insPortCol.updated", new Object[]{insPortCol.getIdentifyLabel()}, "成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

