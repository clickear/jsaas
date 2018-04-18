<%-- 
    Document   : 联系人分组列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>联系人分组列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
body{
	background: #f7f7f7
}

.mini-layout-border>#center{
	background: transparent;
}

</style>

</head>
<body>

	<div id="layout1" class="mini-layout" style="width: 100%; height: 100%;" bodyStyle="border:none;">

		<div title="center" region="center" width="100%" showSplitIcon="true">
			<div class="titleBar mini-toolbar" >
		         <ul>
					<li>
						<a class="mini-button" iconCls="icon-add" plain="true" onclick="addAddrBook">添加联系人</a>
					</li>
					<li>
						<a class="mini-button" iconCls="icon-remove" plain="true" onclick="delAddrBook">刪除联系人</a>
					</li>
					<li>
						<a id="manager" class="mini-button" visible="false" iconCls="icon-setting" plain="true" onclick="managerGrp" style="float: right;">管理分组</a>
					</li>
					<li class="clearfix"></li>
				</ul>
				<div class="searchBox">
					<form id="searchForm" class="search-form" style="display: none;">						
						<ul>
							<li>
								<span class="text">姓名：</span>
								<input id="name" name="Q_name_S_LK" class="mini-textbox" />
							</li>
							<li>
								<span>方法描述：</span><input class="mini-textbox" name="Q_METHOD_DESC__S_LK">
							</li>
							<li class="searchBtnBox">
								<a class="mini-button _search" onclick="onSearch" >搜索</a>
								<a class="mini-button _reset" onclick="onClearList(this)">清空</a>
							</li>
							<li class="clearfix"></li>
						</ul>
					</form>	
					<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
						<i class="icon-sc-lower"></i>
					</span>
				</div>
	     	</div>
			<div class="mini-fit rx-grid-fit form-outer5" style="width: 100%">
				<div 
					id="datagrid1" 
					class="mini-datagrid" 
					style="width: 100%; height: 100%;" 
					allowResize="false" 
					url="${ctxPath}/oa/personal/addrBook/getAllAddrBook.do" 
					idField="addrId" 
					multiSelect="true" 
					showColumnsMenu="true" 
					sizeList="[5,10,20,50,100,200,500]" 
					pageSize="20" allowAlternating="true" 
					pagerButtons="#pagerButtons" 
					onrowdblclick="getInfo"
				>
					<div property="columns">
						<div type="indexcolumn" width="20">序号</div>
						<div type="checkcolumn" width="20"></div>
						<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
						<div field="name" width="120" headerAlign="center" allowSort="true" align="center">姓名</div>
					</div>
				</div>
			</div>
		</div>
		<div 
			title="联系人分组" 
			region="east" 
			showCloseButton="false" 
			width="260" 
			showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false"
			style="border-right:solid 1px #ececec;"
		>
			<div id="toolbar2" class="mini-toolbar toolbar-margin topBtn" style="width: 100%; padding: 0; border-top: 0; border-left: 0; border-right: 0;">
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addGroup()" tooltip="新建联系人分组">新建分组</a>
				<a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshMenu()" tooltip="刷新">刷新</a>
			</div>
			<div class="mini-menu-inner">
				<div id="menu" class="mini-menu-float">
					<div id="all" class="mini-menuitem item title select">
						<div class="mini-menuitem-inner">
							<div class="mini-menuitem-text item ">全部(${total})</div>
						</div>
					</div>
					<c:forEach items="${addrGrps}" var="addrGrp" varStatus="stauts">
						<div id="${addrGrp.groupId}" class="mini-menuitem item">
							<div class="mini-menuitem-inner">
								<div class="mini-menuitem-text item">${addrGrp.name}(${addrGrp.total})</div>
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
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.personal.entity.AddrBook" winHeight="450" winWidth="700" entityTitle="联系人分组" baseUrl="oa/personal/addrGrp" />
	<script type="text/javascript">
    top['grp']=window;
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.pkId;
			var s = '<span class="icon-detail" title="查看" onclick="check(\''
					+ pkId
					+ '\')"></span>'
					+ ' <span class="icon-edit" title="编辑" onclick="edit(\''
					+ pkId
					+ '\')"></span>'
					+ ' <span class="icon-remove" title="删除" onclick="del(\''
					+ pkId + '\')"></span>';
			return s;
		}
		/*删除miniui-menu样式的图标   添加padding样式*/
		$(function(){                     
			$(".mini-menuitem-icon").remove();
			$(".mini-menuitem-allow").remove();
			$(".mini-menuitem-inner").addClass("innerpadding");
		});
		

		
		var datagrid=mini.get("datagrid1");
		var btnManager=mini.get("manager");
		
		/*联系人详情*/
		function check(pkId){                  
	            _OpenWindow({
	            	title:"联系人详情",
	            	height:600,
	            	width:900,
	            	url:__rootPath+"/oa/personal/addrBook/get.do?pkId="+pkId
	            }); 
		}
		
		/*编辑联系人*/
		function edit(pkId){               
	        _OpenWindow({
	    		 url: __rootPath+"/oa/personal/addrBook/edit.do?pkId="+pkId,
	            title: "编辑",
	            width: 900, 
	            height:600,
	            ondestroy: function(action) {
	                if (action == 'ok') {
	                	datagrid.load();
	                }
	            }
	    	});
		}
		
		/*删除联系人*/
		function del(pkId){             
	        if (!confirm("确定删除选中记录？")) return;
	        
	        _SubmitJson({
	        	url:__rootPath+"/oa/personal/addrBook/del.do",
	        	method:'POST',
	        	data:{ids: pkId},
	        	 success: function(text) {
	                datagrid.load();
	            }
	         });
		}
		
		/*添加联系人分组*/
		function addGroup(){                  
				_OpenWindow({
					title : '新建联系人分组',
					url : __rootPath+ "/oa/personal/addrGrp/edit.do",
					width : 720,
					height : 430,
					ondestroy : function(action) {
							if(action=='ok')
								 refreshMenu(); 
					}
				});
		} 
		
		/*分组菜单添加样式和点击事件处理*/
		function itemclick(){                        
			$(this).parent().find(".select").removeClass("select");
			$(this).parent().children().addClass("noselect");
			$(this).removeClass("noselect");
			$(this).addClass("select"); 
			if($(this).attr("id")=="all"){         //如果为全部的分组 则不显示管理分组的按钮
				btnManager.setVisible(false);
				datagrid.setUrl("${ctxPath}/oa/personal/addrBook/getAllAddrBook.do");
				datagrid.load();
			}else{
				btnManager.setVisible(true);
				datagrid.setUrl("${ctxPath}/oa/personal/addrBook/getAddrBookByGroupId.do?groupId="+$(this).attr("id"));
				datagrid.load();
			}
		}
		

		$(".item").on("click",itemclick);
		

		/*双击行打开联系人详情*/
    	function getInfo(e){      
            var record = e.record;
            var pkId = record.pkId;
            var name = record.name;
            _OpenWindow({
            	title:name,
            	height:600,
            	width:900,
            	url:__rootPath+"/oa/personal/addrBook/get.do?pkId="+pkId
            });
    	}
    	
		/*添加联系人*/
    	function addAddrBook(){                   
    		var id=$("#menu").find(".select").attr("id");
    		_OpenWindow({
    			url:__rootPath+"/oa/personal/addrBook/edit.do?groupId="+id,
    			title:"新建联系人",
    			height:600,
    			width:900,
    			ondestroy:function(action){
    				if(action=='ok')
    					rerefreshMenu();
    			}
    		});
    	}
    	
		/*删除联系人*/
    	function delAddrBook(){         
    	        var rows = datagrid.getSelecteds();
    	        if (rows.length <= 0) {
    	        	alert("请选中一条记录");
    	        	return;
    	        }
    	        //行允许删除
    	        if(rowRemoveAllow && !rowRemoveAllow()){
    	        	return;
    	        }
    	        
    	        if (!confirm("确定删除选中记录？")) return;
    	        
    	        var ids = [];
    	        for (var i = 0, l = rows.length; i < l; i++) {
    	            var r = rows[i];
    	            ids.push(r.pkId);
    	        }

    	        _SubmitJson({
    	        	url:__rootPath+"/oa/personal/addrBook/del.do",
    	        	method:'POST',
    	        	data:{ids: ids.join(',')},
    	        	 success: function(text) {
    	        		 datagrid.load();
    	        		 rerefreshMenu();
    	            }
    	        });
    	}
    	
		/*刷新菜单*/
    	function refreshMenu(){       
    		btnManager.setVisible(false);
    		_SubmitJson({
    			url:__rootPath+"/oa/personal/addrGrp/refreshMenu.do",
    			data:{},
    			method:'POST',
    			showMsg:false,
    			success:function(result){
    				$("#menu").empty();
    				var allLi=$("<div id='all' class='mini-menuitem item select' ><div class='mini-menuitem-inner'><div class='mini-menuitem-text item'>全部("+result.data.sum+")</div></div></div>");     //添加全部分组项
    				allLi.on('click',itemclick);
    				var menu=$("#menu");
					menu.append(allLi);			
					var list=result.data.list;
    			      for(var i=0;i<list.length;i++){  
    			    	  var li=$("<div id='"+list[i].groupId+"' class='mini-menuitem item'><div class='mini-menuitem-inner'><div class='mini-menuitem-text item'>"+list[i].name+"("+list[i].total+")</div></div></div>");   //添加其他分组菜单项
    					  menu.append(li);	
   		        	} 
    				datagrid.setUrl("${ctxPath}/oa/personal/addrBook/getAllAddrBook.do");
    				datagrid.load();
    			     
    			    mini.parse();
    				$(".mini-menuitem-icon").remove();
    				$(".mini-menuitem-allow").remove();
    				$(".mini-menuitem-inner").addClass("innerpadding");
    			    $(".item").on("click",itemclick);        //为菜单项添加单击事件
    			}
    		});
    		 
    	}
    	
		/*管理分组*/
    	function managerGrp(){
    		var id=$("#menu").find(".select").attr("id");
 			_OpenWindow({
				title : '管理分组',
				url : __rootPath+ "/oa/personal/addrGrp/edit.do?pkId="+id,
				width : 720,
				height : 430,
				ondestroy : function(action) {
						if(action=='ok')
							 refreshMenu(); 
				}
			}); 
    	}
    	
		/*查询联系人*/
         function onSearch(e){
  			var button = e.sender;
			var name=mini.get('name');
		 	var data={};
		 	//加到查询过滤器中
			data.filter=mini.encode([{name:'Q_name_S_LK',value:name.getValue()}]);
			data.pageIndex=grid.getPageIndex();
			data.pageSize=grid.getPageSize();
		    data.sortField=grid.getSortField();
		    data.sortOrder=grid.getSortOrder();
			grid.load(data);
			 
        } 
	</script>


</body>
</html>