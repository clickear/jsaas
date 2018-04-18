package com.redxun.mobile.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.sys.core.entity.SysDic;
import com.redxun.sys.core.manager.SysDicManager;
import com.redxun.sys.db.manager.SysSqlCustomQueryManager;

@Controller
@RequestMapping("/mobile/component/ajax/")
public class ComponentAjaxController {
	
	@Resource
	SysDicManager sysDicManager;
	@Resource
	SysSqlCustomQueryManager sysSqlCustomQueryManager;
	
	
	@RequestMapping("getDicJsonsByKey")
	@ResponseBody
	public JsonResult getDicJsonsByKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dicKey=request.getParameter("dic");
		List<SysDic> sysDics=sysDicManager.getByTreeKey(dicKey);
		JSONArray jsonArray=new JSONArray();
		for(SysDic sysDic:sysDics){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("key", sysDic.getKey());
			jsonObject.put("value", sysDic.getName());
			jsonArray.add(jsonObject);
		}
		return new JsonResult(true,"",jsonArray);
	}
	
}
