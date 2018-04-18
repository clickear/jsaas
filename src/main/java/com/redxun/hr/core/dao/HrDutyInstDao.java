package com.redxun.hr.core.dao;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.hr.core.entity.HrDutyInst;
/**
 * <pre> 
 * 描述：HrDutyInst数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class HrDutyInstDao extends BaseJpaDao<HrDutyInst> {
	
	/**
	 * 根据用户Id和日期删除实体
	 * @param userId
	 * @param date
	 */
	public void deleteByUserIdAndDate(String userId,Date date){
		String ql="delete from HrDutyInst i where i.userId=? and i.date=?";
		this.delete(ql, new Object[]{userId,date});
	}
	
	public List<HrDutyInst> getAllDutyInstOfUserByUserNameAndDep(String userName,String depName,String tenantId){
		String ql="select distinct i.userId from HrDutyInst i where i.tenantId=?";
		Object[] obj=null;
		String[] array=new String[]{tenantId,"",""};
		if(StringUtils.isNotEmpty(depName)){
			ql+=" and i.depName like ?";
			array[1]=depName+"%";
		}
		if(StringUtils.isNotEmpty(userName)){
			ql+=" and i.userName like ?";
			array[2]=userName+"%";
		}
		if(StringUtils.isNotEmpty(depName))
			obj=new Object[]{tenantId,array[1]};
		else
			obj=new Object[]{tenantId};
		if (obj.length>1){ 
			if(StringUtils.isNotEmpty(userName))
				obj=new Object[]{tenantId,array[1],array[2]};
			else
				obj=new Object[]{tenantId,array[1]};
		}
		else{
			if(StringUtils.isNotEmpty(userName))
				obj=new Object[]{tenantId,array[2]};
			else
				obj=new Object[]{tenantId};
		}
		return this.getByJpql(ql,obj);
	}
	

	
	public List<HrDutyInst> getByUserIdAndDay(String userId,Date sDate,Date eDate){
		String ql="from HrDutyInst i where i.userId=? and (i.date between ? and ?)  order by i.date asc";
		return this.getByJpql(ql, new Object[]{userId,sDate,eDate});
	}
	
	public HrDutyInst getByUserIdAndOneDay(String userId,Date date){
		String ql="from HrDutyInst i where i.userId=? and i.date=?";
		return (HrDutyInst)this.getUnique(ql, new Object[]{userId,date});
	}
	
	public List<HrDutyInst> getBySystemIdAndOneDay(String systemId,Date date){
		String ql="from HrDutyInst i where i.systemId=? and i.date=?";
		return this.getByJpql(ql, new Object[]{systemId,date});
	}
	
	public List<HrDutyInst> getByUserId(String userId){
		String ql="select distinct i.userId from HrDutyInst i where i.userId=?";
		return this.getByJpql(ql, new Object[]{userId});
	}
	
	public HrDutyInst getByHoliDayIdAndUserIdAndDate(String holidayId,String userId,Date date){
		String ql="from HrDutyInst i where i.hrHoliday.holidayId=? and i.userId=? and i.date=?";
		return (HrDutyInst)this.getUnique(ql, new Object[]{holidayId,userId,date});
		
	}
	
    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return HrDutyInst.class;
    }
    
}
