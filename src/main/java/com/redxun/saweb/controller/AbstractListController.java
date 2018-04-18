package com.redxun.saweb.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;

import com.redxun.core.json.IJson;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.FileUtil;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.export.PoiTableBuilder;
import com.redxun.ui.view.model.IGridColumn;

public abstract class AbstractListController extends BaseController {

	@Resource
	protected PoiTableBuilder poiTableBuilder;
	@Resource
	protected IJson iJson;
	
	/**
	 * 获得查询的过滤条件
	 * @param request
	 * @return
	 */
	protected abstract QueryFilter getQueryFilter(HttpServletRequest request);
	
	public abstract List<?>  getPage(QueryFilter queryFilter);
	
	public abstract  Long  getTotalItems(QueryFilter queryFilter);
	
	
	/**
	 * 分页返回查询列表，包括导出单页，所有页
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("listData")
	public void listData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String export=request.getParameter("_export");
		//是否导出
		if(StringUtils.isNotEmpty(export)){
			String exportAll=request.getParameter("_all");
			if(StringUtils.isNotEmpty(exportAll)){
				exportAllPages(request,response);
			}else{
				exportCurPage(request,response);
			}
		}else{
			response.setContentType("application/json");
			QueryFilter queryFilter=getQueryFilter(request);
			
			List<?> list=getPage(queryFilter);
			JsonPageResult<?> result=new JsonPageResult(list,queryFilter.getPage().getTotalItems());
			String jsonResult=iJson.toJson(result);
			PrintWriter pw=response.getWriter();
			pw.println(jsonResult);
			pw.close();
		}
	}
	
	
	
	/**
	 * 导出当前页记录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	//@RequestMapping("exportCurPage")
	public void exportCurPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 根据前台的列来生成列表
		String columns = URLDecoder.decode(request.getParameter("columns"),"UTF-8");

		QueryFilter queryFilter = getQueryFilter(request);
		
		List<?> list = getPage(queryFilter);
		
		response.setContentType("application/ms-excel");
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmm");

		response.setHeader("Content-Disposition", "attachment;filename=" + sdf.format(curDate) + ".xls");
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		
		List<IGridColumn> gridColumns = poiTableBuilder.constructColumns(columns);
		Workbook wb = poiTableBuilder.writeTable(gridColumns, list);
		wb.write(outByteStream);
		// 生成文件
		OutputStream out = response.getOutputStream();
		out.write(outByteStream.toByteArray());
		out.flush();
	}

	/**
	 * 写入记录到文件中
	 * @param dataList
	 * @param columns
	 * @param index
	 * @return
	 * @throws Exception
	 */
	private File writeRecordsToFile(List<?> dataList, List<IGridColumn> columns, int index) throws Exception {
		FileUtil.checkAndCreatePath(ContextUtil.getUserTmpDir());
		File file = new File(ContextUtil.getUserTmpDir() + "/records_" + index + ".xls");
		FileOutputStream fos = new FileOutputStream(file);
		Workbook wb = poiTableBuilder.writeTable(columns, dataList);
		wb.write(fos);
		return file;
	}
	
	/**
	 * 下载所有页记录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportAllPages")
	public void exportAllPages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 根据前台的列来生成列表
		String columns = URLDecoder.decode(request.getParameter("columns"),"UTF-8");
		ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmm");
		response.setContentType("application/zip");
		response.addHeader("Content-Disposition", "attachment; filename=\"all_" + sdf.format(curDate) + ".zip\"");

		QueryFilter queryFilter = getQueryFilter(request);
		// 构建下载列
		List<IGridColumn> gridColumns = poiTableBuilder.constructColumns(columns);
		
		Page page=(Page)queryFilter.getPage();
		
		// 后续的不需要统计其记录总数
		((Page)queryFilter.getPage()).setSkipCountTotal(true);
		// 取得总记录数以计算其页数
		Long totalItems = getTotalItems(queryFilter);
		page.setTotalItems(totalItems.intValue());
		int totalPages = page.getTotalPages();
		// 下载后续的页
		for (int i = 0; i < totalPages; i++) {
			page.setPageIndex(i);
			List<?> list = getPage(queryFilter);
			// 把文件写至临时目录
			File file = writeRecordsToFile(list, gridColumns, i + 1);
			// 添加文件至压缩流
			FileUtil.zipFile(file, zipOutputStream);
			// 删除该临时文件
			file.delete();
		}

		zipOutputStream.close();
	}

}
