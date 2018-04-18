<%-- 
    Document   : 文档明细页
    Created on : 2016-7-5, 13:44:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文档明细</title>
<%@include file="/commons/get.jsp"%>
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>

<style type="text/css">
	.Block{
		padding: 0!important;
	}
	
	.listBlock{
		background:#E0FFFF;
		text-decoration:none;
		border-radius:5px;
		overflow:hidden;
	}
	
	.listBlock:hover{
		border:1px solid #EE5C42;
		color:#EE5C42;
		background:#FFF;
	}
	
	.mini-tabs-body-top{
		background: #f7f7f7;
	}
	
	.mini-toolbar{
		margin-bottom: 0;
		padding-bottom:4px; 
	}
	
	.mini-fit{
		margin-top: 0;
	}
	
</style>
<!-- 加上扩展控件的支持 -->
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom">
		<a class="mini-button" iconCls="icon-reload" plain="true" onclick="location.reload(true)">刷新</a>
	</div>
	<div class="mini-fit">
		<div id="tabs1" class="mini-tabs" style="width: 100%; height: 100%">
			<div title="文件内容">

				<c:choose>
					<c:when test="${doc.docType=='html'}">
						${doc.content}
					</c:when>
					<c:otherwise>
						<div style="width: auto; height: 700px;">
							<OBJECT 
								id="WebOffice" 
								name="WebOffice" 
								width=100% 
								height=800
								classid="clsid:8B23EA28-2009-402F-92C4-59BE0E063499"
								codebase="<%=request.getContextPath()%>/scripts/iweboffice/iWebOffice2009.cab#version=10,8,6,13"
							>
							</OBJECT>
						</div>
					</c:otherwise>
				</c:choose>


			</div>
			<div title="文件基本信息">
				<div class="heightBox"></div>
				<div id="form1" class="form-outer shadowBox90">

					<table style="width: 100%" class="table-detail column_2_m" cellpadding="0" cellspacing="1">
						<caption>文件基本信息</caption>
						<tr>
							<th>文档名称</th>
							<td>${doc.name}</td>
							<th>作　　者</th>
							<td>${doc.author}</td>
						</tr>
						<tr>
							<th>摘　　要</th>
							<td>${doc.summary}</td>
							<th>关键字</th>
							<td>${doc.keywords}</td>
						</tr>
						<tr>
							<th>是否包括附件</th>
							<td>${doc.hasAttach}</td>
							<th>是否共享</th>
							<td>${doc.isShare}</td>
						</tr>
						<tr>
							<th>文档类型</th>
							<td>${doc.docType}</td>
							<th>SWF文件路径</th>
							<td>${doc.swfPath}</td>
						</tr>
						<tr>
							<th>
								<h3>附件列表</h3>
							</th>
							<td colspan="3" class="Block">
								<c:forEach items="${file}" var="file">
									<div>
										<a 
											class="listBlock" 
											href="${ctxPath}/sys/core/file/previewFile.do?fileId=${file.key}"
										>${file.value}</a>
									</div>
								</c:forEach>
								
							</td>
						</tr>
					</table>
					<table class="table-detail column_2_m" cellpadding="0" cellspacing="1">
						<caption>更新信息</caption>
						<tr>
							<th>创  建  人</th>
							<td><rxc:userLabel userId="${doc.createBy}" /></td>
							<th>创建时间</th>
							<td><fmt:formatDate value="${doc.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
						<tr>
							<th>更  新  人</th>
							<td><rxc:userLabel userId="${doc.updateBy}" /></td>
							<th>更新时间</th>
							<td><fmt:formatDate value="${doc.updateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
						</tr>
					</table>
				</div>
			</div>

		</div>
	</div>
	<script type="text/javascript">
	mini.parse();
	var docPath='${docType}';
	<c:if test="${docType!='html'}">
	var type = ".${docType}";
	var docPath="${docPath}";
	if(docPath.length==0){
		docPath=" ";
	}
	var WebOffice=document.getElementById('WebOffice');
	window.onload=function(){
		try{
			WebOffice.WebUrl= "<%=request.getContextPath()%>/iWebOffice/docOfficeServer.jsp?docPath="+docPath;  //处理页地址，这里用的是相对路径
			WebOffice.RecordID = "65422345"; //文档编号
			WebOffice.Template = "12345678"; //模板编号
			WebOffice.FileName ="65422345"+type; //文件名称
			WebOffice.FileType = type; //文件类型
			WebOffice.UserName = "kinggrid"; //用户名
			WebOffice.EditType = "4"; //编辑类型 
			WebOffice.PenColor = "#FF0000"; //默认手写笔迹颜色
			WebOffice.PenWidth = "1"; //默认手写笔迹宽度
			WebOffice.Print = "1"; //是否打印批注
			WebOffice.ShowToolBar = "2"; //关闭工具栏
			WebOffice.WebOpen(false);
			WebOffice.OfficeSize='100%';
			WebOffice.ShowMenu="0";
		}catch(e){
			alert("金格控件请使用IE浏览器"); }
	}	

	</c:if>
	
	$(function(){
		var canEdit = "${canEdit}";
		var canDel = "${canDel}";
		if(canEdit == "false"){
			$("#edit").hide();
		}
		if(canDel=="false"){
			$("#del").hide();
		}
	});
	
	function onEdit(){
		var pkId = "${doc.docId}";
		 _OpenWindow({
    		 url: "${ctxPath}/oa/doc/doc/edit.do?pkId="+pkId,
            title: "编辑文档",
            width: 850, height: 750,
            ondestroy: function(action) {
                if (action == 'ok') {
                    grid.reload();
                }
            }
    	});
	}
	
	function onDel(){
		var pkId = "${doc.docId}";
		if (confirm("确定删除此文件?")) {
			$.ajax({
                type: "Post",
                url : '${ctxPath}/oa/doc/doc/del.do?ids='+pkId,
                data: {},
                beforeSend: function () {
                },
                success: function () {
                	CloseWindow('ok');
                }
            }); 
			tree.removeNode(node);
		}
	}
	
</script>
	<rx:detailScript baseUrl="oa/doc/doc" formId="form1" entityName="com.redxun.oa.doc.entity.Doc" />
</body>
</html>