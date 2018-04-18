package com.redxun.hr.core.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.redxun.bpm.activiti.handler.ProcessEndHandler;
import com.redxun.bpm.activiti.handler.ProcessStartAfterHandler;
import com.redxun.bpm.activiti.handler.TaskAfterHandler;
import com.redxun.bpm.bm.entity.BpmFormInst;
import com.redxun.bpm.bm.manager.BpmFormInstManager;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.IExecutionCmd;
import com.redxun.bpm.core.entity.ProcessStartCmd;
import com.redxun.bpm.core.entity.config.ProcessConfig;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.core.dao.IDao;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.seq.IdGenerator;
import com.redxun.core.util.BeanUtil;
import com.redxun.hr.core.dao.HrErrandsRegisterDao;
import com.redxun.hr.core.entity.HrDutyInst;
import com.redxun.hr.core.entity.HrErrandsRegister;
import com.redxun.hr.core.entity.HrOvertimeExt;
import com.redxun.hr.core.entity.HrTransRestExt;
import com.redxun.hr.core.entity.HrVacationExt;
import com.redxun.oa.res.entity.OStatus;

/**
 * <pre>
 *  
 * 描述：HrErrandsRegister业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class HrErrandsRegisterManager extends BaseManager<HrErrandsRegister> implements 
ProcessStartAfterHandler,TaskAfterHandler,ProcessEndHandler{
	@Resource
	private HrErrandsRegisterDao hrErrandsRegisterDao;
	@Resource
    BpmInstManager bpmInstManager;
	@Resource
	IdGenerator idGenerator;
	@Resource
	HrDutyInstManager hrDutyInstManager;
	@Resource
	BpmFormInstManager bpmFormInstManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return hrErrandsRegisterDao;
	}

	public void deleteCasecade(String proId){
		HrErrandsRegister hrErrandsRegister=get(proId);
		if(hrErrandsRegister!=null){
			if(StringUtils.isNotEmpty(hrErrandsRegister.getBpmInstId())){
				bpmInstManager.deleteCasecadeByActInstId(hrErrandsRegister.getBpmInstId(), "");
			}
			delete(proId);
		}
	}
	
	
	/**
	 * 通过Json更新请假的值
	 * @param json
	 * @param busKey
	 */
	public void updateFromJson(String json,String busKey){
		HrErrandsRegister hrErrandsRegister=get(busKey);
		if(hrErrandsRegister==null) return;
		HrErrandsRegister newHrErrandsRegister=getNewEntity(json, null);
		try {
			BeanUtil.copyNotNullProperties(hrErrandsRegister, newHrErrandsRegister);
		} catch (Exception e) {
			e.printStackTrace();
		}
		hrErrandsRegisterDao.update(hrErrandsRegister);
	}
	/**
	 * 2.任务审批完成时调用，用于更新请假的数据
	 */
	@Override
	public void taskAfterHandle(IExecutionCmd cmd,String nodeId,String busKey){
		updateFromJson(cmd.getJsonData(),busKey);
	}
	/**
	 * 3.流程成功审批完成时，对请假的审批状态进行更新
	 */
	@Override
	public void endHandle(BpmInst bpmInst) {
		String busKey=bpmInst.getBusKey();
		HrErrandsRegister hrErrandsRegister=hrErrandsRegisterDao.get(busKey);
		if(hrErrandsRegister!=null){
			hrErrandsRegister.setStatus(OStatus.APPROVED.name());
			HrTransRestExt tmpHrTransRestExt=null;
			BpmFormInst bpmFormInst=bpmFormInstManager.getByActInstId(bpmInst.getActInstId());
			String jsonData=bpmFormInst.getJsonData();
			JSONObject jsObject=JSONObject.fromObject(jsonData);
			String applyUserId=JSONUtil.getString(jsObject, "SQR");
			Iterator<HrTransRestExt> iterator=hrErrandsRegister.getHrTransRestExts().iterator();
			while (iterator.hasNext()) {
				tmpHrTransRestExt=iterator.next();
			}
			if(HrErrandsRegister.TYPE_TRANS_REST.equals(hrErrandsRegister.getType())&&"ZDTX".equals(tmpHrTransRestExt.getType())){
				 HrDutyInst oldDuty=hrDutyInstManager.getByUserIdAndOneDay(applyUserId, hrErrandsRegister.getEndTime());
				 HrDutyInst oldRest=hrDutyInstManager.getByUserIdAndOneDay(applyUserId, hrErrandsRegister.getStartTime());
				 Date oldDutyDate=oldDuty.getDate();
				 Date oldRestDate=oldRest.getDate();
				 
				oldDuty.setDate(oldRestDate);
				oldRest.setDate(oldDutyDate);
				hrDutyInstManager.update(oldDuty);
				hrDutyInstManager.update(oldRest);
			}
			else{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				boolean isIncludeEDate=true;
				Date sDate=hrErrandsRegister.getStartTime();
				Date eDate=hrErrandsRegister.getEndTime();
				Calendar cal=Calendar.getInstance();
				cal.setTime(eDate);
				if(cal.get(Calendar.HOUR)==0&&cal.get(Calendar.MINUTE)==0&&cal.get(Calendar.SECOND)==0)
					isIncludeEDate=false;
				Date formatSDate=null;
				Date formatEDate=null;
				try {
					formatSDate=sdf.parse(sdf.format(sDate));
					formatEDate=sdf.parse(sdf.format(eDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Calendar startCal=Calendar.getInstance();
				startCal.setTime(formatSDate);
				Calendar endCal=Calendar.getInstance();
				endCal.setTime(formatEDate);
				if(!isIncludeEDate)
					endCal.add(Calendar.DAY_OF_MONTH, -1);
				boolean bContinue=true;
				while (bContinue) {  
	            	Calendar tCal=startCal;
	            	if (!endCal.getTime().after(tCal.getTime())&&!endCal.getTime().equals(tCal.getTime())){  
	                    break;  
	                }
	            	else{
	            		HrDutyInst hrDutyInst=hrDutyInstManager.getByUserIdAndOneDay(applyUserId, tCal.getTime());
	            		if(hrDutyInst!=null){
	            			String type=hrErrandsRegister.getType();
		            		JSONArray jsonArray=new JSONArray();
		            		if(HrErrandsRegister.TYPE_VACATION.equals(type)){
		            			String jsonString=JSON.toJSONString(hrErrandsRegister);
		            			JSONObject jsonObject=JSONObject.fromObject(jsonString);
		            			jsonArray.add(jsonObject);
		            			hrDutyInst.setVacApp(jsonArray.toString());
		            		}
		            		if(HrErrandsRegister.TYPE_OVERTIME.equals(type)){
		            			String jsonString=JSON.toJSONString(hrErrandsRegister);
		            			JSONObject jsonObject=JSONObject.fromObject(jsonString);
		            			jsonArray.add(jsonObject);
		            			hrDutyInst.setOtApp(jsonArray.toString());
		            		}
		            		if(HrErrandsRegister.TYPE_TRANS_REST.equals(type)){
		            			String jsonString=JSON.toJSONString(hrErrandsRegister);
		            			JSONObject jsonObject=JSONObject.fromObject(jsonString);
		            			jsonArray.add(jsonObject);
		            			hrDutyInst.setTrApp(jsonArray.toString());
		            		}
		            		if(HrErrandsRegister.TYPE_OUT.equals(type)){
		            			String jsonString=JSON.toJSONString(hrErrandsRegister);
		            			JSONObject jsonObject=JSONObject.fromObject(jsonString);
		            			jsonArray.add(jsonObject);
		            			hrDutyInst.setOutApp(jsonArray.toString());
		            		}     
		            		hrDutyInstManager.update(hrDutyInst);
	            		}
	            		
	                }
	            	tCal.add(Calendar.DAY_OF_MONTH, 1);
	            }
			}
			hrErrandsRegisterDao.update(hrErrandsRegister);
		}
	}

	/**
	 * 通过Json数据创建请假申请数据
	 * @param json
	 * @param bpmInstId
	 * @return
	 */
	public HrErrandsRegister createFromJson(String json,String actInstId){
		HrErrandsRegister hrErrandsRegister=getNewEntity(json, actInstId);
		hrErrandsRegister.setStatus(OStatus.APPROVEDING.name());
		hrErrandsRegisterDao.create(hrErrandsRegister);
		return hrErrandsRegister;
	}
	
	public HrErrandsRegister getNewEntity(String json,String actInstId){
		HrErrandsRegister hrErrandsRegister=new HrErrandsRegister();
		Set<HrVacationExt> hrVacationExts=new HashSet<HrVacationExt>();
		Set<HrOvertimeExt> hrOvertimeExts=new HashSet<HrOvertimeExt>();
		Set<HrTransRestExt> hrTransRestExts=new HashSet<HrTransRestExt>();
		JSONObject jsonObject=JSONObject.fromObject(json); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime=JSONUtil.getString(jsonObject, "KSSJ");
		String endTime=JSONUtil.getString(jsonObject, "JSSJ");
		Date startDate=null;
		Date endDate=null;
		try {
			if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
				startDate = sdf.parse(startTime);
				endDate =sdf.parse(endTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		hrErrandsRegister.setStartTime(startDate);
		hrErrandsRegister.setEndTime(endDate);
		String type=JSONUtil.getString(jsonObject, "SQLX");
		if(type.equals(HrErrandsRegister.TYPE_VACATION)){
			String vacationType=JSONUtil.getString(jsonObject, "QJLX");
			String reason=JSONUtil.getString(jsonObject, "QJYY");
			String plan=JSONUtil.getString(jsonObject, "GZAP");
			HrVacationExt hrVacationExt=new HrVacationExt();
			hrVacationExt.setVacId(idGenerator.getSID());
			hrVacationExt.setWorkPlan(plan);
			hrVacationExt.setType(vacationType);
			hrVacationExt.setHrErrandsRegister(hrErrandsRegister);
			hrVacationExts.add(hrVacationExt);
			hrErrandsRegister.setReason(reason);
			hrErrandsRegister.setType(HrErrandsRegister.TYPE_VACATION);
			hrErrandsRegister.setHrVacationExts(hrVacationExts);
		}
		if(type.equals(HrErrandsRegister.TYPE_OVERTIME)){
			String otType=JSONUtil.getString(jsonObject, "JBLX");
			String reason=JSONUtil.getString(jsonObject, "JBYY");
			String desc=JSONUtil.getString(jsonObject, "BZ");
			String pay=JSONUtil.getString(jsonObject, "JSLX");
			String title=JSONUtil.getString(jsonObject, "BT");
			HrOvertimeExt hrOvertimeExt=new HrOvertimeExt();
			hrOvertimeExt.setOtId(idGenerator.getSID());
			hrOvertimeExt.setDesc(desc);
			hrOvertimeExt.setType(otType);
			hrOvertimeExt.setPay(pay);
			hrOvertimeExt.setTitle(title);
			hrOvertimeExt.setHrErrandsRegister(hrErrandsRegister);
			hrOvertimeExts.add(hrOvertimeExt);
			hrErrandsRegister.setReason(reason);
			hrErrandsRegister.setType(HrErrandsRegister.TYPE_OVERTIME);
			hrErrandsRegister.setHrOvertimeExts(hrOvertimeExts);
		}
		if(type.equals(HrErrandsRegister.TYPE_TRANS_REST)){
			String trType=JSONUtil.getString(jsonObject, "DXLX");
			String reason=JSONUtil.getString(jsonObject, "DXYY");
			HrTransRestExt hrTransRestExt=new HrTransRestExt();
			hrTransRestExt.setTrId(idGenerator.getSID());
			if("ZDTX".equals(trType)){
				String xxr=JSONUtil.getString(jsonObject, "XXR");
				String sbr=JSONUtil.getString(jsonObject, "SBR");
				Date xxDate=null;
				Date sbDate=null;
				SimpleDateFormat tmpSdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					xxDate = tmpSdf.parse(xxr);
					sbDate =tmpSdf.parse(sbr);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				hrTransRestExt.setWork(sbDate);
				hrTransRestExt.setRest(xxDate);
				hrErrandsRegister.setStartTime(xxDate);
				hrErrandsRegister.setEndTime(sbDate);
			}
			hrTransRestExt.setType(trType);
			hrTransRestExt.setHrErrandsRegister(hrErrandsRegister);
			hrTransRestExts.add(hrTransRestExt);
			hrErrandsRegister.setReason(reason);
			hrErrandsRegister.setType(HrErrandsRegister.TYPE_TRANS_REST);
			hrErrandsRegister.setHrTransRestExts(hrTransRestExts);;
		}
		

		if(StringUtils.isNotEmpty(actInstId))
			hrErrandsRegister.setBpmInstId(actInstId);
		
		return hrErrandsRegister;
	}
	
	/**
	 * 通过流程实例创建完成后通过表单的数据创建请假信息
	 */
//	@Override
	public String processStartAfterHandle(String json, String actInstId) {
		HrErrandsRegister hrErrandsRegister=createFromJson(json,actInstId);
		return hrErrandsRegister.getErId();
	}
	
	
	public List<HrErrandsRegister> getByUserIdAndType(String userId,String type,String tenantId,QueryFilter queryFilter){
		return hrErrandsRegisterDao.getByUserIdAndType(userId, type, tenantId,queryFilter);
	}
	
	/*public List<HrErrandsRegister> getByUserIdAndStatusAndStartTimeAndEndTime(String userId,String status,Date startDate,Date endDate){
		return hrErrandsRegisterDao.getByUserIdAndStatusAndStartTimeAndEndTime(userId,status,startDate,endDate);
	}*/
	
	public List<HrErrandsRegister> getByUserIdAndStatusAndDate(String userId,String status,Date date){
		return hrErrandsRegisterDao.getByUserIdAndStatusAndDate(userId, status, date);
	}

	public String processStartAfterHandle(ProcessConfig processConfig, ProcessStartCmd cmd, BpmInst bpmInst) {
		// TODO Auto-generated method stub
		return null;
	}
}