package com.redxun.sys.bo.entity;

/**
 * 数据保存结果。
 * @author ray
 *
 */
public class BoResult {
	
	public final static class ACTION_TYPE {
		/**
		 * 添加
		 */
		public final static String ADD = "add";
		/**
		 * 更新
		 */
		public final static String UPDATE = "upd";
		
		/**
		 * 删除
		 */
		public final static String DELETE = "del";
	}
	
	public BoResult(){}
	
	
	
	
	public BoResult(String pk, String action, SysBoEnt boEnt) {
		this.pk = pk;
		this.action = action;
		this.boEnt = boEnt;
	}


	/**
	 * 主键
	 */
	private String pk="";
	
	/**
	 * 增加，修改，删除
	 * add,upd,del
	 */
	private String action="";
	
	
	private SysBoEnt boEnt=null;
	
	/**
	 * 是否操作主表。
	 */
	private boolean isMain=true;
	
	
	
	
	public String getAction() {
		return action;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	
	
	public void setAction(String action) {
		this.action = action;
	}
	
	
	public SysBoEnt getBoEnt() {
		return boEnt;
	}
	public void setBoEnt(SysBoEnt boEnt) {
		this.boEnt = boEnt;
	}




	public boolean isMain() {
		return isMain;
	}




	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	
	
}
