package com.redxun.sys.core.manager;
import com.redxun.core.constants.MBoolean;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.core.dao.SysGridFieldDao;
import com.redxun.sys.core.entity.SysGridField;
import com.redxun.ui.view.model.BaseGridColumn;
import com.redxun.ui.view.model.FieldColumn;
import com.redxun.ui.view.model.GroupColumn;
import com.redxun.ui.view.model.IColumn;
import com.redxun.ui.view.model.IGridColumn;
import java.util.ArrayList;
/**
 * <pre> 
 * 描述：SysGridField业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysGridFieldManager extends BaseManager<SysGridField>{
	@Resource
	private SysGridFieldDao sysGridFieldDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysGridFieldDao;
	}
	
	/**
	 * 按路径及视图ID获取字段列表
	 * @param gridViewId
	 * @param parentId
	 * @return 
	 * List<SysGridField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysGridField> getByGridViewIdParentId(String gridViewId,String parentId){
		return sysGridFieldDao.getByGridViewIdParentId(gridViewId, parentId);
	}
	/**
	 * 按视图ID获取其所有的字段展示列表
	 * @param gridViewId
	 * @return 
	 * List<SysGridField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysGridField> getByGridViewId(String gridViewId){
            return sysGridFieldDao.getByGridViewId(gridViewId);
	}
        
        /**
         * 按视图Id获得字段列
         * @param gridViewId
         * @return 
         */
        public List<IGridColumn> getColumnsByGridViewId(String gridViewId){
            List<IGridColumn> columns=new ArrayList<IGridColumn>();
            List<SysGridField> fields=getByGridViewIdParentId(gridViewId,"0");
            for(SysGridField field:fields){
                IGridColumn col=genSubGridColumn(field);
                columns.add(col);
            }
            return columns;
        }
        
        private IGridColumn genSubGridColumn(SysGridField field){
            if("FIELD".equals(field.getItemType())){
                FieldColumn col=new FieldColumn();
                col.setAllowSort(MBoolean.YES.toString().equals(field.getAllowSort()));
                col.setField(field.getFieldName());
                col.setWidth(field.getColWidth());
                col.setHeader(field.getFieldTitle());
                col.setVisible(!MBoolean.YES.toString().equals(field.getIsHidden()));
                return col;
            }else {//if("GROUP".equals(field)){
                List<SysGridField> fields=sysGridFieldDao.getByParentId(field.getGdFieldId());
                GroupColumn gCol=new GroupColumn();
                gCol.setHeader(field.getFieldTitle());
                gCol.setWidth(field.getColWidth());
                gCol.setVisible(!MBoolean.YES.toString().equals(field.getIsHidden()));
                for(SysGridField f:fields){
                    IGridColumn col=genSubGridColumn(f);
                    gCol.getColumns().add(col);
                }
                return gCol;
            }
        }
        
	/**
	 * 按视图ID及字段名获得视图内的字段配置
	 * @param gridViewId
	 * @param fieldName
	 * @return 
	 * SysGridField
	 * @exception 
	 * @since  1.0.0
	 */
	public SysGridField getByGridViewIdFieldName(String gridViewId,String fieldName){
		return sysGridFieldDao.getByGridViewIdFieldName(gridViewId, fieldName);
	}
	/**
	 * 按分组ID获取其所有的字段展示列表
	 * TODO方法名称描述
	 * @param groupId
	 * @return 
	 * List<SysGridField>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysGridField> getByGroupId(String groupId){
            return sysGridFieldDao.getByGroupId(groupId);
	}
	
	public void delByPath(String path){
		sysGridFieldDao.delByPath(path);
	}
}