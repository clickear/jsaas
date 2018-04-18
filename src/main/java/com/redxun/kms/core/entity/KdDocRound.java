package com.redxun.kms.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：KdDocRound实体类定义
 * 文档传阅
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_ROUND")
@TableDefine(title = "文档传阅")
public class KdDocRound extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "ROUND_ID_")
	protected String roundId;
	@FieldDefine(title = "知识文档")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.kms.core.entity.KdDoc kdDoc;

	/**
	 * Default Empty Constructor for class KdDocRound
	 */
	public KdDocRound() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocRound
	 */
	public KdDocRound(String in_roundId) {
		this.setRoundId(in_roundId);
	}

	public com.redxun.kms.core.entity.KdDoc getKdDoc() {
		return kdDoc;
	}

	public void setKdDoc(com.redxun.kms.core.entity.KdDoc in_kdDoc) {
		this.kdDoc = in_kdDoc;
	}

	/**
	 * * @return String
	 */
	public String getRoundId() {
		return this.roundId;
	}

	/**
	 * 设置
	 */
	public void setRoundId(String aValue) {
		this.roundId = aValue;
	}

	/**
	 * 文档ID * @return String
	 */
	public String getDocId() {
		return this.getKdDoc() == null ? null : this.getKdDoc().getDocId();
	}

	/**
	 * 设置 文档ID
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

	@Override
	public String getIdentifyLabel() {
		return this.roundId;
	}

	@Override
	public Serializable getPkId() {
		return this.roundId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.roundId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdDocRound)) {
			return false;
		}
		KdDocRound rhs = (KdDocRound) object;
		return new EqualsBuilder().append(this.roundId, rhs.roundId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.roundId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.tenantId).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("roundId", this.roundId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("tenantId", this.tenantId).append("createBy", this.createBy).toString();
	}

}
