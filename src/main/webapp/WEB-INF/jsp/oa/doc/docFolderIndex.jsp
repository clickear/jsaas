<%-- 
    Document   : 文件夹和文件共同（treegrid）列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文档列表管理</title>
<%@include file="/commons/list.jsp"%>
<style>.mini-panel-border{border-left:none;border-bottom: none;}</style>
</head>
<body>
	 <ul id="popupFileMenu" class="mini-menu" style="display:none;">
	    <li iconCls="icon-html" onclick="addRow3('html')">Html文档</li>
	     <li iconCls="icon-myword" onclick="addRow3('doc')">Word文档</li>
	    <li iconCls="icon-myexcel" onclick="addRow3('xls')">Excel文档</li>
	     <li iconCls="icon-myppt" onclick="addRow3('ppt')">PowerPoint文档</li>
    </ul>
    <ul id="rightClickFileMenu" class="mini-menu" style="display:none;">
	    <li iconCls="icon-html" onclick="addDocOnThat('html')">Html文档</li>
	    <li iconCls="icon-myword" onclick="addDocOnThat('doc')">Word文档</li>
	    <li iconCls="icon-myexcel" onclick="addDocOnThat('xls')">Excel文档</li>
	     <li iconCls="icon-myppt" onclick="addDocOnThat('ppt')">PowerPoint文档</li>

    </ul>
	
	 <div id="toolbar1" class="mini-toolbar" style="padding:2px;">
	  	<a class="mini-menubutton" iconCls="icon-add" plain="true" menu="#popupFileMenu">新增文件</a>
		<!-- a class="mini-button" iconCls="icon-add" onclick="addRow3" plain="true">新增文件</a-->
		<span class="separator"></span>
		<a class="mini-button" iconCls="icon-copyAdd" onclick="addFolder" plain="true">新增文件夹</a>
		<span class="separator"></span>
		<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="location.reload(true)">页面刷新</a>
	    <span class="separator"></span>
	 <input class="text text-border" id="key" name="key" />    
	  <a class="mini-button" plain="true" iconCls="icon-search" onclick="searchForSomeKey" tooltip="包含该字段的条目">查找</a>

    </div>
	<div class="mini-fit rx-grid-fit">
	<div id="datagrid1" class="mini-treegrid" style="width:100%;height:100%;"    showTreeLines="true"
    showTreeIcon="true" 
    treeColumn="name" idField="dafId" parentField="parent" resultAsTree="false"  ondrawnode="onDrawNode"  onnodedblclick="openNew()" 
    allowResize="true" expandOnLoad="true" allowCellSelect="true">
		
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer">操作</div>
				<div  name="name" field="name" width="160" headerAlign="center"  align="left">文档名称</div>
				<div name="createName" field="createName" width="60" headerAlign="center" >创建人</div>
				<div name="createTime" field="createTime"  dateFormat="yyyy-MM-dd" width="60" headerAlign="center" >创建时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
	mini.parse();	
	/*获取页面传来的值*/
	var folderId="${folderId}";
	var multi="${multi}";
	var type ="${type}";
	var isShare="${isShare}";
	var grid=mini.get('datagrid1');
	grid.setUrl("${ctxPath}/oa/doc/doc/listTree.do?folderId="+folderId+"&multi="+multi+"&type="+type);  //listData                                            
	grid.load();
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
	//toolbar新增文件夹按钮
	function addFolder(){
		var folder;
		 $.ajax({
          url: "${ctxPath}/oa/doc/docFolder/getOne.do?folderId="+folderId,
          success: function (text) {
          	folder = mini.decode(text);
          	_OpenWindow({
      			url : '${ctxPath}/oa/doc/docFolder/edit.do?parent='+folderId+"&path="+folder.userId+"&type="+folder.type,
      			title : "新增文件夹",
      			width : 650,
      			height : 340,
      			onload : function() {
      			},
      			ondestroy : function(action) {
      				if (action == 'ok') {
                   location.reload(true);
                   window.parent.tree.load();
               }
      			}
      		});
          }
      }); 
	}
	
	
	//编辑节点
	 function editMyRow() {    
		var tree = mini.get("datagrid1");
		var node = tree.getSelectedNode();
		var pkId=node.dafId;
		var grid=mini.get('datagrid1');
		if(node.type=="文件夹"){
        	_OpenWindow({
       		 url: "${ctxPath}/oa/doc/docFolder/edit.do?pkId="+pkId,
               title: "编辑文件夹",
               width: 690, height: 300,
               ondestroy: function(action) {
               	
                   if (action == 'ok') {
                       grid.reload();
                       window.parent.tree.load();
                   }
               }
       	});	
    	}else{
            _OpenWindow({
       		 url: "${ctxPath}/oa/doc/doc/edit.do?pkId="+pkId,
               title: "编辑文件",
               width: 850, height: 750,
               ondestroy: function(action) {
               	
                   if (action == 'ok') {
                       grid.reload();
                       window.parent.tree.load();
                   }
               }
       	});
           }
    }  
	 
	 //当前页面新增文件
	 function addRow3(docType) {  
			var grid=mini.get('datagrid1');
	        _OpenWindow({
	    		 url: "${ctxPath}/oa/doc/doc/edit.do?folderId="+folderId+"&type="+type + '&docType='+docType,
	            title: "新增文件",
	            width: 850, height: 780,
	            ondestroy: function(action) {
	                if (action == 'ok') {
	                    grid.reload();
	                }
	            }
	    	});
	    }  

	//删除节点
		function delMyRow() {
		 	var tree = mini.get("datagrid1");
			var node = tree.getSelectedNode();
			var isLeaf = tree.isLeaf(node);
			var ids=node.dafId;
			if (node.type!="文件夹") {
				if (confirm("确定删除此文件?")) {
					$.ajax({
		                type: "Post",
		                url : '${ctxPath}/oa/doc/doc/del.do?ids='+ids,
		                data: {},
		                beforeSend: function () {
		                },
		                success: function () {
		                }
		            }); 
					tree.removeNode(node);
					 window.parent.tree.load();
				}
			}else if(node.type=="文件夹"){
				if (confirm("确定删除此文件夹?")) {
					$.ajax({
		                type: "Post",
		                url : '${ctxPath}/oa/doc/docFolder/delContain.do?folderId='+ids+"&type="+type,
		                data: {},
		                beforeSend: function () { 
		                },
		                success: function () {
		                	tree.removeNode(node);
		                	 window.parent.tree.load();
		                }
		            }); 
					
				}
			}
			
		}
	
		//节点增加文件夹
		 function addrowfolder() {    
			var tree = mini.get("datagrid1");
			var node = tree.getSelectedNode();
			var pkId=node.dafId;
			var grid=mini.get('datagrid1');
			
			var folder;
			 $.ajax({
	          url: "${ctxPath}/oa/doc/docFolder/getOne.do?folderId="+folderId,
	          success: function (text) {
	          	folder = mini.decode(text);
	          	_OpenWindow({
	      			url : '${ctxPath}/oa/doc/docFolder/edit.do?parent='+pkId+"&path="+folder.userId+"&type="+folder.type,
	      			title : "新增文件夹",
	      			width : 600,
	      			height : 400,
	      			onload : function() {
	      			},
	      			ondestroy : function(action) {
	      				if (action == 'ok') {
	                   location.reload(true);
	                   window.parent.tree.load();
	               }
	      			}
	      		});
	          }
	      }); 
	    }  
		/* 
		
             var menu = mini.get("rightClickFileMenu");
             menu.showAtPos(e.pageX, e.pageY);
	  */
        	//行功能按钮
	        function onActionRenderer(e){	
				 var record = e.record;
	            var type=record.type;
	            	 var s='';
	            	  s+= '<span class="icon-detail"  title="明细" onclick="detailMyRow()"></span>';
	            	  s+= ' <span class="icon-edit" title="编辑" onclick="editMyRow()"></span>';  
	                  s+= ' <span class="icon-remove" title="删除" onclick="delMyRow()"></span>';
	                  if(type=="文件夹"){
	                	  s+= ' <span class="icon-copyAdd" title="新建文件夹" onclick="addrowfolder()"></span>';  
	                	  s+= ' <span class="icon-add" title="新建文件" onclick="addDocOnThis(event)"></span>';  
	                  }else{
	                	  //文件则增加移动到按钮
	            		  s+=' <span class="icon-transfer" title="移动" onclick="moveTo()"></span>';}
	                   
	            return s;
	        }
        	//移动文件
        	function moveTo(){
        		var tree = mini.get("datagrid1");
				var node = tree.getSelectedNode();
				var pkId=node.dafId;
				_OpenWindow({
		       		 url: "${ctxPath}/oa/doc/docFolder/select.do?docId="+pkId+"&type="+type,
		               title: "选中要移动到的文件夹",
		               width: 450, height: 600,
		               ondestroy: function(action) {
		               
		                   if (action == 'ok') {
		                		location.reload("true");
		                   }
		               }
		       	});	
        	}
        	
        	//明细功能按钮
	        function detailMyRow() {    
	        	var tree = mini.get("datagrid1");
				var node = tree.getSelectedNode();
				var pkId=node.dafId;
	    		var grid=mini.get('datagrid1');
	    		
	    		if(node.type=="文件夹"){
	            	_OpenWindow({
		        		 url: "${ctxPath}/oa/doc/docFolder/get.do?pkId="+pkId,
		                title: "文件夹明细",
		                width: 670, height: 400,
		                ondestroy: function(action) {
		                    if (action == 'ok') {
		                        grid.reload();
		                    }
		                }
		        	});
	            }else {
		            _OpenWindow({
		        		 url: "${ctxPath}/oa/doc/doc/get.do?pkId="+pkId,
		                title: "文件明细",
		                width: 800, height: 800,
		                ondestroy: function(action) {
		                    if (action == 'ok') {
		                        grid.reload();
		                    }
		                }
		        	});
		            }
	    		
	        }  
        	//条目按钮新增文档菜单
        	function addDocOnThis(e){
        		
        		e=e||window.event;
        		
        		var x=e.pageX || e.clientX+document.body.scrollLeft;
        		
        		var y=e.pageY || e.clientY+document.body.scrollTop;
        		
        		 var menu = mini.get("rightClickFileMenu");
                 menu.showAtPos(x,y);
        	}
        	
        	//条目按钮新增文档按钮
        	function addDocOnThat(docType){
        		var tree = mini.get("datagrid1");
				var node = tree.getSelectedNode();
				var pkId=node.dafId;
        		var grid=mini.get('datagrid1');
    	        _OpenWindow({
    	    		 url: "${ctxPath}/oa/doc/doc/edit.do?folderId="+pkId+"&type="+type+"&docType="+docType,
    	            title: "新增文档",
    	            width: 830, height: 710,
    	            ondestroy: function(action){
    	                if (action == 'ok') {
    	                    grid.reload();
    	                }
    	            }
    	    	});
        	}
        	
        	
        	//按某些字段查找
        	function searchForSomeKey(){
        		 var key = document.getElementById("key").value;
        		 	var grid=mini.get('datagrid1');
        		 	var encodekey=encodeURIComponent(key);
        			grid.setUrl("${ctxPath}/oa/doc/doc/getByKey.do?key="+encodekey+"&folderId="+folderId+"&multi="+multi+"&type="+type+"&isShare="+isShare);                                            
        			grid.load();
        	}
        	
        	
        //打开文档视图模式	
        function openNew(){
          var tree = mini.get("datagrid1");
     	  var node = tree.getSelectedNode();
     	 if(node.type=="文件夹"){
     			_OpenWindow({
			       		 url: "${ctxPath}/oa/doc/docFolder/listBoxWindow.do?folderId="+node.dafId+"&type="+type+"&firstOpenId="+node.dafId,
			               title: "文档视图",
			               width: 790, height: 580,
			               ondestroy: function(action) {
			               	if(action=='close')
			               		tree.load();
			               }
			       	});
	            }else{
	            	_OpenWindow({
		        		 url: "${ctxPath}/oa/doc/doc/get.do?pkId="+node.dafId,
		                title: "文件明细",
		                width: 690, height: 600,
		                ondestroy: function(action) {
		                	if(action=='close'||action=='ok'){
		                	location.reload(true);
		                	}
		                }
		        	});
     	  }
     	  }
       
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.doc.entity.Doc" winHeight="450"
		winWidth="700" entityTitle="文档" baseUrl="oa/doc/doc"  />
</body>
</html>