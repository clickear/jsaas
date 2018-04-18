
<%-- 
    Document   : [维度授权]编辑页
    Created on : 2017-05-16 14:12:56
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[维度授权]编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<rx:toolbar toolbarId="toolbar1" pkId="osDimensionRight.id" />
		<div class="shadowBox90">
			<div id="p1" class="form-outer">
				<form id="form1" method="post">
					<div class="form-inner">
						<input id="rightId" name="rightId" class="mini-hidden" value="${osDimensionRight.rightId}" />
						<input id="dimId" name="dimId" class="mini-hidden" value="${osDimensionRight.dimId}" />
						<table class="table-detail column_2" cellspacing="1" cellpadding="0">
							<caption>[${osDimension.name}]维度授权</caption>
							<tr>
								<th>用　户</th>
								<td colspan="3"><input id="userId" name="userId"   class="mini-textboxlist" allowInput="false" style="width: 90%;"  value="${osDimensionRight.userId}" />
								<a class="mini-button"  onclick="selUser()">选择</a>
								</td>
							</tr>
							<tr>
								<th >用户组</th>
								<td colspan="3"><input id="groupId" name="groupId"   class="mini-textboxlist" allowInput="false" style="width: 90%;" value="${osDimensionRight.groupId}" />
								<a class="mini-button"  onclick="selGroup()">选择</a>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	<rx:formScript formId="form1" baseUrl="sys/org/osDimensionRight"
		entityName="com.redxun.sys.org.entity.OsDimensionRight" />
		<script type="text/javascript">
		var userId=mini.get("userId");
		var groupId=mini.get("groupId");
		function selGroup(){
			_GroupDlg(false,function(groups){
				var ids=[];
				var names=[];
				for(var i=0;i<groups.length;i++){
					ids.push(groups[i].groupId);
					names.push(groups[i].name);
				}
				groupId.setValue(ids.join(","));
				groupId.setText(names.join(","));
			});
		}
		
		function selUser(){
			_UserDlg(false,function(users){
				var ids=[];
				var names=[];
				for(var i=0;i<users.length;i++){
					ids.push(users[i].userId);
					names.push(users[i].fullname);
				}
				userId.setValue(ids.join(","));
				userId.setText(names.join(","));
			});
		}
		
		function initText(){
			var userIds=userId.getValue();
			var groupIds=groupId.getValue();
			$.ajax({
				url:"${ctxPath}/sys/org/osDimensionRight/getNames.do",
				type:"post",
				data:{"userIds":userIds,"groupIds":groupIds},
				success:function (result){
					if(result.userNames){
						userId.setText(result.userNames);
					}
					if(result.groupNames){
						groupId.setText(result.groupNames);
					}
				}
			});
		}
		
		initText();
		
		addBody();
		
		</script>
</body>
</html>