<%-- 
    Document   : [CalGrant]列表页
    Created on : 2017-1-7, 0:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<html>
<head>
<title>日历分配列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
	    <table style="width:100%;">
	        <tr>
	        <td >
	            <a class="mini-button" iconCls="icon-user" plain="true" enabled="true" onclick="grantToUser()">授权用户</a>
	            <a class="mini-button" iconCls="icon-group" plain="true" enabled="true" onclick="grantToGroup()">授权用户组</a>
	            <a class="mini-button" iconCls="icon-remove" plain="true" enabled="true" onclick="remove()">删除</a>
	        </td>
	        </tr>
	    </table>
	</div>
	
	<div class="mini-fit form-outer" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/oa/calendar/calGrant/getSettingGrants.do?settingId=${settingId}" idField="grantId"  showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" multiSelect="true">
			<div property="columns">
				<!-- <div name="action" cellCls="actionIcons" width="50" headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div> -->
					<div type="checkcolumn" width="20"></div>
				<div field="grantType" width="120" headerAlign="center" allowSort="true">分配类型</div>
				<div field="belongWho" width="120" headerAlign="center" allowSort="true">被分配者</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//行功能按钮
        	var grid=mini.get("datagrid1");
	       /*  function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        } */
	        
        	function grantToUser(){
        		_UserDlg(false,function(users){
        			var userIds=[];
        			for(var i=0;i<users.length;i++){
        				userIds.push(users[i].userId);
        			}
        			_SubmitJson({
        				url:__rootPath+'/oa/calendar/calGrant/saveUserGroups.do',
        				data:{
        					settingId:'${settingId}',
        					userIds:userIds.join(',')
        				},
        				success:function(result){
        					grid.load();
        				}
        			});
        		});
        	}
        	
        	function grantToGroup(){
        		_GroupDlg(false,function(groups){
        			var groupIds=[];
        			for(var i=0;i<groups.length;i++){
        				groupIds.push(groups[i].groupId);
        			}
        			_SubmitJson({
        				url:__rootPath+'/oa/calendar/calGrant/saveUserGroups.do',
        				data:{
        					settingId:'${settingId}',
        					groupIds:groupIds.join(',')
        				},
        				success:function(result){
        					grid.load();
        				}
        			});
        		});
        	}
	        
	        mini.parse();
	        var grid=mini.get("datagrid1");
        	grid.on("drawcell", function (e) {
	            var record = e.record,
		        field = e.field,
		        value = e.value;
	            if(field=='grantType'){
	            	if(value=="GROUP"){
	            		e.cellHtml='组';
	            	}else{
	            		e.cellHtml='用户';
	            	}
	            }
	        });
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.calendar.entity.CalGrant" winHeight="450" winWidth="700"
		entityTitle="日历分配" baseUrl="oa/calendar/calGrant" />
</body>
</html>