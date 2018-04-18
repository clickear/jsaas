
<%-- 
    Document   : [单据数据列表]编辑页
    Created on : 2017-05-21 12:11:18
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[单据数据列表]编辑</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" href="${ctxPath}/scripts/codemirror/lib/codemirror.css">
<script src="${ctxPath}/scripts/codemirror/lib/codemirror.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/sql/sql.js"></script>
<script src="${ctxPath}/scripts/codemirror/mode/groovy/groovy.js" type="text/javascript"></script>	
<script src="${ctxPath}/scripts/codemirror/addon/edit/matchbrackets.js"></script>
<style type="text/css">
	#tr_freemarkerSql>td .CodeMirror{
	    width: 100% !important;
	    border-top: 1px solid #ececec;
	    box-sizing: border-box;
	}
	#tr_freemarkerSql .mini-toolbar>.mini-button{
		margin: 0 0 8px 8px;
	}
</style>


</head>
<body>

	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom topBtn	">
       	<c:if test="${not empty sysBoList.id }">
  			<a class="mini-button" iconCls="icon-next" plain="true" onclick="onNext">下一步</a>
     	</c:if>
     	<a class="mini-button" iconCls="icon-next" plain="true" onclick="onSaveNext">保存&下一步</a>
<!-- 		<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel">关闭</a> -->
    </div>
	<div class="shadowBox">
	<div id="p1" class="form-outer">
		<form id="form1" method="post">
			<input id="pkId" name="id" class="mini-hidden" value="${sysBoList.id}" />
			<input id="isDialog" name="isDialog" class="mini-hidden" value="${sysBoList.isDialog}" />
			<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
				<caption>[单据数据列表]基本信息</caption>
				<tr>
					<th width="150">
						<span class="starBox">
							名　　称<span class="star">*</span>
						</span>
					</th>
					<td>
						<input name="name" value="${sysBoList.name}" class="mini-textbox" required="true"  style="width: 90%" />
					</td>
					<th width="150">
						<span class="starBox">
							标  识  键<span class="star">*</span>
						</span>
					</th>
					<td>
						<input name="key" value="${sysBoList.key}" required="true" class="mini-textbox"   style="width: 90%" />
					</td>
				</tr>
				<c:if test="${not empty sysBoList.id}">
				<tr>
					<th>访问地址</th>
					<td id="url">
						<c:choose>
							<c:when test="${sysBoList.isDialog=='YES'&&sysBoList.isShare=='NO'}">
								/dev/cus/customData/${sysBoList.key}/dialog.do
							</c:when>
							<c:when test="${sysBoList.isDialog=='YES'&&sysBoList.isShare=='YES'}">
								/dev/cus/customData/share/${sysBoList.key}/dialog.do
							</c:when>
							<c:when test="${sysBoList.isDialog=='NO'&&sysBoList.isShare=='NO'}">
								/dev/cus/customData/${sysBoList.key}/dialog.do
							</c:when>
							<c:otherwise>
								/sys/core/sysBoList/share/${sysBoList.key}/list.do
							</c:otherwise>
						</c:choose>
					</td>
					<th>是否共享表单</th>
					<td>
						<ui:radioBoolean id="isShare" name="isShare" value="${sysBoList.isShare}" onValueChanged="changeUrl"/>
					</td>
				</tr>
				</c:if>
				<c:choose>
					<c:when test="${sysBoList.isDialog!='YES'}">
						<tr>
							<th>启用流程管理</th>
							<td>
								<ui:radioBoolean id="enableFlow" name="enableFlow" value="${sysBoList.enableFlow}"/>
							</td>
	
							<th>绑定表单方案</th>
							<td>
								<input name="formAlias" value="${sysBoList.formAlias}" text="${sysBoList.formName}" allowInput="false" class="mini-buttonedit" onbuttonclick="selectFormSolution" showClose="true" oncloseclick="_OnButtonEditClear" style="width: 90%" />
							</td>
						</tr>
						<tr>
							<th>分　　类 </th>
							<td>
								 <input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BO_LIST" 
								 	multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${sysBoList.treeId}"
							        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="_OnButtonEditClear"
							        popupWidth="300" style="width:300px"/>
							</td>
							<th>
								启用行编辑
							</th>
							<td>
								<ui:radioBoolean name="rowEdit" value="${sysBoList.rowEdit}"/>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th>
								<span class="starBox">
									分　　类<span class="star">*</span>
								</span>
							</th>
							<td colspan="3">
								 <input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_BO_LIST_DLG" 
								 	multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${sysBoList.treeId}"
							        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="_OnButtonEditClear"
							        popupWidth="300" style="width:300px"/>
							</td>
						</tr>
					<tr>
						<th>高　　度</th>
						<td>
							<input name="height" class="mini-spinner" value="${sysBoList.height}" minValue="100" maxValue="1500"/>
						</td>

						<th>宽　　度</th>
						<td>
							<input name="width" class="mini-spinner" value="${sysBoList.width}" minValue="100" maxValue="1500"/>
						</td>
					</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<th>是否多选</th>
					<td>
						<div class="mini-radiobuttonlist" name="multiSelect" value="${sysBoList.multiSelect}" data="[{id:'true',text:'是'},{id:'false',text:'否'}]"/>
					</td>
					<th>是否分页</th>
					<td>
						<ui:radioBoolean name="isPage" value="${sysBoList.isPage}"/>
					</td>
				</tr>
				
				<tr>
					<th>启用导航树</th>
					<td>
						<ui:radioBoolean id="isLeftTree" name="isLeftTree" value="${sysBoList.isLeftTree}" onValueChanged="onChangeLeftTree"/>
					</td>
					<th>左树导航名</th>
					<td>
						<input class="mini-textbox" readOnly="true" name="leftNav" id="leftNav" style="width:80%" value="${sysBoList.leftNav}"/>
					</td>
				</tr>
				<tr>
					<th>
						数据展示类型
					</th>
					<td>
						<div class="mini-radiobuttonlist" name="dataStyle" value="${sysBoList.dataStyle}" data="[{id:'list',text:'数据列表'},{id:'tree',text:'树列表'}]"/>
					</td>
					<th>
						SQL构建方式
					</th>
					<td>
						<div class="mini-radiobuttonlist" id="useCondSql" name="useCondSql" value="${sysBoList.useCondSql}" data="[{id:'NO',text:'Freemarker SQL'},{id:'YES',text:'Groovy SQL'}]" onvaluechanged="changeConditionSql"></div>
					</td>
				</tr>
				<tr>
					<th>
						<span>数  据  源</span>
					</th>
					<td>
						<input id="dbAs" name="dbAs" text="${dsName}" value="${sysBoList.dbAs}" class="mini-buttonedit" onbuttonclick="onSelDatasource" oncloseclick="_OnButtonEditClear" showClose="true" style="width: 280px" />
					</td>
					<th>
						<span>是否统计行</span>
					</th>
					<td>
						<ui:radioBoolean name="showSummaryRow" value="${sysBoList.showSummaryRow}"/>
					</td>
				</tr>
				<tr id="tr_freemarkerSql">
					<th>SQL语句<br/>
					(Freemarker语法)</th>
					<td colspan="3" style="padding: 0">
						<div class="mini-toolbar" style="border-top:none;border-left:none;border-right:none; padding: 8px 0 0 0;">
						
							<a class="mini-menubutton " plain="true" menu="popupMenu" >插入上下文的SQL条件参数</a>
							<!-- a class="mini-button" plain="true" iconCls="icon-run" onclick="onRun">执行</a--> 
							
							
						    <ul id="popupMenu" class="mini-menu" style="display:none;">
						    	<li iconCls="icon-var" onclick="onSqlExp('{CREATE_BY_}')">当前用户ID</li>
						    	<li iconCls="icon-var" onclick="onSqlExp('{DEP_ID_}')">当前部门ID</li>
						    	<li iconCls="icon-var" onclick="onSqlExp('{TENANT_ID_}')">当前机构ID</li>
						    </ul>
							<textarea id="sql" name="sql" cols="60" rows="4" style="width: 90%">${sysBoList.sql}</textarea>
						</div>
					</td>
				</tr>
				<tr id="tr_groovySql" style="display:none">
					<th>Groovy构建SQL</th>
					<td colspan="3" >
				    	<textarea id="condSqls" name="condSqls">${sysBoList.condSqls}</textarea>
					</td>
				<tr>
				<tr>
					<th>描　　述</th>
					<td colspan="3">
						<textarea name="descp" value="${sysBoList.descp}" class="mini-textarea" style="width:90%;height:80px;" ></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	</div>
	<rx:formScript formId="form1" baseUrl="sys/core/sysBoList"
		entityName="com.redxun.sys.core.entity.SysBoList" />
	<script type="text/javascript">
		addBody();
		var conditionGrid=mini.get('conditionGrid');
		function onSelDatasource(e){
			var btnEdit=e.sender;
			mini.open({
				url : "${ctxPath}/sys/core/sysDatasource/dialog.do",
				title : "选择数据源",
				width : 650,
				height : 380,
				ondestroy : function(action) {
					if (action == "ok") {
						var iframe = this.getIFrameEl();
						var data = iframe.contentWindow.GetData();
						data = mini.clone(data);
						if (data) {
							btnEdit.setValue(data.alias);
							btnEdit.setText(data.name);
						}
					}
				}
			});
		}
		
		function changeConditionSql(){
			
			var useCondSql=mini.get('useCondSql').getValue();
			
			if(useCondSql=="YES"){
				$("#tr_groovySql").css('display','');
				$("#tr_freemarkerSql").css('display','none');
			}else{
				$("#tr_groovySql").css('display','none');
				$("#tr_freemarkerSql").css('display','');
			}
		}
		
		$(function(){
			onChangeLeftTree();
			changeConditionSql();
		});
		
		function addSqlRow(){
			_OpenWindow({
				title:'SQL编辑',
				url:__rootPath+'/sys/core/sysBoList/conSqlsqlEditor.do',
				height:400,
				width:800,
				max:true,
				ondestroy:function(action){
					if(action!='ok'){
						return ;
					}
					var win=this.getIFrameEl().contentWindow;
					var data=win.getData();
					conditionGrid.addRow(data);
				}
			});
		}
		
		function editSqlRow(){
			var row=conditionGrid.getSelected();
			if(row==null){
				alert('请选择行！');
				return;
			}
			_OpenWindow({
				title:'SQL编辑',
				url:__rootPath+'/sys/core/sysBoList/conSqlsqlEditor.do',
				height:400,
				width:800,
				max:true,
				onload:function(){
					var win=this.getIFrameEl().contentWindow;
					win.setData(row);
				},
				ondestroy:function(action){
					if(action!='ok'){
						return ;
					}
					var win=this.getIFrameEl().contentWindow;
					var data=win.getData();
					conditionGrid.updateRow(row,data);
				}
			});
		}
		
		function removeSqlRow(){
			var rows=conditionGrid.getSelecteds();
			conditionGrid.removeRows(rows);
		}
		
		function selectFormSolution(e){
			var btn=e.sender;
			_OpenWindow({
				title:'表单方案选择',
				height:450,
				width:800,
				url:__rootPath +'/sys/customform/sysCustomFormSetting/dialog.do',
				ondestroy:function(action){
					if(action!='ok'){
						return;
					}
					var win=this.getIFrameEl().contentWindow;
					var data=win.getData();
					if(data!=null){
						btn.setText(data.name);
						btn.setValue(data.alias);
					}
				}
			});
		}
		
		function onChangeLeftTree(){
			var btn=mini.get('isLeftTree');
			if(btn.getValue()=='YES'){
				mini.get('leftNav').setReadOnly(false);
				mini.get('leftNav').setRequired(true);
			}else{
				mini.get('leftNav').setReadOnly(true);
				mini.get('leftNav').setRequired(false);
			}
		}
		
		var sqlEditor = CodeMirror.fromTextArea(document.getElementById("sql"), {
	        lineNumbers: true,
	        matchBrackets: true,
	        mode: "text/x-sql"
	      });
		sqlEditor.setSize('auto',220);
		
		var groovyEditor = CodeMirror.fromTextArea(document.getElementById("condSqls"), {
	        //lineNumbers: true,
	        matchBrackets: true,
	        mode: "text/x-groovy"
	     });
	     
		groovyEditor.setSize('auto',220);
	
		function onSqlExp(text){
			var doc = sqlEditor.getDoc();
       		var cursor = doc.getCursor(); // gets the line number in the cursor position
      		doc.replaceRange("$"+text, cursor); // adds a new line
		}
		//执行
		function onRun(){
			var sampleGrid=mini.get('sampleDataGrid');
			var ds=mini.get('dbAs').getValue();
			var sql=sqlEditor.getValue();
			_SubmitJson({
				url:__rootPath+'/sys/core/sysBoList/onRun.do',
				data:{
					ds:ds,
					sql:sql
				},
				method:'POST',
				success:function(result){
					
					var fields=result.data.headers;
					var cols=[{type: "indexcolumn"}];
					
					for(var i=0;i<fields.length;i++){
						cols.push({
							header:fields[i].header,
							field:fields[i].field,
							sqlEditor: { type: "textbox"}
						});
					}
					sampleGrid.setUrl(__rootPath+'/sys/core/sysBoList/getDataBySql.do');
					sampleGrid.set({
						columns:cols
					});
					sampleGrid.load({
						ds:ds,
						sql:sql
					});
					$("#resultTr").css('display','');
				}
			});
		}
		
		function onNext(){
			var id='${sysBoList.id}';
			location.href=__rootPath+'/sys/core/sysBoList/edit2.do?id='+id;
		}
		function onSaveNext(){
			var form=new mini.Form('form1');
			form.validate();
			if(!form.isValid()){
				return;
			}
			var formData=form.getData();
			var sql=sqlEditor.getValue();
			var condSqls=groovyEditor.getValue();
			formData.sql=sql;
			formData.condSqls=condSqls;
			_SubmitJson({
				url:__rootPath+'/sys/core/sysBoList/save.do',
				data:formData,
				method:'POST',
				success:function(result){
					var id=result.data.id;
					location.href=__rootPath+'/sys/core/sysBoList/edit2.do?id='+id;
				}
			});
		}
		
		function changeUrl(){
			var isDialog = "${sysBoList.isDialog}";
			var urlTd = $("#url");
			var value = mini.get("isShare").getValue();
			if(value=="NO"&&isDialog=="YES"){
				urlTd.html("/dev/cus/customData/${sysBoList.key}/dialog.do");
			}else if(value=="NO"&&isDialog=="NO"){
				urlTd.html("/sys/core/sysBoList/${sysBoList.key}/list.do");
			}else if(value=="YES"&&isDialog=="YES"){
				urlTd.html("/dev/cus/customData/share/${sysBoList.key}/dialog.do");
			}else if(value=="YES"&&isDialog=="NO"){
				urlTd.html("/sys/core/sysBoList/share/${sysBoList.key}/list.do");
			}
		}
		
		
	</script>
</body>
</html>