
package com.redxun.wx.ent.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.wx.ent.entity.WxEntCorp;
import com.redxun.wx.ent.manager.WxEntCorpManager;

/**
 * 微信企业配置 管理
 * @author mansan
 */
@Controller
@RequestMapping("/wx/ent/wxEntCorp/")
public class WxEntCorpFormController extends BaseFormController {

    @Resource
    private WxEntCorpManager wxEntCorpManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
//    @ModelAttribute("wxEntCorp")
//    public WxEntCorp processForm(HttpServletRequest request) {
//        String id = request.getParameter("id");
//        WxEntCorp wxEntCorp = null;
//        if (StringUtils.isNotEmpty(id)) {
//            wxEntCorp = wxEntCorpManager.get(id);
//        } else {
//            wxEntCorp = new WxEntCorp();
//        }
//
//        return wxEntCorp;
//    }
    /**
     * 保存实体数据
     * @param request
     * @param orders
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @RequestBody WxEntCorp wxEntCorp, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(wxEntCorp.getId())) {
            wxEntCorp.setId(IdUtil.getId());
            wxEntCorpManager.create(wxEntCorp);
            msg = getMessage("wxEntCorp.created", new Object[]{wxEntCorp.getIdentifyLabel()}, "[微信企业配置]成功创建!");
        } else {
            wxEntCorpManager.update(wxEntCorp);
            msg = getMessage("wxEntCorp.updated", new Object[]{wxEntCorp.getIdentifyLabel()}, "[微信企业配置]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

