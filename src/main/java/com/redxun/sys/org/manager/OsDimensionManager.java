package com.redxun.sys.org.manager;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.constants.MStatus;
import com.redxun.core.dao.IDao;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.org.api.context.ProfileUtil;
import com.redxun.org.api.model.IUser;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.sys.org.dao.OsDimensionDao;
import com.redxun.sys.org.dao.OsDimensionQueryDao;
import com.redxun.sys.org.dao.OsRankTypeDao;
import com.redxun.sys.org.entity.OsDimension;
/**
 * <pre> 
 * 描述：OsDimension业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Service
public class OsDimensionManager extends BaseManager<OsDimension>{
	@Resource
	private OsDimensionDao osDimensionDao;
	@Resource
	private OsRankTypeDao osRankTypeDao;
	@Resource
	OsDimensionQueryDao osDimensionQueryDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return osDimensionDao;
	}
	
	@Override
	public void delete(String id) {
		osRankTypeDao.deleteByDimId(id);
		super.delete(id);
	}
	
	public OsDimension getByDimKeyTenantId(String dimKey,String tenantId){
		return osDimensionDao.getByDimKeyTenantId(dimKey, tenantId);
	}
	
	/**
	 * 初始化行政维度
	 * @return
	 */
	public OsDimension initAdminDim(String instId){
		return initDefaultDim(instId,OsDimension.DIM_ADMIN,"行政",OsDimension.SHOW_TYPE_TREE);
	}
	/**
	 * 初始化角色维度
	 * @param instId
	 * @return
	 */
	public OsDimension initRoleDim(String instId){
		return initDefaultDim(instId,OsDimension.DIM_ROLE,"角色",OsDimension.SHOW_TYPE_FLAT);
	}
	
	public OsDimension initJobDim(String instId){
		return initDefaultDim(instId,OsDimension.DIM_POS,"职务",OsDimension.SHOW_TYPE_FLAT);
	}
	
	/**
	 * 初始化默认的维度
	 * @param instId
	 * @param dimKey
	 * @param dimName
	 * @param showType
	 * @return
	 */
	public OsDimension initDefaultDim(String instId,String dimKey,String dimName,String showType){
		OsDimension dim=new OsDimension();
		dim.setDimKey(dimKey);
		dim.setName(dimName);
		dim.setIsCompose(MBoolean.NO.toString());
		dim.setIsGrant(MBoolean.YES.toString());
		dim.setIsSystem(MBoolean.YES.toString());
		dim.setShowType(showType);
		dim.setTenantId(instId);
		dim.setStatus(MStatus.ENABLED.toString());
		dim.setSn(1);
		osDimensionDao.create(dim);
		
		return dim;
	}
	
	public List<OsDimension> filterateAll(String filterSwitch,String tenantId,QueryFilter queryFilter){
		Map<String,Set<String>> profileMap=ProfileUtil.getCurrentProfile();
		Set<String> userSet=profileMap.get("user");
		Set<String> groupSet=profileMap.get("group");
		IUser osUser=ContextUtil.getCurrentUser();
		if(osUser.isSuperAdmin() || osUser.isSaaSAdmin()){
			queryFilter.addParam("isSuperAdmin", "YES");
			//params.put("isSuperAdmin", "YES");//是否超管
		}else{
			queryFilter.addParam("isSuperAdmin", "NO");//是否超管
		}
		queryFilter.addParam("filterSwitch", filterSwitch);//是否按照维度权限过滤
		
		queryFilter.addParam("TENANT_ID_",tenantId);
		
		queryFilter.addParam("userIdSet", userSet);
		queryFilter.addParam("groupIdSet", groupSet);
		return osDimensionQueryDao.filterateAll(queryFilter);
	}
	
}