package com.redxun.sys.org.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.sys.log.LogEnt;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsRankType;
import com.redxun.sys.org.manager.OsDimensionManager;
import com.redxun.sys.org.manager.OsRankTypeManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 *  维度的分类管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 *
 */
@RequestMapping("/sys/org/osRankType/")
@Controller
public class OsRankTypeController extends BaseController{
	@Resource
	private OsRankTypeManager osRankTypeManager;
	@Resource
	private OsDimensionManager osDimensionManager;
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String dimId=request.getParameter("dimId");
		OsDimension osDimension=osDimensionManager.get(dimId);
		return getPathView(request).addObject("osDimension",osDimension);
	}
	/**
	 * 查找某维度下的所有等级分类列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByDimId")
	@ResponseBody
	public List<OsRankType> listByDimId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String dimId=request.getParameter("dimId");
		List<OsRankType> rankTypeList=osRankTypeManager.getByDimIdTenantId(dimId, ContextUtil.getCurrentTenantId());
		return rankTypeList;
	}
	
	@RequestMapping("saveBatch")
	@ResponseBody
	@LogEnt(action = "saveBatch", module = "组织架构", submodule = "关系类型")
	public JsonResult saveBatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String data=request.getParameter("data");
		String dimId=request.getParameter("dimId");
		JSONArray jsonArr = JSONArray.fromObject(data);
		OsRankType[] rankTypes=(OsRankType[])JSONArray.toArray(jsonArr,OsRankType.class);
		OsDimension osDimension=osDimensionManager.get(dimId);
		for(OsRankType osRankType:rankTypes){
			if(osRankType.getLevel()==null){
				continue;
			}
			osRankType.setOsDimension(osDimension);
			osRankTypeManager.saveOrUpdate(osRankType);
		}
		return new JsonResult(true,"成功保存！");
	}
	
	/**
	 * 保存行的数据
	 * @param request
	 * @param resposne
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveRow")
	@ResponseBody
	@LogEnt(action = "saveRow", module = "组织架构", submodule = "关系类型")
	public JsonResult saveRow(HttpServletRequest request,HttpServletResponse resposne) throws Exception{
		String data=request.getParameter("data");
		String dimId=request.getParameter("dimId");
		OsDimension osDimension=osDimensionManager.get(dimId);
		JSONObject jsonObj=JSONObject.fromObject(data);
		OsRankType osRankType=(OsRankType)JSONObject.toBean(jsonObj, OsRankType.class);
		osRankType.setOsDimension(osDimension);
		osRankTypeManager.saveOrUpdate(osRankType);
		return new JsonResult(true,"成功保存！",osRankType);
	}
	
	@RequestMapping("del")
	@ResponseBody
	@LogEnt(action = "del", module = "组织架构", submodule = "关系类型")
	public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ids=request.getParameter("ids");
		String[] pkIds=ids.split("[,]");
		for(String id:pkIds){
			osRankTypeManager.delete(id);
		}
		return new JsonResult(true,"成功删除！");
	}
	
}
