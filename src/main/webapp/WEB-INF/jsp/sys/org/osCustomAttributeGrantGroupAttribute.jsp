<%-- 
    Document   : [自定义属性]列表页
    Created on : 2017-12-14 14:02:29
    Author     : mansan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>属性列表</title>
<%@include file="/commons/list.jsp"%>
<style>
.mini-splitter-handler{
	display: block;
}
</style>
</head>
<body>
    <div class="mini-splitter" id="minispliter" vertical="true" style="width:100%;height:100%">
    <div size="50%" showCollapseButton="true">
        <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchFrm()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="addToForm()">加入</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>属性名称:</span><input class="mini-textbox" name="Q_ATTRIBUTE_NAME__S_LK"></li>
						<li><span>分类:</span><input class="mini-treeselect" url="${ctxPath}/sys/customform/sysCustomFormSetting/getTreeByCat.do?catKey=CAT_CUSTOMATTRIBUTE"  parentField="parentId"  valueField="treeId" textField="name" onnodeclick="treeNodeClick"/></li>
						
					</ul>
                  </td>
              </tr>
         </table>
     </div>
     <div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/sys/org/osCustomAttribute/getAttrsByTreeId.do?type=${attributeType}" idField="ID"
			multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="50" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
				<div field="attributeName"  sortField="ATTRIBUTE_NAME_"  width="120" headerAlign="center" allowSort="true">属性名称</div>
				<div field="key"  sortField="KEY_"  width="120" headerAlign="center" allowSort="true">key</div>
				<div field="widgetType"  sortField="WIDGET_TYPE_"  width="120" headerAlign="center" allowSort="true">控件类型</div>
			</div>
		</div>
	</div>
    </div>
    <div showCollapseButton="true">
       <div id="p1" class="form-outer">
		<form id="form1" method="post">
		<input id="targetId" name="targetId" class="mini-hidden" value="${targetId}" />
			<div class="form-inner">
				<table class="table-detail column_2_m" cellspacing="1" cellpadding="0">
				 <c:forEach items="${osCustomAttributes}" var="attr">
       				<tr id="tr_attr_${attr.ID}">
						<th>${attr.attributeName}：</th>
						<td >
			<c:if test="${attr.widgetType=='textbox'}"><input id="widgetType_${attr.ID}" name="widgetType_${attr.ID}" class="mini-textbox" value="${attr.value}"  style="display: inline-block;"/></c:if>
			<c:if test="${attr.widgetType=='spinner'}"><input id="widgetType_${attr.ID}" name="widgetType_${attr.ID}" class="mini-spinner"  value="${attr.value}"  style="display: inline-block;"/></c:if>
			<c:if test="${attr.widgetType=='datepicker'}"><input id="widgetType_${attr.ID}" name="widgetType_${attr.ID}" class="mini-datepicker"  value="${attr.value}" style="display: inline-block;"/></c:if>
			<c:if test="${attr.widgetType=='combobox'}"><input id="widgetType_${attr.ID}" name="widgetType_${attr.ID}" textField="text" valueField="id"  class="mini-combobox"  data='${attr.valueSource}' value="${attr.value}" style="display: inline-block;"/></c:if>
			<c:if test="${attr.widgetType=='radiobuttonlist'}"><input id="widgetType_${attr.ID}" name="widgetType_${attr.ID}" class="mini-radiobuttonlist"  textField="text" valueField="id" data='${attr.valueSource}' value="${attr.value}"  style="display: inline-block;"/></c:if>
			
			<a class="mini-button" iconCls="icon-remove" plain="true"  onclick="removeTr('tr_attr_${attr.ID}')">删除</a>
						</td>
					</tr>
       			 </c:forEach>
					
				</table>
			</div>
		</form>
	</div>
       <div style="position:fixed;bottom:0;left:40%;"><div class="mini-toolbar">
    <a class="mini-button" iconCls="icon-save" onclick="saveAttributes">保存</a>
    <a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel')">关闭</a>
		</div></div>
    </div>        
</div>

<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.org.entity.OsCustomAttribute" winHeight="450"
		winWidth="700" entityTitle="自定义属性" baseUrl="sys/org/osCustomAttribute" />
	<script type="text/javascript">
	mini.parse();
	
	var spliter=mini.get("minispliter");
	//spliter.collapsePane(1);
	var tree=mini.get("systree");
	var form=new mini.Form("#form1");
	
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-create" title="加入" onclick="joinRowIntoForm(\'' + pkId + '\')"></span>';
			return s;
		}
		
		
		function treeNodeClick(e){
			var node=e.node;
			var treeId=node.treeId;
			grid.setUrl("${ctxPath}/sys/org/osCustomAttribute/getAttrsByTreeId.do?type=${attributeType}&treeId="+treeId);
			grid.reload();
		}
		
		
		function removeTr(id){
			$("#"+id).remove();
		}
		function saveAttributes(){
			$.ajax({
				url:__rootPath+'/sys/org/osAttributeValue/saveTargetAttributes.do',
				type:'post',
				data:form.getData(),
				success:function (result){
					CloseWindow('ok');
				}
			});
		}
		function joinRowIntoForm(attributeId){
			var EL=$("#widgetType_"+attributeId);
			if(!EL.length>0){
				var row = grid.findRow(function(row){
				    if(row.id == attributeId) return true;
				});
				var extendField;
				$(".table-detail").prepend('<tr id="tr_attr_'+attributeId+'"><th>'+row.attributeName+'：</th><td ><input class="mini-'+row.widgetType+'"  textField="text" valueField="id"  id="widgetType_'+attributeId+'" name="widgetType_'+attributeId+'"   style="display: inline-block;"/><a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeTr(\'tr_attr_'+attributeId+'\')">删除</a></td>');
				mini.parse();
				var plugin=mini.get("widgetType_"+attributeId);
				if(row.widgetType=='combobox'||row.widgetType=='radiobuttonlist'){
					plugin.setData(row.valueSource);
				}
			}else{
				//已经存在
			}
			
		} 
		/*逐个添加到表单内*/
		function addToForm(){
			var list=grid.getSelecteds();
			for(var i=0;i<list.length;i++){
				joinRowIntoForm(list[i].id);
			}
		}

	</script>
	
</body>
</html>