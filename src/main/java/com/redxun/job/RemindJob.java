package com.redxun.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.TaskService;
import org.quartz.JobExecutionContext;

import com.redxun.bpm.activiti.util.ProcessHandleHelper;
import com.redxun.bpm.core.dao.BpmRemindHistoryQueryDao;
import com.redxun.bpm.core.dao.BpmRemindInstQueryDao;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.BpmRemindHistory;
import com.redxun.bpm.core.entity.BpmRemindInst;
import com.redxun.bpm.core.entity.BpmTask;
import com.redxun.bpm.core.entity.ProcessMessage;
import com.redxun.bpm.core.entity.ProcessNextCmd;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmTaskManager;
import com.redxun.core.jms.IMessageProducer;
import com.redxun.core.jms.MessageModel;
import com.redxun.core.jms.MessageUtil;
import com.redxun.core.scheduler.BaseJob;
import com.redxun.core.script.GroovyEngine;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.DateUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.org.api.service.UserService;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.util.SysUtil;

/**
 * 催办定时任务。
 * @author ray
 *
 */
public class RemindJob extends BaseJob{
	
	private BpmRemindInstQueryDao bpmRemindInstQueryDao=(BpmRemindInstQueryDao) WebAppUtil.getBean(BpmRemindInstQueryDao.class);
	private BpmRemindHistoryQueryDao bpmRemindHistoryQueryDao= WebAppUtil.getBean(BpmRemindHistoryQueryDao.class);
	private BpmTaskManager bpmTaskManager= WebAppUtil.getBean(BpmTaskManager.class);
	private UserService userService=WebAppUtil.getBean(UserService.class);
	
	
	@Override
	public void executeJob(JobExecutionContext context) {
		
		//取得已经过期的催办实例
		List<BpmRemindInst> exprieList=bpmRemindInstQueryDao.getRemindInst(true);
		List<BpmRemindInst> notExpireList=bpmRemindInstQueryDao.getRemindInst(false);
		
		if(BeanUtil.isEmpty(exprieList) && BeanUtil.isEmpty(notExpireList)) return;
		
		//已过期的催办执行催办操作。
		for(BpmRemindInst inst :exprieList){
			handExpireInst(inst);
		}
		//未过期的执行催办操作。
		for(BpmRemindInst inst :notExpireList){
			handNotExpireInst(inst);
		}
	}
	
	/**
	 * 处理未过期的催办。
	 * @param inst
	 */
	private void handNotExpireInst(BpmRemindInst inst){
		
		
		if("create".equals(inst.getStatus())){
			inst.setStatus("run");
			bpmRemindInstQueryDao.update(inst);
		}
		
		String notifyType=inst.getNotifyType();
		//没有催办配置无需催办。
		if(StringUtil.isEmpty(notifyType)) return;
		//获取起点时间。
		Date baseTime=inst.getTimeToSend();
		
		Date tmpDate=new Date();
		//时间不在催办时间范围内。
		if(tmpDate.before(baseTime) ||  
			tmpDate.after(DateUtil.add(baseTime, Calendar.MINUTE,  inst.getSendTimes()* inst.getSendInterval()))){
			return;
		}
		/**
		 * 遍历发送区间。
		 */
		for(int i=0;i<inst.getSendTimes();i++){
			Date start=DateUtil.add(baseTime, Calendar.MINUTE,  i* inst.getSendInterval()) ;
			Date end=DateUtil.add(baseTime, Calendar.MINUTE,  (i+1)* inst.getSendInterval()) ;
			Date curDate=new Date();
			//判断当前时间是否在区间内
			if(curDate.after(start) && curDate.before(end)){
				Integer rtn= bpmRemindHistoryQueryDao.getByStartEnd(inst.getId(), start, end);
				if(rtn>0) continue;
				//催办
				handRemind(inst);
			}
		}
	}
	
