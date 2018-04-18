<%-- 
    Document   : [KdQuestion]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[KdQuestion]列表管理</title>
<%@include file="/commons/list.jsp"%>
<!-- script src="${ctxPath}/scripts/printer/LodopFuncs.js" type="text/javascript"></script-->
</head>
<body>
	<div class="mini-toolbar mini-toolbar-bottom" >
			<table style="width:100%;">
				 <tr>
					<td style="width:100%;">
						<a class="mini-button" iconCls="icon-add" onclick="addQuestion">添加</a>
						<a class="mini-button" iconCls="icon-detail" onclick="getQuestion()">查看</a>
						<a class="mini-button" iconCls="icon-edit" onclick="editQuestion()">编辑</a>
						<a class="mini-button" iconCls="icon-remove"  onclick="delQuestion()">删除</a>
						<a class="mini-button" iconCls="icon-refresh"  onclick="refresh">刷新</a>
					</td>
				</tr>
			</table>
		</div>

	<div class="mini-fit  rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/kms/core/kdQuestion/getByPath.do?treeId=${param['treeId']}" idField="queId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="subject" width="120" headerAlign="center" allowSort="true">问题标签</div>
				<div field="tags" width="120" headerAlign="center" allowSort="true">标签</div>
				<div field="rewardScore" width="120" headerAlign="center" allowSort="true" align="center">悬赏货币</div>
				<div field="replyType" width="120" headerAlign="center" allowSort="true" align="center">回复者类型</div>
				<div field="status" width="120" headerAlign="center" allowSort="true" align="center" renderer="onStatusRenderer">问题状态</div>
				<div field="replyCounts" width="120" headerAlign="center" allowSort="true" align="center">回复数</div>
			</div>
		</div>
	</div>

<redxun:gridScript gridId="datagrid1" entityName="com.redxun.kms.core.entity.KdQuestion" winHeight="450" winWidth="700" entityTitle="[KdQuestion]" baseUrl="kms/core/kdQuestion" />
	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="getQuestion(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editQuestion(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delQuestion(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
	        function onStatusRenderer(e) {
		         var record = e.record;
		         var status = record.status;
		         
		         var arr = [ {'key' : 'UNSOLVED','value' : '待解决','css' : 'green'}, 
				             {'key' : 'SOLVED','value' : '已解决','css' : 'blue'} ];
				
					return $.formatItemValue(arr,status);
		     }
        	
			grid.on("drawcell", function(e) {
				field = e.field, value = e.value;
				var s;
			    if (field == 'replyType') {//设置回复标识
					if (value == "DOMAIN_EXPERT") {
						s = "领域专家";
					} else if(value == 'EXPERT'){
						s = "专家";
					}else{
						s="所有人";
					}
					e.cellHtml = s;
				}
			});
			
			function getQuestion(pkId){
				var queId="";
				if(pkId!=null)
					  queId=pkId;
				else{
					var rows=grid.getSelecteds();
					if(rows.length<=0){
						alert("请选择一个问题！");
						return;
					}
					else if(rows.length>1){
						alert("查看的问题不能超过一个！");
						return;
					}
					else if(rows.length==1)
						queId=rows[0].queId;
				}
				_OpenWindow({
					url:"${ctxPath}/kms/core/kdQuestion/mgrGet.do?pkId="+queId,
					title:"查看问题",
					height:600,
					width:900,
					ondestroy:function(action){
						if(action=='ok')
							grid.load();
					}
				});
			}
			
			function editQuestion(pkId){
				var queId="";
				if(pkId!=null)
					  queId=pkId;
				else{
					var rows=grid.getSelecteds();
					if(rows.length<=0){
						alert("请选择一个问题！");
						return;
					}
					else if(rows.length>1){
						alert("编辑的问题不能超过一个！");
						return;
					}
					else if(rows.length==1)
						queId=rows[0].queId;
				}
				_OpenWindow({
					url:"${ctxPath}/kms/core/kdQuestion/mgrEdit.do?pkId="+queId,
					title:"编辑问题",
					height:600,
					width:900,
					ondestroy:function(action){
						if(action=='ok')
							grid.load();
					}
				});
			}
			
			function addQuestion(){
				_OpenWindow({
					url:"${ctxPath}/kms/core/kdQuestion/mgrEdit.do",
					title:"添加问题",
					height:600,
					width:900,
					ondestroy:function(action){
						if(action=='ok')
							grid.load();
					}
				});
			}
			
			function delQuestion(pkId){		
				
				
				var ids = [];
				if(pkId!=null){
					  ids.push(pkId);
					  if (!confirm("确定删除选中记录？")) return;
				}
				else{
					var rows=grid.getSelecteds();
					if(rows.length<=0){
						alert("请选择至少一个问题！");
						return;
					}
					else{
						if (!confirm("确定删除选中记录？")) return;
				        for (var i = 0, l = rows.length; i < l; i++) {
				            var r = rows[i];
				            ids.push(r.pkId);
				        }
					}
				}
				
		        _SubmitJson({
		        	url:"${ctxPath}/kms/core/kdQuestion/del.do",
		        	method:'POST',
		        	data:{ids: ids.join(',')},
		        	 success: function(text) {
		                grid.load();
		            }
		        });
			}
			
			function refresh(){
				var pageIndex = grid.getPageIndex();
				var pageSize = grid.getPageSize();
				grid.load({
					pageIndex : pageIndex,
					pageSize : pageSize
				});
			}
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
</body>
</html>