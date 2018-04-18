package com.redxun.saweb.controller;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.redxun.core.context.HttpServletContext;
import com.redxun.core.seq.IdGenerator;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
/**
 * Controller基础类
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class GenericController {
	@Resource
	protected IdGenerator idGenerator;
	@Resource
	MessageSource messageSource;
	
    protected Log logger=LogFactory.getLog(BaseController.class);
    
    protected Logger log=LoggerFactory.getLogger(BaseController.class);
    
    /**
     *
     * @return
     */
    public ModelAndView getPathView(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
       // logger.debug("not foud handle mapping for url: " + requestURI);
        //处理RequestURI
        String contextPath = request.getContextPath();

        requestURI = requestURI.replace(".do", "");
        int cxtIndex = requestURI.indexOf(contextPath);
        if (cxtIndex != -1) {
            requestURI = requestURI.substring(cxtIndex + contextPath.length());
        }
        String[] paths = requestURI.split("[/]");
        String jspPath = null;
        if (paths != null && paths.length == 6) {
        	  jspPath =  paths[1] + "/" + paths[2] + "/" + paths[3]+ "/" + paths[4] + StringUtil.makeFirstLetterUpperCase(paths[5]) + ".jsp";
        }else if (paths != null && paths.length == 5) {
            jspPath =  paths[1] + "/" + paths[2] + "/" + paths[3] + StringUtil.makeFirstLetterUpperCase(paths[4]) + ".jsp";
        }else if(paths!=null && paths.length==4){
        	jspPath =  paths[1] + "/" + paths[2] + StringUtil.makeFirstLetterUpperCase(paths[3]) + ".jsp";
        }
        else {
            jspPath = requestURI + ".jsp";
        }
        logger.debug("getPathView:"+ jspPath);
        return new ModelAndView(jspPath);
    }
    
    protected String getMessage(String msgKey,String defaultMsg){
    	return getMessage(msgKey,new Object[]{},defaultMsg);
    }
    
	/**
	 * 获取到当前语言环境的Key
	 * @param request
	 * @param msgKey
	 * @param args
	 * @return
	 */
	protected String getMessage(String msgKey,Object[]args){
		Locale locale=RequestContextUtils.getLocale(HttpServletContext.getRequest());
		return messageSource.getMessage(msgKey, args, locale);
	}
	
	/**
	 * 获取到当前语言环境的Key
	 * @param request
	 * @param msgKey
	 * @param defaultMsg
	 * @param args
	 * @return
	 */
	protected String getMessage(String msgKey,Object[] args,String defaultMsg){
		Locale locale=RequestContextUtils.getLocale(HttpServletContext.getRequest());
		return messageSource.getMessage(msgKey, args, defaultMsg, locale);
	}
    /**
     * Bind Error Message
     * @param bindResult
     * @return
     */
    protected String getErrorMsg(BindingResult bindResult){
    	List<ObjectError>objErrorList= bindResult.getAllErrors();
    	StringBuffer sb=new StringBuffer();
    	for(ObjectError err:objErrorList){
    		String message=null;
    		if(err instanceof FieldError){
    			FieldError fieldErr=(FieldError)err;
    			String msgKey=fieldErr.getObjectName() + "." + fieldErr.getField();
    			String fielName=getMessage(msgKey,new Object[]{});
    			String rejectVal=fieldErr.getRejectedValue()==null?"":fieldErr.getRejectedValue().toString();
    			message=getMessage("reject.valueError",new Object[]{fielName,rejectVal,fieldErr.getDefaultMessage()});
    		}else{
    			message="对象:"+err.getObjectName()+"，属性：" + ((err.getCodes()==null)?"":err.getCodes()[0]);
    			message +=err.getDefaultMessage();
    		}
    		sb.append(message).append("\n");
    	}
    	return sb.toString();
    }
    /**
     * 保存操作的信息
     * @param request
     * @param message
     */
    protected void saveOpMessage(HttpServletRequest request,String message){
    	request.getSession().setAttribute("opMsg", message);
    }
    
	/**
	 * 获得当前有效的租用机构ID
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	protected String getCurTenantId(HttpServletRequest request) {
		//从页面中获取租用ID,一般是管理员管理多个机构的实例数据时提交的该参数
		String instId=request.getParameter("tenantId");
		if(StringUtils.isNotEmpty(instId) && !"null".equals("tenantId")){
			//当前用户为指定的管理机构下的用户才可以查询到指定的租用机构下的数据
			if(WebAppUtil.getOrgMgrDomain().equals(ContextUtil.getTenant().getDomain())){
				return instId;
			}
		}
		
		return ContextUtil.getCurrentTenantId();
	}
}
