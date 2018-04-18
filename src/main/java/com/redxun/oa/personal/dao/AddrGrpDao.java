package com.redxun.oa.personal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.personal.entity.AddrGrp;

/**
 * <pre>
 * 描述：AddrGrp数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class AddrGrpDao extends BaseJpaDao<AddrGrp> {

	/**
	 * 根据当前用户分组Id获取该分组下联系人的数量
	 * @param groupId 分组Id
	 * @param userId 用户Id
	 * @return
	 */
	public Long getAddrBookTotalByGroupId(String groupId,String userId) {      
		String ql = "select count(*) from AddrGrp p right join p.addrBooks a where p.groupId=? and p.createBy=?";
		return (Long) this.getUnique(ql, new Object[] {groupId,userId});
	}
	
	/**
	 * 根据联系人Id获取该联系人所在的分组
	 * @param addrId 联系人Id
	 * @return
	 */
	public List<AddrGrp> getAllAddrGrpByAddrBookId(String addrId){     
		String ql="select p from AddrGrp p join p.addrBooks b where b.addrId=?";
		return this.getByJpql(ql, new Object[]{addrId});
	}
	
	/**
	 * 获取当前用户下的所有分组
	 * @param userId 用户Id
	 * @return
	 */
	public List<AddrGrp> getAllByUserId(String userId){
		String ql="from AddrGrp p where p.createBy=?";
		return this.getByJpql(ql, new Object[]{userId});
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return AddrGrp.class;
	}

}
