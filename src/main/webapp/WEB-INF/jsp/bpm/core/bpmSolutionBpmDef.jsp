<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="imgArea" uri="http://www.redxun.cn/imgAreaFun"%>
<!DOCTYPE html>
<html>
<head>
	<title>业务流程解决方案管理-流程定义处理</title>
	<%@include file="/commons/edit.jsp"%>
</head>
<body>
	<div id="toolbar1" class="mini-toolbar mini-toolbar-bottom" >
	    <c:if test="${not empty bpmDef}">
        	<a class="mini-button" iconCls="icon-property" onclick="showProcessProperties()" plain="true">流程属性配置</a> 
           	<a class="mini-button" iconCls="icon-flow" plain="true" onclick="showBpmDefInfo('${bpmDef.actDefId}')">
           		${bpmDef.subject}(${bpmDef.key})-版本(${bpmDef.version})
       		</a>
       	</c:if>
	</div>
	
	<div class="mini-fit" style="margin-top: 0;">
		<div style="width:100%;height:100%;background: #fff;" id="bpmImgContainer">
			<c:if test="${empty bpmDef}">
				<div style="width:80%;padding:5px;text-align: center;">
					<span>
						<img src="${ctxPath}/styles/images/warn.png"/>
					</span>
					<span style="font-size:20px;color: red">请绑定流程定义！</span>
				</div>
			</c:if>
			<c:if test="${not empty bpmDef}">
				<span style="display: inline-block;margin-left: 8px;">提示：右击流程节点可进行属性配置</span>
				<br/>
				<img id="bpmImg" src="${ctxPath}/bpm/activiti/processImage.do?actDefId=${bpmDef.actDefId}" usemap="#imgHref"/>
				<imgArea:imgAreaScript actDefId="${bpmDef.actDefId}"></imgArea:imgAreaScript>			
			</c:if>			
		</div>
	</div>
	
	<ul id="contextMenu" class="mini-contextmenu" >
		<li name="prop" iconCls="icon-mgr"  onclick="nodeSetting()">属性设置</li>
		<li name="usermenu" iconCls="icon-user" onclick="userSetting()">人员配置</li>
	</ul>
	
	<script type="text/javascript">
		mini.parse();
		var nodeId;
		var nodeType;
		var nodeName;
		var solId="${param['solId']}";
		
		$(function(){
			/*删除播放按钮*/
			$(".thisPlayButton").remove();
			
			$("area").bind("mouseover",function(){
				  nodeId=$(this).attr("id");
				  nodeType=$(this).attr('type');
				  nodeName=$(this).attr('name');
			});
		});


		/*绑定菜单*/
		window.onload = function () {
             $("#imgHref").bind("contextmenu", function (e) {
                var menu = mini.get("contextMenu");
                var usermenu = mini.getByName("usermenu", menu);
                var formmenu=mini.getByName('formmenu',menu);
                if(nodeType=='userTask'){
   				 	usermenu.show();
   				 	//formmenu.show();
	   			 }else{
	   				usermenu.hide();
	   				//formmenu.hide();
	   			 }
                menu.showAtPos(e.pageX, e.pageY);
                return false;
            }); 
        }
		
		
		var solId='${bpmSolution.solId}';
		//实定义信息明细
		function showBpmDefInfo(procDefId){
			_OpenWindow({
				title:'流程定义明细',
				url:__rootPath+'/bpm/core/bpmDef/get.do?hideRecordNav=true&actDefId='+procDefId,
				max:true
			});
		}
		
		function nodeSetting(){
			_OpenWindow({
				title : '流程节点[' + nodeName + ']-属性配置',
				iconCls : 'icon-mgr',
				max : true,
				width : 600,
				height : 500,
				url : __rootPath + '/bpm/core/bpmNodeSet/getNodeConfig.do?nodeId=' + nodeId + '&nodeType=' + nodeType + '&solId=' + solId +'&actDefId=${bpmDef.actDefId}'
			});
		}
		
		function userSetting(){
			_OpenWindow({
				title : '流程节点[' + nodeName + ']-人员配置',
				iconCls : 'icon-user',
				max : true,
				width : 600,
				height : 500,
				url : __rootPath + '/bpm/core/bpmSolution/nodeUser.do?nodeId=' + nodeId + '&nodeType=' + nodeType + '&solId=' + solId +'&actDefId=${bpmDef.actDefId}'
			});
		}
		
		function showProcessProperties(){
			_OpenWindow({
				title : '流程属性配置-[${bpmSolution.name}]',
				iconCls : 'icon-properties',
				max : true,
				width : 600,
				height : 500,
				url : __rootPath + '/bpm/core/bpmNodeSet/getNodeConfig.do?nodeId=_PROCESS&nodeType=process&solId=' + solId +'&actDefId=${bpmDef.actDefId}'
			});
		}
		
		
	</script>
</body>
</html>