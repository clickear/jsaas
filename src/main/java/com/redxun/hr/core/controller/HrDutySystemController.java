package com.redxun.hr.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.BeanUtil;
import com.redxun.hr.core.entity.HrDutySection;
import com.redxun.hr.core.entity.HrDutySystem;
import com.redxun.hr.core.entity.HrSystemSection;
import com.redxun.hr.core.manager.HrDutySectionManager;
import com.redxun.hr.core.manager.HrDutySystemManager;
import com.redxun.hr.core.manager.HrSystemSectionManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * [HrDutySystem]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutySystem/")
public class HrDutySystemController extends BaseListController {
	@Resource
	HrDutySystemManager hrDutySystemManager;
	@Resource
	HrDutySectionManager hrDutySectionManager;
	@Resource
	HrSystemSectionManager hrSystemSectionManager;

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
				hrDutySystemManager.delete(id);
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
		HrDutySystem hrDutySystem = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrDutySystem = hrDutySystemManager.get(pkId);
		} else {
			hrDutySystem = new HrDutySystem();
		}
		return getPathView(request).addObject("hrDutySystem", hrDutySystem);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		HrDutySystem hrDutySystem = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrDutySystem = hrDutySystemManager.get(pkId);
			if ("true".equals(forCopy)) {
				hrDutySystem.setSystemId(null);
			}
		} else {
			hrDutySystem = new HrDutySystem();
		}
		return getPathView(request).addObject("hrDutySystem", hrDutySystem);
	}
	
	@RequestMapping("saveDutySystem")
	@ResponseBody
	public JsonResult saveDutySystem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String objData=request.getParameter("objData");
		HrDutySystem hrDutySystem=(HrDutySystem)JSONUtil.json2Bean(objData, HrDutySystem.class,new String[]{"SUB_periodicSection"});
		String workSection=request.getParameter("workData");
		if(StringUtils.isEmpty(hrDutySystem.getSystemId())){    //新建班制
			if(StringUtils.isNotEmpty(workSection)){      //周期班制
				JSONArray jsonArray=JSONArray.fromObject(workSection);
				hrDutySystem.setSystemId(idGenerator.getSID());
				hrDutySystemManager.create(hrDutySystem);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject=(JSONObject)jsonArray.get(i);
					HrSystemSection hrSystemSection=new HrSystemSection();
					hrSystemSection.setSystemSectionId(idGenerator.getSID());
					hrSystemSection.setWorkday(Integer.parseInt((String)JSONUtil.getString(jsonObject, "_uid")));
					hrSystemSection.setHrDutySystem(hrDutySystem);
					if("0".equals((String)JSONUtil.getString(jsonObject, "sectionId"))){
						hrSystemSectionManager.create(hrSystemSection);
					}else{	
						HrDutySection hrDutySection=hrDutySectionManager.get((String)JSONUtil.getString(jsonObject, "sectionId"));
						if(hrDutySection!=null)
							hrSystemSection.setHrDutySection(hrDutySection);
						hrSystemSectionManager.create(hrSystemSection);
					}
				}
			}
			else{              //标准一周班制
				HrSystemSection hrSystemSection=new HrSystemSection();
				hrSystemSection.setSystemSectionId(idGenerator.getSID());
				
				HrDutySection hrDutySection=hrDutySectionManager.get(hrDutySystem.getWorkSection());
				if(hrDutySection!=null)
					hrSystemSection.setHrDutySection(hrDutySection);
				
				hrDutySystem.setSystemId(idGenerator.getSID());
				hrDutySystemManager.create(hrDutySystem);
	
				hrSystemSection.setHrDutySystem(hrDutySystem);
				hrSystemSectionManager.create(hrSystemSection);
	
			}
		}
		else{ //修改班制
			HrDutySystem tmpDutySystem=hrDutySystemManager.get(hrDutySystem.getSystemId());
			if(StringUtils.isNotEmpty(workSection)){ //周期班制
				JSONArray jsonArray=JSONArray.fromObject(workSection);
				BeanUtil.copyNotNullProperties(tmpDutySystem, hrDutySystem);
				List<HrSystemSection> hrSystemSections=hrSystemSectionManager.getBySystemId(tmpDutySystem.getSystemId());
				for (int i = 0; i < hrSystemSections.size(); i++) {
					hrSystemSectionManager.delete(hrSystemSections.get(i).getSystemSectionId());
				}
						
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject=(JSONObject)jsonArray.get(i);
					HrSystemSection hrSystemSection=new HrSystemSection();
					hrSystemSection.setSystemSectionId(idGenerator.getSID());
					hrSystemSection.setWorkday(Integer.parseInt((String)JSONUtil.getString(jsonObject, "_uid")));				
					HrDutySection hrDutySection=hrDutySectionManager.get((String)JSONUtil.getString(jsonObject, "sectionId"));
					if(hrDutySection!=null)
						hrSystemSection.setHrDutySection(hrDutySection);
					hrSystemSection.setHrDutySystem(tmpDutySystem);
					hrSystemSectionManager.create(hrSystemSection);
				}
			}
			else{            // 一周班制
				BeanUtil.copyNotNullProperties(tmpDutySystem, hrDutySystem);
				List<HrSystemSection> hrSystemSections=hrSystemSectionManager.getBySystemId(tmpDutySystem.getSystemId());
				for (int i = 0; i < hrSystemSections.size(); i++) {
					hrSystemSectionManager.delete(hrSystemSections.get(i).getSystemSectionId());
				}
				
				HrSystemSection hrSystemSection=new HrSystemSection();
				hrSystemSection.setSystemSectionId(idGenerator.getSID());
				
				HrDutySection hrDutySection=hrDutySectionManager.get(tmpDutySystem.getWorkSection());
				if(hrDutySection!=null)
					hrSystemSection.setHrDutySection(hrDutySection);
	
				hrSystemSection.setHrDutySystem(tmpDutySystem);
				hrSystemSectionManager.create(hrSystemSection);
	
			}
		}
		return new JsonResult(true,"成功保存！");
	}
	
	@RequestMapping("getAllSystem")
	@ResponseBody
	public List<HrDutySystem> getAllSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		List<HrDutySystem> hrDutySystems=hrDutySystemManager.getAll(queryFilter);
		return hrDutySystems;
	}
	
	@RequestMapping("search")
	@ResponseBody
	public List<HrDutySystem> search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name=request.getParameter("name");
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		if(StringUtils.isNotEmpty(name))
			queryFilter.addLeftLikeFieldParam("name", name);
		List<HrDutySystem> hrDutySystems=hrDutySystemManager.getAll(queryFilter);
		return hrDutySystems;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return hrDutySystemManager;
	}

}
