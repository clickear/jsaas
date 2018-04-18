package com.redxun.kms.core.entity;

import java.util.Comparator;

/**
 * 新闻评论的排序类
 * @author Administrator
 *
 */
public class SortHotKdDoc implements Comparator{  
    public int compare(Object arg0,Object arg1){  
    	KdDoc cm0 = (KdDoc)arg0;  
    	KdDoc cm1 = (KdDoc)arg1;  
        int flag = cm1.getViewTimes().compareTo(cm0.getViewTimes());  
        return flag;  
    }  
}  
