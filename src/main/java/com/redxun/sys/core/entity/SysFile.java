package com.redxun.sys.core.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 * 描述：SysFile实体类定义
 * 系统附件
 * 构建组：miweb
 * 作者：keith
 * 邮箱:chshxuan@163.com
 * 日期:2014-2-1-上午12:48:59
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
@Entity
@XmlRootElement
@Table(name = "SYS_FILE")
@JsonIgnoreProperties(value="sysTree,sysModule")
public class SysFile extends BaseTenantEntity {
	/**
	 * 表示来自应用来身=APPLICATION
	 */
	public static final String FROM_APP="APPLICATION";
	/**
	 * 用户上传=SELF
	 */
	public static final String FROM_SELF="SELF";
	/**
	 * 
	 */
	public static final String FROM_OUT_MAIL="OUT_MAIL";
	/**
	 * 用戶通过UEDITOR上=UEDITOR
	 */
	public static final String FROM_UEDITOR="UEDITOR";
	/**
	 * 匿名用户上传=ANONY
	 */
	public static final String FROM_ANONY="ANONY";
	/**
	 * 图片媒体类型=Image
	 */
	public static final String MINE_TYPE_IMAGE="Image";
	/**
	 * 图标媒体类型=Icon
	 */
	public static final String MINE_TYPE_ICON="Icon";
	
	@Id
	@Column(name = "FILE_ID_")
	protected String fileId;
	/* 文件名 */
	@Column(name = "FILE_NAME_")
	@Size(max = 100)
	@NotEmpty
	protected String fileName;
	
	/* 文件名 */
	@Column(name = "NEW_FNAME_")
	@Size(max = 100)
	@NotEmpty
	protected String newFname;
	
	/* 文件路径 */
	@Column(name = "PATH_")
	@Size(max = 256)
	@NotEmpty
	protected String path;
	/* 扩展名 */
	@Column(name = "EXT_")
	@Size(max = 50)
	protected String ext;
	/* 附件类型 */
	@Column(name = "MINE_TYPE_")
	@Size(max = 50)
	protected String mineType;
	/* 说明 */
	@Column(name = "DESC_")
	@Size(max = 255)
	protected String desc;
	/* 总字节数 */
	@Column(name = "TOTAL_BYTES_")
	protected Long totalBytes;
	/* 删除标识 */
	@Column(name = "DEL_STATUS_")
	@Size(max = 20)
	protected String delStatus;
	@ManyToOne
	@JoinColumn(name = "TYPE_ID_")
	protected com.redxun.sys.core.entity.SysTree sysTree;

	@ManyToOne
	@JoinColumn(name = "MODULE_ID_")
	protected SysModule sysModule;

	@Column(name = "RECORD_ID_")
	protected String recordId;
	
	@Column(name = "FROM_")
	protected String from;
	/**缩略图地址**/
	@Column(name="THUMBNAIL_")
	protected String thumbnail;
	
	@Column(name="COVER_STATUS_")
	@Size(max = 20)
	protected String coverStatus;

	/**
	 * Default Empty Constructor for class SysFile
	 */
	public SysFile() {
		super();
	}

	public SysModule getSysModule() {
		return sysModule;
	}

