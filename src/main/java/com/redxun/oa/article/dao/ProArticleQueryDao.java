
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redxun.oa.article.entity.ProArticle;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;

@Repository
public class ProArticleQueryDao extends BaseMybatisDao<ProArticle> {

	@Override
	public String getNamespace() {
		return ProArticle.class.getName();
	}
	public List<ProArticle> getByIds(String[] ids){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("ids", ids);
		return getBySqlKey("getByIds", params);
	}
}

