package com.redxun.saweb.security.metadata;

import com.redxun.saweb.security.consts.SecurityConsts;
import java.util.Collection;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
/**
 * 安全数据源管理
 * @author X230
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class SecurityFilterMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    //private static final ConfigAttribute ROLE_ANONYMOUS = new SecurityConfig("ROLE_ANONYMOUS");
    
    private HashSet<String> anonymousUrls = new HashSet<String>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> configAttribute = new HashSet<ConfigAttribute>();

        FilterInvocation filterInvocation = ((FilterInvocation) object);
        
        HttpServletRequest request = filterInvocation.getRequest();

        String url = getServletUrl(request);

        for(String pUrl:anonymousUrls){
            PathMatcher matcher=new AntPathMatcher();
            boolean isMatch=matcher.match(pUrl, url);
            //匿名访问的URL
            if(isMatch){
               configAttribute.add(SecurityConsts.ROLE_ANONYMOUS);
               return configAttribute;
            }
        }

        configAttribute.add(new SecurityConfig("ROLE_PUBLIC"));
        return configAttribute;
    }
    /**
     * 获得有效的访问地址，不带contextPath
     * @param req
     * @return 
     */
    private String getServletUrl(HttpServletRequest req) {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        if (!"".equals(contextPath)) {
            int index = url.indexOf(contextPath);
            if (index != -1) {
                url = url.substring(index + contextPath.length());
            }
        }
        return url;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
       return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    public HashSet<String> getAnonymousUrls() {
        return anonymousUrls;
    }

    public void setAnonymousUrls(HashSet<String> anonymousUrls) {
        this.anonymousUrls = anonymousUrls;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //TODO,从数据库初始化资源值
    }

}
