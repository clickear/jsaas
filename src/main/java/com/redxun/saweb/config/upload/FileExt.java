package com.redxun.saweb.config.upload;

/**
 * 文件扩展名
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class FileExt {
    /**
     * 文件扩展ID
     */
    private String id;
    /**
     * 文件描述
     */
    private String descp;
    /**
     * 展示的图标
     */
    private String icon;
    /**
     * 文件mineType
     */
    private String mineType;
    
    public FileExt(){
        
    }
    
    public FileExt(String id,String descp,String icon,String mineType){
        this.id=id;
        this.descp=descp;
        this.icon=icon;
        this.mineType=mineType;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMineType() {
        return mineType;
    }

    public void setMineType(String mineType) {
        this.mineType = mineType;
    }
    
    
}
