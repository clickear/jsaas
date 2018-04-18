package com.redxun.oa.calendar.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.calendar.entity.CalSetting;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：CalSetting数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class CalSettingDao extends BaseJpaDao<CalSetting> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return CalSetting.class;
    }
    
    public List<CalSetting> getAllSetting(){
    	String hql="from CalSetting  cs where cs.tenantId=?";
    	String tenantId=ContextUtil.getCurrentTenantId();
    	return this.getByJpql(hql, tenantId);
    }
    
    public CalSetting getByName(String name,String tenantId){
    	String hql="from CalSetting  cs where cs.calName=? and cs.tenantId=?";
    	return (CalSetting) this.getUnique(hql, name,tenantId);
    }
    
    /**
     * 获取默认租户。
     * @return
     */
    public CalSetting getDefault(){
    	String tenantId=ContextUtil.getCurrentTenantId();
    	String hql="from CalSetting  cs where cs.tenantId=? and cs.isCommon='YES'";
    	return (CalSetting) this.getUnique(hql, tenantId);
    }
}
