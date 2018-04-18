package com.redxun.oa.info.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.info.entity.InsPortCol;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：InsPortCol数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class InsPortColDao extends BaseJpaDao<InsPortCol> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return InsPortCol.class;
    }
    /**
     * 查找是否有某个门户某个栏目的关联关系
     * @param portId 门户Id
     * @param colId  栏目Id
     * @return
     */
    @SuppressWarnings("rawtypes")
	public InsPortCol getByPortCol(String portId, String colId){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="from InsPortCol inspc where inspc.insPortal.portId=? and inspc.insColumn.colId=? and inspc.tenantId = ?";
    	return (InsPortCol)this.getUnique(ql, new Object[]{portId, colId,tenantId});
    }
    
    /**
     * 删除PortCol表中某个门户某个栏目的关联关系
     * @param portId 门户Id
     * @param colId  栏目Id
     * @return
     */
    public void delByPortCol(String portId, String colId){
    	String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="delete InsPortCol inspc where inspc.insPortal.portId=? and inspc.insColumn.colId=? and inspc.tenantId = ?";
    	this.delete(ql, new Object[]{portId,colId,tenantId});
    }
    
    /**
     * 删除PortCol表中所有门户Id为portId的数据
     * @param portId 门户Id
     * @return
     */
	public void delByPortal(String portId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql="delete InsPortCol inspc where inspc.insPortal.portId=? and inspc.tenantId = ?";
    	this.delete(ql, new Object[]{portId,tenantId});
    }
    /**
     * 得到门户栏目中间表中所有门户Id为portId的，并按序号从小到大排序
     * @param portId 门户Id
     * @return 
     */
	public List<InsPortCol> getByPortId(String portId){
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="from InsPortCol inspc where inspc.insPortal.portId=? and inspc.tenantId = ? order by inspc.sn";
    	return this.getByJpql(ql, new Object[]{portId,tenantId});
	}
}
