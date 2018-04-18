package com.redxun.ui.view.controller;

import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonPageResult;
import com.redxun.core.json.JsonResult;
import com.redxun.core.query.Page;
import com.redxun.core.query.SortParam;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.saweb.util.PageSortUtil;
import com.redxun.sys.core.entity.SysField;
import com.redxun.sys.core.entity.SysGridField;
import com.redxun.sys.core.entity.SysGridView;
import com.redxun.sys.core.entity.SysModule;
import com.redxun.sys.core.entity.SysSearch;
import com.redxun.sys.core.entity.SysSearchItem;
import com.redxun.sys.core.manager.SysFieldManager;
import com.redxun.sys.core.manager.SysGridFieldManager;
import com.redxun.sys.core.manager.SysGridViewManager;
import com.redxun.sys.core.manager.SysModuleManager;
import com.redxun.ui.view.model.ActionColumn;
import com.redxun.ui.view.model.CheckColumn;
import com.redxun.ui.view.model.IColumn;
import com.redxun.ui.view.model.IGridColumn;
import java.util.ArrayList;
import java.util.List;
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
 * 表格视图控制器
 * @author csx
 */
@Controller
@RequestMapping("/ui/view/sysGridView/")
public class GridViewController extends BaseController{
    @Resource
    SysGridViewManager sysGridViewManager;
    @Resource
    SysGridFieldManager sysGridFieldManager;
    @Resource
    SysModuleManager sysModuleManager;
    @Resource
    SysFieldManager sysFieldManager;
    
    @RequestMapping("listByEntityName")
    @ResponseBody
    public List<SysGridView> listByEntityName(HttpServletRequest request) throws Exception{
        String entityName=request.getParameter("entityName");
        List<SysGridView> views=sysGridViewManager.getViewsByEntityName(entityName, ContextUtil.getCurrentUserId());
        return views;
    }
    
