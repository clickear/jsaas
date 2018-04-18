package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdUserLevel;
import com.redxun.kms.core.manager.KdUserLevelManager;
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
 * [KdUserLevel]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdUserLevel/")
public class KdUserLevelFormController extends BaseFormController {

    @Resource
    private KdUserLevelManager kdUserLevelManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdUserLevel")
    public KdUserLevel processForm(HttpServletRequest request) {
        String confId = request.getParameter("confId");
        KdUserLevel kdUserLevel = null;
        if (StringUtils.isNotEmpty(confId)) {
            kdUserLevel = kdUserLevelManager.get(confId);
        } else {
            kdUserLevel = new KdUserLevel();
        }

        return kdUserLevel;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdUserLevel
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdUserLevel") @Valid KdUserLevel kdUserLevel, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdUserLevel.getConfId())) {
            kdUserLevel.setConfId(idGenerator.getSID());
            kdUserLevelManager.create(kdUserLevel);
            msg = getMessage("kdUserLevel.created", new Object[]{kdUserLevel.getIdentifyLabel()}, "[KdUserLevel]成功创建!");
        } else {
            kdUserLevelManager.update(kdUserLevel);
            msg = getMessage("kdUserLevel.updated", new Object[]{kdUserLevel.getIdentifyLabel()}, "[KdUserLevel]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

