<%--
	Document   : 外部邮件添加邮箱联系人列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>外部邮件添加邮箱联系人列表</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
.select {
	background-color: #AFEEEE;
	border: solid 1px #9a9a9a;
}

.noselect {
	background-color: white;
}

.innerpadding {
	padding-left: 5px;
}
</style>
</head>
<body>
	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;" borderStyle="border:solid 1px #aaa;">

		<div title="center" region="center" width="100%" showSplitIcon="true">
			<div id="toolbar1" class="mini-toolbar" style="padding: 2px;">
				<form id="searchForm">
					姓名： <input id="name" name="name" class="mini-textbox" /> 邮箱： <input id="mail" name="mail" class="mini-textbox" /><a class="mini-button" onclick="onSearch" iconCls="icon-search" plain="true">查询</a> <a class="mini-button" onclick="closeWindow" iconCls="icon-add" plain="true" style="float: right;">添加</a>
				</form>
			</div>
			<div class="mini-fit" style="width: 100%">
				<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" url="${ctxPath}/oa/personal/addrBook/getByGroupId.do" idField="fullName" multiSelect="true" showColumnsMenu="false" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div field="fullname" width="80" headerAlign="center" allowSort="true" align="center">姓名</div>
						<div field="contact" width="120" headerAlign="center" allowSort="true" align="center">邮箱地址</div>
					</div>
				</div>
			</div>
		</div><!-- end of center -->
		
		<div title="联系人分组" region="west" showCloseButton="false" width="200" showSplitIcon="true">
			<div class="mini-menu-inner">
				<div id="menu" class="mini-menu-float">
					<div id="all" class="mini-menuitem item select">
						<div class="mini-menuitem-inner">
							<div class="mini-menuitem-text item">全部</div>
						</div>
					</div>
					<!-- 联系人分组菜单 -->
					<c:forEach items="${addrGrps}" var="addrGrp" varStatus="stauts">
						<div id="${addrGrp.groupId}" class="mini-menuitem item">
							<div class="mini-menuitem-inner">
								<div class="mini-menuitem-text item">${addrGrp.name}</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<!-- end of mini-menu-float -->
			</div>
			<!-- end of mini-menu-inner -->
		</div>
	</div>
	<!-- end of layout -->
	<script type="text/javascript">
	mini.parse();
	
	$(function(){                     //删除miniui-menu样式的图标   添加padding样式
		$(".mini-menuitem-icon").remove();
		$(".mini-menuitem-allow").remove();
		$(".mini-menuitem-inner").addClass("innerpadding");
	});
	
	var datagrid=mini.get("datagrid1");
	datagrid.load();
	
	/*分组菜单添加样式和点击事件处理*/
	function itemclick(){                        
		$(this).parent().find(".select").removeClass("select");
		$(this).parent().children().addClass("noselect");
		$(this).removeClass("noselect");
		$(this).addClass("select"); 
		 	var data={};
			data.pageIndex=datagrid.getPageIndex();  //页码
			data.pageSize=datagrid.getPageSize();    //每页的记录数
		    data.sortField=datagrid.getSortField();   //排序字段
		    data.sortOrder=datagrid.getSortOrder();    //排序顺序
		    if($(this).attr("id")!="all"){   //如果不是"全部"分组
		   		data.Q_groupId=$(this).attr("id");
		    }
			datagrid.setUrl("${ctxPath}/oa/personal/addrBook/getByGroupId.do");
			datagrid.load(data);
	}
	$(".item").on("click",itemclick);
	
	/*获取所有联系人*/
	function getAllAddrBook(value){
		var rows = datagrid.getSelecteds();
		if(rows.length<=0){
			return '';
		}else{
			var data=[];
			for(var i=0;i<rows.length;i++){
				if(value.indexOf(rows[i].contact)>-1)
					continue;
				data.push(rows[i]);
			}
			return data;
		}
	}
	
	/*关闭窗口*/
	function closeWindow(){
		CloseWindow('ok');
	}
	
	/*搜索联系人*/
    function onSearch(e){
    	var groupId=$(".select").attr("id");
    	var name=mini.get("name").getValue();
    	var mail=mini.get("mail").getValue();
		 var button = e.sender;
		 var el=button.getEl();
		 var form=$(el).parents('form');
		 
		 if(form!=null){
		 	var data={};
		 	//加到查询过滤器中
		 	data.Q_mail_S_LK=mail;
		 	data.Q_name_S_LK=name;
			data.pageIndex=datagrid.getPageIndex();  //页码
			data.pageSize=datagrid.getPageSize();    //每页的记录数
		    data.sortField=datagrid.getSortField();   //排序字段
		    data.sortOrder=datagrid.getSortOrder();    //排序顺序
		    if(groupId!="all"){  //如果不是"全部"分组
		   		data.Q_groupId=groupId;
		    }
			datagrid.load(data);
		 }
    }
	</script>
</body>
<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
</html>