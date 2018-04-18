package com.redxun.ui.view.model;

/**
 *
 * @author csx
 */
public class CheckColumn implements IColumn{
    
    protected String type="checkcolumn";
    
    protected int width=50;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
