package com.redxun.oa.personal.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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

import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.core.query.SqlQueryFilter;
import com.redxun.core.util.BeanUtil;
import com.redxun.oa.personal.entity.AddrBook;
import com.redxun.oa.personal.entity.AddrCont;
import com.redxun.oa.personal.entity.AddrGrp;
import com.redxun.oa.personal.entity.ContactInfo;
import com.redxun.oa.personal.manager.AddrBookManager;
import com.redxun.oa.personal.manager.AddrContManager;
import com.redxun.oa.personal.manager.AddrGrpManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;
import com.redxun.saweb.util.QueryFilterBuilder;

/**
 * 联系人Controller
 * 
 * @author zwj
 *  用途：处理对联系人相关操作的请求映射
 */

@Controller
@RequestMapping("/oa/personal/addrBook/")
public class AddrBookController extends TenantListController {
	@Resource
	AddrBookManager addrBookManager;

	@Resource
	AddrGrpManager addrGrpManager;

	@Resource
	AddrContManager addrContManager;

	/**
	 * 根据主键删除联系人实体
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public JsonResult del(HttpServletRequest request, HttpServletResponse response) throws Exception { // 根据主键Id删除记录
		String uId = request.getParameter("ids");
		if (StringUtils.isNotEmpty(uId)) {  //如果联系人Id不为空
			String[] ids = uId.split(",");
			for (String id : ids) {
				addrBookManager.delete(id);  //删除实体
			}
		}
		return new JsonResult(true, "成功删除！");
	}

	/**
	 * 查看联系人明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception { // 根据主键Id获取记录
		String pkId = request.getParameter("pkId");
		Set<AddrCont> mails = new HashSet<AddrCont>(); // 新建集合来存Addrcont从表邮箱实体
		Set<AddrCont> mobiles = new HashSet<AddrCont>();// 新建集合来存Addrcont从表手机实体
		Set<AddrCont> phones = new HashSet<AddrCont>();// 新建集合来存Addrcont从表电话实体
		Set<AddrCont> weixins = new HashSet<AddrCont>();// 新建集合来存Addrcont从表微信号实体
		Set<AddrCont> workunits = new HashSet<AddrCont>();// 新建集合来存Addrcont从表工作单位实体
		Set<AddrCont> addresses = new HashSet<AddrCont>();// 新建集合来存Addrcont从表地址实体
		Set<AddrCont> qqs = new HashSet<AddrCont>();// 新建集合来存Addrcont从表QQ号实体
		String groupNames = ""; //分组列表
		AddrBook addrBook = null;
		if (StringUtils.isNotEmpty(pkId)) {//如果不为空
			addrBook = addrBookManager.get(pkId);
			Set<AddrCont> addrConts = addrBook.getAddrConts();
			Iterator<AddrCont> it = addrConts.iterator();
			String[] types = { "mail", "mobile", "phone", "weixin", "workunit", "address", "qq" }; // 丛表Addrcont实体七种类型的记录
			while (it.hasNext()) {
				AddrCont addrCont = it.next();
				for (int i = 0; i < types.length; i++) {
					if (addrCont.getType().equals(types[i])) {
						switch (i) {
						case 0: // 邮箱类型
							mails.add(addrCont);
							break;
						case 1: // 手机类型
							mobiles.add(addrCont);
							break;
						case 2: // 电话类型
							phones.add(addrCont);
							break;
						case 3: // 微信类型
							weixins.add(addrCont);
							break;
						case 4: // 工作单位类型
							workunits.add(addrCont);
							break;
						case 5: // 地址类型
							addresses.add(addrCont);
							break;
						case 6: // QQ类型
							qqs.add(addrCont);
							break;
						}
					}
				}
			}

			List<AddrGrp> addrGrps = addrGrpManager.getAllAddrGrpByAddrBookId(addrBook.getAddrId());
			for (int i = 0; i < addrGrps.size(); i++) { // 拼接回填联系人分组的字符串
				if (i == 0) {
					groupNames = addrGrps.get(i).getName(); // 获取分组名字
					continue;
				}
				groupNames = groupNames + "," + addrGrps.get(i).getName();
			}
		} else {
			addrBook = new AddrBook();
		}
		return getPathView(request).addObject("addrBook", addrBook).addObject("groupNames", groupNames).addObject("mails", mails).addObject("mobiles", mobiles).addObject("phones", phones).addObject("weixins", weixins).addObject("workunits", workunits).addObject("addresses", addresses).addObject("qqs", qqs); // 将所有类型的记录放进jsp页面
	}

	/**
	 * 编辑联系人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception { // 编辑联系人信息
		String pkId = request.getParameter("pkId");
		String groupIds = request.getParameter("groupId");
		String groupNames = "";
		// 复制添加
		String forCopy = request.getParameter("forCopy");
		AddrBook addrBook = null;
		Set<AddrCont> mails = new HashSet<AddrCont>(); // 新建集合来存Addrcont从表邮箱实体
		Set<AddrCont> mobiles = new HashSet<AddrCont>(); // 新建集合来存Addrcont从表手机实体
		Set<AddrCont> phones = new HashSet<AddrCont>();// 新建集合来存Addrcont从表电话实体
		Set<AddrCont> weixins = new HashSet<AddrCont>();// 新建集合来存Addrcont从表微信号实体
		Set<AddrCont> workunits = new HashSet<AddrCont>();// 新建集合来存Addrcont从表工作单位实体
		Set<AddrCont> addresses = new HashSet<AddrCont>();// 新建集合来存Addrcont从表地址实体
		Set<AddrCont> qqs = new HashSet<AddrCont>();// 新建集合来存Addrcont从表QQ号实体
		if (StringUtils.isNotEmpty(pkId)) { // 如果不为空，即已存在的记录
			addrBook = addrBookManager.get(pkId);
			Set<AddrCont> addrConts = addrBook.getAddrConts();
			Iterator<AddrCont> it = addrConts.iterator();
			String[] types = { "mail", "mobile", "phone", "weixin", "workunit", "address", "qq" }; // 丛表Addrcont实体七种类型的记录
			while (it.hasNext()) {
				AddrCont addrCont = it.next();
				for (int i = 0; i < types.length; i++) {
					if (addrCont.getType().equals(types[i])) {
						switch (i) {
						case 0: // 邮件类型
							mails.add(addrCont);
							break;
						case 1: // 手机类型
							mobiles.add(addrCont);
							break;
						case 2: // 电话类型
							phones.add(addrCont);
							break;
						case 3: // 微信类型
							weixins.add(addrCont);
							break;
						case 4: // 工作单位类型
							workunits.add(addrCont);
							break;
						case 5: // 地址类型
							addresses.add(addrCont);
							break;
						case 6: // QQ类型
							qqs.add(addrCont);
							break;
						}
					}
				}
			}

			List<AddrGrp> addrGrps = addrGrpManager.getAllAddrGrpByAddrBookId(addrBook.getAddrId()); // 拼接回填联系人分组的字符串
			groupIds = "";
			for (int i = 0; i < addrGrps.size(); i++) { // 拼接回填联系人分组的字符串
				if (i == 0) {
					groupIds = addrGrps.get(i).getGroupId();
					groupNames = addrGrps.get(i).getName();
					continue;
				}
				groupIds = groupIds + "," + addrGrps.get(i).getGroupId();
				groupNames = groupNames + "," + addrGrps.get(i).getName();
			}
			if ("true".equals(forCopy)) {
				addrBook.setAddrId(null);
			}
		} else {
			addrBook = new AddrBook();
			if (!"all".equals(groupIds)) { // 新建的时候默认将这个联系人分到当前联系人分组（注：all为全部这个分组）
				AddrGrp addrGrp = addrGrpManager.get(groupIds);
				groupNames = addrGrp.getName();
			}
		}
		List<AddrGrp> addrGrps = addrGrpManager.getAllByUserId(ContextUtil.getCurrentUserId()); // 获取当前用户的所有分组放入菜单中
		if (!addrGrps.isEmpty()) { // 如果没有任何分组的时候 ，将不显示菜单
			request.setAttribute("groups", addrGrps);
		}
		return getPathView(request).addObject("addrBook", addrBook).addObject("groupIds", groupIds).addObject("groupNames", groupNames).addObject("mails", mails).addObject("mobiles", mobiles).addObject("phones", phones).addObject("weixins", weixins).addObject("workunits", workunits).addObject("addresses", addresses).addObject("qqs", qqs); // 将所有集合放进jsp页面中
	}

	/**
	 * 根据分组Id获取该分组下的所有联系人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAddrBookByGroupId")
	@ResponseBody
	public List<AddrBook> getAddrBookByGroupId(HttpServletRequest request, HttpServletResponse response) throws Exception { // 根据分组Id获取该分组下所有联系人
		String pkId = request.getParameter("groupId");
		List<AddrBook> addrBooks = null;
		if (StringUtils.isNotEmpty(pkId)) {
			addrBooks = addrBookManager.getAddrBooksByGroupId(pkId);  //获取该分组下的联系人
		} else {
			addrBooks = new ArrayList<AddrBook>();
		}
		return addrBooks;
	}

	/**
	 * 获取当前用户的所有联系人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllAddrBook")
	@ResponseBody
	public JsonPageResult<AddrBook> getAllAddrBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryFilter queryFilter = QueryFilterBuilder.createQueryFilter(request);
		queryFilter.addFieldParam("createBy", ContextUtil.getCurrentUserId());
		List<AddrBook> addrBooks = addrBookManager.getAll(queryFilter); // 获取当前用户下所有联系人
		JsonPageResult<AddrBook> result=new JsonPageResult<AddrBook>(addrBooks, queryFilter.getPage().getTotalItems());
		return result;
	}

	/**
	 * 保存联系人信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveAllData")
	@ResponseBody
	public JsonResult saveAllData(HttpServletRequest request, HttpServletResponse response) throws Exception { // 新建和编辑时保存数据的处理
		Set<AddrCont> addrConts = new HashSet<AddrCont>();
		String dataJson = request.getParameter("formData"); // 获取jsp传过来主表的信息
		JSONObject jsonObj = JSONObject.fromObject(dataJson);
		AddrBook addrBook = (AddrBook) JSONUtil.json2Bean(dataJson, AddrBook.class); //将json串转成实体类
		String exts = request.getParameter("exts"); // 获取addrCont从表实体的数据
		String tempGroupIds = jsonObj.getString("groupId"); // 获取该联系人所在分组的分组Id
		String[] groupIds = tempGroupIds.split(",");
		if (StringUtils.isEmpty(addrBook.getAddrId())) { // 新建AddrBook
			addrBook.setAddrId(idGenerator.getSID());
			JSONArray array = JSONArray.fromObject(exts); // 将字符串转成json数组
			for (int i = 0; i < array.size(); i++) { // 遍历json数组
				JSONObject typeObj = array.getJSONObject(i);
				String type = typeObj.getString("name"); // 从表AddrCont的记录类型
				String vals = typeObj.getString("vals"); // 从表AddrCont各字段的数据
				JSONArray typeVals = JSONArray.fromObject(vals);
				for (int j = 0; j < typeVals.size(); j++) { // 遍历typesVals这个json数组
					if (j == 0) // 前台获取的时候各类型的记录的第一条都在主表已经存入 所以过滤第一条数据
						continue;
					AddrCont ac = new AddrCont(); // 新建从表AddrCont对象 保存数据
					ac.setContId(idGenerator.getSID());
					ac.setAddrBook(addrBook);
					ac.setType(type);
					JSONObject rowObj = typeVals.getJSONObject(j);
					if (StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext0")) && StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext1")) && StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext2")) && StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext3")) && StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext4"))) // 当从表记录的所有字段同时为空的时候，不保存当前的这条记录
						continue;
					ac.setContact(JSONUtil.getString(rowObj, "ext0")); // 设置从表AddrCont实体属性
					ac.setExt1(JSONUtil.getString(rowObj, "ext1"));
					ac.setExt2(JSONUtil.getString(rowObj, "ext2"));
					ac.setExt3(JSONUtil.getString(rowObj, "ext3"));
					ac.setExt4(JSONUtil.getString(rowObj, "ext4"));
					addrConts.add(ac);
				}
			}
			addrBook.setAddrConts(addrConts);
			addrBookManager.create(addrBook);
			if (StringUtils.isNotEmpty(tempGroupIds)) { // 分组Id不为空时，将该联系人添加到所选分组内
				for (int i = 0; i < groupIds.length; i++) {
					AddrGrp addrGrp = addrGrpManager.get(groupIds[i]);
					Set<AddrBook> addrBooks = addrGrp.getAddrBooks();
					addrBooks.add(addrBook);
					addrGrp.setAddrBooks(addrBooks);
					addrGrpManager.update(addrGrp);
				}
			}
		} else { // 更新addrBook
			AddrBook o_addrBook = addrBookManager.get(addrBook.getAddrId());
			//BeanUtil.copyProperties(o_addrBook, addrBook); // 覆盖旧的对象
			BeanUtil.copyNotNullProperties(o_addrBook, addrBook);
			JSONArray array = JSONArray.fromObject(exts);
			for (int i = 0; i < array.size(); i++) { // 遍历从表数据的json数组
				JSONObject typeObj = array.getJSONObject(i);
				String type = typeObj.getString("name"); // 获取从表记录类型
				String vals = typeObj.getString("vals");
				JSONArray typeVals = JSONArray.fromObject(vals);
				for (int j = 0; j < typeVals.size(); j++) {
					if (j == 0)
						continue; // 第一条已在主表保存 所以第一条不需要
					AddrCont ac = new AddrCont();
					ac.setAddrBook(o_addrBook);
					ac.setType(type);
					JSONObject rowObj = typeVals.getJSONObject(j);
					ac.setContId(JSONUtil.getString(rowObj, "id"));
					if (StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext0")) && StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext1")) && StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext2")) && StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext3")) && StringUtils.isEmpty(JSONUtil.getString(rowObj, "ext4"))) // 当所有字段同时为空的时候，当前的记录不保存
						continue;
					ac.setContact(JSONUtil.getString(rowObj, "ext0"));
					ac.setExt1(JSONUtil.getString(rowObj, "ext1"));
					ac.setExt2(JSONUtil.getString(rowObj, "ext2"));
					ac.setExt3(JSONUtil.getString(rowObj, "ext3"));
					ac.setExt4(JSONUtil.getString(rowObj, "ext4"));

					if (StringUtils.isEmpty(ac.getContId())) { // 如果为新的从表实体，则新建对应实体
						ac.setContId(idGenerator.getSID());
						ac.setAddrBook(o_addrBook);
						addrConts.add(ac);
					} else {
						AddrCont aCont = addrContManager.get(ac.getContId()); // 已存在实体，将新的从表记录覆盖
						BeanUtil.copyNotNullProperties(aCont, ac);
						addrConts.add(aCont);
					}
				}
			}
			o_addrBook.setAddrConts(addrConts);

			if (StringUtils.isNotEmpty(tempGroupIds)) {
				o_addrBook.getAddrGrps().clear();
				for (String gpId : groupIds) {
					AddrGrp grp = addrGrpManager.get(gpId);
					o_addrBook.getAddrGrps().add(grp);
				}
			}
			addrBookManager.update(o_addrBook);

		}
		return new JsonResult(true, "成功");
	}
	
	/**
	 * 根据分组Id获取联系人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByGroupId")
	@ResponseBody
	public JsonPageResult<ContactInfo> getByGroupId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SqlQueryFilter sqlQueryFilter=QueryFilterBuilder.createSqlQueryFilter(request);
		Map<String,Object> queryParams=sqlQueryFilter.getParams();
		String groupId=queryParams.get("groupId")==null?null:queryParams.get("groupId").toString();  //从filter里面获取groupId
		if(StringUtils.isNotEmpty(groupId)){  //如果groupId不为空
			List<ContactInfo>contactInfos=addrBookManager.getByGroupId(sqlQueryFilter);//根据分组Id获取联系人
			JsonPageResult<ContactInfo> result = new JsonPageResult<ContactInfo>(contactInfos, sqlQueryFilter.getPage().getTotalItems());
			return result;
		}
		else{//如果groupId为空
			List<ContactInfo>contactInfos=addrBookManager.getAllMailContact(ContextUtil.getCurrentUserId(),sqlQueryFilter); //获取所有联系人
			JsonPageResult<ContactInfo> result = new JsonPageResult<ContactInfo>(contactInfos, sqlQueryFilter.getPage().getTotalItems());
			return result;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return addrBookManager;
	}

}
