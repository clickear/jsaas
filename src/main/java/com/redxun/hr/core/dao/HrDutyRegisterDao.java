package com.redxun.hr.core.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.hr.core.entity.HrDutyRegister;

/**
 * <pre>
 *  
 * 描述：HrDutyRegister数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class HrDutyRegisterDao extends BaseJpaDao<HrDutyRegister> {

	public HrDutyRegister getByDateAndSectionIdAndUserIdAndInoffFlag(Date date,String sectionId,String userId,String inOffFlag){
		String ql="from HrDutyRegister r where r.date=? and r.sectionId=? and r.createBy=? and r.inOffFlag=?";
		return (HrDutyRegister)this.getUnique(ql, new Object[]{date,sectionId,userId,inOffFlag});
	}
	
	public List<HrDutyRegister> getUserMonthSignRecord(int month,String userId){
		String ql="from HrDutyRegister r where createBy = ? and month(date) = ?";
		List<HrDutyRegister> list = this.getByJpql(ql, new Object[]{userId,month});
		return list;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return HrDutyRegister.class;
	}

}
