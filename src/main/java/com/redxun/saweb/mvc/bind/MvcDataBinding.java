package com.redxun.saweb.mvc.bind;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.redxun.core.util.DateUtil;

/**
 * 用于表单提交数据时，进行字符串转成相应的JAVA属性类型，配置于
 * <pre>
 * <!--Spring3.1 之后的自定义注解 HandlerAdapter -->
	<bean
		class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
		<property name="webBindingInitializer">
			<bean class="com.redxun.saweb.mvc.bind.MvcDataBinding" />
		</property>
		...
	</bean>
 * 
 * </pre>
 * @author X230
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class MvcDataBinding implements WebBindingInitializer {
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
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
