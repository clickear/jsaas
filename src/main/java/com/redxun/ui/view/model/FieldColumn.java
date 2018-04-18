/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redxun.ui.view.model;

/**
 *
 * @author 字段列
 */
public class FieldColumn  extends BaseGridColumn implements IGridColumn{
    //字段属性
    private String field;
    //是否允许排序
    private boolean allowSort=false;

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
    
    
}
