/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redxun.ui.module.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.util.BeanUtil;
import com.redxun.saweb.controller.BaseController;
import com.redxun.sys.core.entity.FieldNode;
import com.redxun.sys.core.entity.SysField;
import com.redxun.sys.core.entity.SysModule;
import com.redxun.sys.core.manager.SysFieldManager;
import com.redxun.sys.core.manager.SysFormSchemaManager;
import com.redxun.sys.core.manager.SysModuleManager;

/**
 * 模块控制器，用于获得所有的模块相关的配置及参数等信息
 * @author csx
 */
@Controller
@RequestMapping("/ui/module/")
public class ModuleController extends BaseController{
     @Resource
    private SysFieldManager sysFieldManager;
    @Resource
    private SysModuleManager sysModuleManager;
    @Resource
    private SysFormSchemaManager sysFormSchemaManager;
    
    @RequestMapping("getModuleFields")
    @ResponseBody
    public List<FieldNode> getModuleFields(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String entityName=request.getParameter("entityName");
        List<FieldNode> nodes=new ArrayList<FieldNode>();
        SysModule mainModule=sysModuleManager.getByEntityClass(entityName);
        //若尚未注册，先注册
        if(mainModule==null){
        	Class<?> classEntity=Class.forName(entityName);
        	mainModule=sysModuleManager.createOrUpdateFromEntityClass(classEntity);
        }
        
        if(mainModule==null) return nodes;
        List<SysField> sysFields=sysFieldManager.getByModuleId(mainModule.getModuleId());
        for(SysField field:sysFields){
            if(SysField.FIELD_COMMON.equals(field.getFieldCat())){//普通字段类型
                FieldNode node=new FieldNode();
                node.setIsLeaf(true);
                BeanUtil.copyProperties(node, field);
                nodes.add(node);
            }else if(SysField.RELATION_MANY_TO_MANY.equals(field.getFieldCat())//各种关系字段类型
                    || SysField.RELATION_MANY_TO_ONE.equals(field.getFieldCat())
                    || SysField.RELATION_ONE_TO_MANY.equals(field.getFieldCat())
                    || SysField.RELATION_ONE_TO_ONE.equals(field.getFieldCat())     
                    ){
                FieldNode parentNode=new FieldNode();
                BeanUtil.copyProperties(parentNode, field);
                parentNode.setIsLeaf(false);
                genChildNodes(parentNode,nodes);
            }
        }
        return nodes;
    }
    /**
     * 获得所有的子节点
     * @param pNode
     * @param allNodes 
     */
    private void genChildNodes(FieldNode pNode,List<FieldNode> allNodes){
        List<SysField> sysFields=sysFieldManager.getByModuleId(pNode.getLinkSysModule().getModuleId());
        for(SysField field:sysFields){
            if(SysField.FIELD_COMMON.equals(field.getFieldCat())){//普通字段类型
                FieldNode node=new FieldNode();
                node.setIsLeaf(true);
                node.setParentId(pNode.getFieldId());
                BeanUtil.copyProperties(node, field);
                allNodes.add(node);
            }else if(SysField.RELATION_MANY_TO_MANY.equals(field.getFieldCat())//各种关系字段类型
                    || SysField.RELATION_MANY_TO_ONE.equals(field.getFieldCat())
                    || SysField.RELATION_ONE_TO_MANY.equals(field.getFieldCat())
                    || SysField.RELATION_ONE_TO_ONE.equals(field.getFieldCat())     
                    ){
                FieldNode parentNode=new FieldNode();
                BeanUtil.copyProperties(parentNode, field);
                parentNode.setIsLeaf(false);
                genChildNodes(parentNode,allNodes);
            }
        }
    } 
   
}
