<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html >
	<head>
		<title>表单打印</title>
		<%@include file="/commons/customForm.jsp"%>
		<style type="text/css">
			body{
				background: #f7f7f7;
				height: auto;
			}
		
			.form-outer{
				border-right: none;
			}
			
			.shadowBox{
				box-shadow: none;
			}
			
			.shadowBox caption,
			.shadowBox th,
			.shadowBox td,
			.mini-grid-topRightCell,
			.mini-grid-headerCell,
			.mini-grid-topRightCell,
			.mini-grid-columnproxy{
				background: transparent;
			}
			.mini-grid-row:last-of-type .mini-grid-cell,
			#showCheckListDiv .mini-grid-topRightCell{
				border-bottom: none;
			}
			
			.mini-panel-viewport,
			td .mini-panel-border{
				border: 1px solid #ececec;
			}
			
			.mini-panel-border{
				border-radius: 0;
			}
			
			.mini-grid-columns>.mini-grid-columns-view>.mini-grid-table tr:nth-of-type(2) .mini-grid-headerCell{
				border-color: #ececec;
			}
			
			.mini-panel-viewport{
				border: none;
			}
			
			.mini-grid-vscroll{
				display: none;
			}
			
			#showImageDiv img{
				width: 100%;
			}
			
			.mini-grid-headerCell .mini-grid-headerCell-inner{
				color: #666;
			}
			
			#showCheckListDiv .mini-grid-border{
				border: 1px solid #ececec;
			}
			
			.shadowBox:last-of-type{
				margin-bottom: 0;
			}
			
			#form-panel>table{
				width: 100% !important;
			}
			
		</style>
	</head>
<body>
	<div class="mini-toolbar noPrint topBtn">
		<a class="mini-button" iconCls="icon-print" onclick="Print();" plain="true">打印</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow();" plain="true">关闭</a>
		
		<c:if test="${empty param['viewId'] &&  empty param['solId'] && empty param['setContent']}">
			<div id="showCheckList" name="showCheckList" class="mini-checkbox" readOnly="false" text="显示审批历史" onvaluechanged="showCheckList"></div>
			<div id="showImage" name="showImage" class="mini-checkbox" readOnly="false" text="显示流程图" onvaluechanged="showImage"></div>
		</c:if>
	</div>
	<div class="form-outer" id="formPrint">
		
		<div class="shadowBox">
			<div id="form-panel" style="margin:0 auto;"></div>
		</div>
		
		<c:if test="${empty param['viewId']}">
			<div style="padding:0">
				<div class="shadowBox" id="showCheckListDiv" style="display:none;padding-top:12px">
					<div style="margin:0 auto;">
						<div 
							id="checkGrid" 
							class="mini-datagrid" 
							style="width:100%;" 
							height="auto" 
							allowResize="false" 
							showPager="false"
							url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?instId=${param['instId']}&actInstId=${actInstId}&taskId=${param['taskId']}" 
							idField="jumpId" 
							allowAlternating="true"
						>
							<div property="columns">
								<div type="indexcolumn" width="20"></div>
								<div field="createTime" dateFormat="yyyy-MM:dd HH:mm" width="60" headerAlign="center" cellStyle="font-size:18px" >发送时间</div>
								<div field="completeTime" dateFormat="yyyy-MM:dd HH:mm" width="60" headerAlign="center" cellStyle="font-size:18px" >处理时间</div>
								<div field="durationFormat" width="60">停留时间</div>
								<div field="nodeName" width="90" headerAlign="center"  cellStyle="">节点名称</div>
								<div field="handlerId" width="40" headerAlign="center" cellStyle="font-size:10px">操作者</div>
								<div field="checkStatusText" width="60" headerAlign="center"  cellStyle="font-size:10px">操作</div>
								<div field="remark" width="210" headerAlign="center" cellStyle="line-height:10px;">处理意见</div>
							</div>
						</div>
					</div>
				</div>
				
				<div id="showImageDiv" class="shadowBox" style="display:none;">
					<div style="text-align:center">
						<img src="${ctxPath}/bpm/activiti/processImage.do?instId=${param['instId']}&actInstId=${param['actInstId']}&taskId=${param['taskId']}"  />
					</div>
				</div>
			
			</div>
		</c:if>
	</div>
	<script type="text/javascript">
		
		function setData(data){
			
			var url="${ctxPath}/bpm/form/bpmFormView/getPrintForm.do";
			var params={
					taskId:"${param['taskId']}",
					solId:"${param['solId']}",
					instId:"${param['instId']}",
					formAlias:"${param['formAlias']}",
					json:data
				}
			$.post(url,params,function(result){
				var html="";
				for(var i=0;i<result.length;i++){
					var o=result[i];
					html+=o.content;
				}
				$("#form-panel").html(html);
				renderMiniHtml({},result);
				loadGrid();
			})
		}
		
		function loadGrid(){
		
			var grid=mini.get('checkGrid');
			grid.load();
			grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	          	var ownerId=record.ownerId;
	            if(field=='handlerId'){
	            	if(ownerId && ownerId!=value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>&nbsp;(原审批人:'+ '<a class="mini-user" iconCls="icon-user" userId="'+ownerId+'"></a>'+')';
	            	}else if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            } 
	        });
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
		}
		
	
		
		function showCheckList(){
			var div=$("#showCheckListDiv");
			if(div.css('display')=='none'){
				div.css('display','');
			}else{
				div.css('display','none');
			}
		}
		
		function showImage(){
			var div=$("#showImageDiv");
			if(div.css('display')=='none'){
				div.css('display','');
			}else{
				div.css('display','none');
			}
		}
	</script>
</body>
</html>