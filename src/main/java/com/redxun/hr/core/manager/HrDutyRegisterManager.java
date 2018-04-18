package com.redxun.hr.core.manager;

import java.text.DateFormat;
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

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.redxun.core.dao.IDao;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.hr.core.dao.HrDutyRegisterDao;
import com.redxun.hr.core.entity.HrDutyInst;
import com.redxun.hr.core.entity.HrDutyInstExt;
import com.redxun.hr.core.entity.HrDutyRegister;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.core.util.SysPropertiesUtil;

/**
 * <pre>
 *  
 * 描述：HrDutyRegister业务服务类
 * 构建组：miweb
 * 作者：keith
 * 邮箱: keith@redxun.cn
 * 日期:2014-2-1-上午12:52:41
 * 版权：广州红迅软件有限公司版权所有
 * </pre>
 */
@Service
public class HrDutyRegisterManager extends BaseManager<HrDutyRegister> {
	@Resource
	private HrDutyRegisterDao hrDutyRegisterDao;
	@Resource
	private HrDutyInstManager hrDutyInstManager;
	
	
	public HrDutyRegister getByDateAndSectionIdAndUserIdAndInoffFlag(Date date,String sectionId,String userId,String inOffFlag){
		return hrDutyRegisterDao.getByDateAndSectionIdAndUserIdAndInoffFlag(date, sectionId, userId,inOffFlag);
	}
	
	public JsonResult isShowSignButton() throws Exception{
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
						HrDutyRegister hrDutyRegister=this.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_INFLAG);
						if(null==hrDutyRegister)
							isShowIn=true;
					}
					if(tmp3<=fullDate.getTime()&&tmp4>=fullDate.getTime()){
						HrDutyRegister hrDutyRegister=this.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_OFFFLAG);
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
							HrDutyRegister hrDutyRegister=this.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_INFLAG);
							if(null==hrDutyRegister)
								isShowIn=true;
						}
						if(tmp3<=fullDate.getTime()&&tmp4>=fullDate.getTime()){
							HrDutyRegister hrDutyRegister=this.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_OFFFLAG);
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
	
	public JsonResult doSignInService(JSONObject addressInfo) throws Exception{
		
		//经纬度
		String longitude = addressInfo.getString("longitude");
		String latitude = addressInfo.getString("latitude");
		//距离
		Integer distance = addressInfo.getInteger("distance");
		String addressDetails = addressInfo.getString("address");
		String signRemark = addressInfo.getString("signRemark");
		//系统参数设定距离
		Integer signLimitDistance = SysPropertiesUtil.getGlobalPropertyInt("oa.signDistance");		
		JSONObject rsJson = new JSONObject();		
		rsJson.put("outOfRange", distance>signLimitDistance);
		
		
		
		Date fullDate=new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(fullDate);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fullSdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=sdf.parse(sdf.format(fullDate));
		HrDutyRegister hrDutyRegister=new HrDutyRegister();
		hrDutyRegister.setRegisterId(IdUtil.getId());
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
					HrDutyRegister register=this.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_INFLAG);
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
						HrDutyRegister register=this.getByDateAndSectionIdAndUserIdAndInoffFlag(date, comparedExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_INFLAG);
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
				hrDutyRegister.setLongitude(Double.parseDouble(longitude));
				hrDutyRegister.setLatitude(Double.parseDouble(latitude));
				hrDutyRegister.setAddresses(addressDetails);
				hrDutyRegister.setSignRemark(signRemark);
				hrDutyRegister.setDistance(distance);
				this.create(hrDutyRegister);
				return new JsonResult(true, "签到成功！",rsJson);
			}
		}
	}
	
	public JsonResult doSignOutService(JSONObject addressInfo) throws Exception{
		//经纬度
		String longitude = addressInfo.getString("longitude");
		String latitude = addressInfo.getString("latitude");
		//地址
		String addressDetails = addressInfo.getString("addresses");
		String signRemark = addressInfo.getString("signRemark");
		
		Date fullDate=new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(fullDate);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fullSdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=sdf.parse(sdf.format(fullDate));
		HrDutyRegister hrDutyRegister=new HrDutyRegister();
		hrDutyRegister.setRegisterId(IdUtil.getId());
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
					HrDutyRegister register=this.getByDateAndSectionIdAndUserIdAndInoffFlag(date, hrDutyInstExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_OFFFLAG);
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
						HrDutyRegister register=this.getByDateAndSectionIdAndUserIdAndInoffFlag(date, comparedExt.getSectionId(), ContextUtil.getCurrentUserId(), HrDutyRegister.TYPE_OFFFLAG);
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
				hrDutyRegister.setLongitude(Double.parseDouble(longitude));
				hrDutyRegister.setLatitude(Double.parseDouble(latitude));
				hrDutyRegister.setAddresses(addressDetails);
				hrDutyRegister.setSignRemark(signRemark);
				this.create(hrDutyRegister);
				return new JsonResult(true, "签退成功！");
			}
		}	
	}
	
	public List<HrDutyRegister> getSignHistory(String date) throws Exception{
		QueryFilter queryFilter = new QueryFilter();
		queryFilter.addFieldParam("createBy", ContextUtil.getCurrentUser().getUserId());
		queryFilter.addFieldParam("date", DateFormat.getDateInstance().parse(date));
		List<HrDutyRegister> list = hrDutyRegisterDao.getAll(queryFilter);
		return list;
		
	}
	
	//当前用户当月所有签到数据
	public List<HrDutyRegister> getUserMonthSignRecord(String date) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateFormat.getDateInstance().parse(date));
		int month = calendar.get(Calendar.MONTH) + 1 ;
		List<HrDutyRegister> list = hrDutyRegisterDao.getUserMonthSignRecord(month, ContextUtil.getCurrentUser().getUserId());
		return list;
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	protected IDao getDao() {
		return hrDutyRegisterDao;
	}
}