package com.redxun.sys.core.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;
import com.redxun.core.util.Tree;

/**
 * <pre>
 * 描述：SysTree实体类定义
 * 系统分类树\r\n用于显示树层次结构的分类\r\n可以允许任何层次结构
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@Table(name = "SYS_TREE")
@TableDefine(title="系统分类")
public class SysTree extends BaseTenantEntity implements Tree{
	/**
	 * 平铺
	 */
	public final static String SHOW_TYPE_FLAT="FLAT";
	/**
	 * 树型
	 */
	public final static String SHOW_TYPE_TREE="TREE";
	
	/**
	 * 用于表单中的自定义数据字典
	 */
	public final static String CAT_FORM_DIC="_FORM_DIC";
	/**
	 * 个人文件
	 */
	public final static String CAT_PERSON_FILE="_PERSON_FILE";
	/**
	 * 表单模板类型
	 */
	public final static String CAT_FORMVIEW_TEMPLATE="_FORMVIEW_TEMPLATE";
	/**
	 * 流程方案类型。
	 */
	public final static String CAT_BPM_SOLUTION="CAT_BPM_SOLUTION";
	/**
	 * 表单方案类型
	 */
	public final static String CAT_FORMSOLUTION="CAT_FORMSOLUTION";
	/**
	 * 自定义属性类型
	 */
	public final static String CAT_CUSTOMATTRIBUTE="CAT_CUSTOMATTRIBUTE";
	
	
	@FieldDefine(title="PKID")
	@Id
	@Column(name = "TREE_ID_")
	protected String treeId;
	
	/* 名称 */
	@FieldDefine(title="名称")
	@Column(name = "NAME_")
	@Size(max = 128)
	@NotEmpty
	protected String name;
	/* 路径 */
	@FieldDefine(title="路径")
	@Column(name = "PATH_")
	@Size(max = 1024)
	protected String path;
	/* 层次 */
	@FieldDefine(title="层次")
	@Column(name = "DEPTH_")
	protected Integer depth;
	/* 父节点 */
	@FieldDefine(title="父节点ID")
	@Column(name = "PARENT_ID_")
	@Size(max = 64)
	protected String parentId;
	/* 节点的分类Key */
	@FieldDefine(title="节点分类Key")
	@Column(name = "KEY_")
	@Size(max = 64)
	@NotEmpty
	protected String key;
	
	@FieldDefine(title="同级编码")
	@Column(name = "CODE_")
	@Size(max = 50)
	protected String code;

	@FieldDefine(title="描述")
	@Column(name = "DESCP_")
	@Size(max = 512)
	protected String descp;
	
	/* 用户ID */
	@FieldDefine(title="用户ID")
	@Column(name = "USER_ID_")
	@Size(max = 64)
	protected String userId;
	
	/* 系统树分类key */
	@FieldDefine(title="系统分类Key")
	@Column(name = "CAT_KEY_")
	@Size(max = 64)
	@NotEmpty
	protected String catKey;
	/* 序号 */
	@FieldDefine(title="序号")
	@Column(name = "SN_")
	protected Integer sn;

	
	@Transient
	private List< Tree> children;
	
	

	/**
	 * 数据展示类型
	 */
	@FieldDefine(title="数据展示类型")
	@Column(name="DATA_SHOW_TYPE_")
	protected String dataShowType;
	
	/**
	 * 是否在分类中使用。
	 */
	@Transient
	protected boolean used=true;
	
	/**
	 * 权限json(展示时过滤用,格式自己定)
	 */
	@Transient
	protected String right;
	/**
	 * Default Empty Constructor for class SysTree
	 */
	public SysTree() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class SysTree
	 */
	public SysTree(String in_treeId) {
		this.setTreeId(in_treeId);
	}

	/**
	 * 分类Id * @return String
	 */
	public String getTreeId() {
		return this.treeId;
	}

	/**
	 * 设置 分类Id
	 */
	public void setTreeId(String aValue) {
		this.treeId = aValue;
	}

	/**
	 * 名称 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置 名称
	 */
	public void setName(String aValue) {
		this.name = aValue;
	}

	/**
	 * 路径 * @return String
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * 设置 路径
	 */
	public void setPath(String aValue) {
		this.path = aValue;
	}

	/**
	 * 层次 * @return Integer
	 */
	public Integer getDepth() {
		return this.depth;
	}

	/**
	 * 设置 层次
	 */
	public void setDepth(Integer aValue) {
		this.depth = aValue;
	}

	public String getDataShowType() {
		return dataShowType;
	}

	public void setDataShowType(String dataShowType) {
		this.dataShowType = dataShowType;
	}

	/**
	 * 父节点 * @return String
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置 父节点
	 */
	public void setParentId(String aValue) {
		this.parentId = aValue;
	}

	/**
	 * 节点的分类Key * @return String
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 节点的分类Key
	 */
	public void setKey(String aValue) {
		this.key = aValue;
	}

	/**
	 * 系统树分类key * @return String
	 */
	public String getCatKey() {
		return this.catKey;
	}

	/**
	 * 设置 系统树分类key
	 */
	public void setCatKey(String aValue) {
		this.catKey = aValue;
	}

	/**
	 * 序号 * @return Integer
	 */
	public Integer getSn() {
		return this.sn;
	}

	/**
	 * 设置 序号
	 */
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.name;
	}

	@Override
	public Serializable getPkId() {
		return this.treeId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.treeId = (String) pkId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public boolean getUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysTree)) {
			return false;
		}
		SysTree rhs = (SysTree) object;
		return new EqualsBuilder().append(this.treeId, rhs.treeId)
				.append(this.tenantId, rhs.tenantId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.treeId)
				.append(this.name).append(this.path).append(this.depth)
				.append(this.parentId).append(this.key).append(this.catKey)
				.append(this.sn).append(this.tenantId).append(this.createBy)
				.append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("treeId", this.treeId)
				.append("name", this.name).append("path", this.path)
				.append("depth", this.depth).append("parentId", this.parentId)
				.append("key", this.key).append("catKey", this.catKey)
				.append("sn", this.sn).append("tenantId", this.tenantId)
				.append("createBy", this.createBy)
				.append("createTime", this.createTime)
				.append("updateBy", this.updateBy)
				.append("updateTime", this.updateTime).toString();
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.treeId;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tree> getChildren() {
		// TODO Auto-generated method stub
		return this.children;
	}

	@Override
	public void setChildren(List<Tree> list) {
		this.children = list;
		
	}

}
