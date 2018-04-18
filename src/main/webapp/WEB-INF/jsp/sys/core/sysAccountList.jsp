<%-- 
    Document   : 账号列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>账号列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.sys.core.entity.SysAccount" />

	<div class="mini-fit rx-grid-fit">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/sys/core/sysAccount/listData.do?tenantId=${param['tenantId']}" idField="accountId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="name" width="120" headerAlign="center" allowSort="true">账号名称</div>
				<div field="userId" width="120" headerAlign="center" allowSort="true">绑定用户</div>
				<div field="status" width="120" headerAlign="center" allowSort="true" renderer="onStatusRenderer">状态</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysAccount" 
		tenantId="${param['tenantId']}"
		winHeight="450" winWidth="700" entityTitle="账号" baseUrl="sys/core/sysAccount" />
	<script type="text/javascript">
        	//编辑
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
	            		+ ' <span class="icon-modify-pwd" title="重设密码" onclick="resetPassword(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + uid + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
	            return s;
	        }
        	
        	//重设置密码
	        function resetPassword(pk){
	        	_OpenWindow({
	        		title:'重设置密码',
	        		url:__rootPath+'/sys/core/sysAccount/resetPwd.do?accountId='+pk,
	        		width:600,
	        		height:400
	        	});
	        }
        	
	        function onStatusRenderer(e) {
	            var record = e.record;
	            var status = record.status;
	           var arr = [ {'key' : 'ENABLED','value' : '启用','css' : 'green'}, 
	   		               {'key' : 'DISABLED','value' : '禁止','css' : 'red'} ];
	   		return $.formatItemValue(arr,status);
	        }
        	
	        grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	            var status = record.status;
	            if(field=='userId'){
	            	if(value){
	            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
	            	}else{
	            		e.cellHtml='<span style="color:red">无</span>';
	            	}
	            }
	        });
	        
	        grid.on('update',function(){
	        	_LoadUserInfo(true);
	        });
        	
        </script>
	
</body>
</html>