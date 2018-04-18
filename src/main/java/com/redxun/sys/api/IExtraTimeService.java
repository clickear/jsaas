package com.redxun.sys.api;

import java.util.Date;
import java.util.List;

/**
 * 请假和加班接口获取默认在某段时间的加班和请假情况。
 * @author ray
 *
 */
public interface IExtraTimeService {
	
	/**
	 * 指定区间获取加班或请假的情况。
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<ExtraTimeModel> getByUser(String userId,Date startTime,Date endTime);
	
	/**
	 * 指定区间获取加班或请假的情况。
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<ExtraTimeModel> getByGroup(String groupId,Date startTime,Date endTime);
	
	/**
	 * 从某个时间开始之后的所有加班和请假
	 * @param userId
	 * @param startTime
	 * @return
	 */
	List<ExtraTimeModel> getByUserAndStartTime(String userId,Date startTime);
	/**
	 * 从某个时间开始之后的所有加班和请假
	 * @param userId
	 * @param startTime
	 * @return
	 */
	List<ExtraTimeModel> getByGroupAndStartTime(String groupId,Date startTime);
	
	
	/**
	 * 查询结束时间大于此时间,但开始时间小于此时间的extraBlock
	 * @param userId
	 * @param startTime
	 * @return
	 */
	ExtraTimeModel getBlockBetweenEndAndStart(String userId,Date startTime);
	/**
	 * 查询结束时间大于此时间,但开始时间小于此时间的extraBlock
	 * @param userId
	 * @param startTime
	 * @return
	 */
	ExtraTimeModel getGroupBlockBetweenEndAndStart(String groupId,Date startTime);
}
