
package com.redxun.sys.org.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsDimensionRight;
import com.redxun.sys.org.manager.OsDimensionRightManager;

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
 * 维度授权 管理
 * @author ray
 */
@Controller
@RequestMapping("/sys/org/osDimensionRight/")
public class OsDimensionRightFormController extends BaseFormController {

    @Resource
    private OsDimensionRightManager osDimensionRightManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("osDimensionRight")
    public OsDimensionRight processForm(HttpServletRequest request) {
        String id = request.getParameter("id");
        OsDimensionRight osDimensionRight = null;
        if (StringUtils.isNotEmpty(id)) {
            osDimensionRight = osDimensionRightManager.get(id);
        } else {
            osDimensionRight = new OsDimensionRight();
        }

        return osDimensionRight;
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
    @LogEnt(action = "save", module = "组织架构", submodule = "组织维度授权")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("osDimensionRight") @Valid OsDimensionRight osDimensionRight, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(osDimensionRight.getRightId())) {
            osDimensionRight.setRightId(IdUtil.getId());
            osDimensionRightManager.create(osDimensionRight);
            msg = getMessage("osDimensionRight.created", new Object[]{osDimensionRight.getIdentifyLabel()}, "[维度授权]成功创建!");
        } else {
            osDimensionRightManager.update(osDimensionRight);
            msg = getMessage("osDimensionRight.updated", new Object[]{osDimensionRight.getIdentifyLabel()}, "[维度授权]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

