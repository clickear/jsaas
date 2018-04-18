package com.redxun.oa.info.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mysql.fabric.xmlrpc.base.Array;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.oa.info.entity.InsColumn;
import com.redxun.oa.info.entity.InsPortCol;
import com.redxun.oa.info.entity.InsPortal;
import com.redxun.oa.info.manager.InsColumnManager;
import com.redxun.oa.info.manager.InsPortColManager;
import com.redxun.oa.info.manager.InsPortalManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * [InsPortal]管理
 * 
 * @author csx
 */
@Controller
@RequestMapping("/oa/info/insPortal/")
public class InsPortalFormController extends BaseFormController {

	@Resource
	private InsPortalManager insPortalManager;
	@Resource
	private InsPortColManager insPortColManager;
	@Resource
	private InsColumnManager insColumnManager;

	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("insPortal")
	public InsPortal processForm(HttpServletRequest request) {
		String portId = request.getParameter("portId");
		InsPortal insPortal = null;
		if (StringUtils.isNotEmpty(portId)) {
			insPortal = insPortalManager.get(portId);
		} else {
			insPortal = new InsPortal();
		}
		return insPortal;
	}

	/**
	 * 在改变栏目位置时的保存，ShowEdit和PersonShowEdit页面的保存
	 * 
	 * @param request
	 * @param insPortal
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "savePortal", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult savePortal(HttpServletRequest request, @ModelAttribute("insPortal") @Valid InsPortal insPortal, BindingResult result) {

		InsPortal insPortal1 = insPortal;// 将原有门户复制一遍
		String tenantId = ContextUtil.getCurrentTenantId();
		String userId = ContextUtil.getCurrentUserId();
		String key = insPortal.getKey();
		if (key.equals("GLOBAL-COMPANY") || key.equals("COMPANY")) {// 判断是公司还是个人门户
			// 判断是否为全局公司，如果是则新建一个COMPANY门户，并把GLOBAL-COMPANY的数据复制到COMPANY，然后用COMPANY门户进行后面的操作
			if (key.equals("GLOBAL-COMPANY")) {
				insPortal = new InsPortal();
				insPortal.setPortId(idGenerator.getSID());
				insPortal.setKey("COMPANY");
				insPortal.setName("公司");
				insPortal.setIsDefault("NO");
				insPortal.setTenantId(tenantId);
				insPortal.setColNums(insPortal1.getColNums());
				insPortal.setColWidths(insPortal1.getColWidths());
				insPortalManager.create(insPortal);
			}
		} else {
			// 判断是否为全局个人，如果是则新建一个PERSONAL门户，并把GLOBAL-PERSONAL的数据复制到PERSONAL，然后用PERSONAL门户进行后面的操作
			if (key.equals("GLOBAL-PERSONAL")) {
				insPortal = new InsPortal();
				insPortal.setPortId(idGenerator.getSID());
				insPortal.setKey("PERSONAL");
				insPortal.setName("个人");
				insPortal.setIsDefault("NO");
				insPortal.setUserId(userId);
				insPortal.setColNums(insPortal1.getColNums());
				insPortal.setColWidths(insPortal1.getColWidths());
				insPortalManager.create(insPortal);
			}
		}
		String msg = null;
		String pans = request.getParameter("data");// 前台传来的所有栏目位置高度等数据
		JSONArray array = JSONArray.fromObject(pans);
		for (int i = 0; i < array.size(); i++) {
			JSONObject a = array.getJSONObject(i);// 获得每个栏目的数据
			String height = a.getString("height").substring(0, a.getString("height").length() - 2);// 获得每个栏目的高度数据，因为传来的是300px，存入数据需要截去px
			InsColumn insColumn = insColumnManager.get(a.getString("id"));// 获得栏目
			InsPortCol insPortCol = insPortColManager.getByPortCol(insPortal.getPortId(), insColumn.getColId());// 查找门户原本是否有这个栏目的中间表数据
			if (insPortCol != null) {// 如果有则更新中间表
				InsPortCol insPC = insPortCol;
				insPC.setSn(a.getInt("sn"));
				insPC.setColNum(a.getInt("column"));
				insPC.setHeight(Integer.parseInt(height));
				insPortColManager.update(insPC);
			} else {// 如果没有则新建中间表
				InsPortCol insPC = new InsPortCol();
				insPC.setSn(a.getInt("sn"));
				insPC.setColNum(a.getInt("column"));
				insPC.setHeight(Integer.parseInt(height));
				insPC.setHeightUnit("px");
				insPC.setInsColumn(insColumn);
				insPC.setInsPortal(insPortal);
				insPC.setConfId(idGenerator.getSID());
				insPortColManager.create(insPC);
				//insPortColManager.flush();
				insPortal.getInsPortCols().add(insPC);
			}

		}
		insPortalManager.saveOrUpdate(insPortal);
		return new JsonResult(true, msg);
	}

	/**
	 * 在改变栏目位置时的保存，Global页面的保存
	 * 
	 * @param request
	 * @param insPortal
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "saveGlobal", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult saveGlobal(HttpServletRequest request, @ModelAttribute("insPortal") @Valid InsPortal insPortal, BindingResult result) {

		String msg = null;
		String pans = request.getParameter("data");// 前台传来的所有栏目位置高度等数据
		JSONArray array = JSONArray.fromObject(pans);
		for (int i = 0; i < array.size(); i++) {
			JSONObject a = array.getJSONObject(i);// 获得每个栏目的数据
			String height = a.getString("height").substring(0, a.getString("height").length() - 2);// 获得每个栏目的高度数据，因为传来的是300px，存入数据需要截去px
			InsColumn insColumn = insColumnManager.get(a.getString("id"));// 获得栏目
			InsPortCol insPortCol = insPortColManager.getByPortCol(insPortal.getPortId(), insColumn.getColId());// 查找门户原本是否有这个栏目的中间表数据
			if (insPortCol != null) {// 如果有则更新中间表
				InsPortCol insPC = insPortCol;
				insPC.setSn(a.getInt("sn"));
				insPC.setColNum(a.getInt("column"));
				insPC.setHeight(Integer.parseInt(height));
				insPortColManager.update(insPC);
			} else {// 如果没有则新建中间表
				InsPortCol insPC = new InsPortCol();
				insPC.setSn(a.getInt("sn"));
				insPC.setColNum(a.getInt("column"));
				insPC.setHeight(Integer.parseInt(height));
				insPC.setHeightUnit("px");
				insPC.setInsColumn(insColumn);
				insPC.setInsPortal(insPortal);
				insPC.setConfId(idGenerator.getSID());
				insPortColManager.create(insPC);
				insPortal.getInsPortCols().add(insPC);
			}

		}
		insPortalManager.saveOrUpdate(insPortal);
		msg = getMessage("insPortal.updated", new Object[] { insPortal.getName() }, "门户成功更新!");
		return new JsonResult(true, msg);
	}

	/**
	 * 点击编辑门户时的保存实体数据，PortalEdit页面的保存
	 * 
	 * @param request
	 * @param insPortal
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, @ModelAttribute("insPortal") @Valid InsPortal insPortal, BindingResult result) {

		if (result.hasFieldErrors()) {
			return new JsonResult(false, getErrorMsg(result));
		}
		String msg = null;
		insPortal.getInsPortCols().clear();
		String column = request.getParameter("insPortCols");// 前台获取的该门户的所有中间表的ID
		// 新门户保存
		/*if (StringUtils.isEmpty(insPortal.getPortId())) {
			insPortal.setPortId(idGenerator.getSID());// 生成Portal门户Id
			if (StringUtils.isNotEmpty(column)) {
				String[] columns = column.split(",");// 获得每一个中间表的ID
				for (int i = 0; i < columns.length; i++) {// 生成中间表实体，并放入Portal的set集合中
					InsPortCol portcol = new InsPortCol();
					portcol.setConfId(idGenerator.getSID());
					portcol.setPortId(insPortal.getPortId());
					portcol.setColId(columns[i]);
					portcol.setHeight(300);
					portcol.setHeightUnit("px");
					portcol.setSn(i);
					insPortal.getInsPortCols().add(portcol);
				}
			}
			insPortalManager.create(insPortal);
			msg = getMessage("insPortal.created", new Object[] { insPortal.getName() }, "门户成功创建!");
		} else {*/
			if (StringUtils.isNotEmpty(column)) {
				String[] columns = column.split(",");// 获得每一个中间表的ID
				int startIndex = columns.length;// 这个是方便序号的储存，防止新增栏目的序号与原来栏目序号冲突
				for (int i = 0; i < columns.length; i++) {
					InsPortCol portCol = insPortColManager.getByPortCol(insPortal.getPortId(), columns[i]);// 判断门户原来是否有这个栏目的中间表
					if (portCol != null) {// 如果原来有则跳过，不做处理
						continue;
					}
					InsPortCol portcol = new InsPortCol();// 如果没有则添加新的栏目门户中间表
					portcol.setInsColumn(insColumnManager.get(columns[i]));
					portcol.setInsPortal(insPortal);
					portcol.setConfId(idGenerator.getSID());
					portcol.setHeight(300);
					portcol.setHeightUnit("px");
					portcol.setSn(startIndex + i);
					portcol.setColNum(0);
					insPortColManager.create(portcol);
					insPortal.getInsPortCols().add(portcol);
				}
				//判断需要删除那些栏目
				ArrayList<String> arrOldCol = new ArrayList<String>();
				ArrayList<String> arrNewCol = new ArrayList<String>();
				ArrayList<String> arrDelCol = new ArrayList<String>();
				List<InsPortCol> inspc = insPortColManager.getByPortId(insPortal.getPortId());
				for(InsPortCol pc:inspc){
					arrOldCol.add(pc.getInsColumn().getColId());
				}
				arrDelCol =  arrContrast(arrOldCol,columns);
				for(String delColId:arrDelCol){
		            //InsPortCol insPortCol = insPortColManager.getByPortCol(insPortal.getPortId(), delColId);//获得这个中间表
		            //insPortal.getInsPortCols().remove(insPortCol);
					insPortColManager.delByPortCol(insPortal.getPortId(),delColId);
				}
				
			}
			insPortalManager.update(insPortal);
			msg = getMessage("insPortal.updated", new Object[] { insPortal.getName() }, "门户成功更新!");
		//}
		return new JsonResult(true, msg);
	}
	
	//处理字符数组,删去相同元素
	private static ArrayList<String> arrContrast(ArrayList<String> arr1, String[] arr2) {
		ArrayList<String> list = new ArrayList<String>();
		for (String str : arr1) { // 处理第一个数组,list里面的值为1,2,3,4
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (String str : arr2) { // 如果第二个数组存在和第一个数组相同的值，就删除
			if (list.contains(str)) {
				list.remove(str);
			}
		}
		String[] result = {}; // 创建空数组
		return list; // List to Array
	}
}
