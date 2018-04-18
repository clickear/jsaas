<%-- 
    Document   : [OdDocument]列表页
    Created on : 2016-3-8, 16:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>[OdDocument]列表管理</title>
<%@include file="/commons/list.jsp"%>
<style type="text/css">
	.mini-layout-border>#center{
 		background: transparent;
	}
</style>
</head>
<body>
<ul id="treeMenu" class="mini-contextmenu" >
		<li iconCls="icon-add" onclick="addNode">添加分类</li>        
	    <li iconCls="icon-edit" onclick="editNode">编辑分类</li>
	    <li iconCls="icon-remove" onclick="delNode">删除分类</li>
	</ul>
	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	    <div  
	    	title="发文分类" 
	    	region="west"  
	    	width=250
	    	showSplitIcon="true"
	    	showCollapseButton="false"
	    	showProxy="false" 
    	>
	        <div id="toolbar1" class="mini-toolbar" borderStyle="border:0px;">
				<a class="mini-button" iconCls="icon-add" plain="true" onclick="addNode()">新建分类</a>
                <a class="mini-button" iconCls="icon-refresh" plain="true" onclick="refreshSysTree()">刷新</a>          
	        </div>
	         <ul 
	         	id="systree" 
	         	class="mini-tree"  
	         	style="width:100%; height:100%;" 
	         	url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_DISPATCH_DOCUMENT"
				showTreeIcon="true" 
				textField="name" 
				idField="treeId" 
				resultAsTree="false" 
				parentField="parentId" 
				expandOnLoad="true" 
				ondrawnode="onDrawNode"
                onnodeclick="treeNodeClick"  
                contextMenu="#treeMenu"
             >
            </ul>
	    </div>
	    <showHeader="true" showCollapseButton="false">
			<redxun:toolbar entityName="com.redxun.offdoc.core.entity.OdDocument" excludeButtons="popupAddMenu,popupAttachMenu,popupSearchMenu,detail,edit,remove,saveCurGridView,saveAsNewGridView"  >
				<div class="self-toolbar">
					<li>
						<a class="mini-button" iconCls="icon-add" onclick="addOne()" plain="true">发文拟稿</a>
					</li>
				</div>
			</redxun:toolbar>

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false" ondrawcell="onDrawCell" onupdate="_LoadUserInfo();_LoadGroupInfo();"
			<%-- url="${ctxPath}/offdoc/core/odDocument/listOdDoc.do" --%> idField="docId" multiSelect="true" showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
			<div property="columns">
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
				<div field="subject" width="220" headerAlign="center" allowSort="true">文件标题</div>
				<div field="issueNo" width="120" headerAlign="center" allowSort="true">发文字号</div>
				<div field="mainDepId" width="120" headerAlign="center" allowSort="true">主送单位</div>
				<div field="status" width="80" headerAlign="center" allowSort="true" renderer="onStatusRenderer">公文状态</div>
				<div field="issuedDate" width="80" headerAlign="center" allowSort="true" renderer="onRenderer">发布日期</div>
			</div>
		</div>
	</div>           
		</div>
	</div>

	<script type="text/javascript">
	mini.parse();
	var dispatchCat;
	
	var tree = mini.get(systree);
	var mynodes = tree.getChildNodes(tree.getRootNode());
	var firstPage;
	if (mynodes.length > 0) {
		firstPage = mynodes[0].treeId;//第一个节点的Id
		mini.get('datagrid1').setUrl("${ctxPath}/offdoc/core/odDocument/listData.do?treeId="+ firstPage+"&archType="+"0");
	} else {//如果没有tree，访问list1，给一个空的列表防止报错
		mini.get('datagrid1').setUrl("${ctxPath}/oa/pro/project/listBlank.do");
	}
	
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var archType=record.archType;
	            var s ="";
	            s+='<span class="icon-detail" title="明细" onclick="detailMyRow(\'' + pkId + '\')"></span>';
	            if(record.status==0){
	            s+=' <span class="icon-edit" title="编辑" onclick="editMyRow(\'' + pkId +'\',\''+archType+ '\')"></span>';
	            }
	            s+=' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
        	
	        function detailMyRow(pkId) {
		        _OpenWindow({
		        	url: __rootPath + "/offdoc/core/odDocument/get.do?pkId=" + pkId,
		            title: "发文明细", 
		            width: 960, 
		            height: 800,
		        });
		    }
	      //按分类树查找数据
			function treeNodeClick(e) {
				var node = e.node;
				var archType="0";
				grid.setUrl(__rootPath + '/offdoc/core/odDocument/listData.do?treeId='+ node.treeId+"&archType="+archType);
				grid.load();
				dispacthCat = node.treeId;
			}
	      
			//设置节点图标
			function onDrawNode(e) {
				var tree = e.sender;
				var node = e.node;
				e.iconCls = 'icon-folder';
			}
			//绘制人名等等
			function onDrawCell(e) {
		        var tree = e.sender;
		        var record=e.record;
		        var field=e.field;
		        	if(field=='issueDepId'){
		        	e.cellHtml='<a class="mini-group"  groupId="'+record.issueDepId+'"></a>';
		        	}
		        	if(field=='mainDepId'){
			        	e.cellHtml='<a class="mini-group"  groupId="'+record.mainDepId+'"></a>';
			        	}
		        	if(field=='ccDepId'){
			        	e.cellHtml='<a class="mini-group"  groupId="'+record.ccDepId+'"></a>';
			        	}
		        	
		        	
		        	if(field=='privacyLevel'){
		        		if(record.privacyLevel=='COMMON'){
		        			e.cellHtml='普通';
		        		}else if(record.privacyLevel=='SECURITY'){
		        			e.cellHtml='秘密';
		        		}else if(record.privacyLevel=='MIDDLE-SECURITY'){
		        			e.cellHtml='机密';
		        		}else if(record.privacyLevel=='TOP SECURITY'){
		        			e.cellHtml='绝密';
		        		}
		        	}
		        	
		        	if(field=='urgentLevel'){
		        		if(record.urgentLevel=='COMMON'){
		        			e.cellHtml='普通';
		        		}else if(record.urgentLevel=='URGENT'){
		        			e.cellHtml='紧急';
		        		}else if(record.urgentLevel=='URGENTEST'){
		        			e.cellHtml='特急';
		        		}else if(record.urgentLevel=='MORE_URGENT'){
		        			e.cellHtml='特提';
		        		}
		        	}
			}
			//新增节点
			function addNode(e) {
				var systree = mini.get("systree");
				var node = systree.getSelectedNode();
				var parentId = node ? node.treeId : 0;
				_OpenWindow({
					title : '添加节点',
					url : __rootPath + '/sys/core/sysTree/edit.do?parentId='
							+ parentId + '&catKey=CAT_DISPATCH_DOCUMENT',
					width : 720,
					height : 350,
					ondestroy : function(action) {
						if (action == 'ok'){
						systree.load();
						}
					}
				});
			}
			//编辑树节点
			function editNode(e) {
				var systree = mini.get("systree");
				var node = systree.getSelectedNode();
				var treeId = node.treeId;
				_OpenWindow({
					title : '新增项目分类',
					url : __rootPath + '/sys/core/sysTree/edit.do?pkId=' + treeId,
					width : 780,
					height : 350,
					ondestroy : function(action) {
						if(action='ok')
							systree.load();
						
					}
				});
			}

			//删除树节点
			function delNode(e) {
				var systree = mini.get("systree");
				var node = systree.getSelectedNode();
				mini.confirm("确定要删除？", "确定？",
        	            function (action) {
        	                if (action == "ok") {
        	                	_SubmitJson({
        	    					url : __rootPath + '/sys/core/sysTree/del.do?ids='+ node.treeId,
        	    					success : function(text) {
        	    						systree.load();
        	    					}
        	    				});
        	                	}
        	    	            });
			}
			
			//添加数据
			function addOne() {
				if (dispatchCat == undefined) {
					dispatchCat = firstPage;
				}
				_OpenWindow({
					url : __rootPath + "/offdoc/core/odDocument/edit.do?treeId="+dispatchCat,
					title : "拟稿",
					width : 800,
					height : 600,
					ondestroy : function(action) {
						if (action == 'ok' && typeof (addCallback) != 'undefined') {
							var iframe = this.getIFrameEl().contentWindow;
							addCallback.call(this, iframe);
						} else if (action == 'ok') {
							grid.reload();
						}
					}
				});
			}
			//添加数据收文
			function addOneIncoming() {
				if (dispatchCat == undefined) {
					dispatchCat = firstPage;
				}
				_OpenWindow({
					url : __rootPath + "/offdoc/core/odDocument/incomingEdit.do?treeId="+dispatchCat,
					title : "收文",
					width : 1000,
					height : 700,
					ondestroy : function(action) {
						if (action == 'ok' && typeof (addCallback) != 'undefined') {
							var iframe = this.getIFrameEl().contentWindow;
							addCallback.call(this, iframe);
						} else if (action == 'ok') {
							grid.reload();
						}
					}
				});
			}
			
			
			 function editMyRow(pkId,archType){
				 
				if(archType==1){//收文
					_OpenWindow({
			    		 url: __rootPath + "/offdoc/core/odDocument/incomingEdit.do?pkId="+pkId,
			            title: "编辑收文",
			            width: 1000, height: 700,
			            ondestroy: function(action) {
			                if (action == 'ok') {
			                    mini.get("datagrid1").reload();
			                }
			            }
			    	});
				}else{//发文
					_OpenWindow({
			    		 url: __rootPath + "/offdoc/core/odDocument/edit.do?pkId="+pkId,
			            title: "编辑发文",
			            width: 760, height: 800,
			            ondestroy: function(action) {
			                if (action == 'ok') {
			                    mini.get("datagrid1").reload();
			                }
			            }
			    	});
				}
				
			} 
			//刷新树
				function refreshSysTree() {
					var systree = mini.get("systree");
					systree.load();
				}
			
				//渲染时间
				 function onRenderer(e) {
			            var value = e.value;
			            if (value) return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
			            return "暂无";
			        }
				//渲染公文状态
				 function onStatusRenderer(e) {
			            var record = e.record;
			            var status = record.status;
			             var arr = [{'key' : '1', 'value' : '发文状态','css' : 'green'}, 
			    			        {'key' : '0','value' : '修改状态','css' : 'red'},
			    			        {'key' : '2','value' : '归档状态','css' : 'blue'}];
			    			return $.formatItemValue(arr,status);
			        }
				  	
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.offdoc.core.entity.OdDocument" winHeight="450" winWidth="700"
		entityTitle="公文" baseUrl="offdoc/core/odDocument" />
</body>
</html>