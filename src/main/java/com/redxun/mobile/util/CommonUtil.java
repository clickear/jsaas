package com.redxun.mobile.util;

import java.util.List;
import java.util.Map;

import com.alibaba.druid.proxy.jdbc.ClobProxyImpl;
import com.redxun.core.util.FileUtil;
import com.redxun.mobile.model.ListModel;

public class CommonUtil {
	
	public static ListModel getListModel(List list,Integer total){
		for(Object o :list){
			if(!(o instanceof Map)) continue;
			Map<String,Object> row=(Map<String,Object>)o;
			for(Map.Entry<String,Object>  ent:row.entrySet()){
				Object val=ent.getValue();
				if(val instanceof ClobProxyImpl ){
					ClobProxyImpl clob=(ClobProxyImpl)val;
					String str= FileUtil.clobToString(clob);
					row.put(ent.getKey(), str);
				}
			}
		}
		
		ListModel model=new ListModel();
		model.setRowList(list);
		model.setTotal(total);
		return model;
	}
	


}
