package com.redxun.sys.core.controller;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysTreeCat;
import com.redxun.sys.core.manager.SysTreeCatManager;
import com.redxun.sys.log.LogEnt;

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
 * 系统树分类管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysTreeCat/")
public class SysTreeCatFormController extends BaseFormController {

    @Resource
    private SysTreeCatManager sysTreeCatManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysTreeCat")
    public SysTreeCat processForm(HttpServletRequest request) {
        String catId = request.getParameter("catId");
        SysTreeCat sysTreeCat = null;
        if (StringUtils.isNotEmpty(catId)) {
            sysTreeCat = sysTreeCatManager.get(catId);
        } else {
            sysTreeCat = new SysTreeCat();
        }

        return sysTreeCat;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysTreeCat
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "系统内核", submodule = "系统分类树")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysTreeCat") @Valid SysTreeCat sysTreeCat, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(sysTreeCat.getCatId())) {
            sysTreeCat.setCatId(idGenerator.getSID());
            sysTreeCatManager.create(sysTreeCat);
            msg = getMessage("sysTreeCat.created", new Object[]{sysTreeCat.getIdentifyLabel()}, "系统树分类成功创建!");
        } else {
            sysTreeCatManager.update(sysTreeCat);
            msg = getMessage("sysTreeCat.updated", new Object[]{sysTreeCat.getIdentifyLabel()}, "系统树分类成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

