package com.redxun.oa.mail.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.mail.entity.MailBox;
import com.redxun.oa.mail.manager.MailBoxManager;
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
 * 内部邮件收件箱FormController
 * @author zwj
 * 用途：处理对内部邮件收件箱相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/mail/mailBox/")
public class MailBoxFormController extends BaseFormController {

    @Resource
    private MailBoxManager mailBoxManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("mailBox")
    public MailBox processForm(HttpServletRequest request) {
        String boxId = request.getParameter("boxId");
        MailBox mailBox = null;
        if (StringUtils.isNotEmpty(boxId)) {
            mailBox = mailBoxManager.get(boxId);
        } else {
            mailBox = new MailBox();
        }

        return mailBox;
    }
    /**
     * 保存实体数据
     * @param request
     * @param mailBox
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("mailBox") @Valid MailBox mailBox, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(mailBox.getBoxId())) {
            mailBox.setBoxId(idGenerator.getSID());
            mailBoxManager.create(mailBox);
            msg = getMessage("mailBox.created", new Object[]{mailBox.getIdentifyLabel()}, "内部邮件收件箱成功创建!");
        } else {
            mailBoxManager.update(mailBox);
            msg = getMessage("mailBox.updated", new Object[]{mailBox.getIdentifyLabel()}, "内部邮件收件箱成功更新!");
        }
        return new JsonResult(true, msg);
    }
}

