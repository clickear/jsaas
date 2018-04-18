package com.redxun.ui.view.model;

/**
 * Checkbox的字段列
 * @author csx
 */
public class ComboBoxColumn extends BaseGridColumn{
    private String trueValue="YES";
    private String falseValue="NO";
    private String type="checkboxcolumn";

    public String getTrueValue() {
        return trueValue;
    }

    public void setTrueValue(String trueValue) {
        this.trueValue = trueValue;
    }

    public String getFalseValue() {
        return falseValue;
    }

    public void setFalseValue(String falseValue) {
        this.falseValue = falseValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
