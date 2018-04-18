package com.redxun.ui.view.model;

/**
 * 前台表格控件展示
 * @author csx
 */
public interface IGridColumn extends IColumn{
   
    //列宽
    public Integer getWidth();
    //列头对齐
    public String getHeaderAlign();

    //列头标签
    public String getHeader();
    //是否隐藏
    public boolean isVisible();
}
