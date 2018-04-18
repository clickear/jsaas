
package com.redxun.oa.info.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsColumnDef;
import com.redxun.oa.info.manager.InsColumnDefManager;
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
 * ins_column_def 管理
 * @author mansan
 */
@Controller
@RequestMapping("/oa/info/insColumnDef/")
public class InsColumnDefFormController extends BaseFormController {

    @Resource
    private InsColumnDefManager insColumnDefManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("insColumnDef")
    public InsColumnDef processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        InsColumnDef insColumnDef = null;
        if (StringUtils.isNotEmpty(id)) {
            insColumnDef = insColumnDefManager.get(id);
        } else {
            insColumnDef = new InsColumnDef();
        }

        return insColumnDef;
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
    public JsonResult save(HttpServletRequest request, @ModelAttribute("insColumnDef") @Valid InsColumnDef insColumnDef, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(insColumnDef.getColId())) {
            insColumnDef.setColId(IdUtil.getId());
            insColumnDefManager.create(insColumnDef);
            msg = getMessage("insColumnDef.created", new Object[]{insColumnDef.getIdentifyLabel()}, "栏目成功创建!");
        } else {
            insColumnDefManager.update(insColumnDef);
            msg = getMessage("insColumnDef.updated", new Object[]{insColumnDef.getIdentifyLabel()}, "栏目成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