     /**
     * 显示某一实体中的视图方案
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
        SysModule sysModule=sysModuleManager.getByEntityClass(entityName);
        List<SysGridView> list=sysGridViewManager.getByModuleIdUserIdName(sysModule.getModuleId(),curUserId, name, page,sortParam);
        JsonPageResult result=new JsonPageResult(list,page.getTotalItems());
        return result;
    }
    
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String entityName=request.getParameter("entityName");
        SysModule sysModule=sysModuleManager.getByEntityClass(entityName);
        return getPathView(request).addObject("sysModule",sysModule);
    }
    
    @RequestMapping("edit")
    @ResponseBody
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String gridViewId=request.getParameter("gdViewId");
        String entityName=request.getParameter("entityName");
        SysGridView sysGridView=null;
        if(StringUtils.isNotEmpty(gridViewId)){
           sysGridView =sysGridViewManager.get(gridViewId);
        }else{
            sysGridView=new SysGridView();
            sysGridView.setIsDefault(MBoolean.NO.toString());
            sysGridView.setIsSystem(MBoolean.YES.toString());
        }
        String asNew=request.getParameter("asNew");
        SysModule sysModule=sysModuleManager.getByEntityClass(entityName);
        //若没有注册，即需要先生成module
        if(sysModule==null){
        	sysModule=new SysModule();
        	sysModule.setEntityName(entityName);
        }
        
        return getPathView(request).addObject("sysGridView",sysGridView)
                .addObject("sysModule",sysModule).addObject("asNew",asNew);
    }
    /**
     * 批量更新保存
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("batchUpdate")
    @ResponseBody
    public JsonResult batchUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String gridData=request.getParameter("gridData");
        JSONArray jsonArr=JSONArray.fromObject(gridData);
        for(int i=0;i<jsonArr.size();i++){
            JSONObject jsonObj=jsonArr.getJSONObject(i);
            String gridViewId=JSONUtil.getString(jsonObj,"gridViewId");
            if(StringUtils.isNotEmpty(gridViewId)){
                String name=JSONUtil.getString(jsonObj,"name");
                Integer sn=JSONUtil.getInt(jsonObj, "sn");
                SysGridView sysGridView=sysGridViewManager.get(gridViewId);
                sysGridView.setName(name);
                sysGridView.setSn(sn);
                sysGridViewManager.update(sysGridView);
            }
        }
        return new JsonResult(true,"成功更新！");
    }
    
    @RequestMapping("fields")
    @ResponseBody
    public List<SysGridField> fields(HttpServletRequest request,HttpServletResponse response) throws Exception{
         String gridViewId=request.getParameter("gdViewId");
         List<SysGridField> sysGridFields=sysGridFieldManager.getByGridViewId(gridViewId);
         return sysGridFields;
    }
    
    @RequestMapping("getColumns")
    @ResponseBody
    public List<IColumn> getColumns(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List<IColumn> columns=new ArrayList<IColumn>();
        String isInclude=request.getParameter("isInclude");
        if("true".equals(isInclude)){
            columns.add(new CheckColumn());
            columns.add(new ActionColumn());
        }
        String gridViewId=request.getParameter("gridViewId");
        if(StringUtils.isNotEmpty("gridViewId")){
            List tmpColums=(List)sysGridFieldManager.getColumnsByGridViewId(gridViewId);
            columns.addAll(tmpColums);
        }
        return columns;
    }
    
    @RequestMapping("saveCurView")
    @ResponseBody
    public JsonResult saveCurView(HttpServletRequest request,HttpServletResponse response) throws Exception{
      String gridViewId=request.getParameter("gridViewId");
      String columns=request.getParameter("columns");
      updateColumnsConfig(columns,gridViewId);
      return new JsonResult(true,"成功保存！");
    }
    //更新列的配置
    private void updateColumnsConfig(String columns,String gridViewId){
        JSONArray colArr=JSONArray.fromObject(columns);
      for(int i=0;i<colArr.size();i++){
          JSONObject obj=colArr.getJSONObject(i);
          //String header=JSONUtil.getString(obj, "header");
          String fieldName=JSONUtil.getString(obj, "field");
          //String allowSort=JSONUtil.getString(obj, "allowSort");
          String sWidth=JSONUtil.getString(obj, "width");
          String visible=JSONUtil.getString(obj, "visible");
          int index=sWidth.lastIndexOf("px");
          int width=100;
          if(index!=-1){
             sWidth=sWidth.substring(0, index);
             width=new Integer(sWidth);
          }
          if(StringUtils.isNotEmpty(fieldName)){
            SysGridField field=sysGridFieldManager.getByGridViewIdFieldName(gridViewId, fieldName);
            field.setFieldName(fieldName);
            String isHidden="true".equals(visible)?MBoolean.NO.toString():MBoolean.YES.toString();
            field.setIsHidden(isHidden);
            field.setColWidth(width);
            sysGridFieldManager.update(field);
          }
          
          String cols=JSONUtil.getString(obj,"columns");
          if(StringUtils.isNotEmpty(cols)){
             updateColumnsConfig(cols,gridViewId);
          }
      }
    }
    
    @RequestMapping("save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String gridViewId = request.getParameter("gridViewId");
        String entityName = request.getParameter("entityName");
        String name = request.getParameter("name");
        String isSystem = request.getParameter("isSystem");
        String isDefault=request.getParameter("isDefault");
        String fieldItems = request.getParameter("fieldItems");
        boolean isCreate=false;
        SysGridView sysGridView = null;
        if (StringUtils.isEmpty(gridViewId)) {
            sysGridView = new SysGridView();
            SysModule sysModule=sysModuleManager.getByEntityClass(entityName);
            sysGridView.setModuleId(sysModule.getModuleId());
            sysGridView.setGridViewId(idGenerator.getSID());
            isCreate=true;
        } else {
            sysGridView = sysGridViewManager.get(gridViewId);
        }
        sysGridView.setName(name);
        sysGridView.setIsSystem(isSystem);
        sysGridView.setIsDefault(isDefault);
        genFieldItems(fieldItems,sysGridView,null);
        sysGridView.setSn(0);
        if(isCreate){
            sysGridViewManager.create(sysGridView);
        }else{
            sysGridViewManager.update(sysGridView);
        }
        return new JsonResult(true,"成功保存！");
    } 
    
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String gridViewId=request.getParameter("gridViewId");
        sysGridViewManager.delete(gridViewId);
        return new JsonResult(true,"成功删除");
    }
    
    @RequestMapping("delItems")
    @ResponseBody
    public JsonResult delItems(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String gdFieldId=request.getParameter("gdFieldId");
        if(StringUtils.isNotEmpty(gdFieldId)){
            SysGridField field=sysGridFieldManager.get(gdFieldId);
            sysGridFieldManager.delByPath(field.getPath());
        }
        return new JsonResult(true,"成功保存！");
    }
    
    private void genFieldItems(String jsons,
            SysGridView sysGridView, SysGridField parentItem) {
        JSONArray jsonArray = JSONArray.fromObject(jsons);
        
        for (int i=0;i<jsonArray.size();i++) {
            SysGridField item = new SysGridField();
            JSONObject jsonObj=jsonArray.getJSONObject(i);
            Object itemId = jsonObj.get("gdFieldId");
            if (itemId == null || itemId.toString().startsWith("v_")) {
                item.setGdFieldId(idGenerator.getSID());
            } else {
                item = sysGridFieldManager.get(itemId.toString());
            }

            Object fieldId = jsonObj.get("fieldId");
            item.setItemType(JSONUtil.getString(jsonObj,"itemType"));
            
            if(fieldId!=null && !(fieldId instanceof JSONNull)){
                SysField sysField = sysFieldManager.get(fieldId.toString());
                item.setSysField(sysField);
            }
            
            item.setFieldName(JSONUtil.getString(jsonObj,"fieldName"));
            item.setFieldTitle(JSONUtil.getString(jsonObj,"fieldTitle"));
            item.setColWidth(JSONUtil.getInt(jsonObj, "colWidth"));
            item.setSn(JSONUtil.getInt(jsonObj, "sn"));
            String isHidden=JSONUtil.getString(jsonObj, "isHidden");
            String allowSort=JSONUtil.getString(jsonObj, "allowSort");
            String format=JSONUtil.getString(jsonObj, "format");
            item.setIsHidden(isHidden);
            item.setFomart(format);
            item.setAllowSort(allowSort);
            item.setSysGridView(sysGridView);
            //设置其上下级目录
            if (parentItem == null) {
                item.setParentId("0");
                item.setPath("0." + item.getGdFieldId() + ".");
            } else {
                item.setParentId(parentItem.getGdFieldId());
                item.setPath(parentItem.getPath() + item.getGdFieldId() + ".");
            }
            sysGridView.getSysGridFields().add(item);
            String children = JSONUtil.getString(jsonObj,"children");
            if(StringUtils.isNotEmpty(children)){
                genFieldItems(children,sysGridView,item);
            }
        }
    }
}
