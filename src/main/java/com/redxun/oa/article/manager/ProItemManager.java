
package com.redxun.oa.article.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.oa.article.dao.ProItemDao;
import com.redxun.oa.article.dao.ProItemQueryDao;
import com.redxun.oa.article.entity.ProItem;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：项目 处理接口
 * 作者:陈茂昌
 * 日期:2017-09-29 14:38:27
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class ProItemManager extends ExtBaseManager<ProItem>{
	@Resource
	private ProItemDao proItemDao;
	@Resource
	private ProItemQueryDao proItemQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return proItemQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return proItemQueryDao;
	}
	
	public ProItem getProItem(String uId){
		ProItem proItem = get(uId);
		return proItem;
	}
}
