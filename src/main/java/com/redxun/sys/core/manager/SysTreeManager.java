package com.redxun.sys.core.manager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.bpm.core.manager.BpmAuthSettingManager;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.StringUtil;
import com.redxun.org.api.model.ITenant;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.dao.SysTreeDao;
import com.redxun.sys.core.entity.SysTree;
/**
 * <pre> 
 * 描述：SysTree业务服务类
 * 构建组：saweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysTreeManager extends BaseManager<SysTree>{
	@Resource
	private SysTreeDao sysTreeDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysTreeDao;
	}
	
	 /**
     * 按Key获得系统树节点
     * @param key
     * @return
     */
    public SysTree getByKey(String key){
    	return sysTreeDao.getByKey(key);
    }
	
	public List<SysTree> getByCatKey(String catKey){
		return sysTreeDao.getByCatKey(catKey);
	}
	
	 /**
     * 获得某分类下的租户下的树菜单
     * @param catKey
     * @param tenantId
     * @return
     */
    public List<SysTree> getByCatKeyTenantId(String catKey,String tenantId){
		return sysTreeDao.getByCatKeyTenantId(catKey,tenantId);
	}
    
	 /**
     * 获得某分类下的租户下的树菜单
     * @param catKey
     * @param tenantId
     * @param judgeRight 传入这个参数即接受权限的管理
     * @return
     */
    public List<SysTree> getByCatKeyTenantId(String catKey,String tenantId,Boolean judgeRight){
		return sysTreeDao.getByCatKeyTenantId(catKey,tenantId);
	}
	
	public SysTree insert(String name,String parentId,String catKey){
		SysTree parentSysTree=sysTreeDao.get(parentId);
		String parentPath="0.";
		int depth=1;
		if(parentSysTree!=null){
			depth=parentSysTree.getDepth()+1;
			parentPath=parentSysTree.getPath();
		}
		SysTree sysTree=new SysTree();
		sysTree.setTreeId(IdUtil.getId());
		sysTree.setName(name);
		sysTree.setCatKey(catKey);
		sysTree.setKey(getUniqueKey(catKey));
		sysTree.setPath(parentPath+sysTree.getTreeId()+".");
		sysTree.setDepth(depth);
		sysTree.setParentId(parentId);
		int sn=sysTreeDao.getCountsByParentId(parentId, catKey, ContextUtil.getCurrentTenantId());
		//获取该父类下的子类数
		sysTree.setSn(sn+1);
		sysTreeDao.create(sysTree);
		return sysTree;
	}
	
	public SysTree update(String treeId,String name,String key,int sn){
		SysTree sysTree=sysTreeDao.get(treeId);
		if(sysTree!=null){
			sysTree.setName(name);
			sysTree.setKey(key);
			sysTree.setSn(sn);
			sysTreeDao.update(sysTree);
		}
		return sysTree;
	}
	
	public void deleteCascade(String treeId){
		SysTree sysTree=sysTreeDao.get(treeId);
		if(StringUtils.isEmpty(sysTree.getPath())){
			sysTreeDao.delete(treeId);
		}else{
			sysTreeDao.delByPath(sysTree.getPath());
		}
	}
	
	public String getUniqueKey(String catKey){
		return catKey+":" + IdUtil.getId();
	}
	
	public SysTree getByKey(String key,String catKey,String tenentId){
		SysTree sysTree= sysTreeDao.getByKey(key, catKey, tenentId);
		if(sysTree==null){
			return sysTreeDao.getByKey(key, catKey, ITenant.ADMIN_TENANT_ID);
		}
		return sysTree;
	}
	
	public boolean isKeyExistInCat(String key,String catKey,String tenentId){
		SysTree sysTree=getByKey(key, catKey, tenentId);
		return sysTree==null?false:true;
	}
	
	public int getCountsByParentId(String parentId){
		return sysTreeDao.getCountsByParentId(parentId);
	}
	
	public List<SysTree>getByParentIdCatKey(String parentId,String catKey,String tenantId){
		return sysTreeDao.getByParentIdCatKey(parentId, catKey, tenantId);
	}
	
	public List<SysTree>getByParentId(String parentId){
		return sysTreeDao.getByParentId(parentId);
	}
	/**
	 * 通过用户ID及分类Key获得树
	 * @param userId
	 * @param catKey
	 * @return
	 */
	public List<SysTree> getByUserIdCatKey(String userId,String catKey){
		return sysTreeDao.getByUserIdCatKey(userId, catKey);
	}
	
	/**
	 * 获得用户的附件文件列表
	 * @param userId
	 * @return
	 */
	public List<SysTree> getPersonFileTree(String userId){
		return sysTreeDao.getByUserIdCatKey(userId, SysTree.CAT_PERSON_FILE);
	}

	 
	 /**
	  * 创建用于表单中的数据字典分类
	  * @return
	  */
	 public SysTree createFormDicTree(String name){
		 String id=IdUtil.getId();
		 SysTree sysTree=new SysTree();
		 sysTree.setCatKey(SysTree.CAT_FORM_DIC);
		 sysTree.setDataShowType(SysTree.SHOW_TYPE_FLAT);
		 sysTree.setDepth(1);
		 sysTree.setKey(id);
		 sysTree.setName(name);
		 sysTree.setSn(1);
		 sysTree.setPath("0."+id+".");
		 create(sysTree);
		 return sysTree;
	 }
	 
	 /**
	     * 知识仓库中获取主目录
	     * @param catKey key值
	     * @param tenantId 租户Id
	     * @return
	     */
	    public List<SysTree> getMainMenu(String catKey,String tenantId){
			return sysTreeDao.getMainMenu(catKey,tenantId);
		}
	
	    public List<SysTree> getByTreeIdNameKey(String tenantId,String path,String name,String key){
	    	return sysTreeDao.getByTreeIdNameKey(tenantId, path, name, key);
	    }
	    
	    public List<SysTree> getByCatKeyTreeId(String catKey,String treeId,String tenantId){
	    	return sysTreeDao.getByCatKeyTreeId(catKey, treeId, tenantId);
	    }	
	    
	 /**
	  * 获取删除的树列表。
	  * @param sysTrees 全部属性数据。
	  * @param userList	使用的属性数据。
	  * @return
	  */
	 public  List<SysTree> getRemoveList(List<SysTree> sysTrees,List userList){
		Map<String,SysTree> treeMap= convertToMap(sysTrees);
		Set<String> treeSet=new HashSet<String>();
		String bpmGrantType=BpmAuthSettingManager.getGrantType();
		for(Object obj:userList){
			Map map=(Map)obj;
			if(map==null) continue;
			String treeId=(String) map.get("TREE_ID_");
			if(treeId==null) continue;
			String treePath=(String) map.get("TREE_PATH_");
			Integer amount=Integer.parseInt(map.get("AMOUNT").toString());
			SysTree sysTree=treeMap.get(treeId);
			//设置数量
			if("bpmAssortment".equals(bpmGrantType)){
				
			}else{
				sysTree.setName(sysTree.getName() +"("+ amount +")");
			}
			treePath=StringUtil.trimPrefix(treePath, "0.");
			treePath=StringUtil.trimSuffix(treePath, ".");
			String[] aryTree=treePath.split("[.]");
			
			treeSet.addAll(Arrays.asList(aryTree));
		}
		/**
		 * 标记为已用。
		 */
		for(Iterator<String> it=treeSet.iterator();it.hasNext();){
			String treeId=it.next();
			SysTree sysTree=treeMap.get(treeId);
			sysTree.setUsed(true);
		}
		/**
		 * 
		 * 整理没用到的数据。
		 */
		List<SysTree> removeTrees=new ArrayList<SysTree>();
		for(SysTree tree:sysTrees){
			if(!tree.getUsed()){
				removeTrees.add(tree);
			}
		}
		return removeTrees;
	 }
	 
	 /**
	  * 设置数量。
	  * @param sysTrees
	  * @param userList
	  */
	 public  void setAmount(List<SysTree> sysTrees,List userList){
			Map<String,SysTree> treeMap= convertToMap(sysTrees);
			for(Object obj:userList){
				Map map=(Map)obj;
				if(map==null) continue;
				String treeId=(String) map.get("TREE_ID_");
				if(treeId==null) continue;
				Integer amount=Integer.parseInt(map.get("AMOUNT").toString());
				SysTree sysTree=treeMap.get(treeId);
				//设置数量
				sysTree.setName(sysTree.getName() +"("+ amount +")");
			}
		 } 
	 
	 /**
	 * 将树形转成
	 * @param sysTrees
	 * @return
	 */
	private Map<String,SysTree> convertToMap(List<SysTree> sysTrees){
		 //List<SysTree> sysTrees
		Map<String,SysTree> treeMap=new HashMap<String, SysTree>();
		for(SysTree tree:sysTrees){
			tree.setUsed(false);
			treeMap.put(tree.getTreeId(), tree);
		}
		return treeMap;
	}
		
	    
}