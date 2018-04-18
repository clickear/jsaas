<%-- 
    Document   : 流程实例列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html >
<html>
<head>
<title>流程实例列表管理</title>

<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="titleBar mini-toolbar" >
		<div class="searchBox">
			<form id="searchForm" class="search-form" style="display: none;">
				<ul>
					<li>
						<span class="text">单号：</span>
						<input class="mini-textbox" name="billNo" />
					</li>
					<li>
						<span class="text">事项：</span>
						<input class="mini-textbox" name="subject" style="width:200px;" />
					</li>
					<li class="clearfix"></li>
				</ul>					
				<ul>
					<li>
						<span class="text">申请时间从：</span>
						<input class="mini-datepicker" name="createtime1" format="yyyy-MM-dd" timeFormat="H:mm:ss" showTime="true"/>
					</li>
					<li>
						<span class="text">至</span><input class="mini-datepicker" name="createtime2" format="yyyy-MM-dd" timeFormat="H:mm:ss" showTime="true"/>
					</li>
					<li>
						<span class="text">审批时间从：</span>
						<input class="mini-datepicker" name="createtime3" format="yyyy-MM-dd" timeFormat="H:mm:ss" showTime="true"/>
					</li>
					<li>
						<span class="text">至</span><input class="mini-datepicker" name="createtime4" format="yyyy-MM-dd" timeFormat="H:mm:ss" showTime="true"/>
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="onSearch" >搜索</a>
						<a class="mini-button _reset" onclick="onReset" >清空</a>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>	
			<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
				<i class="icon-sc-lower"></i>
			</span>
		</div>
     </div>
	<div class="mini-fit rx-grid-fit">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false"
			url="${ctxPath}/oa/personal/bpmInst/myAttendsData.do" 
			idField="instId" 
			multiSelect="true" 
			showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			allowAlternating="true" 
			pagerButtons="#pagerButtons"
		>
			<div property="columns">
				<div type="indexcolumn" width="20">序号</div>
				<div field="billNo" sortField="bill_no_" width="80" headerAlign="center" allowSort="true">单号</div>
				<div field="subject" sortField="subject_" width="160" headerAlign="center" allowSort="true">事项</div>
				<div field="taskNodes" width="80" headerAlign="center" >当前节点</div>
				<div field="taskNodeUsers"    width="80" headerAlign="center">当前节点处理人</div>
				<div field="status" width="60" headerAlign="center" renderer="onStatusRenderer">运行状态</div>
				<div field="createBy" width="60" headerAlign="center">发起人</div>
				<div field="createTime" sortField="CREATE_TIME_" width="60" headerAlign="center" allowSort="true">创建时间</div>
				
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmInst" 
		winHeight="450" winWidth="700" entityTitle="流程实例" baseUrl="bpm/core/bpmInst" />
	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pk = record.instId;
	            var s = '<span class="icon-detail" title="明细" onclick="myDetailRow(\'' + pk + '\')"></span>';
	           
	            return s;
	        }
        	
        	function myDetailRow(pkId){
		    	   _OpenWindow({
		       			title:'流程明细',
		       			max:"true",
		       			url:__rootPath+'/bpm/core/bpmInst/inform.do?instId='+pkId,
		       			ondestroy: function(action){
		       			}
		       		});
		       }
        	
            function onStatusRenderer(e) {
	            var record = e.record;
	            var status = record.status;
	            
	            var arr = [ {'key' : 'DRAFTED','value' : '草稿','css' : 'orange'}, 
	   		                {'key' : 'RUNNING','value' : '运行中','css' : 'green'},
	   		                {'key' : 'SUCCESS_END','value' : '成功结束','css' : 'blue'}, 
	   		                {'key' : 'DISCARD_END','value' : '作废','css' : 'red'},
	   		                {'key' : 'ABORT_END','value' : '异常中止结束','css' : 'red'}, 
	   		                {'key' : 'PENDING','value' : '挂起','css' : 'gray'} 
	   		             ];
	   		
	   			return $.formatItemValue(arr,status);
	        }
        	
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;

	            //格式化日期
	            if (field == "createTime") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            
	            if(field=='createBy'){
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	            
	            if(field=="subject"){
	            	var pk = record.instId;
	            	e.cellHtml= '<a href="javascript:myDetailRow(\'' + pk + '\')">'+record.subject+'</a>';
	            }
	        });
	        
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
	        
	        var form=new mini.Form('#searchForm');
	        console.log(form)
	       function onSearch(){
	    	   grid.load(form.getData(true));
	       }
	       
	       function onReset(){
	    	   form.reset();
	    	   grid.load();
	       }
        </script>
	
</body>
</html>