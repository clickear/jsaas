<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>功能菜单页 </title>
<%@include file="/commons/get.jsp"%>
<style type="text/css">
body{
	background: #fff;
}

.container{
	padding: 8px 0 0 8px;
}

.container .mini-panel{
	margin-right:20px;
	margin-bottom:20px;
}

.menuItem{
	float: left;
	display: block;
	width: 100px;
	height:130px;
	margin: 10px;
	text-align: center;
	padding: 10px;
	cursor: pointer;
	border: solid 1px #eee;
}

.iconfont{
	float: left;
	text-align: center;
	width: 100%;
	margin-bottom: 10px;
	padding-top: 30px;
}

.iconfont:before{
	float: none;
	font-size:40px;
}

.menuItem h1{
	font-size:14px;
	color:#666;
	text-align: center;
	font-weight: normal;
	margin:15px 0 0 0 ;
	float: left;
	width: 100%;
}


.p_top:hover{
	box-shadow: 0 6px 18px 2px #e0e5e7;
	border-color: transparent;
}	
</style>

<!--[if IE 8 ]>
	<style>.p_top:hover{border-color: #ccc;}</style>
<![endif]-->
</head>
	<body>
		 <div class="container">
	        <div class="mini-clearfix">
	        
	        <c:forEach items="${menus}" var="menu">
					<div class="menuItem link p_top" url="${menu.url}"
						menuId="${menu.menuId}" iconCls="${menu.iconCls}"
						title="${menu.name}" showType="${menu.showType}">
						<span class="iconfont ${menu.iconCls}"></span>
						<h1>${menu.name}</h1>
					</div>
			</c:forEach>

			</div>	
		</div>
			
		<script type="text/javascript">
			mini.parse();
			$(function() {
				$("div.menuItem").on('click', function() {
					var url = $(this).attr('url');
					var title = $(this).attr('title');
					var showType = $(this).attr('showType');
					var iconCls = $(this).attr('iconCls');
					var menuId = $(this).attr('menuId');
					top['index'].showTabPage({
						title : title,
						url : url,
						showType : showType,
						iconCls : iconCls,
						menuId : menuId
					});
				});
			});
		</script>	
    </body>
</html>
