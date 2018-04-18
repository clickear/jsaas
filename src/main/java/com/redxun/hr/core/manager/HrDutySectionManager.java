package com.redxun.hr.core.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.hr.core.dao.HrDutySectionDao;
import com.redxun.hr.core.entity.HrDutySection;

/**
 * <pre>
 *  
 * 描述：HrDutySection业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class HrDutySectionManager extends BaseManager<HrDutySection> {
	@Resource
	private HrDutySectionDao hrDutySectionDao;
	
	/**
	 * 根据是否组合班次获取实体
	 * @param isGroup
	 * @return
	 */
	public List<HrDutySection> getByIsGroup(String isGroup,String tenantId){
		return hrDutySectionDao.getByIsGroup(isGroup,tenantId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return hrDutySectionDao;
	}
}