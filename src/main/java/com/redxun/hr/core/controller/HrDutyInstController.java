package com.redxun.hr.core.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.hr.core.entity.DutyInstRowItem;
import com.redxun.hr.core.entity.DutyInstTableHead;
import com.redxun.hr.core.entity.HrDutyInst;
import com.redxun.hr.core.entity.HrDutyInstExt;
import com.redxun.hr.core.entity.HrDutySection;
import com.redxun.hr.core.manager.HrDutyInstExtManager;
import com.redxun.hr.core.manager.HrDutyInstManager;
import com.redxun.hr.core.manager.HrDutySectionManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * [HrDutyInst]管理
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutyInst/")
public class HrDutyInstController extends BaseListController{
    @Resource
    HrDutyInstManager hrDutyInstManager;
    @Resource
    HrDutySectionManager hrDutySectionManager;
    @Resource
    HrDutyInstExtManager hrDutyInstExtManager;
   
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
                hrDutyInstManager.delete(id);
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
        HrDutyInst hrDutyInst=null;
        if(StringUtils.isNotEmpty(pkId)){
           hrDutyInst=hrDutyInstManager.get(pkId);
        }else{
        	hrDutyInst=new HrDutyInst();
        }
        return getPathView(request).addObject("hrDutyInst",hrDutyInst);
    }
    
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	HrDutyInst hrDutyInst=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		hrDutyInst=hrDutyInstManager.get(pkId);
    		if("true".equals(forCopy)){
    			hrDutyInst.setDutyInstId(null);
    		}
    	}else{
    		hrDutyInst=new HrDutyInst();
    	}
    	Set<HrDutyInstExt> hrDutyInstExts=hrDutyInst.getHrDutyInstExts(); 
    	return getPathView(request).addObject("hrDutyInst",hrDutyInst).addObject("exts", iJson.toJson(hrDutyInstExts));
    }
    
    @RequestMapping("getDutyInst")  
    @ResponseBody
    public JsonResult getDutyInst(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String date=request.getParameter("date");
    	String name=request.getParameter("name");
    	String depName=request.getParameter("dep");
    	String userId=request.getParameter("user");
    	List<DutyInstTableHead> tableDateHead=new ArrayList<DutyInstTableHead>();
    	Date now=null;
    	
    	//System.out.println("date:"+date);
    	SimpleDateFormat ymSdf=new SimpleDateFormat("yyyy-MM");
		//Date date2=;
		
		now=ymSdf.parse(date);
    		
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(sdf.parse(sdf.format(now)));
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	Date firstDate = cal.getTime();
    	
    	cal.add(Calendar.MONTH, 1);
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	Date lastDate = cal.getTime();
    	
    	cal.setTime(firstDate);
    	
    	DutyInstTableHead user=new DutyInstTableHead();
    	user.setField("userName");
    	user.setHeader("员工");
    	user.setDisplayName("userName");
    	DutyInstTableHead dep=new DutyInstTableHead();
    	dep.setField("depName");
    	dep.setHeader("部门");
    	dep.setDisplayName("depName");
    	tableDateHead.add(user);
    	tableDateHead.add(dep);

    	
    	String[] week=new String[]{"日","一","二","三","四","五","六"};
    	int num=1;
    	while (true) {
    		if (!lastDate.after(cal.getTime())&&!lastDate.equals(cal.getTime())){  
                break;  
            }
    		else{
    			DutyInstTableHead head=new DutyInstTableHead();
    			head.setField("dutyInstId"+num);
    			head.setHeader(((cal.get(Calendar.MONTH)) + 1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"<br/>"+week[cal.get(Calendar.DAY_OF_WEEK)-1]);
    			head.setDisplayName("sectionShortName"+num);
    			head.setDayOfWeek(week[cal.get(Calendar.DAY_OF_WEEK)-1]);
    			tableDateHead.add(head);
    		}
    		num++;
    		cal.add(Calendar.DAY_OF_MONTH, 1);
		}
    	Map<String, List> datas=new HashMap<String, List>();
    	datas.put("tableHead", tableDateHead);
    	
    	int dayCount=Integer.parseInt(String.valueOf((lastDate.getTime() - firstDate.getTime()) / (1000 * 60 * 60 * 24)));
    	List<HrDutyInst> allUsers=null;
    	if("true".equals(userId)){
    		allUsers=hrDutyInstManager.getByUserId(ContextUtil.getCurrentUserId());
    	}else{
    		allUsers=hrDutyInstManager.getAllDutyInstOfUserByUserNameAndDep(name, depName, ContextUtil.getCurrentTenantId());
    	}
    	List<DutyInstRowItem> rows=new ArrayList<DutyInstRowItem>();
    	for (int i = 0; i < allUsers.size(); i++) {
			List<HrDutyInst> userInsts=hrDutyInstManager.getByUserIdAndDay(((Object)allUsers.get(i)).toString(),firstDate,lastDate);		
			List<HrDutyInst> dayInsts=new ArrayList<HrDutyInst>();
			Calendar scal=Calendar.getInstance();
			scal.setTime(firstDate);
			int nowFlag=0;
			for (int j = 0; j < dayCount+1; j++) {
				HrDutyInst tmpInst =new HrDutyInst();
				if(nowFlag<userInsts.size()){
					if(scal.getTime().equals(userInsts.get(nowFlag).getDate())){
						HrDutyInst tInst=userInsts.get(nowFlag);
						if(tInst.getHrHoliday()!=null){
							tInst.setSectionName("节假日");
							tInst.setSectionShortName("节");
							tInst.setType(HrDutyInst.TYPE_HOLIDAY);
						}
						if(StringUtils.isNotEmpty(tInst.getVacApp())||StringUtils.isNotEmpty(tInst.getOtApp())||StringUtils.isNotEmpty(tInst.getTrApp())||StringUtils.isNotEmpty(tInst.getOutApp())){
							String sectionName=tInst.getSectionShortName()+"/";
							boolean isVac=false;
							boolean isOt=false;
							boolean isTr=false;
							boolean isOut=false;
							if(StringUtils.isNotEmpty(tInst.getVacApp()))
								isVac=true;
							if(StringUtils.isNotEmpty(tInst.getOtApp()))
								isOt=true;
							if(StringUtils.isNotEmpty(tInst.getTrApp()))
								isTr=true;
							if(StringUtils.isNotEmpty(tInst.getOutApp()))
								isOut=true;
							if(isVac){
								sectionName+="假";
							}
							if(isOt){
								if(isVac)
									sectionName+=",加";
								else
									sectionName+="加";
							}
							if(isTr){
								if (!isVac&&!isOt) {
									sectionName+="调";
								}
								else{
									sectionName+=",调";
								}
							}
							if(isOut){
								if (!isVac&&!isOt&&!isTr) {
									sectionName+="出";
								}
								else{
									sectionName+=",出";
								}
							}
							tInst.setSectionShortName(sectionName);	
						}
						tmpInst=tInst;
						dayInsts.add(tmpInst);
						nowFlag++;
					}
					else{
						dayInsts.add(tmpInst);
					}
				}else{
					dayInsts.add(tmpInst);
				}
				scal.add(Calendar.DAY_OF_MONTH, 1);
			}
			DutyInstRowItem row=new DutyInstRowItem();
			row.setHrDutyInsts(dayInsts);
			if(userInsts.size()>0){
				row.setUserId(userInsts.get(0).getUserId());
				row.setUserName(userInsts.get(0).getUserName());
				row.setDepId(userInsts.get(0).getDepId());
				row.setDepName(userInsts.get(0).getDepName());
				rows.add(row);
			}
			
		}
    	datas.put("tableContent", rows);
    	
    	return new JsonResult(true,"",datas);
    }
    
    @RequestMapping("updateInst")
    @ResponseBody
    public JsonResult updateInst(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	String sectionId=request.getParameter("sectionId");
    	HrDutyInst hrDutyInst=hrDutyInstManager.get(pkId);
    	Set<HrDutyInstExt> hrDutyInstExts=new HashSet<HrDutyInstExt>();
    	if("0".equals(sectionId)){
    		hrDutyInst.setSectionId(null);
    		hrDutyInst.setSectionName("休息日");
    		hrDutyInst.setSectionShortName("休");
    		hrDutyInst.setType(HrDutyInst.TYPE_REST);
    		hrDutyInstExtManager.deleteByInstId(hrDutyInst.getDutyInstId());
    		hrDutyInst.setHrDutyInstExts(null);
    		hrDutyInstManager.update(hrDutyInst);
    	}
    	else{
    		hrDutyInst.setSectionId(sectionId);
    		HrDutySection hrDutySection=hrDutySectionManager.get(sectionId);
    		hrDutyInst.setSectionName(hrDutySection.getSectionName());
    		hrDutyInst.setSectionShortName(hrDutySection.getSectionShortName());
    		hrDutyInst.setType(HrDutyInst.TYPE_DUTY);
    		if(MBoolean.YES.name().equals(hrDutySection.getIsGroup())){
    			JSONObject jsonObj=JSONObject.fromObject(hrDutySection.getGroupList());
    			JSONArray jsonArray=JSONArray.fromObject(JSONUtil.getString(jsonObj, "sections"));
    			for (int i = 0; i < jsonArray.size(); i++) {
    				JSONObject jsonObject=(JSONObject)jsonArray.get(i);
					HrDutyInstExt hrDutyInstExt=new HrDutyInstExt();
					hrDutyInstExt.setExtId(idGenerator.getSID());
					hrDutyInstExt.setHrDutyInst(hrDutyInst);
					hrDutyInstExt.setStartSignIn(Long.parseLong((String)JSONUtil.getString(jsonObject, "startSignIn")));
					hrDutyInstExt.setDutyStartTime((String)JSONUtil.getString(jsonObject, "dutyStartTime"));
					hrDutyInstExt.setEndSignIn(Long.parseLong((String)JSONUtil.getString(jsonObject, "endSignIn")));	
					hrDutyInstExt.setEarlyOffTime(Long.parseLong((String)JSONUtil.getString(jsonObject, "earlyOffTime")));
					hrDutyInstExt.setDutyEndTime((String)JSONUtil.getString(jsonObject, "dutyEndTime"));
					hrDutyInstExt.setSignOutTime(Long.parseLong((String)JSONUtil.getString(jsonObject, "signOutTime")));
					hrDutyInstExt.setSectionId((String)JSONUtil.getString(jsonObject, "sectionId"));
					hrDutyInstExts.add(hrDutyInstExt);
				}
    		}else{
    			HrDutyInstExt hrDutyInstExt=new HrDutyInstExt();
				hrDutyInstExt.setExtId(idGenerator.getSID());
				hrDutyInstExt.setHrDutyInst(hrDutyInst);
				hrDutyInstExt.setStartSignIn(hrDutySection.getStartSignIn());
				hrDutyInstExt.setDutyStartTime(hrDutySection.getDutyStartTime());
				hrDutyInstExt.setEndSignIn(hrDutySection.getEndSignIn());	
				hrDutyInstExt.setEarlyOffTime(hrDutySection.getEarlyOffTime());
				hrDutyInstExt.setDutyEndTime(hrDutySection.getDutyEndTime());
				hrDutyInstExt.setSignOutTime(hrDutySection.getSignOutTime());
				hrDutyInstExt.setSectionId(hrDutySection.getSectionId());
				hrDutyInstExts.add(hrDutyInstExt);
    		}
    		
    		hrDutyInstExtManager.deleteByInstId(hrDutyInst.getDutyInstId());
    		
    		hrDutyInst.setHrDutyInstExts(hrDutyInstExts);
    		hrDutyInstManager.update(hrDutyInst);
    	}
    	return new JsonResult(true,"成功保存");
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return hrDutyInstManager;
	}

}
