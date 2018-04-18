



package com.redxun.sys.org.entity;

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
 *  
 * 描述：维度授权实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2017-05-16 14:12:56
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "OS_DIMENSION_RIGHT")
@TableDefine(title = "维度授权")
public class OsDimensionRight extends BaseTenantEntity {

	@FieldDefine(title = "主键ID")
	@Id
	@Column(name = "RIGHT_ID_")
	protected String rightId;

	@FieldDefine(title = "用户ID")
	@Column(name = "USER_ID_")
	protected String userId; 
	@FieldDefine(title = "组ID")
	@Column(name = "GROUP_ID_")
	protected String groupId; 
	@FieldDefine(title = "_")
	@Column(name = "DIM_ID_")
	protected String dimId; 
	
	
	
	
	public OsDimensionRight() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public OsDimensionRight(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.rightId;
	}

	@Override
	public Serializable getPkId() {
		return this.rightId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.rightId = (String) pkId;
	}
	
	public String getRightId() {
		return this.rightId;
	}

	
	public void setRightId(String aValue) {
		this.rightId = aValue;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 用户ID
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	/**
	 * 返回 组ID
	 * @return
	 */
	public String getGroupId() {
		return this.groupId;
	}
	public void setDimId(String dimId) {
		this.dimId = dimId;
	}
	
	/**
	 * 返回 _
	 * @return
	 */
	public String getDimId() {
		return this.dimId;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OsDimensionRight)) {
			return false;
		}
		OsDimensionRight rhs = (OsDimensionRight) object;
		return new EqualsBuilder()
		.append(this.rightId, rhs.rightId) 
		.append(this.userId, rhs.userId) 
		.append(this.groupId, rhs.groupId) 
		.append(this.dimId, rhs.dimId) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.rightId) 
		.append(this.userId) 
		.append(this.groupId) 
		.append(this.dimId) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("rightId", this.rightId) 
				.append("userId", this.userId) 
				.append("groupId", this.groupId) 
				.append("dimId", this.dimId) 
												.toString();
	}

}



