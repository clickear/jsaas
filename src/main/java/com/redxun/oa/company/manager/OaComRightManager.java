package com.redxun.oa.company.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.company.dao.OaComRightDao;
import com.redxun.oa.company.entity.OaComRight;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：OaComRight业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OaComRightManager extends BaseManager<OaComRight>{
	@Resource
	private OaComRightDao oaComRightDao;
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return oaComRightDao;
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
		return oaComRightDao.getAllByBookIdRight(bookId, identityType);
	}
}