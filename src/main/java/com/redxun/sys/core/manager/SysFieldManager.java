package com.redxun.sys.core.manager;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.IDao;
import com.redxun.core.entity.BaseEntity;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.core.dao.SysFieldDao;
import com.redxun.sys.core.dao.SysModuleDao;
import com.redxun.sys.core.entity.FieldRecord;
import com.redxun.sys.core.entity.SysField;
import com.redxun.sys.core.entity.SysModule;
/**
 * <pre> 
 * 描述：SysField业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysFieldManager extends BaseManager<SysField>{
	@Resource
	private SysFieldDao sysFieldDao;
	@Resource
	private SysModuleDao sysModuleDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysFieldDao;
	}
	/**
	 * 按实体Class名取得字段列表
	 * @param entityName
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getByEntityName(String entityName){
		SysModule sysModule=sysModuleDao.getByEntityClass(entityName);
		if(sysModule==null) return new ArrayList<SysField>();
		return getByModuleId(sysModule.getModuleId());
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
    	return sysFieldDao.getDisGoupsByModuleId(moduleId);
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
    	return sysFieldDao.getGroupFields(moduleId, groupName);
    }
    
    public List<SysField> getGroupFields(String moduleId,String groupName,boolean isAdd){
    	if(isAdd){
    		return sysFieldDao.getGroupFieldForAdd(moduleId, groupName);
    	}else{
    		return sysFieldDao.getGroupFieldForEdit(moduleId, groupName);
    	}
    }
	
	/**
	 * 按方法名称取得字段记录实体
	 * @param entityInst
	 * @return 
	 * List<FieldRecord>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<FieldRecord> getFieldRecordsByEntity(Object entityInst){
		List<SysField> sysFields=getByEntityName(entityInst.getClass().getName());
		List<FieldRecord> fieldRecords=new ArrayList<FieldRecord>();
		BaseEntity baseEntity=(BaseEntity)entityInst;
		String pkId=(String)baseEntity.getPkId();
		for(SysField field:sysFields){
			if(SysField.FIELD_PK.equals(field.getFieldCat())) continue;
			FieldRecord record=new FieldRecord();
			record.setPkId(pkId);
			record.setFieldLabel(field.getTitle());
			record.setFieldName(field.getAttrName());
			record.setFieldType(field.getFieldType());
			record.setFieldCat(field.getFieldCat());
			record.setRelationType(field.getRelationType());
			Object objVal=null;
			
			String fieldGetMethod="get"+StringUtil.makeFirstLetterUpperCase(field.getAttrName());
			try{
				Method method=entityInst.getClass().getDeclaredMethod(fieldGetMethod, new Class[]{});
				objVal=method.invoke(entityInst, new Object[]{});
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			
			if(objVal==null) continue;
			
			if(SysField.FIELD_COMMON.equals(field.getFieldCat())){
				record.setFieldVal(objVal);
			}else if(SysField.RELATION_ONE_TO_MANY.equals(field.getRelationType())
					||SysField.RELATION_MANY_TO_MANY.equals(field.getRelationType())){
				Collection<?> items=(Collection<?>)objVal;
				record.setFieldVal("<span class='link_module'>"+ items.size() + "</span>");
			}else if(SysField.RELATION_MANY_TO_ONE.equals(field.getRelationType())){
				BaseEntity inst=(BaseEntity)objVal;
				record.setFieldVal("<span class='link_module'>" + inst.getIdentifyLabel() + "</span>");
			}
			
			fieldRecords.add(record);
		}
		return fieldRecords;
	}
	
	 public SysField getField(String moduleId,String attrName){
		 return sysFieldDao.getField(moduleId, attrName);
	 }
	 
	public List<SysField> getByModuleIdExcludePk(String moduleId) {
		return sysFieldDao.getByModuleIdExcludePk(moduleId);
	}
	 
	public List<SysField> getByModuleId(String moduleId){
		return sysFieldDao.getByModuleId(moduleId);
	}
	
	public List<SysField> getByModuleFieldCat(String moduleId,String fieldCat){
		return sysFieldDao.getByModuleFieldCat(moduleId, fieldCat);
	}
	
	public List<SysField> getByModuleIdRelationType(String moduleId,String relationType){
		return sysFieldDao.getByModuleIdRelationType(moduleId,relationType);
	}
	
	 /**
     * 按模块删除
     * @param moduleId
     */
    public void deleteByModuleId(String moduleId){
    	sysFieldDao.deleteByModuleId(moduleId);
    }
	
	/**
	 * 为列表页中的展示有效的关联模块，仅多对一、一对一关系，
	 * 其他一对多，多对多在界面仅需要显示一列，并且以统计数展示
	 * @param moduleId
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getForToOneRelationFields(String moduleId){
		List<SysField> fieldList1=sysFieldDao.getByModuleIdRelationType(moduleId, SysField.RELATION_MANY_TO_ONE);
		List<SysField> fieldList2=sysFieldDao.getByModuleIdRelationType(moduleId, SysField.RELATION_ONE_TO_ONE);
		fieldList1.addAll(fieldList2);
		return fieldList1;
	}
	
	/**
	 * 获得模块中的缺省的字段列表
	 * @param moduleId
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getDefaultShowFields(String moduleId){
		return sysFieldDao.getByModuleIdIsDefaultCol(moduleId, MBoolean.YES.toString());
	}
	/**
	 * 获得模块中关联的字段列表
	 * @param moduleId
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getLinkFields(String moduleId){
		return sysFieldDao.getLinkFields(moduleId);
	}
	/**
	 * 获得关联模块字段
	 * @param moduleId
	 * @param linkModId
	 * @return 
	 * List<SysField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysField> getByModuleIdLinkModId(String moduleId,String linkModId){
		return sysFieldDao.getByModuleIdLinkModId(moduleId, linkModId);
	}
}