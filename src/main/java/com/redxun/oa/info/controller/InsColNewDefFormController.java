
package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsColNewDef;
import com.redxun.oa.info.manager.InsColNewDefManager;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.redxun.saweb.util.IdUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ins_col_new_def 管理
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insColNewDef/")
public class InsColNewDefFormController extends BaseFormController {

    @Resource
    private InsColNewDefManager insColNewDefManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insColNewDef")
    public InsColNewDef processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsColNewDef insColNewDef = null;
        if (StringUtils.isNotEmpty(id)) {
            insColNewDef = insColNewDefManager.get(id);
        } else {
            insColNewDef = new InsColNewDef();
        }

        return insColNewDef;
    }
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insColNewDef") @Valid InsColNewDef insColNewDef, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insColNewDef.getId())) {
            insColNewDef.setId(IdUtil.getId());
            insColNewDefManager.create(insColNewDef);
            msg = getMessage("insColNewDef.created", new Object[]{insColNewDef.getIdentifyLabel()}, "[ins_col_new_def]成功创建!");
        } else {
            insColNewDefManager.update(insColNewDef);
            msg = getMessage("insColNewDef.updated", new Object[]{insColNewDef.getIdentifyLabel()}, "[ins_col_new_def]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

