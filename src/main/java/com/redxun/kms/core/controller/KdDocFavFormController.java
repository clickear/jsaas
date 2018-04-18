package com.redxun.kms.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.kms.core.entity.KdDocFav;
import com.redxun.kms.core.manager.KdDocFavManager;
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
 * [KdDocFav]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdDocFav/")
public class KdDocFavFormController extends BaseFormController {

    @Resource
    private KdDocFavManager kdDocFavManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdDocFav")
    public KdDocFav processForm(HttpServletRequest request) {
        String favId = request.getParameter("favId");
        KdDocFav kdDocFav = null;
        if (StringUtils.isNotEmpty(favId)) {
            kdDocFav = kdDocFavManager.get(favId);
        } else {
            kdDocFav = new KdDocFav();
        }

        return kdDocFav;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdDocFav
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdDocFav") @Valid KdDocFav kdDocFav, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdDocFav.getFavId())) {
            kdDocFav.setFavId(idGenerator.getSID());
            kdDocFavManager.create(kdDocFav);
            msg = getMessage("kdDocFav.created", new Object[]{kdDocFav.getIdentifyLabel()}, "[KdDocFav]成功创建!");
        } else {
            kdDocFavManager.update(kdDocFav);
            msg = getMessage("kdDocFav.updated", new Object[]{kdDocFav.getIdentifyLabel()}, "[KdDocFav]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

