package com.redxun.saweb.listener;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.core.util.BeanUtil;
import com.redxun.sys.core.manager.SysDataSourceManager;

/**
 * 系统启动时添加数据源。
 * @author ray
 *
 */
public class DataSourceInitListener  implements ApplicationListener<ContextRefreshedEvent>,Ordered{

	protected static final Logger LOGGER = LoggerFactory.getLogger(DataSourceInitListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent ev) {
		// 防止重复执行。
		if (ev.getApplicationContext().getParent() != null) return;
		

		ApplicationContext context = ev.getApplicationContext();
		// 加载数据库中的数据源--->start
		SysDataSourceManager sysDataSourceManager = (SysDataSourceManager) context.getBean("sysDataSourceManager");
		// 获取可用的数据源
		Map<String, DataSource> sysDataSources = sysDataSourceManager.getDataSource();
		if(BeanUtil.isEmpty(sysDataSources)) return;
		// 添加数据实例到容器中。
		for (Map.Entry<String, DataSource> entry : sysDataSources.entrySet()) {
			try {
				DataSourceUtil.addDataSource(entry.getKey(), entry.getValue(), false);
			}catch (Exception e) {
				LOGGER.debug(e.getMessage());
			}
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
