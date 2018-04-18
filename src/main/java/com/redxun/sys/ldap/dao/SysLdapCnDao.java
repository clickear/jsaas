package com.redxun.sys.ldap.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.db.ldap.LdapStatusEnum;
import com.redxun.sys.ldap.entity.SysLdapCn;
/**
 * <pre>
 * 描述：SysLdapCn数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 使用范围：授权给敏捷集团使用
 * </pre>
 */
@Repository
public class SysLdapCnDao extends BaseJpaDao<SysLdapCn> {

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return SysLdapCn.class;
	}

	/**
	 * 通过usnCreated查找唯一对象
	 * 
	 * @Description:
	 * @Title: getUniqueByusnCreated
	 * @param @param usnCreated
	 * @param @return 参数说明
	 * @return SysLdapCn 返回类型
	 * @throws
	 */
	public SysLdapCn getUniqueByUSNCreated(String tenantId, String usnCreated) {
		List<SysLdapCn> beanList = this.getByUSNCreated(tenantId, usnCreated);
		if (beanList.size() > 0) {
			return beanList.get(0);
		}
		return null;
	}
	/**
	 * 通过usnCreated查找对象
	 * 
	 * @Description:
	 * @Title: getByusnCreated
	 * @param @param usnCreated
	 * @param @return 参数说明
	 * @return List<SysLdapCn> 返回类型
	 * @throws
	 */
	public List<SysLdapCn> getByUSNCreated(String tenantId, String usnCreated) {
		String jpql = "select t from SysLdapCn t where t.tenantId=? and t.status!='"
				+ LdapStatusEnum.del.toString() + "' and  t.usnCreated=?";
		return this.getByJpql(jpql, new Object[]{tenantId, usnCreated});
	}

	/**
	 * 查找非删除状态下的对象
	 * 
	 * @Description:
	 * @Title: getByStatusNotDel
	 * @param @return 参数说明
	 * @return List<SysLdapCn> 返回类型
	 * @throws
	 */
	public List<SysLdapCn> getByStatusNotDel(String tenantId) {
		String jpql = "select t from SysLdapCn t where t.tenantId=? and  t.status='"
				+ LdapStatusEnum.add.toString()
				+ "' or t.status='"
				+ LdapStatusEnum.update.toString()
				+ "' or t.status='"
				+ LdapStatusEnum.enable.toString() + "'";
		return this.getByJpql(jpql, new Object[]{tenantId});
	}
	/**
	 * 通过状态查找对象
	 * 
	 * @Description:
	 * @Title: getByStatus
	 * @param @param status
	 * @param @return 参数说明
	 * @return List<SysLdapCn> 返回类型
	 * @throws
	 */
	public List<SysLdapCn> getByStatus(String tenantId, String status) {
		String jpql = "select t from SysLdapCn t where t.tenantId=? and t.status=? ";
		return this.getByJpql(jpql, new Object[]{tenantId, status});
	}
	/**
	 * 更新状态
	 * 
	 * @Description:
	 * @Title: updateStatus
	 * @param @param tenantId
	 * @param @param status 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void updateStatus(String tenantId, String status, String usnCreated) {
		String ql = "update SysLdapCn set status=? where usnCreated=? and status!='"
				+ LdapStatusEnum.del.toString() + "' and tenantId=?";
		this.update(ql, new Object[]{status, usnCreated, tenantId});
	}

	/**
	 * 更新状态
	 * 
	 * @Description:
	 * @Title: updateStatus
	 * @param @param tenantId
	 * @param @param status 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void updateStatus(String tenantId, String status) {
		String ql = "update SysLdapCn set status=? where status!='"
				+ LdapStatusEnum.del.toString() + "' and tenantId=?";
		this.update(ql, new Object[]{status, tenantId});
	}

	/**
	 * 逻辑删除
	 * 
	 * @Description:
	 * @Title: delLogic
	 * @param @param tenantId 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void delLogic(String tenantId) {
		String ql = "update SysLdapCn set status='"
				+ LdapStatusEnum.del.toString() + "' where status='"
				+ LdapStatusEnum.disable.toString() + "' and tenantId=?";
		this.update(ql, new Object[]{tenantId});
	}
}
