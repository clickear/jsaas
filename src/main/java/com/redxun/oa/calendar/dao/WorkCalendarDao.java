package com.redxun.oa.calendar.dao;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl.Work;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.calendar.entity.WorkCalendar;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：WorkCalendar数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class WorkCalendarDao extends BaseJpaDao<WorkCalendar> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return WorkCalendar.class;
    }
    
    /**
     * 获取工作时间区间
     * @param startTime
     * @param endTime
     * @param tenantId
     * @return
     */
    public List<WorkCalendar> getCalendarsLimitByDate(Date startTime,Date endTime,String tenantId,String settingId){
    	String hql="from WorkCalendar wc where  wc.startTime>=? and  wc.startTime<=? and wc.tenantId=? and wc.calSetting.settingId=?";
    	return this.getByJpql(hql,startTime,endTime,tenantId,settingId);
    }
    
    /**
     * 获得开始日期在那一天的工作日历
     * @param date
     * @param tenantId
     * @return
     */
    public List<WorkCalendar> getCalendarInDay(Date date,String tenantId){
    	Calendar   calendar   =   new   GregorianCalendar(); 
        calendar.setTime(date); 
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
        Date nextDay=calendar.getTime(); 
    	String hql="from WorkCalendar where startTime>? and startTime<=? and tenantId=?";
    	return this.getByJpql(hql, date,nextDay,tenantId);
    }
    
    /**
     * 在CalSetting的settingId下获得开始日期在那一天的工作日历
     * @param date
     * @param tenantId
     * @param settingId
     * @return
     */
    public List<WorkCalendar> getCalendarInDayBySettingId(Date date,String tenantId,String settingId){
	    Calendar paramCalendar =new GregorianCalendar();
	    paramCalendar.setTime(date);
	    paramCalendar.set(Calendar.HOUR_OF_DAY, 0);
	    paramCalendar.set(Calendar.MINUTE, 0);
	    paramCalendar.set(Calendar.SECOND, 0);
	    paramCalendar.set(Calendar.MILLISECOND, 0);
	    Date thisDay=paramCalendar.getTime();
    	Calendar   calendar   =   new   GregorianCalendar(); 
        calendar.setTime(thisDay); 
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
        Date nextDay=calendar.getTime(); 
    	String hql="from WorkCalendar wc where wc.startTime>? and wc.startTime<=? and wc.tenantId=? and wc.calSetting.settingId=?";
    	return this.getByJpql(hql, thisDay,nextDay,tenantId,settingId);
    }
    
    /**
     * 寻找从某个Date开始的某个settingId下的工作日历列表
     * @param date
     * @param settingId
     * @return
     */
    public List<WorkCalendar> getByStartDateAndSettingId(Date date,String settingId){
    	String tenantId=ContextUtil.getCurrentTenantId();
    	String hql="from WorkCalendar wc where wc.startTime>=? and wc.calSetting.settingId=? and wc.tenantId=?  order by wc.startTime asc";
    	
    	return this.getByJpql(hql, date,settingId,tenantId);
    }
    
    /**
     * 寻找date是否在某个WorkCalendar区间内,在的话获得workCalendar
     * @param date
     * @return
     */
    public List<WorkCalendar> getTimeBlock(Date date,String settingId){
    	String hql="from WorkCalendar wc where wc.startTime<=? and wc.endTime>? and wc.calSetting.settingId=? and wc.tenantId=? order by wc.startTime asc ";
    	String tenantId=ContextUtil.getCurrentTenantId();
    	return  this.getByJpql(hql, date,date,settingId,tenantId);
    }
    
    /**
     * 通过开始时间结束时间以及所属于的日历设定获取workCalendar
     * @param startDate
     * @param endDate
     * @param settingId
     * @return
     */
    public List<WorkCalendar> getByStartAndEndAndSettingId(Date startDate,Date endDate,String settingId){
    	String hql="from WorkCalendar wc where wc.calSetting.settingId=? and wc.startTime>=? and ?>=wc.endTime and wc.tenantId=? order by wc.startTime asc";
    	String tenantId=ContextUtil.getCurrentTenantId();
    	return this.getByJpql(hql, settingId,startDate,endDate,tenantId);
    }
    
    /**
     * 获取能包裹这个startTime和endtTime的稍微大一点的时间碎片
     * @param startDate
     * @param endDate
     * @param settingId
     * @return
     */
    public WorkCalendar getBiggerBlockThanStartAndEnd(Date startDate,Date endDate,String settingId){
    	String hql="from WorkCalendar wc where wc.calSetting.settingId=? and wc.startTime<=? and wc.endTime>=? and wc.tenantId=? ";
    	String tenantId=ContextUtil.getCurrentTenantId();
    	return (WorkCalendar) this.getUnique(hql, settingId,startDate,endDate,tenantId);
    }
    /**
     *|--- I*****|----------------------I
     *[*****]区间
     * @param date
     * @param settingId
     * @return
     */
    public WorkCalendar getBlockBetweenEndAndStart(Date date,String settingId){
    	String tenantId=ContextUtil.getCurrentTenantId();
    	String hql="from WorkCalendar wc where wc.calSetting.settingId=? and wc.startTime<?  and ?<wc.endTime and  wc.tenantId=?  order by wc.startTime asc";
    	return (WorkCalendar) this.getUnique(hql,settingId, date,date,tenantId);
    }
    
    
    /**
     * I-----------------|*****I----|
     *[*****]区间
     * @param date
     * @param settingId
     * @return
     */
    public WorkCalendar getBlockBetweenStartAndEnd(Date date,String settingId){
    	String tenantId=ContextUtil.getCurrentTenantId();
    	String hql="from WorkCalendar wc where wc.calSetting.settingId=? and wc.startTime>? and ?>wc.endTime order by wc.startTime asc";
    	return (WorkCalendar) this.getUnique(hql,settingId,date,date,tenantId);
    }
    
}
