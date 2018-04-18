package com.redxun.wx.ent.util.model;

import com.redxun.sys.org.entity.OsUser;

public class WxUser {
	
		

		public WxUser() {
		}
		
		public WxUser(OsUser osUser) {
			this.originId=osUser.getUserId();
			this.userid=osUser.getSysAccount().getName();
			this.name=osUser.getFullname();
			this.mobile=osUser.getMobile();
			this.department=osUser.getMainDep().getWxPid();
			this.gender=osUser.getSex().equals("Male")?"1":"0";
			this.email=osUser.getEmail();
			this.enable=osUser.getSysAccount().getStatus().equals("ENABLED")?1:0;
		}

		/**
		 * 原始ID。
		 */
		private String originId="";
		/**
		 * 企业内唯一
		 */
		private String userid="";
		/**
		 * 姓名
		 */
		private String name="";
		/**
		 * 手机号码。企业内必须唯一
		 * 通过手机登录
		 */
		private String mobile="";
		/**
		 * 部门
		 */
		private Integer department=0;
		/**
		 * 性别 1,男 0 女
		 * 
		 */
		private String gender="";
		/**
		 * 邮件企业内唯一
		 */
		private String email="";
		/**
		 * 电话
		 */
		private String telephone="";
		/**
		 * 启用/禁用成员。1表示启用成员，0表示禁用成员
		 */
		private int enable=1;
		
		public String getUserid() {
			return userid;
		}
		public String getName() {
			return name;
		}
		public String getMobile() {
			return mobile;
		}
		public Integer getDepartment() {
			return department;
		}
		public String getGender() {
			return gender;
		}
		public String getEmail() {
			return email;
		}
		public String getTelephone() {
			return telephone;
		}
		public int getEnable() {
			return enable;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public void setDepartment(Integer department) {
			this.department = department;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public void setEnable(int enable) {
			this.enable = enable;
		}
		public String getOriginId() {
			return originId;
		}

		public void setOriginId(String originId) {
			this.originId = originId;
		}

		@Override
		public String toString() {
			return "{\"userid\":\"" + userid + "\",\"name\":\"" + name 
					+ "\", \"mobile\":\"" + mobile + "\", \"department\":[" + department
					+ "], \"gender\":\"" + gender + "\", \"email\":\"" + email + "\", "
							+ "\"telephone\":\"" + telephone + "\", \"enable\":" + enable
					+ "}";
		}
	
		
		public static void main(String[] args) {
			WxUser wx= new WxUser();
			wx.setUserid("ray");
			System.out.println(wx);
		}
	
		
	
	
}
