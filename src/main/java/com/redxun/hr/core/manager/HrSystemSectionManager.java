package com.redxun.hr.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.hr.core.dao.HrSystemSectionDao;
import com.redxun.hr.core.entity.HrSystemSection;
/**
 * <pre> 
 * 描述：HrSystemSection业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class HrSystemSectionManager extends BaseManager<HrSystemSection>{
	@Resource
	private HrSystemSectionDao hrSystemSectionDao;
	
	public List<HrSystemSection> getBySystemId(String systemId){
		return hrSystemSectionDao.getBySystemId(systemId);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return hrSystemSectionDao;
	}
}