package com.redxun.sys.core.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.sys.core.dao.SysSearchItemDao;
import com.redxun.sys.core.entity.SearchItemNode;
import com.redxun.sys.core.entity.SysSearchItem;
/**
 * <pre> 
 * 描述：SysSearchItem业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysSearchItemManager extends BaseManager<SysSearchItem>{
	@Resource
	private SysSearchItemDao sysSearchItemDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysSearchItemDao;
	}
	/**
	 * 按搜索条件ID及父ID获取列表
	 * @param searchId
	 * @param parentId
	 * @return 
	 * List<SysSearchItem>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SysSearchItem> getBySearchIdParentId(String searchId,String parentId){
		return sysSearchItemDao.getBySearchIdParentId(searchId, parentId);
	}
        /**
         * 获得某个搜索下的所有搜索条件项列表
         * @param searchId
         * @return 
         */
        public List<SysSearchItem> getBySearchId(String searchId){
            return sysSearchItemDao.getBySearchId(searchId);
        }
	
	public List<SearchItemNode> getSearchItemNodes(String searchId,String parentId){
		List<SearchItemNode> nodes=new ArrayList<SearchItemNode>();
		List<SysSearchItem> items=sysSearchItemDao.getBySearchIdParentId(searchId, parentId);
		for(SysSearchItem item:items){
			SearchItemNode node=new SearchItemNode();
			BeanUtil.copyProperties(node, item);
			nodes.add(node);
		}
		return nodes;
	}
	
	public void delByParentPath(String path){
		sysSearchItemDao.delByParentPath(path);
	}
	
	/**
	 * 构建树列表
	 * @param searchId
	 * @return 
	 * List<SysSearchItem>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<SearchItemNode> getSearchItemTreeNodes(String searchId){
		List<SearchItemNode> parentNodes=getSearchItemNodes(searchId, "0");
		constructChildren(searchId,parentNodes);
		return parentNodes;
	}
	/**
	 * 构建子树
	 * @param searchId
	 * @param items 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	private void constructChildren(String searchId,List<SearchItemNode> items){
		for(SearchItemNode item:items){
			List<SearchItemNode> subChilds=getSearchItemNodes(searchId,item.getItemId());
			if(subChilds.size()==0) {
				item.setLeaf(true);
				item.setExpanded(false);
				continue;
			}
			item.setLeaf(false);
			item.setExpanded(true);
			item.setChildren(subChilds);
			constructChildren(searchId,subChilds);
		}
	}
	
}