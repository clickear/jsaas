package com.redxun.oa.mail.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.oa.mail.entity.MailConfig;
/**
 * <pre> 
 * 描述：外部邮箱账号配置数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Repository
public class MailConfigDao extends BaseJpaDao<MailConfig> {

	/**
	 * 获取当前用户下的所有外部邮箱账号配置（不分页，用于outlook-tree的显示外部邮箱账号列表）
	 * @param userId 用户Id
	 * @return
	 */
	public List<MailConfig> getAllByUserId(String userId){
		String jpql="from MailConfig m where m.userId=?";
		return getByJpql(jpql, new Object[]{userId});
	}
	
	/**
	 * 判断外部邮箱账号配置是否已存在
	 * @param mailAccount 外部邮箱地址
	 * @return
	 */
	public boolean isMailConfigExist(String mailAccount,String tenantId){
		String jpql="from MailConfig m where m.mailAccount=? and m.tenantId=?";
		MailConfig mailConfig=null;
		mailConfig=(MailConfig)this.getUnique(jpql, new Object[]{mailAccount,tenantId});
		if(mailConfig==null)
			return false;
		else
			return true;
	}
	
    @SuppressWarnings("rawtypes")
	@Override
    protected Class getEntityClass() {
        return MailConfig.class;
    }
    
}
