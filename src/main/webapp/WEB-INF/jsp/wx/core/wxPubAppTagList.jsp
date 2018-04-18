<%-- 
    Document   : [微信用户标签]列表页
    Created on : 2017-06-29 17:55:31
    Author     : ray
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
<title>[微信用户标签]列表管理</title>
<%@include file="/commons/list.jsp"%>
</head>
<body>
	 <div class="mini-toolbar" >
         <table style="width:100%;">
             <tr>
                 <td style="width:100%;">
                     <a class="mini-button" iconCls="icon-create" plain="true" onclick="addTag()">增加</a>
                     <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeTags()">删除</a>
                     <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchTag()">查询</a>
                     <a class="mini-button" iconCls="icon-cancel" plain="true" onclick="clearForm()">清空查询</a>
                 </td>
             </tr>
              <tr>
                  <td style="white-space:nowrap;padding:5px;" class="search-form" >
                     <ul>
						<li><span>标签名:</span><input class="mini-textbox" name="name" id="name"></li>
					</ul>
                  </td>
              </tr>
         </table>
     </div>
	
	<div class="mini-fit" style="height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" allowResize="false"
			url="${ctxPath}/wx/core/wxPubApp/getPubTag.do?pubId=${param.pubId}" idField="id"
			multiSelect="true" showColumnsMenu="true" sizeList="[100]" pageSize="100" allowAlternating="true" pagerButtons="#pagerButtons">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">相关操作</div>
				<div field="name"   width="120" headerAlign="center" >标签名</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid=mini.get("datagrid1");
		grid.load();
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var id = record.id;
			var s ='<span class="icon-edit" title="编辑" onclick="editTag(\'' + id + '\',true)"></span>'
					+'<span class="icon-remove" title="删除" onclick="removeTag(\'' + id + '\')"></span>';
			return s;
		}
		
		function searchTag(){
			var data={};
		    data.name=mini.get("name").getValue();
		    data.filter="YES";
			grid.load(data);
		}
		
		function addTag(){
			_OpenWindow({
				url : __rootPath+"/wx/core/wxTagUser/edit.do?pubId=${param.pubId}",
				title : "新增tag",
				width : 500,
				height : 200,
				max:false,
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
		
		function editTag(id){
			_OpenWindow({
				url : __rootPath+"/wx/core/wxTagUser/edit.do?pubId=${param.pubId}&id="+id,
				title : "编辑tag",
				width : 500,
				height : 200,
				max:false,
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
		
		//删除多条记录
	    function removeTags() {
	        var rows = grid.getSelecteds();
	        if (rows.length <= 0) {
	        	alert("请选中一条记录");
	        	return;
	        }
	        
	        if (!confirm("确定删除选中记录？")) return;
	        
	        var ids = [];
	        for (var i = 0, l = rows.length; i < l; i++) {
	            var r = rows[i];
	            ids.push(r.id);
	        }

	        _SubmitJson({
	        	url:__rootPath+"/wx/core/wxPubApp/deleteTags.do",
	        	type:'POST',
	        	data:{ids: ids.join(','),pubId:'${param.pubId}'},
	        	 success: function(text) {
	        		 if(text.success){
	        			 grid.load();
	        		 }else{alert("删除失败");}
	            }
	        });
	       
	    }
		
		function removeTag(id){
			 $.ajax({
		        	url:__rootPath+"/wx/core/wxPubApp/deleteTags.do",
		        	type:'POST',
		        	data:{ids: id,pubId:'${param.pubId}'},
		        	 success: function(text) {
		        		 if(text.success){
		        			 grid.load();
		        		 }else{alert("删除失败");}
		                
		            }
		        });
		}
		
	</script>
	<redxun:gridScript gridId="datagrid1" entityName="com.redxun.wx.core.entity.WxTagUser" winHeight="450"
		winWidth="700" entityTitle="微信用户标签" baseUrl="wx/core/wxTagUser" />
</body>
</html>