package com.redxun.sys.customform.manager;

import com.alibaba.fastjson.JSONObject;
import com.redxun.sys.bo.entity.BoResult;

/**
 * 自定义表单数据处理器接口。
 * @author ray
 *
 */
public interface ICustomFormDataHandler {
	
	/**
	 * 获取初始数据。
	 * @return
	 */
	JSONObject getInitData(String boDefId);
	
	/**
	 * 根据pk返回数据。
	 * @param pk
	 * @return
	 */
	JSONObject getByPk(String pk,String boDefId);
	
	
	/**
	 * 保存表单数据。
	 * @param jsonData
	 * @return
	 */
	BoResult save(String boDefId,JSONObject jsonData);
	
	
	void delById(String id);
	
	
}
