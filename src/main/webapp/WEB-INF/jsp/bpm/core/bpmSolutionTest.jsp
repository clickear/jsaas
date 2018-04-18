<%-- 
    Document   : 测试方案列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>业务流程解决方案测试列表管理</title>
<%@include file="/commons/list.jsp"%>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
</head>
<body>

	<div class="mini-toolbar">
		 <a class="mini-button" iconCls="icon-add" onclick="addSol()" plain="true">添加测试方案</a>
					<a class="mini-button" iconCls="icon-remove" plain="true" onclick="delSol()">删除测试方案</a>  		
	</div>
	<div class="mini-fit rx-grid-fit form-outer5" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/bpm/core/bpmSolution/listTestSolsBySolIdActDefId.do?solId=${param.solId}&actDefId=${param.actDefId}" idField="testSolId"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="testNo" width="120" headerAlign="center" allowSort="true">方案编号</div>
				<div field="memo" width="120" headerAlign="center" allowSort="true">测试方案描述</div>
				<div field="createTime" width="60" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">创建时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
			var solId='${param.solId}';
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var uid=record._uid;
	            var s = '<span class="icon-debug" title="测试" onclick="debugRow(\'' + uid + '\')"></span>'
	            		+ ' <span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
	        function addSol (){
	        	_OpenWindow({
		    		url: '${ctxPath}/bpm/core/bpmTestSol/edit.do?solId='+solId + '&actDefId=${param.actDefId}',
		            title: "新增测试方案", 
		            width: 700,
		            height: 400,
		            ondestroy: function(action) {
		            	if(action == 'ok') {
		                    grid.reload();
		                }
		            }
		    	});
	        }
	        
	        function delSol(){
	        	var ids=[];
	        	var rs=grid.getSelecteds();
	        	for(var i=0;i<rs.length;i++){
	        		ids.push(rs[i].pkId);
	        	}
	        	_SubmitJson({
	        		url:__rootPath+'/bpm/core/bpmTestSol/del.do?',
	        		data:{
	        			ids:ids.join(',')
	        		},
	        		success:function(result){
	        			
	        		}
	        	});
	        }
	        
	        //测试用例行
	        function debugRow(uid){
	        	var row=grid.getRowByUID(uid);
	        	_OpenWindow({
	        		iconCls:'icon-debug',
		    		url: '${ctxPath}/bpm/core/bpmTestSol/testCases.do?testSolId='+row.pkId+'&actDefId='+row.actDefId,
		            title: "用例测试"+row.testNo, 
		            width: 700,
		            height: 400,
		            ondestroy: function(action) {
		            	if(action == 'ok') {
		                    grid.reload();
		                }
		            }
		    	});
	        }
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmTestSol" winHeight="450"
		winWidth="700" entityTitle="测试方案"
		baseUrl="bpm/core/bpmTestSol" />
</body>
</html>