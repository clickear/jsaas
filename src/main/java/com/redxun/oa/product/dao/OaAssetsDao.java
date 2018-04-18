package com.redxun.oa.product.dao;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.product.entity.OaAssets;
/**
 * <pre> 
 * 描述：OaAssets数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaAssetsDao extends BaseJpaDao<OaAssets> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaAssets.class;
    }
    
}
