
<%-- 
    Document   : [数据源定义管理]编辑页
    Created on : 2017-02-07 09:03:54
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[数据源定义管理]编辑</title>
<%@include file="/commons/edit.jsp"%>
<style type="text/css">
	.mini-panel-border .mini-grid-row:last-of-type .mini-grid-cell{
		border-bottom: none;
	}
</style>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar topBtn" >
	    <a class="mini-button" iconCls="icon-save"  onclick="onOk">保存</a>
		<a class="mini-button" iconCls="icon-relation"  onclick="onTestConnect">测试连接</a>
<!-- 		<a class="mini-button" iconCls="icon-close"  onclick="onCancel">关闭</a> -->
	</div>
	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
				<input id="pkId" name="id" class="mini-hidden" value="${sysDataSource.id}" />
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
					<caption>[数据源定义管理]基本信息</caption>
					<tr>
						<th>
							<span class="starBox">
								数据源名<span class="star">*</span>
							</span>		
						</th>
						<td>
							<input name="name" value="${sysDataSource.name}" class="mini-textbox"   style="width: 90%" required="true" requiredErrorText="名称不能为空"/>
						</td>
					
						<th>
							<span class="starBox">
								别　　名<span class="star">*</span>
							</span>
						</th>
						<td>
							<input name="alias" value="${sysDataSource.alias}" class="mini-textbox"  required="true" requiredErrorText="数据源别名不能为空" style="width: 90%" />
						</td>
					</tr>
					<tr>
						<th>是否使用</th>
						<td>
							<div id="enabled"   class="mini-checkbox" readOnly="false" trueValue="yes" falseValue="no" value="${sysDataSource.enabled}" ></div>
						</td>
				
						<th>启动时初始化</th>
						<td>
							<div id="initOnStart"   class="mini-checkbox" readOnly="false" trueValue="yes" falseValue="no" value="${sysDataSource.initOnStart}" ></div>
						</td>
					</tr>
					
					<tr>
						<th>数据库类型</th>
						<td colspan="3">
							<input name="dbType" class="mini-combobox" style="width:150px;" 
								textField="name" valueField="id" value="${sysDataSource.dbType}" onvaluechanged="changeDbType"
								data="[{id:'mysql',name:'mysql',url:'jdbc:mysql://localhost:3306/dbName?useUnicode=true&amp;characterEncoding=utf-8'},{id:'oracle',name:'oracle',url:'jdbc:oracle:thin:@localhost:1521:orcl'},{id:'sqlserver',name:'sqlserver',url:'jdbc:sqlserver://localhost:1433;DatabaseName=dbname;integratedSecurity=false'}]"/>
						</td>
					</tr>
					
					
				</table>
				<div class="mini-toolbar" style="border-bottom: none;">参数配置</div>
				
				<div id="paramsGrid" class="mini-datagrid" style="width: 100%;" allowResize="false" showPager="false"
					allowCellEdit="true" allowCellSelect="true"  allowAlternating="true">
						<div property="columns">
							<div field="name" width="100" headerAlign="center" >参数名</div>
							<div field="comment" width="100" headerAlign="center">参数说明</div>
							<div field="type" width="20" headerAlign="center">参数类型</div>
							<div field="val" width="150" headerAlign="center">参数值
								<input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
							</div>
						</div>
				</div>
		</form>
	</div>
	
	<rx:formScript formId="form1" baseUrl="sys/core/sysDataSource"
		entityName="com.redxun.sys.core.entity.SysDataSource" />
		
	<script type="text/javascript">
		addBody();
		var setting=${sysDataSource.setting};
		var form = new mini.Form("#form1");            
		var paramsGrid=mini.get("paramsGrid");
		paramsGrid.setData(setting);
		
		 
		//修改数据库类型
		function changeDbType(e) {
			var val=e.value;
			var obj=e.selected;
			var row = paramsGrid.findRow(function(row){
			    if(row.name == "url") {
			    	return true;
			    }
			});
			paramsGrid.updateRow(row,{val:obj.url});
        }
		
		//提交数据
		function handleFormData(formData){
			var result={isValid:true,formData:formData};
			
			var enabled=mini.get("enabled").getValue();
			var initOnStart=mini.get("initOnStart").getValue();
			
			var enabledObj={name:"enabled",value:enabled};
			var initOnStartObj={name:"initOnStart",value:initOnStart};
			
			
			var setting=mini.encode(paramsGrid.getData());
			var obj={name:"setting",value:setting};
			formData.push(enabledObj);
			formData.push(initOnStartObj);
			formData.push(obj);
			return result;
		}
		
		//测试连接
		function onTestConnect(){
			 form.validate();
	         if (form.isValid() == false) return;
	         var formData=$("#form1").serializeArray();
	         var result=handleFormData(formData);
	         if(!result.isValid) return;
	         var config={
	         	url: __rootPath +"/sys/core/sysDataSource/testConnect.do",
	         	method:'POST',
	         	data:formData,
	         	showMsg:true
	         }
	         _SubmitJson(config);

		}
	</script>
</body>
</html>