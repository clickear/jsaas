package com.redxun.sys.org.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.constants.MStatus;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.sys.org.dao.OsDimensionDao;
import com.redxun.sys.org.dao.OsRelTypeDao;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsRelType;
/**
 * <pre> 
 * 描述：OsRelType业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class OsRelTypeManager extends BaseManager<OsRelType>{
	@Resource
	private OsRelTypeDao osRelTypeDao;
	@Resource
	private OsDimensionDao osDimensionDao;
	
	@Resource
	private OsRelInstManager osRelInstManager;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return osRelTypeDao;
	}
	/**
	 * 获得用户的关系，排除用户从属关系
	 * @return
	 */
	public List<OsRelType> getUserRelTypeExcludeBelong(){
		return osRelTypeDao.getUserRelTypeExcludeBelong();
	}

	public void deleteCacade(String id){
		osRelInstManager.deleteByRelTypeId(id);
		osRelTypeDao.delete(id);
	}
	
	 /**
     * 获得某种类型的关系列表
     * @param relType
     * @return
     */
    public List<OsRelType> getByRelType(String relType){
    	return osRelTypeDao.getByRelType(relType);
    }
    
    /**
     * 初始化用户组-用户的从属关系
     * @param instId
     * @return
     */
    public OsRelType initGroupBelongRelType(String instId){
    	OsRelType relType=new OsRelType();
    	relType.setName("从属");
    	relType.setParty1("从");
    	relType.setParty2("属");
    	relType.setConstType(OsRelType.CONST_TYPE_ONE_MANY);
    	relType.setKey(OsRelType.REL_CAT_GROUP_USER_BELONG);
    	relType.setRelType(OsRelType.REL_TYPE_GROUP_USER);
    	relType.setIsDefault(MBoolean.YES.toString());
    	relType.setIsSystem(MBoolean.YES.toString());
    	relType.setIsTwoWay(MBoolean.NO.toString());
    	relType.setStatus(MStatus.ENABLED.toString());
    	relType.setTenantId(instId);
    	osRelTypeDao.create(relType);
    	return relType;
    }
    
    /**
     * 初始化用户组-用户的组负责人
     * @param instId
     * @return
     */
    public OsRelType initGroupLeaderRelType(String instId,String dimId){
    	OsDimension dim=osDimensionDao.get(dimId);
    	OsRelType relType=new OsRelType();
    	relType.setName("组负责人");
    	relType.setParty1("组");
    	relType.setParty2("负责人");
    	relType.setDim1(dim);
    	relType.setConstType(OsRelType.CONST_TYPE_ONE_MANY);
    	relType.setKey(OsRelType.REL_CAT_GROUP_USER_LEADER);
    	relType.setRelType(OsRelType.REL_TYPE_GROUP_USER);
    	relType.setIsDefault(MBoolean.YES.toString());
    	relType.setIsSystem(MBoolean.YES.toString());
    	relType.setIsTwoWay(MBoolean.NO.toString());
    	relType.setStatus(MStatus.ENABLED.toString());
    	relType.setTenantId(instId);
    	osRelTypeDao.create(relType);
    	return relType;
    }
    
    /**
     * 获得某租户下的Key对应的关系
     * @param key
     * @param tenantId
     * @return
     */
    public OsRelType getByKeyTenanId(String key,String tenantId){
    	return osRelTypeDao.getByKeyTenanId(key, tenantId);
    }
    /**
     * 获得用户间的关系类型
     * @param tenantId
     * @return
     */
    public List<OsRelType> getUserRelType(String tenantId){
    	return osRelTypeDao.getByRelTypeTenantIdIncludePublic(OsRelType.REL_TYPE_USER_USER, tenantId);
    }
    
    /**
     * 通过维度ID及关系类型获得所有关系
     * @param dimId
     * @param relType
     * @param tenantId
     * @return
     */
    public List<OsRelType> getByDimId1RelType(String dimId,String relType,String tenantId){
    	return osRelTypeDao.getByDimId1RelType(dimId, relType,tenantId);
    }
    /**
     * 获得该维度下的用户与用户组的关系类型定义
     * @param dimId
     * @param tenantId
     * @return
     */
    public List<OsRelType> getRelTypesOfGroupUser(String dimId,String tenantId){
    	return osRelTypeDao.getByDimId1RelType(dimId, OsRelType.REL_TYPE_GROUP_USER,tenantId);
    }
    /**
     * 获得该维度下的用户组间的关系类型定义
     * @param dimId
     * @param tenantId
     * @return
     */
    public List<OsRelType> getRelTypesOfGroupGroup(String dimId,String tenantId){
    	return osRelTypeDao.getByDimId1RelType(dimId, OsRelType.REL_TYPE_GROUP_GROUP,tenantId);
    }
    
    /**
     * 获得从属关系的类型
     * @param tenantId
     * @return
     */
    public OsRelType getBelongRelType(String tenantId){
    	return osRelTypeDao.getByKeyTenanId(OsRelType.REL_CAT_GROUP_USER_BELONG, tenantId);
    }
    
    /**
     * 按Key获得某种关系类型
     * @param key
     * @return
     */
    public OsRelType getBelongRelType(){
    	return osRelTypeDao.getByRelTypeKey(OsRelType.REL_CAT_GROUP_USER_BELONG);
    }
    
    /**
     * 获得组与用户的关系类型列表
     * @param relType
     * @return
     */
    public List<OsRelType> getGroupUserRelations(String tenantId){
    	return osRelTypeDao.getByRelTypeTenantIdIncludePublic(OsRelType.REL_TYPE_GROUP_USER, tenantId);
    }
    /**
     * 获得用户组间的关系类型列表
     * @return
     */
    public List<OsRelType> getGroupGroupRelations(String tenantId){
    	return osRelTypeDao.getByRelTypeTenantId(OsRelType.REL_TYPE_GROUP_GROUP, tenantId);
    }
    
    /**
     * 获得某个维度下的所有关系
     * @param dimId
     * @return
     */
    public List<OsRelType> getByDimIdRelType(String dimId){
    	return osRelTypeDao.getByDimIdRelType(dimId);
    }
    
    
}