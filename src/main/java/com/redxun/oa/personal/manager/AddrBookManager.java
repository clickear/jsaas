package com.redxun.oa.personal.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.oa.personal.dao.AddrBookDao;
import com.redxun.oa.personal.dao.ContactInfoQueryDao;
import com.redxun.oa.personal.entity.AddrBook;
import com.redxun.oa.personal.entity.ContactInfo;
/**
 * <pre> 
 * 描述：联系人业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class AddrBookManager extends BaseManager<AddrBook>{
	@Resource
	private AddrBookDao addrBookDao;
	
	@Resource
	private ContactInfoQueryDao contactInfoQueryDao;
	
	/**
	 * 根据分组Id获取该分组下的所有联系人
	 * @param groupId 分组Id
	 * @return
	 */
	public List<AddrBook> getAddrBooksByGroupId(String groupId){  
		return addrBookDao.getAddrBooksByGroupId(groupId);
	}
	
	/**
	 * 获取当前用户联系人数量
	 * @param userId 用户Id
	 * @return
	 */
	public Long getAddrBookSumByUserId(String userId){  
		return addrBookDao.getAddrBookSumByUserId(userId);
	}
	
	/**
	 * 根据分组Id获取该分组下的所有联系人
	 * @param groupId 分组Id
	 * @return
	 */
	public Long getAddrBookSumByGroupId(String groupId){    
		return addrBookDao.getAddrBookSumByGroupId(groupId);
	}
	
	/**
	 * 获取所有邮箱联系人节点
	 * @param userId 用户Id
	 * @param sqlQueryFilter 查询参数对象
	 * @return
	 */
	public List<ContactInfo> getAllMailContact(String userId,SqlQueryFilter sqlQueryFilter){
		return contactInfoQueryDao.getAllMailContact(userId,sqlQueryFilter);
	}
	
	/**
	 * 根据获取分组Id获取联系人节点
	 * @param sqlQueryFilter 查询参数对象
	 * @return
	 */
	public List<ContactInfo> getByGroupId(SqlQueryFilter sqlQueryFilter){
		return contactInfoQueryDao.getByGroupId(sqlQueryFilter);
	}
	
	public List<AddrBook> listAddrBook(QueryFilter queryFilter){
		return addrBookDao.listAddrBook(queryFilter);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return addrBookDao;
	}
}