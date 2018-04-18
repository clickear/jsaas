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
 * 描述：KdDocContr实体类定义
 * 知识文档贡献者
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_CONTR")
@TableDefine(title = "知识文档贡献者")
public class KdDocContr extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "CONT_ID_")
	protected String contId;
	/*
	 * 修改原因分类
	 */
	@FieldDefine(title = "修改原因分类")
	@Column(name = "MOD_TYPE_")
	@Size(max = 50)
	@NotEmpty
	protected String modType;
	/**/
	@FieldDefine(title = "修改原因")
	@Column(name = "REASON_")
	@Size(max = 500)
	protected String reason;
	@FieldDefine(title = "文档Id")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.kms.core.entity.KdDoc kdDoc;

	/**
	 * Default Empty Constructor for class KdDocContr
	 */
	public KdDocContr() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocContr
	 */
	public KdDocContr(String in_contId) {
		this.setContId(in_contId);
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
	public String getContId() {
		return this.contId;
	}

	/**
	 * 设置
	 */
	public void setContId(String aValue) {
		this.contId = aValue;
	}

	/**
	 * 词条 * @return String
	 */
	public String getDocId() {
		return this.getKdDoc() == null ? null : this.getKdDoc().getDocId();
	}

	/**
	 * 设置 词条
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
	 * 更正错误 内容扩充 删除冗余 目录结构 概述 基本信息栏 内链 排版 参考资料 图片 * @return String
	 */
	public String getModType() {
		return this.modType;
	}

	/**
	 * 设置 更正错误 内容扩充 删除冗余 目录结构 概述 基本信息栏 内链 排版 参考资料 图片
	 */
	public void setModType(String aValue) {
		this.modType = aValue;
	}

	/**
	 * * @return String
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * 设置
	 */
	public void setReason(String aValue) {
		this.reason = aValue;
	}

	@Override
	public String getIdentifyLabel() {
		return this.contId;
	}

	@Override
	public Serializable getPkId() {
		return this.contId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.contId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdDocContr)) {
			return false;
		}
		KdDocContr rhs = (KdDocContr) object;
		return new EqualsBuilder().append(this.contId, rhs.contId).append(this.modType, rhs.modType).append(this.reason, rhs.reason).append(this.tenantId, rhs.tenantId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.contId).append(this.modType).append(this.reason).append(this.tenantId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("contId", this.contId).append("modType", this.modType).append("reason", this.reason).append("tenantId", this.tenantId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).toString();
	}

}
