package com.redxun.sys.log.entity;

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
 * 描述：日志实体实体类定义
 * 作者：陈茂昌
 * 邮箱: maochang163@163.com
 * 日期:2017-09-25 14:27:06
 * 版权：广州红迅软件
 * </pre>
 */
@Entity
@Table(name = "SYS_AUDIT")
@TableDefine(title = "日志实体")
public class LogEntity extends BaseTenantEntity {

	@FieldDefine(title = "ID_")
	@Id
	@Column(name = "ID_")
	protected String id;

	@FieldDefine(title = "所属模块")
	@Column(name = "MODULE_")
	protected String module; 
	@FieldDefine(title = "功能")
	@Column(name = "SUB_MODULE_")
	protected String subModule; 
	@FieldDefine(title = "操作名")
	@Column(name = "ACTION_")
	protected String action; 
	@FieldDefine(title = "操作IP")
	@Column(name = "IP_")
	protected String ip; 
	@FieldDefine(title = "设备信息")
	@Column(name = "USER_AGENT_")
	protected String userAgent; 
	@FieldDefine(title = "主部门")
	@Column(name = "MAIN_GROUP_NAME_")
	protected String mainGroupName; 
	@FieldDefine(title = "主部门ID")
	@Column(name = "MAIN_GROUP_")
	protected String mainGroup; 
	@FieldDefine(title = "持续时间")
	@Column(name = "DURATION_")
	protected Long duration; 
	@FieldDefine(title = "操作目标")
	@Column(name = "TARGET_")
	protected String target; 
	
	
	
	
	
	public LogEntity() {
	}

	/**
	 * Default Key Fields Constructor for class Orders
	 */
	public LogEntity(String in_id) {
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
	
	public void setModule(String module) {
		this.module = module;
	}
	
	/**
	 * 返回 所属模块
	 * @return
	 */
	public String getModule() {
		return this.module;
	}
	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}
	
	/**
	 * 返回 功能
	 * @return
	 */
	public String getSubModule() {
		return this.subModule;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	/**
	 * 返回 操作名
	 * @return
	 */
	public String getAction() {
		return this.action;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * 返回 操作IP
	 * @return
	 */
	public String getIp() {
		return this.ip;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	/**
	 * 返回 操作目标
	 * @return
	 */
	public String getTarget() {
		return this.target;
	}
	
	
	
		

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getMainGroupName() {
		return mainGroupName;
	}

	public void setMainGroupName(String mainGroupName) {
		this.mainGroupName = mainGroupName;
	}

	public String getMainGroup() {
		return mainGroup;
	}

	public void setMainGroup(String mainGroup) {
		this.mainGroup = mainGroup;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof LogEntity)) {
			return false;
		}
		LogEntity rhs = (LogEntity) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id) 
		.append(this.module, rhs.module) 
		.append(this.subModule, rhs.subModule) 
		.append(this.action, rhs.action) 
		.append(this.ip, rhs.ip) 
		.append(this.target, rhs.target) 
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id) 
		.append(this.module) 
		.append(this.subModule) 
		.append(this.action) 
		.append(this.ip) 
		.append(this.target) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
				.append("module", this.module) 
				.append("subModule", this.subModule) 
				.append("action", this.action) 
				.append("ip", this.ip) 
				.append("target", this.target) 
												.toString();
	}

}



