
<%-- 
    Document   : [文章]编辑页
    Created on : 2017-09-29 14:39:26
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文章页面</title>
<%@include file="/commons/edit.jsp"%>
<style>
.mini-splitter-handler{
	display: block;
}
</style>
</head>

<body>
	<ul id="popupMenu" class="mini-menu" style="display:none;">
	    <li iconCls="icon-move-in" onclick="exportArticle">导出HTML</li>
	    <li iconCls="icon-move-in" onclick="downloadPDF">导出PDF</li>
    </ul>
    
	<div class="mini-splitter" style="width:100%;height:100%;" allowResize="true">
	    <div size="20%" showCollapseButton="true">
			<div class="mini-toolbar">
			    <a class="mini-button" iconCls="icon-preview" onclick="previewHtml">在线预览</a>
			    <a class="mini-menubutton" iconCls="icon-move-in" menu="#popupMenu">导出</a>
			</div>    
			<div class="mini-fit">
				<ul id="tree1" class="mini-tree" url="${ctxPath}/oa/article/proArticle/getAllByItemId.do?itemId=${itemId}" style="width: 400px;height:100%; padding: 5px;" showTreeIcon="true" textField="title"
				idField="id"  parentField="parentId" allowDrag="true"  allowDrop="true" onnodeclick="changeFrame" onbeforedrop="onbeforedrop" contextMenu="#treeMenu" resultAsTree="false" expandOnLoad="true" ondrawnode="onDrawNode">
				</ul>
			</div>
		</div>
	    <div showCollapseButton="false">
	        <iframe id="contentFrame" frameborder="0" scrolling="auto"   style="width: 100%;height: 100%;"></iframe>
	    </div>        
	</div>
	<ul id="treeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen" >
		<li name="add" iconCls="icon-add" onclick="onAddNode">添加</li>
		<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除</li>
		<li name="move" iconCls="icon-leader" onclick="onMoveNode">移动</li>
		<li name="exportZip" iconCls="icon-var" onclick="exportZip">迁出数据</li>
		<li name="importZip" iconCls="icon-handle" onclick="importZip">迁入数据</li>
	</ul>
	
	<script type="text/javascript">
	mini.parse();
	var tree = mini.get("tree1");
	var moveSignal=false;
	var preClickNodeId=null;
	
	function changeFrame(e){
		var node=e.node;
		if(moveSignal){//移动点击
			moveSignal=false;	
			mini.confirm("确定移动到此处?", "提示",
		            function (action) {
		                if (action == "ok") {
		                	var legalityCheck=checkLegality(preClickNodeId,node.id);
		                	if(legalityCheck){//检查合法性
		                		$.ajax({
				                	   url:"${ctxPath}/oa/article/proArticle/moveArticle.do",
				                	   type:"post",
				                	   data:{targetId:node.id,nodeId:preClickNodeId},
				                	   success:function(result){
				                		   mini.showTips({
				                	            content: "<b>成功</b> <br/>移动成功",
				                	            state: 'success',
				                	            x: 'center',
				                	            y: 'center',
				                	            timeout: 3000
				                	        }); 
				                		   tree.reload();
				                		   tree.collapseAll();
				                		   var nodes = tree.findNodes(function(fnode){
				                			    if(fnode.id== node.id) return true;
				                			});
				                			var targetNode=nodes[0];//操作的node
				                			tree.expandPath(targetNode);
				                	   }
				                   });
		                	}else{
		                		mini.showTips({
	                	            content: "<b>失败</b> <br/>不合法的移动",
	                	            state: 'success',
	                	            x: 'center',
	                	            y: 'center',
	                	            timeout: 3000
	                	        }); 
		                	}
		                }
		                
		                preClickNodeId=null;
		            }
		        );
		}else{//正常点击
			if(node.type!="root"){//根节点点击不触发编辑动作
				$("#contentFrame").attr("src","${ctxPath}/oa/article/proArticle/edit.do?pkId="+node.id+"&parentId="+node.parentId+"&itemId="+node.belongProId);
			}
		}
		
		
	}
	
	function exportArticle(){
		window.location.href="${ctxPath}/oa/article/proItem/exportArticle.do?pkId=${param['pkId']}";
	}
	
	function downloadPDF(pkId){
		mini.prompt("不输入或者取消则不加密", "请添加PDF密码",
	            function (action, value) {
	                if (action == "ok") {
	                	window.location.href="${ctxPath}/oa/article/proArticle/parseHtml2Pdf.do?itemId=${param['pkId']}&password="+value;
	                } else {
	                	window.location.href="${ctxPath}/oa/article/proArticle/parseHtml2Pdf.do?itemId=${param['pkId']}";
	                }
	            }
	        );
		
			
	}
	
	
	function initIframe(){
		$("#contentFrame").attr("src","");
	}
	
	function onbeforedrop(e){
		var node =e.dragNode;
		var targetNode=e.dropNode;
		var dragAction=e.dragAction;
		if((dragAction!="add")||(node.id=="")||(targetNode.id=="")){
			e.cancel=true;//取消拖拽
		}
		if(!e.cancel){
			$.ajax({
				url:"${ctxPath}/oa/article/proArticle/moveArticle.do",
				type:"post",
				data:{targetId:targetNode.id,nodeId:node.id},
				success:function (){
					mini.showTips({
			            content: "<b>成功</b> <br/>投放成功",
			            state: 'success',
			            x: 'center',
			            y: 'center',
			            timeout: 3000
			        });
				}
			});
		}else{
			mini.showTips({
	            content: "<b>非法拖拽</b> <br/>节点只允许移动到节点内",
	            state: 'danger',
	            x: 'center',
	            y: 'center',
	            timeout: 3000
	        });
		}
	}
	
	function dataSaveResult(id){
		tree.reload();
		tree.collapseAll();
		var nodes = tree.findNodes(function(node){
		    if(node.id== id) return true;
		});
		var targetNode=nodes[0];//操作的node
		tree.expandPath(targetNode);
		mini.showTips({
            content: "<b>成功</b> <br/>数据保存成功",
            state: 'success',
            x: 'center',
            y: 'center',
            timeout: 3000
        });
	}
	
	function onDrawNode(e){
		 var tree = e.sender;
         var node = e.node;
         var nodeType=node.type;
		 if(nodeType=="index"){
			 e.iconCls = "icon-end";
		 }else if(nodeType=="article"){
			 e.iconCls = "icon-copydown";
		 }else{
			 e.iconCls = "icon-node";
		 }
 
	}
	
	//阻止浏览器默认右键菜单
	function onBeforeOpen(e) {
		var menu = e.sender;
		var node = tree.getSelectedNode();
		if (!node) {
			e.cancel = true;
			return;
		}
		if (node && node.text == "Base") {
			e.cancel = true;
			e.htmlEvent.preventDefault();
			return;
		}
		var addItem = mini.getbyName("add", menu);
	    var removeItem = mini.getbyName("remove", menu);
	    var moveItem = mini.getbyName("move", menu);

	    if (node.type=="root") {
	    	removeItem.disable();
	    	moveItem.disable();
	    }
	    if (node.type!="root") {
	        removeItem.enable();
	        moveItem.enable();
	    }
	    if(node.id==""){
	    	addItem.disable();
	    }
	    if(node.id!=""){
	    	addItem.enable();
	    }
	    if(moveSignal){
	    	removeItem.disable();
	    	moveItem.disable();
	    	addItem.disable();
	    }
	    if(!moveSignal){
	    	removeItem.enable();
	    	moveItem.enable();
	    	addItem.enable();
	    }
	    

	}
	
	function onAddNode(e){
		 var node = tree.getSelectedNode();
		 var newNode = {};
		 newNode.parentId=node.id;
		 newNode.id="";
		 newNode.title="新文章";
		 newNode.belongProId="${itemId}";
		 tree.addNode(newNode,0,node);
		 $("#contentFrame").attr("src","${ctxPath}/oa/article/proArticle/edit.do?parentId="+node.id+"&itemId=${itemId}");
		 tree.selectNode(newNode);
	}
	
	function onRemoveNode(e){
		var node = tree.getSelectedNode();
		var id=node.id;
		var nodes=tree.getAllChildNodes(node);
		var ids=id;
		for(var i=0;i<nodes.length;i++){
			var subNode=nodes[i];
			ids+=","+subNode.id;
		}
		mini.confirm("确认删除?", "提示",
	            function (action) {
	                if (action == "ok") {
	                	_SubmitJson({
	                    	url:"${ctxPath}/oa/article/proArticle/del.do",
	                    	method:'POST',
	                    	data:{ids: ids},
	                    	 success: function(text) {
	                            tree.reload();
	                            $("#contentFrame").attr("src","");
	                        }
	                     });	
	                }
	            }
	        );
		
		
		
	}
	
	function previewHtml(){
		$.ajax({
			url:"${ctxPath}/oa/article/proArticle/previewHtml.do",
			type:"post",
			data:{itemId:"${itemId}"},
			success:function(result){
				if(result.success){
					top.showTabFromPage({
						title : '预览文档',
						tabId : "article${itemId}",
						url : "${ctxPath}/article/${itemId}/index.html"
					});
				}
			}
		});
	}
	
	function onMoveNode(e){
		var node = tree.getSelectedNode();
		var id=node.id;
		 mini.confirm("请选中需要移动到的位置", "提示",
		            function (action) {
		                if (action == "ok") {
		                	preClickNodeId=id;
		                    moveSignal=true;
		                }
		            }
		        );
	}
	
	/**
	检查移动节点是否合法
	thisNodeId:将移动的节点Id
	targetNodeId:移动目的地节点Id
	*/
	function checkLegality(thisNodeId,targetNodeId){
		if(targetNodeId=="0"){//祖宗特权不检查
			return true;
		}
		 var nodes = tree.findNodes(function(fnode){//调用api查找算法获取节点
			    if(fnode.id== targetNodeId) return true;
			});
		 var targetNode=nodes[0];//移动目的地node
		 if(targetNode.parentId==thisNodeId||thisNodeId==targetNodeId){//获取节点父ID判断是否乱伦
			 return false;
		 }
		 if(targetNode.parentId=='0'){//祖宗很干净,检查完毕
			 return true;
		 }
		 return  checkLegality(thisNodeId,targetNode.parentId);//迭代直到父ID=0
	}
	
	function exportZip(){
		var node = tree.getSelectedNode();
		
		var nodes=tree.getAllChildNodes(node);
		node.prefixTitle="first";//标记为导出点
		nodes.unshift(node);
		var submitNodes=[];
		for(var i=0;i<nodes.length;i++){
			delete nodes[i]._id;
			delete nodes[i]._pid;
			delete nodes[i]._uid;
			submitNodes.push(nodes[i].id);
		}
		jQuery.download('${ctxPath}/oa/article/proArticle/exportZip.do',{submitNodes:submitNodes.join(","),firstNodeId:node.id},'post'); 
	}
	function importZip(){
		var node = tree.getSelectedNode();
		_OpenWindow({
			title:'上传Zip文件',
			height:200,
			width:500,
			url:'${ctxPath}/oa/article/proArticle/uploadZip.do?articleId='+node.id+'&itemId='+node.belongProId,
			ondestroy:function(action){
				if(action=='ok'){
					mini.showTips({
			            content: "<b>成功</b> <br/>数据迁移成功",
			            state: 'success',
			            x: 'center',
			            y: 'center',
			            timeout: 3000
			        });
					tree.reload();
				}
			}
		});
	}
	
	</script>
</body>
</html>