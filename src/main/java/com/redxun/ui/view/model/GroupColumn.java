/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redxun.ui.view.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组列
 * @author csx
 */
public class GroupColumn extends BaseGridColumn implements IGridColumn{
    //其子列
    private List<IGridColumn> columns=new ArrayList<IGridColumn>();

    public List<IGridColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<IGridColumn> columns) {
        this.columns = columns;
    }
    
}
