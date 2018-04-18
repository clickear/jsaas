
package com.redxun.oa.info.manager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.dao.mybatis.BaseMybatisDao;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.info.dao.InsColNewDefDao;
import com.redxun.oa.info.dao.InsColNewDefQueryDao;
import com.redxun.oa.info.entity.InsColNewDef;

/**
 * 
 * <pre> 
 * 描述：ins_col_new_def 处理接口
 * 作者:mansan
 * 日期:2017-08-25 10:08:03
 * 版权：广州红迅软件
 * </pre>
 */
@Service
public class InsColNewDefManager extends ExtBaseManager<InsColNewDef>{
	@Resource
	private InsColNewDefDao insColNewDefDao;
	@Resource
	private InsColNewDefQueryDao insColNewDefQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return insColNewDefDao;
	}
	
	@Override
	public BaseMybatisDao getMyBatisDao() {
		return insColNewDefQueryDao;
	}
	
	public InsColNewDef getInsColNewDef(String uId){
		InsColNewDef insColNewDef = get(uId);
		return insColNewDef;
	}
	
	public List<InsColNewDef> getByColId(QueryFilter queryFilter){
		return insColNewDefQueryDao.getByColId(queryFilter);
	}
	
	public List<InsColNewDef> getByNewId(QueryFilter queryFilter){
		return insColNewDefQueryDao.getByNewId(queryFilter);
	}
	
	public void delByNewId(String newId){
		insColNewDefQueryDao.delByNewId(newId);
	}
}
