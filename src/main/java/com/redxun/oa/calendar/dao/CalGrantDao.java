package com.redxun.oa.calendar.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.calendar.entity.CalGrant;
import com.redxun.saweb.context.ContextUtil;
/**
 * <pre> 
 * 描述：CalGrant数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class CalGrantDao extends BaseJpaDao<CalGrant> {

    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return CalGrant.class;
    }
    /**
     * 获取settingId的分配设定
     * @param settingId
     * @return
     */
   public  List<CalGrant> getBySettingId(String settingId){
	   String tenantId=ContextUtil.getCurrentTenantId();
	   String hql="from CalGrant cg where cg.calSetting.settingId=? and cg.tenantId=?";
	   return this.getByJpql(hql, settingId,tenantId);
   }
   
   public CalGrant getBySettingIdGrantTypeBelongWho(String settingId,String grantType,String guId){
	   String ql="from CalGrant cg where cg.calSetting.settingId=? and cg.grantType=? and cg.belongWho=?";
	   return (CalGrant) this.getUnique(ql,new Object[]{settingId,grantType,guId});
   }
   /**
	 * 查询之前是否存在groupId或者userId
	 * @param type
	 * @param groupOrUserId
	 * @return
	 */
	public boolean checkIsExist(String type,String groupOrUserId,String tenantId){
		String hql="from CalGrant cg where cg.grantType=? and belongWho=? and cg.tenantId=?";
		List<CalGrant> calGrants;
		if("COMMON".equals(type)){
			hql="from CalGrant cg where cg.grantType=? and cg.tenantId=?";
			calGrants=this.getByJpql(hql, type,tenantId);
		}else{
			calGrants=this.getByJpql(hql, type,groupOrUserId,tenantId);
		}
		
		if(calGrants.size()>=1){
			return true;
		}else{return false;}
	}
	/**
	 * 通过groupId或者userId查询分配的日历
	 * @param type
	 * @param groupOrUserId
	 * @return
	 */
	public CalGrant getByGroupIdOrUserId(String type,String groupOrUserId,String tenantId){
		String hql="from CalGrant cg where cg.grantType=? and belongWho=? and cg.tenantId=?";
		if("COMMON".equals(type)){
			hql="from CalGrant cg where cg.grantType=? and cg.tenantId=?";
			List<CalGrant> calGrants=this.getByJpql(hql, type,tenantId);
			if(calGrants.size()>0){
				return calGrants.get(0);
			}else{
				 return null;
			}
		}
		List<CalGrant> calGrants=this.getByJpql(hql, type,groupOrUserId,tenantId);
		if(calGrants.size()>0){
			return calGrants.get(0);
		}else{
			 return null;
		}
	}
    
}
