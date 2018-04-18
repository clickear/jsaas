package com.redxun.saweb.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.redxun.core.util.AppBeanUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.util.WebAppUtil;

/**
 *
 * <pre>
 * 描述：程序的应用上下文
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:keith@mitom.cn
 * 日期:2014年5月2日-下午2:28:52
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public class ContextUtil {
	//当前用户线程
	private static ThreadLocal<IUser> curUserLocal=new ThreadLocal<IUser>();

    /**
     * 获取到当前的登录用户
     *
     * @return
     */
    public static IUser getCurrentUser() {
        
    	IUser curUser=curUserLocal.get();
    	if(curUser!=null){
    		return curUser;
    	}
    	
    	SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return null;
        }
        Authentication auth = securityContext.getAuthentication();
        if (auth == null) {
            return null;
        }
        if(auth.getPrincipal() instanceof IUser){
        	return (IUser) auth.getPrincipal();
        }else{
        	return null;
        }
    }
   
    
    /**
     * 设置当前用户
     * @param userAmt
     */
    public static void setCurUser(IUser user){
        curUserLocal.set(user);
    }
    
    /**
     * 根据帐号设置当前登录用户。
     * @param account
     */
    public static void setCurUser(String account){
    	//SysAccount 
    	UserService userService=(UserService)AppBeanUtil.getBean(UserService.class);
    	IUser user=userService.getByUsername(account);
       
        curUserLocal.set(user);
    }

    /**
     * 清除当前用户
     * @param userAmt
     */
    public static void cleanAll(){
        curUserLocal.remove();
        
    }
    /**
     * 获得当前用户的租用ID
     * @return
     */
    public static ITenant getTenant(){
    	 IUser curUser = getCurrentUser();
    	 if(curUser==null) return null;
    	 return curUser.getTenant();
    }

    public static String getCurrentUserId() {
        IUser iUser = getCurrentUser();
        if (iUser==null) return null;
        return iUser.getUserId();
    }

    /**
     * 返回当前用户的租用Id
     *
     * @return String
     * @exception
     * @since 1.0.0
     */
    public static String getCurrentTenantId() {
        IUser curUser = getCurrentUser();
        if (curUser == null) {
            return null;
        }
        return curUser.getTenant().getTenantId();
    }
    
    /**
     * 获得用户的临时文件处理目录
     * @return 
     * @throws Exception 
     */
    public static String getUserTmpDir() throws Exception{
        return WebAppUtil.getUploadPath()+"/" + getCurrentUser().getUsername()+"/tmp";
    }
    
    public static void clearCurLocal(){
    	curUserLocal.remove();
    }

}
