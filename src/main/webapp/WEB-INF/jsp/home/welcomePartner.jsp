<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<%@include file="/commons/dynamic.jspf" %>
<script src="${ctxPath}/scripts/miniui/miniui.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctxPath}/styles/welcome.css" type="text/css">
<script type="text/javascript" src="${ctxPath}/scripts/common/baiduTemplate.js"></script>
<script src="${ctxPath}/scripts/jquery-1.11.3.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/index/index.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/index/welcome.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/manage.js" type="text/javascript"></script>



<title>首页</title>
</head>

<body>
	<div class="content_bg">
		<!--顶部卡片-->
		<div class="content_1p">			
		</div>
		<div class="clearfix"></div>
		<!--左边卡片-->
		<div class="content_2p">
			<div id="myTasks" class="content_2p_margin_t content_2p_box">
				<img src="${ctxPath}/styles/images/index/index_content_Quick_01.png" alt="">
				<span>
					<h3></h3>
					<h2>待处理</h2>
				</span>
			</div>
			<div id="myAttends" style="margin: 0 20px 20px 20px" class="content_2p_box">
				<img src="${ctxPath}/styles/images/index/index_content_Quick_02.png" alt="">
				<span>
					<h3></h3>
					<h2>已处理</h2>
				</span>
			</div>
			<div class="clearfix"></div>
		</div>
		<!--代办-->
		<div class="content_db">
			<div class="content_db_title">
				<h1>待办事项</h1>
				<span>
				<img id="refresh" src="${ctxPath}/styles/images/index/content_p_title_02.png" alt="">
				</span>
				<div class="clearfix"></div>
			</div>
			<ul>
				<li class="content_db_head">
					<span class="content_db_w_01">
						<p>审批环节</p>
					</span>
					<span class="content_db_w_02">
						<p>事项</p>
					</span>
					<span class="content_db_w_03">
						<p>日期</p>
					</span>
					<span class="content_db_w_05">
						<p>操作</p>
					</span>
					<div class="clearfix"></div>
				</li>
			</ul>
			<div id="myTaskList"></div>
			<input id="more" type="button" value="查看更多" class="content_more">
		</div>	

	</div>
	
	<script id="myTaskListTemplate"  type="text/html">
  	<#for(var i=0;i<list.length;i++){
  	  var task=list[i];
 	 #>	
		<ul>
			<li >
				<span class="content_db_w_01">
					<p><#=task.name#></p>
				</span>
				<span class="content_db_w_02">
					<p><#=task.description#></p>
				</span>
				<span class="content_db_w_03">
					<p><#=task.createTime#></p>
				</span>
				<span class="content_db_w_05">
					<a href="javascript:handleTask('<#=task.id#>')">审批</a>
				</span>
				<div class="clearfix"></div>
			</li>
		</ul>
<#}#>
</script>
<script type="text/javascript">
	function handleTask(id){
		_OpenWindow({
			url:"${ctxPath}/bpm/core/bpmTask/toStart.do?taskId="+id,
			title:'待办审批',
			max:true,
			width:800,
			height:450,
			ondestroy:function(action){
				if(action!='ok'){
					return;
				}
				document.location.reload();
    		}
		});
	}
	
</script>
</body>
</html>
