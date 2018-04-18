<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>文档列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/filemanager.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>

<style type="text/css">
	
</style>
</head>
<body>
<ul id="popupFileMenu" class="mini-menu" style="display:none;">
	    <li iconCls="icon-html" onclick="addDoc('html')">Html文档</li>
	    <li iconCls="icon-myword" onclick="addDoc('doc')">Word文档</li>
	    <!-- <li iconCls="icon-myword" onclick="addDoc('docx')">Word2007文档</li>
	    <li iconCls="icon-myexcel" onclick="addDoc('xls')">Excel2003文档</li>
	    <li iconCls="icon-myexcel" onclick="addDoc('xlsx')">Excel2007文档</li>
	     <li iconCls="icon-myppt" onclick="addDoc('ppt')">PowerPoint2003文档</li>
	    <li iconCls="icon-myppt" onclick="addDoc('pptx')">PowerPoint2007文档</li> -->
    </ul>
	 <div id="toolbar1" class="mini-toolbar" style="padding:2px;">
		<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="location.reload(true)">页面刷新</a>
	    <span class="separator"></span>
	    <a class="mini-button" iconCls="icon-rowbefore" plain="true" onclick="toBefore()">后退</a>
	    <span class="separator"></span>
	    <a id="adddoc" class="mini-menubutton" iconCls="icon-add" plain="true" menu="#popupFileMenu">新增文件</a>
	    <span class="separator spanme" ></span>
	    <a id="addfolder" class="mini-button" iconCls="icon-copyAdd" plain="true" onclick="addFolder()">添加文件夹</a>
	    <span class="separator spanme" ></span>
	    <a id="toparentFolder" class="mini-button" iconCls="icon-up" plain="true" onclick="toparentFolder()">返回上级目录</a>
	    <input class="text" id="key" name="key"  style="width:80px;"/>    
	    <a id="toparentFolder" class="mini-button" iconCls="icon-search" plain="false" onclick="finddaf()">查找</a>
	    <a id="toparentFolder" class="mini-button" iconCls="icon-trash" plain="false" onclick="clearsearch()">清空</a>
     </div>



<div class="mini-fit" style="height: 100%;" >
     
         <div id="filesDataView" style="width:100%;height:100%;overflow:auto;">
             <div id="filesView" class="filesView">
             </div>
         </div>
         <div id="filesListBox" textField="name" class="mini-listbox" 
             style="width:100%;height:100%;overflow:auto;display:none;" borderStyle="border:0;"  >
             <div property="columns" >
                 <div field="name" width="auto">名称</div>
                 <div field="type" width="100">类型</div>
                 <div field="dafId" width="auto">Id</div>
             </div>
         </div>
           
