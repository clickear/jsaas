<%-- 
    Document   : [OdDocument]明细页
    Created on : 2016-3-8, 17:42:57
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="imgArea" uri="http://www.redxun.cn/imgAreaFun"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>${status}明细</title>
<%@include file="/commons/get.jsp" %> 
<%-- <link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" /> --%>
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/jquery/plugins/jquery.getscripts.min.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.css" />
<script src="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.js" type="text/javascript"></script>
<!-- 加上扩展控件的支持 -->
<link href="${ctxPath}/scripts/ueditor/form-design/form-def.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.redfont {
	font-family: "微软雅黑";
	font-size: 14px;
 	font-weight: normal; 
	font-style: normal;
	color: #666;
	font-variant: normal;
	line-height:22px;
}

.form-outer {
	border: none;
	border-top: 0;
	padding: 2px;
}

.form-inner {
	padding: 5px;
}

.form-table {
	border-collapse: collapse;
	border: solid 1px #ececec;
	width: 100%;
	margin-top: 6px;
}

form-table td,th {
	padding: 5px;
}

.form-table-td {
	border: solid 1px #ececec;
	padding: 5px;
	text-align: left;
}

.form-table-th {
	border: solid 1px #ececec;
	font-weight: bold;
	padding: 5px;
	white-space: nowrap;
	text-align: right;
}

.table-detail {
	border-collapse: collapse;
	border: solid 1px #ececec;
	width: 100%;
	margin-top: 6px;
}

.table-detail>tbody>tr>th {
	border: solid 1px #ececec;
/* 	text-align: right; */
/* 	font-weight: bold; */
	padding: 6px;
}

.table-detail>tbody>tr>td {
	border: solid 1px #ececec;
	text-align: left;
	padding: 6px;	
}

.table-detail .mini-checkboxlist td {
	border: none;
}

.table-detail .mini-radiobuttonlist td {
	border: none;
}

.upload-panel {
	font-size: 9pt;
	font-family: '微软雅黑';
	overflow: hidden;
	position: relative;
	outline: none;
	padding: 2px;
	border: solid 1px #ececec;
}

.asLabel .mini-textbox-border,
.asLabel .mini-textbox-input,
.asLabel .mini-buttonedit-border,
.asLabel .mini-buttonedit-input,
.asLabel .mini-textboxlist-border{
	border: 0;
	background: none;
	cursor: default;
}

.asLabel .mini-buttonedit-button,
.asLabel .mini-textboxlist-close {
	display: none;
}

