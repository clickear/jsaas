
/**
 * 
 * <pre> 
 * 描述：新闻公告权限表 DAO接口
 * 作者:mansan
 * 日期:2017-11-03 11:47:25
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import com.redxun.oa.info.entity.InsNewsCtl;
import org.springframework.stereotype.Repository;
import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class InsNewsCtlDao extends BaseJpaDao<InsNewsCtl> {


	@Override
	protected Class getEntityClass() {
		return InsNewsCtl.class;
	}
	
	public void deleteByNewsId(String newsId){
		String ql = "delete from InsNewsCtl where newsId = ?";
		this.delete(ql, new Object[]{newsId});
	}
	
	public InsNewsCtl getByRightNewsId(String right,String newsId){
		String ql = "from InsNewsCtl ctl where ctl.right=? and ctl.newsId = ?";
		return (InsNewsCtl) this.getUnique(ql, new Object[]{right,newsId});
	}

}

