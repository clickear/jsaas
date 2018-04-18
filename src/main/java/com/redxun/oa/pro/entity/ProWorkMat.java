package com.redxun.oa.pro.entity;

import com.redxun.core.entity.BaseTenantEntity;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * <pre>
 * 描述：ProWorkMat实体类定义
 * 工作动态
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2015-12-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "OA_WORK_MAT")
@TableDefine(title = "工作动态")
public class ProWorkMat extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ACTION_ID_")
	protected String actionId;
	/* 评论内容 */
	@FieldDefine(title = "评论内容")
	@Column(name = "CONTENT_")
	@Size(max = 512)
	@NotEmpty
	protected String content;
	/* 类型 */
	@FieldDefine(title = "类型")
	@Column(name = "TYPE_")
	@Size(max = 50)
	@NotEmpty
	protected String type;
	/* 类型主键ID */
	@FieldDefine(title = "类型主键ID")
	@Column(name = "TYPE_PK_")
	@Size(max = 64)
	@NotEmpty
	protected String typePk;

	/**
	 * Default Empty Constructor for class ProWorkMat
	 */
	public ProWorkMat() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class ProWorkMat
	 */
	public ProWorkMat(String in_actionId) {
		this.setActionId(in_actionId);
	}

	/**
	 * 主键 * @return String
	 */
	public String getActionId() {
		return this.actionId;
	}

	/**
	 * 设置 主键
	 */
	public void setActionId(String aValue) {
		this.actionId = aValue;
	}

	/**
	 * 评论内容 * @return String
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置 评论内容
	 */
	public void setContent(String aValue) {
		this.content = aValue;
	}

	/**
	 * 类型 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置 类型
	 */
	public void setType(String aValue) {
		this.type = aValue;
	}

	/**
	 * 类型主键ID * @return String
	 */
	public String getTypePk() {
		return this.typePk;
	}

	/**
	 * 设置 类型主键ID
	 */
	public void setTypePk(String aValue) {
		this.typePk = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.actionId;
	}

	@Override
	public Serializable getPkId() {
		return this.actionId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.actionId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProWorkMat)) {
			return false;
		}
		ProWorkMat rhs = (ProWorkMat) object;
		return new EqualsBuilder().append(this.actionId, rhs.actionId).append(this.content, rhs.content).append(this.type, rhs.type).append(this.typePk, rhs.typePk).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.actionId).append(this.content).append(this.type).append(this.typePk).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy)
				.append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("actionId", this.actionId).append("content", this.content).append("type", this.type).append("typePk", this.typePk).append("tenantId", this.tenantId).append("createBy", this.createBy)
				.append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
