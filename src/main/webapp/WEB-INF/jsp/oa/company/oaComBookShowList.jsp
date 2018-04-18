<%-- 
    Document   : [OaComBook]列表页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>公司通讯录列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>

	<redxun:toolbar entityName="com.redxun.oa.company.entity.OaComBook" excludeButtons="popupSearchMenu,popupAttachMenu,popupSettingMenu,fieldSearch,popupAddMenu" >
	<div class="self-toolbar">
			<a class="mini-button" iconCls="icon-add" plain="true" onclick="addBook">添加</a>
			<a class="mini-button" iconCls="icon-add" plain="true" onclick="addBooks">批量添加</a>
			<span class="separator"></span>
			<span>姓名：</span> <input id="name" name="Q_name_S_LK" class="mini-textbox" style="width:150px;"/>&nbsp;&nbsp;
			<a class="mini-button" onclick="onSearch" iconCls="icon-search">查询</a>
		</div>
		</redxun:toolbar>

	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" 
		url="${ctxPath}/oa/company/oaComBook/getByPath.do?catId=${param['catId']}" idField="comId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="name" width="50" headerAlign="center" allowSort="true">姓名</div>
				<div field="isEmployee" width="50" headerAlign="center" allowSort="true">内部员工</div>
				<div field="depName" width="100" headerAlign="center" allowSort="true">主部门</div>
				<div field="mobile" width="120" headerAlign="center" allowSort="true">主手机号</div>
				<div field="phone" width="100" headerAlign="center" allowSort="true">固话</div>
				<div field="email" width="120" headerAlign="center" allowSort="true">邮箱</div>
				<div field="qq" width="100" headerAlign="center" allowSort="true">QQ</div>
				
			</div>
		</div>
	</div>

	<script type="text/javascript">
			var catId = "${param['catId']}";
	
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-lock" title="权限" onclick="rightRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
        	
        	function rightRow(id){
        		_OpenWindow({
					url:"${ctxPath}/oa/company/oaComBook/right.do?pkId=" +id,
					title:"通讯录权限",
					height:600,
					width:800,
					ondestroy:function(action){
						if(action=='ok')
							grid.load();
					}
				});
        	}
        	
			//新增文档
			function addBook(){
				_OpenWindow({
					url:"${ctxPath}/oa/company/oaComBook/add.do?catId=" +catId,
					title:"添加通讯录",
					height:600,
					width:800,
					ondestroy:function(action){
						if(action=='ok')
							grid.load();
					}
				});
			}
			
			//批量新增文档
			function addBooks(){
				_OpenWindow({
					url:"${ctxPath}/oa/company/oaComBook/multiAdd.do?catId=" +catId,
					title:"批量添加通讯录",
					height:600,
					width:800,
					ondestroy:function(action){
						if(action =='ok')
							grid.load();
					}
				});
			}
			
			/*查询联系人*/
	         function onSearch(){
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
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.oa.company.entity.OaComBook" winHeight="450" winWidth="700" entityTitle="通讯录" baseUrl="oa/company/oaComBook" />
</body>
</html>