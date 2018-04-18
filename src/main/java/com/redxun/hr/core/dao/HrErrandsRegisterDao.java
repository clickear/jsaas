package com.redxun.hr.core.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.hr.core.entity.HrErrandsRegister;
import com.redxun.oa.res.entity.OStatus;

/**
 * <pre>
 *  
 * 描述：HrErrandsRegister数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class HrErrandsRegisterDao extends BaseJpaDao<HrErrandsRegister> {

	public List<HrErrandsRegister> getByUserIdAndType(String userId,String type,String tenantId,QueryFilter queryFilter){
		String ql="from HrErrandsRegister h where h.createBy=? and h.type=? and h.tenantId=?";
		return getByJpql(ql, new Object[]{userId,type,tenantId},queryFilter.getPage());
	}
	
	/*public List<HrErrandsRegister> getByUserIdAndStatusAndStartTimeAndEndTime(String userId,String status,Date startDate,Date endDate){
		String ql="from HrErrandsRegister h where h.createBy=? and h.status=? and h.startTime>=? and ?>h.startTime ";
		return getByJpql(ql, new Object[]{userId,status,startDate,endDate});
	}*/
	
	public List<HrErrandsRegister> getByUserIdAndStatusAndDate(String userId,String status,Date date){
		String ql="from HrErrandsRegister h where h.createBy=? and h.status=? and( (h.startTime<=? and h.endTime>?) or (h.startTime>=? and ?>h.startTime))";
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return getByJpql(ql, new Object[]{userId,OStatus.APPROVED.name(),date,date,date,cal.getTime()});
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return HrErrandsRegister.class;
	}

}
