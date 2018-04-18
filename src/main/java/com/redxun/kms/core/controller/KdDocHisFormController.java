package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocHis;
import com.redxun.kms.core.manager.KdDocHisManager;
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
 * [KdDocHis]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocHis/")
public class KdDocHisFormController extends BaseFormController {

    @Resource
    private KdDocHisManager kdDocHisManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocHis")
    public KdDocHis processForm(HttpServletRequest request) {
        String hisId = request.getParameter("hisId");
        KdDocHis kdDocHis = null;
        if (StringUtils.isNotEmpty(hisId)) {
            kdDocHis = kdDocHisManager.get(hisId);
        } else {
            kdDocHis = new KdDocHis();
        }

        return kdDocHis;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocHis
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocHis") @Valid KdDocHis kdDocHis, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocHis.getHisId())) {
            kdDocHis.setHisId(idGenerator.getSID());
            kdDocHisManager.create(kdDocHis);
            msg = getMessage("kdDocHis.created", new Object[]{kdDocHis.getIdentifyLabel()}, "[KdDocHis]成功创建!");
        } else {
            kdDocHisManager.update(kdDocHis);
            msg = getMessage("kdDocHis.updated", new Object[]{kdDocHis.getIdentifyLabel()}, "[KdDocHis]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

