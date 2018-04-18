package com.redxun.sys.org.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.entity.BaseEntity;
import com.redxun.saweb.constants.HandlerResult;
import com.redxun.saweb.handler.EntityPersistenceHandler;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;
/**
 * 
 * <pre> 
 * 描述：实体用户组持久化处理器
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱: chshxuan@163.com
 * 日期:2014年12月26日-下午12:01:35
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class OsGroupHandler implements EntityPersistenceHandler{
	@Resource
	OsGroupManager osGroupManager;
	
	@Override
	public HandlerResult preExecute(BaseEntity entity,boolean isCreated) {
		OsGroup group=(OsGroup)entity;
		//设置层次关系等信息
		if(!isCreated) return HandlerResult.SCCUESS;
		String parentId=group.getParentId();
		if(parentId==null) parentId="0";
		OsGroup parentGroup=null;
		group.setChilds(0);
		if(!"0".equals(parentId)){
			parentGroup=osGroupManager.get(parentId);
			group.setDepth(parentGroup.getDepth()+1);
			group.setPath(parentGroup.getPath()+group.getGroupId()+".");
			Long childs=osGroupManager.getChildCounts(parentId);
			parentGroup.setChilds(childs.intValue()+1);
			osGroupManager.update(parentGroup);
		}else{
			group.setPath("0."+group.getGroupId()+".");
			group.setDepth(1);
			
		}
		group.setForm("ADDED");
		return HandlerResult.SCCUESS;
	}

	@Override
	public HandlerResult afterExecute(BaseEntity entity, boolean isCreated) {
		return HandlerResult.SCCUESS;
	}
	
}
