package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocRound;
import com.redxun.kms.core.manager.KdDocRoundManager;
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
 * [KdDocRound]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocRound/")
public class KdDocRoundFormController extends BaseFormController {

    @Resource
    private KdDocRoundManager kdDocRoundManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocRound")
    public KdDocRound processForm(HttpServletRequest request) {
        String roundId = request.getParameter("roundId");
        KdDocRound kdDocRound = null;
        if (StringUtils.isNotEmpty(roundId)) {
            kdDocRound = kdDocRoundManager.get(roundId);
        } else {
            kdDocRound = new KdDocRound();
        }

        return kdDocRound;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocRound
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocRound") @Valid KdDocRound kdDocRound, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocRound.getRoundId())) {
            kdDocRound.setRoundId(idGenerator.getSID());
            kdDocRoundManager.create(kdDocRound);
            msg = getMessage("kdDocRound.created", new Object[]{kdDocRound.getIdentifyLabel()}, "[KdDocRound]成功创建!");
        } else {
            kdDocRoundManager.update(kdDocRound);
            msg = getMessage("kdDocRound.updated", new Object[]{kdDocRound.getIdentifyLabel()}, "[KdDocRound]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

