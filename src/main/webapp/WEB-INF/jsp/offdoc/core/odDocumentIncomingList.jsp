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
</head>
<body>
          <redxun:toolbar entityName="com.redxun.offdoc.core.entity.OdDocument" excludeButtons="popupAddMenu,popupAttachMenu,popupSearchMenu,detail,edit,remove,saveCurGridView,saveAsNewGridView,popupSettingMenu"  >
				<div class="self-toolbar">
				</div>
			</redxun:toolbar>

	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div 
			id="datagrid1" 
			class="mini-datagrid" 
			style="width: 100%; height: 100%;" 
			allowResize="false" 
			ondrawcell="onDrawCell" 
			onupdate="_LoadUserInfo();_LoadGroupInfo();"
			url="${ctxPath}/offdoc/core/odDocument/listOdDoc.do" 
			idField="docId" 
			multiSelect="true" 
			showColumnsMenu="true"
			sizeList="[5,10,20,50,100,200,500]" 
			pageSize="20" 
			allowAlternating="true" 
		>
			<div property="columns">
				<div 
					name="action" 
					cellCls="actionIcons" 
					width="22" 
					headerAlign="center" 
					align="center" 
					renderer="onActionRenderer"
					cellStyle="padding:0;"
				>操作</div>
				<div field="subject" width="220" headerAlign="center" allowSort="true">文件标题</div>
				<div field="issueNo" width="120" headerAlign="center" allowSort="true">发文字号</div>
				<div field="issueDepId" width="120" headerAlign="center" allowSort="true">发文机关或部门</div>
				<div field="urgentLevel" width="80" headerAlign="center" allowSort="true">紧急程度</div>
				<div field="cntype" width="80" headerAlign="center" allowSort="true">公文的类型</div>
				<div field="issuedDate" width="80" headerAlign="center" allowSort="true"  renderer="onRenderer">发布日期</div>
			</div>
		</div>
	</div>           
		


	<script type="text/javascript">
	mini.parse();

	
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
		        	if(field=='status'){
		        		if(record.status==1){e.cellHtml='发文状态';}
			        	else if(record.status==0){
			        		e.cellHtml='修改状态';
			        	}else if(record.status==2){
			        		e.cellHtml='归档状态';
			        	}
		        	
		    } 
		    }
	      
			//渲染时间
			 function onRenderer(e) {
		            var value = e.value;
		            if (value) return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
		            return "暂无";
		        }
        	
			
			//添加数据
			function addOne() {
				
				_OpenWindow({
					url : __rootPath + "/offdoc/core/odDocument/edit.do?treeId="+dispatchCat,
					title : "拟稿",
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
			//添加数据收文
			function addOneIncoming() {
				
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
			
			//公文明细
			function detailMyRow(pkId) {
		        _OpenWindow({
		        	url: __rootPath + "/offdoc/core/odDocument/get.do?pkId=" + pkId,
		            title: "收文明细", 
		            width: 720, 
		            height: 800,
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
			            width: 1000, height: 700,
			            ondestroy: function(action) {
			                if (action == 'ok') {
			                    mini.get("datagrid1").reload();
			                }
			                
			            }
			    	});
				}
				
			} 
			 
		
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.offdoc.core.entity.OdDocument" winHeight="450" winWidth="700"
		entityTitle="[OdDocument]" baseUrl="offdoc/core/odDocument" />
</body>
</html>