<%-- 
    Document   : [KdDoc]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>知识文档列表管理</title>
<%@include file="/commons/list.jsp"%>
<!-- script src="${ctxPath}/scripts/printer/LodopFuncs.js" type="text/javascript"></script-->
</head>
<body>
	<div class="mini-toolbar" >
			<table style="width: 100%;">
				<tr>
					<td style="width:100%;">
						<a class="mini-button" iconCls="icon-add"  onclick="addDoc">添加</a>
					    <a class="mini-button" iconCls="icon-detail" onclick="getDoc()">查看</a>
					    <a class="mini-button" iconCls="icon-edit"  onclick="editDoc()">编辑</a>
					    <a class="mini-button" iconCls="icon-remove"  onclick="delDoc()">删除</a>
					    <a class="mini-button" iconCls="icon-refresh"  onclick="refresh">刷新</a>
				    </td>
				</tr>
			</table>
		</div>

	<div class="mini-fit rx-grid-fit form-outer" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/kms/core/kdDoc/getAllByPath.do?catId=${param['catId']}" idField="docId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="10"></div>
				<div field="action" name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="sysTree.name" width="30" headerAlign="center" allowSort="true">所属分类</div>
				<div field="subject" width="100" headerAlign="center" allowSort="true">文档标题</div>
				<div field="isEssence" width="20" headerAlign="center" allowSort="true" renderer="onIsEssenceRenderer">是否精华</div>
				<div field="author" width="30" headerAlign="center" allowSort="true">作者</div>
				<div field="issuedTime" width="50" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">发布日期</div>
				<div field="status" width="20" headerAlign="center" allowSort="true" renderer="onStatusRenderer">文档状态</div>
				<div field="version" width="20" headerAlign="center" allowSort="true">版本号</div>
			</div>
		</div>
	</div>

	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.kms.core.entity.KdDoc" winHeight="450" winWidth="700" entityTitle="知识文档" baseUrl="kms/core/kdDoc" />
	<script type="text/javascript">
        	//行功能按钮
	       /* function onDrawCell(e) {
			var record = e.record;
			var essence = record.isEssence;
			var pkId = record.pkId;
			if (e.field == "action") {
				if (essence == "YES") {
					e.cellHtml = '<span class="icon-detail" title="明细" onclick="getDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-edit" title="编辑" onclick="editDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-notEssence" title="取消精华" onclick="notEssence(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-pictureAdd" title="推荐首页" onclick="remDoc(\'' + pkId + '\')"></span>';
				}
			}
		} */
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var essence = record.isEssence;
			var isPortalPic = record.isPortalPic;
			var s ="";
			if (essence == "YES") {
				if(isPortalPic == "YES"){
					s = '<span class="icon-detail" title="明细" onclick="getDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-edit" title="编辑" onclick="editDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-notEssence" title="取消精华" onclick="notEssence(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-pictureDelete" title="取消推荐" onclick="notRem(\'' + pkId + '\')"></span>';
				}else {
					s = '<span class="icon-detail" title="明细" onclick="getDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-edit" title="编辑" onclick="editDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-notEssence" title="取消精华" onclick="notEssence(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-pictureAdd" title="推荐首页" onclick="remDoc(\'' + pkId + '\')"></span>';
				}
			}else{
				if(isPortalPic == "YES"){
					s = '<span class="icon-detail" title="明细" onclick="getDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-edit" title="编辑" onclick="editDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="delDoc(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-essence" title="设为精华" onclick="essence(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-pictureDelete" title="取消推荐" onclick="notRem(\'' + pkId + '\')"></span>';
				}else {
					s = '<span class="icon-detail" title="明细" onclick="getDoc(\'' + pkId + '\')"></span>' 
					+ ' <span class="icon-edit" title="编辑" onclick="editDoc(\'' + pkId + '\')"></span>' 
					+ ' <span class="icon-remove" title="删除" onclick="delDoc(\'' + pkId + '\')"></span>' 
					+ ' <span class="icon-essence" title="设为精华" onclick="essence(\'' + pkId + '\')"></span>'
					+ ' <span class="icon-pictureAdd" title="推荐首页" onclick="remDoc(\'' + pkId + '\')"></span>';
					}
			}
			return s;

		}
		
		 function onStatusRenderer(e) {
	         var record = e.record;
	         var status = record.status;
	         var arr = [ {'key' : 'ISSUED','value' : '发布','css' : 'green'}, 
			             {'key' : 'DRAFT','value' : '草稿','css' : 'orange'} ];
			return $.formatItemValue(arr,status);
	     }	
		
		  function onIsEssenceRenderer(e) {
	            var record = e.record;
	            var isEssence = record.isEssence;
	             var arr = [{'key' : 'YES', 'value' : '是','css' : 'green'}, 
	    			        {'key' : 'NO','value' : '否','css' : 'red'} ];
	    			return $.formatItemValue(arr,isEssence);
	        }
		 
			//打开文档
			function getDoc(pkId){
				var docId="";
				if(pkId!=null)
					  docId=pkId;
				else{
					var rows=grid.getSelecteds();
					if(rows.length<=0){
						alert("请选择一个文档！");
						return;
					}
					else if(rows.length>1){
						alert("查看的文档不能超过一个！");
						return;
					}
					else if(rows.length==1)
						docId=rows[0].docId;
				}
				_OpenWindow({
					url:"${ctxPath}/kms/core/kdDoc/show.do?docId="+docId,
					title:"查看文档",
					height:600,
					width:900,
					max:true,
					ondestroy:function(action){
							grid.load();
					}
				});
			}
			//编辑文档
			function editDoc(pkId){
				var docId="";
				if(pkId!=null)
					docId=pkId;
				else{
					var rows=grid.getSelecteds();
					if(rows.length<=0){
						alert("请选择一个文档！");
						return;
					}
					else if(rows.length>1){
						alert("编辑的文档不能超过一个！");
						return;
					}
					else if(rows.length==1)
						docId=rows[0].docId;
				}
				_OpenWindow({
					url:"${ctxPath}/kms/core/kdDoc/edit.do?pkId="+docId,
					title:"编辑文档",
					height:600,
					width:900,
					max: true,
					ondestroy:function(action){
							grid.load();
					}
				});
			}
			//新增文档
			function addDoc(){
				_OpenWindow({
					url:"${ctxPath}/kms/core/kdDoc/new1.do",
					title:"添加文档",
					height:600,
					width:900,
					max: true,
					ondestroy:function(action){
						if(action=='ok')
							grid.load();
					}
				});
			}
			//删除文档
			function delDoc(pkId){				
				var ids = [];
				if(pkId!=null){
					  ids.push(pkId);
					  if (!confirm("确定删除选中记录？")) return;
				}
				else{
					var rows=grid.getSelecteds();
					if(rows.length<=0){
						alert("请选择至少一个文档！");
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
		        	url:"${ctxPath}/kms/core/kdDoc/del.do",
		        	method:'POST',
		        	data:{ids: ids.join(',')},
		        	 success: function(text) {
		                grid.load();
		            }
		        });
			}
			//刷新
			function refresh(){
				var pageIndex = grid.getPageIndex();
				var pageSize = grid.getPageSize();
				grid.load({
					pageIndex : pageIndex,
					pageSize : pageSize
				});
			}
			
			//设为精华
			function essence(pkId) {
				var essence = "YES";
				_SubmitJson({
					url : "${ctxPath}/kms/core/kdDoc/essence.do",
					data : {
						docId : pkId,
						essence : essence,
					},
					method : 'POST',
					showMsg : false,
					success : function(result) {
						alert(result.message);
						grid.reload();
					},

				});
			}

			//取消精华
			function notEssence(pkId) {
				var essence = "NO";
				_SubmitJson({
					url : "${ctxPath}/kms/core/kdDoc/essence.do",
					data : {
						docId : pkId,
						essence : essence,
					},
					method : 'POST',
					showMsg : false,
					success : function(result) {
						alert(result.message);
						grid.reload();
					},

				});
			}
			
			//点击推荐知识
			function remDoc(pkId) {
				_SubmitJson({
					url : "${ctxPath}/kms/core/kdDocRem/homeRem.do",
					data : {
						docId : pkId,
					},
					method : 'POST',
					success : function(result) {
						alert(result.message);
						grid.reload();
					}
				}); 
			}
			
			//点击取消推荐
			function notRem(pkId) {
				_SubmitJson({
					url : "${ctxPath}/kms/core/kdDocRem/notRem.do",
					data : {
						docId : pkId,
					},
					method : 'POST',
					success : function(result) {
						alert(result.message);
						grid.reload();
					}
				}); 
			}
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
</body>
</html>