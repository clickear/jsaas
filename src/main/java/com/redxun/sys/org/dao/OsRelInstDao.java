package com.redxun.sys.org.dao;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.redxun.core.dao.jpa.BaseJpaDao;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
/**
 * <pre>
 * 描述：OsRelInst数据访问类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: chshxuan@163.com
 * 日期:2014-2-1-上午12:52:41
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Repository
public class OsRelInstDao extends BaseJpaDao<OsRelInst> {

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return OsRelInst.class;
	}

	/**
	 * 按关系类型来删除关系实例
	 * 
	 * @param relTypeId
	 */
	public void deleteByRelTypeId(String relTypeId) {
		String ql = "delete from OsRelInst t where t.osRelType.id=?";
		this.delete(ql, new Object[]{relTypeId});
	}

	/**
	 * 按party2方来删除关系记录
	 * 
	 * @param party2
	 */
	public void delByParty2(String party2) {
		String ql = "delete from OsRelInst o where o.party2=?";
		this.delete(ql, new Object[]{party2});
	}
	
	public OsRelInst getByParty1RelTypeId(String party1,String relTypeId){
		String ql = "from OsRelInst o where o.party1=? and o.osRelType.id=?";
		return (OsRelInst) this.getUnique(ql, new Object[]{party1, relTypeId});
	}
	 
	 public OsRelInst getByParty2RelTypeId(String party2,String relTypeId){
		 String ql = "from OsRelInst o where o.party2=? and o.osRelType.id=?"; 
		 return (OsRelInst) this.getUnique(ql, new Object[]{party2, relTypeId});
	 }
	 

	/**
	 * 删除用户组
	 * 
	 * @param groupId
	 */
	public void delByGroupId(String groupId) {
		String ql = "delete from OsRelInst o where o.party1=? and o.relType=?";
		this.delete(ql, new Object[]{groupId, OsRelType.REL_TYPE_GROUP_USER});

		ql = "delete from OsRelInst o where o.party1=? and o.relType=?";
		this.delete(ql, new Object[]{groupId, OsRelType.REL_TYPE_GROUP_GROUP});

		ql = "delete from OsRelInst o where o.party2=? and o.relType=?";
		this.delete(ql, new Object[]{groupId, OsRelType.REL_TYPE_GROUP_GROUP});
	}

	/**
	 * 删除用户组与用户有某种类型关系的关系实例
	 * 
	 * @param groupId
	 * @param userId
	 * @param relTypeId
	 */
	public void delByGroupIdUserIdRelTypeId(String groupId, String userId,
			String relTypeId) {
		String ql = "delete from OsRelInst o where o.party1=? and o.party2=? and o.osRelType.id=?";
		this.delete(ql, new Object[]{groupId, userId, relTypeId});
	}

	/**
	 * 取得某种关系的根节点，只针对用户间的关系及用户组的关系
	 * 
	 * @param typeId
	 * @return
	 */
	public OsRelInst getByRootInstByTypeId(String typeId) {
		String ql = "from OsRelInst o where o.party1=? and o.osRelType.id=?";
		return (OsRelInst) this.getUnique(ql, new Object[]{"0", typeId});
	}

	/**
	 * 通过用户Id来删除关系
	 * 
	 * @param userId
	 */
	public void delByUserId(String userId) {
		String ql = "delete from OsRelInst o where o.party2=? and o.relType=?";
		this.delete(ql, new Object[]{userId, OsRelType.REL_TYPE_GROUP_USER});

		ql = "delete from OsRelInst o where o.party2=? and o.relType=?";
		this.delete(ql, new Object[]{userId, OsRelType.REL_TYPE_GROUP_GROUP});
	}

	/**
	 * 查找某个租户下的实体关系列表
	 * 
	 * @param relTypeId
	 * @param tenantId
	 * @return
	 */
	public List<OsRelInst> getByRelTypeIdTenantId(String relTypeId,
			String tenantId) {
		String ql = "from OsRelInst o where o.osRelType.id=? and o.tenantId=?";
		return this.getByJpql(ql, new Object[]{relTypeId, tenantId});
	}
	
	/**
	 * 是否存在于关系中
	 * @param relTypeId
	 * @param party
	 * @return
	 */
	public boolean isPartyExistInRelation(String relTypeId,String party){
		String ql="select count(*) from OsRelInst o where o.osRelType.id=? and (o.party1=? or o.party2=?)";
		Long cnt=(Long)this.getUnique(ql, new Object[]{relTypeId,party,party});
		if(cnt!=null && cnt>0){
			return true;
		}
		return false;
	}

	/**
	 * 按关系双方及关系类型查找
	 * 
	 * @param party1
	 * @param party2
	 * @param relTypeId
	 * @return
	 */
	public OsRelInst getByParty1Party2RelTypeId(String party1, String party2,
			String relTypeId) {
		String ql = "from OsRelInst os where os.party1=? and os.party2=? and osRelType.id=?";
		List<OsRelInst> list = this.getByJpql(ql, new Object[]{party1, party2,
				relTypeId});
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
		// return (OsRelInst)this.getUnique(ql, new
		// Object[]{party1,party2,relTypeId});
	}

	/**
	 * 获得用户组下的所有从属关系的所有用户
	 * 
	 * @param userId
	 * @param tenantId
	 * @return
	 */
	public List<OsRelInst> getBelongGroupsByUserId(String userId,
			String tenantId) {
		String ql = "select o from OsRelInst o where o.relTypeKey=? and o.party2=? and o.tenantId=? ";
		return this.getByJpql(ql, new Object[]{
				OsRelType.REL_CAT_GROUP_USER_BELONG, userId, tenantId});
	}
	/**
	 * 按关系分类ID及关系方1查找实例
	 * 
	 * @param relTypeId
	 * @param party1
	 * @return
	 */
	public List<OsRelInst> getByRelTypeIdParty1(String relTypeId, String party1) {
		String ql = "select o from OsRelInst o where o.osRelType.id=? and o.party1=?";
		return this.getByJpql(ql, new Object[]{relTypeId, party1});
	}

	/**
	 * 按关系分类ID及关系方2查找实例
	 * 
	 * @param relTypeId
	 * @param party2
	 * @return
	 */
	public List<OsRelInst> getByRelTypeIdParty2(String relTypeId, String party2) {
		String ql = "select o from OsRelInst o where o.osRelType.id=? and o.party2=?";
		return this.getByJpql(ql, new Object[]{relTypeId, party2});
	}

	/**
	 * 获得用户其他关系的实例
	 * 
	 * @param userId
	 * @return
	 */
	public List<OsRelInst> getUserOtherRelInsts(String userId) {
		String ql = "from OsRelInst o where o.party2=? and (o.osRelType.relType=? or o.osRelType.relType=?) and o.relTypeKey!=?";
		return this.getByJpql(ql, new Object[]{userId,
				OsRelType.REL_TYPE_GROUP_USER, OsRelType.REL_TYPE_USER_USER,
				OsRelType.REL_CAT_GROUP_USER_BELONG});
	}

	/**
	 * 按关系方1 关系类型 是否主关系查找实例
	 * 
	 * @param party1
	 * @param RelType
	 * @param isMain
	 * @return
	 */
	public List<OsRelInst> getByParty1RelTypeIsMain(String party1,
			String relType, String isMain) {
		String ql = "from OsRelInst o where o.party1=? and o.relTypeKey=? and o.isMain=?";
		return this.getByJpql(ql, new Object[]{party1, relType, isMain});
	}

	/**
	 * 按关系方1 类型 查找实例
	 * 
	 * @param type
	 * @param part1
	 * @return
	 */
	public List<OsRelInst> getByTypePart1(String type, String part1) {

		String ql = "from OsRelInst o where o.relTypeKey=? and o.party1 in("
				+ part1 + ")";
		return this.getByJpql(ql, new Object[]{type});
	}

	/**
	 * 按关系方1 关系方2 类型 查找实例
	 * 
	 * @param type
	 * @param part1
	 * @param part2
	 * @return
	 */
	public List<OsRelInst> getByTypePart1Part2(String type, String part1,
			String part2) {
		String ql = "from OsRelInst o where o.relType=? and o.party1=? and o.party2=?";
		return this.getByJpql(ql, type, part1, part2);
	}

	/**
	 * 按关系分类ID 维度ID及关系方2查找实例
	 * 
	 * @param relTypeId
	 * @param party2
	 * @param dim1
	 * @return
	 */
	public List<OsRelInst> getByRelTypeIdParty2Dim1(String relTypeId,
			String party2, String dim1) {
		String ql = "from OsRelInst o where o.osRelType.id=? and o.party2=? and o.dim1=?";
		return this.getByJpql(ql, new Object[]{relTypeId, party2, dim1});
	}

}
