package com.redxun.sys.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.core.dao.SubsystemDao;
import com.redxun.sys.core.dao.SubsystemQueryDao;
import com.redxun.sys.core.dao.SysMenuDao;
import com.redxun.sys.core.entity.Subsystem;
import com.redxun.sys.core.entity.SysInstType;
/**
 * <pre> 
 * 描述：Subsystem业务服务类
 * 构建组：saweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SubsystemManager extends BaseManager<Subsystem>{
	@Resource
	private SubsystemDao subsystemDao;
	@Resource
	private SysMenuDao sysMenuDao;
	
	@Resource
	private SubsystemQueryDao subsystemQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return subsystemDao;
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
	public Subsystem getByKey(String key){
		return subsystemDao.getByKey(key);
	}
	
	 
    /**
     * 获得所有子系统并排序
     * @return
     */
    public List<Subsystem> getAllOrderBySn(){
    	return subsystemDao.getAllOrderBySn();
    }
	
	/**
	 * 级联删除，同时删除该系统下的所有子菜单
	 * @param sysId 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void deleteCascade(String sysId){
		sysMenuDao.delBySysId(sysId);
		delete(sysId);
	}
	/**
	 * 获得有效的系统
	 * @return
	 */
	public List<Subsystem> getByValidSystem(){
		return subsystemQueryDao.getByStatus(MBoolean.ENABLED.toString());
	}
	
	public List<Subsystem> getPlatformValidSystem(){
		return subsystemQueryDao.getByInstTypeStatus(SysInstType.INST_TYPE_PLATFORM, MBoolean.ENABLED.name());
	}
	/**
	 * 通过子系统和机构关系表找到类型下的子系统
	 * @return
	 */
	public List<Subsystem> getPlatformValidSystemBySTSR(){
		return subsystemQueryDao.getSubSysSTSR(SysInstType.INST_TYPE_PLATFORM, MBoolean.ENABLED.name());
	}
	
	/**
	 * 获得有效的子系统
	 * @param instType
	 * @return
	 */
	public List<Subsystem> getInstTypeValidSystem(String instType){
		return subsystemQueryDao.getByInstTypeStatus(instType, MBoolean.ENABLED.toString());
	}
	
	public List<Subsystem> getByInstType(String instType){
		return subsystemDao.getByInstType(instType);
	}
	
	/**
	 * 获得用户组授权的子系统
	 * @param groupId
	 * @return
	 */
	public List<Subsystem> getGrantSubsByGroupId(String groupId){
		return subsystemQueryDao.getGrantSubsByGroupId(groupId);
	}
	
	public List<Subsystem> getGrantSubsByUserId(String userId,String tenantId,String instType){
		return subsystemQueryDao.getGrantSubsByUserId(userId,tenantId,instType);
	}
	/**
	 * 通过的条件增加STSR(机构类型与子系统关系表)
	 * @param userId
	 * @param tenantId
	 * @param instType
	 * @return
	 */
	public List<Subsystem> getGrantSubsByUserIdBySTSR(String userId,String tenantId,String instType){
		return subsystemQueryDao.getGrantSubsByUserIdBySTSR(userId,tenantId,instType);
	}
	
	/**
	 * 按SaaS及状态获得系统管理列表
	 * @param isSaas
	 * @param status
	 * @return
	 */
	public List<Subsystem> getByIsSaasStatus(String isSaas,String status){
		return subsystemQueryDao.getByIsSaasStatus(isSaas, status);
	}
	
	/**
	 * 根据机构类型及状态获得子系统
	 * @param instType
	 * @param status
	 * @return
	 */
	public List<Subsystem> getByInstTypeStatus(String instType,String status){
		return subsystemDao.getByInstTypeStatus(instType, status);
	}
	
	/**
	 * 根据机构类型及状态获得子系统
	 * @param instType
	 * @param status
	 * @return
	 */
	public List<Subsystem> getByInstTypeStatusBySTSR(String instType,String status){
		return subsystemQueryDao.getSubSysSTSR(instType, status);
	}
	
	/**获取所有某状态的租户下的子系统
	 * @param status
	 * @param tenantId
	 * @return
	 */
	public List<Subsystem> getByStatus(String status,String tenantId){
		return subsystemDao.getByStatus(status,tenantId);
	}
		
	
	
}