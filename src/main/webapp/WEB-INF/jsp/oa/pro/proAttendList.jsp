<%-- 
    Document   : [ProAttend]列表页
    Created on : 2015-12-17, 10:11:48
    Author     : cmc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<html>
<head>
	<title>项目人员管理</title>
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
		<redxun:toolbar entityName="com.redxun.oa.pro.entity.ProAttend"
			excludeButtons="popupAddMenu,popupAttachMenu,detail,edit,remove,popupSearchMenu,fieldSearch,popupSettingMenu" >
			<div class="self-toolbar">
				<a class="mini-button" iconCls="icon-add" onclick="addOne()" plain="true">增加人员</a>
			</div>
		</redxun:toolbar>
	</div>
	<div style="height: 10px;"></div>
	<div class="mini-fit form-outer" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid"
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/oa/pro/proAttend/myList.do?projectId=${projectId}" 
			idField="attId"
			multiSelect="true" 
			showColumnsMenu="true"    
			onrowdblclick="openDetail(e)"
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20"
			allowAlternating="true"
		>
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				 <div field="usernames" name="usernames" width="120" headerAlign="center"  >参与人</div>
				<div field="groupnames" name="groupnames" width="120" headerAlign="center"  >参与组</div> 
				<div field="partType" width="120" headerAlign="center"  >参与类型</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	mini.parse();
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailMyRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editMyRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
	      //添加人员
		    function addOne() {
		    	_OpenWindow({
		    		url: __rootPath+"/oa/pro/proAttend/edit.do?parentId="+${projectId},
		            title: "新增", width: 800, height: 300,
		            ondestroy: function(action) {
		            	if(action == 'ok' && typeof(addCallback)!='undefined'){
		            		var iframe = this.getIFrameEl().contentWindow;
		            		addCallback.call(this,iframe);
		            	}else if (action == 'ok') {
		                    grid.reload();
		                }
		            }
		    	});
		    }
	      
	      //编辑
		    function editMyRow(pkId) {        
		        _OpenWindow({
		    		 url: __rootPath+"/oa/pro/proAttend/edit.do?pkId="+pkId,
		            title: "编辑人员",
		            width: 800, height: 300,
		            ondestroy: function(action) {
		                if (action == 'ok') {
		                    grid.reload();
		                }
		            }
		    	});
		    }
		    
		    
		    //参与人员明细
		    function detailMyRow(pkId) {
		        _OpenWindow({
		        	url: __rootPath+"/oa/pro/proAttend/get.do?pkId=" + pkId,
		            title: "参与人员明细", width: 650, height: 410,
		        });
		    }
		    
		    
		  //双击打开项目明细
			function openDetail(e){
				e.sender;
				var record=e.record;
				var pkId=record.pkId;
				detailMyRow(pkId);
			}
        	
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.oa.pro.entity.ProAttend" winHeight="450"
		winWidth="700" entityTitle="参与人员" baseUrl="oa/pro/proAttend" />
			 <script type="text/javascript">
	 	grid.on('drawcell',function(e){
	 		 var record = e.record,
		        field = e.field,
		        value = e.value;
             if(field=="partType"){
            	if(value=='JOIN'){
            		e.cellHtml="参与";
            	}
            	else if(value=='RESPONSE'){
            		e.cellHtml="负责";
            	}
            	else if(value=='APPROVE'){
            		e.cellHtml="审批";
            	}
            	else if(value=='PAY_ATT'){
            		e.cellHtml="关注";
            	}
           }
	 	});
	 </script>
</body>
</html>