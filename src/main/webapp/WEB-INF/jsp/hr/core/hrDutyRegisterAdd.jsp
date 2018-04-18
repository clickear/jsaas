<%-- 
    Document   : [HrDutyRegister]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>[HrDutyRegister]列表管理</title>
<%@include file="/commons/list.jsp"%>
<style >
html,body
{
    width:100%;
    height:100%;
    border:0;
    margin:0;
    padding:0;
    overflow:visible;
    background: #fff;
}
.RegisterBox{
	width: 1000px;
	margin: 0 auto;
}

.RegisterBox > h1{
	font-weight: normal;
	font-family: '宋体' !important;
	font-size: 22px;
	text-align: center;
	color: #666;
	padding: 20px 0 6px 0;
}

.RegisterBtn{
	text-align: center;
}
</style>
</head>
<body>
	<div class="RegisterBox">
		<h1>我的考勤</h1>
		<div class="RegisterBtn">
			<a id="in" class="mini-button" plain="true" onclick="signIn">签到</a>
			<a id="out" class="mini-button" plain="true" onclick="signOut">签退</a>
			<!-- <a id="check" class="mini-button" style="margin-left: 100px;" onclick>查看考勤记录</a> -->
		</div>
	</div>
	
	<script type="text/javascript">
	mini.parse();
		$(function(){
			_SubmitJson({
				url:__rootPath+"/hr/core/hrDutyRegister/isShowButton.do",
				data:{},
				method:'POST',
				showMsg:false,
				success:function(result){
					var data=result.data;
					if(!data.isPlan){
						mini.get("in").setEnabled(false);
						mini.get("out").setEnabled(false);
					}else{
						if(!data.isShowIn){
							mini.get("in").setEnabled(false);
						}
						if(!data.isShowOut){
							mini.get("out").setEnabled(false);
						}
					}
				}
			});
		});
		
		function signIn(){
			_SubmitJson({
				url:__rootPath+"/hr/core/hrDutyRegister/signIn.do",
				data:{},
				method:'POST',
				showMsg:false,
				success:function(result){
					alert(result.message);
				}
			});
		}
		
		function signOut(){
			_SubmitJson({
				url:__rootPath+"/hr/core/hrDutyRegister/signOut.do",
				data:{},
				method:'POST',
				showMsg:false,
				success:function(result){
					alert(result.message);
				}
			});
		}
	</script>
</body>
</html>