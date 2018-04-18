package com.redxun.sys.core.enums;

/**
 * 
 * <pre>
 * 描述：系统中的按钮类型
 * 构建组：ent-base-web
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2014年9月13日-下午11:45:26
 * 广州红迅软件有限公司（http://www.redxun.cn）
 * </pre>
 */
public enum ButtonType {
	/**
	 * 明细=DETAIL
	 */
	DETAIL("DETAIL","明细","TOP"),

	/**
	 * 新建=NEW
	 */
	NEW("NEW","新建","TOP"),
	/**
	 * 修改=EDIT
	 */
	EDIT("EDIT","修改","TOP"),
	/**
	 * 删除=DEL
	 */
	DEL("DEL","删除","TOP"),
	
	/**
	 * 高级查询=SEARCH_COMPOSE
	 */
	SEARCH("SEARCH_COMPOSE","高级查询","TOP"),
	/**
	 * 新增附件=NEW_ATTACH
	 */
	ATTACH("NEW_ATTACH","新增附件","TOP"),

	/**
	 * 打印=PRINT
	 */
	PRINT("PRINT","打印","TOP"),
	/**
	 * 导出=EXPORT
	 */
	EXPORT("EXPORT","导出","TOP"),
	/**
	 * 按字段查询=SEARCH_FIELD
	 */
	SEARCH_FIELD("SEARCH_FIELD","按字段查询","TOP2"),
	/**
	 * 管理列中的查看明细=M_DETAIL
	 */
	M_DETAIL("M_DETAIL","明细","MANAGE"),
	/**
	 * 管理列中的编辑=M_EDIT
	 */
	M_EDIT("M_EDIT","编辑","MANAGE"),
	/**
	 * 管理列中的删除=M_DEL
	 */
	M_DEL("M_DEL","删除","MANAGE"),
	
	/**
	 * 保存=SAVE
	 */
	SAVE("SAVE","保存","FORM_BOTTOM"),
	/**
	 * 上一条记录=PREV
	 */
	PREV("PREV","上一条记录","FORM_BOTTOM"),
	/**
	 * 下一条记录=NEXT
	 */
	NEXT("NEXT","下一条记录","FORM_BOTTOM"),

	/**
	 * 自定义=CUSTOM
	 */
	CUSTOM("CUSTOM","自定义","CUSTOM");

	
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

	private ButtonType(String btnType) {
		this.btnType = btnType;
	}
	
	private ButtonType(String btnType,String btnText,String btnPos){
		this.btnType=btnType;
		this.btnText=btnText;
		this.btnPos=btnPos;
	}

	public String getBtnType() {
		return btnType;
	}

	public String getBtnText() {
		return btnText;
	}
	
	public String getBtnPos() {
		return btnPos;
	}

	public  static void main(String[]args){
		ButtonType[] types=ButtonType.values();
		for(ButtonType t:types){
			System.out.println("t:"+ t.getBtnType() +" " + t.getBtnText());
		}
	}
	
}
