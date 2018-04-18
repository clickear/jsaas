package com.redxun.sys.core.manager;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.SortParam;
import com.redxun.core.util.BeanUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.core.dao.SysGridViewDao;
import com.redxun.sys.core.entity.GridColumn;
import com.redxun.sys.core.entity.SysField;
import com.redxun.sys.core.entity.SysGridField;
import com.redxun.sys.core.entity.SysGridFieldNode;
import com.redxun.sys.core.entity.SysGridView;
import com.redxun.sys.core.entity.SysModule;
/**
 * <pre> 
 * 描述：SysGridView业务服务类
 * 构建组：saweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysGridViewManager extends BaseManager<SysGridView>{
	@Resource
	private SysGridViewDao sysGridViewDao;
	@Resource
	private SysModuleManager sysModuleManager;
	@Resource
	private SysGridFieldManager sysGridFieldManager;

	@Resource
	private SysFieldManager sysFieldManager;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysGridViewDao;
	}
	/**
	 * 通过实体名获取视图列表
	 * @param entityName
	 * @param creatorId
	 * @return 
	 * List<SysGridView>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysGridView> getViewsByEntityName(String entityName,String creatorId){
		SysModule sysModule=sysModuleManager.getByEntityClass(entityName);
		if(sysModule==null) return new ArrayList<SysGridView>();
		return sysGridViewDao.getByModuleId(sysModule.getModuleId(), creatorId);
	}
        /**
         * 按模块获得用户的视图信息
         * @param moduleId 实体模块Id
         * @param userId 用户ID
         * @param viewName 视图名称
         * @param page 分页
         * @param sortParam 排序字段
         * @return 
         */
        public List<SysGridView> getByModuleIdUserIdName(String moduleId,String userId,String viewName,Page page,SortParam sortParam){
            return sysGridViewDao.getByModuleIdUserIdName(moduleId, userId, viewName, page, sortParam);
        }
	/**
	 * 取得模块字段列表，除去某属性及主键的属性字段外
	 * @param moduleId
	 * @param fieldType
	 * @return 
	 * List<GridColumn>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<GridColumn> getColumnsByModuleIdExcludeFieldType(String moduleId,String fieldType){
		List<GridColumn> columns=new ArrayList<GridColumn>();
		SysModule sysModule=sysModuleManager.get(moduleId);
		if(sysModule==null) return columns;
		List<SysField> sysFields=sysFieldManager.getDefaultShowFields(sysModule.getModuleId());
		for(SysField field:sysFields){
			
			if(field.getLinkSysModule()!=null && field.getLinkSysModule().getEntityName().equals(fieldType)) continue;
			
			if(!SysField.FIELD_COMMON.equals(field.getFieldCat())) continue;
			
			GridColumn col=new GridColumn();
			col.setText(field.getTitle());
			col.setDataIndex(field.getAttrName());
			if(MBoolean.YES.toString().equals(field.getAllowSort())){
				col.setSortable(true);
			}else{
				col.setSortable(false);
			}
			columns.add(col);
		}
		return columns;
	}
	
	/**
	 * 为选择器返回有效的列
	 * @param entityName
	 * @return 
	 * List<GridColumn>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<GridColumn> getColumnsByForSelector(String entityName){
		List<GridColumn> columns=new ArrayList<GridColumn>();
		SysModule sysModule=sysModuleManager.getByEntityClass(entityName);
		if(sysModule==null) return columns;
		//TODO 允许为每个实体中定义选择器显示的列视图
		//SysGridView sysGridView=sysGridViewDao.getDefaultViewByModuleIdCreateorId(sysModule.getModuleId());
		
		List<SysField> sysFields=sysFieldManager.getByModuleFieldCat(sysModule.getModuleId(), SysField.FIELD_COMMON);
		for(SysField field:sysFields){
			GridColumn col=new GridColumn();
			col.setText(field.getTitle());
			col.setDataIndex(field.getAttrName());
			if(MBoolean.YES.toString().equals(field.getAllowSort())){
				col.setSortable(true);
			}else{
				col.setSortable(false);
			}
			columns.add(col);
		}
		return columns;
	}

	public List<GridColumn> getColumnsByEntityNameUserId(String entityName,String userId){
		List<GridColumn> columns=new ArrayList<GridColumn>();
		SysModule sysModule=sysModuleManager.getByEntityClass(entityName);
		if(sysModule==null) return columns;
		SysGridView sysGridView=sysGridViewDao.getDefaultViewByModuleIdCreateorId(sysModule.getModuleId(), userId);
		if(sysGridView!=null){
			return getColumnsByGridView(sysGridView.getGridViewId(),"0");
		}
		return getColumnsByEntityName(entityName);
	}
	/**
	 * 按实体名及视图名获取表格列表
	 * @param entityName
	 * @param viewName
	 * @param creatorId 创建人ID
	 * @return 
	 * List<GridColumn>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<GridColumn> getColumnsByEntityName(String entityName){
		List<GridColumn> columns=new ArrayList<GridColumn>();
		SysModule sysModule=sysModuleManager.getByEntityClass(entityName);
		
		if(sysModule==null) return columns;
		//当视图名称为空时，则代表默认的视图，其字段从该模块的所有字段中获取
		
		List<SysField> sysFields=sysFieldManager.getDefaultShowFields(sysModule.getModuleId());
		for(SysField field:sysFields){
			GridColumn col=new GridColumn();
			col.setText(field.getTitle());
			col.setDataIndex(field.getAttrName());
			if(MBoolean.YES.toString().equals(field.getAllowSort())){
				col.setSortable(true);
			}else{
				col.setSortable(false);
			}
			columns.add(col);
		}
		return columns;
	}
	

	/**
	 * 按父ID及视图取得字段及分组列表
	 * @param gridViewId
	 * @param parentId
	 * @return 
	 * List<GridColumn>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<GridColumn> getColumnsByGridView(String gridViewId,String parentId){
		List<GridColumn> columns=new ArrayList<GridColumn>();
		
		List<SysGridField> gridFields=sysGridFieldManager.getByGridViewIdParentId(gridViewId,parentId);
		
		for(SysGridField field:gridFields){
			if("FIELD".equals(field.getItemType())){
				GridColumn column=new GridColumn();
				column.setWidth(field.getColWidth());
				column.setDataIndex(field.getFieldName());
				column.setText(field.getFieldTitle());
				column.setHidden(MBoolean.YES.toString().equals(field.getIsHidden()));
				columns.add(column);
			}else{
				GridColumn column=new GridColumn();
				column.setWidth(field.getColWidth());
				column.setText(field.getFieldTitle());
				column.setHidden(MBoolean.YES.toString().equals(field.getIsHidden()));
				List<GridColumn> childColumns=getColumnsByGridView(gridViewId,field.getGdFieldId());
				column.setColumns(childColumns);
				columns.add(column);
			}
		}

		return columns;
	}
	/**
	 * 构建树节点列表
	 * @param gridViewId
	 * @param parentId
	 * @return 
	 * List<SysGridFieldNode>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysGridFieldNode> getSysGridFieldNodes(String gridViewId,String parentId){
		List<SysGridFieldNode> nodes=new ArrayList<SysGridFieldNode>();
		List<SysGridField> gridFields=sysGridFieldManager.getByGridViewIdParentId(gridViewId,parentId);
		for(SysGridField field:gridFields){
			SysGridFieldNode node=new SysGridFieldNode();
			BeanUtil.copyProperties(node, field);	
			if("GROUP".equals(field.getItemType())){
				List<SysGridFieldNode> childNodes=getSysGridFieldNodes(gridViewId,field.getGdFieldId());
				node.setChildren(childNodes);
				node.setLeaf(false);
				node.setExpanded(true);
			}else{
				node.setLeaf(true);
				node.setExpanded(false);
			}
			nodes.add(node);
		}
		return nodes;
	}
	
	@Override
	public void create(SysGridView entity) {
		if(MBoolean.YES.toString().equals(entity.getIsDefault())){
			sysGridViewDao.updateIsDefault(MBoolean.NO.toString(),entity.getSysModule().getModuleId(),ContextUtil.getCurrentUserId());
		}
		super.create(entity);
	}
	
	@Override
	public void update(SysGridView entity) {
		if(MBoolean.YES.toString().equals(entity.getIsDefault())){
			sysGridViewDao.updateIsDefault(MBoolean.NO.toString(),entity.getSysModule().getModuleId(),ContextUtil.getCurrentUserId());
		}
		super.update(entity);
	}
}