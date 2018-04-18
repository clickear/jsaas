package com.redxun.sys.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysGridField;

/**
 * <pre>
 * 描述：SysGridField数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysGridFieldDao extends BaseJpaDao<SysGridField> {

    @SuppressWarnings("rawtypes")
    @Override
    protected Class getEntityClass() {
        return SysGridField.class;
    }

    public List<SysGridField> getByGridViewId(String gridViewId) {
        String ql = "from SysGridField sgf where sgf.sysGridView.gridViewId=? order by sgf.sn asc";
        return this.getByJpql(ql, gridViewId);
    }

    /**
     * 按GridViewId及字段名查找系统的字段配置
     *
     * @param gridViewId
     * @param fieldName
     * @return SysGridField
     * @exception
     * @since 1.0.0
     */
    public SysGridField getByGridViewIdFieldName(String gridViewId, String fieldName) {
        String ql = "from SysGridField sgf where sgf.sysGridView.gridViewId=? and sgf.fieldName=?";
        return (SysGridField) this.getUnique(ql, gridViewId, fieldName);
    }

    public List<SysGridField> getByGridViewIdParentId(String gridViewId, String parentId) {
        String ql = "from SysGridField sgf where sgf.sysGridView.gridViewId=? and sgf.parentId=? order by sgf.sn asc";
        return this.getByJpql(ql, gridViewId, parentId);
    }
    
    public List<SysGridField> getByParentId(String parentId) {
        String ql = "from SysGridField sgf where sgf.parentId=? order by sgf.sn asc";
        return this.getByJpql(ql,parentId);
    }

    public List<SysGridField> getByGroupId(String groupId) {
        String ql = "from SysGridField sgf where sgf.sysGridGroup.groupId=? order by sgf.sn asc";
        return this.getByJpql(ql, groupId);
    }

    public void delByPath(String path) {
        String ql = "delete from SysGridField sgf where sgf.path like ?";
        this.delete(ql, path + "%");
    }

}
