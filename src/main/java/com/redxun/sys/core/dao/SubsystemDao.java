package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.Subsystem;
import com.redxun.sys.core.entity.SysMenu;
/**
 * <pre> 
 * 描述：Subsystem数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SubsystemDao extends BaseJpaDao<Subsystem> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return Subsystem.class;
    }
    
	/**
	 * 
	 * 按Key与tenantId获取其值
	 * @param key
	 * @param tenantId
	 * @return SysTreeCat
	 * @exception
	 * @since 1.0.0
	 */
	public Subsystem getByKey(String key) {
		String ql = "from Subsystem where key=?";
		return (Subsystem) getUnique(ql, new Object[] { key });
	}
	
	 
    /**
     * 获得所有子系统并排序
     * @return
     */
    public List<Subsystem> getAllOrderBySn(){
    	String ql="from Subsystem m order by m.sn asc";
    	return this.getByJpql(ql, new Object[]{});
    }
	
	/**
	 * 按状态查找子系统
	 * @param status
	 * @return
	 */
	public List<Subsystem> getByStatus(String status,String tenantId){
		String ql="from Subsystem where status=? and tenantId=? order by sn asc";
		return this.getByJpql(ql,new Object[]{status,tenantId});
	}
	
	/**
	 * 按SAAS模式查找
	 * @param isSaas
	 * @return
	 */
	public List<Subsystem> getByIsSaasStatus(String isSaas,String status){
		String ql="from Subsystem where isSaas=? and status=? order by sn asc";
		return this.getByJpql(ql,new Object[]{isSaas,status});
	}
	/**
	 * 根据机构类型获得子系统
	 * @param instType
	 * @return
	 */
	public List<Subsystem> getByInstType(String instType){
		String ql="from Subsystem where instType=? order by sn asc";
		return this.getByJpql(ql, new Object[]{instType});
	}
	
	/**
	 * 根据机构类型及状态获得子系统
	 * @param instType
	 * @param status
	 * @return
	 */
	public List<Subsystem> getByInstTypeStatus(String instType,String status){
		String ql="from Subsystem where instType=? and status=? order by sn asc";
		return this.getByJpql(ql, new Object[]{instType,status});
	}
	
    
}
