package com.redxun.oa.personal.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.personal.entity.AddrCont;
/**
 * <pre> 
 * 描述：通讯录联系人信息数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class AddrContDao extends BaseJpaDao<AddrCont> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return AddrCont.class;
    }
    
}
