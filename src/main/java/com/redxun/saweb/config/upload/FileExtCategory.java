package com.redxun.saweb.config.upload;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件扩展名分类
 * @author CSX
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class FileExtCategory {
    private String id;
    private String descp;
    List<FileExt> files=new ArrayList<FileExt>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public List<FileExt> getFiles() {
        return files;
    }

    public void setFiles(List<FileExt> files) {
        this.files = files;
    }

    public FileExtCategory() {
    }
    
    public FileExtCategory(String id,String descp){
        this.id=id;
        this.descp=descp;
    }
}