.asLabel .mini-textboxlist-item {
	padding-right: 8px;
}
</style>
</head>
<body>
	<div class="heightBox"></div>
		
	<div id="form1" class="form-outer shadowBox90">
		<div style="padding: 5px;">
			<table style="width: 100%" class="table-detail column_2_m" cellpadding="0" cellspacing="1">
				 <caption style="font-size:20px;">接收公文</caption> 
				<tr>
					<th><div class="redfont">文件标题</div></th>
					<td colspan="3">${odDocument.subject}</td>
				</tr>
				<%-- <tr>
					<th><div class="redfont">发文分类ID</div></th>
					<td>${odDocument.treeId}</td>
				</tr> --%>
				<tr>
					<th><div class="redfont">发文字号</div></th>
					<td>${odDocument.issueNo}</td>

					<th><div class="redfont">发文机关或部门</div></th>
					<td>${issueDep}</td>
				</tr>
				<!-- mainDep ccDep issueDep takeDep joinDeps assDep -->
				<%-- <tr>
					<th>是否联合发文件</th>
					<td>${odDocument.isJoinIssue}</td>
				</tr> --%>

				<tr class="dispatch" style="display: none;">
					<th><div class="redfont">主送单位</div></th>
					<td>${mainDep}</td>

					<th><div class="redfont">抄送单位</div></th>
					<td>${ccDep}</td>
				</tr>
				<tr class="incoming" style="display: none;">
					<th><div class="redfont">承办部门</div></th>
					<td>${takeDep}</td>

					<th><div class="redfont">协办部门</div></th>
					<td>${assDep}</a></td>
				</tr>
				<tr id="joindeps" style="display: none;">

					<th><div class="redfont">联合发文单位或部门</div></th>
					<td>${joinDeps}</td>

				</tr>
				<tr>
					<th><div class="redfont">秘密等级</div></th>
					<td>${privacyLevel}</td>

					<th><div class="redfont">保密期限(年)</div></th>
					<td>${odDocument.secrecyTerm}</td>
				</tr>
				<tr>
					<th><div class="redfont">公文的类型</div></th>
					<td>${odDocument.CNType}</td>

					<th><div class="redfont">发布日期</div></th>
					<td><fmt:formatDate value="${odDocument.issuedDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<th><div class="redfont">打印份数</div></th>
					<td>${odDocument.printCount}</td>
					<th><div class="redfont">公文状态</div></th>
					<td>${status}</td>
					
				</tr>
				<tr>
					<th><div class="redfont">紧急程度</div></th>
					<td>${urgenLevel}</td>
					<th><div class="redfont">发  文  人</div></th>
					<td><rxc:userLabel userId="${odDocument.issueUsrId}" /></td>
					
				</tr>
				
				<tr  class="incoming" style="display: none;">
					<th ><div class="redfont">公文自编号</div></th>
					<td colspan="3">${odDocument.selfNo}</td>
                  </tr>
               
				<tr>
				<th><div class="redfont">附件列表</div></th>
					<td colspan="3"><c:forEach items="${file}" var="file">
							<li><a href="${ctxPath}/sys/core/file/previewFile.do?fileId=${file.key}">${file.value}</a></li>
						</c:forEach></td>
				</tr>
				 <tr>
                <th><div class="redfont">主  题  词</div></th>
					<td colspan="3">${odDocument.keywords}</td>
					
				</tr>
				<tr>
					<th><div class="redfont">内容简介</div></th>
					<td colspan="3">${odDocument.summary}</td>

				</tr>
				<c:if test="${bpmInstId!=''}">
  				<tr>
					<td colspan="4">显示审批记录
						<input type="checkbox" name="checkHis"   onclick="showCheckList(this);"/>
						显示流程图	
						<input type="checkbox" name="bpmImage"   onclick="bpmImageCheck(this);"/>
					</td>
				</tr>
			<tr id="checkListTr"  style="display: none;">
				<td colspan="4" style="border-width: 0px; padding: 0px;">
					<div 
						id="checkGrid" 
						class="mini-datagrid" 
						style="width:100%;" height="auto" 
						allowResize="false" 
						showPager="false"  
						pageSize="50"  
						allowCellWrap="true"
						bodyStyle="border-bottom: none"
						url="${ctxPath}/bpm/core/bpmNodeJump/listForInst.do?actInstId=${odDocument.bpmInstId}" 
						idField="jumpId" 
						allowAlternating="true" 
					>
						<div property="columns">
							<div field="completeTime" dateFormat="yyyy-MM-dd HH:mm" width="70" headerAlign="center" cellStyle="font-size:12px" >时间</div>
							<div field="nodeName" width="90" headerAlign="center"  cellStyle="">节点名称</div>
							<div field="handlerId" width="40" headerAlign="center" cellStyle="font-size:10px">操作者</div>
							<div field="checkStatusText" width="50" headerAlign="center"  cellStyle="font-size:10px">操作</div>
							<div field="remark" width="210" headerAlign="center" cellStyle="line-height:10px;">处理意见</div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4" id="processImageTr" style="display:none;">
					<div style="width:1000px;overflow: auto;padding:6px;">
						<img src="${ctxPath}/bpm/activiti/processImage.do?instId=${bpmInstId}"  usemap="#imgHref" style="border:0px;"/>
						<imgArea:imgAreaScript instId="${bpmInstId}"></imgArea:imgAreaScript>
					</div>
				</td>
			</tr>
			<script type="text/javascript">
					var actionVar=false;
						mini.parse();	
						var grid=mini.get('checkGrid');
						grid.load();
						grid.on("drawcell", function (e) {
				            var record = e.record,
					        field = e.field,
					        value = e.value;
				          	var ownerId=record.ownerId;
				            if(field=='handlerId'){
				            	if(ownerId && ownerId!=value){
				            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>&nbsp;(代:'+ '<a class="mini-user" iconCls="icon-user" userId="'+ownerId+'"></a>'+')';
				            	}else if(value){
				            		e.cellHtml='<a class="mini-user" iconCls="icon-user" userId="'+value+'"></a>';
				            	}else{
				            		e.cellHtml='<span style="color:red">无</span>';
				            	}
				            } 
				            if(field=='remark'){
				            	e.cellHtml='<span style="line-height:22px;">'+value+'</span>';
				            }
				            if(field=='checkStatusText'){
				            	e.cellHtml='<span style="line-height:22px;">'+value+'</span>';
				            }
				            if(field=='nodeName'){
				            	e.cellHtml='<span style="line-height:22px;">'+value+'</span>';
				            }
				        });
				        
				        grid.on('update',function(){
				        	_LoadUserInfo();
				        });
				        
				        function showCheckList(ck){
				        	if(ck.checked){
				        		$("#checkListTr").css('display','');
				        	}else{
				        		$("#checkListTr").css('display','none');
				        	}
				        }
				        function bpmImageCheck(ck){
							if(ck.checked){
								$("#processImageTr").css('display','');
								$(".thisPlayButton").hide();
							}else{
								$("#processImageTr").css('display','none');
								$(".thisPlayButton").hide();
							}
						}
					</script>
				</c:if>
				
				<tr>
					<td colspan="4">
						<div style="width: auto;">
							<OBJECT 
								id="WebOffice" 
								name="WebOffice" 
								width=100% 
								classid="clsid:8B23EA28-2009-402F-92C4-59BE0E063499"
								codebase="<%=request.getContextPath()%>/scripts/iweboffice/iWebOffice2009.cab#version=10,1,0,0"
							>
							</OBJECT>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
	</div>
	<script type="text/javascript">
	
	mini.parse();
	
	$(function(){
		if(${odDocument.archType}==0){//发文
			$(".dispatch").show();
		}else if(${odDocument.archType}==1){//收文
			$(".incoming").show();
		}
		if((${!	empty odDocument.joinDepIds})&&(${odDocument.archType}==0)){
			$("#joindeps").show();//联合收文单位
		}
	});
	
	
	$(function(){
		 $("area[type='userTask']").each(function(){
			 	var nodeId=$(this).attr('id');
				$(this).qtip({
					content: {
		                text: function(event, api) {
		                    $.ajax({
		                        url: __rootPath+"/bpm/core/bpmTask/calUsers.do?nodeId="+nodeId+"&instId=${bpmInstId}"
		                    })
		                    .then(function(content) {
		                        api.set('content.text', content);
		                    }, function(xhr, status, error) {
		                        api.set('content.text', status + ': ' + error);
		                    });
		                    return '正在加载...'; 
		                }
		            },
		            position: {
		                target: 'mouse', // Position it where the click was...
		                adjust: { mouse: false } // ...but don't follow the mouse
		            }
			    });
		 });
	});
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
			alert("金格控件请使用IE浏览器");
			}
	}	
	function getBpminst(){
		_OpenWindow({
      		 url: "${ctxPath}/bpm/core/bpmInst/get.do?actInstId=${odDocument.bpmInstId}",
              title: "流程实例",
              width: 800, height: 600,
              ondestroy: function(action) {
               
              }
      	});
		
	}
	
	</script>
	<rx:detailScript baseUrl="offdoc/core/odDocument" entityName="com.redxun.offdoc.core.entity.OdDocument" formId="form1" />
</body>
</html>