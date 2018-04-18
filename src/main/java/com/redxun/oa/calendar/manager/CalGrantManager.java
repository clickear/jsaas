package com.redxun.oa.calendar.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.calendar.dao.CalGrantDao;
import com.redxun.oa.calendar.entity.CalGrant;
import com.redxun.sys.org.dao.OsGroupDao;
import com.redxun.sys.org.dao.OsUserDao;
/**
 * <pre> 
 * 描述：CalGrant业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class CalGrantManager extends BaseManager<CalGrant>{
	@Resource
	private CalGrantDao calGrantDao;
	@Resource
	private OsGroupDao osGroupDao;
	@Resource
	private OsUserDao osUserDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return calGrantDao;
	}
	/**
	 * 通过日历设定Id来获取授权
	 * @param settingId
	 * @return
	 */
	public List<CalGrant> getBySettingId(String settingId){
		return calGrantDao.getBySettingId(settingId);
	}
	/**
	 * 查询之前是否存在groupId或者userId
	 * @param type
	 * @param groupOrUserId
	 * @return
	 */
	public boolean checkIsExist(String type,String groupOrUserId){
		String tenantId;
		if("GROUP".equals(type)) {
			tenantId=osGroupDao.get(groupOrUserId).getTenantId();
		}else {
			tenantId=osUserDao.get(groupOrUserId).getTenantId();
		}
		return calGrantDao.checkIsExist(type,groupOrUserId,tenantId);
	}
	/**
	 * 通过groupId或者userId查询分配的日历
	 * @param type
	 * @param groupOrUserId
	 * @return
	 */
	public CalGrant getByGroupIdOrUserId(String type,String groupOrUserId,String tenantId){
		return calGrantDao.getByGroupIdOrUserId(type, groupOrUserId,tenantId);
	}
	
	public CalGrant getBySettingIdGrantTypeBelongWho(String settingId,String grantType,String guId){
		return calGrantDao.getBySettingIdGrantTypeBelongWho(settingId,grantType,guId);
	}
	
}