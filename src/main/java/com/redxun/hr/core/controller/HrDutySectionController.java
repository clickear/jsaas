package com.redxun.hr.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.BeanUtil;
import com.redxun.hr.core.entity.HrDutySection;
import com.redxun.hr.core.manager.HrDutySectionManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/**
 * [HrDutySection]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutySection/")
public class HrDutySectionController extends BaseListController {
	@Resource
	HrDutySectionManager hrDutySectionManager;

	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}

	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {
			String[] ids = uId.split(",");
			for (String id : ids) {
				hrDutySectionManager.delete(id);
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 查看明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		HrDutySection hrDutySection = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrDutySection = hrDutySectionManager.get(pkId);
		} else {
			hrDutySection = new HrDutySection();
		}
		return getPathView(request).addObject("hrDutySection", hrDutySection);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		HrDutySection hrDutySection = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrDutySection = hrDutySectionManager.get(pkId);
			if ("true".equals(forCopy)) {
				hrDutySection.setSectionId(null);
			}
		} else {
			hrDutySection = new HrDutySection();
		}
		return getPathView(request).addObject("hrDutySection", hrDutySection);
	}
	
	@RequestMapping("groupEdit")
	public ModelAndView groupEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		HrDutySection hrDutySection = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrDutySection = hrDutySectionManager.get(pkId);
			if ("true".equals(forCopy)) {
				hrDutySection.setSectionId(null);
			}
		} else {
			hrDutySection = new HrDutySection();
		}
		return getPathView(request).addObject("hrDutySection", hrDutySection);
	}
		
	
	@RequestMapping("getSingleSection")
	@ResponseBody
	public List<HrDutySection> getSingleSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<HrDutySection> sections=hrDutySectionManager.getByIsGroup(MBoolean.NO.name(),ContextUtil.getCurrentTenantId());
		return sections;
	}
	
	@RequestMapping("getAllSection")
	@ResponseBody
	public List<HrDutySection> getAllSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		List<HrDutySection> sections=hrDutySectionManager.getAll(queryFilter);
		return sections;
	}
	
	@RequestMapping("getAllSingleSection")
	@ResponseBody
	public JsonPageResult<HrDutySection> getAllSingleSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		List<HrDutySection> singleSections=hrDutySectionManager.getByIsGroup(MBoolean.NO.name(),ContextUtil.getCurrentTenantId());
		JsonPageResult<HrDutySection> results=new JsonPageResult<HrDutySection>(singleSections, queryFilter.getPage().getTotalItems());
		return results;
	}
	
	@RequestMapping("getAllGroupSection")
	@ResponseBody
	public JsonPageResult<HrDutySection> getAllGroupSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		List<HrDutySection> groupSections=hrDutySectionManager.getByIsGroup(MBoolean.YES.name(),ContextUtil.getCurrentTenantId());
		List<HrDutySection> showSections=new ArrayList<HrDutySection>();
		Object obj;
		for (int i = 0; i < groupSections.size(); i++) {
			JSONObject jsonObject=JSONObject.fromObject(groupSections.get(i).getGroupList());
			JSONArray jsonArray=JSONArray.fromObject(JSONUtil.getString(jsonObject, "sections"));
			if(jsonArray.size()>1){
				for (int j = 0; j < jsonArray.size(); j++) {
					obj=jsonArray.get(j);
					if(!(obj instanceof JSONNull)){
						JSONObject tmpObject= (JSONObject) obj;
						HrDutySection hrDutySection=(HrDutySection)JSONUtil.json2Bean(tmpObject.toString(),HrDutySection.class);
						showSections.add(hrDutySection);
					}
				}
			}
			showSections.add(groupSections.get(i));
		}
		
		JsonPageResult<HrDutySection> results=new JsonPageResult<HrDutySection>(groupSections, queryFilter.getPage().getTotalItems());
		return results;
	}
	
	@RequestMapping("groupSave")
	@ResponseBody
	public JsonResult groupSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String data=request.getParameter("objData");
		HrDutySection hrDutySection=(HrDutySection)JSONUtil.json2Bean(data, HrDutySection.class,new String[]{"SUB_section"});
		String gridData=request.getParameter("gridData");
		JSONArray jsonArray=JSONArray.fromObject(gridData);
		JSONArray newJsonArray=new JSONArray();
		String sectionId;
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			sectionId=JSONUtil.getString(jo, "sectionId");
			if(StringUtils.isNotEmpty(sectionId)){
				HrDutySection tmpDutySection=hrDutySectionManager.get(sectionId);
	            newJsonArray.add(com.alibaba.fastjson.JSONObject.toJSON(tmpDutySection));
			}
		}
		String newJson="{\"sections\":"+newJsonArray.toString()+"}";
		hrDutySection.setGroupList(newJson);
		if (StringUtils.isEmpty(hrDutySection.getSectionId())) {
			hrDutySection.setSectionId(idGenerator.getSID());
			hrDutySectionManager.create(hrDutySection);  
		} else {
			HrDutySection o_section=hrDutySectionManager.get(hrDutySection.getSectionId());
			BeanUtil.copyNotNullProperties(o_section, hrDutySection);
			hrDutySectionManager.update(o_section);

		}
		return new JsonResult(true, "成功保存！");
	}
	
	@RequestMapping("getAllSectionAndRestSection")
	@ResponseBody
	public List<HrDutySection> getAllSectionAndRestSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		List<HrDutySection> sections=hrDutySectionManager.getAll(queryFilter);
		HrDutySection hrDutySection=new HrDutySection();
		hrDutySection.setSectionId("0");
		hrDutySection.setSectionName("休息日");
		hrDutySection.setSectionShortName("休");
		sections.add(hrDutySection);
		return sections;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return hrDutySectionManager;
	}

}
