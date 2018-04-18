package com.redxun.kms.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.kms.core.entity.KdUserLevel;
/**
 * <pre> 
 * 描述：KdUserLevel数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class KdUserLevelDao extends BaseJpaDao<KdUserLevel> {
	
	public KdUserLevel getByPoint(Integer point,String tenantId){
		String ql="from KdUserLevel where ?>=startVal and ?<=endVal and tenantId=?";
		return (KdUserLevel)this.getUnique(ql, new Object[]{point,point,tenantId});
	}

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return KdUserLevel.class;
    }
    
}
