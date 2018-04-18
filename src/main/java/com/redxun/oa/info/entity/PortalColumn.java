package com.redxun.oa.info.entity;
/**
 * Portal门户的展现页面的每个栏目的传输类
 * @author Administrator
 *
 */
public class PortalColumn {

	protected String colName;//栏目名字
	protected int colNum;//栏目在第几列
	protected int sn;//序号
	protected int height;//栏目高度
	protected String url;//栏目显示的Url
	protected String moreUrl;//栏目更多按钮的Url
	protected String colId;//栏目的Id
	protected String closeAllow;//是否允许关闭
	//生成的Html
	protected String html;
	//加载类型
	protected String loadType;
	protected String iconCls;
	
	PortalColumn(){
		
	}

	public PortalColumn(String colName, int colNum, int sn, int height,
			String url, String moreUrl, String colId) {
		super();
		this.colName = colName;
		this.colNum = colNum;
		this.sn = sn;
		this.height = height;
		this.url = url;
		this.moreUrl = moreUrl;
		this.colId = colId;
	}
	
	public PortalColumn(String colName, int colNum, int sn, int height,
			String url, String moreUrl, String colId,String closeAllow) {
		super();
		this.colName = colName;
		this.colNum = colNum;
		this.sn = sn;
		this.height = height;
		this.url = url;
		this.moreUrl = moreUrl;
		this.colId = colId;
		this.closeAllow = closeAllow;
	}
	
	
	public String getCloseAllow() {
		return closeAllow;
	}

	public void setCloseAllow(String closeAllow) {
		this.closeAllow = closeAllow;
	}

	public String getMoreUrl() {
		return moreUrl;
	}

	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getColId() {
		return colId;
	}

	public void setColId(String colId) {
		this.colId = colId;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

}
