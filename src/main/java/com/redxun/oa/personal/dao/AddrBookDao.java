package com.redxun.oa.personal.dao;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.oa.personal.entity.AddrBook;
/**
 * <pre> 
 * 描述：联系人数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class AddrBookDao extends BaseJpaDao<AddrBook> {

	/**
	 * 根据分组Id获取该分组下的所有联系人
	 * @param groupId 分组Id
	 * @return
	 */
	public List<AddrBook> getAddrBooksByGroupId(String groupId){   
		String jpl="select a from AddrGrp p right join p.addrBooks a where p.groupId=?";
		return getByJpql(jpl, new Object[]{groupId});
	}
	
	/**
	 * 获取当前用户联系人数量
	 * @param userId 用户Id
	 * @return
	 */
	public Long getAddrBookSumByUserId(String userId){    
	   	String ql="select count(*) from AddrBook a where a.createBy=?";
	   	return(Long)this.getUnique(ql, new Object[]{userId});
	}
	
	/**
	 * 根据分组Id获取该分组下的所有联系人
	 * @param groupId 分组Id
	 * @return
	 */
	public Long getAddrBookSumByGroupId(String groupId){     
	   	String ql="select count(*) from AddrGrp p right join p.addrBooks a where p.groupId=?";
	   	return(Long)this.getUnique(ql, new Object[]{groupId});
	}
	
    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return AddrBook.class;
    }

	public List<AddrBook> listAddrBook(QueryFilter queryFilter) {
		StringBuilder builder = new StringBuilder();
		
		String ql="from AddrBook ab where createBy = ?";
		builder.append(ql);
		String nameParam =queryFilter.getParams().get("name").toString();
		String createBy =queryFilter.getParams().get("createBy").toString();
		if(StringUtils.isNotBlank(nameParam)){
			String name = "and name like '%" + nameParam + "%'";
			builder.append(name);
			
		}
		Object[] params = new Object[]{createBy};
		return this.getByJpql(builder.toString(), params);
	}
    
}