	/**
	 * 发送操办提醒。
	 * @param inst
	 * @throws Exception 
	 */
	private void handRemind(BpmRemindInst inst) {
		
		
		String taskId=inst.getTaskId();
		
		BpmTask bpmTask = bpmTaskManager.get(taskId);
		Set<IUser> userSet= bpmTaskManager.getTaskUsers(taskId);
		if(BeanUtil.isEmpty(userSet)) return;

		//获取系统默认发送人。
		String account= WebAppUtil.getProperty("sendRemindUser");
		
		IUser sender=userService.getByUsername(account);
		

		String template=MessageUtil.getFlowTemplateByAlias("remind");
		
		MessageModel msgModel=new MessageModel();
		msgModel.setSubject(inst.getName() + "任务催办");
		msgModel.setTemplateAlias(template);
		msgModel.setSender(sender);
		msgModel.setType(inst.getNotifyType());
		
		Map<String,Object> vars=msgModel.getVars();
		vars.put("sender", sender.getFullname());
		vars.put("serverUrl", WebAppUtil.getInstallHost());
		vars.put("taskId", inst.getTaskId());
		vars.put("subject", bpmTask.getDescription());
		
		
		
		for(IUser receiver:userSet){
			List<IUser> receiverList=new ArrayList<IUser>();
			receiverList.add(receiver);
			vars.put("receiver", receiver.getFullname());
			msgModel.setVars(vars);
			msgModel.setRecieverList(receiverList);
			//发送催办消息。
			SysUtil.sendMessage(msgModel);
		}
		
		//添加提醒历史。
		addHistory(inst,"remind");
	}
	
	/**
	 * 处理到期动作。
	 * @param inst
	 */
	private void handExpireInst(BpmRemindInst inst){
		String action=inst.getAction();
		//审批
		if("approve".equals(action) || "back".equals(action) || "backToStart".equals(action)){
			handTask(inst,action);
		}
		//脚本
		else if("script".equals(action)){
			handScript(inst);
		}
		inst.setStatus("finish");
		
		bpmRemindInstQueryDao.update(inst);
		
		addHistory(inst,"expire");
	}
	
	/**
	 * 添加催办历史。
	 * @param inst
	 * @param type	expire，到期处理，remind ，催办处理
	 */
	private void addHistory(BpmRemindInst inst ,String type){
		BpmRemindHistory history=new BpmRemindHistory();
		history.setId(IdUtil.getId());
		history.setReminderInstId(inst.getId());
		history.setRemindType(type);
		history.setCreateTime(new Date());
		history.setTenantId(inst.getTenantId());
		
		bpmRemindHistoryQueryDao.create(history);
	}
	
	/**
	 * 处理审批，让任务自动跳过。
	 * @param inst
	 */
	private void handTask(BpmRemindInst inst,String action){
		String sendAccount= WebAppUtil.getProperty("sendRemindUser");
		BpmTaskManager taskManager=WebAppUtil.getBean(BpmTaskManager.class);
		BpmInstManager bpmInstManager=WebAppUtil.getBean(BpmInstManager.class);
		//设置审批人。
		ContextUtil.setCurUser(sendAccount);
	
		ProcessNextCmd cmd = new ProcessNextCmd();
		cmd.setTaskId(inst.getTaskId());
		String actType="";
		String opinion="";
		if("approve".equals(action)){
			actType="AGREE";
			opinion="催办自动审批";
		}
		else if("back".equals(action)){
			actType="BACK";
			opinion="催办自动驳回";
		}
		else if("backToStart".equals(action)){
			actType="BACK_TO_STARTOR";
			opinion="催办自动驳回发起人";
		}
		cmd.setJumpType(actType);
		cmd.setOpinion(opinion);
		
		ProcessHandleHelper.clearBackPath();
		ProcessMessage handleMessage  = new ProcessMessage();
		ProcessHandleHelper.setProcessMessage(handleMessage);
		try{
			taskManager.doNext(cmd);
		}
		catch(Exception ex){
			if (handleMessage.getErrorMsges().size() > 0) {
				BpmTask bpmTask=taskManager.get(cmd.getTaskId());
				BpmInst bpmInst=bpmInstManager.getByActInstId(bpmTask.getProcInstId());
				
				bpmInst.setErrors(handleMessage.getErrors());
				bpmInstManager.update(bpmInst);
			}
		}
		ProcessHandleHelper.clearProcessMessage();
	}
	
	/**
	 * 处理脚本执行。
	 * @param inst
	 */
	private void handScript(BpmRemindInst inst){
		Map<String, Object> vars = getVars(inst);
		GroovyEngine groovyEngine=WebAppUtil.getBean(GroovyEngine.class);
		try {
			groovyEngine.executeScripts(inst.getScript(), vars);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取变量。
	 * @param inst
	 * @return
	 */
	private Map<String, Object>  getVars(BpmRemindInst inst){
		TaskService taskService=WebAppUtil.getBean(TaskService.class);
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> variables = taskService.getVariables( inst.getTaskId());
		vars.putAll(variables);
		vars.put("inst", inst);
		return vars;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.err.println(format.format(new Date()) +"," + format.format(DateUtil.add(new Date(), Calendar.MINUTE, 60)));
	}

}
