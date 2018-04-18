<%-- 
    Document   : 内部短消息发送页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>发送新消息</title>
<%@include file="/commons/edit.jsp"%>
<style>
	.shadowBox90 table th,
	.shadowBox90 table td{
		border: 1px solid #ececec;
		font-size: 14px;
	}
	
	.shadowBox90 table td{
		padding:4px;
	}
	
	.shadowBox90{
		padding-top: 8px;
	}
	
</style>
</head>
<body>

	<div class="heightBox"></div>
<!-- 	<ul class="shadowBox90">
	<li class="userBox">
		<div style="margin-left: 10px;">
			<span>收信人</span>
			<textarea 
				class="mini-textboxlist" 
				emptyText="请选择收信人" 
				allowInput="false" 
				validateOnChanged="false" 
				style="margin-top: 5px;" 
				id="receive" 
				name="receive" 
				width="250px" 
				height="50px"
			></textarea>
		</div>
		<div>
			<a 
				class="mini-button" 
				style=" margin-left: 10px; 
				margin-top: 5px;" 
				iconCls="icon-addMsgPerson" 
				onclick="addPerson()">新增收信人</a>
			<a 
				class="mini-button" 
				style=" margin-left: 10px; 
				margin-top: 5px;" 
				iconCls="icon-cancel" 
				onclick="clearPerson()">清空收信人</a>
		</div>
	</li>

	<li class="userBox">
	
		<div style="margin-left: 10px; margin-top: 0px;">
			<span>收信组</span>
			<textarea 
				class="mini-textboxlist" 
				emptyText="请选择收信组" 
				allowInput="false" 
				validateOnChanged="false" 
				id="receiveGroup" 
				name="receiveGroup" 
				width="250px" 
				height="50px"
			></textarea>
		</div>
		<div>
			<a 
				class="mini-button" 
				style=" margin-left: 10px; margin-top: 0px;" 
				iconCls="icon-addMsgGroup" 
				onclick="addGroup()"
			>新增收信组</a>
			<a 
				class="mini-button" 
				style=" margin-left: 10px; margin-top: 5px;" 
				iconCls="icon-cancel" 
				onclick="clearGroup()"
			>清空收信组</a>
		</div>
	
	</li>
	<li  class="userBox">
	
		<div id="subjectPart">
			<div style="float: left; margin-left: 10px; margin-top: 11px;">
				<span>任务主题：</span>
			</div>
			<div id="subject" style="float: left;"></div>
		</div>
		<div style="margin-left: 10px; clear: both;">
			<span>内　容</span>
			<textarea 
				class="mini-textarea" 
				emptyText="请输入信息" 
				id="sendContext" 
				vtype="maxLength:128" 
				name="sendContext" 
				style="width:450px;height:120px;"
			></textarea>
		</div>
	
	
	
	</li>
	
	</ul> -->
	
	<div class="shadowBox90">
		<table class="column_2_m" cellspacing="1" cellpadding="0">
			
			<tr>
				<th>收信人</th>
				<td>
					<textarea 
						class="mini-textboxlist" 
						emptyText="请选择收信人" 
						allowInput="false" 
						validateOnChanged="false"
						id="receive" 
						name="receive" 
						width="333px"
					></textarea>
				
					<a 
						class="mini-button"
						iconCls="icon-addMsgPerson" 
						onclick="addPerson()"
					>新增收信人</a>
					<a 
						class="mini-button"
						iconCls="icon-cancel" 
						onclick="clearPerson()"
					>清空收信人</a>
				</td>
			</tr>
			<tr>
				<th>收信组</th>
				<td>
				
					<textarea 
						class="mini-textboxlist" 
						emptyText="请选择收信组" 
						allowInput="false" 
						validateOnChanged="false" 
						id="receiveGroup" 
						name="receiveGroup" 
						width="333px"
					></textarea>
					
					<a 
						class="mini-button" 
						iconCls="icon-addMsgGroup" 
						onclick="addGroup()"
					>新增收信组</a>
					<a 
						class="mini-button"
						iconCls="icon-cancel" 
						onclick="clearGroup()"
					>清空收信组</a>
				</td>
			</tr>
			<tr id="subjectPart">
				<th>任务主题</th>
				<td id="subject"></td>
			</tr>
			<tr>
				<th>内　容</th>
				<td>
					<textarea 
						class="mini-textarea" 
						emptyText="请输入信息" 
						id="sendContext" 
						vtype="maxLength:128" 
						name="sendContext" 
						style="width:450px;height:120px;"
					></textarea>
				</td>
			</tr>
		
		</table>
	</div>
	
	
	<div style="text-align: center;border: none;" class="mini-toolbar dialog-footer topBtn">
		<a class="mini-button"  iconCls="icon-sendMsg" onclick="sendMsg()">发送</a>
		<a class="mini-button "  iconCls="icon-Reset" onclick="clearAll()">重置</a>
		<a class="mini-button" iconCls="icon-cancel" onclick="cancel()">取消</a>
	</div>
	<script type="text/javascript">
		addBody();
		$(function() {
			if ($("#subject").html() == "") {
				$("#subjectPart").hide();
			}
		});

		//发送新消息
		function sendMsg() {
			var infUser = mini.get('receive');//获取收信人
			var infGroup = mini.get('receiveGroup');//获取收信组
			var context = mini.get('sendContext');//获取内容
			if (infUser.getValue() == "" && infGroup.getValue() == "") {//判断两个收信是否为空
				mini.showTips({
					content : "收信人和收信组两个至少需要填写一个。",
					state : "info",
					x : "center",
					y : "bottom",
					timeout : 4000
				});
				return;
			}
			if (context.getValue() == "") {//判断正文是否为空
				mini.showTips({
					content : "消息内容不能为空。",
					state : "info",
					x : "center",
					y : "bottom",
					timeout : 3000
				});
				return;
			}
			if (context.getValue().length > 128) {//判断正文是否超过128个字符
				mini.showTips({
					content : "消息内容过长。",
					state : "info",
					x : "center",
					y : "bottom",
					timeout : 3000
				});
				return;
			}
			var userId = infUser.getValue();//收信人Id
			var userName = infUser.getText();//收信人名
			var groupId = infGroup.getValue();//收信组Id
			var groupName = infGroup.getText();//收信组名
			var contextMsg = context.getValue();//内容
			var linkMsg = $('#subject').html();
			//console.log($('#subject').html());

			//发送到后台
			_SubmitJson({
				url : __rootPath + '/oa/info/infInnerMsg/sendMsg.do',
				method : 'POST',
				data : {
					userId : userId,
					userName : userName,
					groupId : groupId,
					groupName : groupName,
					context : contextMsg,
					linkMsg : linkMsg
				},
				success : function(result) {
					if (!confirm("成功保存，是否写下一条消息？")) {
						CloseWindow('ok');
						return;
					} else {
						location.reload();
						return;
					}
				}
			});
		}
		//清空收信人
		function clearPerson() {
			var infUser = mini.get('receive');
			infUser.setValue("");
			infUser.setText("");
		}
		//清空收信组
		function clearGroup() {
			var infGroup = mini.get('receiveGroup');
			infGroup.setValue("");
			infGroup.setText("");
		}
		//重置所有
		function clearAll() {
			if (confirm("重置将清空所有数据,是否要重置?")) {
				var infUser = mini.get('receive');
				infUser.setValue("");
				infUser.setText("");
				var infGroup = mini.get('receiveGroup');
				infGroup.setValue("");
				infGroup.setText("");
				var context = mini.get('sendContext');
				context.setValue("");
			}
		}
		//取消按钮
		function cancel() {
			CloseWindow();
		}
		//增加收信人按钮
		function addPerson() {
			var infUser = mini.get('receive');
			infUser.setValue("");
			infUser.setText("");
			_UserDlg(false, function(users) {//打开收信人选择页面,返回时获得选择的user的Id和name
				var uIds = [];
				var uNames = [];
				//将返回的选择分别存起来并显示
				for (var i = 0; i < users.length; i++) {
					uIds.push(users[i].userId);
					uNames.push(users[i].fullname);
				}
				if (infUser.getValue() != '') {
					uIds.unshift(infUser.getValue().split(','));
				}
				if (infUser.getText() != '') {
					uNames.unshift(infUser.getText().split(','));
				}
				infUser.setValue(uIds.join(','));
				infUser.setText(uNames.join(','));
			});
		}
		//增加收信组
		function addGroup() {
			var infGroup = mini.get('receiveGroup');
			infGroup.setValue("");
			infGroup.setText("");
			_GroupDlg(false, function(users) {
				var uIds = [];
				var uNames = [];
				for (var i = 0; i < users.length; i++) {
					uIds.push(users[i].groupId);
					uNames.push(users[i].name);
				}
				if (infGroup.getValue() != '') {
					uIds.unshift(infGroup.getValue().split(','));
				}
				if (infGroup.getText() != '') {
					uNames.unshift(infGroup.getText().split(','));
				}
				infGroup.setValue(uIds.join(','));
				infGroup.setText(uNames.join(','));
			});
		}

		function setSubject(s) {
			s = s.replace(/rootPath/g, '${pageContext.request.contextPath}');
			$('#subject').html(s);

			$('.subject').on("click", function() {//绑定新的提交点击事件
				var url = $(this).attr('href1');
				_OpenWindow({
					title : '任务详细',
					height : 500,
					width : 780,
					//max:true,
					url : url,
				});
			});
			$("#subjectPart").show();
		}
	</script>
</body>
</html>