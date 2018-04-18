package com.redxun.sys.core.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.scheduler.IJobLogService;
import com.redxun.core.scheduler.SysJobLog;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.core.dao.SysQuartzLogDao;
import com.redxun.sys.core.entity.SysQuartzLog;

@Service
public class JobLogService implements IJobLogService {
	
	@Resource
	private SysQuartzLogDao sysQuartzLogDao;

	@Override
	public void create(SysJobLog jobLog) {
		
		SysQuartzLog log=new SysQuartzLog();
		log.setLogId(IdUtil.getId());
		log.setAlias(jobLog.getJobName());
		log.setJobName(jobLog.getJobName());
		log.setContent(jobLog.getContent());
		log.setTriggerName(jobLog.getTrigName());
		log.setStatus(jobLog.getState());
		log.setStartTime(jobLog.getStartTime());
		log.setEndTime(jobLog.getEndTime());
		log.setRunTime(jobLog.getRunTime());
		
		sysQuartzLogDao.create(log);;

	}

}
