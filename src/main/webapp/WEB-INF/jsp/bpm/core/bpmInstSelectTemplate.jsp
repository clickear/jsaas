<%
	//流程模板选择框
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>流程选择框</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
   	<%@include file="/commons/dynamic.jspf" %>
   	<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
    <link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
    <link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
    
    <script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
    <script src="${ctxPath}/scripts/share.js" type="text/javascript"></script> 
    <link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/hcolumns/css/reset.css" type="text/css">
    <link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/hcolumns/css/hcolumns.css" type="text/css">
    <script src="${ctxPath}/scripts/jquery/plugins/hcolumns/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxPath}/scripts/jquery/plugins/hcolumns/js/jquery.hcolumns.js"></script>
    <style>
    .mini-layout-region-west {
    	border-left:1px solid #d1d1d1;
	}
    </style>
    
</head>
<body>
 <div id="columns"></div>
<div id="solutionDescp" ></div>

	<script type="text/javascript">
	mini.parse();
	//str='${showNode}'.replace(/"(\w+)"(\s*:\s*)/g, "$1$2");
	var instId ="${param.instId}";
var solutionDescp=$("#solutionDescp");

$(document).ready(function() {
	var nodes =${showNode};
	
    $("#columns").hColumns({
        nodeSource: function(node_id, callback) {
            if(node_id === null) {
                node_id = 0;
            }
            if( !(node_id in nodes) ) {
                return callback("此分类为空");
            }
            return callback(null, nodes[node_id]);
        },
        customNodeTypeHandler:{
	        	link: function(hColumn, node, data) {
	        		var solutionId=data.url;
	        		solutionDescp.html("");
	        		if(solutionId.length>0){
	        			$.ajax({
	        				url:"${ctxPath}/bpm/core/bpmSolution/showSolutionName.do",
	        				data:{"solId":solutionId},
	        				async:false,
	        				success:function(result){
	        					solutionDescp.append(result.descp);
	        				}
	        				
	        			});
	        			mini.confirm("<b>请阅读下方流程说明</b> <br/>确定启动此流程", "确定？",
	        		            function (action) {
	        		                if (action == "ok") {
	        		                	return window.open("${ctxPath}/bpm/core/bpmInst/start.do?from=template&tmpInstId="+instId+"&solId="+data.url,"_self");
	        		                } else {
	        		                    
	        		                }
	        		            });
	        		}else{
	        			mini.showTips({
	        	            content: "<b>提醒</b> <br/>该分类未绑定流程",
	        	            state: 'warning',
	        	            x: 'center',
	        	            y: 'center',
	        	            timeout: 3000
	        	        });
	        		}
	        		
        		}
        }
    });
});





</script>
	
	
</body>
</html>