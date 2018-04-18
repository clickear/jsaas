package com.redxun.sys.ldap.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.core.db.ldap.LdapStatusEnum;
import com.redxun.sys.ldap.entity.SysLdapOu;
/**
 * <pre>
 * 描述：SysLdapOu数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 使用范围：授权给敏捷集团使用
 * </pre>
 */
@Repository
public class SysLdapOuDao extends BaseJpaDao<SysLdapOu> {
	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return SysLdapOu.class;
	}

	/**
	 * 取得该父节点下的所有子结节点
	 * 
	 * @param parentId
	 * @return List<SysLdapOu>
	 * @exception
	 * @since 1.0.0
	 */
	public List<SysLdapOu> getByParentId(String tenantId, String parentId) {
		String ql = "from SysLdapOu t where t.tenantId=? and t.parentId=?";
		return this.getByJpql(ql, new Object[]{tenantId, parentId});
	}

	/**
	 * 通过usnCreated查找唯一对象
	 * 
	 * @Description:
	 * @Title: getUniqueByusnCreated
	 * @param @param usnCreated
	 * @param @return 参数说明
	 * @return SysLdapOu 返回类型
	 * @throws
	 */
	public SysLdapOu getUniqueByUSNCreated(String tenantId, String usnCreated) {
		List<SysLdapOu> beanList = this.getByUSNCreated(tenantId, usnCreated);
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
	 * @return List<SysLdapOu> 返回类型
	 * @throws
	 */
	public List<SysLdapOu> getByUSNCreated(String tenantId, String usnCreated) {
		String jpql = "select t from SysLdapOu t where t.tenantId=? and  t.status!='"
				+ LdapStatusEnum.del.toString() + "' and t.usnCreated=?";
		return this.getByJpql(jpql, new Object[]{tenantId, usnCreated});
	}

	/**
	 * 通过状态查找对象
	 * 
	 * @Description:
	 * @Title: getByStatus
	 * @param @param status
	 * @param @return 参数说明
	 * @return List<SysLdapOu> 返回类型
	 * @throws
	 */
	public List<SysLdapOu> getByStatus(String tenantId, String status) {
		String jpql = "select t from SysLdapOu t where t.tenantId=? and  t.status=?";
		return this.getByJpql(jpql, new Object[]{tenantId, status});
	}

	/**
	 * 查找非删除状态下的对象
	 * 
	 * @Description:
	 * @Title: getByStatusNotDel
	 * @param @return 参数说明
	 * @return List<SysLdapOu> 返回类型
	 * @throws
	 */
	public List<SysLdapOu> getByStatusNotDel(String tenantId) {
		String jpql = "select t from SysLdapOu t where t.tenantId=? and  t.status='"
				+ LdapStatusEnum.add.toString()
				+ "' or t.status='"
				+ LdapStatusEnum.update.toString()
				+ "' or t.status='"
				+ LdapStatusEnum.enable.toString() + "'";
		return this.getByJpql(jpql, new Object[]{tenantId});
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
		String ql = "update SysLdapOu set status=? where usnCreated=? and status!='"
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
		String ql = "update SysLdapOu set status=? where status!='"
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
		String ql = "update SysLdapOu set status='"
				+ LdapStatusEnum.del.toString() + "' where status='"
				+ LdapStatusEnum.disable.toString() + "' and tenantId=?";
		this.update(ql, new Object[]{tenantId});
	}
}
