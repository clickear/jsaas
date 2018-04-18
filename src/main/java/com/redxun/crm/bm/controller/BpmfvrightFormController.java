package com.redxun.crm.bm.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.crm.bm.entity.Bpmfvright;
import com.redxun.crm.bm.manager.BpmfvrightManager;
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
 * [Bpmfvright]管理
 * @author csx
 */
@Controller
@RequestMapping("/crm/bm/bpmfvright/")
public class BpmfvrightFormController extends BaseFormController {

    @Resource
    private BpmfvrightManager bpmfvrightManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("bpmfvright")
    public Bpmfvright processForm(HttpServletRequest request) {
        String rightId = request.getParameter("rightId");
        Bpmfvright bpmfvright = null;
        if (StringUtils.isNotEmpty(rightId)) {
            bpmfvright = bpmfvrightManager.get(rightId);
        } else {
            bpmfvright = new Bpmfvright();
        }

        return bpmfvright;
    }
    /**
     * 保存实体数据
     * @param request
     * @param bpmfvright
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("bpmfvright") @Valid Bpmfvright bpmfvright, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(bpmfvright.getRightId())) {
            bpmfvright.setRightId(idGenerator.getSID());
            bpmfvrightManager.create(bpmfvright);
            msg = getMessage("bpmfvright.created", new Object[]{bpmfvright.getIdentifyLabel()}, "[Bpmfvright]成功创建!");
        } else {
            bpmfvrightManager.update(bpmfvright);
            msg = getMessage("bpmfvright.updated", new Object[]{bpmfvright.getIdentifyLabel()}, "[Bpmfvright]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

