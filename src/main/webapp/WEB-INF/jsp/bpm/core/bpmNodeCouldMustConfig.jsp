<%-- 
    Document   : [BpmInstCtl]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : 节点配置人员
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/commons/list.jsp"%>
</head>
<body>

<div class="mini-toolbar mini-toolbar-bottom">
    <a class="mini-button" iconCls="icon-save" onclick="saveNodeSelectConfig()">保存 </a>
    <a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel')">关闭</a>
</div>
	  <div id="p1" class="form-outer">
	  <form id="couldSelect">
	  <div class="form-inner">
<table class="table-detail column_2" cellspacing="1" cellpadding="0" style="width:100%;">
			<c:forEach items="${couldSelect}" var="cs" varStatus="csStatus">
				<tr>
					<th>可选执行人员：</th>
					<td style="width:20%">${cs.value}</td>
					<td style="width:40%"><input id="${cs.key}" name="${cs.key}"  class="mini-textboxlist"
						style="width: 65%" allowInput="false" />
					<a class="mini-button " plain="true" iconCls="icon-collapse" onclick="selectNode('${cs.key}')">选择</a>
				   <a class="mini-button " plain="true" iconCls="icon-remove" onclick="clearTheButton('${cs.key}')">清空</a>
						</td>
				</tr>
			</c:forEach>

			<c:forEach items="${mustSelect}" var="ms" varStatus="msStatus">
				<tr>
				<th >必选执行人员：</th>
					<td style="width:20%">${ms.value} </td>
					<td style="width:40%"><input id="${ms.key}" name="${ms.key}"  class="mini-textboxlist" style="width: 65%"
						allowInput="false" required="true" />
					<a class="mini-button " plain="true" iconCls="icon-collapse" onclick="selectNode('${ms.key}')">选择</a>
				   <a class="mini-button " plain="true" iconCls="icon-remove" onclick="clearTheButton('${ms.key}')">清空</a>
						</td>
				</tr>
			</c:forEach>
		</table>
		</div>
  </form>
</div>
	

	<script type="text/javascript">
        	mini.parse();
        	var nodeUsers="";
        	var jsonArray=new Array();
        	var showText;
        	function saveNodeSelectConfig(){
        		jsonArray.splice(0,jsonArray.length);
        		var couldSelectForm=new mini.Form('couldSelect');
        		//var mustSelectForm=new mini.Form('mustSelect');
        		couldSelectForm.validate();
    			if(!couldSelectForm.isValid()){
    				return;
    			}
    			var formData=couldSelectForm.getData();
        		//改变保存的json
        		nodeUsers=couldSelectForm.getData();
        		var formJson=mini.decode(formData);
        		 for (var key in formJson){
        			jsonArray.push({
            			nodeId:key,
            			userIds:formJson[key]
    				});
        		}
        		 bringShowMsg(jsonArray);
        		CloseWindow('ok');//结束后用打开页面的var iframe = this.getIFrameEl().contentWindow;调用getJsonArray()获得相应格式数组
        	}
        	
        	function getJsonArray(){
        		return jsonArray;
        	}
        	 function bringShowMsg(myJsonArray){
        		 var allText="";
        		
        		 for (var x=0; x<myJsonArray.length;x++){
        			var everyEL=mini.get(myJsonArray[x].nodeId);
        			var nameText=everyEL.getText();
        			allText=allText+myJsonArray[x].nodeId+":"+nameText+"  ";
        		} 
        		showText=allText;
        	} 
        	 function getShowMsg(){
        		 return showText;
        	 }
        	
        	//选择人员
    		function selectNode(buttonId){
    			var elButton=mini.get(buttonId);
    			var otherHiddenButton=mini.get(buttonId+"NOTEMPTY");
    			_UserDlg(false, function(users) {
    				var ids = [];
    				var fullnames = [];
    				for (var i = 0; i < users.length; i++) {
    					ids.push(users[i].userId);
    					fullnames.push(users[i].fullname);
    				}
    				elButton.setValue(ids.join(','));
    				elButton.setText(fullnames.join(','));
    				$("#"+buttonId).attr("alt",fullnames.join(','));
    				/* otherHiddenButton.setValue(buttonId+":"+fullnames.join(',')); */
    			});
    		}
    		
    		//清空elButton的值
    		function clearTheButton(buttonId){
    			var elButton=mini.get(buttonId);
    			elButton.setValue("");
    			elButton.setText("");
    		}
        	
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>

</body>
</html>