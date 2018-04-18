package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.constants.FieldPermision;
import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysField;
/**
 * 
 * <pre> 
 * 描述：SysField数据访问类
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年11月2日-下午3:19:17
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysFieldDao extends BaseJpaDao<SysField> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysField.class;
    }
    
    /**
     * 获得模块字段的分组名称
     * @param moduleId
     * @return 
     * List<String>
     * @exception 
     * @since  1.0.0
     */
    public List<String> getDisGoupsByModuleId(String moduleId){
    	String ql="select distinct m.fieldGroup from SysField m where m.sysModule.moduleId=?";
    	return (List) this.getByJpql(ql, moduleId);
    }
    /**
     * 通过模块及字段分组获得字段列表
     * @param moduleId
     * @param groupName
     * @return 
     * List<SysField>
     * @exception 
     * @since  1.0.0
     */
    public List<SysField> getGroupFields(String moduleId,String groupName){
    	String ql="from SysField m where m.sysModule.moduleId=? and m.fieldGroup=? order by m.sn asc";
    	return this.getByJpql(ql, new Object[]{moduleId,groupName});
    }
    /**
     * 获得组内添加模式下的有编辑或读的字段权限
     * @param moduleId
     * @param groupName
     * @return 
     * List<SysField>
     * @exception 
     * @since  1.0.0
     */
    public List<SysField> getGroupFieldForAdd(String moduleId,String groupName){
    	String ql="from SysField m where m.sysModule.moduleId=? and m.fieldGroup=? and m.addRight <> ? order by m.sn asc";
    	return this.getByJpql(ql, new Object[]{moduleId,groupName,FieldPermision.HIDE.toString()});
    }
    
    /**
     * 获得组内编辑模式下的有编辑或读的字段权限
     * @param moduleId
     * @param groupName
     * @return 
     * List<SysField>
     * @exception 
     * @since  1.0.0
     */
    public List<SysField> getGroupFieldForEdit(String moduleId,String groupName){
    	String ql="from SysField m where m.sysModule.moduleId=? and m.fieldGroup=? and ( m.editRight =? or m.editRight=?) order by m.sn asc";
    	return this.getByJpql(ql, new Object[]{moduleId,groupName,FieldPermision.EDIT.toString(),FieldPermision.READ.toString()});
    }
    
    /**
     * 取得某个模块下的字段属性
     * @param moduleId
     * @param fieldName
     * @return 
     * SysField
     * @exception 
     * @since  1.0.0
     */
    public SysField getField(String moduleId,String attrName){
    	String ql="from SysField sf where sf.sysModule.moduleId=? and sf.attrName=?";
    	return (SysField)this.getUnique(ql, new Object[]{moduleId,attrName});
    }
    
    /**
     * 按模块ID获取所有字段列表
     * @param moduleId
     * @return 
     * List<SysField>
     * @exception 
     * @since  1.0.0
     */
    public List<SysField> getByModuleId(String moduleId){
    	String ql="from SysField sf where sf.sysModule.moduleId=? order by sf.sn asc";
    	return this.getByJpql(ql, moduleId);
	}
    
    /**
     * 按模块删除
     * @param moduleId
     */
    public void deleteByModuleId(String moduleId){
    	String ql="delete from SysField sf where sf.sysModule.moduleId=? ";
    	this.delete(ql, new Object[]{moduleId});
    }
    
    /**
     * 按模块ID获得该模块除主键外的字段列表
     * @param moduleId
     * @return 
     * List<SysField>
     * @exception 
     * @since  1.0.0
     */
    public List<SysField>getByModuleIdExcludePk(String moduleId){
    	String ql="from SysField sf where sf.sysModule.moduleId=? and sf.fieldCat!=? order by sf.sn asc";
    	return this.getByJpql(ql, new Object[]{moduleId,SysField.FIELD_PK});
    }
    
	/**
	 * 按模块ID及是否字段类型获取字段列表 
	 * @param moduleId
	 * @param fieldCat
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getByModuleFieldCat(String moduleId,String fieldCat){
		String ql="from SysField sf where sf.sysModule.moduleId=? and sf.fieldCat=? order by sf.sn asc";
		return this.getByJpql(ql,new Object[]{moduleId,fieldCat});
	}
	/**
	 * 按模块及关系类型取得字段列表
	 * @param moduleId
	 * @param relationType
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getByModuleIdRelationType(String moduleId,String relationType){
		String ql="from SysField sf where sf.sysModule.moduleId=? and sf.relationType=? order by sf.sn asc";
		return this.getByJpql(ql,new Object[]{moduleId,relationType});
	}
	
	/**
	 * 按模块ID及字段类型及是否缺省显示获取字段列表 
	 * @param moduleId
	 * @param fieldCat
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getByModuleIdFieldCatIsDefautCol(String moduleId,String fieldCat,String isDefaultCol){
		String ql="from SysField sf where sf.sysModule.moduleId=? and sf.fieldCat=? and sf.isDefaultCol=?";
		return this.getByJpql(ql, new Object[]{moduleId,fieldCat,isDefaultCol});
	}
	/**
	 * 按模块及缺省
	 * @param moduleId
	 * @param isDefaultCol
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getByModuleIdIsDefaultCol(String moduleId,String isDefaultCol){
		String ql="from SysField sf where sf.sysModule.moduleId=? and sf.isDefaultCol=?";
		return  this.getByJpql(ql, new Object[]{moduleId,isDefaultCol});
	}

	/**
	 * 取得模块的关联字段列表
	 * @param moduleId
	 * @return 
	 * List<SysModule>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getLinkFields(String moduleId){
		String ql="from SysField s where s.sysModule.moduleId=? and s.linkSysModule.moduleId is not null";
		return this.getByJpql(ql,new Object[]{moduleId});
	}
	/**
	 * 按主模块Id及关联模块Id获得关联关系字段列表
	 * @param moduleId 主模块ID
	 * @param linkModId 关联模块ID
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getByModuleIdLinkModId(String moduleId,String linkModId){
		String ql="from SysField s where s.sysModule.moduleId=? and s.linkSysModule.moduleId=?";
		return this.getByJpql(ql,new Object[]{moduleId,linkModId});
	}
}
