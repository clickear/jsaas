package com.redxun.sys.core.enums;
/**
 * 
 * <pre> 
 * 描述：子项实体列表中的管理按钮
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年10月19日-上午11:31:54
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public enum SubButtonType {
	/**
	 * 添加子项
	 */
	SUB_ADD("SUB_ADD","添加","TBAR"),
	/**
	 * 删除子项
	 */
	SUB_DEL("SUB_DEL","删除","TBAR"),
	/**
	 * 子项明细
	 */
	SUB_DETAIL("SUB_DETAIL","明细","TBAR"),
	
	/**
	 * 管理列中的删除子项
	 */
	SUB_MGR_EDIT("SUB_MGR_EDIT","删除","COL_MGR"),
	
	/**
	 * 管理列的子项明细
	 */
	SUB_MGR_DETAIL("SUB_MGR_DETAIL","明细","COL_MGR"),
	
	/**
	 * 管理列中的删除子项
	 */
	SUB_MGR_DEL("SUB_MGR_DEL","删除","COL_MGR");
	
	/**
	 * 按钮类型
	 */
	private String btnType;
	/**
	 * 按钮描述
	 */
	private String btnText;
	/**
	 * 按钮的位置
	 */
	private String btnPos;

	private SubButtonType(String btnType) {
		this.btnType = btnType;
	}
	
	private SubButtonType(String btnType,String btnText,String btnPos){
		this.btnType=btnType;
		this.btnText=btnText;
		this.btnPos=btnPos;
	}

	public String getBtnType() {
		return btnType;
	}

	public void setBtnType(String btnType) {
		this.btnType = btnType;
	}

	public String getBtnText() {
		return btnText;
	}

	public void setBtnText(String btnText) {
		this.btnText = btnText;
	}

	public String getBtnPos() {
		return btnPos;
	}

	public void setBtnPos(String btnPos) {
		this.btnPos = btnPos;
	}
	
	
}
