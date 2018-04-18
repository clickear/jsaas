package com.redxun.sys.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redxun.core.cache.CacheUtil;
import com.redxun.core.constants.MBoolean;
import com.redxun.core.json.JSONUtil;
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.query.QueryFilter;
import com.redxun.org.api.model.ITenant;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.BaseListController;
import com.redxun.saweb.util.QueryFilterBuilder;
import com.redxun.sys.core.entity.SysDic;
import com.redxun.sys.core.entity.SysTree;
import com.redxun.sys.core.entity.SysTreeCat;
import com.redxun.sys.core.manager.SysDicManager;
import com.redxun.sys.core.manager.SysTreeManager;
import com.redxun.sys.org.entity.OsDimension;
import com.redxun.sys.org.entity.OsGroup;

/**
 * 数据字典管理
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
@Controller
@RequestMapping("/sys/core/sysDic/")
public class SysDicController extends BaseListController{
    @Resource
    SysDicManager sysDicManager;
    @Resource
    SysTreeManager sysTreeManager;
   
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
            	SysDic sysDic=sysDicManager.get(id);
            	if(sysDic!=null){
            		sysDicManager.delete(id);
            		CacheUtil.delCache("SYS_DIC_" + sysDic.getTypeId());
            	}
            }
            
        }
        return new JsonResult(true,"成功删除！");
    }
   
    /**
     * 进入某一个数据字典项的分类维护
     * @param catKey
     * @param dicKey
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("{catKey}/{dicKey}/oneMgr")
    public ModelAndView oneMgr(@PathVariable("catKey")String catKey,
    		@PathVariable("dicKey")String dicKey,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	SysTree sysTree=sysTreeManager.getByKey(dicKey, catKey, ContextUtil.getCurrentTenantId());
    	
    	ModelAndView mv=new ModelAndView("sys/core/sysDicOneMgr.jsp");
    	
    	if(sysTree!=null){
    		mv.addObject("treeId",sysTree.getTreeId());
    		mv.addObject("showType",sysTree.getDataShowType());
    		mv.addObject("sysTree",sysTree);
    	}
    	mv.addObject("catKey",catKey);
    	
    	return mv;
    }
    
    /**
     *  按树分类获得数据字典列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("listByTreeId")
    @ResponseBody
    public List<SysDic> listByTreeId(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	String treeId=request.getParameter("treeId");
    	List dicList=sysDicManager.getByTreeId(treeId);
    	return dicList;
    }
    
    /**
     * 按类别Key查询数据列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("listByKey")
    @ResponseBody
    public List<SysDic> listByKey(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String catKey=request.getParameter("catKey");
    	String key=request.getParameter("key");
    	if(StringUtils.isEmpty(catKey)){
    		catKey=SysTreeCat.CAT_DIM;
    	}
    	SysTree sysTree=sysTreeManager.getByKey(key, catKey, ITenant.ADMIN_TENANT_ID);
    	
    	if(sysTree==null){
    		logger.error("not found the key " + key +" as dic list");
    		return new ArrayList<SysDic>();
    	}
    	List<SysDic> dicList=sysDicManager.getByTreeId(sysTree.getTreeId());
    	return dicList;
    }
    
    /**
     * 按分类节点获得数据字典列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Deprecated
    @RequestMapping("getByDicKey")
    @ResponseBody
    public List<SysDic> getByDicKey(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String dicKey=request.getParameter("dicKey");
    	SysTree sysTree=sysTreeManager.getByKey(dicKey);
    	List sysDics=new ArrayList<SysDic>();
    	if(sysTree==null) {
    		return sysDics;
    	}
    	sysDics=sysDicManager.getByTreeId(sysTree.getTreeId());
    	return sysDics;
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
        SysDic sysDic=null;
        if(StringUtils.isNotBlank(pkId)){
           sysDic=sysDicManager.get(pkId);
        }else{
        	sysDic=new SysDic();
        }
        return getPathView(request).addObject("sysDic",sysDic);
    }

    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	SysDic sysDic=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		sysDic=sysDicManager.get(pkId);
    		if("true".equals(forCopy)){
    			sysDic.setDicId(null);
    		}
    	}else{
    		sysDic=new SysDic();
    	}
    	return getPathView(request).addObject("sysDic",sysDic);
    }
    
    /**
	 * 批量数据字典
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("batchSave")
	@ResponseBody
	public JsonResult batchSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String treeId=request.getParameter("treeId");
		SysTree sysTree=sysTreeManager.get(treeId);
		String gridData=request.getParameter("gridData");
		genDics(gridData,null,sysTree);
		return new JsonResult(true,"成功保存数据字典！");
	}
	/**
	 * 行保存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rowSave")
	@ResponseBody
	public JsonResult rowSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String treeId=request.getParameter("treeId");
		SysTree sysTree=sysTreeManager.get(treeId);
		String data=request.getParameter("data");
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"children"});
		JSONObject jsonObj=JSONObject.fromObject(data,jsonConfig);
		SysDic sysDic=(SysDic)JSONObject.toBean(jsonObj, SysDic.class);
		if(StringUtils.isEmpty(sysDic.getDicId())){
			sysDic.setDicId(idGenerator.getSID());
			if(StringUtils.isNotEmpty(sysDic.getParentId())){
				SysDic parentDic=sysDicManager.get(sysDic.getParentId());
				sysDic.setPath(parentDic.getPath()+sysDic.getDicId()+".");
				sysDicManager.update(parentDic);
			}else{
				sysDic.setPath("0."+sysDic.getDicId()+".");
			}
			sysDic.setSysTree(sysTree);
			sysDicManager.create(sysDic);
		}else{
			sysDicManager.update(sysDic);
		}
		return new JsonResult(true,"成功保存数组字典-"+sysDic.getName(),sysDic);
	}
	
	/**
	 * 产生数据项及上下级关系
	 * @param menuJson
	 * @param parentMenu
	 * @param subsystem
	 */
	private void genDics(String groupJson,SysDic parentDic,SysTree sysTree){
		JSONArray jsonArray = JSONArray.fromObject(groupJson);
		for(int i=0;i<jsonArray.size();i++){
			 JSONObject jsonObj=jsonArray.getJSONObject(i);
	            Object dicId = jsonObj.get("dicId");
	            SysDic sysDic=null;
	            //是否为创建
	            boolean isCreated=false;
	            if(dicId==null){
	            	sysDic=new SysDic();
	            	sysDic.setDicId(idGenerator.getSID());
	            	sysDic.setSysTree(sysTree);
	            	isCreated=true;
	            }else{
	            	sysDic=sysDicManager.get(dicId.toString());
	            }
	            
	            if(sysDic==null) continue;
	            
	            String name=jsonObj.getString("name");
	            String key=jsonObj.getString("key");
	            String value=jsonObj.getString("value");
	            String descp=JSONUtil.getString(jsonObj,"descp");
	            int sn=JSONUtil.getInt(jsonObj,"sn");
	            
	            sysDic.setName(name);
	            sysDic.setKey(key);
	            sysDic.setValue(value);
	            sysDic.setDescp(descp);
	            sysDic.setSn(sn);
	            
	            if(parentDic==null){
	            	sysDic.setParentId("0");
	            	sysDic.setPath("0."+sysDic.getDicId()+".");
	            }else{
	            	sysDic.setParentId(parentDic.getDicId());
	            	sysDic.setPath(parentDic.getPath()+sysDic.getDicId()+".");
	            }
	            
	            String children = JSONUtil.getString(jsonObj,"children");
	            if(StringUtils.isNotEmpty(children)){
	            	genDics(children, sysDic, sysTree);
	            }
	            if(isCreated){
	            	sysDicManager.create(sysDic);
	            }else{
	            	sysDicManager.update(sysDic);
	            }
		}
		//同时需要更新这个缓存
		CacheUtil.delCache("SYS_DIC_" + sysTree.getTreeId());
		
	}
    
	/**
	 * 根据SysTree的Key和数字字典的key获取对应分类下的子节点数据字典
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByTreeKeyAndDicKey")
	@ResponseBody
	public List<SysDic> listByTreeKeyAndDicKey(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String key=request.getParameter("key");
		String dicKey=request.getParameter("dicKey");
		SysTree sysTree=sysTreeManager.getByKey(key);
		List<SysDic> sysDics=sysDicManager.getByParentId(sysTree.getTreeId(),dicKey);
		return sysDics;
	}
	
	@RequestMapping("listByTreeKey")
	@ResponseBody
	public List<SysDic> listByTreeKey(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String key=request.getParameter("key");
		List<SysDic> sysDics=sysDicManager.getByTreeKey(key);
		return sysDics;
	}
	
	/**
	 * 根据主键获取对应分类下的数据字典
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listByDicId")
	@ResponseBody
	public JsonResult listByDicId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String dicId=request.getParameter("dicId");
		SysDic sysDic=sysDicManager.get(dicId);
		List<SysDic> sysDics=sysDicManager.getBySysTreeId(sysDic.getSysTree().getTreeId());
		Map<String, String> sysDicSet=new HashMap<String, String>();
		for (int i = 0; i < sysDics.size(); i++) {
			SysDic tmpSysdic=sysDics.get(i);
			if(i==0){
				sysDicSet.put("dbtype", tmpSysdic.getSysTree().getKey());
				continue;
			}
			if("dbname".equals(tmpSysdic.getKey()))
				sysDicSet.put("dbname", tmpSysdic.getValue());
			if("dburl".equals(tmpSysdic.getKey()))
				sysDicSet.put("dburl", tmpSysdic.getValue());
			if("dbdriver".equals(tmpSysdic.getKey()))
				sysDicSet.put("dbdriver", tmpSysdic.getValue());
			
		}
		return new JsonResult(true,"", sysDicSet);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return sysDicManager;
	}

}
