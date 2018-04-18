package com.redxun.oa.product.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.product.entity.OaAssetsBid;
import com.redxun.oa.product.manager.OaAssetsBidManager;
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
 * [OaAssetsBid]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/product/oaAssetsBid/")
public class OaAssetsBidFormController extends BaseFormController {

    @Resource
    private OaAssetsBidManager oaAssetsBidManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("oaAssetsBid")
    public OaAssetsBid processForm(HttpServletRequest request) {
        String bidId = request.getParameter("bidId");
        OaAssetsBid oaAssetsBid = null;
        if (StringUtils.isNotEmpty(bidId)) {
            oaAssetsBid = oaAssetsBidManager.get(bidId);
        } else {
            oaAssetsBid = new OaAssetsBid();
        }

        return oaAssetsBid;
    }
    /**
     * 保存实体数据
     * @param request
     * @param oaAssetsBid
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("oaAssetsBid") @Valid OaAssetsBid oaAssetsBid, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(oaAssetsBid.getBidId())) {
            oaAssetsBid.setBidId(idGenerator.getSID());
            oaAssetsBidManager.create(oaAssetsBid);
            msg = getMessage("oaAssetsBid.created", new Object[]{oaAssetsBid.getIdentifyLabel()}, "[OaAssetsBid]成功创建!");
        } else {
            oaAssetsBidManager.update(oaAssetsBid);
            msg = getMessage("oaAssetsBid.updated", new Object[]{oaAssetsBid.getIdentifyLabel()}, "[OaAssetsBid]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

