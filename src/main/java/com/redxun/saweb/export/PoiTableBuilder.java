package com.redxun.saweb.export;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import com.redxun.core.json.JSONUtil;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.StringUtil;
import com.redxun.saweb.export.model.PoiTableHeader;
import com.redxun.ui.view.model.ExportFieldColumn;
import com.redxun.ui.view.model.FieldColumn;
import com.redxun.ui.view.model.GroupColumn;
import com.redxun.ui.view.model.IGridColumn;

/**
 * POI表格构建
 * 使用示例
 * <pre>
 * 使用示列
    public void genExcel(List<IGridColumn> gridColumns) throws Exception{
        FileOutputStream fos=new FileOutputStream("D:\\demo1-2.xls");	
        Workbook wb=new HSSFWorkbook();
	Sheet sheet=wb.createSheet();
        
        //List<FieldColumn> fieldColumns=new ArrayList<FieldColumn>();
        Integer rowIndex=0;
        Integer colIndex=0;
        int maxRows=calHeaderMaxRows(gridColumns);
        PoiTableHeader counter=new PoiTableHeader(rowIndex,colIndex,maxRows,wb,sheet);
        genTableCell(gridColumns,counter);
        wb.write(fos);
        fos.close();
    }
 * </pre>
 * @author csx
 *  @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class PoiTableBuilder {
    
    /**
     * 计算表头的最大跨行数
     * @param columns 
     * @return 
     */
    public int calHeaderMaxRows(List<IGridColumn> columns){
        int maxChildRows=1;

        for(IGridColumn col:columns){
            int tmpRows=calColumnRows(col);
            if(maxChildRows<tmpRows){
                maxChildRows=tmpRows;
            }
        }
        return maxChildRows;
    }
    /**
     * 计算当前列所涉及到的行数
     * @param col
     * @return 
     */
    private int calColumnRows(IGridColumn col){
        int rows=1;
        int subRows=0;
        if(col instanceof GroupColumn){
             subRows=1;
             GroupColumn gc=(GroupColumn)col;
             for(int i=0;i<gc.getColumns().size();i++){
                 //取得子列下的最大层数
                 int maxSubRows=calColumnRows(gc.getColumns().get(i));
                 if(maxSubRows>subRows){
                     subRows=maxSubRows;
                 }
             }
        }
        return rows+subRows;
    }

   
    private int calColumnChilds(IGridColumn col){
        if(col instanceof FieldColumn) return 1;
        if(col instanceof GroupColumn){
            GroupColumn gc=(GroupColumn)col;
            int cn=0;
            for(IGridColumn gcn:gc.getColumns()){
                cn+=calColumnChilds(gcn);
            }
            return cn;
        }
        return 0;
    }
    
    public void genTableHeader(List<IGridColumn> gridColumns,PoiTableHeader header){
        //计算同行中最大的层数
        int maxLevl=calHeaderMaxRows(gridColumns);
        //保存当前索引行
        int rowIndex=header.getStartRowIndex();
        
        HSSFCellStyle cellStyle=(HSSFCellStyle)header.getWorkbook().createCellStyle();
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        
        HSSFFont headerFont = (HSSFFont)header.getWorkbook().createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
        headerFont.setFontName("宋体");
        headerFont.setFontHeightInPoints((short) 12);
        cellStyle.setFont(headerFont);
        
        for(IGridColumn gc:gridColumns){
            Row row=header.getSheet().getRow(header.getStartRowIndex());
            if(row==null){
                row=header.getSheet().createRow(header.getStartRowIndex());
            }
            if(gc instanceof GroupColumn){
                //取得该组下的所有列
                int colNums=calColumnChilds(gc);
                System.out.println("colNums:"+ colNums);
                //取得合并的最后序号
                int endColumnIndex=header.getStartColIndex()+colNums-1;
                //System.out.println("endColIndex:"+ endColumnIndex + " startColIndex:"+ header.getStartColIndex());
                GroupColumn gcTmp=(GroupColumn)gc;
                
                //创建合并区域
                CellRangeAddress cra=new CellRangeAddress(header.getStartRowIndex(),header.getStartRowIndex(),header.getStartColIndex(), endColumnIndex);
                header.getSheet().addMergedRegion(cra);
               
                //创建表格
                Cell cell=row.createCell(header.getStartColIndex());
                cell.setCellValue(gc.getHeader());
                cell.setCellStyle(cellStyle);
                //tdDraw.getSheet().setColumnWidth(tdDraw.getStartColIndex(), gc.getWidth());
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, cra, header.getSheet(), header.getWorkbook());
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, cra, header.getSheet(), header.getWorkbook());
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, cra, header.getSheet(), header.getWorkbook());
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, cra, header.getSheet(), header.getWorkbook());
                
                //递增该列序号
                header.setStartRowIndex(header.getStartRowIndex()+1);
                
                genTableHeader(gcTmp.getColumns(),header);
                //恢复原行号
                header.setStartRowIndex(rowIndex);
                
            }else if(gc instanceof FieldColumn){//为字段表头
                /**
                 * 当字段所在的行达不到最大行值时，合并其单元格至最大行
                 */
                int endRow=header.getStartRowIndex()+maxLevl-1;
                if(endRow<header.getMaxRowSpan()-1){
                    endRow=header.getMaxRowSpan()-1;
                }
                CellRangeAddress cra=new CellRangeAddress(header.getStartRowIndex(),endRow,header.getStartColIndex(), header.getStartColIndex());
                header.getSheet().addMergedRegion(cra);
                
                Cell cell=row.createCell(header.getStartColIndex());
                
                RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, cra, header.getSheet(), header.getWorkbook());
                RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, cra, header.getSheet(), header.getWorkbook());
                RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, cra, header.getSheet(), header.getWorkbook());
                RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, cra, header.getSheet(), header.getWorkbook());
                cell.setCellStyle(cellStyle);
               // System.out.println("start index:"+ header.getStartColIndex()+" width:" + gc.getWidth());
                header.getSheet().setColumnWidth(header.getStartColIndex(), gc.getWidth()*256/7);
                cell.setCellValue(gc.getHeader());
                
                //加到当前字段头至PoiHeader
                header.getFieldColumns().add((FieldColumn)gc);
                //对列的索引行加1
                header.setStartColIndex(header.getStartColIndex()+1);

            }
        }
        
    }
    
    /**
     * 输出表格数据
     * @param header
     * @param Data 
     */
    public void writeTableData(PoiTableHeader header,List<?> dataList){
        
        HSSFCellStyle cellStyle=(HSSFCellStyle)header.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        
        int startRow=header.getMaxRowSpan();
        for(int i=0;i<dataList.size();i++){
            Object obj=dataList.get(i);
            Row row=header.getSheet().createRow(startRow+i);
            for(int j=0;j<header.getFieldColumns().size();j++){
                FieldColumn fieldColumn=header.getFieldColumns().get(j);
                Cell cell=row.createCell(j);
                String field=fieldColumn.getField();
                Object val=BeanUtil.getFieldValueFromObject(obj, field);
                if(val!=null){
                    cell.setCellValue(val.toString());
                }
                cell.setCellStyle(cellStyle);
            }
        }
        
    }
    /**
     * 生成表格，包括表头及数据
     * @param columns 列
     * @param dataList 数据
     * @return 
     */
    public Workbook writeTable(List<IGridColumn> columns,List<?> dataList){
        Integer rowIndex=0;
        Integer colIndex=0;
        int maxRows=calHeaderMaxRows(columns);
        Workbook wb=new HSSFWorkbook();
	Sheet sheet=wb.createSheet();
        PoiTableHeader header=new PoiTableHeader(rowIndex,colIndex,maxRows,wb,sheet);
        genTableHeader(columns,header);
        //必须调用genTableHeader才能调用writeTableData;
        writeTableData(header, dataList);
        return header.getWorkbook();
    }
    
    
    /**
     * 通过Json构建列
     * @param jsonColumns
     * @return 
     */
    public List<IGridColumn> constructColumns(String jsonColumns){
        JSONArray colArr=JSONArray.fromObject(jsonColumns);
        List<IGridColumn> columns=new ArrayList<IGridColumn>();
        for(int i=0;i<colArr.size();i++){
          JSONObject obj=colArr.getJSONObject(i);
          String header=JSONUtil.getString(obj, "header");
          String fieldName=JSONUtil.getString(obj, "field");
          String allowSort=JSONUtil.getString(obj, "allowSort");
          String sWidth=JSONUtil.getString(obj, "width");
          String visible=JSONUtil.getString(obj, "visible");
          int index=sWidth.lastIndexOf("px");
          int width=100;
          if(index!=-1){
             sWidth=sWidth.substring(0, index);
             width=new Integer(sWidth);
          }
          
          String cols=JSONUtil.getString(obj,"columns");
          if(StringUtils.isNotEmpty(cols)){
             //updateColumnsConfig(cols,gridViewId);
              GroupColumn gc=new GroupColumn();
              gc.setHeader(header);
              gc.setHeaderAlign("center");
              gc.setVisible("true".equals(visible));
              gc.setWidth(width);
              List<IGridColumn> subColumns=constructColumns(cols);
              gc.setColumns(subColumns);
              columns.add(gc);
          }else{
              FieldColumn fc=new FieldColumn();
              fc.setAllowSort("true".equals(allowSort));
              fc.setField(fieldName);
              fc.setHeader(header);
              fc.setHeaderAlign("center");
              fc.setVisible("true".equals(visible));
              fc.setWidth(width);
              columns.add(fc);
          }
        }
        return columns;
    }    
    
    /**
     * 通过Json构建多表头Field列
     * @param jsonColumns
     * @return 
     */
    public List<ExportFieldColumn> constructMutiExportFieldColumns(String jsonColumns){
        JSONArray colArr=JSONArray.fromObject(jsonColumns);
        List<ExportFieldColumn> columns=new ArrayList<ExportFieldColumn>();
        for(int i=0;i<colArr.size();i++){
          List<ExportFieldColumn> childColumn = new ArrayList<ExportFieldColumn>();
          JSONObject obj=colArr.getJSONObject(i);
          String header=JSONUtil.getString(obj, "header");
          String fieldName=JSONUtil.getString(obj, "field");
          String allowSort=JSONUtil.getString(obj, "allowSort");
          String sWidth=JSONUtil.getString(obj, "width");
          String visible=JSONUtil.getString(obj, "visible");
          String childColumns=JSONUtil.getString(obj, "children");
          int index=sWidth.lastIndexOf("px");
          int width=100;
          int colspan = 1;
          if(index!=-1){
             sWidth=sWidth.substring(0, index);
             width=new Integer(sWidth);
          }
          if(StringUtil.isNotEmpty(childColumns)&&!("[]".equals(childColumns))){
        	  childColumn = constructMutiExportFieldColumns(childColumns);
        	  colspan = getRowSpan(childColumn,0);
          }
          ExportFieldColumn fc=new ExportFieldColumn();
          fc.setAllowSort("true".equals(allowSort));
          fc.setField(fieldName);
          fc.setHeader(header);
          fc.setHeaderAlign("center");
          fc.setVisible("true".equals(visible));
          fc.setWidth(width);
          fc.setChildColumn(childColumn);
          fc.setColspan(colspan);
          columns.add(fc);
        }
        return columns;
    }
    
    /**
     * 通过Json构建单表头Field列
     * @param jsonColumns
     * @return 
     */
    public List<ExportFieldColumn> constructSingleExportFieldColumns(String jsonColumns,List<ExportFieldColumn> columns){
        JSONArray colArr=JSONArray.fromObject(jsonColumns);
        for(int i=0;i<colArr.size();i++){
          List<ExportFieldColumn> childColumn = new ArrayList<ExportFieldColumn>();
          JSONObject obj=colArr.getJSONObject(i);
          String header=JSONUtil.getString(obj, "header");
          String fieldName=JSONUtil.getString(obj, "field");
          String allowSort=JSONUtil.getString(obj, "allowSort");
          String sWidth=JSONUtil.getString(obj, "width");
          String visible=JSONUtil.getString(obj, "visible");
          String childColumns=JSONUtil.getString(obj, "children");
          int index=sWidth.lastIndexOf("px");
          int width=100;
          int colspan = 1;
          if(index!=-1){
             sWidth=sWidth.substring(0, index);
             width=new Integer(sWidth);
          }
          if(StringUtil.isNotEmpty(childColumns)&&!("[]".equals(childColumns))){
        	  childColumn = constructSingleExportFieldColumns(childColumns,columns);
          }else{
              ExportFieldColumn fc=new ExportFieldColumn();
              fc.setAllowSort("true".equals(allowSort));
              fc.setField(fieldName);
              fc.setHeader(header);
              fc.setHeaderAlign("center");
              fc.setVisible("true".equals(visible));
              fc.setWidth(width);
              fc.setChildColumn(childColumn);
              fc.setColspan(colspan);
              columns.add(fc); 
          }
        }
        return columns;
    }
    
    private int getRowSpan(List<ExportFieldColumn> childColumns,int colspan){
    	for(ExportFieldColumn childColumn:childColumns){
    		if(childColumn.getChildColumn().size()>0){
    			List<ExportFieldColumn> subChildColumns = childColumn.getChildColumn();
    			colspan = getRowSpan(subChildColumns, colspan);
    		}else{
    			colspan +=1;
    		}
    	}
    	return colspan;
    }
    
}
