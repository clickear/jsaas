package com.redxun.hr.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.hr.core.entity.HrDutyInstExt;
/**
 * <pre> 
 * 描述：HrDutyInstExt数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class HrDutyInstExtDao extends BaseJpaDao<HrDutyInstExt> {
	
	public void deleteByInstId(String instId){
		String ql="delete from HrDutyInstExt e where e.hrDutyInst.dutyInstId=?";
		this.delete(ql, new Object[]{instId});
	}

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return HrDutyInstExt.class;
    }
    
}
