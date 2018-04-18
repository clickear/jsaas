<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>功能展示区</title>
	<%@include file="/commons/list.jsp"%>
	<style type="text/css">
      	.menuItem {
			float: left;
			display: block;
			width: 120px;
			height:110px;
			margin: 10px;
			text-align: center;
			padding: 40px 0 0;
			cursor: pointer;
			border: solid 1px #eee;
		}
				
		.p_top i{
			float: left;
			text-align: center;
			width: 100%;
		}
		
		.p_top i:before{
			float: none;
			font-size:50px;
		}
		
		.menuItem h1{
			font-size:14px;
			line-height:16px;
			color:#666;
			text-align: center;
			font-weight: normal;
			margin:30px 0 0 0 ;
			float: left;
			width: 100%;
		}
		
		.mini-tab-text{
			margin-left: 4px;
		}
		
		.mini-tab {
			padding-left:10px;
			padding-right:10px;
		}
		
		.mini-panel-header-inner .mini-iconfont::before{
			font-size:20px !important;
			float: left;
			color: #fff;
		}
		
		.mini-panel .mini-panel-title{
			line-height:22px;
			color: #fff;
		}
		
		#tab-nav .mini-tab{
			border-color:#fff;
			border-bottom-color:#ececec;
			padding:6px 10px;
		}
		
		#tab-nav .mini-tab-active{
			background:#fff;
			border-bottom:solid 2px #29b4f7;
		}
		
		#tab-nav .mini-tab-active span{
			color:#29b4f7;
			font-weight:500;
		}
		
		#tab-nav .mini-tab .mini-iconfont:before{
			font-size:24px;
		}
		
		#tab-nav .mini-tab-active .mini-iconfont:before{
			color:#29b4f7;
		}
		
		.p_top:hover{
			box-shadow: 0 6px 18px 2px #e0e5e7;
			border-color: transparent;
		}
		
		.mini-panel-header-inner{
			padding: 6px 4px;
		}
		
		.mini-panel .mini-panel-icon{
		    line-height: 22px;
    		height: 22px;
		}
		
		.mini-fit{
			margin-top: 0;
		}
		
		.mini-tabs-bodys{
			border: none;
		}
		
		body .mini-tabs-plain .mini-tabs-headers{
			padding-top: 10px;
			background: #fff;
		}
		
	</style>
<!--[if IE 8 ]>
	<style>.p_top:hover{border-color: #ccc;}</style>
<![endif]-->
</head>
<body>
    	<!-- tab类型 -->
    	<c:if test="${menuDisplay=='tab'}">
    		<div class="mini-fit">
		        <div id="tab-nav" class="mini-tabs" tabPosition="top" style="width:100%;height:100%">
		         <c:forEach items="${menus}" var="menu">
		         	<div title="${menu.name}"  iconCls="${menu.iconCls}" style="margin: 5px;padding: 5px;">
						<c:forEach items="${menu.childList}" var="subMenu">		
				            <div 
				            	class="menuItem link p_top" 
				            	url="${subMenu.url}" 
				            	menuId="${subMenu.menuId}" 
		           				iconCls="${subMenu.iconCls}" 
		           				title="${subMenu.name}" 
		           				showType="${subMenu.showType}"
	           				>
								<i class="${subMenu.iconCls} span-icon"></i> 
								<h1 class="tabName">${subMenu.name}</h1>
							</div>												
						</c:forEach>										         
		         	</div>
		         </c:forEach>
		        </div>
	        </div>
        </c:if>
        <!-- 块状按钮类型 -->
        <c:if test="${menuDisplay=='block'}">
	        <div class="container">
		        <div class="mini-clearfix">
				<c:choose>
					<c:when test="${isMultiMenus==false}">
						<c:forEach items="${menus}" var="menu">
							<div 
								class="menuItem link p_top" 
								url="${menu.url}"
								menuId="${menu.menuId}" 
								iconCls="${menu.iconCls}"
								title="${menu.name}" 
								showType="${menu.showType}"
							>
								<i class="${menu.iconCls}"></i>
								<h1>${menu.name}</h1>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach items="${menus}" var="menu">
							<div 
								class="mini-panel" 
								iconCls="${menu.iconCls}" 
								title="${menu.name}" 
								width="auto"
								height="auto" 
								style="margin: 5px;padding: 5px;"
							>
								<c:forEach items="${menu.childList}" var="subMenu">
									<div 
										class="menuItem link p_top" 
										url="${subMenu.url}" 
										title="${subMenu.name}" 
										menuId="${menu.menuId}" 
										iconCls="${menu.iconCls}"
										showType="${menu.showType}"
									>
										<i class="${subMenu.iconCls}"></i>
										<h1>${subMenu.name}</h1>
									</div>
								</c:forEach>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
	
				</div>	
			</div>
        </c:if>
        
        
        
        
        
        <script type="text/javascript">
            mini.parse();
            $(function() {
    			$('.link').click(function() {
    				var link = $(this);
    				var showType = link.attr('showType');
    				var title = link.attr('title');
    				var url = link.attr('url');
    				var menuId=link.attr('menuId');
    				var iconCls=link.attr('iconCls');
    				
    				top['index'].showTabPage({
						title:title,
						url:url,
						showType:showType,
						iconCls:iconCls,
						menuId:menuId
					});
    			});
    		});
    		
        </script>
    </body>
</html>
