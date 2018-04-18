package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.Page;
import com.redxun.core.query.SortParam;
import com.redxun.sys.core.entity.SysGridView;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
/**
 * <pre> 
 * 描述：SysGridView数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysGridViewDao extends BaseJpaDao<SysGridView> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysGridView.class;
    }
    /**
     * 取得某个模块下的列表展现列表
     * @param moduleId
     * @return 
     * List<SysGridView>
     * @exception 
     * @since  1.0.0
     */
    public List<SysGridView> getByModuleId(String moduleId){
    	String ql="from SysGridView gv where gv.sysModule.moduleId=? order by sn asc";
    	return this.getByJpql(ql, moduleId);
    }
    
    /**
     * 按模块获得用户的模块展示的视图信息
     * @param moduleId 模块ID
     * @param userId 用户Id
     * @param name 名称
     * @param page 分页
     * @param sortParam 排序字段
     * @return 
     */
    public List<SysGridView> getByModuleIdUserIdName(String moduleId,String userId,String name,Page page,SortParam sortParam){
        List params=new ArrayList();
        String ql="from SysGridView gv where gv.sysModule.moduleId=? and gv.createBy=? ";
        params.add(moduleId);
        params.add(userId);
        if(StringUtils.isNotEmpty(name)){
            ql+="and gv.name like ?";
            params.add("%" + name + "%");
        }
        //加上排序
        if(sortParam!=null){
            ql+=" order by " + sortParam.getProperty() + " " + sortParam.getDirection();
        }
        return this.getByJpql(ql,params.toArray(),page);
    }
    
    /**
     * 取得某个模块下的列表展现列表
     * @param moduleId
     * @param creatorId
     * @return 
     * List<SysGridView>
     * @exception 
     * @since  1.0.0
     */
    public List<SysGridView> getByModuleId(String moduleId,String creatorId){
    	String ql="from SysGridView gv where gv.sysModule.moduleId=? and gv.createBy=? order by sn asc";
    	return this.getByJpql(ql, moduleId,creatorId);
    }
    /**
     * 取得用户默认的模块视图方案
     * @param moduleId
     * @param creatorId
     * @return 
     * SysGridView
     * @exception 
     * @since  1.0.0
     */
    public SysGridView getDefaultViewByModuleIdCreateorId(String moduleId,String creatorId){
    	String ql="from SysGridView gv where gv.sysModule.moduleId=? and gv.createBy=? and gv.isDefault=?";
    	return (SysGridView) this.getUnique(ql, new Object[]{moduleId,creatorId,MBoolean.YES.toString()});
    }
    /**
     * 按模块Id、视图名称
     * @param moduleId
     * @param viewName
     * @return 
     * List<SysGridView>
     * @exception 
     * @since  1.0.0
     */
    public List<SysGridView>getByModuleIdViewName(String moduleId,String viewName){
    	String ql="from SysGridView gv where gv.sysModule.moduleId=? and gv.name=?";
    	return this.getByJpql(ql, new Object[]{moduleId,viewName});
    }
    
    /**
     * 按模块Id、视图名称
     * @param moduleId
     * @param viewName
     * @return 
     * List<SysGridView>
     * @exception 
     * @since  1.0.0
     */
    public SysGridView getByModuleIdViewName(String moduleId,String viewName,String creatorId){
    	String ql="from SysGridView gv where gv.sysModule.moduleId=? and gv.name=? and gv.createBy=? ";
    	return (SysGridView)this.getUnique(ql, new Object[]{moduleId,viewName,creatorId});
    }
    
    /**
     * 按列表表格视图
     * @param moduleId
     * @param creatorId
     * @param isDefault
     * @return 
     * List<SysGridView>
     * @exception 
     * @since  1.0.0
     */
    public List<SysGridView> getByModuleIdIsDefault(String moduleId,String creatorId,String isDefault){
    	String ql="from SysGridView gv where gv.sysModule.moduleId=? and gv.createBy=? and gv.isDefault=?";
    	return this.getByJpql(ql, new Object[]{moduleId,creatorId,isDefault});
    }
    
    /**
     * 更新其状态
     * TODO方法名称描述
     * @param moduleId
     * @param userId 
     * void
     * @exception 
     * @since  1.0.0
     */
    public void updateIsDefault(String isDefault,String moduleId,String userId){
    	String ql="Update SysGridView v set v.isDefault=? where v.sysModule.moduleId=? and v.createBy=?";
    	this.update(ql,isDefault,moduleId,userId);
    }
    
}
