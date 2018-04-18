<%-- 
    Document   : 流程实例列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
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
   						<input class="mini-textbox" name="Q_billNo_S_LK"   />
					</li>
					<li>
						<span class="text">发起时间：</span>
                        <input name="Q_createTime_D_GE" class="mini-datepicker" format="yyyy-MM-dd" />
					</li>
					<li>
						<span class="text">至</span><input name="Q_createTime_D_LE" class="mini-datepicker" format="yyyy-MM-dd" />
					</li>
					<li>
						<span class="text"> 标題：</span>
   						<input class="mini-textbox" name="Q_subject_S_LK"   />
					</li>
					<li class="searchBtnBox">
						<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
						<a class="mini-button _reset" onclick="onClearList(this)" >清空</a>
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
			url="${ctxPath}/oa/personal/MybpmSolution/myApp.do" 
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
				<div field="billNo" width="80" headerAlign="center" allowSort="true">单号</div>
				<div field="subject" width="220" headerAlign="center" allowSort="true">标题</div>
				<div field="taskNodes" width="80" headerAlign="center" >当前节点</div>
				<div field="taskNodeUsers"    width="80" headerAlign="center">当前节点处理人</div>						
				<div field="createBy" width="60" headerAlign="center" allowSort="true">发起人</div>
				<div field="createTime" width="60" headerAlign="center" allowSort="true">创建时间</div>
				<div field="endTime" width="60" headerAlign="center" allowSort="true">结束时间</div>
				<div field="status" width="60" headerAlign="center" allowSort="true">运行状态</div>
			</div>
		</div>
	</div>
<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmInst" winHeight="450" winWidth="700" entityTitle="流程实例"
		baseUrl="bpm/core/bpmInst" />
	<script type="text/javascript">
        	
        	
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	            var status = record.status;
	            //格式化日期
	            if (field == "createTime") {
	                if (mini.isDate(value)) e.cellHtml = mini.formatDate(value, "yyyy-MM-dd HH:mm");
	            }
	            
	            if(field=='subject'){
	            	e.cellHtml= '<a href="javascript:myDetailRow(\'' + record.pkId + '\')">'+record.subject+'</a>';
	            }
	            
	            if(field=='createBy'){
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	            
	            if(field=="status"){
	            	  var arr = [ {'key' : 'DRAFTED','value' : '草稿','css' : 'orange'}, 
	  	   		                {'key' : 'RUNNING','value' : '运行中','css' : 'green'},
	  	   		                {'key' : 'SUCCESS_END','value' : '成功结束','css' : 'blue'}, 
	  	   		                {'key' : 'DISCARD_END','value' : '作废','css' : 'red'},
	  	   		                {'key' : 'ABORT_END','value' : '异常中止结束','css' : 'red'}, 
	  	   		                {'key' : 'PENDING','value' : '挂起','css' : 'gray'},
	    			            {'key' : 'CANCEL_END','value' : '取消结束','css' : 'red'}];
	            	
	    			
	            	e.cellHtml= $.formatItemValue(arr,status);
	            }

	            	

	        });
	        
	        grid.on('update',function(){
	        	_LoadUserInfo();
	        });
	        
	       function myDetailRow(pkId){
	    	   _OpenWindow({
	       			title:'流程明细',
	       			max:"true",
	       			url:__rootPath+'/bpm/core/bpmInst/inform.do?instId='+pkId,
	       			ondestroy: function(action){
	       			}
	       		});
	       }
        </script>
	
</body>
</html>