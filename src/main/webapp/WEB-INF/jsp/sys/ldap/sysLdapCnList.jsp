<%-- 
    Document   : LDAP用户列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>LDAP用户列表管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>

</head>
<body>
<!-- 
	<redxun:toolbar entityName="com.redxun.sys.ldap.entity.SysLdapCn" />
	-->
	<redxun:pager pagerId="pagerButtons"
		entityName="com.redxun.sys.ldap.entity.SysLdapCn" />

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/ldap/sysLdapCn/listData.do"
			idField="sysLdapUserId" multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20"
			allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22"
					headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">#</div>

<div field="name" width="120" headerAlign="center" allowSort="true">名称</div>
					
<div field="userAccount" width="120" headerAlign="center"
					allowSort="true">账户</div>
				<div field="userCode" width="120" headerAlign="center"
					allowSort="true">用户编号</div>
					
					
					<div field="status" width="120" headerAlign="center"
					allowSort="true">状态</div>

				
					
				<div field="userId" width="120" headerAlign="center"
					allowSort="true">用户ID</div>
				
				<div field="mail" width="120" headerAlign="center" allowSort="true">邮件</div>
				<div field="usnCreated" width="120" headerAlign="center"
					allowSort="true">USN_CREATED</div>
				<div field="usnChanged" width="120" headerAlign="center"
					allowSort="true">USN_CHANGED</div>

			
				<!-- 
				
				
				<div field="sysLdapOuId" width="120" headerAlign="center"
					allowSort="true">组织单元（主键）</div>
					
					
				<div field="tel" width="120" headerAlign="center" allowSort="true">电话</div>
				
					<div field="whenCreated" width="120" headerAlign="center"
					allowSort="true">LDAP创建时间</div>
				<div field="whenChanged" width="120" headerAlign="center"
					allowSort="true">LDAP更新时间</div>
					
					
				<div field="userPrincipalName" width="120" headerAlign="center"
					allowSort="true">用户主要名称</div>
				<div field="dn" width="120" headerAlign="center" allowSort="true">区分名
				</div>
				<div field="oc" width="120" headerAlign="center" allowSort="true">对象类型

				</div>
				 -->
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>' + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>' + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
	</script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1"
		entityName="com.redxun.sys.ldap.entity.SysLdapCn" winHeight="450"
		winWidth="700" entityTitle="LDAP用户" baseUrl="sys/ldap/sysLdapCn" />
</body>
</html>