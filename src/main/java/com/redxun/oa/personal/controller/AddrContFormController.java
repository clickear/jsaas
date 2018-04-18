package com.redxun.oa.personal.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.personal.entity.AddrCont;
import com.redxun.oa.personal.manager.AddrContManager;
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
 *通讯录联系人信息FormController
 * 
 * @author zwj
 *  用途：处理对联系人信息相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/personal/addrCont/")
public class AddrContFormController extends BaseFormController {

    @Resource
    private AddrContManager addrContManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("addrCont")
    public AddrCont processForm(HttpServletRequest request) {
        String contId = request.getParameter("contId");
        AddrCont addrCont = null;
        if (StringUtils.isNotEmpty(contId)) {
            addrCont = addrContManager.get(contId);
        } else {
            addrCont = new AddrCont();
        }

        return addrCont;
    }
    /**
     * 保存实体数据
     * @param request
     * @param addrCont
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("addrCont") @Valid AddrCont addrCont, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(addrCont.getContId())) {
            addrCont.setContId(idGenerator.getSID());
            addrContManager.create(addrCont);
            msg = getMessage("addrCont.created", new Object[]{addrCont.getType()}, "通讯录联系人信息成功创建!");
        } else {
            addrContManager.update(addrCont);
            msg = getMessage("addrCont.updated", new Object[]{addrCont.getType()}, "通讯录联系人信息成功更新!");
        }
        return new JsonResult(true, msg);
    }
}

