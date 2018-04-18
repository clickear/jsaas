package com.redxun.oa.company.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.oa.company.entity.OaComBook;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：OaComBook数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class OaComBookDao extends BaseJpaDao<OaComBook> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return OaComBook.class;
    }
    
    /**
     * 根据tenantId获得所有通讯录
     * @return
     */
    public List<OaComBook> getAllTheTenantAddress(String tenantId){
    	String hql="from OaComBook ocb where ocb.tenantId=? order by ocb.firstLetter asc";
    	return this.getByJpql(hql, tenantId);
    }
    
    /**
     * 删除所有有关该用户的权限
     * @param docId
     */
    public void delByUserId(String userId){
		String tenantId = ContextUtil.getCurrentTenantId();
    	String ql = "delete from OaComRight r where r.oaComBook.comId = ? and r.tenantId = ?";
    	this.delete(ql, new Object[]{userId,tenantId});
    }
    
    /**
     * 根据手机号码1或者2查找通讯录人员
     * @param mobile
     * @return
     */
    public List<OaComBook> getByMoblie(String mobile){
    	String hql="from OaComBook ocb where ocb.mobile=? or ocb.mobile2=?";
    	return this.getByJpql(hql, mobile,mobile);
    }
    /**
	 * 根据权限、用户人Id、用户类型查询所有的doc
	 * @param userId 用户Id
	 * @param identityType 用户类型 user/group
	 * @param right 权限类型 read/edit/..
	 * @param page 
	 * @return
	 */
	public List<OaComBook> getDocByRight(String userId,String identityType,String treeId, QueryFilter queryFilter){
		List<Object> params = new ArrayList<Object>();
		String tenantId = ContextUtil.getCurrentTenantId();
		String ql="select k from OaComBook k left join k.oaComRights as rights where rights.identityType = ? and rights.identityId = ? and k.tenantId = ?";
		params.add(identityType);
		params.add(userId);
		params.add(tenantId);
		Map<String, QueryParam> queryParams = queryFilter.getQueryParams();
		// 是否有查询状态
		if(StringUtils.isNotBlank(treeId)){
			ql += " and k.sysTree.path like ?";
			params.add("%"+treeId+"%");
		}
		if (queryParams.get("name") != null) {
			ql += " and k.name like ?";
			params.add(queryParams.get("name").getValue());
		}
		
/*		if (queryParams.get("authorType") != null) {
			ql += " and k.authorType = ?";
			params.add(queryParams.get("authorType").getValue());
		}*/
		// 排序
			ql += " order by k.createTime desc";

		List<OaComBook> list = this.getByJpql(ql, params.toArray(), queryFilter.getPage());
		return list;
	}
    
	/**
	 * 通过姓名或者首字母获得通讯录联系人
	 * @param Name
	 * @return
	 */
	public List<OaComBook> getByName(String Name){
		String hql="from OaComBook ocb where ocb.name like ? or ocb.firstLetter like ?";
		return this.getByJpql(hql, "%"+Name,"%"+Name);
	}
    
}
