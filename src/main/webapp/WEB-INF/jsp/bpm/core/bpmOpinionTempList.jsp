<%-- 
    Document   : [BPM_OPINION_TEMP]列表页
    Created on : 2017-09-26 18:02:24
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>暂存流程列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="add()">增加</a>
                     <a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>类型(inst,task):</span><input class="mini-textbox" name="Q_TYPE__S_LK"></li>
						<li><span>任务或实例ID:</span><input class="mini-textbox" name="Q_INST_ID__S_LK"></li>
						<li><span>意见:</span><input class="mini-textbox" name="Q_OPINION__S_LK"></li>
						<li><span>附件:</span><input class="mini-textbox" name="Q_ATTACHMENT__S_LK"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/bpm/core/bpmOpinionTemp/listData.do" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="type"  sortField="TYPE_"  width="120" headerAlign="center" allowSort="true">类型(inst,task)</div>
				<div field="instId"  sortField="INST_ID_"  width="120" headerAlign="center" allowSort="true">任务或实例ID</div>
				<div field="opinion"  sortField="OPINION_"  width="120" headerAlign="center" allowSort="true">意见</div>
				<div field="attachment"  sortField="ATTACHMENT_"  width="120" headerAlign="center" allowSort="true">附件</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
					+'<span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\',true)"></span>'
					+'<span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
			return s;
		}
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.bpm.core.entity.BpmOpinionTemp" winHeight="450"
		winWidth="700" entityTitle="BPM_OPINION_TEMP" baseUrl="bpm/core/bpmOpinionTemp" />
</body>
</html>