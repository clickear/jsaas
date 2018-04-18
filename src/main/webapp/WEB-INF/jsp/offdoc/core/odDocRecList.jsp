<%-- 
    Document   : [OdDocRec]列表页
    Created on : 2016-3-8, 16:11:48
    Author     : 陈茂昌
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>收文列表管理</title>
   <%@include file="/commons/list.jsp"%>
</head>
<body>
	<redxun:toolbar entityName="com.redxun.offdoc.core.entity.OdDocRec"  excludeButtons="popupAddMenu,popupAttachMenu,popupSearchMenu,detail,edit,remove,saveCurGridView,saveAsNewGridView" />
	<div class="mini-fit rx-grid-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/offdoc/core/odDocRec/recDoc.do?type=${param.type}" idField="recId" multiSelect="true" showColumnsMenu="true" ondrawcell="onDrawCell" onupdate="_LoadUserInfo();_LoadGroupInfo();"
			sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" >
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer"
					cellStyle="padding:0;">操作</div>
				<div field="recDepId" width="120" headerAlign="center" allowSort="true">收文部门</div>
				<div field="recDtype" width="120" headerAlign="center" allowSort="true">收文单位类型</div>
				<div field="isRead" width="120" headerAlign="center" allowSort="true" renderer="onIsReadRenderer">是否阅读</div>
				<div field="readTime" width="120" headerAlign="center" allowSort="true" renderer="onRenderer">最新阅读时间</div>
				<div field="signUsrId" width="120" headerAlign="center" allowSort="true">签收人</div>
				<div field="signStatus" width="120" headerAlign="center" allowSort="true" renderer="onSignStatusRenderer">签收状态</div>
				<div field="signTime" width="120" headerAlign="center" allowSort="true" renderer="onRenderer">签收时间</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        	//行功能按钮
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var docId=record.docId;
	            var recId=record.recId;
	            var isRead=record.isRead;
	            var s ="";
	                   if("NOTSIGN"==record.signStatus){
	       	            s+=' <span class="icon-to-receive" title="收文" onclick="dispatchRow(\'' + docId+'\',\''+pkId+'\',\''+recId+ '\');toRead(\'' + pkId + '\');"></span>';
	       	            }
	                    s+= ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	            return s;
	        }
        	
	        function dispatchRow(docId,pkId,recId){
	        	var read="YES";
				_OpenWindow({
		    		 url: __rootPath + "/offdoc/core/odDocument/incomingEdit.do?pkId="+docId+"&isRead="+read+"&recId="+recId,
		            title: "收文",
		            width: 760, height: 800,
		            ondestroy: function(action) {
		                if (action == 'ok') {
		                	mini.showTips({
		    		            content: "<b>成功</b> <br/>已收文",
		    		            state: 'warning',
		    		            x: 'center',
		    		            y: 'center',
		    		            timeout: 3000
		    		        }); 
		                    mini.get("datagrid1").reload();
		                }
		            }
		    	});
		}
	        
	        
	        function toRead(pkId){
	        	var read="YES";
	        	$.ajax({
	                type: "Post",
	                url : '${ctxPath}/offdoc/core/odDocRec/haveRead.do?pkId='+pkId+"&isRead="+read,
	                data: {},
	                beforeSend: function () {
	                    
	                },
	                success: function () {
	                	 mini.get("datagrid1").reload();
	                }
	            }); 
	        }
	        
	      //绘制人名等等
			function onDrawCell(e) {
		        var tree = e.sender;
		        var record=e.record;
		        var field=e.field;
		        	if(field=='signUsrId'){//给reporId那列显示成名字
		        	e.cellHtml='<a class="mini-user"  userId="'+record.signUsrId+'"></a>';
		        	}
		        	if(field=='recDepId'){
		        	e.cellHtml='<a class="mini-group"  groupId="'+record.recDepId+'"></a>';
		        	}
		        	
		        	if(field=="recDtype"){
		        		if("INCOMING"==record.recDtype){
		        			e.cellHtml='收文部门';
		        		}else if("COPYTO"==record.recDtype){
		        			e.cellHtml='抄送部门';
		        		}
		        	}
		    } 
	            
			  function onIsReadRenderer(e) {
	  	            var record = e.record;
	  	            var isRead = record.isRead;
	  	             var arr = [{'key' : 'YES', 'value' : '是','css' : 'green'}, 
	  	    			        {'key' : 'NO','value' : '否','css' : 'red'} ];
	  	    			return $.formatItemValue(arr,isRead);
	  	        }
	         
			 function onSignStatusRenderer(e) {
		            var record = e.record;
		            var signStatus = record.signStatus;
		             var arr = [{'key' : 'NOTSIGN', 'value' : '未签收','css' : 'red'}, 
		    			        {'key' : 'SIGNING','value' : '正在签收','css' : 'orange'},
		    			        {'key' : 'SIGNED','value' : '已签收','css' : 'green'}];
		    			return $.formatItemValue(arr,signStatus);
		        }
	      
			//渲染时间
			 function onRenderer(e) {
		            var value = e.value;
		            if (value) return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
		            return "暂无";
		        }
        </script>
	<script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.offdoc.core.entity.OdDocRec" winHeight="450" winWidth="700"
		entityTitle="接收公文" baseUrl="offdoc/core/odDocRec" />
</body>
</html>