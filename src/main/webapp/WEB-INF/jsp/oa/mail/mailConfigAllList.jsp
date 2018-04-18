<%-- 
    Document   : 外部邮箱账号配置列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>邮箱配置列表</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div class="titleBar mini-toolbar" >
         <ul>
			<li>
				<a class="mini-button" iconCls="icon-add" onclick="addConfig">增加邮箱账号配置</a>
			</li>
			<li>
				<a class="mini-button" iconCls="icon-remove"  onclick="deleteConfig">删除邮箱账号配置</a>
			</li>
			<li class="clearfix"></li>
		</ul>
     </div>

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false" 
			url="${ctxPath}/oa/mail/mailConfig/getAllMailConfig.do" 
			idField="configId" 
			multiSelect="true" 
			showColumnsMenu="true" 
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			allowAlternating="true"
		>
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div type="indexcolumn" width="20" headerAlign="center">序号</div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="account" width="120" headerAlign="center" allowSort="true" align="center">邮箱账户名</div>
				<div field="mailAccount" width="120" headerAlign="center" allowSort="true" align="center">邮箱账号</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
        	var grid=mini.get("datagrid1");
        	
        	/*添加账号配置*/
        	function addConfig(){
    			_OpenWindow({
    				title : '添加账号配置',
    				url : __rootPath+ "/oa/mail/mailConfig/edit.do",
    				width : 720,
    				height : 600,
    				ondestroy : function(action) {
    					if(action=='ok')
    						grid.load();
    				}
    			});
        	}
        	
        	/*删除账号配置*/
        	function deleteConfig() {
    	        var rows = grid.getSelecteds();
    	        if (rows.length <= 0) {
    	        	alert("请选中一条记录");
    	        	return;
    	        }
    	        //行允许删除
    	        if(rowRemoveAllow && !rowRemoveAllow()){
    	        	return;
    	        }
    	        
    	        if (!confirm("确定将选中的账号配置？")) return;
    	        
    	        var ids = [];
    	        for (var i = 0, l = rows.length; i < l; i++) {
    	            var r = rows[i];
    	            ids.push(r.pkId);
    	        }
    	        _SubmitJson({
    	        	url:__rootPath+"/oa/mail/mailConfig/del.do",
    	        	method:'POST',
    	        	data:{ids: ids.join(',')},
    	        	 success: function(text) {
    	                grid.load();
    	            }
    	        });
        	}
        </script>


	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.mail.entity.MailConfig" winHeight="450" winWidth="700" entityTitle="邮箱账号配置" baseUrl="oa/mail/mailConfig" />
</body>
</html>