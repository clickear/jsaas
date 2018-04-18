package com.redxun.ui.view.model;

/**
 * 
 * @author csx
 */
public class BaseGridColumn implements IGridColumn{
    private Integer width;
    private String header;

    private String headerAlign="center";
    private boolean visible=false;

    @Override
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String getHeaderAlign() {
        return headerAlign;
    }

    public void setHeaderAlign(String headerAlign) {
        this.headerAlign = headerAlign;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    

    
}
