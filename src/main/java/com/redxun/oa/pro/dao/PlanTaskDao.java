package com.redxun.oa.pro.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.oa.pro.entity.PlanTask;
/**
 * <pre> 
 * 描述：计划数据访问类
 * 构建组：miweb
 * 作者：陈茂昌
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class PlanTaskDao extends BaseJpaDao<PlanTask> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return PlanTask.class;
    }
    
    /**
     * 通过projectId(项目Id)获取计划
     * @param projectId
     * @return
     */
    public List<PlanTask> getByProjectId(String projectId){
    	String ql="select p from PlanTask p left join p.project pp where pp.projectId=?";
		return getByJpql(ql, projectId);}
    
    /**
     * 查找项目或者需求的负责人匹配的计划
     * @param UserId
     * @return
     */
    public List<PlanTask> getByUserId(String UserId){
    	String ql="select p from PlanTask p left join p.project pro left join p.reqMgr req where pro.reporId=? or req.repId=?";
		return getByJpql(ql, UserId,UserId);}
    
    /**
     * 查找项目或者需求的负责人匹配的计划
     * @param UserId
     * @return
     */
    public List<PlanTask> getPlanByMyId(String UserId){
    	String ql="select p from PlanTask p  where p.createBy=?";
		return getByJpql(ql, UserId);}
    
    
}
