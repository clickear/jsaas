package com.redxun.oa.product.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.product.entity.OaProductDefParaValue;
/**
 * <pre> 
 * 描述：OaProductDefParaValue数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaProductDefParaValueDao extends BaseJpaDao<OaProductDefParaValue> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaProductDefParaValue.class;
    }
    
    /**
     * 删除某个KEY表下的字段列表
     * @param keyId
     */
    public void delByKeyId(String keyId){
    	String ql="delete from OaProductDefParaValue oa where oa.oaProductDefParaKey.keyId=?";
    	this.delete(ql, new Object[]{keyId});
    }
    
    /**
     * 按keyId获得字段列表
     * @param keyId
     * @return
     */
    public List<OaProductDefParaValue> getByKeyId(String keyId){
    	String ql="from OaProductDefParaValue oa where oa.oaProductDefParaKey.keyId=?";
    	return this.getByJpql(ql, new Object[]{keyId});
    }
    
}
