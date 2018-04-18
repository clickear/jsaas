<%-- 
    Document   : [机构类型]选择
    Created on : 2017-11-08 18:35:32
    Author     : cmc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>机构类型选择</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
<div class="mini-fit">
<ul id="tree" class="mini-tree" url="${ctxPath}/sys/core/sysInstType/showInstTypes.do?subSysId=${subSysId}" style="width:200px;padding:5px;" 
        showTreeIcon="true" textField="typeName" idField="typeName" parentField="" resultAsTree="false"  checkedField="enabled"
        showCheckBox="true" checkRecursive="true" allowSelect="false" enableHotTrack="false" >
</ul>
<div class="mini-toolbar" style="position:fixed;bottom:0;text-align:center;width: 100%;">
    <a class="mini-button" iconCls="icon-save" onclick="saveInstType">确定</a>
    <a class="mini-button" iconCls="icon-edit" onclick="CloseWindow('cancel')">取消</a>
</div>
</div>
	 <script type="text/javascript">
	 mini.parse();
	 var tree=mini.get("tree");
	 
	 function saveInstType(){
		 debugger;
		 var checkedNodes=tree.getCheckedNodes(false);
		 var typeIds=[];
		 for(var i=0;i<checkedNodes.length;i++){
			 typeIds.push(checkedNodes[i].typeId);
		 }
		 console.log(typeIds);
		 $.ajax({
			 type:'post',
			 url:'${ctxPath}/sys/core/sysTypeSubRef/addRef.do',
			 data:{typeIds:mini.encode(typeIds),subSysId:'${subSysId}'},
			 success:function(result){
				 if(result.success){
					 CloseWindow('ok');
				 }
			 }
		 });
		 
	 }
	 </script>
</body>
</html>