package com.redxun.oa.pro.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.pro.entity.WorkLog;
/**
 * <pre> 
 * 描述：ProTask数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class WorkLogDao extends BaseJpaDao<WorkLog> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return WorkLog.class;
    }
    
    public List<WorkLog> getByPlanId(String planId){
    	String ql="select p from WorkLog p left join p.PlanTask pl where pl.planId=?";
		return getByJpql(ql, planId);}
    
}
