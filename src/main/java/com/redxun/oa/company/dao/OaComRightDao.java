package com.redxun.oa.company.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.company.entity.OaComRight;
import com.redxun.oa.doc.entity.DocRight;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：OaComRight数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaComRightDao extends BaseJpaDao<OaComRight> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaComRight.class;
    }
    
    /**
	 * 根据user（group）组别查询这个通讯录的所有权限
	 * 
	 * @param docId
	 * @param identityType
	 * @param right
	 * @return
	 */
	public List<OaComRight> getAllByBookIdRight(String bookId, String identityType) {
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql = "from OaComRight r where r.oaComBook.comId = ? and r.identityType=? and r.tenantId = ?";
		return this.getByJpql(ql, new Object[] { bookId, identityType, tenantId });
	}
    
}
