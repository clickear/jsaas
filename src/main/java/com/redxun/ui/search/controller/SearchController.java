/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redxun.ui.search.controller;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.IJson;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.Page;
import com.redxun.core.query.SortParam;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.saweb.util.PageSortUtil;
import com.redxun.sys.core.entity.SysField;
import com.redxun.sys.core.entity.SysModule;
import com.redxun.sys.core.entity.SysSearch;
import com.redxun.sys.core.entity.SysSearchItem;
import com.redxun.sys.core.manager.SysFieldManager;
import com.redxun.sys.core.manager.SysModuleManager;
import com.redxun.sys.core.manager.SysSearchItemManager;
import com.redxun.sys.core.manager.SysSearchManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author csx
 */
@Controller
@RequestMapping("/ui/search/sysSearch/")
public class SearchController extends BaseController {

    @Resource
    SysModuleManager sysModuleManager;
    @Resource
    SysSearchManager sysSearchManager;
    @Resource
    SysFieldManager sysFieldManager;
    @Resource
    SysSearchItemManager sysSearchItemManager;
    @Resource
    IJson iJson;
    
    /**
     * 显示某一实体中的高级搜索
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("listData")
    @ResponseBody
    public JsonPageResult listData(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String entityName=request.getParameter("entityName");
        String curUserId=ContextUtil.getCurrentUserId();
        String name=request.getParameter("name");
        Page page=PageSortUtil.constructPageFromRequest(request);
        SortParam sortParam=PageSortUtil.constructSortParamFromRequest(request);
        List<SysSearch> list=sysSearchManager.getByEntityNameUserIdName(entityName,curUserId, name, page,sortParam);
        JsonPageResult result=new JsonPageResult(list,page.getTotalItems());
        return result;
    }
    
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String entityName=request.getParameter("entityName");
        SysModule sysModule=sysModuleManager.getByEntityClass(entityName);
        return getPathView(request).addObject("sysModule",sysModule);
    }
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String entityName = request.getParameter("entityName");
        SysModule sysModule = sysModuleManager.getByEntityClass(entityName);
        String searchId=request.getParameter("searchId");
        SysSearch sysSearch=null;
        if(StringUtils.isNotEmpty(searchId)){
            sysSearch=sysSearchManager.get(searchId);
        }else{
            sysSearch=new SysSearch();
            sysSearch.setEnabled(MBoolean.YES.toString());
            sysSearch.setIsDefault(MBoolean.NO.toString());
        }
        return getPathView(request).addObject("sysModule", sysModule).addObject("sysSearch",sysSearch);
    }

    @ResponseBody
    @RequestMapping("save")
    public JsonResult save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String searchId = request.getParameter("searchId");
        String entityName = request.getParameter("entityName");
        String name = request.getParameter("name");
        String enabled = request.getParameter("enabled");
        String isDefault=request.getParameter("isDefault");
        String searchItems = request.getParameter("searchItems");
        boolean isCreate=false;
        SysSearch search = null;
        if (StringUtils.isEmpty(searchId)) {
            search = new SysSearch();
            search.setEntityName(entityName);
            search.setSearchId(idGenerator.getSID());
            isCreate=true;
        } else {
            search = sysSearchManager.get(searchId);
        }
        search.setName(name);
        search.setEnabled(enabled);
        search.setIsDefault(isDefault);
        genSubItems(searchItems,search,null);
        if(isCreate){
            sysSearchManager.create(search);
        }else{
            sysSearchManager.update(search);
        }
        return new JsonResult(true, "成功保存！");
    }

    private void genSubItems(String jsons,
            SysSearch search, SysSearchItem parentItem) {
        JSONArray jsonArray = JSONArray.fromObject(jsons);
        
        for (int i=0;i<jsonArray.size();i++) {
            SysSearchItem item = new SysSearchItem();
            JSONObject jsonObj=jsonArray.getJSONObject(i);
            Object itemId = jsonObj.get("itemId");
            if (itemId == null) {
                item.setItemId(idGenerator.getSID());
                item.setSn(i);
            } else {
                item = sysSearchItemManager.get(itemId.toString());
            }

            Object fieldId = jsonObj.get("fieldId");
            item.setNodeType(JSONUtil.getString(jsonObj,"nodeType"));
            item.setNodeTypeLabel(JSONUtil.getString(jsonObj, "nodeTypeLabel"));
            
            if(fieldId!=null && !(fieldId instanceof JSONNull)){
                SysField sysField = sysFieldManager.get(fieldId.toString());
                item.setSysField(sysField);
                item.setFieldName(sysField.getAttrName());
                item.setFieldType(sysField.getFieldType());
                item.setFieldTitle(sysField.getTitle());
                item.setFieldVal(JSONUtil.getString(jsonObj,"fieldVal"));
                item.setFieldOp(JSONUtil.getString(jsonObj,"fieldOp"));
                item.setFieldOpLabel(JSONUtil.getString(jsonObj, "fieldOpLabel"));
                item.setCtlType(sysField.getCompType());
                item.setLabel(sysField.getTitle()+item.getFieldOp()+" " + item.getFieldVal()); 
            }else{
                item.setLabel(item.getNodeType());
            }
            
            item.setSysSearch(search);
            //设置其上下级目录
            if (parentItem == null) {
                item.setParentId("0");
                item.setPath("0." + item.getItemId() + ".");
            } else {
                item.setParentId(parentItem.getItemId());
                item.setPath(parentItem.getPath() + item.getItemId() + ".");
            }
            search.getSysSearchItems().add(item);
            String children = JSONUtil.getString(jsonObj,"children");
            if(StringUtils.isNotEmpty(children)){
                genSubItems(children,search,item);
            }
        }
    }
//    
//   
//    @RequestMapping("getBySearchId")
//    @ResponseBody
//    public Map<String,Object> getBySearchId(HttpServletRequest request,HttpServletResponse response) throws Exception{
//        String searchId=request.getParameter("searchId");
//        SysSearch sysSearch=sysSearchManager.get(searchId);
//        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("sysSearch",sysSearch);
//        map.put("items",sysSearch.getSysSearchItems());
//        return map;
//    }
//    
    /**
     * 获得某个搜索条件下的搜索项列表
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("getSearchItems")
    @ResponseBody
    public List<SysSearchItem> getSearchItems(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String searchId=request.getParameter("searchId");
        return sysSearchItemManager.getBySearchId(searchId);
    }
    
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String searchId=request.getParameter("searchId");
        sysSearchManager.delete(searchId);
        return new JsonResult(true,"成功删除");
    }
    
    @RequestMapping("delItem")
    @ResponseBody
    public JsonResult delItem(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String itemId=request.getParameter("itemId");
        sysSearchItemManager.delete(itemId);
        return new JsonResult(true,"成功删除");
    }
}
