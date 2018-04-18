/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redxun.ui.view.model;

import java.util.List;

/**
 *
 * @author 字段列
 */
public class ExportFieldColumn  extends BaseGridColumn implements IGridColumn{
    //字段属性
    private String field;
    //是否允许排序
    private boolean allowSort=false;
    //这个字段横跨多少列
    private int colspan;
    //子表头
    private List<ExportFieldColumn> childColumn ;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isAllowSort() {
        return allowSort;
    }

    public void setAllowSort(boolean allowSort) {
        this.allowSort = allowSort;
    }

	public List<ExportFieldColumn> getChildColumn() {
		return childColumn;
	}

	public void setChildColumn(List<ExportFieldColumn> childColumn) {
		this.childColumn = childColumn;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
    
	
    
    
}