	public void setSysModule(SysModule sysModule) {
		this.sysModule = sysModule;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * Default Key Fields Constructor for class SysFile
	 */
	public SysFile(String in_fileId) {
		this.setFileId(in_fileId);
	}

	public com.redxun.sys.core.entity.SysTree getSysTree() {
		return sysTree;
	}

	public void setSysTree(com.redxun.sys.core.entity.SysTree in_sysTree) {
		this.sysTree = in_sysTree;
	}

	/**
	 * * @return String
	 */
	public String getFileId() {
		return this.fileId;
	}

	/**
	 * 设置
	 */
	public void setFileId(String aValue) {
		this.fileId = aValue;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * 分类ID * @return String
	 */
	public String getTypeId() {
		return this.getSysTree() == null ? null : this.getSysTree().getTreeId();
	}

	/**
	 * 设置 分类ID
	 */
	public void setTypeId(String aValue) {
		if (aValue == null) {
			sysTree = null;
		} else if (sysTree == null) {
			sysTree = new com.redxun.sys.core.entity.SysTree(aValue);
			// sysTree.setVersion(new Integer(0));//set a version to cheat
			// hibernate only
		} else {
			//
			sysTree.setTreeId(aValue);
		}
	}

	/**
	 * 文件名 * @return String
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * 设置 文件名
	 */
	public void setFileName(String aValue) {
		this.fileName = aValue;
	}

	/**
	 * 文件路径 * @return String
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * 设置 文件路径
	 */
	public void setPath(String aValue) {
		this.path = aValue;
	}

	/**
	 * 扩展名 * @return String
	 */
	public String getExt() {
		return this.ext;
	}

	/**
	 * 设置 扩展名
	 */
	public void setExt(String aValue) {
		this.ext = aValue;
	}

	/**
	 * 附件类型 * @return String
	 */
	public String getMineType() {
		return this.mineType;
	}

	/**
	 * 设置 附件类型
	 */
	public void setMineType(String aValue) {
		this.mineType = aValue;
	}

	public String getNewFname() {
		return newFname;
	}

	public void setNewFname(String newFname) {
		this.newFname = newFname;
	}

	/**
	 * 说明 * @return String
	 */
	public String getDesc() {
		return this.desc;
	}

	/**
	 * 设置 说明
	 */
	public void setDesc(String aValue) {
		this.desc = aValue;
	}

	/**
	 * 总字节数 * @return Long
	 */
	public Long getTotalBytes() {
		return this.totalBytes;
	}

	/**
	 * 设置 总字节数
	 */
	public void setTotalBytes(Long aValue) {
		this.totalBytes = aValue;
	}

	/**
	 * 删除标识 * @return String
	 */
	public String getDelStatus() {
		return this.delStatus;
	}

	/**
	 * 设置 删除标识
	 */
	public void setDelStatus(String aValue) {
		this.delStatus = aValue;
	}

	public String getCoverStatus() {
		return coverStatus;
	}

	public void setCoverStatus(String coverStatus) {
		this.coverStatus = coverStatus;
	}

	@Override
	public String getIdentifyLabel() {
		return this.fileId;
	}

	@Override
	public Serializable getPkId() {
		return this.fileId;
	}

	@Override
	public void setPkId(Serializable pkId) {
		this.fileId = (String) pkId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSizeFormat() {
		DecimalFormat df = new DecimalFormat("0.00");
		if(totalBytes==null){
			return "0 B"; 
		}
		if (totalBytes < 1024 * 1024) {
			double m = new Double(totalBytes) / 1024;
			return df.format(m) + " KB";
		} else if (totalBytes < 1024 * 1024 * 1024) {
			double m = new Double(totalBytes) / (1024 * 1024);
			return df.format(m) + " MB";
		} else if (totalBytes < 1024 * 1024 * 1024 * 1024) {
			double m = new Double(totalBytes) / (1024 * 1024 * 1024);
			return df.format(m) + " GB";
		} else {
			return totalBytes + " B";
		}
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysFile)) {
			return false;
		}
		SysFile rhs = (SysFile) object;
		return new EqualsBuilder().append(this.fileId, rhs.fileId).append(this.fileName, rhs.fileName).append(this.path, rhs.path).append(this.ext, rhs.ext).append(this.mineType, rhs.mineType).append(this.desc, rhs.desc).append(this.totalBytes, rhs.totalBytes).append(this.delStatus, rhs.delStatus).append(this.tenantId, rhs.tenantId).append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime).append(this.updateBy, rhs.updateBy).append(this.updateTime, rhs.updateTime).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.fileId).append(this.fileName).append(this.path).append(this.ext).append(this.mineType).append(this.desc).append(this.totalBytes).append(this.delStatus).append(this.tenantId).append(this.createBy).append(this.createTime).append(this.updateBy).append(this.updateTime).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("fileId", this.fileId).append("fileName", this.fileName).append("path", this.path).append("ext", this.ext).append("mineType", this.mineType).append("desc", this.desc).append("totalBytes", this.totalBytes).append("delStatus", this.delStatus).append("tenantId", this.tenantId).append("createBy", this.createBy)
				.append("createTime", this.createTime).append("updateBy", this.updateBy).append("updateTime", this.updateTime).toString();
	}

}
