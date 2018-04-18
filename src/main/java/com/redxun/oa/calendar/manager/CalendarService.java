package com.redxun.oa.calendar.manager;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redxun.core.util.StringUtil;
import com.redxun.oa.calendar.entity.CalGrant;
import com.redxun.oa.calendar.entity.CalSetting;
import com.redxun.oa.calendar.entity.WorkCalendar;
import com.redxun.sys.api.ExtraTimeModel;
import com.redxun.sys.api.ICalendarService;
import com.redxun.sys.api.IExtraTimeService;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsUserManager;
/**
 * 实现ICalendarService
 * @author Administrator
 *
 */
@Service
public class CalendarService implements ICalendarService {
	
	@Resource
	OsUserManager osUserManager;
	@Resource
	OsGroupManager osGroupManager;
	@Resource
	CalGrantManager calGrantManager;
	@Resource
	WorkCalendarManager workCalendarManager;
	@Resource
	CalSettingManager calSettingManager;
	@Autowired(required=false)
	IExtraTimeService extraTimeService;
	
	/**
	 * 根据用户获取日历ID 。
	 * @param userId
	 * @return
	 */
	private String getByUserId(String userId){
		OsUser osUser=osUserManager.get(userId);
		String tenantId=osUser.getTenantId();
		CalGrant calGrant=calGrantManager.getByGroupIdOrUserId("USER", userId,tenantId);
		if(calGrant!=null){
			return calGrant.getCalSetting().getSettingId();
		}
		String settingId="";
		
		List<OsGroup> osGroups=osGroupManager.getBelongGroups(userId);
		for (OsGroup osGroup : osGroups) {
			CalGrant thisCalGrant=calGrantManager.getByGroupIdOrUserId("GROUP", osGroup.getGroupId(),tenantId);
			if(thisCalGrant!=null){
				calGrant=thisCalGrant;
				settingId=calGrant.getCalSetting().getSettingId();
				break;
			}
		}
		if(calGrant==null){//如果最后也没找到组织的日历,则查找系统公共日历
			CalSetting calSetting=calSettingManager.getDefault();
			if(calSetting!=null){
				settingId=calSetting.getSettingId();
			}
			
		}
		
		return settingId;
	}
	
	/**
	 * 根据用户组获取日历ID 。
	 * @param userId
	 * @return
	 */
	private String getByGroupId(String groupId){
		OsGroup osGroup=osGroupManager.get(groupId);
		String tenantId=osGroup.getTenantId();
		CalGrant calGrant=calGrantManager.getByGroupIdOrUserId("GROUP", groupId,tenantId);
		if(calGrant!=null){
			return calGrant.getCalSetting().getSettingId();
		}
		String settingId="";
		
		if(calGrant==null){//如果最后也没找到组织的日历,则查找系统公共日历
			CalSetting calSetting=calSettingManager.getDefault();
			if(calSetting!=null){
				settingId=calSetting.getSettingId();
			}
		}
		
		return settingId;
	}
	
	
	/**
	 * 日历排序。
	 * @param calendarList
	 */
	private void sortCalendar(List<WorkCalendar> calendarList ){
		Collections.sort(calendarList, new Comparator<Object>() {  //排列出大小
            public int compare(Object o1, Object o2) {  
            	WorkCalendar workCalendar1=(WorkCalendar) o1;
            	WorkCalendar workCalendar2=(WorkCalendar) o2;
                if (workCalendar1.getStartTime().getTime()>workCalendar2.getStartTime().getTime()) {  
                    return 1;  
                }  
                if (workCalendar1.getStartTime().getTime()<workCalendar2.getStartTime().getTime()) {  
                    return -1;  
                }  
                return 0;  
            }  
        });
	}
	
