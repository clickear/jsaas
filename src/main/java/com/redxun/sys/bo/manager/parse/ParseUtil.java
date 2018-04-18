package com.redxun.sys.bo.manager.parse;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.database.api.model.Column;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.bo.entity.SysBoAttr;

public class ParseUtil {
	
	
	/**
	 * 解析el的data json数据。
	 * 		//[{"_id":31,"_uid":31,"_state":"added","key":"math","name":"数学"},
	 *	//{"_id":30,"_uid":30,"_state":"added","key":"eng","name":"英语"},
	 *	//{"_id":29,"_uid":29,"_state":"added","key":"chinaese","name":"语文"}]
	 * @param el
	 * @return
	 */
	public static String parseJosnData(Element el){
		String jsonStr=el.attr("data");
		if(StringUtil.isEmpty(jsonStr)) return "";
		
		JSONObject rtnJson=new JSONObject();
		JSONArray ary=JSONArray.parseArray(jsonStr);
		for(Object obj:ary){
			JSONObject jsonObj=(JSONObject)obj;
			rtnJson.put(jsonObj.getString("key"), jsonObj.getString("name"));
		}
		return rtnJson.toJSONString();
		
	}
	
	/**
	 * 设置字符串长度。
	 * @param boAttr
	 * @param el
	 */
	public static void setStringLen(SysBoAttr boAttr,Element el){
		boAttr.setDataType(Column.COLUMN_TYPE_VARCHAR);
		String strLen=el.attr("length");
		if(StringUtil.isEmpty(strLen)){
			strLen="64";
		}
		boAttr.setLength(Integer.parseInt(strLen)); 
	}
	

}
