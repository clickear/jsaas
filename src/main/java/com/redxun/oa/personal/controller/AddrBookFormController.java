package com.redxun.oa.personal.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.personal.entity.AddrBook;
import com.redxun.oa.personal.manager.AddrBookManager;
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
 * 联系人FormController
 * 
 * @author zwj
 *  用途：处理对联系人相关操作的请求映射
 */

@Controller
@RequestMapping("/oa/personal/addrBook/")
public class AddrBookFormController extends BaseFormController {

    @Resource
    private AddrBookManager addrBookManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("addrBook")
    public AddrBook processForm(HttpServletRequest request) {
        String addrId = request.getParameter("addrId");
        AddrBook addrBook = null;
        if (StringUtils.isNotEmpty(addrId)) {
            addrBook = addrBookManager.get(addrId);
        } else {
            addrBook = new AddrBook();
        }

        return addrBook;
    }
    /**
     * 保存实体数据
     * @param request
     * @param addrBook
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("addrBook") @Valid AddrBook addrBook, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(addrBook.getAddrId())) {
            addrBook.setAddrId(idGenerator.getSID());
            addrBookManager.create(addrBook);
            msg = getMessage("addrBook.created", new Object[]{addrBook.getName()}, "联系人成功创建!");
        } else {
            addrBookManager.update(addrBook);
            msg = getMessage("addrBook.updated", new Object[]{addrBook.getName()}, "联系人成功更新!");
        }
        return new JsonResult(true, msg);
    }
}

