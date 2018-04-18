package com.redxun.hr.core.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.redxun.core.query.QueryFilter;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.hr.core.entity.HrDutyInst;
import com.redxun.hr.core.entity.HrDutyInstExt;
import com.redxun.hr.core.entity.HrDutyRegister;
import com.redxun.hr.core.manager.HrDutyInstManager;
import com.redxun.hr.core.manager.HrDutyRegisterManager;

/**
 * [HrDutyRegister]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/hr/core/hrDutyRegister/")
public class HrDutyRegisterController extends BaseListController {
	@Resource
	HrDutyRegisterManager hrDutyRegisterManager;
	@Resource
	HrDutyInstManager hrDutyInstManager;

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
				hrDutyRegisterManager.delete(id);
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
		HrDutyRegister hrDutyRegister = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrDutyRegister = hrDutyRegisterManager.get(pkId);
		} else {
			hrDutyRegister = new HrDutyRegister();
		}
		return getPathView(request).addObject("hrDutyRegister", hrDutyRegister);
	}

	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pkId = request.getParameter("pkId");
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		HrDutyRegister hrDutyRegister = null;
		if (StringUtils.isNotEmpty(pkId)) {
			hrDutyRegister = hrDutyRegisterManager.get(pkId);
			if ("true".equals(forCopy)) {
				hrDutyRegister.setRegisterId(null);
			}
		} else {
			hrDutyRegister = new HrDutyRegister();
		}
		return getPathView(request).addObject("hrDutyRegister", hrDutyRegister);
	}
	
	@RequestMapping("isShowButton")
	@ResponseBody
	public JsonResult isShowButton(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date fullDate=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fullSdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=sdf.parse(sdf.format(fullDate));
		HrDutyInst hrDutyInst=hrDutyInstManager.getByUserIdAndOneDay(ContextUtil.getCurrentUserId(), date);
		Calendar cal=Calendar.getInstance();
		cal.setTime(fullDate);
		boolean isShowIn=false;
		boolean isShowOut=false;
		boolean isPlan=true;
		if(hrDutyInst!=null){
			if(hrDutyInst.getHrHoliday()==null){
				Set<HrDutyInstExt> hrDutyInstExts=hrDutyInst.getHrDutyInstExts();
				if(hrDutyInstExts.size()==1){
					Iterator<HrDutyInstExt> iterator=hrDutyInstExts.iterator();
					HrDutyInstExt hrDutyInstExt=iterator.next();
					Date tmpStartTime=fullSdf.parse(sdf.format(date)+" "+hrDutyInstExt.getDutyStartTime()+":00");
					Date tmpEndTime=fullSdf.parse(sdf.format(date)+" "+hrDutyInstExt.getDutyEndTime()+":00");
					long tmp1=tmpStartTime.getTime()-hrDutyInstExt.getStartSignIn()*60*1000;
					long tmp2=tmpStartTime.getTime()+hrDutyInstExt.getEndSignIn()*60*1000;
					long tmp3=tmpEndTime.getTime()-hrDutyInstExt.getEarlyOffTime()*60*1000;
					long tmp4=tmpEndTime.getTime()+hrDutyInstExt.getSignOutTime()*60*1000;
					if(tmp1<=fullDate.getTime()&&tmp2>=fullDate.getTime()){
						HrDutyRegister hrDutyRegister=hrDutyRegisterManager.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_INFLAG);
						if(null==hrDutyRegister)
							isShowIn=true;
					}
					if(tmp3<=fullDate.getTime()&&tmp4>=fullDate.getTime()){
						HrDutyRegister hrDutyRegister=hrDutyRegisterManager.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_OFFFLAG);
						if(null==hrDutyRegister)
							isShowOut=true;
					}
				}else if(hrDutyInstExts.size()>1){
					Iterator<HrDutyInstExt> iterator=hrDutyInstExts.iterator();
					while(iterator.hasNext()){
						HrDutyInstExt hrDutyInstExt=iterator.next();
						Date tmpStartTime=fullSdf.parse(sdf.format(date)+" "+hrDutyInstExt.getDutyStartTime()+":00");
						Date tmpEndTime=fullSdf.parse(sdf.format(date)+" "+hrDutyInstExt.getDutyEndTime()+":00");
						long tmp1=tmpStartTime.getTime()-hrDutyInstExt.getStartSignIn()*60*1000;
						long tmp2=tmpStartTime.getTime()+hrDutyInstExt.getEndSignIn()*60*1000;
						long tmp3=tmpEndTime.getTime()-hrDutyInstExt.getEarlyOffTime()*60*1000;
						long tmp4=tmpEndTime.getTime()+hrDutyInstExt.getSignOutTime()*60*1000;
						if(tmp1<=fullDate.getTime()&&tmp2>=fullDate.getTime()){
							HrDutyRegister hrDutyRegister=hrDutyRegisterManager.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_INFLAG);
							if(null==hrDutyRegister)
								isShowIn=true;
						}
						if(tmp3<=fullDate.getTime()&&tmp4>=fullDate.getTime()){
							HrDutyRegister hrDutyRegister=hrDutyRegisterManager.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_OFFFLAG);
							if(null==hrDutyRegister)
								isShowOut=true;
						}
						if(isShowIn&&isShowOut)
							break;
					}
				}
			}
		}else{
			isPlan=false;
		}
		Map<String, Boolean> datas=new HashMap<String, Boolean>();
		datas.put("isShowIn", isShowIn);
		datas.put("isShowOut", isShowOut);
		datas.put("isPlan", isPlan);
		return new JsonResult(true,"", datas);
	}
	
	@RequestMapping("signIn")
	@ResponseBody
	public JsonResult signIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date fullDate=new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(fullDate);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fullSdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=sdf.parse(sdf.format(fullDate));
		HrDutyRegister hrDutyRegister=new HrDutyRegister();
		hrDutyRegister.setRegisterId(idGenerator.getSID());
		hrDutyRegister.setRegisterTime(fullDate);
		hrDutyRegister.setDayofweek((long)(cal.get(Calendar.DAY_OF_WEEK)-1));
		hrDutyRegister.setInOffFlag(HrDutyRegister.TYPE_INFLAG);
		HrDutyInst hrDutyInst=hrDutyInstManager.getByUserIdAndOneDay(ContextUtil.getCurrentUserId(), date);
		if(hrDutyInst==null)
			return new JsonResult(true, "签到失败！","你今天还未安排排班！");
		else{
			if(hrDutyInst.getHrHoliday()!=null){
				return new JsonResult(true, "签到失败！","今天是节假日！");
			}
			else{
				Set<HrDutyInstExt> hrDutyInstExts=hrDutyInst.getHrDutyInstExts();
				if(hrDutyInstExts.size()==1){
					Iterator<HrDutyInstExt> iterator=hrDutyInstExts.iterator();
					HrDutyInstExt hrDutyInstExt=iterator.next();
					HrDutyRegister register=hrDutyRegisterManager.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_INFLAG);
					if(null!=register)
						return new JsonResult(true, "你已经签到了！不能再签了！");
					Date tmp=fullSdf.parse(sdf.format(date)+" "+hrDutyInstExt.getDutyStartTime()+":00");
					long tmp2=tmp.getTime()+hrDutyInstExt.getEndSignIn()*60*1000;
					if(fullDate.getTime()>tmp2)
						return new JsonResult(true, "签到时间已过，请刷新页面！","签到时间已过，请刷新页面！");
					NumberFormat nf=new DecimalFormat("0.0"); 
					double min=Double.parseDouble(nf.format((fullDate.getTime()-tmp.getTime())/(1000*60)));
					int mins=(int)min;
					if(mins>0){
						hrDutyRegister.setRegFlag((short)2);
						hrDutyRegister.setRegMins((long)mins);
					}else{
						hrDutyRegister.setRegFlag((short)1);
						hrDutyRegister.setRegMins(0L);
					}
					hrDutyRegister.setSectionId(hrDutyInstExt.getSectionId());
				}else if(hrDutyInstExts.size()>1){
					Iterator<HrDutyInstExt> iterator=hrDutyInstExts.iterator();
					HrDutyInstExt comparedExt=null;
					while(iterator.hasNext()){
						HrDutyInstExt hrDutyInstExt=iterator.next();
						Date tmpStartTime=fullSdf.parse(sdf.format(date)+" "+hrDutyInstExt.getDutyStartTime()+":00");
						long tmp1=tmpStartTime.getTime()-hrDutyInstExt.getStartSignIn()*60*1000;
						long tmp2=tmpStartTime.getTime()+hrDutyInstExt.getEndSignIn()*60*1000;
						if(tmp1<=fullDate.getTime()&&tmp2>=fullDate.getTime()){
							comparedExt=hrDutyInstExt;
						}
					}
					if(null==comparedExt)
						return new JsonResult(true,"签到时间已过，请刷新页面！","签到时间已过，请刷新页面！");
					else{
						HrDutyRegister register=hrDutyRegisterManager.getByDateAndSectionIdAndUserIdAndInoffFlag(date, comparedExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_INFLAG);
						if(null!=register)
							return new JsonResult(true, "你已经签到了！不能再签了！");
						Date tmp=fullSdf.parse(sdf.format(date)+" "+comparedExt.getDutyStartTime()+":00");
						NumberFormat nf=new DecimalFormat("0.0"); 
						double min=Double.parseDouble(nf.format((fullDate.getTime()-tmp.getTime())/(1000*60)));
						int mins=(int)min;
						if(mins>0){
							hrDutyRegister.setRegFlag((short)2);
							hrDutyRegister.setRegMins((long)mins);
						}else{
							hrDutyRegister.setRegFlag((short)1);
							hrDutyRegister.setRegMins(0L);
						}
						hrDutyRegister.setSectionId(comparedExt.getSectionId());
					}
				}
				hrDutyRegister.setSystemId(hrDutyInst.getSystemId());
				hrDutyRegister.setDate(date);
				hrDutyRegister.setUserName(ContextUtil.getCurrentUser().getFullname());
				hrDutyRegisterManager.create(hrDutyRegister);
				return new JsonResult(true, "签到成功！");
			}
		}	
	}

	@RequestMapping("signOut")
	@ResponseBody
	public JsonResult signOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date fullDate=new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(fullDate);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fullSdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=sdf.parse(sdf.format(fullDate));
		HrDutyRegister hrDutyRegister=new HrDutyRegister();
		hrDutyRegister.setRegisterId(idGenerator.getSID());
		hrDutyRegister.setRegisterTime(fullDate);
		hrDutyRegister.setDayofweek((long)(cal.get(Calendar.DAY_OF_WEEK)-1));
		hrDutyRegister.setInOffFlag(HrDutyRegister.TYPE_OFFFLAG);
		HrDutyInst hrDutyInst=hrDutyInstManager.getByUserIdAndOneDay(ContextUtil.getCurrentUserId(), date);
		if(hrDutyInst==null)  //没有排班
			return new JsonResult(true, "签退失败！","你今天还未安排排班！");
		else{
			if(hrDutyInst.getHrHoliday()!=null){    //有节假日
				return new JsonResult(true, "签退失败！","今天是节假日！");
			}
			else{
				Set<HrDutyInstExt> hrDutyInstExts=hrDutyInst.getHrDutyInstExts();
				if(hrDutyInstExts.size()==1){
					Iterator<HrDutyInstExt> iterator=hrDutyInstExts.iterator();
					HrDutyInstExt hrDutyInstExt=iterator.next();
					HrDutyRegister register=hrDutyRegisterManager.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_OFFFLAG);
					if(null!=register)
						return new JsonResult(true, "你已经签退了！不能再签了！");
					Date tmp=fullSdf.parse(sdf.format(date)+" "+hrDutyInstExt.getDutyEndTime()+":00");
					long tmp2=tmp.getTime()+hrDutyInstExt.getSignOutTime()*60*1000;
					if(fullDate.getTime()>tmp2)
						return new JsonResult(true, "签退时间已过，请刷新页面！","签退时间已过，请刷新页面！");
					NumberFormat nf=new DecimalFormat("0.0"); 
					double min=Double.parseDouble(nf.format((fullDate.getTime()-tmp.getTime())/(1000*60)));
					int mins=(int)min;
					if(mins<0){
						hrDutyRegister.setRegFlag((short)3);
						hrDutyRegister.setRegMins((long)(Math.abs(mins)));
					}else{
						hrDutyRegister.setRegFlag((short)1);
						hrDutyRegister.setRegMins(0L);
					}
					hrDutyRegister.setSectionId(hrDutyInstExt.getSectionId());
				}else if(hrDutyInstExts.size()>1){
					Iterator<HrDutyInstExt> iterator=hrDutyInstExts.iterator();
					HrDutyInstExt comparedExt=null;
					while(iterator.hasNext()){
						HrDutyInstExt hrDutyInstExt=iterator.next();
						Date tmpEndTime=fullSdf.parse(sdf.format(date)+" "+hrDutyInstExt.getDutyEndTime()+":00");
						long tmp1=tmpEndTime.getTime()-hrDutyInstExt.getEarlyOffTime()*60*1000;
						long tmp2=tmpEndTime.getTime()+hrDutyInstExt.getSignOutTime()*60*1000;
						if(tmp1<=fullDate.getTime()&&tmp2>=fullDate.getTime()){
							comparedExt=hrDutyInstExt;
						}
					}
					if(null==comparedExt)
						return new JsonResult(true,"签退时间已过，请刷新页面！","签退时间已过，请刷新页面！");
					else{
						HrDutyRegister register=hrDutyRegisterManager.getByDateAndSectionIdAndUserIdAndInoffFlag(date, comparedExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_OFFFLAG);
						if(null!=register)
							return new JsonResult(true, "你已经签退了！不能再签了！");
						Date tmp=fullSdf.parse(sdf.format(date)+" "+comparedExt.getDutyEndTime()+":00");
						NumberFormat nf=new DecimalFormat("0.0"); 
						double min=Double.parseDouble(nf.format((fullDate.getTime()-tmp.getTime())/(1000*60)));
						int mins=(int)min;
						if(mins<0){
							hrDutyRegister.setRegFlag((short)3);
							hrDutyRegister.setRegMins((long)(Math.abs(mins)));
						}else{
							hrDutyRegister.setRegFlag((short)1);
							hrDutyRegister.setRegMins(0L);
						}
						hrDutyRegister.setSectionId(comparedExt.getSectionId());
					}
				}
				hrDutyRegister.setSystemId(hrDutyInst.getSystemId());
				hrDutyRegister.setDate(date);
				hrDutyRegister.setUserName(ContextUtil.getCurrentUser().getFullname());
				hrDutyRegisterManager.create(hrDutyRegister);
				return new JsonResult(true, "签退成功！");
			}
		}	
	}
	
	@RequestMapping("getAllRegister")
	@ResponseBody
	public JsonPageResult<HrDutyRegister> getAllRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("tenantId", ContextUtil.getCurrentTenantId());
		List<HrDutyRegister> hrDutyRegisters=hrDutyRegisterManager.getAll(queryFilter);
		return new JsonPageResult<HrDutyRegister>(hrDutyRegisters,queryFilter.getPage().getTotalItems());
	}
	
	@RequestMapping("getRegisterByUserId")
	@ResponseBody
	public List<HrDutyRegister> getRegisterByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter=QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("createBy", ContextUtil.getCurrentUserId());
		List<HrDutyRegister> hrDutyRegisters=hrDutyRegisterManager.getAll(queryFilter);
		return hrDutyRegisters;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return hrDutyRegisterManager;
	}

}
