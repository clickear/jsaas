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
 * 描述：OFFICE版本实体类定义
 * 作者：ray
 * 邮箱: ray@redxun.com
 * 日期:2018-01-15 15:34:18
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_OFFICE_VER")
@TableDefine(title = "OFFICE版本")
public class SysOfficeVer extends BaseTenantEntity {

	@FieldDefine(title = "主键")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "OFFICE主键")
	@Column(name = "OFFICE_ID_")
	protected String officeId; 
	@FieldDefine(title = "版本")
	@Column(name = "VERSION_")
	protected Integer version; 
	@FieldDefine(title = "附件ID")
	@Column(name = "FILE_ID_")
	protected String fileId; 
	@FieldDefine(title = "文件名")
	@Column(name = "FILE_NAME_")
	protected String fileName; 
	
	
	
	
	public SysOfficeVer() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public SysOfficeVer(String in_id) {
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
	
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	/**
	 * 返回 OFFICE主键
	 * @return
	 */
	public String getOfficeId() {
		return this.officeId;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	/**
	 * 返回 版本
	 * @return
	 */
	public Integer getVersion() {
		return this.version;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	/**
	 * 返回 附件ID
	 * @return
	 */
	public String getFileId() {
		return this.fileId;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * 返回 文件名
	 * @return
	 */
	public String getFileName() {
		return this.fileName;
	}
	
	
	
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysOfficeVer)) {
			return false;
		}
		SysOfficeVer rhs = (SysOfficeVer) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.officeId, rhs.officeId) 
		.append(this.version, rhs.version) 
		.append(this.fileId, rhs.fileId) 
		.append(this.fileName, rhs.fileName) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.officeId) 
		.append(this.version) 
		.append(this.fileId) 
		.append(this.fileName) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("officeId", this.officeId) 
				.append("version", this.version) 
				.append("fileId", this.fileId) 
				.append("fileName", this.fileName) 
												.toString();
	}

}



