//package com.redxun.oa.res.job;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.quartz.JobExecutionContext;
//
//import com.redxun.core.util.AppBeanUtil;
//import com.redxun.job.jobclass.BaseJob;
//import com.redxun.oa.msg.bean.MsgNoticeBean;
//import com.redxun.oa.msg.manager.MsgNoticeManager;
//import com.redxun.oa.res.entity.OaCarApp;
//import com.redxun.oa.res.manager.OaCarAppManager;
//
//public class JobResCarApp extends BaseJob {
//
//	@Override
//	public void executeJob(JobExecutionContext context) throws Exception {
//		OaCarAppManager oaCarAppManager=(OaCarAppManager) AppBeanUtil.getBean("oaCarAppManager");
//		MsgNoticeManager msgNoticeManager = (MsgNoticeManager) AppBeanUtil.getBean("msgNoticeManager");
//		List<OaCarApp> oaList=oaCarAppManager.getOaCarApp();
//		
//		for(OaCarApp oaCarr:oaList){
//			MsgNoticeBean msgNoticeBean = new MsgNoticeBean();
//			msgNoticeBean.setTaskName(oaCarr.getCreateBy());
//			msgNoticeBean.setTitle("请归还或者延迟车辆！");
//			msgNoticeBean.setContent("您申请的"+oaCarr.getCarName()+"车，于"+oaCarr.getStartTime()+"开始使用,至"+oaCarr.getEndTime()+"已经到期,请及时归还或者选择延迟归还!");
//			msgNoticeBean.setFrom("1");
//			msgNoticeBean.setTo(oaCarr.getCreateBy());
//
//			List<String> list=new ArrayList<String>();
//			list.add(MsgNoticeBean.inertMail);
//			list.add(MsgNoticeBean.innerMsg);
//			list.add(MsgNoticeBean.shortMsg);
//			list.add(MsgNoticeBean.weixin_reminder);
//			msgNoticeBean.setNoticeTypes(list);
//			msgNoticeManager.createSend(msgNoticeBean);
//		}
//		
//	}
//
//}
