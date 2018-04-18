<%-- 
    Document   : 需求列表页
    Created on : 2015-3-21, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>需求列表管理</title>
<%@include file="/commons/list.jsp"%>
<style>
	.mini-toolbar{
		margin: 0
	}
	.titleBar{
		margin-top: 0;
		margin-bottom: 0;
	}
</style>
</head>
<body>
	<div style="height: 10px;"></div>
	<div id="toolbutton1" class=" form-outer">
        <redxun:toolbar entityName="com.redxun.oa.pro.entity.ReqMgr" excludeButtons="popupAddMenu,popupAttachMenu,detail,edit,remove,popupSearchMenu,fieldSearch,popupSettingMenu" >
		<div class="self-toolbar">
		 <a class="mini-button" iconCls="icon-add" onclick="addOne()" plain="true">增加需求</a>
		 <a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload();">刷新</a>
		 <a class="mini-button" iconCls="icon-flow-design" plain="true" href= "${ctxPath}/oa/pro/project/generateToWord.do?projectId=${projectId}" target="_blank">生成word</a>
		</div>
      </redxun:toolbar>
     </div>
     <div style="height: 10px;"></div>
	 <div class="mini-fit form-outer" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-treegrid" 
			style="width: 100%; height: 100%;" 
			showTreeLines="true" 
			showTreeIcon="true" 
			treeColumn="subject"
			idField="reqId" 
			parentField="parentId" 
			resultAsTree="false" 
			ondrawnode="onDrawNode" 
			onnodedblclick="openNew(e)" 
			allowResize="true"
			ondrawcell="onDrawCell" 
			onupdate="_LoadUserInfo();" 
			onbeforecollapse="_LoadUserInfo();"  
			collapse="_LoadUserInfo();" 
			expandOnLoad="true" 
			allowCellSelect="true"
		>
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="45" headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
				<div name="subject" field="subject" width="230" headerAlign="center">标题</div>
				<div name="version" field="version" width="120" headerAlign="center">版本号</div>
				<div field="status" width="80" headerAlign="center" renderer="onStatusRenderer">状态</div>
				<div field="checkerId" width="80" headerAlign="center">负责人</div>
				<div field="repId" width="80" headerAlign="center">审批人</div>
				<div field="exeId" width="80" headerAlign="center">执行人</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	mini.parse();
	var projectID=${projectId};
	var grid=mini.get('datagrid1');
	grid.setUrl("${ctxPath}/oa/pro/reqMgr/listMgr.do?projectId="+projectID);  //listData                                            
	grid.load();
	var Listnum=${ListSize};
	//绘制节点
	function onDrawNode(e) {
		e.iconCls='icon-folder';//绘制图标
        var tree = e.sender;
        var node = e.node;
        var myTrueSn=SnFn(node)+"--";
        var totlelength=''+node.subject;//转变为字符串
       
        		if(totlelength.length>10){
                	NodeName=myTrueSn+" "+node.subject.substring(0,9)+"…";
                	e.nodeHtml= '<a title="' +node.subject+ '">' +NodeName+ '</a>';
                	}else{
                		e.nodeHtml= '<a title="' +node.subject+ '">'+myTrueSn+" "+node.subject+ '</a>';
                   }
    }
	//绘制节点前面的序号
	function SnFn(node){
        var mySn;
        var parent=grid.getParentNode(node);
        var NodeName;
        
        	//获得同级节点的数组长度
        	var brothers=grid.getChildNodes(parent);
        	for(var i=0;i<brothers.length;i++){
        		if(brothers[i]==node)
        			{mySn=i+1;
        			}
        	}
        	 if(grid.getLevel(parent)>=0){
        		node=grid.getParentNode(node);	
        		mySn=SnFn(node)+"."+mySn;
        	}
        	 
        	return mySn;
	}
	
	
	
	//绘制负责人等等
	function onDrawCell(e) {
        var tree = e.sender;
        var node = e.node;
        var field=e.field;
        	if(field=='checkerId'){
        	e.cellHtml='<a class="mini-user"  userId="'+node.repId+'"></a>';
        	}
        	if(field=='repId'){
            	e.cellHtml='<a class="mini-user"  userId="'+node.checkerId+'"></a>';
            	}
        	if(field=='exeId'){
            	e.cellHtml='<a class="mini-user"  userId="'+node.exeId+'"></a>';
            	}
        	
         _LoadUserInfo();//加载user进去单元格
    } 
	 
	
	
	
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailMyRow(\'' + pkId + '\')"></span>';
                if(record.status=='DRAFT'||record.status=='NOTPASS'){//如果是草稿或者是没通过的需求才可以进行编辑
                    s+= ' <span class="icon-edit" title="编辑" onclick="editOne(\'' + pkId + '\')"></span>';
                }
                if(record.status=='DRAFT'||record.status=='NOTPASS'){
    	            s+= ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
                }
	                s+= ' <span class="icon-add" title="增加子需求" onclick="addSubReq(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
	        //增加子需求
		       function addSubReq(pkId) {
		        	var node=grid.getNode(pkId);
			    	var newNode = {};
			    	var level=grid.getLevel(node)+1;
			    	if(Listnum>0){
			    		_OpenWindow({
				    		url: __rootPath+"/oa/pro/reqMgr/edit.do?projectId="+projectID+"&parentId="+pkId+"&level="+level+"&subnode="+"YES",
				            title: "新增需求", width: 940, height: 700,
				            ondestroy: function(action) {
				                    grid.reload();
				            }
				    	});
			    	}else{
			    		alert("缺少未发布的版本，请新增版本");
			    	}
			    }
        	
        	
	        //增加需求
		    function addOne() {
	        	var level=0;
	        	var parent=0;
		    	if(Listnum>0){
		    		_OpenWindow({
			    		url: __rootPath+"/oa/pro/reqMgr/edit.do?projectId="+projectID+"&parentId="+parent+"&level="+level,
			            title: "新增需求", width: 940, height: 700,
			            ondestroy: function(action) {
			            	if(action == 'ok' && typeof(addCallback)!='undefined'){
			            		var iframe = this.getIFrameEl().contentWindow;
			            		addCallback.call(this,iframe);
			            	}else if (action == 'ok') {
			                    grid.reload();
			                }
			            }
			    	});
		    	}else{
		    		alert("缺少未发布的版本，请新增版本");
		    	}
		    }
	        
	       //需求明细
	       function detailMyRow(pkId) {
        _OpenWindow({
        	url: __rootPath+"/oa/pro/reqMgr/get.do?pkId=" + pkId,
            title: "需求明细",
            width: 750,
            height: 690,
        });
    }
	         
	     //编辑行数据
	       function editOne(pkId) {        
	           _OpenWindow({
	       		 url: __rootPath+"/oa/pro/reqMgr/edit.do?pkId="+pkId+"&projectId="+projectID,
	               title: "编辑需求",
	               width: 1100, height: 700,
	               ondestroy: function(action) {
	                   if (action == 'ok') {
	                       grid.reload();
	                   }
	               }
	       	});
	       }
	         
	     
	     //评论项目
	   	function discussRow(pkId){
	   		_OpenWindow({
	   		url:__rootPath+"/oa/pro/proWorkMat/edit.do?projectId="+pkId+"&type="+"REQ",
	   		title:"评论项目",
	   		width:960,
	   		height:730,
	   		ondestroy:function(action){
	   			if(action=='ok'){
	   				//XX操作
	   			}
	   		}
	   			
	   		});
	   	}
	     
	     
	     
	     //双击打开明细窗口
	     function openNew(e){
	    	 e.sender;
	    	var node=e.node;
	    	var ReqId=node.reqId;
	    	
	    	 _OpenWindow({
	    		 url:__rootPath+"/oa/pro/reqMgr/get.do?pkId="+ReqId,
	    		 title:"需求明细",
	    		 width:800,height:690,
	    				
	    	 });  
	     }
	     
	     
	   //刷新树
			function refreshSysTree() {
				var systree = mini.get("datagrid1");
				systree.load();
			}
        	
			function onStatusRenderer(e) {
	            var record = e.record;
	            var status = record.status;
	            
	            var arr = [ {'key' : 'PENDING AUDIT', 'value' : '待审核','css' : 'green'}, 
				            {'key' : 'DRAFT','value' : '草稿','css' : 'orange'} ];
				
				return $.formatItemValue(arr,status);
	        }
	     
	     
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.pro.entity.ReqMgr" winHeight="450"
		winWidth="700" entityTitle="需求" baseUrl="oa/pro/reqMgr" />
		
</body>
</html>