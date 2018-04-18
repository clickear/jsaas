<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>功能展示区</title>
   <%@include file="/commons/list.jsp"%>
        <style type="text/css">
        	.span-icon{
        		display: block;
        		padding:5px;
        		clear: both;
        		margin:5px;
        	}
        	
        	.menuItem{
	        	float:left;
	        	display: block;
	        	width:100px;
	        	margin:5px;
	        	text-align:center;
	        	padding:10px;
	        	cursor: pointer;
	        	border:solid 1px #eee
        	}
        	
        	.menuItem:hover{
        		background-color: yellow;
        	}
        </style>
    </head>
    <body>
        <div class="mini-tabs" tabPosition="left" style="width:100%;height:100%">
         <c:forEach items="${tabMenuMap}" var="tabMenu">
         	<div title="${tabMenu.key.name}"  iconCls="${tabMenu.key.iconCls}">
	         	
	         	<div style="margin:20px;padding:10px;">
        		
		         	<c:forEach items="${tabMenu.value}" var="menu">
		         		<div class="menuItem link"  id="${menu.key}" url="${menu.url}" menuId="${menu.menuId}"
							name="${menu.name}" showType="${menu.showType}">
							<c:choose>
								<c:when test="${menu.iconCls!=''}">
									<c:set var="iconCls" value="${menu.iconCls}"/>
								</c:when>
								<c:otherwise><c:set var="iconCls" value="icon-entform"/></c:otherwise>
							</c:choose>
			         		<span class="iconfont ${iconCls} span-icon" ></span>
			         		<span><a>${menu.name}</a></span>
		         		 </div>
		         	</c:forEach>
		      
		     </div>
         	</div>
         	
         	
         </c:forEach>
        </div>
        <script type="text/javascript">
            mini.parse();
            $(function() {
    			$('.link').click(function() {
    				var link = $(this);
    				var showType = link.attr('showType');
    				var id = link.attr('id');
    				var name = link.attr('name');
    				var url = link.attr('url');
    				var menuId=link.attr('menuId');
    				if (url!='' && url.indexOf('http') == -1) {
    					url = __rootPath + url;
    				}
    				if(showType=='FUN'){
    					url = __rootPath + '/ui/view/menuView/funPage.do?menuId='+id;
    				}else if(showType=='FUNS'){
    					url = __rootPath + '/ui/view/menuView/funsPage.do?menuId='+id;
    				}
    				
    				//打开窗口
    				if (showType == 'NEW_WIN') {
    					_OpenWindow({
    						title : name,
    						max : true,
    						height : 500,
    						width : 800,
    						url : url
    					});
    				}else{
    					top['index'].showTabFromPage({
    						iconCls:'icon-tab',
    	        			tabId:id,
    	        			title:name,
    	        			url:url
    	        		});
    				}
    			});
    		});
        </script>
    </body>
</html>
