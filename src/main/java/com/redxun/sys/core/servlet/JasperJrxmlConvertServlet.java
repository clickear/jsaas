package com.redxun.sys.core.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.redxun.bpm.view.util.FormViewUtil;
import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.manager.SysReportManager;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import net.sf.json.JSONObject;
/**
 * JasperReport 转化servlet
 * @author mansan
 *
 */
public class JasperJrxmlConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection connection = null;
		Map<String, Object> params = FormViewUtil.contructParams(request);
		// 获取参数
		String repId = request.getParameter("repId");// 报表Id
		String dbId = request.getParameter("dbId");// 数据库Id
		String json = request.getParameter("dataJson");// 前台选择参数的Json
		String pageStr = request.getParameter("page");// 分页页数

		if (repId == null) {
			repId = (String) session.getAttribute("repId");
		} else {
			session.setAttribute("repId", repId);
		}
		if (dbId == null) {
			dbId = (String) session.getAttribute("dbId");
		} else {
			session.setAttribute("dbId", dbId);
		}
		if (json == null) {
			json = (String) session.getAttribute("dataJson");
		} else {
			session.setAttribute("dataJson", json);
		}

		// 将Json进行转码
		String dataJson = new String();
		if (!StringUtils.isEmpty(json)) {
			dataJson = java.net.URLDecoder.decode(json, "utf-8");
		}
		// 获取Manager
		
		SysReportManager sysRepManager = WebAppUtil.getBean(SysReportManager.class);
		String html = sysRepManager.get(repId).getParamConfig();// 获取初始化参数
		String path = sysRepManager.get(repId).getFilePath();// 模板文件路径
		
		String modelJson = "{}";// 如果前台有值就放入这个json中

		String filePath = WebAppUtil.getAppAbsolutePath() + "/reports/" + path;// 拼接模板的路径
		InputStream input = new FileInputStream(new File(filePath));
		Map<String, Object> parameters = new HashMap<String, Object>();// 该Map用于传参数到报表模板
		// 判断是否有选择参数,如果没有则显示默认模板,如果有则将前台传来的json进行处理传入Map中
		if (dataJson.isEmpty()) {
			parameters = sysRepManager.parseHtml(html, params, modelJson);// 默认
		} else {
			JSONObject jsonObject = JSONObject.fromObject(dataJson);// 前台选择了参数
			List<String> arr = jsonObject.names();// 获取名
			for (String name : arr) {
				parameters.put(name, jsonObject.get(name));// 将(名,值)传入Map
			}

		}
		// 如果有图片
		String[] picPathArr = path.split("/");
		String picPath = picPathArr[0] + "/" + picPathArr[1];// 获取图片目录
		parameters.put("BaseDir", new File(WebAppUtil.getAppAbsolutePath() + "/reports/" + picPath));// 传入图片目录

		int pageIndex = 0;// 显示页数

		try {
			connection=DataSourceUtil.getConnectionByAlias(dbId) ;
		
			JasperDesign design = JRXmlLoader.load(input);
			JasperReport report = JasperCompileManager.compileReport(design);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
			// 对显示页数的处理
			int lastPageIndex = print.getPages().size()-1;
			if (pageStr != null) {
				pageIndex = Integer.parseInt(pageStr);
				// 传来的PageIndex是从1开始，而PAGE_INDEX是从0开始的
				if (pageIndex > 0) {
					pageIndex = pageIndex - 1;
				}
				if (pageIndex == -100) {
					pageIndex = lastPageIndex;
				}else if (pageIndex < 0) {
					pageIndex = 0;
				}
				if (pageIndex > lastPageIndex) {
					pageIndex = lastPageIndex;
				}

			}
			DataSourceUtil.close(connection);
			
			response.setContentType("text/html;charset=UTF-8");
			// 获得输出流 ,这里不能这样response.getOutputStream()
			PrintWriter printWriter = response.getWriter();
			// 创建JRHtmlExporter对象
			HtmlExporter htmlExporter = new HtmlExporter();
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, print);

			SimpleHtmlExporterOutput out = new SimpleHtmlExporterOutput(printWriter);
			out.setImageHandler(new WebHtmlResourceHandler("image?image={0}"));
			SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
			configuration.setPageIndex(pageIndex);
			htmlExporter.setConfiguration(configuration);
			// configuration.setRemoveEmptySpaceBetweenRows(true);
			htmlExporter.setExporterInput(new SimpleExporterInput(print));
			htmlExporter.setExporterOutput(out);
			
			
			htmlExporter.exportReport();
			printWriter.close();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}