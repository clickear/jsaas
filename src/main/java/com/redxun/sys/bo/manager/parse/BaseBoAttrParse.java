package com.redxun.sys.bo.manager.parse;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.redxun.sys.bo.entity.SysBoAttr;

@Service
public class BaseBoAttrParse extends AbstractBoAttrParse {

	@Override
	protected void parseExt(SysBoAttr field, Element el) {

	}

	@Override
	public String getPluginName() {
		return "baseBo";
	}

	@Override
	public String getDescription() {
		
		return "";
	}
	
	@Override
	public boolean isSingleAttr() {
		return true;
	}


}
