



package com.redxun.oa.info.entity;

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
 * 描述：新闻公告权限表实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-11-03 11:47:25
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "INS_NEWS_CTL")
@TableDefine(title = "新闻公告权限表")
public class InsNewsCtl extends BaseTenantEntity {
	
	public static final String CTL_RIGHT_DOWN = "DOWN";
	public static final String CTL_RIGHT_CHECK = "CHECK";
	public static final String CTL_TYPE_ALL = "ALL";
	public static final String CTL_TYPE_LIMIT = "LIMIT";

	@FieldDefine(title = "CTL_ID_")
	@Id
	@Column(name = "CTL_ID_")
	protected String ctlId;

	@FieldDefine(title = "NEWS_ID_")
	@Column(name = "NEWS_ID_")
	protected String newsId; 
	@FieldDefine(title = "USER_ID_")
	@Column(name = "USER_ID_")
	protected String userId; 
	@FieldDefine(title = "GROUP_ID_")
	@Column(name = "GROUP_ID_")
	protected String groupId; 
	@FieldDefine(title = "RIGHT_")
	@Column(name = "RIGHT_")
	protected String right; 
	@FieldDefine(title = "TYPE_")
	@Column(name = "TYPE_")
	protected String type; 
	
	
	
	
	public InsNewsCtl() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public InsNewsCtl(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.ctlId;
	}

	@Override
	public Serializable getPkId() {
		return this.ctlId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.ctlId = (String) pkId;
	}
	
	public String getCtlId() {
		return this.ctlId;
	}

	
	public void setCtlId(String aValue) {
		this.ctlId = aValue;
	}
	
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
	/**
	 * 返回 NEWS_ID_
	 * @return
	 */
	public String getNewsId() {
		return this.newsId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 USER_ID_
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	/**
	 * 返回 GROUP_ID_
	 * @return
	 */
	public String getGroupId() {
		return this.groupId;
	}
	public void setRight(String right) {
		this.right = right;
	}
	
	/**
	 * 返回 RIGHT_
	 * @return
	 */
	public String getRight() {
		return this.right;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回 TYPE_
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsNewsCtl)) {
			return false;
		}
		InsNewsCtl rhs = (InsNewsCtl) object;
		return new EqualsBuilder()
		.append(this.ctlId, rhs.ctlId) 
		.append(this.newsId, rhs.newsId) 
		.append(this.userId, rhs.userId) 
		.append(this.groupId, rhs.groupId) 
		.append(this.right, rhs.right) 
		.append(this.type, rhs.type) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.ctlId) 
		.append(this.newsId) 
		.append(this.userId) 
		.append(this.groupId) 
		.append(this.right) 
		.append(this.type) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("ctlId", this.ctlId) 
				.append("newsId", this.newsId) 
				.append("userId", this.userId) 
				.append("groupId", this.groupId) 
				.append("right", this.right) 
				.append("type", this.type) 
												.toString();
	}

}



