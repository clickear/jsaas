package com.redxun.saweb.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.redxun.core.util.DateUtil;
import com.redxun.saweb.mvc.bind.CustomTimestampEditor;

/**
 * 基础表单
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class BaseFormController extends GenericController{
	/*
	 * 转成在spring-mvc.xml中的以下配置信息
	 * <bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter">
    	<property name="webBindingInitializer">
			<bean class="com.redxun.saweb.mvc.bind.MvcDataBinding" />
		</property>
        <property name="messageConverters">
            <list>
                <bean class="com.redxun.saweb.json.JsonHttpMessageConverter" />
            </list>
        </property>
    </bean>*/
	@InitBinder
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) {
		logger.debug("init binder ....");
		binder.registerCustomEditor(Integer.class, null,new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
		binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_YMD);
		SimpleDateFormat datetimeFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_FULL);
		datetimeFormat.setLenient(false);
		
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(java.sql.Timestamp.class, new CustomTimestampEditor(datetimeFormat, true));
	}
	
}
