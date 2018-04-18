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

import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.hr.core.entity.HrDutySection;
import com.redxun.hr.core.entity.HrSystemSection;
import com.redxun.hr.core.entity.SectionNode;
import com.redxun.hr.core.manager.HrSystemSectionManager;

/**
 * [HrSystemSection]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrSystemSection/")
public class HrSystemSectionController extends BaseListController{
    @Resource
    HrSystemSectionManager hrSystemSectionManager;
   
	@Override
	protected QueryFilter getQueryFilter(HttpServletRequest request) {
		return QueryFilterBuilder.createQueryFilter(request);
	}
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                hrSystemSectionManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 查看明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        HrSystemSection hrSystemSection=null;
        if(StringUtils.isNotEmpty(pkId)){
           hrSystemSection=hrSystemSectionManager.get(pkId);
        }else{
        	hrSystemSection=new HrSystemSection();
        }
        return getPathView(request).addObject("hrSystemSection",hrSystemSection);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	HrSystemSection hrSystemSection=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		hrSystemSection=hrSystemSectionManager.get(pkId);
    		if("true".equals(forCopy)){
    			hrSystemSection.setSystemSectionId(null);
    		}
    	}else{
    		hrSystemSection=new HrSystemSection();
    	}
    	return getPathView(request).addObject("hrSystemSection",hrSystemSection);
    }
    
    @RequestMapping("getBySystemId")
    @ResponseBody
    public List<SectionNode> getBySysTemId(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String systemId=request.getParameter("sId");
    	List<HrSystemSection> hrSystemSections= hrSystemSectionManager.getBySystemId(systemId);
    	List<SectionNode> sectionNodes=new ArrayList<SectionNode>();
    	for (int i = 0; i < hrSystemSections.size(); i++) {
			HrSystemSection tmpHrSystemSection=hrSystemSections.get(i);
			HrDutySection hrDutySection=tmpHrSystemSection.getHrDutySection();
			SectionNode sectionNode=new SectionNode();
			if(null==hrDutySection){
				sectionNode.setSectionId("0");
				sectionNode.setSectionName("休息日");
			}
			else{
				sectionNode.setSectionId(hrDutySection.getSectionId());
				sectionNode.setSectionName(hrDutySection.getSectionName());
			}
			sectionNodes.add(sectionNode);
		}
    	return sectionNodes;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return hrSystemSectionManager;
	}

}
