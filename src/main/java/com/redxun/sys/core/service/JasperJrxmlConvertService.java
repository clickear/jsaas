package com.redxun.sys.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.manager.SysReportManager;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import net.sf.json.JSONObject;

/**
 * 报表管理
 * @author mansan
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 */

@Service
public class JasperJrxmlConvertService {

	@Resource
	private SysReportManager sysReportManager;

	/**
	 * 将报表填充,返回一个Jasperprint对象
	 * 
	 * @param map
	 * @return Jasperprint
	 * @throws Exception 
	 */
	public JasperPrint fill(Map<String, Object> map) throws Exception {
		Connection connection = null;
		String repId = (String) map.get("repId");// 报表Id
		String dbId = (String) map.get("dbId");// 数据库Id
		String json = (String) map.get("json");// 前台选择参数的Json
		String bean = (String) map.get("bean");

		Map<String, Object> params = new HashMap<String, Object>();
		// 将Json进行转码
		// 获取Manager
		String html = sysReportManager.get(repId).getParamConfig();// 获取初始化参数
		String path = sysReportManager.get(repId).getFilePath();// 模板文件路径
		String modelJson = "{}";// 如果前台有值就放入这个json中

		String filePath = WebAppUtil.getAppAbsolutePath() + "/reports/" + path;// 拼接模板的路径
		InputStream input = null;
		try {
			input = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Map<String, Object> parameters = new HashMap<String, Object>();// 该Map用于传参数到报表模板
		// 判断是否有选择参数,如果没有则显示默认模板,如果有则将前台传来的json进行处理传入Map中
		if (StringUtils.isBlank(bean)) {
			parameters = sysReportManager.parseHtml(html, params,modelJson);
			if (StringUtils.isNotEmpty(json)) {
				JSONObject jsonObject = JSONObject.fromObject(json);// 前台选择了参数
				List<String> arr = jsonObject.names();// 获取名
				for (String name : arr) {
					parameters.put(name, jsonObject.get(name));// 将(名,值)传入Map
				}
			}
		} else {
			parameters = map;// 传入参数
		}
		// 如果有图片
		String[] picPathArr = path.split("/");
		String picPath = picPathArr[0] + "/" + picPathArr[1];// 获取图片目录
		parameters.put("BaseDir", new File(WebAppUtil.getAppAbsolutePath() + "/reports/" + picPath));// 传入图片目录
		try {
			connection =DataSourceUtil.getConnectionByAlias(dbId);
			if (picPathArr[2].contains(".jasper")) {
				JasperPrint print = JasperFillManager.fillReport(input, parameters, connection);
				return print;
			} else if (picPathArr[2].contains(".jrxml")) {
				JasperDesign design = JRXmlLoader.load(input);// 读取jrxml文件
				JasperReport report = JasperCompileManager.compileReport(design);// 将jrxml文件编译成jasper文件，或者说JasperReport类
				JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);// 编译成print格式，这种格式可以导出和展示
				return print;
			}
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			DataSourceUtil.close(connection);
		}
		return new JasperPrint();
	}

	/**
	 * 预览报表
	 * 
	 * @param map
	 *            传入参数
	 * @throws Exception
	 */
	public void show(Map<String, Object> map) throws Exception {
		String pageStr = (String) map.get("page");// 分页页数
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpServletResponse response = (HttpServletResponse) map.get("response");
		int pageIndex = 0;// 显示页数
		try {
			JasperPrint print = fill(map);
			// 对显示页数的处理
			int lastPageIndex = print.getPages().size();
			if (pageStr != null) {
				pageIndex = Integer.parseInt(pageStr);
				// 传来的PageIndex是从1开始，而PAGE_INDEX是从0开始的
			}
			response.setContentType("text/html;charset=UTF-8");
			// 获得输出流 ,这里不能这样response.getOutputStream()
			PrintWriter printWriter = response.getWriter();
			// 创建HtmlExporter对象
			HtmlExporter htmlExporter = new HtmlExporter();
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, print);// 显示图片需要的设定
			SimpleHtmlExporterOutput out = new SimpleHtmlExporterOutput(printWriter);
			out.setImageHandler(new WebHtmlResourceHandler(request.getContextPath() + "/image?image={0}"));// 显示图片需要的设定
			SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
			configuration.setPageIndex(pageIndex);// 显示特定的某一页，如果没有指定将一页显示所有页面
			htmlExporter.setConfiguration(configuration);
			// configuration.setRemoveEmptySpaceBetweenRows(true);
			htmlExporter.setExporterInput(new SimpleExporterInput(print));
			htmlExporter.setExporterOutput(out);

			htmlExporter.exportReport();
			printWriter.write("<script type='text/javascript'>var pageIndex=" + pageIndex + ";</script>");
			printWriter.write("<script type='text/javascript'>var lastPageIndex=" + lastPageIndex + ";</script>");
			printWriter.close();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 根据不同的导出类型,导出不同的类型报表
	 * 
	 * @param ext
	 *            类型(xsl,pdf..)
	 * @param jasperPrint
	 * @param map
	 *            参数
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected void getExporterByExt(String ext, JasperPrint jasperPrint, Map<String, Object> map) throws IOException {
		JRAbstractExporter exporter = null;
		HttpServletResponse response = (HttpServletResponse) map.get("response");
		OutputStream out = response.getOutputStream();
		if ("pdf".equalsIgnoreCase(ext)) {
			exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setCreatingBatchModeBookmarks(true);
			exporter.setConfiguration(configuration);
			response.setContentType("application/pdf");
		} else if ("xls".equalsIgnoreCase(ext)) {// 不分页显示
			exporter = new JRXlsExporter();
			response.setContentType("application/xls");
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(false);
			exporter.setConfiguration(configuration);
		} else if ("pagexls".equalsIgnoreCase(ext)) {// 分页显示
			exporter = new JRXlsExporter();
			response.setContentType("application/xls");
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			exporter.setConfiguration(configuration);
		} else if ("doc".equalsIgnoreCase(ext)) {
			exporter = new JRDocxExporter();
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
		}

		try {
			exporter.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 导出报表
	 * 
	 * @param exportType
	 *            报表类型(pdf...)
	 * @param map
	 *            参数
	 * @throws Exception
	 */
	public void export(String exportType, Map<String, Object> map) throws Exception {
		JasperPrint print = fill(map);
		getExporterByExt(exportType, print, map);
	}

	
}