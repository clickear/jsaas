package com.redxun.saweb.export.model;

import com.redxun.ui.view.model.FieldColumn;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 用表头数据及POI构建EXCEL的表头类
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class PoiTableHeader {
    //开始的行索引
    private int startRowIndex;
    //开始的结束索引行
    private int startColIndex;
    //Excel中的workbook
    private Workbook workbook;
    //Excel中当前操作的Sheet
    private Sheet sheet;
    //表头中的最大跨行数据
    private int maxRowSpan=1;
    //表头中的字段表头
    private List<FieldColumn> fieldColumns=new ArrayList<FieldColumn>();
    

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public int getStartColIndex() {
        return startColIndex;
    }

    public void setStartColIndex(int startColIndex) {
        this.startColIndex = startColIndex;
    }

    public List<FieldColumn> getFieldColumns() {
        return fieldColumns;
    }

    public void setFieldColumns(List<FieldColumn> fieldColumns) {
        this.fieldColumns = fieldColumns;
    }

    public PoiTableHeader(int startRowIndex, int startColIndex, int maxRowSpan,Workbook workbook, Sheet sheet) {
        this.startRowIndex = startRowIndex;
        this.startColIndex = startColIndex;
        this.maxRowSpan=maxRowSpan;
        this.workbook = workbook;
        this.sheet = sheet;
    }

    public PoiTableHeader() {
    }

    public int getMaxRowSpan() {
        return maxRowSpan;
    }

    public void setMaxRowSpan(int maxRowSpan) {
        this.maxRowSpan = maxRowSpan;
    }

}
