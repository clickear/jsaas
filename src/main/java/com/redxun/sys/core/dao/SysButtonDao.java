package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysButton;
/**
 * <pre> 
 * 描述：SysButton数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州微通软件有限公司版权所有
 * </pre>
 */
@Repository
public class SysButtonDao extends BaseJpaDao<SysButton> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysButton.class;
    }
    /**
     * 按模块、位置取得系统按钮列表
     * @param moduleId
     * @param pos
     * @return 
     * List<SysButton>
     * @exception 
     * @since  1.0.0
     */
    public List<SysButton> getByModuleIdPos(String moduleId,String pos){
    	String ql="from SysButton sb where sb.sysModule.moduleId=? and sb.pos=? order by sb.sn asc";
    	return getByJpql(ql, new Object[]{moduleId,pos});
    }
    /**
     * 取得模块下的所有按钮配置
     * @param moduleId
     * @return 
     * List<SysButton>
     * @exception 
     * @since  1.0.0
     */
    public List<SysButton> getByModuleId(String moduleId){
    	String ql="from SysButton sb where sb.sysModule.moduleId=? order by sb.sn asc";
    	return getByJpql(ql, new Object[]{moduleId});
    }
    /**
     * 按模块ID及按钮类型获取按钮实体定义
     * @param moduleId
     * @param btnType
     * @return 
     * SysButton
     * @exception 
     * @since  1.0.0
     */
    public SysButton getByModuleIdButtonType(String moduleId,String btnType){
    	String ql="from SysButton sb where sb.sysModule.moduleId=? and sb.btnType=?";
    	return (SysButton)this.getUnique(ql, new Object[]{moduleId,btnType});
    }
    /**
     * 按模块ID及关联模块Id、按钮的类型获得按钮的定义
     * @param moduleId
     * @param linkModuleId
     * @param btnType
     * @return 
     * SysButton
     * @exception 
     * @since  1.0.0
     */
    public SysButton getByRefModuleIdButtonType(String moduleId,String linkModuleId,String btnType){
    	String ql="from SysButton sb where sb.sysModule.moduleId=? and sb.linkSysModule.moduleId=? and sb.btnType=?";
    	return (SysButton) this.getUnique(ql, new Object[]{moduleId,linkModuleId,btnType});
    }
    /**
     * 按模块ID及关联模块Id及位置获得按钮列表
     * @param moduleId
     * @param linkModuleId
     * @param btnPos
     * @return 
     * List<SysButton>
     * @exception 
     * @since  1.0.0
     */
    public List<SysButton> getByRefModuleIdButtonPos(String moduleId,String linkModuleId,String btnPos){
    	String ql="from SysButton sb where sb.sysModule.moduleId=? and sb.linkSysModule.moduleId=? and sb.pos=? order by sb.sn asc";
    	return this.getByJpql(ql, new Object[]{moduleId,linkModuleId,btnPos});
    }
    
}
