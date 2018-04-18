
package com.redxun.oa.article.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.oa.article.dao.ProArticleDao;
import com.redxun.oa.article.dao.ProArticleQueryDao;
import com.redxun.oa.article.entity.ProArticle;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;

/**
 * 
 * <pre> 
 * 描述：文章 处理接口
 * 作者:陈茂昌
 * 日期:2017-09-29 14:39:26
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class ProArticleManager extends ExtBaseManager<ProArticle>{
	@Resource
	private ProArticleDao proArticleDao;
	@Resource
	private ProArticleQueryDao proArticleQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return proArticleQueryDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return proArticleQueryDao;
	}
	
	public ProArticle getProArticle(String uId){
		ProArticle proArticle = get(uId);
		return proArticle;
	}
	public List<ProArticle> getByItemId(String itemId){
		return proArticleDao.getByItemId(itemId);
	}
	public List<ProArticle> getByIds(String[] ids){
		return proArticleQueryDao.getByIds(ids);
	}
}
