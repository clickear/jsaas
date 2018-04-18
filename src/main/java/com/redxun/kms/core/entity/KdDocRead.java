package com.redxun.kms.core.entity;

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
 * 描述：KdDocRead实体类定义
 * 知识文档阅读
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_READ")
@TableDefine(title = "知识文档阅读")
public class KdDocRead extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "READ_ID_")
	protected String readId;
	/* 阅读文档阶段 */
	@FieldDefine(title = "阅读文档阶段")
	@Column(name = "DOC_STATUS_")
	@Size(max = 50)
	protected String docStatus;
	@FieldDefine(title = "知识文档")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.kms.core.entity.KdDoc kdDoc;

	/**
	 * Default Empty Constructor for class KdDocRead
	 */
	public KdDocRead() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocRead
	 */
	public KdDocRead(String in_readId) {
		this.setReadId(in_readId);
	}

	public com.redxun.kms.core.entity.KdDoc getKdDoc() {
		return kdDoc;
	}

	public void setKdDoc(com.redxun.kms.core.entity.KdDoc in_kdDoc) {
		this.kdDoc = in_kdDoc;
	}

	/**
	 * 阅读人ID * @return String
	 */
	public String getReadId() {
		return this.readId;
	}

	/**
	 * 设置 阅读人ID
	 */
	public void setReadId(String aValue) {
		this.readId = aValue;
	}

	/**
	 * * @return String
	 */
	public String getDocId() {
		return this.getKdDoc() == null ? null : this.getKdDoc().getDocId();
	}

	/**
	 * 设置
	 */
	public void setDocId(String aValue) {
		if (aValue == null) {
			kdDoc = null;
		} else if (kdDoc == null) {
			kdDoc = new com.redxun.kms.core.entity.KdDoc(aValue);
		} else {
			kdDoc.setDocId(aValue);
		}
	}

	/**
	 * 阅读文档阶段 * @return String
	 */
	public String getDocStatus() {
		return this.docStatus;
	}

	/**
	 * 设置 阅读文档阶段
	 */
	public void setDocStatus(String aValue) {
		this.docStatus = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.readId;
	}

	@Override
	public Serializable getPkId() {
		return this.readId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.readId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdDocRead)) {
			return false;
		}
		KdDocRead rhs = (KdDocRead) object;
		return new EqualsBuilder().append(this.readId, rhs.readId).append(this.docStatus, rhs.docStatus).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.readId).append(this.docStatus).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.tenantId).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("readId", this.readId).append("docStatus", this.docStatus).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("tenantId", this.tenantId).append("createBy", this.createBy).toString();
	}

}
