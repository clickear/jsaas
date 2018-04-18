package com.redxun.sys.api;

import java.util.Date;

/**
 * 日历计算接口。
 * <pre>
 * 	1.根据开始时间和指定的有效时间计算结束时间。
 *  2.根据开始时间和结束时间计算有效的工作时间。
 * </pre>
 * @author redxun
 *
 */
public interface ICalendarService {
	
	/**
	 * 根据用户，开始时间和有效的时长获取将来的时间点。
	 * <pre>
	 *  1.查找个人的日历。
	 *  2.如果找不到，则查询组织的日历。
	 *  3.如果找不到则获取系统配置的默认日历。
	 *  4.如果没有日历则直接按照 直接进行计算。
	 *  需要考虑加班和请假时间。
	 * </pre>
	 * @param userId
	 * @param startTime
	 * @param minute
	 * @return
	 * @throws Exception 
	 */
	Date getByUserId(String userId,Date startTime,int minute) throws Exception;
	
	/**
	 * 根日历Id,开始时间和指定有效工作时间获取将来的时间点。
	 * @param calendarId
	 * @param startTime
	 * @param minite
	 * @return
	 * @throws Exception 
	 */
	Date getByCalendarId(String calendarId,Date startTime,int minute) throws Exception;
	
	
	
	
	
	/**
	 * 根据指定的calendarId，开始时间，结束时间计算中间所花费的实际时间,时间单位为分钟
	 * <pre>
	 *  需要考虑加班和请假时间。
	 * </pre>
	 * @param calendarId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception 
	 */
	int getActualTimeCalenderId(String calendarId,Date beginDate,Date endDate) throws Exception;
	
	
	/**
	 * 根据用户ID，开始时间，结束时间获取中间所花费的实际时间，时间单位为分钟。
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	int getActualTimeUserId(String userId,Date beginDate,Date endDate);
	
}
