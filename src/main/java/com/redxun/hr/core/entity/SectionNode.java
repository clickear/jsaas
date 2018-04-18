package com.redxun.hr.core.entity;

import java.io.Serializable;

import com.redxun.core.entity.BaseTenantEntity;

/**
 * <pre>
 *  
 * 描述：
 * 班制\r\n
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */

public class SectionNode extends BaseTenantEntity {
	
	String sectionId;
	String sectionName;
	
	

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	@Override
	public String getIdentifyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable getPkId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPkId(Serializable pkId) {
		// TODO Auto-generated method stub
		
	}
	
}

