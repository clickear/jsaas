<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/scripts/miniui/themes/bootstrap/skin.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.span-icon {
			display: block;
			padding: 5px;
			clear: both;
			margin: 5px;
		}
		.menuItem {
			float: left;
			display: block;
			width: 130px;
			margin: 5px;
			text-align: center;
			padding: 10px;
			cursor: pointer;
			border: solid 1px #ccc;
			font-size:12px;
		}
		.menuItem:hover {
			background-color: yellow;
		}
		
		.container
	    {
	        padding-top:10px;
	        clear: both;
	    }
	    
	    .mini-panel li{
	    	height:26px;
	    }
	</style>
</head>
	<body>
			<div style=" padding:5px; height:auto !important; min-height:200px; width:100%;clear:both;">
				<div style="margin: 20px; padding: 10px;">
					
					<div class="menuItem link" url="${ctxPath}/sys/core/sysBoList/accOpen/list.do">
						<span class="iconfont icon-network-50 span-icon"></span> 
						<span><a>我的工单</a></span>
					</div>
					
					
				</div>
			</div>
			
			<script type="text/javascript">
			mini.parse();
			$(function(){
				$("div.menuItem").on('click',function(){
					
					var url=$(this).attr('url');
					var title=$(this).children('span:nth-child(2)').text();
					if(!url){
						return;
					}
					_OpenWindow({
						title:title,
						url:url,
						height:500,
						width:800,
						max:true
					});
				});
			});
		</script>
    </body>
</html>
