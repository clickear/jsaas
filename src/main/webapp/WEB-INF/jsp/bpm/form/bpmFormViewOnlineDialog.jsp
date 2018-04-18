<%-- 
    Document   : 业务视图对话框
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>业务视图对话框</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">	
	    <div region="south" showSplit="false" showHeader="false" height="34" showSplitIcon="false"  style="width:100%" bodyStyle="border:0">
			<div class="mini-toolbar dialog-footer" style="text-align:center;border:none;" >
			    <a class="mini-button" iconCls="icon-ok" onclick="CloseWindow('ok');">确定</a>			  
			    <a class="mini-button" iconCls="icon-cancel" onclick="CloseWindow('cancel');">取消</a>
			</div>	 
		 </div> 
		    
		    <%-- <div title="业务视图分类" region="west" width="180"  showSplitIcon="true" >
		         <ul id="systree" class="mini-tree" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_FORM_VIEW" style="width:100%;" 
					showTreeIcon="true" textField="name" idField="treeId" resultAsTree="false" parentField="parentId" expandOnLoad="true"
	                onnodeclick="treeNodeClick"  >        
	            </ul>
		    </div> --%>
		     
		    <div title="业务视图列表" region="center" showHeader="false" showCollapseButton="false">
				 <div class="mini-toolbar" >
				 	<table style="width:100%;">
				 		<tr>
				 			<th>
				                <a class="mini-button" iconCls="icon-search" onclick="searchForm(this)">查询</a>
							    <a class="mini-button" iconCls="icon-cancel"  onclick="onClearList(this)">清空</a>
				 			</th>
				 		</tr>
			            <tr>			                	
			               <td style="width:100%;">
			                  <form  class="text-distance">	
	                    		<div>
		                    		<span class="text">名称：</span><input class="mini-textbox" name="Q_NAME__S_LK"/>&nbsp;
	                       			<span class="text">标识键:</span><input class="mini-textbox" name="Q_KEY__S_LK"/>
		                    		<input class="mini-hidden" name="Q_boDefId_S_EQ" value="${param.Q_boDefId_S_EQ}" />
	                    		</div>
			                   </form>
			               </td>
			             </tr>
			        </table>
                 </div>
		         <div class="mini-fit rx-grid-fit form-outer5">
			        
						<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
						 allowResize="false" url="${ctxPath}/bpm/form/bpmFormView/onlineSearch.do" idField="fmId" multiSelect="${multiSelect}"
						 sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
							<div property="columns">
								<div type="checkcolumn" width="20"></div>
								<div field="name" width="140" headerAlign="center" >名称</div>
								<div field="key" width="140" headerAlign="center" >标识键</div>
								<div field="version" width="80" headerAlign="center">版本号</div>
							</div>
						</div>
		        </div>
		    </div><!-- end of the center region  -->	
		     
		  
		</div>
		<script type="text/javascript">
		var boDefId="${param.Q_boDefId_S_EQ}";
		mini.parse();
		var grid=mini.get('datagrid1');
		var multiSelect='${multiSelect}';
		grid.load();
		
		   	
	   	//按分类树查找数据字典
	   	function treeNodeClick(e){
	   		var node=e.node;
	   		grid.setUrl(__rootPath+'/bpm/form/bpmFormView/onlineSearch.do?Q_boDefId_S_EQ='+boDefId+'&treeId='+node.treeId);
	   		grid.load();
	   	}
	   	
	   	function getFormView(){
	   		return grid.getSelecteds();	
	   	}
	   	
	   	function onOk(){
	   		CloseWindow('ok');
	   	}
	   	
	   	function onCancel(){
	   		CloseWindow('cancel');
	   	}
	   	
    </script>
	
	
</body>
</html>