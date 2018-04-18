package com.redxun.oa.info.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.redxun.core.annotion.table.FieldDefine;
import com.redxun.core.annotion.table.TableDefine;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 
 * 描述：INS_MSGBOX_BOX_DEF实体类定义
 * 作者：mansan
 * 邮箱: keitch@redxun.com
 * 日期:2017-09-01 10:58:03
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "INS_MSGBOX_BOX_DEF")
@TableDefine(title = "INS_MSGBOX_BOX_DEF")
public class InsMsgboxBoxDef extends BaseTenantEntity {

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "SN_")
	@Column(name = "SN_")
	protected String sn;
	@FieldDefine(title = "MSG_ID_")
	@Column(name = "MSG_ID_")
	protected String msgId;
	@FieldDefine(title = "BOX_ID_")
	@Column(name = "BOX_ID_")
	protected String boxId;

	@Transient
	protected String content;		


	public InsMsgboxBoxDef() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public InsMsgboxBoxDef(String in_id) {
		this.setPkId(in_id);
	}

	@Override
	public String getIdentifyLabel() {
		return this.id;
	}

	@Override
	public Serializable getPkId() {
		return this.id;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.id = (String) pkId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String aValue) {
		this.id = aValue;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 返回 SN_
	 * 
	 * @return
	 */
	public String getSn() {
		return this.sn;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * 返回 MSG_ID_
	 * 
	 * @return
	 */
	public String getMsgId() {
		return this.msgId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	/**
	 * 返回 BOX_ID_
	 * 
	 * @return
	 */
	public String getBoxId() {
		return this.boxId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InsMsgboxBoxDef)) {
			return false;
		}
		InsMsgboxBoxDef rhs = (InsMsgboxBoxDef) object;
		return new EqualsBuilder().append(this.sn, rhs.sn)
				.append(this.msgId, rhs.msgId).append(this.boxId, rhs.boxId)
				.append(this.id, rhs.id).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.sn)
				.append(this.msgId).append(this.boxId).append(this.id)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("sn", this.sn)
				.append("msgId", this.msgId).append("boxId", this.boxId)
				.append("id", this.id).toString();
	}

}
