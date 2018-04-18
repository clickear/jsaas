package com.redxun.oa.product.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.product.dao.OaProductDefParaDao;
import com.redxun.oa.product.entity.OaProductDefPara;
/**
 * <pre> 
 * 描述：OaProductDefPara业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OaProductDefParaManager extends BaseManager<OaProductDefPara>{
	@Resource
	private OaProductDefParaDao oaProductDefParaDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return oaProductDefParaDao;
	}
	
    /**
     * 按defId获得字段列表
     * @param defId
     * @return
     */
    public List<OaProductDefPara> getByDefId(String defId){
    	return oaProductDefParaDao.getByDefId(defId);
    }
    /**
     * 删除某个中间表下的字段列表
     * @param valueId
     */
    public void delByValueId(String valueId){
    	oaProductDefParaDao.delByValueId(valueId);
    }
    
	/**
     * 得到产品参数定义中间表中产品定义Id为defId、产品类型Id为keyId、产品属性Id为valueId
     * @return 
     */
    public OaProductDefPara getDefByKeyOrValueId(String defId,String keyId,String valueId){
    	return oaProductDefParaDao.getDefByKeyOrValueId(defId, keyId, valueId);
    }
}