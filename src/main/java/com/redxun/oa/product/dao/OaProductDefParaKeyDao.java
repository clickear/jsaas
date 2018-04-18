package com.redxun.oa.product.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.product.entity.OaProductDefParaKey;
/**
 * <pre> 
 * 描述：OaProductDefParaKey数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaProductDefParaKeyDao extends BaseJpaDao<OaProductDefParaKey> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaProductDefParaKey.class;
    }
    /**
     * 按treeId获得字段列表
     * @param treeId
     * @return
     */
    public List<OaProductDefParaKey> getByTreeId(String treeId){
    	String ql="from OaProductDefParaKey oa where oa.sysTree.treeId=?";
    	return this.getByJpql(ql, new Object[]{treeId});
    }
    
}
