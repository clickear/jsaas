package com.example.stock.core.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.bpmclient.model.JsonResult;
import com.redxun.core.dao.mybatis.CommonDao;
import com.redxun.core.entity.SqlModel;

@Controller
@RequestMapping("/stock/core/calUnit")
public class CallUnitController {
	@Resource 
	CommonDao commonDao;
	
	@RequestMapping("checkCode")
	@ResponseBody
	public JsonResult checkCode(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("ID_");
		String code=request.getParameter("code");
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="select * from w_calUnit where f_code=#{code}";
		params.put("code", code);
		if(StringUtils.isNotEmpty(id)){
			params.put("id", id);
			sql="select * from w_calunit where f_code=#{code} and id_!=#{id}";
		}
		SqlModel sqlModel=new SqlModel(sql, params);
		Object row=commonDao.queryOne(sqlModel);
		if(row!=null){
			return new JsonResult(false,"已经存在Code！");
		}else{
			return new JsonResult(true,"不存在Code！");
		}
	}
}
