package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysDic;
import com.redxun.sys.core.manager.SysDicManager;
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
 * [SysDic]管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysDic/")
public class SysDicFormController extends BaseFormController {

    @Resource
    private SysDicManager sysDicManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysDic")
    public SysDic processForm(HttpServletRequest request) {
        String dicId = request.getParameter("dicId");
        SysDic sysDic = null;
        if (StringUtils.isNotEmpty(dicId)) {
            sysDic = sysDicManager.get(dicId);
        } else {
            sysDic = new SysDic();
        }

        return sysDic;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysDic
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysDic") @Valid SysDic sysDic, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysDic.getDicId())) {
            sysDic.setDicId(idGenerator.getSID());
            sysDicManager.create(sysDic);
            msg = getMessage("sysDic.created", new Object[]{sysDic.getIdentifyLabel()}, "[SysDic]成功创建!");
        } else {
            sysDicManager.update(sysDic);
            msg = getMessage("sysDic.updated", new Object[]{sysDic.getIdentifyLabel()}, "[SysDic]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

