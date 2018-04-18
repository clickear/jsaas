package com.redxun.oa.personal.entity;

import java.io.Serializable;

import com.redxun.core.entity.BaseEntity;
/**
 * <pre>
 * 描述：联系人节点实体类定义
 * 联系人节点
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
public class ContactInfo  extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /*联系人名字*/
	private String fullname;
	
	/*联系人通讯信息*/
	private String contact;
	
	/**
	 * 获取联系人名字
	 * @return
	 */
	public String getFullname() {
		return fullname;
	}
	/**
	 * 设置联系人名字
	 * @param fullname
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	/**
	 * 获取联系人通讯信息
	 * @return
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * 设置联系人通讯信息
	 * @param contact
	 */
	public void setContact(String contact) {
		this.contact = contact;
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
