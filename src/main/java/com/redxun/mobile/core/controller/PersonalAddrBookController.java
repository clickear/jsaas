package com.redxun.mobile.core.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JsonConfig;

import org.apache.activemq.util.IdGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.QueryParam;
import com.redxun.core.util.StringUtil;
import com.redxun.oa.personal.entity.AddrBook;
import com.redxun.oa.personal.manager.AddrBookManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.QueryFilterBuilder;


@Controller
@RequestMapping("/mobile/oa/personalAddrBook/")
public class PersonalAddrBookController extends BaseController {
	
	@Resource
	AddrBookManager addrBookManager;
	
	@RequestMapping("edit")
	public JsonResult edit(HttpServletRequest request, HttpServletResponse response) throws Exception { // 编辑联系人信息
		
		
		return null;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public JsonResult save(HttpServletRequest request, HttpServletResponse response) throws Exception { // 新增联系人信息
		//联系人信息
		String data = request.getParameter("data");
		if(StringUtils.isNotBlank(data)){
			AddrBook addrBook = (AddrBook) JSONUtil.json2Bean(data, AddrBook.class);
			addrBook.setAddrId(idGenerator.getSID());
			addrBookManager.create(addrBook);
		}
		return new JsonResult(true, "保存成功");
	}
	
	
	@RequestMapping("saveList")
	@ResponseBody
	public JsonResult saveList(HttpServletRequest request, HttpServletResponse response) throws Exception { // 新增联系人信息
		//联系人信息
		String data = request.getParameter("data");
		Map<String,AddrBook> filterMap = new HashMap<String, AddrBook>();
		if(StringUtils.isNotBlank(data)){
			List<AddrBook> addrBook = JSONArray.parseArray(data, AddrBook.class);
			QueryFilter queryFilter = new QueryFilter();
			queryFilter.addFieldParam("createBy", ContextUtil.getCurrentUserId());
			List<AddrBook> mobileList = addrBookManager.getAll(queryFilter);
			
			if(mobileList!=null&&mobileList.size()>0){
				for(AddrBook newAddrBoos: addrBook){
					filterMap.put(newAddrBoos.getMobile(), newAddrBoos);
				}			
				for(AddrBook orginBook: mobileList){
					if(filterMap.containsKey(orginBook.getMobile())){
						filterMap.remove(orginBook.getMobile());
					}
				}	
				
				for(Map.Entry<String, AddrBook> map : filterMap.entrySet()){
					addrBookManager.create(map.getValue());				
				}
			}			
		}
		return new JsonResult(true, "同步成功",filterMap.size());
	}
	
	
	
	@RequestMapping("listAddrBook")
	@ResponseBody
	public JsonPageResult<AddrBook> listAddrBook(HttpServletRequest request, HttpServletResponse response) throws Exception { // 获取联系人信息
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		String search = request.getParameter("search");
		if(StringUtils.isNotBlank(search)){
			queryFilter.addFieldParam("name", QueryParam.OP_LEFT_LIKE,search+"%");
		}
		queryFilter.addFieldParam("createBy", ContextUtil.getCurrentUserId());
		List<AddrBook> addrBooks =  addrBookManager.getAll(queryFilter);
		return new JsonPageResult(true,addrBooks,queryFilter.getPage().getTotalItems(),"读取成功");
	
	}
	
}
