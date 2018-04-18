package com.redxun.oa.res.controller;

import java.sql.Timestamp;
import java.util.Date;

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
import com.redxun.core.util.DateUtil;
import com.redxun.oa.res.entity.OStatus;
import com.redxun.oa.res.entity.OaCar;
import com.redxun.oa.res.entity.OaCarApp;
import com.redxun.oa.res.manager.OaCarAppManager;
import com.redxun.oa.res.manager.OaCarManager;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * [OaCarApp]管理
 * @author csx
 */
@Controller
@RequestMapping("/oa/res/oaCarApp/")
public class OaCarAppController extends BaseListController{
    @Resource
    OaCarAppManager oaCarAppManager;
    @Resource
    OaCarManager oaCarManager;
   

   
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                oaCarAppManager.delete(id);
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
        OaCarApp oaCarApp=null;
        if(StringUtils.isNotEmpty(pkId)){
           oaCarApp=oaCarAppManager.get(pkId);
        }else{
        	oaCarApp=new OaCarApp();
        }
        return getPathView(request).addObject("oaCarApp",oaCarApp);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaCarApp oaCarApp=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaCarApp=oaCarAppManager.get(pkId);
    		if("true".equals(forCopy)){
    			oaCarApp.setAppId(null);
    		}
    	}else{
    		oaCarApp=new OaCarApp();
    	}
    	return getPathView(request).addObject("oaCarApp",oaCarApp);
    }
    
    @RequestMapping("end")
    public ModelAndView end(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	OaCarApp oaCarApp=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		oaCarApp=oaCarAppManager.get(pkId);
    		if("true".equals(forCopy)){
    			oaCarApp.setAppId(null);
    		}
    	}else{
    		oaCarApp=new OaCarApp();
    	}
    	return getPathView(request).addObject("oaCarApp",oaCarApp);
    }
    /**
     * 保存延迟时间
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
     * */
	@RequestMapping("saveCarEnd")
    @ResponseBody
    public JsonResult saveCarEnd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String endTime=request.getParameter("endTime");
		String appId = request.getParameter("appId");
		OaCarApp oaCarApp=oaCarAppManager.get(appId);
		Date end=DateUtil.parseDate(endTime);
		oaCarApp.setEndTime(new Timestamp(end.getTime()));
		oaCarAppManager.saveOrUpdate(oaCarApp);
    	return new JsonResult(true,"成功延迟车辆使用时间！");
    }

	/**
	 * 页面的下一步按钮的保存和更新
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveCar")
	@ResponseBody
	public String saveCar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String appId=request.getParameter("appId");
		String carId=request.getParameter("carId");
		String carName=request.getParameter("carName");
		String carCat=request.getParameter("carCat");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		Date start=DateUtil.parseDate(startTime);
		Date end=DateUtil.parseDate(endTime);
		String driver=request.getParameter("driver");
		String category=request.getParameter("category");
		String destLoc=request.getParameter("destLoc");
		int travMiles;
		if(StringUtils.isEmpty(request.getParameter("travMiles"))){
			 travMiles=0;
		}else {
			travMiles=Integer.parseInt(request.getParameter("travMiles"));
		}
		

		String useMans=request.getParameter("useMans");
		String memo=request.getParameter("memo");
		OaCarApp oaCarApp = null;

		if (StringUtils.isEmpty(appId)) {
			oaCarApp=new OaCarApp();
			OaCar oaCar=oaCarManager.get(carId);
			oaCarApp.setAppId(idGenerator.getSID());
			oaCarApp.setCarCat(carCat);
			oaCarApp.setCarName(carName);
			oaCar.setStatus(OStatus.INUSED.name());
			oaCarApp.setOaCar(oaCar);
			oaCarApp.setStartTime(new Timestamp(start.getTime()));
			oaCarApp.setEndTime(new Timestamp(end.getTime()));
			oaCarApp.setDriver(driver);
			oaCarApp.setCategory(category);
			oaCarApp.setDestLoc(destLoc);
			oaCarApp.setTravMiles(travMiles);
			oaCarApp.setUseMans(useMans);
			oaCarApp.setMemo(memo);
			oaCarAppManager.create(oaCarApp);
		}else {
			oaCarApp=oaCarAppManager.get(appId);
			OaCar oaCar=oaCarManager.get(carId);
			oaCarApp.getOaCar().setStatus(OStatus.INFREE.name());
			oaCarApp.setCarCat(carCat);
			oaCarApp.setCarName(carName);
			oaCar.setStatus(OStatus.INUSED.name());
			oaCarApp.setOaCar(oaCar);
			oaCarApp.setStartTime(new Timestamp(start.getTime()));
			oaCarApp.setEndTime(new Timestamp(end.getTime()));
			oaCarApp.setDriver(driver);
			oaCarApp.setCategory(category);
			oaCarApp.setDestLoc(destLoc);
			oaCarApp.setTravMiles(new Integer(travMiles));
			oaCarApp.setUseMans(useMans);
			oaCarApp.setMemo(memo);
			oaCarAppManager.update(oaCarApp);
			
			
		}
		return oaCarApp.getAppId();
	}
	
	/**
	 * 修改车辆状态 根据carId修改
	 * 
	 * */
	@RequestMapping("updateStatus")
	@ResponseBody
	public JsonResult updateStatus(HttpServletRequest request) {
		String pkId = request.getParameter("ids");
		OaCarApp oaCarApp = null;
		if (StringUtils.isNotEmpty(pkId)) {
			String[] idArr = pkId.split(",");
			for (String id : idArr) {
				oaCarApp = oaCarAppManager.get(id);
				oaCarApp.getOaCar().setStatus(OStatus.INFREE.name());
				oaCarAppManager.update(oaCarApp);
			}
		} else {
			oaCarApp = new OaCarApp();
		}
		return new JsonResult(true, "成功归还车辆!");
	}
	
    
	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return oaCarAppManager;
	}

}
