<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="imgArea" uri="http://www.redxun.cn/imgAreaFun"%>
<!DOCTYPE html>
<html>
<head>
<title>流程图</title>
<%@include file="/commons/edit.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.css" />
<script src="${ctxPath}/scripts/jquery/plugins/qtips/jquery.qtip.min.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/bpmAnimate.js" type="text/javascript"></script>
</head>
<body>
	<img src="${ctxPath}/bpm/activiti/processImage.do?actDefId=${param['actDefId']}&actInstId=${param['actInstId']}&taskId=${param['taskId']}"  usemap="#imgHref"/>
	<imgArea:imgAreaScript actInstId="${param['actInstId']}" taskId="${param['taskId']}" actDefId="${param['actInstId']}"></imgArea:imgAreaScript>
	<script type="text/javascript">
	var list;
	$(function(){
		 $.ajax({
	          url: "${ctxPath}/bpm/core/bpmRuPath/calculateNode.do",
	          data: {"instId":"${bpmInst.instId}","taskId":"${param['taskId']}"}, 
	          success: function (text) {
	        	  list=text;//把返回的json数据传到list
	        	  var length=text.length;
	        	  cartoon($("[type='startNode']").attr("id"),index);//从开始节点出发
	          }
	      });
		
		 $("area[type='userTask']").each(function(){
			 	var nodeId=$(this).attr('id');
				$(this).qtip({
					content: {
		                text: function(event, api) {
		                    $.ajax({
		                        url: __rootPath+"/bpm/core/bpmNodeJump/byNodeId.do?actInstId=${param['actInstId']}&nodeId="+nodeId+"&taskId=${param['taskId']}"
		                    })
		                    .then(function(content) {
		                        api.set('content.text', content);
		                    }, function(xhr, status, error) {
		                        api.set('content.text', status + ': ' + error);
		                    });
		                    return '正在加载...'; 
		                }
		            },
		            position: {
		                target: 'mouse', // Position it where the click was...
		                adjust: { mouse: false } // ...but don't follow the mouse
		            },
			    });
		 });
		  
		 var showButton='YES';
		 showButton='${param['showButton']}';
		 if(showButton=='NO'){
			 $(".thisPlayButton").remove();
		 }
	});
	</script>
</body>
</html>