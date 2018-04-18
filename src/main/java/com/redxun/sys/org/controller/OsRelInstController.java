package com.redxun.sys.org.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.constants.MStatus;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsGroup;
import com.redxun.sys.org.entity.OsRelInst;
import com.redxun.sys.org.entity.OsRelType;
import com.redxun.sys.org.entity.OsUser;
import com.redxun.sys.org.entity.RelationLine;
import com.redxun.sys.org.entity.ReportLine;
import com.redxun.sys.org.manager.OsGroupManager;
import com.redxun.sys.org.manager.OsRelInstManager;
import com.redxun.sys.org.manager.OsRelTypeManager;
import com.redxun.sys.org.manager.OsUserManager;
/**
 * 关系实例控制器
 * @author mansan
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/org/osRelInst/")
public class OsRelInstController extends BaseController{
	@Resource
	OsRelInstManager osRelInstManager;
	@Resource
	OsUserManager osUserManager;
	
	@Resource
	OsGroupManager osGroupManager;
	
	@Resource
	OsRelTypeManager osRelTypeManager;
	
	/**
	 * 显示某个汇报线
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("treeLine")
	public ModelAndView treeLine(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String relTypeId=request.getParameter("relTypeId");
		OsRelType osRelType=osRelTypeManager.get(relTypeId);
		return getPathView(request).addObject("osRelType", osRelType);
	}
	/**
	 * 按关系类型Id显示关系线实例
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByRelTypeId")
	@ResponseBody
	public List<RelationLine> listByRelTypeId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String relTypeId=request.getParameter("relTypeId");
		OsRelType osRelType=osRelTypeManager.get(relTypeId);
		List<RelationLine> lines=new ArrayList<RelationLine>();
		RelationLine rootLine=new RelationLine("0",osRelType.getName(),"-1");
		
		lines.add(rootLine);
		List<OsRelInst> osRelInsts=osRelInstManager.getByRelTypeIdTenantId(relTypeId, ContextUtil.getCurrentTenantId());
		
		for(OsRelInst inst:osRelInsts){
			if(OsRelType.REL_TYPE_GROUP_GROUP.equals(osRelType.getRelType())){
				OsGroup osGroup=osGroupManager.get(inst.getParty2());
				if(osGroup!=null){
					RelationLine line=new RelationLine(osGroup.getGroupId(), osGroup.getName()+"("+osGroup.getKey()+")", inst.getParty1());
					line.setRelationId(inst.getInstId());
					line.setIconCls("icon-group");
					lines.add(line);	
				}
			}else if(OsRelType.REL_TYPE_USER_USER.equals(osRelType.getRelType())){
				OsUser osUser=osUserManager.get(inst.getParty2());
				if(osUser!=null){
					RelationLine line=new RelationLine(osUser.getUserId(),osUser.getFullname()+"("+osUser.getUserNo()+")",inst.getParty1());
					line.setRelationId(inst.getInstId());
					line.setIconCls("icon-user");
					lines.add(line);
				}
			}
		}
		return lines;
	}
	
	/**
	 * 显示汇报线
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getReportLines")
	@ResponseBody
	public ReportLine getReportLines(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String typeId=request.getParameter("typeId");
		OsRelType osRelType=osRelTypeManager.get(typeId);
		ReportLine reportLine=null;
		if(OsRelType.REL_TYPE_GROUP_USER.equals(osRelType.getRelType())){
			reportLine=new ReportLine("0",osRelType.getName(),osRelType.getName()+"关系定义");
			reportLine.setClassName("top-level");
			reportLine.setInstId("0");
			//TODO
		}else {
			reportLine=new ReportLine("0",osRelType.getName(),osRelType.getName()+"关系定义");
			reportLine.setClassName("top-level");
			genReportLines(reportLine,osRelType);
		}

		return reportLine;
	}
	
	private void genReportLines(ReportLine reportLine,OsRelType relType){
		List<OsRelInst> insts=osRelInstManager.getByRelTypeIdParty1(relType.getId(), reportLine.getId());
		for(OsRelInst inst:insts){
			if(StringUtils.isEmpty(inst.getParty2())){
				continue;
			}
			if(OsRelType.REL_TYPE_USER_USER.equals(relType.getRelType())){
				OsUser osUser=osUserManager.get(inst.getParty2());
				ReportLine rl=new ReportLine(osUser.getUserId(),osUser.getFullname(),osUser.getUserNo());
				rl.setInstId(inst.getInstId());
				rl.setClassName("top-level");
				reportLine.getChildren().add(rl);
				genReportLines(rl,relType);
			}else if(OsRelType.REL_TYPE_GROUP_GROUP.equals(relType.getRelType())){
				OsGroup osGroup=osGroupManager.get(inst.getParty2());
				ReportLine rl=new ReportLine(osGroup.getGroupId(),osGroup.getName(),osGroup.getName());
				rl.setClassName("top-level");
				rl.setInstId(inst.getInstId());
				reportLine.getChildren().add(rl);
				//递归往下查找所有关系
				genReportLines(rl,relType);
			}
		}
	}
	
	/**
	 * 删除汇报实例
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delInst")
	@ResponseBody
	public JsonResult delInst(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String instId=request.getParameter("instId");
		
		OsRelInst inst=osRelInstManager.get(instId);

		osRelInstManager.delInstCascade(inst);

		return new JsonResult(true,"成功删除!");
	}
	
	
	
	@RequestMapping("saveRelInst")
	@ResponseBody
	@LogEnt(action = "saveRelInst", module = "组织架构", submodule = "关系实例")
	public JsonResult saveRelInst(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String typeId=request.getParameter("typeId");
		OsRelType osRelType=osRelTypeManager.get(typeId);
		String userId=request.getParameter("userId");
		String groupId=request.getParameter("groupId");
		

		OsRelInst inst=new OsRelInst();
		if(osRelType!=null &&  OsRelType.REL_TYPE_USER_USER.equals(osRelType.getRelType())){
			inst.setParty2(userId);
		}else{
			inst.setParty2(groupId);
		}
		inst.setParty1("0");
		
		inst.setIsMain(MBoolean.NO.name());
		inst.setStatus(MStatus.ENABLED.name());
		inst.setOsRelType(osRelType);
		inst.setRelTypeKey(osRelType.getKey());
		inst.setRelType(osRelType.getRelType());
		
		//查找是否已经存在根，若存在，不允许创建
		if("0".equals(inst.getParty1())){
			OsRelInst rootInst=osRelInstManager.getByRootInstByTypeId(typeId);
			if(rootInst!=null){
				return new JsonResult(false,"该关系根节点已经存在！");
			}
		}
		OsRelInst osRelInst= osRelInstManager.getByParty1Party2RelTypeId(inst.getParty1(),inst.getParty2(),typeId);
		if(osRelInst!=null){
			return new JsonResult(false,"该关系节点已经存在！");
		}
		
		osRelInstManager.create(inst);
		
		return new JsonResult(true,"成功创建了关系节点！");
	}
	
	/**
	 * 更改当前关系实例为另一用户或组
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateRelation")
	@ResponseBody
	@LogEnt(action = "updateRelation", module = "组织架构", submodule = "关系实例")
	public JsonResult updateRelation(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String userId=request.getParameter("userId");
		String groupId=request.getParameter("groupId");
		String instId=request.getParameter("instId");
		OsRelInst osRelInst=osRelInstManager.get(instId);
		if(StringUtils.isNotEmpty(userId)){
			boolean isExist=osRelInstManager.isPartyExistInRelation(osRelInst.getOsRelType().getId(), userId);
			if(isExist){
				return new JsonResult(true,"你选择的用户在该关系中已经存在，请先移除该用户再更改！");
			}
		}else{
			boolean isExist=osRelInstManager.isPartyExistInRelation(osRelInst.getOsRelType().getId(), groupId);
			if(isExist){
				return new JsonResult(true,"你选择的用户组在该关系中已经存在，请先移除该用户组再更改！");
			}
		}
		//if(osRelInst)
		osRelInstManager.doChangeInst(instId,userId,groupId);
		
		return new JsonResult(true,"成功更改！");
	}
	
	@RequestMapping("saveRelInsts")
	@ResponseBody
	@LogEnt(action = "saveRelInsts", module = "组织架构", submodule = "关系实例")
	public JsonResult saveRelInsts(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String typeId=request.getParameter("typeId");
		OsRelType osRelType=osRelTypeManager.get(typeId);
		String userIds=request.getParameter("userIds");
		String groupIds=request.getParameter("groupIds");
		String instId=request.getParameter("instId");

		String[]uIds=null;
		if(StringUtils.isNotEmpty(userIds)){
			uIds=userIds.split("[,]");
		}else{
			uIds=groupIds.split("[,]");
		}
		
		for(String uId:uIds){
			OsRelInst inst=new OsRelInst();
			
			inst.setParty2(uId);
			
			if(StringUtils.isEmpty(instId)){
				inst.setParty1("0");
				inst.setPath("0."+uId+".");
			}else{
				OsRelInst parentInst=osRelInstManager.get(instId);
				//判断当前关系方是否已经出现在上级的里，以使关系不要形成闭环.自身不跟自身建立关系
				boolean isExist=isExistInParent(parentInst.getParty2(),uId,osRelType);
				if(isExist){
					continue;
				}
				inst.setParty1(parentInst.getParty2());
				inst.setPath(parentInst.getPath() +uId+ ".");
			}
			inst.setIsMain(MBoolean.NO.name());
			inst.setStatus(MStatus.ENABLED.name());
			inst.setOsRelType(osRelType);
			inst.setRelTypeKey(osRelType.getKey());
			inst.setRelType(osRelType.getRelType());
			
			//查找该关系是否已经存在了
			OsRelInst osRelInst= osRelInstManager.getByParty1Party2RelTypeId(inst.getParty1(),inst.getParty2(),typeId);
			if(osRelInst!=null){
				continue;
			}
			osRelInstManager.create(inst);
		}
		
		return new JsonResult(true,"成功创建了关系节点！");
	}
	/**
	 * 检查Party2其关系是否存在
	 * @param party1
	 * @param party2
	 * @param osRelType
	 */
	private boolean isExistInParent(String party1,String party2,OsRelType osRelType){
		if(party2.equals(party1)){
			return true;
		}
		List<OsRelInst> osRelInsts= osRelInstManager.getByRelTypeIdParty2(osRelType.getId(),party1);
		
		for(OsRelInst is:osRelInsts){
			if(is.getParty1().equals(party2)){
				return true;
			}
			boolean isExist=isExistInParent(is.getParty1(), party2, osRelType);
			if(isExist){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 显示某种关系的定义
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("line")
	public ModelAndView line(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String typeId=request.getParameter("typeId");
		OsRelType osRelType=osRelTypeManager.get(typeId);
		return getPathView(request).addObject("osRelType", osRelType);
	}
	
	/**
	 * 获得用户组关系内的用户数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("groupRelUsersData")
	@ResponseBody
	public JsonPageResult<OsUser> groupRelUsersData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获得用户组1的Id
		String p1=request.getParameter("p1");
		//获得用户组2的Id
		String p2=request.getParameter("p2");
		
		//TODO
		//主用户列表
		List<OsUser> mainUsers=osUserManager.getBelongUsers(p1);
		//从用户列表
		List<OsUser> subUsers=osUserManager.getBelongUsers(p2);
		//获得同时在两个组内的用户
		mainUsers.retainAll(subUsers);
		
		return new JsonPageResult<OsUser>(mainUsers, mainUsers.size());
	}
	
	/**
	 * 移除用户与两个用户组的从属关系
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delByTwoPartyUserId")
	@ResponseBody
	@LogEnt(action = "delByTwoPartyUserId", module = "组织架构", submodule = "关系实例")
	public JsonResult delByTowPartyUserId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String p1=request.getParameter("p1");
		String p2=request.getParameter("p2");
		String userId=request.getParameter("userId");
		
		osRelInstManager.delByGroupIdUserIdRelTypeId(p1,userId,OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
		osRelInstManager.delByGroupIdUserIdRelTypeId(p2,userId,OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
		
		return new JsonResult(true,"成功移除关系！");
			
	}
	
	/**
	 * 把多个用户同时加至两个组内
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("joinTwoGroupUsers")
	@ResponseBody
	@LogEnt(action = "joinTwoGroupUsers", module = "组织架构", submodule = "关系实例")
	public JsonResult joinTwoGroupUsers(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String p1=request.getParameter("p1");
		String p2=request.getParameter("p2");
		String userIds=request.getParameter("userIds");
		
		String[]uIds=userIds.split("[,]");
		
		OsGroup mainGroup=osGroupManager.get(p1);
		String dimId1=null;
		if(mainGroup!=null&& mainGroup.getOsDimension()!=null){
			dimId1=mainGroup.getOsDimension().getDimId();
		}
		OsGroup subGroup=osGroupManager.get(p2);
		
		String dimId2=null;
		if(subGroup!=null&& subGroup.getOsDimension()!=null){
			dimId1=subGroup.getOsDimension().getDimId();
		}
		
		OsRelType belongRelType=osRelTypeManager.get(OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
		for(String uId:uIds){
			OsRelInst osRelInst=osRelInstManager.getByParty1Party2RelTypeId(p1, uId, OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
			if(osRelInst==null){
				OsRelInst inst=new OsRelInst();
				inst.setParty1(p1);
				inst.setParty2(uId);
				inst.setIsMain(MBoolean.NO.name());
				inst.setStatus(MStatus.ENABLED.name());
				inst.setOsRelType(belongRelType);
				inst.setRelTypeKey(belongRelType.getKey());
				inst.setRelType(belongRelType.getRelType());
				inst.setDim1(dimId1);
				osRelInstManager.create(inst);
			}
			
			OsRelInst osRelInst2=osRelInstManager.getByParty1Party2RelTypeId(p2, uId, OsRelType.REL_CAT_GROUP_USER_BELONG_ID);
			if(osRelInst2==null){
				OsRelInst inst=new OsRelInst();
				inst.setParty1(p2);
				inst.setParty2(uId);
				inst.setIsMain(MBoolean.NO.name());
				inst.setStatus(MStatus.ENABLED.name());
				inst.setOsRelType(belongRelType);
				inst.setRelTypeKey(belongRelType.getKey());
				inst.setRelType(belongRelType.getRelType());
				inst.setDim1(dimId2);
				osRelInstManager.create(inst);
			}
		}
		
		return new JsonResult(true,"成功加入用户！");
	}
	
	/**
	 * 获得用户的从属关系以外的关系实例
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByUserIdExcludeBelong")
	@ResponseBody
	@LogEnt(action = "listByUserIdExcludeBelong", module = "组织架构", submodule = "关系实例")
	public List<OsRelInst> listByUserIdExcludeBelong(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userId=request.getParameter("userId");
		List<OsRelInst> relInsts=osRelInstManager.getUserOtherRelInsts(userId);
		
		for(OsRelInst inst:relInsts){
			if(StringUtils.isNotEmpty(inst.getParty1()) && inst.getRelTypeKey()!=null && 
					 inst.getRelTypeKey().indexOf(OsRelType.REL_TYPE_GROUP_USER)!=-1){
				OsGroup osGroup=osGroupManager.get(inst.getParty1());
				if(osGroup!=null){
					inst.setPartyName1(osGroup.getName());
				}
				OsUser osUser=osUserManager.get(inst.getParty2());
				if(osUser!=null){
					inst.setPartyName2(osUser.getFullname());
				}
			}else if(StringUtils.isNotEmpty(inst.getParty1())  && 
					OsRelType.REL_TYPE_USER_USER.equals(inst.getRelType())){
				OsUser osUser=osUserManager.get(inst.getParty1());
				if(osUser!=null){
					inst.setPartyName1(osUser.getFullname());
				}
				OsUser osUser2=osUserManager.get(inst.getParty2());
				if(osUser2!=null){
					inst.setPartyName2(osUser2.getFullname());
				}
			}
		}
		
		return relInsts;
	}
	/**
	 * 删除关系实例
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	
	public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String instId=request.getParameter("instId");
		osRelInstManager.delete(instId);
		return new JsonResult(true,"成功删除了关系实例!");
	}
}
