



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
 * 描述：INS_MSG_DEF实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-09-01 10:40:15
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "INS_MSG_DEF")
@TableDefine(title = "INS_MSG_DEF")
public class InsMsgDef extends BaseTenantEntity {

	@FieldDefine(title = "MSG_ID_")
	@Id
	@Column(name = "MSG_ID_")
	protected String msgId;

	@FieldDefine(title = "颜色")
	@Column(name = "COLOR_")
	protected String color; 
	@FieldDefine(title = "更多URl")
	@Column(name = "URL_")
	protected String url; 
	@FieldDefine(title = "图标")
	@Column(name = "ICON_")
	protected String icon; 
	@FieldDefine(title = "文字")
	@Column(name = "CONTENT_")
	protected String content; 
	@FieldDefine(title = "数据库名字")
	@Column(name = "DS_NAME_")
	protected String dsName; 
	@FieldDefine(title = "数据库id")
	@Column(name = "DS_ALIAS_")
	protected String dsAlias; 
	@FieldDefine(title = "SQL语句")
	@Column(name = "SQL_FUNC_")
	protected String sqlFunc; 
	@FieldDefine(title = "TYPE_")
	@Column(name = "TYPE_")
	protected String type; 
	
	
	
	
	public InsMsgDef() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public InsMsgDef(String in_id) {
		this.setPkId(in_id);
	}
	
	@Override
	public String getIdentifyLabel() {
		return this.msgId;
	}

	@Override
	public Serializable getPkId() {
		return this.msgId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.msgId = (String) pkId;
	}
	
	public String getMsgId() {
		return this.msgId;
	}

	
	public void setMsgId(String aValue) {
		this.msgId = aValue;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * 返回 颜色
	 * @return
	 */
	public String getColor() {
		return this.color;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 返回 更多URl
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	/**
	 * 返回 图标
	 * @return
	 */
	public String getIcon() {
		return this.icon;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 返回 文字
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	
	/**
	 * 返回 数据库名字
	 * @return
	 */
	public String getDsName() {
		return this.dsName;
	}
	public void setDsAlias(String dsAlias) {
		this.dsAlias = dsAlias;
	}
	
	/**
	 * 返回 数据库id
	 * @return
	 */
	public String getDsAlias() {
		return this.dsAlias;
	}
	public void setSqlFunc(String sqlFunc) {
		this.sqlFunc = sqlFunc;
	}
	
	/**
	 * 返回 SQL语句
	 * @return
	 */
	public String getSqlFunc() {
		return this.sqlFunc;
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
		if (!(object instanceof InsMsgDef)) {
			return false;
		}
		InsMsgDef rhs = (InsMsgDef) object;
		return new EqualsBuilder()
		.append(this.msgId, rhs.msgId) 
		.append(this.color, rhs.color) 
		.append(this.url, rhs.url) 
		.append(this.icon, rhs.icon) 
		.append(this.content, rhs.content) 
		.append(this.dsName, rhs.dsName) 
		.append(this.dsAlias, rhs.dsAlias) 
		.append(this.sqlFunc, rhs.sqlFunc) 
		.append(this.type, rhs.type) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.msgId) 
		.append(this.color) 
		.append(this.url) 
		.append(this.icon) 
		.append(this.content) 
		.append(this.dsName) 
		.append(this.dsAlias) 
		.append(this.sqlFunc) 
		.append(this.type) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("msgId", this.msgId) 
				.append("color", this.color) 
				.append("url", this.url) 
				.append("icon", this.icon) 
				.append("content", this.content) 
														.append("dsName", this.dsName) 
				.append("dsAlias", this.dsAlias) 
				.append("sqlFunc", this.sqlFunc) 
				.append("type", this.type) 
		.toString();
	}

}



