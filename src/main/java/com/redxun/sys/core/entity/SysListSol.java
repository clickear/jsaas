package com.redxun.sys.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 
 * 描述：系统列表方案实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.cn
 * 日期:2017-05-21 12:11:18
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_LIST_SOL")
@TableDefine(title = "系统列表方案")
public class SysListSol extends BaseTenantEntity {

	@FieldDefine(title = "解决方案ID")
	@Id
	@Column(name = "SOL_ID_")
	protected String solId;

	@FieldDefine(title = "标识健")
	@Column(name = "KEY_")
	protected String key;
	@FieldDefine(title = "名称")
	@Column(name = "NAME_")
	protected String name;
	@FieldDefine(title = "描述")
	@Column(name = "DESCP_")
	protected String descp;
	@FieldDefine(title = "权限配置")
	@Column(name = "RIGHT_CONFIGS_")
	protected String rightConfigs;

	public SysListSol() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysListSol(String in_id) {
		this.setPkId(in_id);
	}

	@Override
	public String getIdentifyLabel() {
		return this.solId;
	}

	@Override
	public Serializable getPkId() {
		return this.solId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.solId = (String) pkId;
	}

	public String getSolId() {
		return this.solId;
	}

	public void setSolId(String aValue) {
		this.solId = aValue;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 返回 标识健
	 * 
	 * @return
	 */
	public String getKey() {
		return this.key;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	/**
	 * 返回 描述
	 * 
	 * @return
	 */
	public String getDescp() {
		return this.descp;
	}

	public void setRightConfigs(String rightConfigs) {
		this.rightConfigs = rightConfigs;
	}

	/**
	 * 返回 权限配置
	 * 
	 * @return
	 */
	public String getRightConfigs() {
		return this.rightConfigs;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysListSol)) {
			return false;
		}
		SysListSol rhs = (SysListSol) object;
		return new EqualsBuilder().append(this.solId, rhs.solId)
				.append(this.key, rhs.key).append(this.name, rhs.name)
				.append(this.descp, rhs.descp)
				.append(this.rightConfigs, rhs.rightConfigs).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.solId)
				.append(this.key).append(this.name).append(this.descp)
				.append(this.rightConfigs).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("solId", this.solId)
				.append("key", this.key).append("name", this.name)
				.append("descp", this.descp)
				.append("rightConfigs", this.rightConfigs).toString();
	}

}
