<%-- 
    Document   : Ldap配置列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Ldap配置列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" 	type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

</head>
<body>
	<redxun:toolbar entityName="com.redxun.sys.ldap.entity.SysLdapConfig">
		<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-downgrade" plain="true"
				onclick="ldapConn">LDAP连接</a> <a class="mini-button"
				iconCls="icon-reload" plain="true" onclick="syncData">同步数据</a>


		</div>
	</redxun:toolbar>
	<redxun:pager pagerId="pagerButtons"
		entityName="com.redxun.sys.ldap.entity.SysLdapConfig" />
	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/ldap/sysLdapConfig/listData.do"
			idField="sysLdapConfigId" multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>
					
					<div field="url" width="120" headerAlign="center" allowSort="true">地址</div>
			<div field="account" width="120" headerAlign="center"
					allowSort="true">账号名称</div>
			
				<div field="dnBase" width="120" headerAlign="center"
					allowSort="true">基本DN</div>
				<div field="dnDatum" width="120" headerAlign="center"
					allowSort="true">基准DN</div>
					
				<div field="statusCn" width="120" headerAlign="center"
					allowSort="true">状态中文</div>



				<!-- 
					
							<div field="status" width="120" headerAlign="center"
					allowSort="true">状态</div>
					
				<div field="password" width="120" headerAlign="center"
					allowSort="true">密码</div>
				<div field="attUserNo" width="120" headerAlign="center"
					allowSort="true">用户编号属性</div>
				<div field="attUserAcc" width="120" headerAlign="center"
					allowSort="true">用户账户属性</div>
				<div field="attUserName" width="120" headerAlign="center"
					allowSort="true">用户名称属性</div>
				<div field="attUserPwd" width="120" headerAlign="center"
					allowSort="true">用户密码属性</div>
				<div field="attUserTel" width="120" headerAlign="center"
					allowSort="true">用户电话属性</div>
				<div field="attUserMail" width="120" headerAlign="center"
					allowSort="true">用户邮件属性</div>
				<div field="attDeptName" width="120" headerAlign="center"
					allowSort="true">部门名称属性</div>
					
					 -->
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//将html标签解析为miniui控件。
		mini.parse();
		var grid = mini.get("datagrid1");
		//grid.load();
		grid.on("drawcell", function(e) {
			var record = e.record;
			var field = e.field;
			var value = e.value;
			if (field == 'statusCn') {
				if (value) {
					e.cellHtml = '<span  class="icon-ok">&nbsp;&nbsp;&nbsp;&nbsp;' + value + '</span>';
				} else {
					e.cellHtml = '<span  class="icon-no">&nbsp;&nbsp;&nbsp;&nbsp;没有连接</span>';
				}
			}
		});
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>' + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>' + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		//LDAP连接
		function ldapConn() {
			var row = grid.getSelected();
			if (row == null) {
				mini.alert("请选中一条记录");
				return;
			}
			var pkId = row.pkId;
			_SubmitJson({
				url : "${ctxPath}/sys/ldap/sysLdapConfig/ldapConn.do",
				method : 'POST',
				data : {
					ids : pkId
				},
				success : function(text) {
					//alert('text='+text.message);
					grid.load();
					mini.alert(text.message);
				}
			});
		}

		//同步数据
		function syncData() {
			var row = grid.getSelected();
			if (row == null) {
				mini.alert("请选中一条记录");
				return;
			}
			var pkId = row.pkId;
			_SubmitJson({
				url : "${ctxPath}/sys/ldap/sysLdapConfig/syncData.do",
				method : 'POST',
				data : {
					ids : pkId
				},
				success : function(text) {
					//alert('text='+text.message);
					grid.load();
					mini.alert(text.message);
				}
			});
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.sys.ldap.entity.SysLdapConfig" winHeight="450"
		winWidth="700" entityTitle="Ldap配置" baseUrl="sys/ldap/sysLdapConfig" />
</body>
</html>