	/**
	 * 获取最小的起始时间段。
	 * @param calendarList
	 * @return
	 */
	private WorkCalendar getMin(List<WorkCalendar> calendarList){
		WorkCalendar startBlock=Collections.min(calendarList, new Comparator<Object>() {  //比较出集合开始时间最早的元素
	        public int compare(Object o1, Object o2) {  
	        	WorkCalendar workCalendar1=(WorkCalendar) o1;
	        	WorkCalendar workCalendar2=(WorkCalendar) o2;
	            if (workCalendar1.getStartTime().getTime()>workCalendar2.getStartTime().getTime()) {  
	                return 1;  
	            }  
	            if (workCalendar1.getStartTime().getTime()<workCalendar2.getStartTime().getTime()) {  
	                return -1;  
	            }  
	            return 0;  
	        }  
	    });
		return startBlock;
	}
	
	
	
	
	/**
	 * 根据用户ID,开始时间和指定有效工作时间获取将来的时间点。
	 * <pre>
	 *  1.查找个人的日历。
	 *  2.如果找不到，则查询组织的日历。
	 *  3.如果找不到则获取系统配置的默认日历。
	 *  4.如果没有日历则直接按照 直接进行计算。
	 *  
	 *  需要考虑加班和请假时间。
	 * </pre>
	 * @param userId
	 * @param startTime
	 * @param minite
	 * @return
	 * @throws Exception
	 */
	@Override
	public Date getByUserId(String userId, Date startTime, int minute) throws Exception {
		WorkCalendar resultCalendar=null;//结果日历区间
		Long startTimeMs=startTime.getTime();//开始时间的时间戳
		
		String settingId=getByUserId( userId);
		
		if(StringUtil.isEmpty(settingId)) {
			return  new Timestamp(startTimeMs+(long)minute*60000);
		}
		
		
		//经过上一轮洗牌,基本都能找到分配的日历,如果还是null,则按照24小时制度进行计算
		List<WorkCalendar> workCalendars=workCalendarManager.getByStartDateAndSettingId(startTime, settingId);
		long lastMinutes=0;
		if(workCalendars.size()>0){
			for (WorkCalendar workCalendar : workCalendars) {
				lastMinutes+=(workCalendar.getEndTime().getTime()-workCalendar.getStartTime().getTime())/60000;
			}
		}else{
			return new Timestamp(startTimeMs+(long)minute*60000);
		}
		WorkCalendar workCalendarBefore=workCalendarManager.getBlockBetweenEndAndStart(startTime, settingId);
		if(workCalendarBefore!=null){
			lastMinutes+=(workCalendarBefore.getEndTime().getTime()-startTime.getTime())/60000;
		}
		
		//获取加班
		if(extraTimeService!=null){
			List<ExtraTimeModel> overTime=extraTimeService.getByUserAndStartTime(userId, startTime);
			for (ExtraTimeModel extraTimeModel : overTime) {
				if(extraTimeModel.getIsPositive()){
					lastMinutes+=(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/600000;
				}
			}
			
			ExtraTimeModel overBeforeTime=extraTimeService.getBlockBetweenEndAndStart(userId, startTime);
			if(overBeforeTime!=null){
				lastMinutes+=(overBeforeTime.getEndTime().getTime()-startTime.getTime())/60000;
			}
			
			
		}
		if(lastMinutes<minute){
			throw new Exception("日历配置时间不够用于计算,请调整日历配置!");
		}
			
		//////////////////////////////////////////////////////////////////////////////////////
		List<WorkCalendar> dayWorkCalendars=workCalendarManager.getTimeBlock(startTime,settingId);
		if(dayWorkCalendars.size()>0){
			int difTimeMinutes=(int) ((dayWorkCalendars.get(0).getEndTime().getTime()-startTime.getTime())/60000);
			minute=minute-difTimeMinutes;//修正是否startTime在当天时间段安排内
			if(minute<0){return new Timestamp(dayWorkCalendars.get(0).getEndTime().getTime()+minute*60000);}//如果没有超过一天则返回当个区间加上minute的时间戳
		}
		List<WorkCalendar> calendars=workCalendarManager.getByStartDateAndSettingId(startTime, settingId);//此日期后的这个设定下的所有日历区间
		/*加班时间
		 * 将某个开始点以后的加班时间以workCalendar的实体形式插入到calendars里再在最后进行排序
		 * 排序后可以按照日历之前的按顺序用lastMinutes减法计算直到lastMinutes<0则是哪个日期
		 * */
		if(extraTimeService!=null){
			
			List<ExtraTimeModel> extraTimeModels=extraTimeService.getByUserAndStartTime(userId, startTime);
			for (ExtraTimeModel extraTimeModel : extraTimeModels) {
				if(extraTimeModel.getIsPositive()){
					WorkCalendar workCalendar=new WorkCalendar();
					workCalendar.setStartTime(extraTimeModel.getStartTime());
					workCalendar.setEndTime(extraTimeModel.getEndTime());
					calendars.add(workCalendar);
				}
			}
		}
		
		//取最小的日历
		 WorkCalendar startBlock= getMin(calendars);
		 List<WorkCalendar> resultBlock=workCalendarManager.getByStartDateAndSettingId(startBlock.getStartTime(), settingId);
		 /*计算加班
		  * 将塞入了加班的日历碎片进行重新检索找出包括加班在内的最早时间,再按这个时间再次检索所有日历和加班时间碎片,方法同上
		  * */
		 if(extraTimeService!=null){
				List<ExtraTimeModel> extraTimeModels=extraTimeService.getByUserAndStartTime(userId, startBlock.getStartTime());
				for (ExtraTimeModel extraTimeModel : extraTimeModels) {
					if(extraTimeModel.getIsPositive()){
						WorkCalendar workCalendar=new WorkCalendar();
						workCalendar.setStartTime(extraTimeModel.getStartTime());
						workCalendar.setEndTime(extraTimeModel.getEndTime());
						resultBlock.add(workCalendar);
					}
				}
				//将开始点夹在某个加班区间开始与结束的的加班区间初始化成传入的startTime和这个结束时间,以workCalendar的形式假如resultBlock参与计算
				ExtraTimeModel overBeforeTime=extraTimeService.getBlockBetweenEndAndStart(userId, startTime);
				if(overBeforeTime!=null){
					WorkCalendar beforeWorkCalendar=new WorkCalendar();
					beforeWorkCalendar.setStartTime(new Timestamp(startTime.getTime()));
					beforeWorkCalendar.setEndTime(overBeforeTime.getEndTime());
					resultBlock.add(beforeWorkCalendar);
				}
				
		 }
		 sortCalendar(resultBlock);
		 
		 
		 
		 for (int i = 0; i < resultBlock.size(); i++) {
			 int sub=(int) ((resultBlock.get(i).getEndTime().getTime()-resultBlock.get(i).getStartTime().getTime())/60000);
			 if(extraTimeService!=null){//减去请假的时长
				 List<ExtraTimeModel> thisBlock=extraTimeService.getByUser(userId, resultBlock.get(i).getStartTime(), resultBlock.get(i).getEndTime());
				 for (ExtraTimeModel extraTimeModel : thisBlock) {
					sub=(int) (sub-(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/60000);
				}
			 }
			 if(minute>0){
				 int tempMinute=minute;
				 minute=minute-sub;
				 if(minute<=0){
					 resultCalendar=resultBlock.get(i);
					 return new Timestamp(resultCalendar.getStartTime().getTime()+tempMinute*60000);
				 }
			 }else{
				 resultCalendar=resultBlock.get(i);
				 break;
			 }
			
		}
		if(resultCalendar!=null){
			return resultCalendar.getStartTime();
		}
			
		return null;
	}

	/**
	 * 根据指定的日历Id，开始时间，结束时间计算中间所花费的实际时间。
	 * <pre>
	 *  需要考虑加班和请假时间。
	 * </pre>
	 * @param settingId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception 
	 */
	@Override
	public int getActualTimeCalenderId(String settingId, Date beginDate, Date endDate) throws Exception {
		CalSetting calSetting=calSettingManager.get(settingId);
		if(calSetting==null){
			throw new Exception();
		}
		List<WorkCalendar> workCalendars=workCalendarManager.getByStartAndEndAndSettingId(beginDate, endDate, settingId);
		int result=0;
		for (WorkCalendar workCalendar : workCalendars) {
			result+=(workCalendar.getEndTime().getTime()-workCalendar.getStartTime().getTime())/60000;//计算中间
		}
		WorkCalendar workCalendar=workCalendarManager.getBlockBetweenEndAndStart(beginDate,settingId);
		if(workCalendar!=null){
			result+=(workCalendar.getEndTime().getTime()-beginDate.getTime())/60000;
		}//计算首部
		
		WorkCalendar workCalendar2=workCalendarManager.getBlockBetweenStartAndEnd(endDate,settingId);
		if(workCalendar2!=null){
			result+=(endDate.getTime()-workCalendar2.getStartTime().getTime())/60000;
		}//计算尾部
		
		//如果只有一个内部的区间则重新计算result
		WorkCalendar littleCalendar=workCalendarManager.getBiggerBlockThanStartAndEnd(beginDate, endDate, settingId);
		if(littleCalendar!=null){//如果有则代表这个小区间只在某个区间内
			result=(int) ((endDate.getTime()-beginDate.getTime())/60000);
		}
		return result;
	}

	/**
	 * 根日历Id,开始时间和指定有效工作时间获取将来的时间点。
	 * @param calendarId
	 * @param startTime
	 * @param minite
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Date getByCalendarId(String settingId, Date startTime, int minute) throws Exception {
			WorkCalendar resultCalendar=null;//结果日历区间
			List<WorkCalendar> workCalendars=workCalendarManager.getByStartDateAndSettingId(startTime, settingId);
			int lastMinutes=0;
			if(workCalendars.size()>0){
				for (WorkCalendar workCalendar : workCalendars) {
					lastMinutes+=(workCalendar.getEndTime().getTime()-workCalendar.getStartTime().getTime())/60000;
				}
			}
			
				WorkCalendar workCalendar=workCalendarManager.getBlockBetweenEndAndStart(startTime, settingId);
				if(workCalendar!=null){
					lastMinutes+=(workCalendar.getEndTime().getTime()-startTime.getTime())/60000;
				}
			//////////////////////////////////////////////////////////////////////////////////////
			if(lastMinutes>=minute){//总区间大于可计算区间
				List<WorkCalendar> dayWorkCalendars=workCalendarManager.getTimeBlock(startTime,settingId);
				if(dayWorkCalendars.size()>0){
					int difTimeMinutes=(int) ((dayWorkCalendars.get(0).getEndTime().getTime()-startTime.getTime())/60000);
					minute=minute-difTimeMinutes;//修正是否startTime在当天时间段安排内
					if(minute<0){return new Timestamp(dayWorkCalendars.get(0).getEndTime().getTime()+minute*60000);}//如果没有超过一天则返回当个区间加上minute的时间戳
				}
					List<WorkCalendar> calendars=workCalendarManager.getByStartDateAndSettingId(startTime, settingId);//此日期后的这个设定下的所有日历区间
					WorkCalendar startBlock=Collections.min(calendars, new Comparator<Object>() {  //比较出集合开始时间最早的元素
				            public int compare(Object o1, Object o2) {  
				            	WorkCalendar workCalendar1=(WorkCalendar) o1;
				            	WorkCalendar workCalendar2=(WorkCalendar) o2;
				                if (workCalendar1.getStartTime().getTime()>workCalendar2.getStartTime().getTime()) {  
				                    return 1;  
				                }  
				                if (workCalendar1.getStartTime().getTime()<workCalendar2.getStartTime().getTime()) {  
				                    return -1;  
				                }  
				                return 0;  
				            }  
				        });
					 List<WorkCalendar> resultBlock=workCalendarManager.getByStartDateAndSettingId(startBlock.getStartTime(), settingId);
					 for (int i = 0; i < resultBlock.size(); i++) {
						 int sub=(int) ((resultBlock.get(i).getEndTime().getTime()-resultBlock.get(i).getStartTime().getTime())/60000);
						 if(minute>0){
							 int tempMinute=minute;
							 minute=minute-sub;
							 if(minute<=0){
								 resultCalendar=resultBlock.get(i);
								 return new Timestamp(resultCalendar.getStartTime().getTime()+tempMinute*60000);
							 }
						 }else{
							 resultCalendar=resultBlock.get(i);
							 break;
						 }
						
					}
			}
			if(resultCalendar!=null){
				return resultCalendar.getStartTime();
			}else{
				throw new Exception("日历并不够长");
			}
			
		
		
	}

	/**
	 * 根据用户ID，开始时间，结束时间获取中间所花费的实际时间，时间单位为分钟。
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@Override
	public int getActualTimeUserId(String userId, Date beginDate, Date endDate) {
		
		String settingId= getByUserId( userId);
		
		if(StringUtils.isNotBlank(settingId)){//经过上一轮洗牌,基本都能找到分配的日历,如果还是null,则按照24小时制度进行计算
			
			List<WorkCalendar> workCalendars=workCalendarManager.getByStartAndEndAndSettingId(beginDate, endDate, settingId);
			int result=0;
			boolean calculated=false;
				WorkCalendar biggerBlock=workCalendarManager.getBiggerBlockThanStartAndEnd(beginDate, endDate, settingId);
				if(biggerBlock!=null) {
					result+=(endDate.getTime()-beginDate.getTime())/60000;
					calculated=true;
				}
			
			for (WorkCalendar workCalendar : workCalendars) {
				result+=(workCalendar.getEndTime().getTime()-workCalendar.getStartTime().getTime())/60000;//计算中间
			}
			WorkCalendar workCalendar=workCalendarManager.getBlockBetweenEndAndStart(beginDate,settingId);
			if(workCalendar!=null&&!calculated){
				result+=(workCalendar.getEndTime().getTime()-beginDate.getTime())/60000;
			}//计算首部
			
			WorkCalendar workCalendar2=workCalendarManager.getBlockBetweenStartAndEnd(endDate,settingId);
			if(workCalendar2!=null&&!calculated){
				result+=(endDate.getTime()-workCalendar2.getStartTime().getTime())/60000;
			}//计算尾部
			
			if(extraTimeService!=null){
				List<ExtraTimeModel> computeBlocks=extraTimeService.getByUser(userId, beginDate, endDate);
				for (ExtraTimeModel extraTimeModel : computeBlocks) {
					if(extraTimeModel.getIsPositive()){//如果是加班则加时间
						result+=(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/60000;
					}else{
						result-=(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/60000;
					}
				}
			}
			return result;
		}else{//否则按照正常时间计算
			int subResultTime=(int) ((endDate.getTime()-beginDate.getTime())/60000);
			if(extraTimeService!=null){
				List<ExtraTimeModel> computeBlocks=extraTimeService.getByUser(userId, beginDate, endDate);
				for (ExtraTimeModel extraTimeModel : computeBlocks) {
					if(extraTimeModel.getIsPositive()){//如果是加班则加时间
						subResultTime+=(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/60000;
					}else{
						subResultTime-=(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/60000;
					}
				}
			}
			return subResultTime;
		}
		
	}
	
	/**
	 * 根据用户ID，开始时间，结束时间获取中间所花费的实际时间，时间单位为分钟。
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public int getActualTimeGroupId(String groupId, Date beginDate, Date endDate) {
		
		String settingId= getByGroupId(groupId);
		
		if(StringUtils.isNotBlank(settingId)){//经过上一轮洗牌,基本都能找到分配的日历,如果还是null,则按照24小时制度进行计算
			
			List<WorkCalendar> workCalendars=workCalendarManager.getByStartAndEndAndSettingId(beginDate, endDate, settingId);
			int result=0;
			for (WorkCalendar workCalendar : workCalendars) {
				result+=(workCalendar.getEndTime().getTime()-workCalendar.getStartTime().getTime())/60000;//计算中间
			}
			WorkCalendar workCalendar=workCalendarManager.getBlockBetweenEndAndStart(beginDate,settingId);
			if(workCalendar!=null){
				result+=(workCalendar.getEndTime().getTime()-beginDate.getTime())/60000;
			}//计算首部
			
			WorkCalendar workCalendar2=workCalendarManager.getBlockBetweenStartAndEnd(endDate,settingId);
			if(workCalendar2!=null){
				result+=(endDate.getTime()-workCalendar2.getStartTime().getTime())/60000;
			}//计算尾部
			
			if(extraTimeService!=null){
				List<ExtraTimeModel> computeBlocks=extraTimeService.getByGroup(groupId, beginDate, endDate);
				for (ExtraTimeModel extraTimeModel : computeBlocks) {
					if(extraTimeModel.getIsPositive()){//如果是加班则加时间
						result+=(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/60000;
					}else{
						result-=(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/60000;
					}
				}
			}
			return result;
		}else{//否则按照正常时间计算
			int subResultTime=(int) ((endDate.getTime()-beginDate.getTime())/60000);
			if(extraTimeService!=null){
				List<ExtraTimeModel> computeBlocks=extraTimeService.getByGroup(groupId, beginDate, endDate);
				for (ExtraTimeModel extraTimeModel : computeBlocks) {
					if(extraTimeModel.getIsPositive()){//如果是加班则加时间
						subResultTime+=(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/60000;
					}else{
						subResultTime-=(extraTimeModel.getEndTime().getTime()-extraTimeModel.getStartTime().getTime())/60000;
					}
				}
			}
			return subResultTime;
		}
		
	}

}
