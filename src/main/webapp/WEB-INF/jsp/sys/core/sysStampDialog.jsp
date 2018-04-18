<%-- 
    Document   : [office印章]列表页
    Created on : 2018-02-01 09:41:39
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[office印章]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                     
                     <a class="mini-button" iconCls="icon-ok" plain="true" onclick="onOk()">确定</a>
                 </td>
             </tr>
              <tr>
                  <td class="search-form" >
                     <ul>
						<li><span>签章名称:</span><input class="mini-textbox" name="Q_NAME__S_LK"></li>
						<li><span>签章用户:</span><input class="mini-textbox" name="Q_SIGN_USER__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/core/sysStamp/listData.do" idField="id"
			multiSelect="false" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">签章名称</div>
				<div field="signUser"  sortField="SIGN_USER_"  width="120" headerAlign="center" allowSort="true">签章用户</div>
				<div field="description"  sortField="DESCRIPTION_"  width="120" headerAlign="center" allowSort="true">描述</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	function onOk(){
		if (window.opener != null && !window.opener.closed) {
			var row= grid.getSelected ();
			window.opener.signStamp(row.stampId);
        }
        window.close();
	}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysStamp" winHeight="450"
		winWidth="700" entityTitle="office印章" baseUrl="sys/core/sysStamp" />
</body>
</html>