package com.redxun.oa.product.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.product.entity.OaProductDefPara;
/**
 * <pre> 
 * 描述：OaProductDefPara数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaProductDefParaDao extends BaseJpaDao<OaProductDefPara> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaProductDefPara.class;
    }
    
    /**
     * 按defId获得字段列表
     * @param defId
     * @return
     */
    public List<OaProductDefPara> getByDefId(String defId){
    	String ql="from OaProductDefPara oa where oa.oaProductDef.prodDefId=?";
    	return this.getByJpql(ql, new Object[]{defId});
    }
    
    /**
     * 删除某个中间表下的字段列表
     * @param valueId
     */
    public void delByValueId(String valueId){
    	String ql="delete from OaProductDefPara oa where oa.oaProductDefParaValue.valueId=?";
    	this.delete(ql, new Object[]{valueId});
    }
    
    
	/**
     * 得到产品参数定义中间表中产品定义Id为defId、产品类型Id为keyId、产品属性Id为valueId
     * @param defId 产品定义Id
     * @param keyId 产品类型Id
     * @param valueId 产品属性Id
     * @return 
     */
	public OaProductDefPara getDefByKeyOrValueId(String defId,String keyId,String valueId){
		String ql="from OaProductDefPara oa where oa.oaProductDef.prodDefId= ? and oa.oaProductDefParaKey.keyId= ? and oa.oaProductDefParaValue.valueId= ?";
		return (OaProductDefPara) this.getUnique(ql, new Object[]{defId,keyId,valueId});
	}
}
