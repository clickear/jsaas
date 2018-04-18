package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocDir;
import com.redxun.kms.core.manager.KdDocDirManager;
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
 * [KdDocDir]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocDir/")
public class KdDocDirFormController extends BaseFormController {

    @Resource
    private KdDocDirManager kdDocDirManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocDir")
    public KdDocDir processForm(HttpServletRequest request) {
        String dirId = request.getParameter("dirId");
        KdDocDir kdDocDir = null;
        if (StringUtils.isNotEmpty(dirId)) {
            kdDocDir = kdDocDirManager.get(dirId);
        } else {
            kdDocDir = new KdDocDir();
        }

        return kdDocDir;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocDir
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocDir") @Valid KdDocDir kdDocDir, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocDir.getDirId())) {
            kdDocDir.setDirId(idGenerator.getSID());
            kdDocDirManager.create(kdDocDir);
            msg = getMessage("kdDocDir.created", new Object[]{kdDocDir.getIdentifyLabel()}, "[KdDocDir]成功创建!");
        } else {
            kdDocDirManager.update(kdDocDir);
            msg = getMessage("kdDocDir.updated", new Object[]{kdDocDir.getIdentifyLabel()}, "[KdDocDir]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

