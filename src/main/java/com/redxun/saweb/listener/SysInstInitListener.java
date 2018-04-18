package com.redxun.saweb.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

import com.redxun.sys.core.manager.SysInstManager;

/**
 * 在系统启动时初始化租户信息。
 * @author ray
 *
 */
public class SysInstInitListener implements ApplicationListener<ContextRefreshedEvent>,Ordered{
	
	@Resource
	SysInstManager sysInstManager;
	
	@Override
	public int getOrder() {
		
		return 1;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent ev) {
		// 防止重复执行。
		if (ev.getApplicationContext().getParent() != null) return;
		//系统启动时加载租户。
		sysInstManager.initTenants();
	}

}
