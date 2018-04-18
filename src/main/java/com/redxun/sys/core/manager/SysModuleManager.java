package com.redxun.sys.core.manager;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.annotion.ui.FieldConfig;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.constants.WidgetType;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.core.dao.SysModuleDao;
import com.redxun.sys.core.entity.SysField;
import com.redxun.sys.core.entity.SysModule;
/**
 * <pre> 
 * 描述：SysModule业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysModuleManager extends BaseManager<SysModule>{
	@Resource
	private SysModuleDao sysModuleDao;
	
	@Resource
	private SysFieldManager sysFieldManager;
	@Resource
	SysButtonManager sysButtonManager;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysModuleDao;
	}
	
	/**
	 * 按实体类名获取系统模块记录
	 * @param classEntity
	 * @return 
	 * SysModule
	 * @exception 
	 * @since  1.0.0
	 */
	public SysModule getByEntityClass(String classEntity){
		return sysModuleDao.getByEntityClass(classEntity);
	}
	
	public <T> SysModule getFromEntityClass(Class<T> classEntity){
		SysModule module=sysModuleDao.getByEntityClass(classEntity.getName());
		if(module!=null) return module;
		return createOrUpdateFromEntityClass(classEntity);
	}
	
	/**
	 * 从Class创建或更新功能模块
	 * @param classEntity
	 * @return 
	 * SysModule
	 * @exception 
	 * @since  1.0.0
	 */
	public <T> SysModule createOrUpdateFromEntityClass(Class<T> classEntity){
		SysModule module=sysModuleDao.getByEntityClass(classEntity.getName());
		logger.debug("entityName:" + classEntity.getName() + " module " +module );
		if(module==null){
			module=new SysModule();
			TableDefine tableDef=classEntity.getAnnotation(TableDefine.class);
			Table table=classEntity.getAnnotation(Table.class);
			if(tableDef==null) return null;
			if(table==null) return null;
			
			module.setTableName(table.name());
			module.setTitle(tableDef.title());
			module.setEntityName(classEntity.getName());
			module.setNamespace(classEntity.getPackage().getName());
			//设置配置属性
			module.setIsEnabled(MBoolean.YES.toString());
			module.setAllowAudit(MBoolean.YES.toString());
			module.setAllowApprove(MBoolean.NO.toString());
			module.setHasAttachs(MBoolean.NO.toString());
			module.setHasChart(MBoolean.NO.toString());
			module.setIsDefault(MBoolean.NO.toString());
		}
		
		//关系字段
		List<Field> joinFields=new ArrayList<Field>();
		int i=0;
		Class<?> cls=classEntity;
		for(;cls != Object.class;cls=cls.getSuperclass()){
			for(Field field: cls.getDeclaredFields()){
				FieldDefine fieldDefine=field.getAnnotation(FieldDefine.class);
				//若字段没有注释，则忽略
				if(fieldDefine==null || StringUtils.isEmpty(fieldDefine.title())){
					continue;
				}
				Column column=field.getAnnotation(Column.class);
				OneToMany oneToMany=field.getAnnotation(OneToMany.class);
				ManyToOne manyToOne=field.getAnnotation(ManyToOne.class);
				ManyToMany manyToMany=field.getAnnotation(ManyToMany.class);
				OneToOne oneToOne=field.getAnnotation(OneToOne.class);
				
				SysField sysField=new SysField();
				String dbFieldName=null;
				//如果为普通字段列
				if(column!=null){
					dbFieldName=column.name();
					sysField.setFieldCat(SysField.FIELD_COMMON);
					sysField.setRelationType(SysField.RELATION_NONE);
				}else if(oneToMany!=null){//一对多关系
					joinFields.add(field);
					sysField.setFieldCat(SysField.FIELD_RELATION);
					sysField.setRelationType(SysField.RELATION_ONE_TO_MANY);
				}else if(manyToOne!=null){//如果为关联列 一对多
					JoinColumn joinColumn=field.getAnnotation(JoinColumn.class);
					dbFieldName=joinColumn.name();
					joinFields.add(field);
					sysField.setFieldCat(SysField.FIELD_RELATION);
					sysField.setRelationType(SysField.RELATION_MANY_TO_ONE);
				}else if(manyToMany!=null){//多对多
					joinFields.add(field);
					sysField.setFieldCat(SysField.FIELD_RELATION);
					sysField.setRelationType(SysField.RELATION_MANY_TO_MANY);
				}else if(oneToOne!=null){//一对一
					sysField.setFieldCat(SysField.FIELD_RELATION);
					sysField.setRelationType(SysField.RELATION_ONE_TO_ONE);
					joinFields.add(field);
				}else{
					continue;
				}
				Size size=field.getAnnotation(Size.class);
				if(size!=null){
					sysField.setFieldLength(size.max());
				}
				NotEmpty empty=field.getAnnotation(NotEmpty.class);
				if(empty!=null){
					sysField.setIsRequired(MBoolean.YES.toString());
				}else{
					sysField.setIsRequired(MBoolean.NO.toString());
				}
				Id id=field.getAnnotation(Id.class);
				if(id!=null){
					module.setPkField(field.getName());
					sysField.setFieldCat(SysField.FIELD_PK);
					sysField.setTitle(field.getName());
					module.setPkDbField(column.name());
					sysField.setIsHidden(MBoolean.YES.toString());
				}
				
				sysField.setDbFieldName(dbFieldName);
				String jsonConfig=getJsonConfig(field);
				if(fieldDefine!=null){
					//检查该功能字段是否存在了
					if(module.getModuleId()!=null){
						SysField sField=sysFieldManager.getField(module.getModuleId(), field.getName());
						if(sField!=null){
							sField.setTitle(fieldDefine.title());
							sField.setDbFieldName(dbFieldName);
							sField.setFieldType(field.getType().getName());
							sField.setRelationType(sysField.getRelationType());
							sField.setJsonConfig(jsonConfig);
							if(size!=null){
								sField.setFieldLength(size.max());
							}
							if(empty!=null){
								sField.setIsRequired(MBoolean.YES.toString());
							}else{
								sField.setIsRequired(MBoolean.NO.toString());
							}
							sField.setIsDefaultCol(fieldDefine.defaultCol().toString());
							String comType=fieldDefine.widgetType().getXtype();
							//若默认设置TextField,则设置缺省的类型
							if(WidgetType.NULL.getXtype().equals(comType)){
								comType=sField.getDefaultWidgetType();
							}
							sField.setAddRight(fieldDefine.insertable().getPermision());
							sField.setEditRight(fieldDefine.editable().getPermision());
							sField.setCompType(comType);
							sysFieldManager.update(sField);
							continue;
						}
					}
					//设置编辑权限
					sysField.setAddRight(fieldDefine.insertable().getPermision());
					sysField.setEditRight(fieldDefine.editable().getPermision());
					
					sysField.setTitle(fieldDefine.title());
					sysField.setFieldGroup(fieldDefine.group());
					sysField.setIsDefaultCol(fieldDefine.defaultCol().toString());
					sysField.setIsQueryCol(fieldDefine.searchCol().toString());
					sysField.setAllowSort(fieldDefine.sortable().toString());
					
					String comType=fieldDefine.widgetType().getXtype();
					//若默认设置TextField,则设置缺省的类型
					if(WidgetType.NULL.getXtype().equals(comType)){
						comType=sysField.getDefaultWidgetType();
					}
					//加载控件类型
					sysField.setCompType(comType);
				}
				
				sysField.setJsonConfig(jsonConfig);
				sysField.setFieldId(IdUtil.getId());
				sysField.setAttrName(field.getName());
			
				sysField.setFieldType(field.getType().getName());
	
				sysField.setSn(++i);
				if(!SysField.FIELD_PK.equals(sysField.getFieldCat())){
					sysField.setIsHidden(MBoolean.NO.toString());
				}
				sysField.setIsReadable(MBoolean.NO.toString());
				sysField.setIsDisabled(MBoolean.NO.toString());
				sysField.setAllowGroup(MBoolean.YES.toString());
				sysField.setAllowSum(MBoolean.NO.toString());
				
				sysField.setShowNavTree(MBoolean.NO.toString());
				sysField.setAllowExcelInsert(MBoolean.YES.toString());
				sysField.setAllowExcelEdit(MBoolean.YES.toString());
				sysField.setHasAttach(MBoolean.NO.toString());
				sysField.setIsCharCat(MBoolean.NO.toString());
				sysField.setSysModule(module);
				//加到Moduel中
				module.getSysFields().add(sysField);
			}
		}
		if(module.getModuleId()==null){//创建模块
			create(module);
			//初始化该模块的按钮列表
			//sysButtonManager.initDefaultAllButtons(module.getModuleId());
		}else{//更新模块
			update(module);
		}
		//提交上述内容
		//flush();
		//创建关联模块
		//createJoinRelation(module, joinFields);
		createJoinFields(module,joinFields);
		
		return module;
	}
	
	/**
	 * 返回Json配置
	 * @param field
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public String getJsonConfig(Field field){
		FieldConfig fieldConfig=field.getAnnotation(FieldConfig.class);
		if(fieldConfig==null) return "{}";
		return fieldConfig.jsonConfig();
	}
	
	/**
	 * 创建字段的实体关联
	 * @param module
	 * @param joinFields 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void createJoinFields(SysModule module,List<Field> joinFields){
		for(Field field:joinFields){
			//一对多
			OneToMany oneToMany=field.getAnnotation(OneToMany.class);
			//多对一
			ManyToOne manyToOne=field.getAnnotation(ManyToOne.class);
			//多对多
			ManyToMany manyToMany=field.getAnnotation(ManyToMany.class);

			JoinTable joinTable=field.getAnnotation(JoinTable.class);
			
			//SysModLink smLink=sysModLinkManager.getByModuleIdAttrName(module.getModuleId(), field.getName());
			SysField sysField=sysFieldManager.getField(module.getModuleId(), field.getName());
			
			if(sysField==null){
				logger.error("================sysField "+field.getName()+" is null : ");
				return ;
			}
			
			if(oneToMany!=null){
				//取得该集合的实体类型
		        ParameterizedType pt = (ParameterizedType) field.getGenericType(); 
		        java.lang.reflect.Type refFieldType=pt.getActualTypeArguments()[0];
				String fieldCls=refFieldType.toString().replace("class ", "");
		        SysModule linkModule=sysModuleDao.get(fieldCls);
				
				if(linkModule==null){
					try{
						Class FieldClass=Class.forName(fieldCls);
						linkModule=getFromEntityClass(FieldClass);
						if(linkModule==null){
							throw new  RuntimeException("FieldClass[" + FieldClass +"] module is not exist");
						}
					}catch(Exception ex){
						logger.error(ex.toString());
					}
				}
				sysField.setLinkSysModule(linkModule);
				sysField.setCompType(WidgetType.LISTMODULE.getXtype());
			}else if(manyToOne!=null){//如果为关联列 多对一
				SysModule linkModule=sysModuleDao.get(field.getType().getName());
				if(linkModule==null){
					linkModule=getFromEntityClass(field.getType());
				}
				if(linkModule==null){
					throw new  RuntimeException("FieldClass[" + field.getType() +"] module is not exist");
				}
				sysField.setLinkSysModule(linkModule);
				sysField.setCompType(WidgetType.MODULE.getXtype());
			}else if(manyToMany!=null){//多对多
				JoinColumn[] joinColumns=joinTable.inverseJoinColumns();
				
				//取得该集合的实体类型
		        ParameterizedType pt = (ParameterizedType) field.getGenericType(); 
		        java.lang.reflect.Type refFieldType=pt.getActualTypeArguments()[0];
				String fieldCls=refFieldType.toString().replace("class ", "");
		        SysModule linkModule=sysModuleDao.get(fieldCls);
				
				if(linkModule==null){
					try{
						Class FieldClass=Class.forName(fieldCls);
						linkModule=getFromEntityClass(FieldClass);
					}catch(Exception ex){
						logger.error(ex.toString());
					}
				}
				if(linkModule==null){
					throw new  RuntimeException("FieldClass[" + fieldCls +"] module is not exist");
				}
				sysField.setCompType(WidgetType.LISTMODULE.getXtype());
				sysField.setLinkSysModule(linkModule);
			}
			//默认为选择方式
			sysField.setLinkAddMode(SysField.ADD_MODE_SEL);
			sysFieldManager.update(sysField);
		}
	}
	/**
	 * 级别删除模块包括字段信息
	 * @param moduleId
	 */
	public void removeEntityCascade(String moduleId){
		sysFieldManager.deleteByModuleId(moduleId);
		delete(moduleId);
	}
	
	
}