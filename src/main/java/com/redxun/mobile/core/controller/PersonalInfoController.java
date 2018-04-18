package com.redxun.mobile.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.EncryptUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.sys.core.entity.SysAccount;
import com.redxun.sys.core.manager.SysAccountManager;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;

/**
 * 手机端登录控制器
 * 
 * @author csx
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn） 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/mobile/oa/personalInfo/")
public class PersonalInfoController extends BaseFormController {

    @Resource
    private OsUserManager osUserManager;
    @Resource
    OsGroupManager osGroupManager;
    @Resource
    private SysAccountManager sysAccountManager;

    @SuppressWarnings("rawtypes")
    @RequestMapping("getUser")
	@ResponseBody
	public Map<String,Object> getUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Map<String,Object> resultMap = new HashMap<String, Object>();
		OsUser user = osUserManager.get(ContextUtil.getCurrentUserId());
		OsGroup mainDept = osGroupManager.getMainDeps(ContextUtil.getCurrentUserId());
		boolean isSuperAdmin = ContextUtil.getCurrentUser().isSuperAdmin();

		List<SysAccount> account = sysAccountManager.getByUserId(user.getUserId());
		resultMap.put("user", user);
		resultMap.put("account", account);
		resultMap.put("mainDept", mainDept);
		resultMap.put("isSuperAdmin", isSuperAdmin);
		
		return resultMap;
	}
    
    
    
    
    
    @SuppressWarnings("rawtypes")
	@RequestMapping("edit")
	@ResponseBody
	public JsonResult editUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userInfo = request.getParameter("userInfo");
		OsUser newUserInfo=(OsUser)JSONUtil.json2Bean(userInfo, OsUser.class);
		OsUser user = osUserManager.get(ContextUtil.getCurrentUserId());
		BeanUtil.copyNotNullProperties(user,newUserInfo);
		osUserManager.update(user);
		return new JsonResult(true,"修改成功");
	}
    
    @SuppressWarnings("rawtypes")
	@RequestMapping("editPassword")
    @ResponseBody
    public JsonResult editPassword(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String password=request.getParameter("password");
    	List<SysAccount> sysAccounts=sysAccountManager.getByUserId(ContextUtil.getCurrentUserId());
    	if(sysAccounts==null||sysAccounts.isEmpty()){
    		return new JsonResult(true,"没有找到当前用户！");
    	}
    	SysAccount sysAccount=sysAccounts.get(0);
    	sysAccount.setPwd(EncryptUtil.encryptSha256(password.trim()));
    	sysAccountManager.saveOrUpdate(sysAccount);
    	return new JsonResult(true,"成功修改密码！");
    }
    
}
