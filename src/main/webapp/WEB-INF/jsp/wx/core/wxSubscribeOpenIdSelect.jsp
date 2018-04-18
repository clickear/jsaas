
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
				<a class="mini-button" iconCls="icon-ok" onclick="confirmOpenId">确定</a> <a class="mini-button" iconCls="icon-cancel"
					onclick="CloseWindow('cancel');">取消</a>
			</div>
		</div>
		<div region="center" showSplit="false" showHeader="false"  showSplitIcon="false" style="width: 100%" bodyStyle="border:0">
			<div class="mini-fit rx-grid-fit" style="height: 95%;">
				<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 95%;" allowResize="false"
			url="${ctxPath}/wx/core/wxSubscribe/listAll.do?pubId=${param.pubId}" idField="id" multiSelect="true"
			 showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true"  allowCellSelect="true">
			<div property="columns">
			<div type="checkcolumn" width="20"></div>
				<div field="nickName"  sortField="nickName"  width="120" headerAlign="center" allowSort="true">名称</div>
				<!-- <div field="subscribe"  sortField="SUBSCRIBE_"  width="120" headerAlign="center" allowSort="true">是否关注</div> -->
				<div field="city"  sortField="city"  width="120" headerAlign="center" allowSort="true">城市</div>
				<!-- <div field="subscribeTime" sortField="SUBSCRIBE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">最后的关注时间</div> -->
			</div>
		</div>
			</div>
		</div>
		
	</div>


	<script type="text/javascript">
	mini.parse();
	var grid=mini.get("datagrid1");
	grid.load();
	
	
	var recieverValue="";
	var recieverText="";
	function getReceiverValue(){
		var nodes=grid.getSelecteds();
		for(var i=0;i<nodes.length;i++){
			recieverValue+=nodes[i].openId+",";
		}
		if(recieverValue.length>0){
			recieverValue=recieverValue.substring(0,recieverValue.length-1);
		}
		return recieverValue;
	}	
	function getReceiverText(){
		var nodes=grid.getSelecteds();
		for(var i=0;i<nodes.length;i++){
			recieverText+=nodes[i].nickName+",";
		}
		if(recieverText.length>0){
			recieverText=recieverText.substring(0,recieverText.length-1);
		}
		return recieverText;
	}

	
	function confirmOpenId(){
		var nodes=grid.getSelecteds();
		if(nodes.length<2){
			alert("群发消息人数必须大于1");
			return ;
		}
		CloseWindow('ok');
	}
	
	
	
	
	</script>
</body>
</html>