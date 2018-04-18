package com.redxun.oa.doc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redxun.core.json.JsonResult;
import com.redxun.oa.doc.entity.DocFolder;
import com.redxun.oa.doc.manager.DocFolderManager;
import com.redxun.saweb.controller.BaseFormController;

/**
 * DocFolder管理
 * @author 陈茂昌
 */
@Controller
@RequestMapping("/oa/doc/docFolder/")
public class DocFolderFormController extends BaseFormController {

    @Resource
    private DocFolderManager docFolderManager;
    /**
     * 处理表单
     * @param request
     * @return
     */
    @ModelAttribute("docFolder")
    public DocFolder processForm(HttpServletRequest request) {
        String folderId = request.getParameter("folderId");
        DocFolder docFolder = null;
        if (StringUtils.isNotEmpty(folderId)) {
            docFolder = docFolderManager.get(folderId);
    
        } else {
            docFolder = new DocFolder();
          
        }

        return docFolder;
    }
    /**
     * 保存实体数据
     * @param request
     * @param docFolder
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(HttpServletRequest request, @ModelAttribute("docFolder") @Valid DocFolder docFolder, BindingResult result) {
    	String SID=idGenerator.getSID();
        if (result.hasFieldErrors()) {
            return new JsonResult(false, getErrorMsg(result));
        }
        String msg = null;
        if (StringUtils.isEmpty(docFolder.getFolderId())) {//当前docFolder的Id若不存在则给这个实体设置sid，并且创建它，否则更新这个实体
        	
            docFolder.setFolderId(SID);
            
            if(!"0".equals(docFolder.getParent())){//如果实体的parent不是0，则按父路径加ID构建新路径，否则按0.加ID加点构建新路径
            	DocFolder parentDoc=docFolderManager.get(docFolder.getParent());
            	if(parentDoc!=null){//如果有parent文件夹才执行
            		
            		docFolder.setPath(parentDoc.getPath()+docFolder.getFolderId()+".");//父路径加文件夹Id.构成路径
            		
            		docFolder.setDepth(parentDoc.getDepth()+1);
            		docFolderManager.update(parentDoc);
            	}
            }else{
            	if("0".equals(docFolder.getParent())){//如果创建的是根目录则把路径设置成0.
        			docFolder.setPath(SID+".");
        		}else{
        			docFolder.setPath(SID+"."+docFolder.getFolderId()+".");}//否则就按   Id.文件夹Id.  的格式设置路径
            	
            docFolder.setDepth(1);
            }
            docFolderManager.create(docFolder);
            msg = getMessage("docFolder.created", new Object[]{docFolder.getName()}, "文件夹成功创建!");
        } else {
            docFolderManager.update(docFolder);
            msg = getMessage("docFolder.updated", new Object[]{docFolder.getName()}, "文件夹成功更新!");
        }
        //saveOpMessage(request, msg);
        return new JsonResult(true, msg);
    }
    
   
}

