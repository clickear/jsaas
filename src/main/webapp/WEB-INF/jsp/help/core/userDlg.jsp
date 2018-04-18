<%
	//用户对话框示例
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户选择框</title>
   <%@include file="/commons/list.jsp"%> 
</head>
<body>
	<h2>用户对话框示例</h2>
	<input type="button" value="..." onclick="showUserDlg()"/>
	
	<input class="mini-buttonedit icon-user-button" name="user"  allowinput="false"  data-options="{single:true}"/>
	
	<input class="mini-buttonedit icon-user-button" name="userv" single="true" allowinput="false" data-options="{single:false}"/>
	
	<script type="text/javascript">
		mini.parse();
		
		$(function(){
			$(".icon-user-button").each(function(){
				var obj=$(this);
				var name=obj.children('input[type="hidden"]').attr('name');
				var userSel=mini.getByName(name);
				if(userSel){//加上事件
					userSel.on('buttonclick',function(){
						var single=userSel.single;
						_UserDlg(single,function(users){
							if(single){
								userSel.setValue(users.userId);
								userSel.setText(users.fullname);
							}else{
								var uIds=[];
								var uNames=[];
								for(var i=0;i<users.length;i++){
									uIds.push(users[i].userId);
									uNames.push(users[i].fullname);
								}
								userSel.setValue(uIds.join(','));
								userSel.setText(uNames.join(','));
							}
						});
						
					});
				}
			});
		});
		
		function showUserDlg(){
			_UserDlg(false,function(user){
					//为多个用户
					for(var i=0;i<user.length;i++){
						alert(user[i].fullname);
					}
					//返回单个用户
					//alert(user.fullname);
			});
		}
	</script>
</body>
</html>