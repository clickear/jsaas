<%-- 
    Document   : 系统模块明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>系统模块明细</title>
<%@include file="/commons/get.jsp"%>
<style type="text/css">
	.mini-panel-border .mini-grid-row:last-of-type .mini-grid-cell{
		border-bottom: none;
	}
	h5{
		font-weight: normal;
		font-size: 18px;
		margin: 40px 0 10px;
		text-align: center;
	}
	
	#datagrid1 .mini-grid-rows-view>.mini-grid-rows-content{
		border: 1px solid #ececec;
		border-top: none;
	}
</style>
</head>
<body>
<%-- 	<rx:toolbar toolbarId="toolbar1" /> --%>
	<div class="heightBox"></div>
	<div id="form1" class="form-outer shadowBox90">
		<div>
			<table style="width: 100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
				<caption>基本信息</caption>
				<tr>
					<th>模块标题</th>
					<td colspan="3">${sysModule.title}</td>
				</tr>
				<tr>
					<th>描　　述 </th>
					<td>${sysModule.descp}</td>

					<th>映射URL地址</th>
					<td>${sysModule.reqUrl}</td>
				</tr>
				<tr>
					<th>icon地址样式 </th>
					<td>${sysModule.iconCls}</td>

					<th>简　　称</th>
					<td>${sysModule.shortName}</td>
				</tr>
				<tr>
					<th>所属子系统 </th>
					<td>${sysModule.sysId}</td>

					<th>表　　名 </th>
					<td>${sysModule.tableName}</td>
				</tr>
				<tr>
					<th>实  体  名 </th>
					<td>${sysModule.entityName}</td>

					<th>命名空间</th>
					<td>${sysModule.namespace}</td>
				</tr>
				<tr>
					<th>主键字段名</th>
					<td>${sysModule.pkField}</td>

					<th>数据库主键字段</th>
					<td>${sysModule.pkDbField}</td>
				</tr>
				<tr>
					<th>编码字段名</th>
					<td>${sysModule.codeField}</td>

					<th>排序字段名</th>
					<td>${sysModule.orderField}</td>
				</tr>
				<tr>
					<th>日期字段</th>
					<td>${sysModule.dateField}</td>

					<th>年份字段</th>
					<td>${sysModule.yearField}</td>
				</tr>
				<tr>
					<th>月份字段</th>
					<td>${sysModule.monthField}</td>

					<th>季度字段</th>
					<td>${sysModule.sensonField}</td>
				</tr>
				<tr>
					<th>文件字段</th>
					<td>${sysModule.fileField}</td>
					<th>是否启用</th>
					<td>${sysModule.isEnabled}</td>
				</tr>
				<tr>
					<th>是否审计执行日记</th>
					<td>${sysModule.allowAudit}</td>
					<th>是否启动审批</th>
					<td>${sysModule.allowApprove}</td>
				</tr>
				<tr>
					<th>是否有附件</th>
					<td>${sysModule.hasAttachs}</td>
					<th>缺省排序字段</th>
					<td>${sysModule.defOrderField}</td>
				</tr>
				<tr>
					<th>编码流水键</th>
					<td>${sysModule.seqCode}</td>
					<th>是否有图表</th>
					<td>${sysModule.hasChart}</td>
				</tr>
				<tr>
					<th>帮助内容</th>
					<td colspan="3">${sysModule.helpHtml}</td>
				</tr>
				<tr>
					<th>是否系统默认 YES NO</th>
					<td colspan="3">${sysModule.isDefault}</td>
				</tr>
			</table>
		</div>
		<div>
			<h5>字段信息</h5>
			
				<div 
					id="datagrid1" 
					class="mini-datagrid" 
					style="width: 100%; 
					mini-height:200px;" 
					allowResize="false" 
					height="auto"  
					showPager="false"
					url="${ctxPath}/sys/core/sysField/getByModuleId.do?moduleId=${sysModule.moduleId}" 
					idField="fieldId" 
					allowAlternating="true"
				>
					<div property="columns">
						<div name="action" cellCls="actionIcons" width="50" headerAlign="center" align="center" renderer="onActionRenderer" >操作</div>
						<div field="title" width="120" headerAlign="center" allowSort="true">标　题</div>
						<div field="attrName" width="120" headerAlign="center" allowSort="true">字段名称</div>
						<div field="dbFieldName" width="120" headerAlign="center" allowSort="true">数据库字段名</div>
						<div field="fieldType" width="100" headerAlign="center" allowSort="true">字段类型</div>
						<div field="fieldGroup" width="80" headerAlign="center" allowSort="true">字段分组</div>
						<div field="sn" width="50" headerAlign="center" allowSort="true">字段序号</div>
					</div>
				</div>
			
		</div>

		<div>
		<table class="table-detail column_2" cellpadding="0" cellspacing="1">
			<caption>更新信息</caption>
			<tr>
				<th>创  建  人</th>
				<td><rxc:userLabel userId="${sysModule.createBy}" /></td>
				<th>创建时间</th>
				<td><fmt:formatDate value="${sysModule.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
			</tr>
			<tr>
				<th>更  新  人</th>
				<td><rxc:userLabel userId="${sysModule.updateBy}" /></td>
				<th>更新时间</th>
				<td><fmt:formatDate value="${sysModule.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
			</tr>
		</table>
	</div>
	</div>
	<rx:detailScript baseUrl="sys/core/sysModule" formId="form1"  entityName="com.redxun.sys.core.entity.SysModule"  />
	<script type="text/javascript">
		addBody();
		var grid=mini.get("datagrid1");
		grid.load();
		
		function onActionRenderer(e) {
            var record = e.record;
            var uid = record.pkId;
            var s = '<span class="icon-detail iconfont" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
            + ' <span class="icon-edit iconfont" title="编辑" onclick="editRow(\'' + uid + '\')"></span>';
            if(record.fieldCat=='FIELD_COMMON'){
            	s+=' <span class="icon-remove iconfont" title="删除" onclick="delRow(\'' + uid + '\')"></span>';
            }
            return s;
        }
		
		function detailRow(pk){
			_OpenWindow({
				title:'字段明细',
				url:__rootPath+'/sys/core/sysField/get.do?pkId='+pk,
				height:320,
				width:700,
				max:true
			});
		}
		
		function editRow(pk){
			_OpenWindow({
				title:'字段编辑',
				url:__rootPath+'/sys/core/sysField/edit.do?pk='+pk,
				height:320,
				width:700,
				max:true
			});
		}
		
		function delRow(pk){
			_SubmitJson({
				url:__rootPath+'/sys/core/sysField/del.do',
				method:'POST',
				data:{
					pk:pk
				},
				success:function(result){
					grid.load();
				}
			});
		}
		
	</script>
</body>
</html>