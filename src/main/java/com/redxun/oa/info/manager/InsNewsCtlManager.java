
package com.redxun.oa.info.manager;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.oa.info.dao.InsNewsCtlDao;
import com.redxun.oa.info.dao.InsNewsCtlQueryDao;
import com.redxun.oa.info.entity.InsNewsCtl;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：新闻公告权限表 处理接口
 * 作者:mansan
 * 日期:2017-11-03 11:47:25
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class InsNewsCtlManager extends ExtBaseManager<InsNewsCtl>{
	@Resource
	private InsNewsCtlDao insNewsCtlDao;
	@Resource
	private InsNewsCtlQueryDao insNewsCtlQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insNewsCtlDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return insNewsCtlQueryDao;
	}
	
	public InsNewsCtl getInsNewsCtl(String uId){
		InsNewsCtl insNewsCtl = get(uId);
		return insNewsCtl;
	}
	
	public void deleteByNewsId(String newsId){
		insNewsCtlDao.deleteByNewsId(newsId);
	}
	
	public InsNewsCtl getByRightNewsId(String right,String newsId){
		return insNewsCtlDao.getByRightNewsId(right,newsId);
	}
}
