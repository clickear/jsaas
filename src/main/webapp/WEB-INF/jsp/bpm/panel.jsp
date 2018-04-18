<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>流程模块快速入口 </title>
<%@include file="/commons/get.jsp"%>
<link href="${ctxPath}/scripts/miniui/themes/bootstrap/skin.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.container{
		padding-top: 20px;
	}

	.container .mini-panel{
	     width:98% !important;
	     margin:0 auto 20px auto;
	}
	
	.menuItem > span{
		float: left;
		width:100%;
		margin: 22px 0 12px 0;
		text-align: center;
		color: #666;
		font-size: 16px;
	}
	
	.menuItem > span:before{
		font-size: 60px;
	}
	
	.menuItem > span>i{
		float: left;
		width: 100%;
		text-align: center;
	}
	
	.menuItem{
		float: left;
		width: 130px;
		height: 147px;
		margin: 5px;
		padding-top: 25px;
		text-align: center;
		cursor: pointer;
		border-radius: 10px;
		background: #fff;
		position:relative;
		border: 1px solid #ececec;
	    margin-right: 10px;
	}	
	
	.mini-panel-viewport{
		border-radius: 0 0 8px 8px ;
	}
	
	.mini-panel-header-inner{
		padding:0;
	}
	
	.mini-panel-header-inner .mini-panel-title{
		line-height: 50px;
		font-size: 20px;
		font-weight: 200;
		text-align: center;
		width:100%;
		color:#666;
	}
	
	.menuItem a{
		font-size:14px;
	}
	
	.menuItem{
		box-shadow: none;
		transition: box-shadow .3s;
	}
	
	.menuItem:hover{
		box-shadow: 0 6px 18px 2px #e0e5e7;
		border-color: transparent;
	}	
	
	.mini-panel-body{
		padding: 10px;
	}
	
	.mini-panel-border,
	.mini-panel-header{
		border: none;
	}
	
	.mini-panel-body{
		border: 1px solid #ececec;
		border-top: none;
		border-radius: 0 0 8px  8px;
	}
	
	.mini-panel-title{
		color:white !important;
		font-weight: bold !important;
	}
	
</style>
</head>
<body>
	 <div class="container">
        <div class="mini-clearfix">
			<div class="mini-panel" title="业务设计入口" height="auto">
		            <div class="menuItem link" url="${ctxPath}/bpm/core/bpmDef/list.do">
						<span class="iconfont icon-flow-design-50  span-icon"></span>
						<span>流程定义设计</span>
					</div>
					<div class="menuItem link" url="${ctxPath}/bpm/form/bpmFormView/list.do">
						<span class="iconfont icon-form-edit-50  span-icon"></span> 
						<span><a>流程表单设计</a></span>
					</div>
					<div class="menuItem link" url="${ctxPath}/bpm/form/bpmMobileForm/list.do">
						<span class="iconfont icon-mobile-50  span-icon"></span> 
						<span><a>手机表单设计</a></span>
					</div>
					<div class="menuItem link" url="${ctxPath}/sys/org/sysOrg/mgr.do">
						<span class="iconfont icon-user-org-50  span-icon"></span> 
						<span><a>组织架构设计</a></span>
					</div>
					<div class="menuItem link" url="${ctxPath}/bpm/core/bpmSolution/list.do">
						<span class="iconfont icon-solution-50 span-icon"></span> 
						<span><a>流程方案设计</a></span>
					</div>
					<div class="menuItem link" url="${ctxPath}/bpm/core/bpmAuthSetting/list.do">
						<span class="iconfont icon-grant-50  span-icon"></span> 
						<span><a>流程方案授权</a></span>
					</div>
        	</div>
       
        	 <div class="mini-panel " title="功能辅助入口" width="auto" height="auto">
        	 	<div class="menuItem link" url="${ctxPath}/sys/customform/sysCustomFormSetting/list.do">
					<span class="iconfont icon-form-sol-50 span-icon"></span> 
					<span><a>表单方案设计</a></span>
				</div>
               <div class="menuItem link" url="${ctxPath}/sys/core/sysBoList/list.do">
				<span class="iconfont icon-list-50  span-icon"></span> 
					<span><a>业务列表设计</a></span>
				</div>
				<div class="menuItem link" url="${ctxPath}/sys/core/sysBoList/dialogs.do">
					<span class="iconfont icon-dialog-50  span-icon"></span> 
					<span><a>对话框设计</a></span>
				</div>
				<div class="menuItem link" url="${ctxPath}/sys/db/sysSqlCustomQuery/list.do">
					<span class="iconfont icon-search-50  span-icon"></span> 
					<span><a>查询设计</a></span>
				</div>
				<div class="menuItem link" url="${ctxPath}/sys/core/sysSeqId/list.do">
					<span class="iconfont icon-seq-no-50 span-icon"></span> 
					<span><a>流水号设计</a></span>
				</div>
				<div class="menuItem link" url="${ctxPath}/sys/core/sysDic/mgr.do">
					<span class="iconfont icon-book-50 span-icon"></span> 
					<span><a>数据字典设计</a></span>
				</div>
				<div class="menuItem link" url="${ctxPath}/sys/core/sysDataSource/list.do">
					<span class="iconfont icon-database-50  span-icon"></span> 
					<span><a>数据源设计</a></span>
				</div>
				 <div class="menuItem link" url="${ctxPath}/bpm/core/bpmInst/list.do">
				<span class="iconfont icon-bpm-inst-50  span-icon"></span> 
					<span><a>流程实例管理</a></span>
				</div>
				<div class="menuItem link" url="${ctxPath}/bpm/core/bpmTask/list.do">
					<span class="iconfont icon-bpm-task-50  span-icon"></span> 
					<span><a>待办管理</a></span>
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
					
					_OpenWindow({
						openType:'NewWin',
						title:title,
						url:url,
						height:500,
						width:800,
						max:true
					});
				});
			});
			
			
			
			$(".menuItem")
				.mouseenter(function(){
					$(this).stop(true,true).animate({top:-2},100)
				})
				.mouseleave(function(){
					$(this).stop(true,true).animate({top:0},100)
			});
</script>	
</body>
</html>
