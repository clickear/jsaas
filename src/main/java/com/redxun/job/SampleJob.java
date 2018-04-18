package com.redxun.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import com.redxun.core.scheduler.BaseJob;

/**
 * 任务实例。
 * @author redxun
 *
 */
public class SampleJob extends BaseJob {

	@Override
	public void executeJob(JobExecutionContext context) {
		System.out.println("SampleJob");
		//获取任务参数代码
		JobDataMap data=context.getJobDetail().getJobDataMap();
		System.out.println(data.get("day"));
	}

}
