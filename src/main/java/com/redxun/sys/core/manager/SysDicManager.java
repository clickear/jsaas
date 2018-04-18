package com.redxun.sys.core.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.cache.CacheUtil;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.sys.core.dao.SysDicDao;
import com.redxun.sys.core.entity.SysDic;
/**
 * <pre> 
 * 描述：SysDic业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class SysDicManager extends BaseManager<SysDic>{
	@Resource
	private SysDicDao sysDicDao;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return sysDicDao;
	}
	
	 /**
     * 按分类Id获得数据字典列表
     * @param treeId
     * @return
     */
    public List<SysDic> getByTreeId(String treeId){
    	List<SysDic> list= (List<SysDic>) CacheUtil.getCache("SYS_DIC_" + treeId);
    	if(BeanUtil.isEmpty(list)){
    		List<SysDic> tmp= sysDicDao.getByTreeId(treeId);
    		CacheUtil.addCache("SYS_DIC_" + treeId, tmp);
    		return tmp;
    	}
    	else{
    		return list;
    	}
    	
    }
    
    /**
     *  按sysTree父节点和key获取数据字典
     * @param parentId sysTree父节点
     * @param dicKey 数据字典的key值
     * @return
     */
    public List<SysDic> getByParentId(String parentId,String dicKey){
    	return sysDicDao.getByParentId(parentId,dicKey);
    }
    
    /**
     * 按分类Id获得某一个节点下所有数据字典（不排序）
     * @param treeId 分类Id
     * @return
     */
    public List<SysDic> getBySysTreeId(String treeId){
    	return sysDicDao.getBySysTreeId(treeId);
    }
    

    /**
     * 按分类key和数字字典key获取数据字典
     * @param key 分类key
     * @param dicKey 数字字典key
     * @return
     */
    public SysDic getBySysTreeKeyAndDicKey(String key,String dicKey){
    	return sysDicDao.getBySysTreeKeyAndDicKey(key, dicKey);
    }
    
    /**
     * 按分类的key获取数据字典
     * @param key 分类Key
     * @return
     */
    public List<SysDic> getByTreeKey(String key){
    	return sysDicDao.getByTreeKey(key);
    }
}