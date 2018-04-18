package com.redxun.sys.core.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysFormField;
/**
 * <pre> 
 * 描述：SysFormField数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysFormFieldDao extends BaseJpaDao<SysFormField> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysFormField.class;
    }
    
}