</div>

     <!-- 文件夹右键菜单 -->
     <div id="fmenu">
	 <ul id="listMenu" class="mini-contextmenu" >
		<li name="add" iconCls="icon-copyAdd" onclick="newfolder()">新建文件夹</li>
		<li class="separator"></li>
		<li name="add" iconCls="icon-add" onclick="newfile()" >新建文件</li>
		<li class="separator"></li>
		<li name="edit" iconCls="icon-edit" onclick="editFolder()">编辑</li>
		<li class="separator"></li>
		<li name="delete" iconCls="icon-remove" onclick="deleteFolderAndFile()">删除</li>
	</ul> 
	</div>
	
	<!-- 文件的右键菜单 -->
	<div id="dmenu">
	<ul id="doclistMenu" class="mini-contextmenu">
	<li name="edit" iconCls="icon-edit" onclick="editDoc()">编辑</li>
	<li name="delete" iconCls="icon-remove" onclick="deletedoc()">删除</li>
	</ul>
	</div>

	<script type="text/javascript">
	mini.parse();
    //点击之后的实体的ID
	var staticID;
    var firstOpenId="${firstOpenId}";
	var folderId = "${folderId}";
	var type = "${type}";
	var isShare = "${isShare}";
	
	
	 if(firstOpenId==""){//共享页面没有firstOpenId,给它赋初值
			firstOpenId=folderId;
		}
	//在共享文件夹禁止返回上一级
	$(function(){
		if(isShare=="YES"){
		$('#toparentFolder').hide();//禁止向父级目录返回
		$('#adddoc').hide();//禁止在共享文件里直接添加文件
		$('#addfolder').hide();//禁止在共享文件里直接添加文件夹
		$('.spanme').hide();
		}
		
		$.ajax({
	         url: "${ctxPath}/oa/doc/docFolder/getParentFolderId.do?folderId="+folderId,//得到当前folder的parent
	            success: function (text) {
	            	if("0"==text){//在根目录也禁止返回上一级目录
	            		$('#toparentFolder').hide();
	            	}
	            }
	        });
		
	});

	
	//页面加载文件以及文件夹Json
        $.ajax({
         url: "${ctxPath}/oa/doc/doc/listBox.do?folderId="+folderId+"&type="+type+"&isShare="+isShare+"&multi="+"0",//0是指显示自己的文件夹和文件
            success: function (text) {
            	var files = mini.decode(text);
                refreshFiles(files);
            }
        });
    //把数据包装成图标加文字加链接等
	function refreshFiles(data) {
        if (!data) data = [];
        var filesListBox = mini.get("filesListBox");
        var filesDataView = document.getElementById("filesDataView");
                var sb = [];
            for (var i = 0, l = data.length; i < l; i++) {
                var file = data[i];
                var N=file.dafId;
                var ele='';
                var jel=null;
                if(file.type=="docx"||file.type=="doc"){
                	ele='<div class="folderBind '+file.name+'" folderId="'+N+'" title='+file.name+'>';
                	ele =ele+  '<a  href="javascript:void(0);"  class="file" onclick="fileOpen('+N+')" hideFocus>';
                	ele = ele+ '<image class="file-image" ';
                    var src = "${ctxPath}/styles/filetype/word.png";
                   ele=ele+ ' src="' + src + '"';
                   ele=ele+ ' />';
                   ele=ele+ '<span class="file-text" >';
                   var shortname=file.name;
                   if(file.name.length>6){
                	  shortname=file.name.substring(0,6)+"…";
                   }
                   ele=ele+ '<p style="margin-top:-2px;">'+shortname+'</p>';
                   ele=ele+ '</span>';
                   ele=ele+ '</a>';
                   ele=ele+'</div>';
                   jel=$(ele);
                   $(jel).bind("contextmenu",function (e) {//把菜单绑定下去
                       var menu = mini.get("doclistMenu");
                       if(isShare!="YES"){
                       menu.showAtPos(e.pageX, e.pageY);}
                       return false;
                   });
                   
                   $(jel).on('mousedown',function(e){//鼠标点击之后把当前的folderId设置进去staticID
                	   var folderId=$(this).attr('folderId');
                	   staticID=folderId;
                   });
                }else if(file.type=="xlsx"||file.type=="xls"){
                	ele='<div class="folderBind '+file.name+'" folderId="'+N+'" title='+file.name+'>';
                	ele =ele+  '<a  href="javascript:void(0);"  class="file" onclick="fileOpen('+N+')" hideFocus>';
                	ele = ele+ '<image class="file-image" ';
                    var src = "${ctxPath}/styles/filetype/excel.png";
                   ele=ele+ ' src="' + src + '"';
                   ele=ele+ ' />';
                   ele=ele+ '<span class="file-text" >';
                   var shortname=file.name;
                   if(file.name.length>6){
                	  shortname=file.name.substring(0,6)+"…";
                   }
                   ele=ele+ '<p style="margin-top:-2px;">'+shortname+'</p>';
                   ele=ele+ '</span>';
                   ele=ele+ '</a>';
                   ele=ele+'</div>';
                   jel=$(ele);
                   $(jel).bind("contextmenu",function (e) {//把菜单绑定下去
                       var menu = mini.get("doclistMenu");
                       if(isShare!="YES"){
                       menu.showAtPos(e.pageX, e.pageY);}
                       return false;
                   });
                   
                   $(jel).on('mousedown',function(e){//鼠标点击之后把当前的folderId设置进去staticID
                	   var folderId=$(this).attr('folderId');
                	   staticID=folderId;
                   });
                }else if(file.type=="pptx"||file.type=="ppt"){
                	ele='<div class="folderBind '+file.name+'" folderId="'+N+'" title='+file.name+'>';
                	ele =ele+  '<a  href="javascript:void(0);"  class="file" onclick="fileOpen('+N+')" hideFocus>';
                	ele = ele+ '<image class="file-image" ';
                    var src = "${ctxPath}/styles/filetype/ppt.png";
                   ele=ele+ ' src="' + src + '"';
                   ele=ele+ ' />';
                   ele=ele+ '<span class="file-text" >';
                   var shortname=file.name;
                   if(file.name.length>6){
                	  shortname=file.name.substring(0,6)+"…";
                   }
                   ele=ele+ '<p style="margin-top:-2px;">'+shortname+'</p>';
                   ele=ele+ '</span>';
                   ele=ele+ '</a>';
                   ele=ele+'</div>';
                   jel=$(ele);
                   $(jel).bind("contextmenu",function (e) {//把菜单绑定下去
                       var menu = mini.get("doclistMenu");
                       if(isShare!="YES"){
                       menu.showAtPos(e.pageX, e.pageY);}
                       return false;
                   });
                   
                   $(jel).on('mousedown',function(e){//鼠标点击之后把当前的folderId设置进去staticID
                	   var folderId=$(this).attr('folderId');
                	   staticID=folderId;
                   });
                }else if(file.type=="html"){
                	ele='<div class="folderBind '+file.name+'" folderId="'+N+'" title='+file.name+'>';
                	ele =ele+  '<a  href="javascript:void(0);"  class="file" onclick="fileOpen('+N+')" hideFocus>';
                	ele = ele+ '<image class="file-image" ';
                    var src = "${ctxPath}/styles/filetype/file1.png";
                   ele=ele+ ' src="' + src + '"';
                   ele=ele+ ' />';
                   ele=ele+ '<span class="file-text" >';
                   var shortname=file.name;
                   if(file.name.length>6){
                	  shortname=file.name.substring(0,6)+"…";
                   }
                   ele=ele+ '<p style="margin-top:-2px;">'+shortname+'</p>';
                   ele=ele+ '</span>';
                   ele=ele+ '</a>';
                   ele=ele+'</div>';
                   jel=$(ele);
                   $(jel).bind("contextmenu",function (e) {//把菜单绑定下去
                       var menu = mini.get("doclistMenu");
                       if(isShare!="YES"){
                       menu.showAtPos(e.pageX, e.pageY);}
                       return false;
                   });
                   
                   $(jel).on('mousedown',function(e){//鼠标点击之后把当前的folderId设置进去staticID
                	   var folderId=$(this).attr('folderId');
                	   staticID=folderId;
                   });
                }else if(file.type=="文件夹"){
                	//这边是文件夹的处理
                	ele='<div class="folderBind  '+file.name+'" folderId="'+N+'" title='+file.name+'>';
                	ele=ele+ '<a id="context-menu-one" class="file"  href="${ctxPath}/oa/doc/docFolder/listBoxWindow.do?folderId='+N+'&type='+type+'&isShare='+isShare+'&firstOpenId='+firstOpenId+'"   hideFocus  >';//onmousedown="rightmenu(event)"
                	ele=ele+ '<image class="folder-image" ';
                    var src = "${ctxPath}/styles/filetype/folder.png";
                   ele=ele+ ' src="' + src + '"';
                   ele=ele+ ' />';
                   ele=ele+ '<span class="file-text" >';
                   var shortname=file.name;
                   if(file.name.length>6){
                	  shortname=file.name.substring(0,6)+"…";
                   }
                   ele=ele+ '<p style="margin-top:-2px;">'+shortname+'</p>';
                   ele=ele+ '</span>';
                   ele=ele+ '</a>';
                   ele=ele+'</div>';
                   jel=$(ele);  
                   $(jel).bind("contextmenu",function (e) {//把菜单绑定下去
                       var menu = mini.get("listMenu");
                   if(isShare!="YES")
                   { menu.showAtPos(e.pageX, e.pageY);}
                       return false;
                   });
                   
                   $(jel).on('mousedown',function(e){
                	   var folderId=$(this).attr('folderId');
                	   staticID=folderId;
                   });
                }
               
                
                $("#filesView").append(jel);
            }
			
            $("#filesView").append('<div style="clear:both;"></div>');

    }
	//打开文件   明细
	function fileOpen(N){
		_OpenWindow({
   		 url: "${ctxPath}/oa/doc/doc/get.do?pkId="+N,
           title: "文件明细",
           width: 690, height: 600,
           ondestroy: function(action) {
           	
               if (action == 'ok') {
                   grid.reload();
               }
           }
   	});
	}

    //文件夹内创建文件夹
	function newfolder(){
		var folder;
		 $.ajax({
            url: "${ctxPath}/oa/doc/docFolder/getone.do?folderId="+staticID,
            success: function (text) {
            	folder = mini.decode(text);
            	_OpenWindow({
        			url : '${ctxPath}/oa/doc/docFolder/edit.do?parent='+staticID+"&path="+folder.userId+"&type="+folder.type,
        			title : "新增文件夹",
        			width : 830,
        			height : 400,
        			onload : function() {
        			},
        			ondestroy : function() {
        				location.reload(true);
        			}
        		});
            }
        }); 
	}
	//菜单文件夹内创建文件
	function newfile(){
		_OpenWindow({
   		 url: "${ctxPath}/oa/doc/doc/edit.do?folderId="+staticID,
           title: "新增文件",
           width: 830, height: 710,
           ondestroy: function(action) {
           	if(action=='ok'){
           		location.reload(true);
           	}
           }
   	});
	}
	//删除文件夹以及所包含的所有东西
	function deleteFolderAndFile(){
		if (confirm("此操作将会删除包含的子文件，确定要继续吗?")) {
			$.ajax({
                type: "Post",
                url : '${ctxPath}/oa/doc/docFolder/delContain.do?folderId='+staticID+"&type="+type,
                data: {},
                beforeSend: function () {
                    
                },
                success: function () {
                	location.reload(true);
                }
            }); 
		}
	}
	//删除文件
	function deletedoc(){
		if (confirm("确定要删除此文件吗?")) {
			$.ajax({
                type: "Post",
                url : '${ctxPath}/oa/doc/doc/del.do?ids='+staticID,
                data: {},
                beforeSend: function () {
                    
                },
                success: function () {
                	location.reload(true);
                }
            }); 
		}
	}
	
	//编辑文件夹
	function editFolder(){
		_OpenWindow({
      		 url: "${ctxPath}/oa/doc/docFolder/edit.do?pkId="+staticID,
              title: "编辑",
              width: 690, height: 300,
              ondestroy: function(action) {
            	  if (action == 'ok') 
              	location.reload(true);
              }
      	});	
	}
	//编辑文件
	function editDoc(){
		_OpenWindow({
   		 url: "${ctxPath}/oa/doc/doc/edit.do?pkId="+staticID,
           title: "编辑",
           width: 830, height: 800,
           ondestroy: function(action) {
        	   if (action == 'ok')
           	location.reload(true);
               }
           }
   	);
	}
	//返回
	function toBefore(){
		if(firstOpenId!=folderId){
	      history.back();}else{
	    	  alert("到达根目录,不能执行后退操作!");
	      }
     }
	
	//返回上级
	function toparentFolder(){
		$.ajax({
	         url: "${ctxPath}/oa/doc/docFolder/getParentFolderId.do?folderId="+folderId,//得到当前folder的parent
	            success: function (text) {
	            	if("0"!=text){//不等于文档的根才允许返回上层
	            	window.location.href="${ctxPath}/oa/doc/docFolder/listBoxWindow.do?folderId="+text+"&type="+type+"&isShare="+isShare+"&firstOpenId="+firstOpenId;
	            	}else{ alert("到达根目录,不能执行向上操作!");}
	            }
	        });
		
	}
	
	//当前文件夹内添加文件
	function addDoc(docType){
		_OpenWindow({
   		 url: "${ctxPath}/oa/doc/doc/edit.do?folderId="+folderId+"&type="+type+ '&docType='+docType,
           title: "新增",
           width: 830, height: 710,
           ondestroy: function(action) {
               if (action == 'ok') {
                   location.reload(true);
               }
           }
   	});
	}
	
	
	
	//当前文件夹内添加文件夹
	function addFolder(){
		var folder;
		 $.ajax({
           url: "${ctxPath}/oa/doc/docFolder/getOne.do?folderId="+folderId,
           success: function (text) {
           	folder = mini.decode(text);
           	_OpenWindow({
       			url : '${ctxPath}/oa/doc/docFolder/edit.do?parent='+folderId+"&path="+folder.userId+"&type="+folder.type,
       			title : "新增文件夹",
       			width : 600,
       			height : 400,
       			onload : function() {
       			},
       			ondestroy : function(action) {
       				if (action == 'ok') {
                    location.reload(true);
                }
       			}
       		});
           }
       }); 
	}
	
	//搜索页面文件和文件夹
	function finddaf(){
		 var key = document.getElementById("key").value.trim();
		 if(key==""){}else{
			 $(function(){
				 $(".folderBind").each(function(){
					if($(this).attr("title").indexOf(key)==-1){
						$(this).fadeOut();
					}
				 });
				});
		 }
		 
	}
	//清空搜索
	function clearsearch(){
		var key = document.getElementById("key").value;
		$(function(){
			$("#key").val("");
			$(".folderBind").fadeOut();
			$(".folderBind").fadeIn();
		});
	}
	
        </script> 

	
</body>
</html>