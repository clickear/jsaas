
package com.redxun.ui.view.model;

/**
 * Action操作列
 * @author X230
 */
public class ActionColumn implements IColumn{
    protected String name="action";
    protected String cellCls="actionIcons";
    protected String align="center";
    protected String headerAlign="center";
    protected String renderer="onActionRenderer";
    protected String cellStyle="padding:0";
    protected int width=80;
    protected String header="#";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellCls() {
        return cellCls;
    }

    public void setCellCls(String cellCls) {
        this.cellCls = cellCls;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        this.renderer = renderer;
    }

    public String getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(String cellStyle) {
        this.cellStyle = cellStyle;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeaderAlign() {
        return headerAlign;
    }

    public void setHeaderAlign(String headerAlign) {
        this.headerAlign = headerAlign;
    }
    
      
}
