package com.redxun.oa.personal.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.personal.entity.AddrGrp;
import com.redxun.oa.personal.manager.AddrGrpManager;
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
 * 联系人分组FormController
 * 
 * @author zwj
 *  用途：处理对联系人分组相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/personal/addrGrp/")
public class AddrGrpFormController extends BaseFormController {

    @Resource
    private AddrGrpManager addrGrpManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("addrGrp")
    public AddrGrp processForm(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        AddrGrp addrGrp = null;
        if (StringUtils.isNotEmpty(groupId)) {
            addrGrp = addrGrpManager.get(groupId);
        } else {
            addrGrp = new AddrGrp();
        }

        return addrGrp;
    }
    /**
     * 保存实体数据
     * @param request
     * @param addrGrp
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("addrGrp") @Valid AddrGrp addrGrp, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(addrGrp.getGroupId())) {
            addrGrp.setGroupId(idGenerator.getSID());
            addrGrpManager.create(addrGrp);
            msg = getMessage("addrGrp.created", new Object[]{addrGrp.getName()}, "联系人分组成功创建!");
        } else {
            addrGrpManager.update(addrGrp);
            msg = getMessage("addrGrp.updated", new Object[]{addrGrp.getName()}, "联系人分组成功更新!");
        }
        return new JsonResult(true, msg);
    }
}

