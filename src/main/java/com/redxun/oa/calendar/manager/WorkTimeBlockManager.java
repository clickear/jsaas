package com.redxun.oa.calendar.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.calendar.dao.WorkTimeBlockDao;
import com.redxun.oa.calendar.entity.WorkTimeBlock;
/**
 * <pre> 
 * 描述：WorkTimeBlock业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class WorkTimeBlockManager extends BaseManager<WorkTimeBlock>{
	@Resource
	private WorkTimeBlockDao workTimeBlockDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return workTimeBlockDao;
	}
    /**
     * 通过tenantId获取相应租户下面的所有
     * 工作时间段
     */
	 public List<WorkTimeBlock> getAllByTenantId(String tenantId){
		 return workTimeBlockDao.getAllByTenantId(tenantId);
	 }
	 /**
	     * 通过名字查找唯一的WorkTimeBlock
	     * @param name
	     * @return
	     */
	    public WorkTimeBlock getWorkTimeBlockByName(String name){
	    	return workTimeBlockDao.getWorkTimeBlockByName(name);
	    }
}