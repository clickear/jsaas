
<%-- 
    Document   : [微信关注者]编辑页
    Created on : 2017-06-30 08:51:08
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>选择接收者</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>
	
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;">
		<div region="south" showSplit="false" showHeader="false" height="34" showSplitIcon="false" style="width: 100%" bodyStyle="border:0">
			<div class="mini-toolbar dialog-footer" style="text-align: center; border: none;">
				<a class="mini-button" iconCls="icon-ok" onclick="confirmChoose">确定</a> <a class="mini-button" iconCls="icon-cancel"
					onclick="CloseWindow('cancel');">取消</a>
			</div>
		</div>
		<div title="接收者列表" region="center" showHeader="false" showCollapseButton="false">
			<ul id="tree1" class="mini-tree" url="${ctxPath}/wx/core/wxSubscribe/getTagAndSubscribe.do?pubId=${param.pubId}" textField="name"
				idField="id" parentField="parentId" style="width: 300px; padding: 5px;" showTreeIcon="true" resultAsTree="false" showCheckBox="true"
				checkRecursive="false" onnodecheck="checkNode" ondrawnode="onDrawNode">
			</ul>
		</div>
	</div>


	<script type="text/javascript">
	var rtnNode=[];
	function checkNode(e){
		var node =e.node;
		if(node.checked){
			rtnNode.push(node);
		}else{
			deleteObjInArray(rtnNode,node);
		}
	}
	/*删除数组中的元素*/
	function deleteObjInArray(array,obj){
		for(var i=0;i<array.length;i++){
			if(array[i]==obj){
				array.splice(i,1);
			}
		}
	}
	function onDrawNode(e) {
        var tree = e.sender;
        var node = e.node;
        if(node.type=="subscribe"){
        	e.iconCls='icon-user';
        	}else{
        		e.iconCls='icon-File';
        	}
        	
    }
	
	function getNodes(){
		return rtnNode;
	}
	
	function confirmChoose(){
		var subscribeNum=0;
		var tagNum=0;
		for(var i=0;i<rtnNode.length;i++){
			if(rtnNode[i].type=="subscribe"){
				subscribeNum++;
			}else if(rtnNode[i].type=="tag"){
				tagNum++;
			}
		}
		if(tagNum<=4&&(subscribeNum>1||subscribeNum==0)){
			CloseWindow('ok');
		}else{
			alert("tag标签一个月只能选择累计4个,用户必须大于1或者不填");
		}
		
	}
	</script>
</body>
</html>