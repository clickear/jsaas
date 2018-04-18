package com.redxun.saweb.util;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import com.redxun.core.bean.PropertyPlaceholderConfigurerExt;
import com.redxun.core.util.FileUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.core.util.SysPropertiesUtil;

/**
 * Web应用配置工具类
 * @author csx
 * @Email chshxuan@163.com
 * 版权：广州红迅软件有限公司
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class WebAppUtil implements ApplicationContextAware {
	
    private static Log logger = LogFactory.getLog(WebAppUtil.class);
    
    /**
     * 配置属性
     */
    private static PropertyPlaceholderConfigurerExt configProperties=null;
    
    private static ApplicationContext applicationContext = null;
    
    private static ServletContext servletContext;

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        applicationContext = ctx;
        //加载其配置项信息
        configProperties=ctx.getBean(PropertyPlaceholderConfigurerExt.class);
    }

    public static void init(ServletContext sContext) {
        servletContext = sContext;
        String ctxPath=servletContext.getRealPath("/");
        if(!ctxPath.endsWith("/") && !ctxPath.endsWith("\\")){
        	ctxPath=ctxPath+"/";
        }
        String path=ctxPath +"WEB-INF" + File.separator +"dll" +File.separator + "jec-core.jar";
    	String outPut=ctxPath +"WEB-INF" + File.separator  +"classes";
    	try {
			FileUtil.unzip(path, outPut, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * 返回程序的物理安装路径
     *
     * @return String
     * @exception
     * @since 1.0.0
     */
    public static String getAppAbsolutePath() {
        return servletContext.getRealPath("/");
    }

    /**
     * 返回Class路径。
     * @return
     */
    public static String getClassPath() {
        return WebAppUtil.class.getResource("/").getPath();
    }



    /**
	 * 根据beanId获取配置在系统中的对象实例。
	 * 
	 * @param beanId
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	public static Object getBean(String beanId) {
		try{
			return applicationContext.getBean(beanId);
		}
		catch(Exception ex){
			logger.debug("getBean:" + beanId +"," + ex.getMessage());
		}
		return null;
	}

	/**
	 * 获取Spring容器的Bean
	 * 
	 * @param beanClass
	 * @return T
	 * @exception
	 * @since 1.0.0
	 */
	public static <T> T getBean(Class<T> beanClass) {
		try{
			return  applicationContext.getBean(beanClass);
		}
		catch(Exception ex){
			logger.debug("getBean:" + beanClass +"," + ex.getMessage());
		}
		return null;
	}
    
    /**
     * 发布事件。
     * @param event
     */
    public static void publishEvent(ApplicationEvent event){
		if(applicationContext!=null){
			applicationContext.publishEvent(event);
		}
	}

    /**
     * 返回上传的目录
     *
     * @return String
     * @throws Exception 
     * @exception
     * @since 1.0.0
     */
    public static String getUploadPath()  {
    	try{
    		return SysPropertiesUtil.getGlobalProperty("upload.dir");
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
			return "";
    	}
    }
    
    /**
     * 返回UEditor的上传目录 
     * @return
     * @throws Exception 
     */
    public  static String getUeditorUploadPath() throws Exception{
    	return getUploadPath()+"/ueditor";
    }
    
    /**
     * 获得当前Saas中的管理组织机构的域名，该域名下的管理员具有超级权限，
     * 可以管理其他组织架构的数据
     * @return
     * @throws Exception 
     */
    public static String getOrgMgrDomain() {
		try {
			return SysPropertiesUtil.getGlobalProperty("org.mgr.domain");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
    
    /**
     * 返回数据库类型
     * @return
     */
    public static String getDbType(){
    	return getProperty("db.type");
    }
    /**
     * 获得邮箱的发送地址
     * @return
     */
    public static String getMailFrom(){
    	return getProperty("mail.from");
    }
    
    /**
     * 获得邮箱的发送目标地址
     * @return
     */
    public static String getMailTo(){
    	return getProperty("mail.to");
    }
    /**
     * 获得安装的访问根目录
     * @return
     * @throws Exception 
     */
    public static String getInstallHost() {
    	try{
    		return SysPropertiesUtil.getGlobalProperty("install.host");
    	}
    	catch(Exception ex){
    		return "";
    	}
    }
    /**
     * 是否为Saas模式
     * @return
     * @throws Exception 
     */
    public static boolean getIsSaasMode() throws Exception{
    	return  SysPropertiesUtil.getGlobalPropertyBool("install.saas");
    }
    
    /**
     * 获得应用的名称
     * @return
     * @throws Exception 
     */
    public static String getAppName() {
    	try{
    		return SysPropertiesUtil.getGlobalProperty("app.name");
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    		return "JSAAS业务流程平台";
    	}
    }
    
    /**
     * 是否为Saas管理机构下的用户
     * @return
     * @throws Exception 
     */
    public static boolean isSaasMgrUser() {
    	
    	return getOrgMgrDomain().equals(ContextUtil.getTenant().getDomain());
    }
    
    /**
     * 是否启用SAAS
     * @return
     * @throws Exception 
     */
    public static boolean isEanbleSaaS() throws Exception{
    	return getIsSaasMode();
    }
    
    /**
     * 匿名用户上传目录
     * @return
     * @throws Exception 
     */
    public static String  getAnonymusUploadDir() throws Exception{
    	return SysPropertiesUtil.getGlobalProperty("upload.dir.anony");
    }

    /**
     * 根据key获取属性。
     * @param key
     * @return
     */
    public static String getProperty(String key){
    	String str= configProperties.getProperty(key);
    	if(str==null) return "";
    	return str;
    }
    
    public static String getProperty(String key,String defaultVal){
    	String str= configProperties.getProperty(key);
    	if(StringUtil.isEmpty(str)) return defaultVal;
    	return str;
    }
    
    /**
     * 获取布尔值属性。
     * @param key
     * @return
     */
    public static Boolean getBoolProperty(String key){
    	String str=getProperty(key);
    	if("".equals(str)){
    		return false;
    	}
    	return Boolean.parseBoolean(str);
    }
    
    /**
     * 获取长整型属性值。
     * @param key
     * @return
     */
    public static Long getLongProperty(String key){
    	String str=getProperty(key);
    	if("".equals(str)){
    		return 0L;
    	}
    	return Long.parseLong(str);
    }
    
    /**
     * 获取整型属性。
     * @param key
     * @return
     */
    public static Integer getIntProperty(String key){
    	String str=getProperty(key);
    	if("".equals(str)){
    		return 0;
    	}
    	return Integer.parseInt(str);
    }
    

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	

}
