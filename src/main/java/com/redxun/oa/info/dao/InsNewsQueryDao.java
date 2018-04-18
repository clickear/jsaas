
/**
 * 
 * <pre> 
 * 描述：信息公告 DAO接口
 * 作者:mansan
 * 日期:2018-04-16 17:14:23
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.entity.InsNews;

@Repository
public class InsNewsQueryDao extends BaseMybatisDao<InsNews> {

	@Override
	public String getNamespace() {
		return InsNews.class.getName();
	}

	public List<InsNews> getByColId(QueryFilter filter){
		List<InsNews> list= this.getPageBySqlKey("getByColId", filter);
		return list;
	} 
}

