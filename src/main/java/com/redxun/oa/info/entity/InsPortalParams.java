package com.redxun.oa.info.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * portal 类型参数
 * @author mansan
 *
 */
public class InsPortalParams {
	private String userId;
	private String tenantId;	
	private Integer pageSize=5;
	
	private Map<String,Object> params=new HashMap<String, Object>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	
}
