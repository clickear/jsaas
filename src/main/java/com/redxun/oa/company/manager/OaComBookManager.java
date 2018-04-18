package com.redxun.oa.company.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.kms.core.entity.KdDoc;
import com.redxun.kms.core.entity.KdDocRight;
import com.redxun.oa.company.dao.OaComBookDao;
import com.redxun.oa.company.dao.OaComRightDao;
import com.redxun.oa.company.entity.OaComBook;
import com.redxun.oa.company.entity.OaComRight;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.manager.OsGroupManager;
/**
 * <pre> 
 * 描述：OaComBook业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class OaComBookManager extends BaseManager<OaComBook>{
	@Resource
	private OaComBookDao oaComBookDao;
	@Resource
	private OsGroupManager osGroupManager;
	@Resource
	private OaComRightDao oaComRightDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return oaComBookDao;
	}
	 /**
     * 根据tenantId获得所有通讯录
     * @return
     */
    public List<OaComBook> getAllTheTenantAddress(String tenantId){
    	return oaComBookDao.getAllTheTenantAddress(tenantId);
    }
    
    /**
     * 删除所有有关该用户的权限
     * @param userId
     */
    public void delByUserId(String userId){
		oaComBookDao.delByUserId(userId);
    }
    /**
     * 根据手机号码1或者2查找通讯录人员
     * @param mobile
     * @return
     */
    public List<OaComBook> getByMoblie(String mobile){
    	return oaComBookDao.getByMoblie(mobile);
    }
    
    /**
	 * 搜索页面:搜索所有的某一权限的的Doc
	 * 
	 * @param userId
	 * @param right
	 * @param page
	 * @return
	 */
	public List<OaComBook> getAllByRgiht(String userId, String treeId, QueryFilter queryFilter) {
		// 获得user的Doc
		List<OaComBook> rightDoc = oaComBookDao.getDocByRight(userId, KdDocRight.IDENTITYTYPE_USER, treeId, queryFilter);
		// 获得user所属的group的Doc
		List<OsGroup> groups = osGroupManager.getBelongGroups(userId);
		if (groups.size() > 0) {
			for (OsGroup group : groups) {// 遍历每个组
				List<OaComBook> list = oaComBookDao.getDocByRight(group.getGroupId(), KdDocRight.IDENTITYTYPE_GROUP, treeId, queryFilter);// 获得每个组的阅读权限
				for (int i = 0; i < list.size(); i++) {
					if (!rightDoc.contains(list.get(i))) {
						rightDoc.add(list.get(i));
					}
				}
			}
		}

		List<OaComBook> allDoc = oaComBookDao.getDocByRight(OaComRight.IDENTITYID_ALL, OaComRight.IDENTITYTYPE_ALL, treeId, queryFilter);
		rightDoc.addAll(allDoc);
		return rightDoc;

	}
	
	/**
	 * 通过姓名或者首字母获得通讯录联系人
	 * @param Name
	 * @return
	 */
	public List<OaComBook> getByName(String Name){
		return oaComBookDao.getByName(Name);
	}
	
	
}