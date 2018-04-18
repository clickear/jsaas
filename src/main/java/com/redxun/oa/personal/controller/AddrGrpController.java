package com.redxun.oa.personal.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.redxun.core.json.JsonResult;
import com.redxun.core.manager.BaseManager;
import com.redxun.core.util.BeanUtil;
import com.redxun.oa.personal.entity.AddrBook;
import com.redxun.oa.personal.entity.AddrGrp;
import com.redxun.oa.personal.entity.GrpAndBook;
import com.redxun.oa.personal.manager.AddrBookManager;
import com.redxun.oa.personal.manager.AddrGrpManager;
import com.redxun.saweb.context.ContextUtil;
import com.redxun.saweb.controller.TenantListController;

/**
 * 联系人分组Controller
 * 
 * @author zwj
 *  用途：处理对联系人分组相关操作的请求映射
 */
@Controller
@RequestMapping("/oa/personal/addrGrp/")
public class AddrGrpController extends TenantListController{
    @Resource
    AddrGrpManager addrGrpManager;
    
    @Resource
    AddrBookManager addrBookManager;
   
    /**
     * 根据主键删除联系人实体
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("del")
    @ResponseBody
    public JsonResult del(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String uId=request.getParameter("ids");
        if(StringUtils.isNotEmpty(uId)){
            String[] ids=uId.split(",");
            for(String id:ids){
                addrGrpManager.delete(id); //删除实体
            }
        }
        return new JsonResult(true,"成功删除！");
    }
    
    /**
     * 查看联系人分组明细
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("get")
    public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pkId=request.getParameter("pkId");
        AddrGrp addrGrp=null;
        if(StringUtils.isNotEmpty(pkId)){
           addrGrp=addrGrpManager.get(pkId);
        }else{
        	addrGrp=new AddrGrp();
        }
        return getPathView(request).addObject("addrGrp",addrGrp);
    }
    
    /**
     * 编辑联系人分组
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String pkId=request.getParameter("pkId");
    	//复制添加
    	String forCopy=request.getParameter("forCopy");
    	AddrGrp addrGrp=null;
    	if(StringUtils.isNotEmpty(pkId)){
    		addrGrp=addrGrpManager.get(pkId);
    		if("true".equals(forCopy)){
    			addrGrp.setGroupId(null);
    		}
    	}else{
    		addrGrp=new AddrGrp();
    	}
    	return getPathView(request).addObject("addrGrp",addrGrp);
    }
    
    /**
     * 获取当前用户的分组
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("list")
    public ModelAndView getAllGroup(HttpServletRequest request,HttpServletResponse response) throws Exception{ 
    	String isSendMail=request.getParameter("mail");
    	List<AddrGrp> addrGrps=addrGrpManager.getAllByUserId(ContextUtil.getCurrentUserId());  //获取用户的所有分组
    	request.setAttribute("addrGrps", addrGrps);
    	if(StringUtils.isNotEmpty(isSendMail)&&"sendmail".equals(isSendMail))  //如果是发邮件时添加联系人
    		return new ModelAndView("/oa/mail/mailAddrBookList.jsp");    //返回对应视图
    	else{
			for (int i = 0; i < addrGrps.size(); i++) {
				Long num = addrGrpManager.getAddrBookTotalByGroupId(addrGrps.get(i).getGroupId(), ContextUtil.getCurrentUserId()); // 获取当前用户的每一个分组下联系人数量
				String total = Long.toString(num);
				addrGrps.get(i).setTotal(total);
			}
			String sum = Long.toString(addrBookManager.getAddrBookSumByUserId(ContextUtil.getCurrentUserId())); // 获取当前用户所有联系人数量
			return getPathView(request).addObject("total", sum);
    	}
    }
    
    /**
     * 刷新联系人分组菜单
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("refreshMenu")
    @ResponseBody
    public JsonResult refreshMenu(HttpServletRequest request,HttpServletResponse response) throws Exception{     
    	List<AddrGrp> addrGrps=addrGrpManager.getAllByUserId(ContextUtil.getCurrentUserId());       //获取当前用户的所有分组
    	for (int i = 0; i < addrGrps.size(); i++) {
			Long num=addrGrpManager.getAddrBookTotalByGroupId(addrGrps.get(i).getGroupId(),ContextUtil.getCurrentUserId());      //获取当前用户的所有分组下联系人数量
			String total=Long.toString(num);
			addrGrps.get(i).setTotal(total);
		}
    	String sum=Long.toString(addrBookManager.getAddrBookSumByUserId(ContextUtil.getCurrentUserId()));            //获取当前用户所有联系人数量
    	Map<String,Object> dataMap=new HashMap<String,Object>();             //将集合和对应的总数放到Set集合中
    	dataMap.put("list", addrGrps);
    	dataMap.put("sum", sum);
    	
    	return  new JsonResult(true,"刷新",dataMap);
    	
    }
    
    /**
     * 获取构建联系人树的所有节点（用于添加联系人分组的同时添加联系人）
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("getAllGrpAndBook")
    @ResponseBody
    public List<GrpAndBook> getAllGrpAndBook(HttpServletRequest request,HttpServletResponse response) throws Exception{           
    	List<AddrGrp> addrGrps=addrGrpManager.getAllByUserId(ContextUtil.getCurrentUserId());     //获取该用户下的所有分组
    	List<GrpAndBook> grpAndBooks=new ArrayList<GrpAndBook>();
    	List<AddrBook> addrBooks=new ArrayList<AddrBook>();
    	for (int i = 0; i < addrGrps.size(); i++) {
			AddrGrp tmpGrp=addrGrps.get(i);
			GrpAndBook parent_grpAndBook=new GrpAndBook();       // 构建联系人分组的树节点
			parent_grpAndBook.setGabId(tmpGrp.getGroupId());
			Long sum=addrBookManager.getAddrBookSumByGroupId(tmpGrp.getGroupId());    //获取该联系人分组的数量
			parent_grpAndBook.setName(tmpGrp.getName()+"("+sum+")");
			grpAndBooks.add(parent_grpAndBook);
		    addrBooks=addrBookManager.getAddrBooksByGroupId(tmpGrp.getGroupId());
			for (int j = 0; j < addrBooks.size(); j++) {
				AddrBook tmpBook=addrBooks.get(j);
				GrpAndBook child_grpAndBook=new GrpAndBook();   //构建联系人分组下的联系人的树节点
				child_grpAndBook.setGabId(tmpBook.getAddrId());
				child_grpAndBook.setName(tmpBook.getName());
				child_grpAndBook.setParentId(tmpGrp.getGroupId());
				grpAndBooks.add(child_grpAndBook);
			}
		}
    	return grpAndBooks;
    	
    }
    
    /**
     * 保存分组下的联系人
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("saveGrp")
    @ResponseBody
    public JsonResult saveGrp(HttpServletRequest request,HttpServletResponse response) throws Exception{       
    	String formData=request.getParameter("formData");
    	String listData=request.getParameter("listData");
		JSONObject jsonObj=JSONObject.fromObject(formData);
		AddrGrp addrGrp=(AddrGrp)JSONUtil.json2Bean(jsonObj.toString(), AddrGrp.class);   //将json串转成实体类
		if(StringUtils.isEmpty(addrGrp.getGroupId())){                 //新建分组
			addrGrp.setGroupId(idGenerator.getSID());
			JSONArray array = JSONArray.fromObject(listData); 
			Set<AddrBook>addrBooks=addrGrp.getAddrBooks();
			for (int i = 0; i < array.size(); i++) {
				JSONObject addrBookObj = array.getJSONObject(i);
				String tmp=JSONUtil.getString(addrBookObj, "addrId");
				String addrId =StringUtils.isNotEmpty(tmp)?tmp:JSONUtil.getString(addrBookObj, "gabId");     //获取联系人对应的实体的Id
				AddrBook addrBook=addrBookManager.get(addrId);
				addrBooks.add(addrBook);
			}
			addrGrpManager.create(addrGrp);
		}
		else{           //编辑分组
			JSONArray array = JSONArray.fromObject(listData);
			Set<AddrBook>addrBooks=addrGrp.getAddrBooks();
			addrBooks.clear();
			for (int i = 0; i < array.size(); i++) {
				JSONObject addrBookObj = array.getJSONObject(i);
				String tmp=JSONUtil.getString(addrBookObj, "addrId");
				String addrId = StringUtils.isNotEmpty(tmp)?tmp:JSONUtil.getString(addrBookObj, "gabId");            //获取联系人对应实体的Id
				AddrBook addrBook=addrBookManager.get(addrId);
				addrBooks.add(addrBook);
			}
			AddrGrp o_grp=addrGrpManager.get(addrGrp.getGroupId());
			BeanUtil.copyNotNullProperties(o_grp, addrGrp);
			addrGrpManager.update(o_grp);
		}
		return new JsonResult(true,"成功保存");
    }

	@SuppressWarnings("rawtypes")
	@Override
	public BaseManager getBaseManager() {
		return addrGrpManager;
	}

}
