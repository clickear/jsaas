package com.redxun.hr.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.hr.core.entity.HrSystemSection;
/**
 * <pre> 
 * 描述：HrSystemSection数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class HrSystemSectionDao extends BaseJpaDao<HrSystemSection> {
	
	public List<HrSystemSection> getBySystemId(String systemId){
		String ql="from HrSystemSection h where h.hrDutySystem.systemId=? order by h.workday asc";
		return this.getByJpql(ql, new Object[]{systemId});
	}

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return HrSystemSection.class;
    }
    
}
