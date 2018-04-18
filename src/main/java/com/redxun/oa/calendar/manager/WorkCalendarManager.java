package com.redxun.oa.calendar.manager;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.calendar.dao.WorkCalendarDao;
import com.redxun.oa.calendar.entity.CalSetting;
import com.redxun.oa.calendar.entity.WorkCalendar;
/**
 * <pre> 
 * 描述：WorkCalendar业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class WorkCalendarManager extends BaseManager<WorkCalendar>{
	@Resource
	private WorkCalendarDao workCalendarDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return workCalendarDao;
	}
	 /**
     * 获取工作时间区间
     * @param startTime
     * @param endTime
     * @param tenantId
     * @return
     */
    public List<WorkCalendar> getCalendarsLimitByDate(Date startTime,Date endTime,String tenantId,String settingId){
    	return workCalendarDao.getCalendarsLimitByDate(startTime, endTime, tenantId,settingId);
    }
    
    /**
     * 获得开始日期在那一天的工作日历
     * @param date
     * @param tenantId
     * @return
     */
    public List<WorkCalendar> getCalendarInDay(Date date,String tenantId){
    	return workCalendarDao.getCalendarInDay(date, tenantId);
    }
    /**
     * 在CalSetting的settingId下获得开始日期在那一天的工作日历
     * @param date
     * @param tenantId
     * @param settingId
     * @return
     */
    public List<WorkCalendar> getCalendarInDayBySettingId(Date date,String tenantId,String settingId){
    	return workCalendarDao.getCalendarInDayBySettingId(date, tenantId, settingId);
    }
    
    /**
     * 寻找从某个Date开始的某个settingId下的工作日历列表
     * @param date
     * @param settingId
     * @return
     */
    public List<WorkCalendar> getByStartDateAndSettingId(Date date,String settingId){
    	return workCalendarDao.getByStartDateAndSettingId(date,settingId);
    }
    
    /**
     * 寻找date是否在某个WorkCalendar区间内,在的话获得workCalendar
     * @param date
     * @return
     */
    public List<WorkCalendar> getTimeBlock(Date date,String settingId){
    	return workCalendarDao.getTimeBlock(date,settingId);
    }
    
    /**
     * 通过开始时间结束时间以及所属于的日历设定获取workCalendar
     * @param startDate
     * @param endDate
     * @param settingId
     * @return
     */
    public List<WorkCalendar> getByStartAndEndAndSettingId(Date startDate,Date endDate,String settingId){
    	return workCalendarDao.getByStartAndEndAndSettingId(startDate, endDate, settingId);
    }
    
    public WorkCalendar getBlockBetweenEndAndStart(	Date date,String settingId){
    	return workCalendarDao.getBlockBetweenEndAndStart(date,settingId);
    }
    
    public WorkCalendar getBlockBetweenStartAndEnd(	Date date,String settingId){
    	return workCalendarDao.getBlockBetweenEndAndStart(date,settingId);
    }
    /**
     * 获取能包裹这个startTime和endtTime的稍微大一点的时间碎片
     * @param startDate
     * @param endDate
     * @param settingId
     * @return
     */
    public WorkCalendar getBiggerBlockThanStartAndEnd(Date startDate,Date endDate,String settingId){
    	return workCalendarDao.getBiggerBlockThanStartAndEnd(startDate, endDate, settingId);
    }
    
    

}