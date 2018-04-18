
package com.redxun.oa.info.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.oa.info.dao.InsNewsColumnDao;
import com.redxun.oa.info.dao.InsNewsColumnQueryDao;
import com.redxun.oa.info.entity.InsNewsColumn;

/**
 * 
 * <pre> 
 * 描述：公告栏目管理 处理接口
 * 作者:mansan
 * 日期:2018-04-16 17:38:10
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class InsNewsColumnManager extends ExtBaseManager<InsNewsColumn>{
	@Resource
	private InsNewsColumnDao insNewsColumnDao;
	@Resource
	private InsNewsColumnQueryDao insNewsColumnQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insNewsColumnDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return insNewsColumnQueryDao;
	}
	
	public List<InsNewsColumn> getAllByTenantId(String tenantId){
		List<InsNewsColumn> list=insNewsColumnQueryDao.getAllByTenantId(tenantId);
		return list;
		
	}
}
