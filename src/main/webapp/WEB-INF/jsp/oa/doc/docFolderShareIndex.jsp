<%-- 
    Document   : 共享文档列表页
    Created on : 2015-10-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>共享文件夹列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body oncontextmenu="return false">
 <div id="toolbar1" class="mini-toolbar" style="padding:2px;">
		<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="location.reload(true)">页面刷新</a>
	    <span class="separator"></span>
	 <input class="text" id="key" name="key" />     <a class="mini-button" iconCls="icon-search"  plain="true" onclick="searchByKey" tooltip="包含该字段的条目">查找</a>
		

</div>
	<div class="mini-fit rx-grid-fit">
	<div id="datagrid1" class="mini-treegrid" style="width:100%;height:100%;"    showTreeLines="true"
     showTreeIcon="true" 
    treeColumn="name" idField="dafId" parentField="parent" resultAsTree="false"  ondrawnode="onDrawNode"  onnodedblclick="openNew()"
    allowResize="true" expandOnLoad="true" allowCellSelect="true">
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer">操作</div>
				<div name="name" field="name" width="160" headerAlign="center" align="left">文档名称</div>
				<div name="createName" field="createName" width="60" headerAlign="center">创建人</div>
				<div name="createTime" field="createTime"  dateFormat="yyyy-MM-dd" width="60" headerAlign="center" >创建时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	var folderId="${folderId}";
	var multi="${multi}";
	var type="${type}";
	var isShare="${isShare}";
	var grid=mini.get('datagrid1');
	grid.setUrl("${ctxPath}/oa/doc/doc/listTree.do?folderId="+folderId+"&multi="+multi+"&type="+type+"&isShare="+isShare);  
	grid.load();
	
	//把创建人设为名称
	grid.on("drawcell", function (e){
        var record = e.record,
        field = e.field,
        value = e.value;
        if(field=='createBy'){
        	if(value){
        		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
        	}else{
        		e.cellHtml='<span style="color:red">未知</span>';
        	}
        }
    });
    
    grid.on('update',function(){
    	_LoadUserInfo();
    });
  //设置文件夹图标
    function onDrawNode(e) {
        var tree = e.sender;
        var node = e.node;
        switch (node.type)
               {
               case "文件夹":
               	e.iconCls='icon-folder';
                 break;
               case "html":
                  	e.iconCls='icon-html';
                    break;
               case "doc":
            	   e.iconCls='icon-myword';
                 break;
               case "docx":
            	   e.iconCls='icon-myword';
                 break;
               case "xls":
            	   e.iconCls='icon-myexcel';
               	break;
               case "xlsx":
            	   e.iconCls='icon-myexcel';
                 break;
               case "ppt":
            	   e.iconCls='icon-myppt';
                 break;
               case "pptx":
            	   e.iconCls='icon-myppt';
                   break;
               }
    	if(node.name.length>15){
    		var shortnodeName=node.name.substring(0,14)+"…";
    	e.nodeHtml= '<a title="' +node.name+ '">' +shortnodeName+ '</a>';
    	}else{
    		e.nodeHtml= '<a title="' +node.name+ '">' +node.name+ '</a>';
    	}
    }
	
	//行功能按钮
    function onActionRenderer(e) {
        var record = e.record;
        var pkId = record.pkId;
        var s = '<span class="icon-detail" title="明细" onclick="detailRow2(\'' + pkId + '\')"></span>';
        return s;
    }
	
    function detailRow2() {    
    	var node=mini.get("datagrid1").getSelectedNode();
    	var pkId=node.dafId;
		var grid=mini.get('datagrid1');
		
		if(node.type!="文件夹"){
            _OpenWindow({
        		 url: "${ctxPath}/oa/doc/doc/get.do?pkId="+pkId,
                title: "文件明细",
                width: 690, height: 600,
                ondestroy: function(action) {
                	
                    if (action == 'ok') {
                        grid.reload();
                    }
                }
        	});
            }else
    		if(node.type=="文件夹"){
            	_OpenWindow({
	        		 url: "${ctxPath}/oa/doc/docFolder/get.do?pkId="+pkId,
	                title: "文件夹明细",
	                width: 690, height: 400,
	                ondestroy: function(action) {
	                	
	                    if (action == 'ok') {
	                        grid.reload();
	                    }
	                }
	        	});
            }
    }  
    
  //按某些字段查找
	function searchByKey(){
		 var key = document.getElementById("key").value;
		 
		 	var grid=mini.get('datagrid1');
		 	var encodekey=encodeURIComponent(key);
		 	grid.setUrl("${ctxPath}/oa/doc/doc/getByKey.do?key="+encodekey+"&folderId="+folderId+"&multi="+multi+"&type="+type+"&isShare="+isShare);                                            
			grid.load();
	}
  
  
  
	 //打开子窗口显示文件夹和文件 	
    function openNew(){
      var tree = mini.get("datagrid1");
 	  var node = tree.getSelectedNode();
 	 if(node.type=="文件"){
            _OpenWindow({
        		 url: "${ctxPath}/oa/doc/doc/get.do?pkId="+node.dafId,
                title: "文件明细",
                width: 690, height: 600,
                ondestroy: function(action) {
                	
                    if (action == 'ok') {
                        grid.reload();
                    }
                }
        	});
            }else{
 		 _OpenWindow({
    		 url: "${ctxPath}/oa/doc/docFolder/listBoxWindow.do?folderId="+node.dafId+"&type="+type+"&isShare="+isShare,
            title: "文件夹",
            width: 690, height: 480,
            ondestroy: function(action) {
            	
                if (action == 'ok') {
                    grid.reload();
                }
            }
    	});
 	  }
 	  }
  
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.doc.entity.Doc" winHeight="450"
		winWidth="700" entityTitle="共享文件" baseUrl="oa/doc/doc"  />
</body>
</html>