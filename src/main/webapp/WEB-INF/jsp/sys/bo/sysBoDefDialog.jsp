<%-- 
    Document   : [BO定义]列表页
    Created on : 2017-03-01 23:24:22
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[BO定义]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">	
	     <div region="south" showSplit="false" showHeader="false" height="40" showSplitIcon="false"  
	     style="width:100%" bodyStyle="border:0;text-align:center;padding-top:5px;">
			
			     <a class="mini-button" iconCls="icon-ok"   onclick="onOk()">确定</a>
				    <a class="mini-button" iconCls="icon-cancel"  onclick="onCancel()">取消</a>
				 
		 </div> 
		    
		     <div title="业务视图分类" region="west" width="180"  showSplitIcon="true" >
		         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_FORM_VIEW" style="width:100%;height:100%;" 
					showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
	                onnodeclick="treeNodeClick"  >        
	            </ul>
		    </div> 
		     
		    <div title="业务视图列表" region="center" showHeader="false" showCollapseButton="false">
				 <div class="mini-toolbar" >
				 	<table style="width:100%;">
		             <tr>
		                 <td style="width:100%;">
		                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchForm(this)">查询</a>
		                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="onClearList(this)">清空</a>
		                 </td>
		             </tr>
		              <tr>
		                  <td style="white-space:nowrap;/*  */ class="search-form" >
		                     <form class="text-distance">
								<div>
									<span class="text">名称:</span><input class="mini-textbox" name="Q_NAME__S_LK">
									<span class="text">别名:</span><input class="mini-textbox" name="Q_ALAIS__S_LK">
								</div>
							</form>
		                  </td>
		              </tr>
		         </table>
                 </div>
		         <div class="mini-fit rx-grid-fit">
						<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
							url="${ctxPath}/sys/bo/sysBoDef/listData.do?Q_SUPPORT_DB__S_EQ=${param.Q_SUPPORT_DB__S_EQ}" idField="id"
							multiSelect="${param.multi}" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
							<div property="columns">
								<div type="checkcolumn" width="20"></div>
								<div field="name"  sortField="NAME_"  width="120" headerAlign="center" allowSort="true">名称</div>
								<div field="alais"  sortField="ALAIS_"  width="120" headerAlign="center" allowSort="true">别名</div>
								<div field="supportDb"  sortField="SUPPORT_DB_" renderer="onDbRenderer" width="120" headerAlign="center" allowSort="true">支持数据表</div>
								<div field="createTime" sortField="CREATE_TIME_" dateFormat="yyyy-MM-dd HH:mm:ss" width="120" headerAlign="center" allowSort="true">创建时间</div>
							</div>
						</div>
		        </div>
		    </div>
	</div> 

	<script type="text/javascript">
		 
	
		//行功能按钮
		function onDbRenderer(e) {
	            var record = e.record;
	            var supportDb = record.supportDb;
	            var arr = [ { 'key' : 'yes','value' : '支持','css' : 'red'}, 
				            {'key' : 'no', 'value' : '不支持','css' : 'green'} ];
				return $.formatItemValue(arr,supportDb);
	            
	    }
		
		function getBoDefs(){
			return grid.getSelecteds();
		}
		
		function onCancel(){
			CloseWindow('cancel');
		}
		
		function onOk(){
			CloseWindow('ok');
		}
		
		//按分类树查找数据字典
	   	function treeNodeClick(e){
	   		var node=e.node;
	   		grid.setUrl("${ctxPath}/sys/bo/sysBoDef/listData.do?Q_TREE_ID__S_EQ="+node.treeId);
	   		grid.load();
	   	}
		
		
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.bo.entity.SysBoDef" winHeight="450"
		winWidth="700" entityTitle="BO定义" baseUrl="sys/bo/sysBoDef" />
</body>
</html>