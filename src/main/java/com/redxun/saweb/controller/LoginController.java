package com.redxun.saweb.controller;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.constants.MStatus;
import com.redxun.core.json.JsonResult;
import com.redxun.core.util.CookieUitl;
import com.redxun.core.util.EncryptUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.filter.SecurityUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.entity.SysInst;
import com.redxun.sys.core.entity.SysInstType;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.core.manager.SysInstManager;
import com.redxun.sys.core.manager.SysInstTypeManager;
import com.redxun.sys.log.manager.LogEntityManager;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsUserManager;
import com.redxun.wx.ent.manager.WxEntAgentManager;
/**
 * 登录控制器
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController{
    
    @Resource
    SysInstManager sysInstManager;
	@Resource
    UserService userService;
	@Resource
	WxEntAgentManager wxEntAgentManager;
	@Resource
	SysInstTypeManager sysInstTypeManager;
	@Resource
	LogEntityManager  logEntityManager;
	@Resource
	OsUserManager osUserManager;
	
	
    private String defaultLogin="/login.jsp";
    
    
    
    
    @RequestMapping("login")
    @ResponseBody
    public JsonResult login(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //用户名，若输入的用户名不带@，则认为该用户是管理机构下的用户
    	String username=request.getParameter("acc");
        String password=request.getParameter("pd");
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
        	return new JsonResult(false,"请输入用户与密码！");
        }
        //自动加上domain后缀,若为其他租户的登录，则需要加上domain方式来进行登录
        if(username.indexOf("@")==-1){
        	username=username.trim()+"@"+WebAppUtil.getOrgMgrDomain();
        }
        
        String from= RequestUtil.getString(request, "from");
        //添加cookie
        CookieUitl.addCookie("loginAction", from, true, request, response);
        
        
        OsUser user=(OsUser) userService.getByUsername(username);
        //启用SAAS模式
    	if(user==null || !(user.getUsername()).equals(username) || !user.getPwd().equals(EncryptUtil.hexToBase64(password.trim()))){
            return new JsonResult(false,"密码或用户名不正确！");
        }
        if(user.getTenant()==null || !MStatus.ENABLED.toString().equals(user.getTenant().getStatus())){
        	return new JsonResult(false,"企业机构已经被禁用！");
        }
        
        //加上登录机构类型
        String instType=user.getTenant().getInstType();
        CookieUitl.addCookie("instType", instType, true, request, response);
        
        SecurityUtil.login(request,username, password, false);
        //处理记住密码
        SecurityUtil.writeRememberMeCookie(request,response,username,password);
        
        //记录登录日志
        logEntityManager.recordTheLog(request,response);
        /*将用户头像地址暂时设置到用户里*/
        osUserManager.virtualSetUserPhoto(user, request);
        
        return new JsonResult<OsUser>(true, "Login Success", user);
    }
    
    /**
     * 合作伙伴或租户登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("partner/login")
    @ResponseBody
    public JsonResult partnerLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //用户名，若输入的用户名不带@，则认为该用户是管理机构下的用户
    	String username=request.getParameter("username");
        String password=request.getParameter("password");
        String companyName=request.getParameter("companyName");
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(companyName)){
        	return new JsonResult(false,"请输入用户、公司名与密码！");
        }
       
        String from= RequestUtil.getString(request, "from");
        //添加cookie
        CookieUitl.addCookie("loginAction", from, true, request, response);
        SysInst sysInst=sysInstManager.getByNameCn(companyName);
        if(sysInst==null || !MStatus.ENABLED.toString().equals(sysInst.getStatus())){
        	return new JsonResult(false,"企业机构不存在或已经被禁用！");
        }
      
        if(username.indexOf("@")==-1){
        	username=username.trim()+"@"+sysInst.getDomain();
        }
        
        IUser user=userService.getByUsername(username);
      
    	if(user==null || !(user.getUsername()).equals(username) || 
    			!user.getPwd().equals(EncryptUtil.hexToBase64(password.trim()))){
            return new JsonResult(false,"密码或用户名不正确！");
        }
 
        //加上登录机构类型
        String instType=sysInst.getInstType();
        CookieUitl.addCookie("instType", instType, true, request, response);
        
        SecurityUtil.login(request,username, password, false);
        //处理记住密码
        SecurityUtil.writeRememberMeCookie(request,response,username,password);
    	
        return new JsonResult(true,"Login Success");
    }
    
    /**
     * 网页上使用企业微信登录。
     * <pre>
     * 1.网页调用微信获取二维码。
     * 2.用户打开企业微信扫描此二维码。
     * 3.确认登录后跳转到此页面。
     * 4.在此页面中获取微信传递过来的code。
     * 5.根据code调用微信的api获取当前登录的用户。
     * 6.根据这个用户自动登录系统。
     * </pre>
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("loginWx")
    public void loginWx(HttpServletRequest request,HttpServletResponse response) throws Exception{
//       String code=RequestUtil.getString(request, "code");
//       String corpId="wwef2127890086b34d";
//       String secret="ktGDcy55x_ks9l6XvBGJyQpKcj2e_OpuYOMtgVBDOvs";
//       String userJson=WeixinUtil.getUserByCode(corpId,secret,code);
//       JSONObject obj=JSONObject.parseObject(userJson);
//       String userId=obj.getString("UserId");
//       System.out.println("--------------------------"+userId);
//       SecurityUtil.login(request,"admin@redxun.cn", "", true);
//       
//       response.sendRedirect("index.do");
       
    }
    
    
    
    @RequestMapping("logoutRedirect")
	public void logoutRedirect(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//根据机构类型来访问不同的登录页
    	String instType=CookieUitl.getValueByName("instType", request);
		if(StringUtil.isNotEmpty(instType)){
			if(!SysInstType.INST_TYPE_PLATFORM.equals(instType)){
				response.sendRedirect(request.getContextPath() +"/partner/login.jsp");
				return;
			}
		}
    	
    	String loginAction=CookieUitl.getValueByName("loginAction", request);
		String redirectUrl="";
		Map<String,String> actionPageMap=(Map<String,String>)WebAppUtil.getBean("actionPageMap");
		if(StringUtil.isNotEmpty(loginAction) && actionPageMap.containsKey(loginAction)){
			//删除cookie
			//CookieUitl.delCookie("loginAction", request, response);
			redirectUrl=actionPageMap.get(loginAction);
			response.sendRedirect(request.getContextPath() +redirectUrl);
			return ;
		}
		logEntityManager.recordTheLogOut(request, response);
		response.sendRedirect(request.getContextPath() +this.defaultLogin);
	}
    /**
     * 显示有效的机构
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getValidSysInsts")
    @ResponseBody
    public List<SysInst> getValidSysInsts(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	return sysInstManager.getValidSysInsts();
    }
    
    
}
