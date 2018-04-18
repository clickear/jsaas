package com.redxun.sys.core.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysReport;
import com.redxun.sys.core.manager.SysDataSourceManager;
import com.redxun.sys.core.manager.SysReportManager;
import com.redxun.sys.core.manager.SysTreeManager;

/**
 * [SysReport]管理
 * @author mansan
 * @Email keitch@redxun.cn
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
@Controller
@RequestMapping("/sys/core/sysReport/")
public class SysReportFormController extends BaseFormController {

	@Resource
	private SysReportManager sysReportManager;
	@Resource
	private SysDataSourceManager sysDatasourceManager;
	@Resource
	private SysTreeManager sysTreeManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("sysReport")
	public SysReport processForm(HttpServletRequest request) {
		String repId = request.getParameter("repId");
		SysReport sysReport = null;
		if (StringUtils.isNotEmpty(repId)) {
			sysReport = sysReportManager.get(repId);
		} else {
			sysReport = new SysReport();
		}

		return sysReport;
	}

	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param sysReport
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("sysReport") @Valid SysReport sysReport, BindingResult result) {
		String treeId = request.getParameter("treeId");
		String sourceId = request.getParameter("sourceId");
		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		if (StringUtils.isEmpty(sysReport.getRepId())) {
			sysReport.setRepId(idGenerator.getSID());
			sysReport.setDsAlias(sourceId);
			sysReport.setSysTree(sysTreeManager.get(treeId));
			sysReportManager.create(sysReport);
			msg = getMessage("sysReport.created", new Object[] { sysReport.getIdentifyLabel() }, "[SysReport]成功创建!");
		} else {
			sysReportManager.detach(sysReport.getSysTree());
			sysReport.setDsAlias(sourceId);
			sysReport.setSysTree(sysTreeManager.get(treeId));
			sysReportManager.update(sysReport);
			msg = getMessage("sysReport.updated", new Object[] { sysReport.getIdentifyLabel() }, "[SysReport]成功更新!");
		}
		// saveOpMessage(request, msg);
		return new JsonResult(true, msg);
	}

	/**
	 * 上传报表的保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("uploadTemplate")
	@ResponseBody
	public JSONObject uploadTemplate(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		String treeId = request.getParameter("treeId");
		String sourceId = request.getParameter("sourceId");
		String repId = request.getParameter("repId");
		String subject = request.getParameter("subject");
		String key = request.getParameter("key");
		String descp = request.getParameter("descp");
		String paramConfig=request.getParameter("paramConfig");
		//String bean = request.getParameter("selfHandleBean");
		String engine = "JASPER";//只有这个引擎,暂时固死
		Map<String, MultipartFile> files = request.getFileMap();
		
		String tenantId = ContextUtil.getCurrentTenantId();
		
		Iterator<MultipartFile> it = files.values().iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String path = sdf.format(new Date());
		String uid = IdUtil.getId();
		StringBuffer sbPath = new StringBuffer();
		JSONObject rtnObj=new JSONObject();
		
		//检测是否存在这个key值对应的report
		SysReport checkReportExist=sysReportManager.getByKey(key, tenantId);
		//如果新建时租户内存在这个key的报表||或者更新时这个key的报表不是现在这个
		if((StringUtils.isBlank(repId)&&checkReportExist!=null)||(checkReportExist!=null&&!checkReportExist.getRepId().equals(repId))){
			rtnObj.put("success", false);
			return rtnObj;
		}
		
		if (StringUtils.isEmpty(repId)) {
			SysReport sysReport = new SysReport();
			sysReport.setRepId(IdUtil.getId());
			sysReport.setDsAlias(sourceId);
			sysReport.setSysTree(sysTreeManager.get(treeId));
			sysReport.setSubject(subject);
			sysReport.setKey(key);
			sysReport.setDescp(descp);
			sysReport.setEngine(engine);
			sysReport.setParamConfig(paramConfig);
			//sysReport.setSelfHandleBean(bean);
			while (it.hasNext()) {
				MultipartFile f = it.next();
				String filename = f.getOriginalFilename();
				String[] type = filename.split("[.]");
				File file = new File(WebAppUtil.getAppAbsolutePath() + "/reports/" + path + "/" + uid, filename);
				if (!file.exists()) {
					file.mkdirs();
				}
				f.transferTo(file);
				
				if (!type[1].equals("jrxml")&&!type[1].equals("jasper")) {
					File zipFile = file;
					String zipPath = WebAppUtil.getAppAbsolutePath() + "/reports/" + path + "/" + uid + "/";
					filename = unZipFiles(zipFile, zipPath);
				}
				sbPath.append(path).append("/").append(uid).append("/").append(filename);
			}
			sysReport.setFilePath(sbPath.toString());
			sysReportManager.create(sysReport);
			rtnObj.put("success", true);
		} else {
			SysReport sysReport = sysReportManager.get(repId);
			sysReportManager.detach(sysReport.getSysTree());
			sysReport.setDsAlias(sourceId);
			sysReport.setSysTree(sysTreeManager.get(treeId));
			sysReport.setSubject(subject);
			sysReport.setKey(key);
			sysReport.setParamConfig(paramConfig);
			//sysReport.setSelfHandleBean(bean);
			sysReport.setDescp(descp);
			sysReport.setEngine(engine);

			if(files.size()>0){
				// 删除之前的模板文件,然后添加新的模板文件
				String filePath = sysReport.getFilePath();// 获得之前的路径
				File oldFile = new File(WebAppUtil.getAppAbsolutePath() + "/reports/" + filePath);// 得到完整路径
				oldFile.delete();
				String[] oldPath = filePath.split("/");// 获取之前的目录路径
				while (it.hasNext()) {
					MultipartFile f = it.next();
					String filename = f.getOriginalFilename();
					String[] type = filename.split("[.]");
					
					File file = new File(WebAppUtil.getAppAbsolutePath() + "/reports/" + oldPath[0] + "/" + oldPath[1], filename);
					if (!file.exists()) {
						file.mkdirs();
					}
					f.transferTo(file);
					
					if (!type[1].equals("jrxml")&&!type[1].equals("jasper")) {
						File zipFile = file;
						String zipPath = WebAppUtil.getAppAbsolutePath() + "/reports/" + oldPath[0] + "/" + oldPath[1] + "/";
						filename = unZipFiles(zipFile, zipPath);
					}
					sbPath.append(oldPath[0]).append("/").append(oldPath[1]).append("/").append(filename);
				}
				sysReport.setFilePath(sbPath.toString());	
			}
			
			sysReportManager.update(sysReport);
			rtnObj.put("success", true);
		}
		return rtnObj;
	}

	@RequestMapping("saveParam")
	@ResponseBody
	public JsonResult saveParam(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String param = request.getParameter("htmlCode");
		String repId = request.getParameter("pkId");
		SysReport sysReport = sysReportManager.get(repId);
		sysReport.setParamConfig(param);
		sysReportManager.update(sysReport);
		return new JsonResult(true, "成功保存！");
	}

	public static void unZipFiles(String zipPath, String descDir) throws IOException {
		unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * 解压文件到指定目录,并返回模板文件的名字
	 * 
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String unZipFiles(File zipFile, String descDir) throws IOException {
		File pathFile = new File(descDir);
		String path = null;
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			String[] type = zipEntryName.split("\\.");
			if (type[1].equals("jrxml") || type[1].equals("jasper")) {
				path = zipEntryName;
			}
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
			;
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			System.out.println(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		return path;
	}
}
