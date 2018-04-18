
/**
 * 
 * <pre> 
 * 描述：ins_col_new_def DAO接口
 * 作者:mansan
 * 日期:2017-08-25 10:08:03
 * 版权：广州红迅软件
 * </pre>
 */
package com.redxun.oa.info.dao;

import com.redxun.oa.info.entity.InsColNewDef;
import com.redxun.saweb.context.ContextUtil;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;

@Repository
public class InsColNewDefDao extends BaseJpaDao<InsColNewDef> {


	@Override
	protected Class getEntityClass() {
		return InsColNewDef.class;
	}
	
    public Boolean booleanByColIdNewId(String colId,String newId){
    	String ql="from InsColNewDef cn where cn.colId=? and cn.newId=? ";
    	return (this.getByJpql(ql, new Object[]{colId,newId}).size()>0);
    }

}

