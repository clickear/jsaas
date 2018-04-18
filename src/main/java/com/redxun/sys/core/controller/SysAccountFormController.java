package com.redxun.sys.core.controller;

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
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.core.manager.SysAccountManager;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.log.LogEnt;

/**
 * 账号管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysAccount/")
public class SysAccountFormController extends BaseFormController {

    @Resource
    private SysAccountManager sysAccountManager;
    @Resource
    private SysInstManager sysInstManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("sysAccount")
    public SysAccount processForm(HttpServletRequest request) {
        String accountId = request.getParameter("accountId");
        SysAccount sysAccount = null;
        if (StringUtils.isNotEmpty(accountId)) {
            sysAccount = sysAccountManager.get(accountId);
        } else {
            sysAccount = new SysAccount();
        }

        return sysAccount;
    }
    /**
     * 保存实体数据
     * @param request
     * @param sysAccount
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @LogEnt(action = "save", module = "系统内核", submodule = "子系统")
    public JsonResult save(HttpServletRequest request, @ModelAttribute("sysAccount") @Valid SysAccount sysAccount, BindingResult result) {
    	if(StringUtils.isEmpty(sysAccount.getAccountId())){
    		String domain=ContextUtil.getTenant().getDomain();
    		sysAccount.setDomain(domain);
        	SysAccount existAccount=sysAccountManager.getByNameDomain(sysAccount.getName(), sysAccount.getDomain());
        	if(existAccount!=null){
        		result.rejectValue("name", "accout.exist","账号已经存在！");
        	}
    	}
        if (result.hasFieldErrors()) {
        	return new JsonResult(false, getErrorMsg(result));
        }
        
        String msg = null;
        if (StringUtils.isEmpty(sysAccount.getAccountId())) {
        	sysAccount.setAccountId(idGenerator.getSID());
            sysAccountManager.create(sysAccount);
            msg = getMessage("sysAccount.created", new Object[]{sysAccount.getName()}, "账号成功创建!");
        } else {
            sysAccountManager.update(sysAccount);
            msg = getMessage("sysAccount.updated", new Object[]{sysAccount.getName()}, "账号成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
}

