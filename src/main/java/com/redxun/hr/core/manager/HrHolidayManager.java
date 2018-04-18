package com.redxun.hr.core.manager;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.hr.core.dao.HrHolidayDao;
import com.redxun.hr.core.entity.HrDutyInst;
import com.redxun.hr.core.entity.HrHoliday;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.manager.OsRelInstManager;

/**
 * <pre>
 *  
 * 描述：HrHoliday业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class HrHolidayManager extends BaseManager<HrHoliday> {
	@Resource
	private HrHolidayDao hrHolidayDao;
	@Resource
	HrDutyInstManager hrDutyInstManager;
	@Resource
	OsRelInstManager osRelInstManager;
	
	public void delete(String hrHolidayId){
		HrHoliday hrHoliday=this.get(hrHolidayId);
		if(StringUtils.isNotEmpty(hrHoliday.getUserId())){
			String[] userIds=hrHoliday.getUserId().split(",");
			for (int i = 0; i < userIds.length; i++) {
				HrDutyInst hrDutyInst=hrDutyInstManager.getByHoliDayIdAndUserIdAndDate(hrHoliday.getHolidayId(), userIds[i],hrHoliday.getStartDay());
				if(hrDutyInst!=null){
					hrDutyInst.setHrHoliday(null);
					hrDutyInstManager.update(hrDutyInst);
					hrDutyInstManager.flush();
				}
			}
		}
		if(StringUtils.isNotEmpty(hrHoliday.getGroupId())){
			String[] groupIds=hrHoliday.getGroupId().split(",");
			for (int i = 0; i < groupIds.length; i++) {
				List<OsRelInst> osRelInsts=osRelInstManager.getByParty1RelTypeIsMain(groupIds[i], OsRelType.REL_CAT_GROUP_USER_BELONG, MBoolean.YES.name());
				for (int j = 0; j < osRelInsts.size(); j++) {
					HrDutyInst hrDutyInst=hrDutyInstManager.getByHoliDayIdAndUserIdAndDate(hrHoliday.getHolidayId(),osRelInsts.get(j).getParty2(),hrHoliday.getStartDay());
					if(hrDutyInst!=null){
						hrDutyInst.setHrHoliday(null);
						hrDutyInstManager.update(hrDutyInst);
						hrDutyInstManager.flush();
					}
				}
			}		
		}
		if(StringUtils.isNotEmpty(hrHoliday.getSystemId())){
			String[] systemIds=hrHoliday.getSystemId().split(",");
			for (int i = 0; i < systemIds.length; i++) {
				List<HrDutyInst> hrDutyInsts=hrDutyInstManager.getBySystemIdAndOneDay(systemIds[i],hrHoliday.getStartDay());
				for (int j = 0; j < hrDutyInsts.size(); j++) {
					HrDutyInst hrDutyInst=hrDutyInsts.get(j);
					if(hrDutyInst!=null){
						hrDutyInst.setHrHoliday(null);
						hrDutyInstManager.update(hrDutyInst);
						hrDutyInstManager.flush();
					}
				}
			}
		}
		super.delete(hrHolidayId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return hrHolidayDao;
	}
}