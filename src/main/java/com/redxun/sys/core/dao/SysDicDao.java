package com.redxun.sys.core.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.core.entity.SysDic;
/**
 * <pre> 
 * 描述：SysDic数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class SysDicDao extends BaseJpaDao<SysDic> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return SysDic.class;
    }
    /**
     * 按分类Id获得数据字典列表
     * @param treeId
     * @return
     */
    public List<SysDic> getByTreeId(String treeId){
    	String ql="from SysDic sd where sd.sysTree.treeId=? order by sd.sn asc";
    	return this.getByJpql(ql, new Object[]{treeId});
    }
    
    /**
     * 按sysTree父节点和key获取数据字典
     * @param parentId sysTree父节点
     * @param dicKey 数据字典的key值
     * @return
     */
    public List<SysDic> getByParentId(String parentId,String dicKey){
    	String ql="from SysDic sd where sd.sysTree.parentId=? and sd.key=?";
    	return this.getByJpql(ql, new Object[]{parentId,dicKey});
    }
    
    /**
     * 按分类Id获得某一个节点下所有数据字典（不排序）
     * @param treeId 分类Id
     * @return
     */
    public List<SysDic> getBySysTreeId(String treeId){
    	String ql="from SysDic sd where sd.sysTree.treeId=?";
    	return this.getByJpql(ql, new Object[]{treeId});
    }
    
    /**
     * 按分类key和数字字典key获取数据字典
     * @param key 分类key
     * @param dicKey 数据字典key
     * @return
     */
    public SysDic getBySysTreeKeyAndDicKey(String key,String dicKey){
    	String ql="from SysDic sd where sd.sysTree.key=? and sd.key=?";
    	return (SysDic)this.getUnique(ql, new Object[]{key,dicKey});
    }
    
    /**
     * 按分类的key获取数据字典
     * @param key 分类key
     * @return
     */
    public List<SysDic> getByTreeKey(String key){
    	String ql="from SysDic sd where sd.sysTree.key=?";
    	return this.getByJpql(ql, new Object[]{key});
    }
    
}
