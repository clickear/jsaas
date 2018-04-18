package com.redxun.kms.core.controller;

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

import com.redxun.core.json.JsonResult;
import com.redxun.kms.core.entity.KdUser;
import com.redxun.kms.core.manager.KdUserManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.entity.OsUser;

/**
 * [KdUser]管理
 * @author csx
 */
@Controller
@RequestMapping("/kms/core/kdUser/")
public class KdUserFormController extends BaseFormController {

    @Resource
    private KdUserManager kdUserManager;
    
    @Resource
    SysTreeManager sysTreeManager;
    
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("kdUser")
    public KdUser processForm(HttpServletRequest request) {
        String kuserId = request.getParameter("kuserId");
        KdUser kdUser = null;
        if (StringUtils.isNotEmpty(kuserId)) {
            kdUser = kdUserManager.get(kuserId);
        } else {
            kdUser = new KdUser();
        }

        return kdUser;
    }
    /**
     * 保存实体数据
     * @param request
     * @param kdUser
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("kdUser") @Valid KdUser kdUser, BindingResult result) {

        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(kdUser.getKuserId())) {
            kdUser.setKuserId(idGenerator.getSID());
            String knSysId=request.getParameter("knSysId");
        	String reqSysId=request.getParameter("reqSysId");
        	kdUser.setKnSysTree(sysTreeManager.get(knSysId));
        	kdUser.setReqSysTree(sysTreeManager.get(reqSysId));
            kdUser.setUser((OsUser)ContextUtil.getCurrentUser());
            kdUser.setSn(1);
            kdUser.setPoint(0);
            kdUser.setGrade("小菜鸡");
            kdUserManager.create(kdUser);
            msg = getMessage("kdUser.created", new Object[]{kdUser.getIdentifyLabel()}, "[KdUser]成功创建!");
        } else {
        	String knSysId=request.getParameter("knSysId");
        	String reqSysId=request.getParameter("reqSysId");
        	kdUser.setKnSysTree(sysTreeManager.get(knSysId));
        	kdUser.setReqSysTree(sysTreeManager.get(reqSysId));
            kdUserManager.update(kdUser);
            msg = getMessage("kdUser.updated", new Object[]{kdUser.getIdentifyLabel()}, "[KdUser]成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

