package com.redxun.oa.res.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.Page;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.util.DateUtil;
import com.redxun.oa.res.entity.OaCar;
import com.redxun.oa.res.manager.OaCarManager;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * [OaCar]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaCar/")
public class OaCarController extends BaseListController{
    @Resource
    OaCarManager oaCarManager;
   
   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                oaCarManager.delete(id);
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 在新增车辆申请和编辑车辆申请时,显示所有的车辆以便选择
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getCarByDicId")
    @ResponseBody
    public List<OaCar> getCarByDicId(HttpServletRequest request,HttpServletResponse response) throws Exception{
    		String carCat=request.getParameter("sysDicId");
    		List<OaCar> oaCar = oaCarManager.getCarByDicId(carCat);
    		return oaCar;
    }
    
    /**
     * 获取空闲时间段的车辆和空闲状态的车
     * @param request
     * @param response
     * @return
     * @throws Exception 
     * */
    @RequestMapping("getTimeByStutsCar")
    @ResponseBody
    public JsonPageResult<OaCar> getTimeByStutsCar(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String startDate=request.getParameter("startTime");
    	String endDate=request.getParameter("endTime");
		Date start=DateUtil.parseDate(startDate);
		Date end=DateUtil.parseDate(endDate);
    	Page page = QueryFilterBuilder.createPage(request);
    	List<OaCar> list=oaCarManager.getByTimeOrStuts(start,end);
    	return new JsonPageResult<OaCar>(list,page.getTotalItems());
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
        OaCar oaCar=null;
        if(StringUtils.isNotEmpty(pkId)){
           oaCar=oaCarManager.get(pkId);
        }else{
        	oaCar=new OaCar();
        }
        return getPathView(request).addObject("oaCar",oaCar);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaCar oaCar=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaCar=oaCarManager.get(pkId);
    		if("true".equals(forCopy)){
    			oaCar.setCarId(null);
    		}
    	}else{
    		oaCar=new OaCar();
    	}
    	return getPathView(request).addObject("oaCar",oaCar);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaCarManager;
	}

}
