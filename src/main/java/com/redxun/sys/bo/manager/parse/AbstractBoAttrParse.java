package com.redxun.sys.bo.manager.parse;

import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.util.StringUtil;
import com.redxun.sys.bo.entity.SysBoAttr;

public abstract class AbstractBoAttrParse implements IBoAttrParse {
	
	
	@Override
	public JSONObject getInitData(SysBoAttr attr) {
		return null;
	}


	protected int getLength(String len,int defaultLen) {
		if(StringUtil.isNotEmpty(len)){
			return Integer.parseInt(len);
		}
		return defaultLen;
	}
	
	

	protected abstract void parseExt(SysBoAttr field,Element el);
	

	@Override
	public SysBoAttr parse(String pluginName, Element el) {
		SysBoAttr field=new SysBoAttr();
		
		/**
		 * 解析基本信息
		 */
		parseBase( field, el);
		
		//解析扩展信息
		parseExt(field, el);
		return field;
	}

	private void parseBase(SysBoAttr boAttr,Element el){
		String label = el.attr("label").trim();
		String name = el.attr("name").trim();
		String plugins=el.attr("plugins");
		
		boAttr.setIsSingle(isSingleAttr()?1:0);
		boAttr.setComment(label);
		boAttr.setName(name);
		boAttr.setControl(plugins);
		
		
		
		
	}
}
