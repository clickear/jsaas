<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>功能展示区</title>
<%@include file="/commons/dynamic.jspf" %>
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
						<span><a>互联网接入开通单</a></span>
					</div>
					<div class="menuItem link">
						<span class="iconfont icon-ele-50  span-icon"></span> 
						<span><a>电路租用开通单</a></span>
					</div>
					<div class="menuItem link">
						<span class="iconfont icon-bpm-inst-50  span-icon"></span> 
						<span><a>数据专线开通单</a></span>
					</div>
					<div class="menuItem link">
						<span class="iconfont icon-vpn-50  span-icon"></span> 
						<span><a>IPVPN开通单</a></span>
					</div>
					<div class="menuItem link">
						<span class="iconfont icon-gdata-50  span-icon"></span> 
						<span><a>集团WLAN(PBOSS)开通单</a></span>
					</div>
					<div class="menuItem link">
						<span class="iconfont icon-centrex-50  span-icon"></span> 
						<span><a>统一Centrex开通单</a></span>
					</div>
					
				</div>
			</div>
			
			 <div class="container">
		        <div class="mini-clearfix ">
		            <div class="mini-col-6" style="border-right:solid 1px #ccc;width:49%;">
						<div class="mini-panel " title="待办事项" width="auto" height="300">
							<ul>
								<li><a href="#">您有3份勘察单待审批</a></li>
								<li><a href="#">您有4份互联网开通业务单处理</a></li>
								<li><a href="#">您有3份勘察单待审批</a></li>
								<li><a href="#">您有4份互联网开通业务单处理</a></li>
								<li><a href="#">您有3份勘察单待审批</a></li>
								<li><a href="#">您有4份互联网开通业务单处理</a></li>
								<li><a href="#">您有4份互联网开通业务单处理</a></li>
								<li><a href="#">您有4份互联网开通业务单处理</a></li>
							</ul>
			            </div>
			        </div>
			        <div class="mini-col-6" style="border-right:solid 1px #ccc;;width:49%;">
						<div class="mini-panel " title="最新公告" width="auto" height="300">
							<ul>
							<li><a href="#">2017-08-20勘察单流程最新发布了新的审批规则！</a></li>
							<li><a href="#">2017-08-20勘察单流程最新发布了新的审批规则！</a></li>
							<li><a href="#">2017-08-20勘察单流程最新发布了新的审批规则！</a></li>
							<li><a href="#">2017-08-20勘察单流程最新发布了新的审批规则！</a></li>
							<li><a href="#">2017-08-20勘察单流程最新发布了新的审批规则！</a></li>
							<li><a href="#">2017-08-20勘察单流程最新发布了新的审批规则！</a></li>
							<li><a href="#">2017-08-20勘察单流程最新发布了新的审批规则！</a></li>
							<li><a href="#">2017-08-20勘察单流程最新发布了新的审批规则！</a></li>
							</ul>
			            </div>
			        </div>
			     </div>
			     
			     <div class="mini-clearfix ">
		            <div class="mini-col-6" style="border-right:solid 1px #ccc;;width:49%;">
						<div class="mini-panel " title="2017年业务开通统计图" width="auto" height="400" url="${ctxPath}/data/zhaoqinMobile1.do">
							
			            </div>
			        </div>
			        <div class="mini-col-6" style="border-right:solid 1px #ccc;;width:49%;">
						<div class="mini-panel " title="本周专线工单跟进情况统计" width="auto" height="400" url="${ctxPath}/data/zhaoqinMobile2.do">
							
			            </div>
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
