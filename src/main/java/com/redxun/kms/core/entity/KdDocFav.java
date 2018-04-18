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
 * 描述：KdDocFav实体类定义
 * 文档收藏
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Entity
@Table(name = "KD_DOC_FAV")
@TableDefine(title = "文档收藏")
public class KdDocFav extends BaseTenantEntity {

	@FieldDefine(title = "PKID")
	@Id
	@Column(name = "FAV_ID_")
	protected String favId;
	@FieldDefine(title = "知识文档")
	@ManyToOne
	@JoinColumn(name = "DOC_ID_")
	protected com.redxun.kms.core.entity.KdDoc kdDoc;
	@FieldDefine(title = "知识问答")
	@ManyToOne
	@JoinColumn(name = "QUE_ID_")
	protected com.redxun.kms.core.entity.KdQuestion kdQuestion;

	/**
	 * Default Empty Constructor for class KdDocFav
	 */
	public KdDocFav() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class KdDocFav
	 */
	public KdDocFav(String in_favId) {
		this.setFavId(in_favId);
	}

	public com.redxun.kms.core.entity.KdDoc getKdDoc() {
		return kdDoc;
	}

	public void setKdDoc(com.redxun.kms.core.entity.KdDoc in_kdDoc) {
		this.kdDoc = in_kdDoc;
	}
	
	public com.redxun.kms.core.entity.KdQuestion getKdQuestion() {
		return kdQuestion;
	}

	public void setKdQuestion(com.redxun.kms.core.entity.KdQuestion kdQuestion) {
		this.kdQuestion = kdQuestion;
	}

	/**
	 * * @return String
	 */
	public String getFavId() {
		return this.favId;
	}

	/**
	 * 设置
	 */
	public void setFavId(String aValue) {
		this.favId = aValue;
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
		return this.favId;
	}

	@Override
	public Serializable getPkId() {
		return this.favId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.favId = (String) pkId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KdDocFav)) {
			return false;
		}
		KdDocFav rhs = (KdDocFav) object;
		return new EqualsBuilder().append(this.favId, rhs.favId).append(this.updateTime, rhs.updateTime).append(this.updateBy, rhs.updateBy).append(this.createTime, rhs.createTime).append(this.createBy, rhs.createBy).append(this.tenantId, rhs.tenantId).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.favId).append(this.updateTime).append(this.updateBy).append(this.createTime).append(this.createBy).append(this.tenantId).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("favId", this.favId).append("updateTime", this.updateTime).append("updateBy", this.updateBy).append("createTime", this.createTime).append("createBy", this.createBy).append("tenantId", this.tenantId).toString();
	}

}
