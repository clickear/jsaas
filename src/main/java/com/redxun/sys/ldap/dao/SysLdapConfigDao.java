package com.redxun.sys.ldap.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.ldap.entity.SysLdapConfig;
/**
 * <pre> 
 * 描述：SysLdapConfig数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 使用范围：授权给敏捷集团使用
 * </pre>
 */
@Repository
public class SysLdapConfigDao extends BaseJpaDao<SysLdapConfig> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysLdapConfig.class;
    }
    
}
