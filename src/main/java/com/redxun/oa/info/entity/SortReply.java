package com.redxun.oa.info.entity;

import java.util.Comparator;

/**
 * 新闻评论的排序类
 * @author Administrator
 *
 */
public class SortReply implements Comparator{  
    public int compare(Object arg0,Object arg1){  
    	InsNewsCm cm0 = (InsNewsCm)arg0;  
    	InsNewsCm cm1 = (InsNewsCm)arg1;  
        int flag = cm0.getCreateTime().compareTo(cm1.getCreateTime());  
        return flag;  
    }  
}  
