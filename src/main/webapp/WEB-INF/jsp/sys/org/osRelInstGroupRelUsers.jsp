<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
     <title>组间关系内用户管理</title>
    <%@include file="/commons/list.jsp"%>
</head>
<body>

	<div class="mini-toolbar">
		<table style="width:100%">
			<tr>
				<td>
					<a class="mini-button" iconCls="icon-add" plain="true" onclick="joinUser">加入用户</a>
					<a class="mini-button" iconCls="icon-close" plain="true" onclick="CloseWindow()">关闭</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" 
			url="${ctxPath}/sys/org/osRelInst/groupRelUsersData.do?p1=${param['p1']}&p2=${param['p2']}" showPager="false" idField="userId" multiSelect="true" 
			sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"  headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="fullname" width="120">姓名</div>
				<div field="userNo" width="100">用户编号</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		mini.parse();
		var grid=mini.get('datagrid1');
		var p1="${param['p1']}";
		var p2="${param['p2']}";
		grid.load();
		function onActionRenderer(e){
			var record = e.record;
            var pk = record.pkId;
            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pk + '\')"></span>'
                    + ' <span class="icon-close" title="移除" onclick="removeRow(\'' + pk + '\')"></span>';
            return s;
		}
		//编辑行
		function detailRow(pk){
			_OpenWindow({
        		url:'${ctxPath}/sys/org/osUser/get.do?pkId='+pk,
        		title:'用户明细',
        		max:true,
        		height:350,
        		width:650
        	});
		}
		//移除行
		function removeRow(userId){
			if(!confirm('确定要移除该用户其所属的用户组关系吗？')){
				return;
			}
			
			_SubmitJson({
				url:__rootPath+'/sys/org/osRelInst/delByTwoPartyUserId.do',
				data:{
					userId:userId,
					p1:p1,
					p2:p2
				},
				success:function(){
					grid.load();
				}
			});
		}
		//加入用户
		function joinUser(){
			_UserDlg(false,function(users){
				
				var userIds=[];
				
				for(var i=0;i<users.length;i++){
					userIds.push(users[i].userId);
				}
				
				_SubmitJson({
					url:__rootPath+'/sys/org/osRelInst/joinTwoGroupUsers.do',
					data:{
						userIds:userIds.join(','),
						p1:p1,
						p2:p2
					},
					success:function(){
						grid.load();
					}
				});
			});
			
		}
	</script>
</body>
</html>