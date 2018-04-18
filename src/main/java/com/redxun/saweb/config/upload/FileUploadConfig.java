package com.redxun.saweb.config.upload;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.redxun.core.util.Dom4jUtil;

/**
 * 文件附件上传的配置信息
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class FileUploadConfig implements InitializingBean{

	private String fileExtGroupMapJson = "";
    private Map<String,FileExtCategory> fileExtCatMap=new HashMap<String,FileExtCategory>();
    private Map<String,FileExt> fileExtMap=new HashMap<String,FileExt>();
    
    public void afterPropertiesSet() throws Exception {
 
        Resource resource = new ClassPathResource("config/fileTypeConfig.xml");
        
        InputStream is=resource.getInputStream();
        
        Document doc=Dom4jUtil.load(is);
        
        Element rootEl=doc.getRootElement();
        
        List<Node> fileExtCats = rootEl.selectNodes("/fileCat/fileExtCat");
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i=0;i<fileExtCats.size();i++){
            Element el=(Element)fileExtCats.get(i);
            String id=el.attributeValue("id");
            String descp=el.attributeValue("descp");
            List<Element> fileList=el.elements();
            FileExtCategory category=new FileExtCategory(id,descp);
            for(Element fileEl:fileList){
                String fileId=fileEl.attributeValue("id");
                String fileDescp=fileEl.attributeValue("descp");
                String icon=fileEl.attributeValue("icon");
                FileExt fileExt=new FileExt(fileId,fileDescp,icon,id);
                fileExtMap.put(fileId, fileExt);
                category.getFiles().add(fileExt);
            }
            fileExtCatMap.put(id,category);
            sb.append("{id:'"+id+"',text:'"+descp+"'}");
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        fileExtGroupMapJson=sb.toString();
    }
    
    /**
     * 返回某种文档类型中允许上传的文件类型,
     * @param mineType
     * @return 如gif,bmp,jpeg,jpg
     */
    public String getAllowExts(String mineType){
        FileExtCategory category=fileExtCatMap.get(mineType);
        if(category==null) return "";
        StringBuilder sb=new StringBuilder();
        for(FileExt ext:category.getFiles()){
            sb.append(ext.getId()).append(",");
        }
        if(sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    
    
    /**
     * 判断扩展名时候属于指定类型。
     * @param mineType
     * @param extType
     * @return
     */
    public  boolean isAllowExts(String mineType,String extType){
        FileExtCategory category=fileExtCatMap.get(mineType);
        if(category==null) return false;
        for(FileExt ext:category.getFiles()){
           boolean rtn= ext.getId().equalsIgnoreCase(extType);
           if(rtn) return true;
        }
        return false;
    }
    

    public Map<String, FileExtCategory> getFileExtCatMap() {
        return fileExtCatMap;
    }

    public Map<String, FileExt> getFileExtMap() {
        return fileExtMap;
    }
    
    public String getFileExtGroupMapJson(){
    	return fileExtGroupMapJson;
    }

}
