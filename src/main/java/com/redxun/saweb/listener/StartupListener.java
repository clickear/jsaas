package com.redxun.saweb.listener;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.redxun.saweb.util.WebAppUtil;

/**
 * 
 * <pre> 
 * 描述：TODO
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@redxun.cn
 * 日期:2014年6月21日-下午1:58:06
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * 网址：http://www.redxun.cn
 * </pre>
 */
public class StartupListener extends ContextLoaderListener{
	 private final static Log logger=LogFactory.getLog(StartupListener.class);
	
    @Override
    public void contextInitialized(ServletContextEvent event) {
    	WebAppUtil.init(event.getServletContext());
    	super.contextInitialized(event);
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	super.contextDestroyed(event);
    }
    
    
    
}
