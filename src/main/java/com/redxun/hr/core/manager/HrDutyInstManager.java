package com.redxun.hr.core.manager;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.hr.core.dao.HrDutyInstDao;
import com.redxun.hr.core.entity.HrDutyInst;
/**
 * <pre> 
 * 描述：HrDutyInst业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class HrDutyInstManager extends BaseManager<HrDutyInst>{
	@Resource
	private HrDutyInstDao hrDutyInstDao;
	
	/**
	 * 根据用户Id和日期删除实体
	 * @param userId
	 * @param date
	 */
	public void deleteByUserIdAndDate(String userId,Date date){
		hrDutyInstDao.deleteByUserIdAndDate(userId, date);
	}
	
	public List<HrDutyInst> getAllDutyInstOfUserByUserNameAndDep(String userName,String depName,String tenantId){
		return hrDutyInstDao.getAllDutyInstOfUserByUserNameAndDep(userName,depName,tenantId);
	}
	
	public List<HrDutyInst> getByUserIdAndDay(String userId,Date sDate,Date eDate){
		return hrDutyInstDao.getByUserIdAndDay(userId, sDate,eDate);
	}
	
	public HrDutyInst getByUserIdAndOneDay(String userId,Date date){
		return hrDutyInstDao.getByUserIdAndOneDay(userId, date);
	}
	
	public List<HrDutyInst> getBySystemIdAndOneDay(String systemId,Date date){
		return hrDutyInstDao.getBySystemIdAndOneDay(systemId, date);
	}
	
	public List<HrDutyInst> getByUserId(String userId){
		return hrDutyInstDao.getByUserId(userId);
	}
	
	public HrDutyInst getByHoliDayIdAndUserIdAndDate(String holidayId,String userId,Date date){
		return hrDutyInstDao.getByHoliDayIdAndUserIdAndDate(holidayId, userId, date);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return hrDutyInstDao;
	}
}