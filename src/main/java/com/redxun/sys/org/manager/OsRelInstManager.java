package com.redxun.sys.org.manager;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.sys.org.dao.OsRelInstDao;
import com.redxun.sys.org.dao.OsRelInstQueryDao;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
/**
 * <pre> 
 * 描述：OsRelInst业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class OsRelInstManager extends BaseManager<OsRelInst>{
	@Resource
	private OsRelInstDao osRelInstDao;
	@Resource
	private OsRelInstQueryDao osRelInstQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return osRelInstDao;
	}
	
	/**
	 * 关系是否已经存在
	 * @param relTypeId
	 * @param party
	 * @return
	 */
	public boolean isPartyExistInRelation(String relTypeId,String party){
		return osRelInstDao.isPartyExistInRelation(relTypeId, party);
	}
	
	/**
	 * 更改当前关系的实例
	 * @param instId
	 * @param userId
	 * @param groupId
	 */
	public void doChangeInst(String instId,String userId,String groupId){
		OsRelInst osRelInst=get(instId);
		String orgParty2=osRelInst.getParty2();
		if(StringUtils.isNotEmpty(userId)){
			if(StringUtils.isNotEmpty(osRelInst.getPath())){
				String newPath=osRelInst.getPath().replace(osRelInst.getParty2()+".",userId+".");
				osRelInst.setPath(newPath);
			}
			osRelInst.setParty2(userId);
			update(osRelInst);
			List<OsRelInst> insts=getByRelTypeIdParty1(osRelInst.getOsRelTypeId(), orgParty2);
			for(OsRelInst inst:insts){
				inst.setParty1(userId);
				update(inst);
			}
		}else{
			if(StringUtils.isNotEmpty(osRelInst.getPath())){
				String newPath=osRelInst.getPath().replace(osRelInst.getParty2()+".",groupId+".");
				osRelInst.setPath(newPath);
			}
			update(osRelInst);
			osRelInst.setParty2(groupId);
			update(osRelInst);
			List<OsRelInst> insts=getByRelTypeIdParty1(osRelInst.getOsRelTypeId(), orgParty2);
			for(OsRelInst inst:insts){
				inst.setParty1(groupId);
				update(inst);
			}
		}
	}
	
	public void delInstCascade(OsRelInst inst){
		
		if(StringUtils.isNotEmpty(inst.getParty2())){
			delete(inst.getInstId());
		}
		
		//取得其下级
		List<OsRelInst> subInsts=getByRelTypeIdParty1(inst.getOsRelType().getId(), inst.getParty2());
		
		if(subInsts.size()==0){
			delete(inst.getInstId());
		}
		
		for(OsRelInst oi:subInsts){
			delInstCascade(oi);
		}
	}

	/**
     * 按关系类型来删除关系实例
     * @param relTypeId
     */
    public void deleteByRelTypeId(String relTypeId){
    	osRelInstDao.deleteByRelTypeId(relTypeId);
    }
	
	 public OsRelInst getByParty1Party2RelTypeId(String party1,String party2,String relTypeId){
		 return osRelInstDao.getByParty1Party2RelTypeId(party1, party2, relTypeId);
	 }
	 
	 public OsRelInst getByParty1RelTypeId(String party1,String relTypeId){
		return osRelInstDao.getByParty1RelTypeId(party1,relTypeId); 
	 }
	 
	 public OsRelInst getByParty2RelTypeId(String party2,String relTypeId){
		 return osRelInstDao.getByParty2RelTypeId(party2,relTypeId); 
	 }
	 
	 
	 /**
	  * 查找某种关系类型的列表
	  * @param relTypeId
	  * @param tenantId
	  * @return
	  */
	 public List<OsRelInst> getByRelTypeIdTenantId(String relTypeId,String tenantId){
		return osRelInstDao.getByRelTypeIdTenantId(relTypeId, tenantId); 
	 }
	 
	 public OsRelInst getByRootInstByTypeId(String typeId){
		 return osRelInstDao.getByRootInstByTypeId(typeId);
	 }

	 /**
	  * 获得用户下的有效授权访问的用户组
	  * @param userId
	  * @param tenantId
	  * @return
	  */
	 public List<OsRelInst> getGrantGroupsByUserId(String userId,String tenantId){
		 return osRelInstDao.getBelongGroupsByUserId(userId, tenantId);
	 }
	 
	 public List<OsRelInst> getByGroupIdRelTypeId(String groupId,String relTypeId,SqlQueryFilter filter){
		 return osRelInstQueryDao.getByGroupIdRelTypeId(groupId, relTypeId, filter);
	 }
	 
 	/**
     * 按关系分类ID及关系方1查找实例
     * @param relTypeId
     * @param party1
     * @return
     */
    public List<OsRelInst> getByRelTypeIdParty1(String relTypeId,String party1){
    	return osRelInstDao.getByRelTypeIdParty1(relTypeId, party1);
    }
    
	/**
	 * 通过part1和part2还有type来查找关系
	 * @param type
	 * @param part1
	 * @param part2
	 * @return
	 */
	public List<OsRelInst> getByTypePart1Part2(String type,String part1,String part2){
		return osRelInstDao.getByTypePart1Part2(type, part1, part2);
	}
    
    /**
     * 按关系分类ID及关系方2查找实例
     * @param relTypeId
     * @param party2
     * @return
     */
    public List<OsRelInst> getByRelTypeIdParty2(String relTypeId,String party2){
    	return osRelInstDao.getByRelTypeIdParty2(relTypeId, party2);
    }
    
    /**
     * 获得用户其他关系的实例
     * @param userId
     * @return
     */
    public List<OsRelInst> getUserOtherRelInsts(String userId){
    	return osRelInstDao.getUserOtherRelInsts(userId);
    }
    /**
     * 通过用户组来删除关系
     * @param groupId
     */
    public void delByGroupId(String groupId){
    	osRelInstDao.delByGroupId(groupId);
    }
    
    /**
     * 删除用户组与用户有某种类型关系的关系实例
     * @param groupId
     * @param userId
     * @param relTypeId
     */
    public void delByGroupIdUserIdRelTypeId(String groupId,String userId,String relTypeId){
    	osRelInstDao.delByGroupIdUserIdRelTypeId(groupId, userId, relTypeId);
    }
    
    /**
     * 通过用户Id来删除关系
     * @param groupId
     */
    public void delByUserId(String userId){
    	osRelInstDao.delByUserId(userId);
    }
    
    /**
     * 按关系方1 关系类型 是否主关系查找实例
     * @param party1
     * @param relTypeKey
     * @param isMain
     * @return
     */
    public List<OsRelInst> getByParty1RelTypeIsMain(String party1,String relTypeKey,String isMain){
    	return osRelInstDao.getByParty1RelTypeIsMain(party1,relTypeKey,isMain);
    }
    
    /**
     * 按关系分类ID 维度ID及关系方2查找实例
     * @param relTypeId
     * @param party2
     * @param dim1
     * @return
     */
    public List<OsRelInst> getByRelTypeIdParty2Dim1(String relTypeId,String party2,String dim1){
    	return osRelInstDao.getByRelTypeIdParty2Dim1(relTypeId, party2, dim1);
    }
    
    /**
     * 添加从属关系
     * @param groupId
     * @param userId
     * @param isMain
     */
    public void addBelongRelInst(String groupId,String userId,String isMain){
    	OsRelInst inst=new OsRelInst();
    	inst.setDim1(OsDimension.DIM_ADMIN_ID);
    	inst.setDim2(null);
    	inst.setParty1(groupId);
    	inst.setParty2(userId);
    	inst.setRelTypeId(OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
    	inst.setRelTypeKey(OsRelType.REL_CAT_GROUP_USER_BELONG);
    	inst.setIsMain(isMain);
    	inst.setRelType(OsRelType.REL_TYPE_GROUP_USER);
    	inst.setStatus(MBoolean.ENABLED.name());
    	create(inst);
    }
}