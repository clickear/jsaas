<%-- 
    Document   : [office模板]列表页
    Created on : 2018-01-28 11:11:47
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[office模板]列表管理</title>
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
						<li><span>名称:</span><input class="mini-textbox" name="Q_NAME__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/core/sysOfficeTemplate/listData.do?Q_TYPE__S_EQ=${param.Q_TYPE__S_EQ}" idField="id"
			multiSelect="false" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">名称</div>
				<div field="docName"  sortField="DOC_NAME_"  width="120" headerAlign="center" allowSort="true">文件名</div>
				<div field="description"  sortField="DESCRIPTION_"  width="120" headerAlign="center" allowSort="true">描述</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var type="${param.Q_TYPE__S_EQ}";
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
		
		
		
		function onOk(){
			if (window.opener != null && !window.opener.closed) {
				var row= grid.getSelected ();
				window.opener.loadOfficeTemplate(row.docId,type);
	        }
	        window.close();
		}
		
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysOfficeTemplate" winHeight="450"
		winWidth="700" entityTitle="office模板" baseUrl="sys/core/sysOfficeTemplate" />
</body>
</html>