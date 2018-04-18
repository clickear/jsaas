package com.redxun.sys.ldap.job;

import static com.redxun.org.api.model.ITenant.ADMIN_TENANT_ID;

import java.util.List;

import org.quartz.JobExecutionContext;

import com.redxun.core.scheduler.BaseJob;
import com.redxun.core.util.AppBeanUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.sys.ldap.entity.SysLdapConfig;
import com.redxun.sys.ldap.entity.SysLdapLog;
import com.redxun.sys.ldap.manager.SysLdapConfigManager;
/**
 * 
 * AD定时任务
 * 
 * @Description:
 * @ClassName: JobLdap
 * @date 2016年11月11日 下午2:28:47
 * @author cjx
 * @Email: keitch@redxun.cn
 * @Copyright (c) 2014-2016 使用范围：授权给敏捷集团使用 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class JobLdap extends BaseJob {

	@Override
	public void executeJob(JobExecutionContext context) {
		SysLdapConfigManager sysLdapConfigManager = (SysLdapConfigManager) AppBeanUtil
				.getBean("sysLdapConfigManager");
		List<SysLdapConfig> sysLdapConfigList = sysLdapConfigManager
				.getAllByTenantId(ITenant.ADMIN_TENANT_ID);
		String id = null;
		if (sysLdapConfigList.size() > 0) {
			id = sysLdapConfigList.get(0).getSysLdapConfigId();
			SysLdapLog sysLdapLog = new SysLdapLog();
			sysLdapLog.setLogName("定时同步AD数据");
			try {
				sysLdapConfigManager
						.doSyncData(ADMIN_TENANT_ID, id, sysLdapLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
