package com.redxun.saweb.tag;
/**
 * 工具栏上的按钮的常量定义
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public enum ToolbarButtonType {
	//新建
	add("新建"),
	//复制新建
	copyAdd("复制新建"),
	//新建查询方案
	newSearch("新建查询方案"),
	//新建查询列表
	searchList("新建查询列表"),
	//新建附件
	newAttach("新建附件"),
	//预览所有附件
	previewAttachs("预览所有附件"),
	//下载附件列表
	downloadAttachs("下载附件列表"),
	//保存当前方案
	saveCurGridView("保存当前方案"),
	//保存为新方案
	saveAsNewGridView("保存为新方案"),
	//打印当前页
	printCurPage("打印当前页"),
	//打印所有页
	printAllPage("打印所有页"),
	//打印单条记录明细
	printOneRecord("打印单条记录明细"),
	//导出当前页
	exportCurPage("导出当前页"),
	//导出所有页
	exportAllPage("导出所有页"),
	//导出单条记录明细
	exportOneRecord("导出单条记录明细"),
	//明细
	detail("明细"),
	//增加菜单
	addMenu("增加菜单"),
	//编辑
	edit("编辑"),
	//删除
	remove("删除"),
	//高级查询方案菜单
	popupSearchMenu("高级查询方案菜单"),
	//附件菜单
	popupAttachMenu("附件菜单"),
	//工具菜单 
	popupSettingMenu("工具菜单"),
	//添加菜单
	popupAddMenu("添加菜单"),
	//字段查询
	fieldSearch("字段查询"),
	//保存
	save("保存"),
	//上一条记录
	prevRecord("上一条记录"),
	//下一条记录
	nextRecord("下一条记录");
	
	private String text;
	
	ToolbarButtonType(String text){
		this.text=text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getKey(){
		return this.name();
	}
	
}
