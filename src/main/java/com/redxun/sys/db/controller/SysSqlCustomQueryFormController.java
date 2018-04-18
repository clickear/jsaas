package com.redxun.sys.db.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.redxun.core.json.JsonResult;
import com.redxun.saweb.controller.BaseFormController;
import com.redxun.saweb.util.IdUtil;
import com.redxun.sys.db.entity.SysSqlCustomQuery;
import com.redxun.sys.db.manager.SysSqlCustomQueryManager;

import net.sf.jasperreports.engine.util.JsonUtil;
/**
 * 自定义查询 管理
 * 
 * @author cjx
 */
@Controller
@RequestMapping("/sys/db/sysSqlCustomQuery/")
public class SysSqlCustomQueryFormController extends BaseFormController {
	@Resource
	private SysSqlCustomQueryManager sysSqlCustomQueryManager;
	/**
	 * 处理表单
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("sysSqlCustomQuery")
	public SysSqlCustomQuery processForm(HttpServletRequest request) {
		String id = request.getParameter("id");
		SysSqlCustomQuery sysSqlCustomQuery = null;
		if (StringUtils.isNotEmpty(id)) {
			sysSqlCustomQuery = sysSqlCustomQueryManager.get(id);
		} else {
			sysSqlCustomQuery = new SysSqlCustomQuery();
		}
		return sysSqlCustomQuery;
	}
	/**
	 * 保存实体数据
	 * 
	 * @param request
	 * @param orders
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String msg = null;
		try {
			String jsonStr = request.getParameter("json");
			SysSqlCustomQuery sysSqlCustomQuery = (SysSqlCustomQuery) JSONObject.parseObject(jsonStr, SysSqlCustomQuery.class);
						
			if (StringUtils.isEmpty(sysSqlCustomQuery.getId())) {
				sysSqlCustomQuery.setId(IdUtil.getId());
				sysSqlCustomQueryManager.create(sysSqlCustomQuery);
				msg = getMessage("sysSqlCustomQuery.created", new Object[]{sysSqlCustomQuery.getIdentifyLabel()}, "[自定义查询]成功创建!");
			} else {
				sysSqlCustomQueryManager.update(sysSqlCustomQuery);
				msg = getMessage("sysSqlCustomQuery.updated", new Object[]{sysSqlCustomQuery.getIdentifyLabel()}, "[自定义查询]成功更新!");
			}
			// saveOpMessage(request, msg);
			return new JsonResult(true, msg);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, e.getMessage());
		}
	}
}
