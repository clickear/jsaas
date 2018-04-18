package com.redxun.oa.calendar.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.calendar.dao.CalSettingDao;
import com.redxun.oa.calendar.entity.CalSetting;
/**
 * <pre> 
 * 描述：CalSetting业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class CalSettingManager extends BaseManager<CalSetting>{
	@Resource
	private CalSettingDao calSettingDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return calSettingDao;
	}
	public CalSetting getByName(String name,String tenantId){
		return calSettingDao.getByName(name,tenantId);
	}
    /**
     * 获取默认的日历。
     * @return
     */
    public CalSetting getDefault(){
    	return calSettingDao.getDefault();
    }
}