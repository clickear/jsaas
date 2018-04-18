<%-- 
    Document   : tab权限页面
    Created on : 2015-11-21, 10:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>tab权限页</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript" src="${ctxPath}/scripts/common/baiduTemplate.js"></script>
</head>
<body>
		<div class="mini-toolbar" >
		    <a class="mini-button" iconCls="icon-ok" onclick="oncancel">确定</a>
		    <a class="mini-button" iconCls="icon-Reset" onclick="reSetAll">重置</a>
		    <a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel');">关闭</a>
		</div>
        <div id="p1" class="form-outer">
			<form id="form1" method="post">
				<div class="form-inner">
					<table id="showInfo" class="table-detail" cellspacing="1" cellpadding="0">
						
					</table>
				</div>
			</form>
		</div> 
      
<script type="text/html" charset="utf-8" id='template_1'>
<#for(var i=0; i<list.length; i++){ #>
	<tr>
		<th rowspan="2"><#=list[i].titleName#></th>
		<td >
			<a class="mini-button" onclick="openChooseDlg('<#=list[i].titleName#>@split@whiteList')" > 白名单</a>
			<div style="display:inline-block" class="personDisplayDiv" id="<#=list[i].titleName#>whiteList"><#=list[i].whiteList #></div>
		</td>
	</tr>
	<tr>
		<td ><a class="mini-button" onclick="openChooseDlg('<#=list[i].titleName#>@split@blackList')" > 黑名单</a>
			<div style="display:inline-block" class="personDisplayDiv" id="<#=list[i].titleName#>blackList"><#=list[i].blackList #></div>
		</td>
	</tr>
<# } #>
</script>
   

	<script type="text/javascript">
	//设置左分隔符为 <!
    baidu.template.LEFT_DELIMITER='<#';
    //设置右分隔符为 <!  
    baidu.template.RIGHT_DELIMITER='#>';
    var bt=baidu.template;
	
	
     mini.parse();
     var form=new mini.Form("form1");
     var title='${titleArray}';
     var rightsJsonStr='${jsonObject}';
     var rightsJson={};
     if(rightsJsonStr){
    	 rightsJson=JSON.parse(rightsJsonStr);
     }
     var typeJson=${typeJson};
     
     function initRightJson(){
    	 var titleArray=title.split(',');
    	 
    	 for(var i=0;i<titleArray.length;i++){
    		 var tabTitle=titleArray[i];
    		 rightsJson[tabTitle]={};
         }
     }
     
     function init(){
    	 var titleArray=title.split(',');
         var tempArray=[];
         for(var i=0;i<titleArray.length;i++){
        	 var obj={};
        	 obj.titleName=titleArray[i];
        	 obj.whiteList=jointData(titleArray[i],"whiteList");
        	 obj.blackList=jointData(titleArray[i],"blackList");
        	 tempArray.push(obj);
         }
         var data={"list":tempArray};
         var tableHtml=bt('template_1',data);
     	 $("#showInfo").html(tableHtml);
     }
     
     //初始显示
     init();
     
     
     function getTabRight(){
    	 return rightsJson;
     }
     
     
     
     /*生成该tab的黑白名单权限的成员数组*/
     function getListByTitle(title,listType,jsonObject){
    	 var genArray=[];
    	 if(!jsonObject) return null;
    	 
    	 for(var tabName in jsonObject){
       		 if(tabName!=title) continue;
       		 var tabObj=jsonObject[tabName];
       		 buildJson(tabObj[listType],genArray);
         }
       return genArray;
     }
	
     function buildJson(list,genArray){
    	 for(type in list){
			var obj={};
			obj.ids=list[type].id;
			obj.names=list[type].name;
			obj.type=type;
			genArray.push(obj);
		 }
     }
     
   
     
     function oncancel(){
    	 $.ajax({
  			url:"${ctxPath}/bpm/core/bpmSolFv/saveTabRight.do",
  			type:"post",
  			data:{"data":mini.encode(rightsJson),"pkId":"${pkId}"},
  			success:function(result){
  				if(result.success){
  					CloseWindow('cancel');
  				}else{
  					alert("保存出错");
  				}
  			}
  		});
    	 
     }
     
     /*将tab对应的中文信息拼接进baidutemplate 的data中*/
     function jointData(title,listType){
    	//tab对象
    	 var tab=rightsJson[title]||{};
    	 var appendStr="";
		 var listObj=tab[listType]||{};
		//对授权类型遍历
			for(var type in listObj){
				var typeObj=listObj[type]
				appendStr+=" "+typeJson[type]+":"+typeObj.name;
			}
		return appendStr;
     		
     }
     
     function openChooseDlg(type){
    	 var typeArray=type.split("@split@");
    	 var tabTitle=typeArray[0];
    	 var listType=typeArray[1];
    	 _OpenWindow({
     		url: __rootPath + "/sys/core/public/profileDialog.do?hideRadio=YES",
             title: "选择方案", width: "800", height: "600",
             onload:function(){
             	var iframe = this.getIFrameEl().contentWindow;
             	var json=getListByTitle(tabTitle,listType,rightsJson);
             	iframe.init(json);
             },
             ondestroy: function(action) {
            	if(action!="ok") return; 
            	var iframe = this.getIFrameEl().contentWindow;
           		var data=iframe.getData();
           		changeRightJson(data,tabTitle,listType);//这里jsonObject已经更新了
           		init();
             }
     	});
     }
     /*将此条编辑过的数据修改jsonObject*/
     function changeRightJson(data,title,listType){
    	 rightsJson =rightsJson || {};
    	 var tab=rightsJson[title] || {};
		 var listObj={};
		 for(var i=0;i<data.length;i++){
			 var tmp=data[i];
			 listObj[tmp.type]={id:tmp.ids,name:tmp.names};
    	 }
		 tab[listType]=listObj;
		 rightsJson[title]=tab;
		 
     }
 	 
     function reSetAll(){
    	 rightsJson={};
    	 $(".personDisplayDiv").html("");
     }
	</script>
</body>
</html>