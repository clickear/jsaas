
package com.redxun.oa.article.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.ExtBaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.BeanUtil;
import com.redxun.core.util.FileUtil;
import com.redxun.oa.article.entity.ProArticle;
import com.redxun.oa.article.entity.ProItem;
import com.redxun.oa.article.manager.ProArticleManager;
import com.redxun.oa.article.manager.ProItemManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseMybatisListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.saweb.util.RequestUtil;
import com.redxun.saweb.util.WebAppUtil;
import com.redxun.sys.core.entity.SysFile;
import com.redxun.sys.core.manager.SysFileManager;
import com.redxun.sys.log.LogEnt;

/**
 * 项目控制器
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/article/proItem/")
public class ProItemController extends BaseMybatisListController{
    @Resource
    ProItemManager proItemManager;
    @Resource
    ProArticleManager proArticleManager;
    @Resource
    FreemarkEngine freemarkEngine;
    @Resource
    SysFileManager sysFileManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    @LogEnt(action = "del", module = "oa", submodule = "项目")
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=RequestUtil.getString(request, "ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                proItemManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除!");
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=RequestUtil.getString(request, "pkId");
        ProItem proItem=null;
        if(StringUtils.isNotEmpty(pkId)){
           proItem=proItemManager.get(pkId);
        }else{
        	proItem=new ProItem();
        }
        return getPathView(request).addObject("proItem",proItem);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=RequestUtil.getString(request, "pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	ProItem proItem=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		proItem=proItemManager.get(pkId);
    		if("true".equals(forCopy)){
    			proItem.setID(null);
    		}
    	}else{
    		proItem=new ProItem();
    	}
    	return getPathView(request).addObject("proItem",proItem);
    }
    
    @RequestMapping("mylist")
    @ResponseBody
    public JsonPageResult<ProItem> mylist(HttpServletRequest request,HttpServletResponse response){
    	String tenantId=ContextUtil.getCurrentTenantId();
    	QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
    	queryFilter.addFieldParam("TENANT_ID_", tenantId);
    	List<ProItem> proItems=proItemManager.getAll(queryFilter);
    	JsonPageResult<ProItem> jsonPageResult=new JsonPageResult<ProItem>(proItems, queryFilter.getPage().getTotalItems());
    	return jsonPageResult;
    }


	@SuppressWarnings("rawtypes")
	@Override
	public ExtBaseManager getBaseManager() {
		return proItemManager;
	}
	
	@RequestMapping("exportArticle")
	public void exportArticle(HttpServletRequest request,HttpServletResponse response) throws IOException, Exception{
		String pkId=RequestUtil.getString(request, "pkId");
		ProItem proItem=proItemManager.get(pkId);
		String genSrc=proItem.getGenSrc();
		List<ProArticle> proArticles=proArticleManager.getByItemId(pkId);
		List<ProArticle> list=BeanUtil.listToTree(proArticles);
		Map<String,Object> dataMap=new HashMap<String,Object>();//构建树形结构数据
		dataMap.put("dto", list);
		dataMap.put("proItemName", proItem.getName());
        try {
			String indexContent=freemarkEngine.mergeTemplateIntoString("rqt/articleIndex.ftl", dataMap);
			FileUtil.writeFile(genSrc+File.separator+"index.html", indexContent);
			for (ProArticle proArticle : proArticles) {//遍历每篇文章内容
    			String content=proArticle.getContent();//原始content
    			String newContent;				
				if("index".equals(proArticle.getType())){//如果是目录,则放弃输出内容
					newContent="";//赋予空值
				}else{
					newContent=translate2LocalHtml(content, genSrc);//转换后的content
				}
    			String newFileName=genSrc+File.separator+"html"+File.separator+proArticle.getId()+".html";//生成的文件名
    			FileUtil.writeFile(newFileName, newContent);
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        response.setContentType("application/zip");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode( proItem.getName(),"utf-8") + ".zip\"");
		ArchiveOutputStream zipOutputStream = new ArchiveStreamFactory()
		.createArchiveOutputStream(ArchiveStreamFactory.ZIP,response.getOutputStream());
		
		File dir = new File(genSrc);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        for (int i = 0; i < files.length; i++) {
        	if(files[i].isDirectory()){
        		File[] insideFiles=files[i].listFiles();
        		for (int j = 0; j < insideFiles.length; j++) {
        			zipOutputStream.putArchiveEntry(new ZipArchiveEntry("newZip/"+files[i].getName()+"/"+insideFiles[j].getName()));
                	FileInputStream is = new FileInputStream(insideFiles[j]);
        			IOUtils.copy(is, zipOutputStream);
        			zipOutputStream.closeArchiveEntry();	
				}
        	}else{
        		zipOutputStream.putArchiveEntry(new ZipArchiveEntry("newZip/"+files[i].getName()));
            	FileInputStream is = new FileInputStream(files[i]);
    			IOUtils.copy(is, zipOutputStream);
    			zipOutputStream.closeArchiveEntry();	
        	}
        	
		}
        zipOutputStream.close();
        
	}
	
	
	/**
	 * 将服务器版文件转换成本地html,顺带把图片都存到本地
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	private String translate2LocalHtml(String content,String targetSrc) throws Exception{
		if(StringUtils.isBlank(content)){
			return "";
		}
		Document document=Jsoup.parse(content);
 		org.jsoup.select.Elements elements=document.select("img[src]");
		for (Element element : elements) {
	        String imgUrl = element.attr("src");
	        String[] urlArray=imgUrl.split("fileId=");
	        String fileId=urlArray[1];
	        SysFile sysFile=sysFileManager.get(fileId);	
	        String suffix=sysFile.getExt();
	        String path=WebAppUtil.getUploadPath()+ File.separator +sysFile.getPath();
	        File sourceFile=new File(path);
	        File targetFile=new File(targetSrc+File.separator+"img"+File.separator+fileId+suffix);
	        if (!targetFile.getParentFile().exists()) {
	        	targetFile.getParentFile().mkdirs();
			}
	        FileUtil.copyFileUsingFileChannels(sourceFile, targetFile);
	        element.attr("src", "../img/"+targetFile.getName());
	      }
		Element element=document.getElementsByTag("body").first();
		return element.toString();
	}
	


}
