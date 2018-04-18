package com.redxun.oa.personal.manager;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.oa.personal.dao.AddrGrpDao;
import com.redxun.oa.personal.entity.AddrGrp;
/**
 * <pre> 
 * 描述：联系人分组业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class AddrGrpManager extends BaseManager<AddrGrp>{
	@Resource
	private AddrGrpDao addrGrpDao;
	
	/**
	 * 根据当前用户分组Id获取该分组下联系人的数量
	 * @param groupId 分组Id
	 * @param userId 用户Id
	 * @return
	 */
	public Long getAddrBookTotalByGroupId(String groupId,String userId){         
		return addrGrpDao.getAddrBookTotalByGroupId(groupId,userId);
	}
	
	/**
	 * 根据联系人Id获取该联系人所在的分组
	 * @param addrId 联系人Id
	 * @return
	 */
	public List<AddrGrp> getAllAddrGrpByAddrBookId(String addrId){    
		return addrGrpDao.getAllAddrGrpByAddrBookId(addrId);
	}
	
	/**
	 * 获取当前用户所有联系人分组
	 * @param userId 用户Id 
	 * @return
	 */
	public List<AddrGrp> getAllByUserId(String userId){
		return addrGrpDao.getAllByUserId(userId);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return addrGrpDao;
	}
}