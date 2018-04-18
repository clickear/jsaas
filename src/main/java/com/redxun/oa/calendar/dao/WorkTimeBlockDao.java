package com.redxun.oa.calendar.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.calendar.entity.WorkTimeBlock;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：WorkTimeBlock数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class WorkTimeBlockDao extends BaseJpaDao<WorkTimeBlock> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return WorkTimeBlock.class;
    }
    
    /**
     * 通过tenantId获取相应租户下面的所有
     * 工作时间段
     */
    public List<WorkTimeBlock> getAllByTenantId(String tenantId){
    	String hql="from WorkTimeBlock wtb where wtb.tenantId=?";
    	return this.getByJpql(hql, tenantId);
    }
    
    /**
     * 通过名字查找唯一的WorkTimeBlock
     * @param name
     * @return
     */
    public WorkTimeBlock getWorkTimeBlockByName(String name){
    	String tenantId=ContextUtil.getCurrentTenantId();
    	String hql="from WorkTimeBlock wtb where wtb.settingName=? and wtb.tenantId=?";
    	return (WorkTimeBlock) this.getUnique(hql, name,tenantId);
    }
}
