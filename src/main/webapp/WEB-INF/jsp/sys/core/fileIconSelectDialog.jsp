<%-- 
    Document   : iconSelectDialog
    Created on : 2017-5-26, 7:26:58
    Author     : cmc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>项目列表</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
*{
	font-family: '微软雅黑';
}

.displayPannel{
	width: 504px;
}

.divicon{
	float:left;
	cursor:pointer;
	border: 1px solid white;
	border-radius:4px;
	width: 38px;
	height:38px;
	background: #efefef !important;
	margin:0  1px;
    text-align: center;
}

.divicon:last-child::after{
	content:'';
	display:block;
	clear: both;
}

.divicon:hover{
	border: 1px solid #BCD2EE; 
}

.divicon::before{
    font-size: 24px;
    height: 38px;
    width: 38px;
    line-height: 40px;
    font-weight: normal;
    padding-right:0;
    margin-right:0;
}
.mini-panel-header-inner{
/* 	padding:0 !important; */
}

.mini-panel-header{
	background: #fff !important;
}

.mini-panel-header-inner span{
	display: none;
}

.mini-panel-header-inner .mini-panel-title{
	width: 100%;
	text-align: center;
}

.mini-pager{
	padding: 3px 0;
	border-top: 1px solid #ececec;
	width: 100%;
	position: fixed; 
	bottom: 0;
	left: 0;
	height: auto;
}
body{
	background: #fff;
}

.mini-messagebox .mini-panel-header{
	padding: 6px 0;
	background: #fff;
}

.mini-messagebox .mini-messagebox-content-text .iconfont:before{
	font-size: 26px;
}


</style>
</head>
    </head>
    <body>
	<div class="mini-fit">
		<div id="displayPannel" style="width: 100%;"></div>
		<div 
			id="pager" 
			class="mini-pager"
			onpagechanged="onPageChanged"
			showPageSize="false" 
			showPageSize="true" 
			showPageIndex="true" 
			showPageInfo="true" 
			buttons="#buttons"
		></div>
	</div>

	<script type="text/javascript">
		var iconArray;
		var chooseIconName;
		var displayPannel = $("#displayPannel");
		function getIcon() {
			return chooseIconName;
		}

		$(function() {
			iconArray = '${matchList}'.substring(1, '${matchList}'.length - 1).split(",");
			
			mini.parse();
			mini.get("pager").setTotalCount(iconArray.length);
			mini.get("pager").setPageSize(72);
			showTheIconArray(1);

		});

		function showTheIconArray(pageIndex) {
			if (pageIndex <= 0) {
				pageIndex = 1;
			}
			displayPannel.empty();
			for (var i = (pageIndex - 1) * (72); i < pageIndex * 72; i++) {
				if(i>=iconArray.length){
					break;
				}
				var icon = "<div class='divicon iconfont "
						+ iconArray[i] + "' id='" + iconArray[i] + "' ></div>"
				displayPannel.append(icon);
			}
			
			$(".divicon").click(function(){
				var obj=$(this);
				chooseIcon(obj.attr("id"));
			})
			
			
		}

		function onPageChanged(e) {
			showTheIconArray(e.pageIndex + 1);
		}
		
		function chooseIcon(iconName){
			mini.confirm("确定选中此图标 <span class='iconfont "+iconName+"' </span>", "",
		            function (action) {
		                if (action != "ok")  return;
		                
		                chooseIconName=iconName;
		                CloseWindow('ok');
		            }
		        );
		}
	</script>
       
    </body>
</html>
