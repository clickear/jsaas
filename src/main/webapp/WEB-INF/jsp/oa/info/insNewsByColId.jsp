<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/list.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${insColumn.name}-公告信息列表</title>
</head>
<body>
	<div class="titleBar mini-toolbar">
		<div class="searchBox">
			<form id="searchForm" class="text-distance" style="display: none;">						
				<ul>
					<li>
						标题 ：<input class="mini-textbox" id="subject" name="Q_subject_S_LK" emptyText="请输入标题" /> 
					</li>
					<li>
						关键字：<input class="mini-textbox" id="keywords" name="Q_keywords_S_LK" emptyText="请输入关键字" />
					</li>
					<li>
						作者：<input class="mini-textbox" id="author" name="Q_author_S_LK" emptyText="请输入作者" />
					</li>
					<li>
						<div class="searchBtnBox">
							<a class="mini-button _search" onclick="onSearch">搜索</a>
							<a class="mini-button _reset" onclick="onClear">清空</a>
						</div>
					</li>
					<li class="clearfix"></li>
				</ul>
			</form>	
			<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
				<i class="icon-sc-lower"></i>
			</span>
		</div>
    </div>
	<div class="mini-fit" style="height: 100px;">
		<div 
			id="newsgrid" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false" 
			url="${ctxPath}/oa/info/insNews/listByColId.do?colId=${colId}" 
			idField="newId" 
			multiSelect="true" 
			showColumnsMenu="true" 
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			onrowdblclick="onRowDblClick" 
			ondrawcell="onDrawCell" 
			allowAlternating="true"
		>
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="action" name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
				<div field="subject" width="150" headerAlign="center" allowSort="true">标题</div>
				<div field="keywords" width="100" headerAlign="center" allowSort="true">关键字</div>
				<div field="readTimes" width="80" headerAlign="center" allowSort="true">阅读次数</div>
				<div field="author" width="80" headerAlign="center" allowSort="true">作者</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
			mini.parse();
			//用于弹出的子页面获得父窗口
			top['main']=window;
			//console.log(${insColumn.colId});
			
			var colId='${colId}';
			var grid=mini.get('newsgrid');
			grid.load();
			var portal = "${param['portal']}";
			function onDrawCell(e) {
				var record = e.record;
				var uid = record.pkId;
				//功能键
				if (e.field == "action") {
					if (portal == "YES") {
						e.cellHtml = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>';
					}
				}
				//超链接
				if (e.field == "subject") {
					var sub = record.subject;
					e.cellStyle = "text-align:left";
					e.cellHtml = '<a href="javascript:detailRow(\'' + uid + '\')">' + sub + '</a>';

				}
			}
			function onRowDblClick(e) {
				var record = e.record;
				var pkId = record.pkId;
				var row = grid.getSelected();
				var title = row.subject;
				_OpenWindow({
					title:title,
					url:__rootPath+'/oa/info/insNews/get.do?permit=no&pkId='+pkId,
					width:800,
					height:800,
					//max:true
				});
			}
			//功能键
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.pkId;
	            var s = '<span class="icon-detail" title="明细" onclick="detailRow(\'' + uid + '\')"></span>'
	            + '<span class="icon-remove" title="删除" onclick="delNew(\'' + uid + '\')"></span>';
	            return s;
	        }
			
			function editNew(pkId){
				var colId = '${colId}';
				_OpenWindow({
					title:"编辑",
					url:__rootPath+'/oa/info/insColNew/edit.do?pkId='+pkId+'&colId='+colId,
					width:800,
					height:450,
				});
			}
			
			//查看明细
			function detailRow(pkId){
				var row = grid.getSelected();
				var title = row.subject;
				_OpenWindow({
					title:title,
					url:__rootPath+'/oa/info/insNews/get.do?permit=no&pkId='+pkId,
					width:800,
					height:800,
					//max:true
				});
			}
			//清空查询
			function onClear(){
				$("#searchForm")[0].reset();		
			}
			//查询
			function onSearch(){
				var formData=$("#searchForm").serializeArray();
				var filter=mini.encode(formData);
				grid.setUrl(__rootPath+'/oa/info/insNews/listByColId.do?colId='+colId+'&filter='+filter);
				grid.load();
			}
			
			//弹出选择新闻加入此栏目的页面
			function showInfoDialog(){
				_OpenWindow({
					title:'加入信息至栏目-${insColumn.name}',
					url:__rootPath+'/oa/info/insNews/dialog.do',
					width:800,
					height:500,
					ondestroy:function(action){
						if(action!='ok')return;
						var iframe = this.getIFrameEl();
			            var newsIds = iframe.contentWindow.getNewsIds();
			            if(newsIds=='')return;
			            _SubmitJson({
			            	url:__rootPath+'/oa/info/insNews/joinColumn.do',
			            	data:{
			            		colId:'${colId}',
			            		newsIds:newsIds
			            	},
			            	method:'POST',
			            	success:function(e){
			            		grid.load();
			            	}
			            });
			            
					}
				});
			}
			
			//删除此栏目与此新闻的关系
			function delNew(pkId){
				var colId = '${colId}';
				if (!confirm("确定删除选中记录？")) return;
			    console.log(pkId);
		        _SubmitJson({
		        	url:__rootPath + '/oa/info/insColNewDef/delNew.do',
		        	method:'POST',
		        	data:{ids: pkId,
		        		  colId:colId},
		        	 success: function(text) {
		                grid.load();
		            }
		         });
			}
	</script>
</body>
</html>