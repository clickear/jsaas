package com.redxun.oa.product.controller;

import java.util.ArrayList;

import com.redxun.oa.product.entity.OaProductDefPara;

public class OaMyArrayList extends ArrayList<OaProductDefPara> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean add(OaProductDefPara oValue){
		for(int i=0;i<super.size();i++){
			OaProductDefPara oKeyValue=super.get(i);
			if(oValue.getOaProductDefParaKey().getName().equals(oKeyValue.getOaProductDefParaKey().getName())){
				oKeyValue.getOaProductDefParaValue().setValueId(oKeyValue.getOaProductDefParaValue().getValueId()+","+oValue.getOaProductDefParaValue().getValueId());
				oKeyValue.getOaProductDefParaValue().setName(oKeyValue.getOaProductDefParaValue().getName()+","+oValue.getOaProductDefParaValue().getName());
				return true;
			}
		}
		return super.add(oValue);
	}
}
