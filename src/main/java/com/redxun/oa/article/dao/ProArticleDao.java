
/**
 * 
 * <pre> 
 * 描述：文章 DAO接口
 * 作者:陈茂昌
 * 日期:2017-09-29 14:39:26
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.article.dao;

import java.util.List;

import com.redxun.oa.article.entity.ProArticle;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class ProArticleDao extends BaseJpaDao<ProArticle> {


	@Override
	protected Class getEntityClass() {
		return ProArticle.class;
	}
	public List<ProArticle> getByItemId(String itemId){
		String hql="from ProArticle pa where pa.belongProId=? order by pa.sn+0 asc";
		return this.getByJpql(hql, itemId);
	}

}

