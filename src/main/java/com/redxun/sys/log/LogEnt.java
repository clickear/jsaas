package com.redxun.sys.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)   
@Documented  
@Inherited 
public @interface LogEnt {
	
	/**
	 * 所属模块
	 * @return
	 */
	public String module();
		
	/**
	 * 功能
	 * @return
	 */
	public String submodule();
	

	/**
	 * 操作
	 * @return
	 */
	public String action();
	
	
	/**
	 * 传的参数
	 * @return
	 */
	public String params() default "";
	
}
