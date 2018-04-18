<%-- 
    Document   : 产品分类定义编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/formFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品分类定义编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/commons/dynamic.jspf"%>
<script src="${ctxPath}/scripts/common/form.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/form.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/list.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="mini-toolbar" style="padding:2px;" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;">
	             	<a class="mini-button" iconCls="icon-save" plain="true" onclick="onSave">保存</a>
	             	<span class="separator"></span>
	                <a class="mini-button" iconCls="icon-close" plain="true" onclick="onClose">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="prodDefId" class="mini-hidden"
					value="${oaProductDef.prodDefId}" />
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption>产品分类定义基本信息</caption>

					<tr>
						<th>所属分类 ：</th>
						<td><input id="treeId" name="treeId" class="mini-treeselect" url="${ctxPath}/sys/core/sysTree/listByCatKey.do?catKey=CAT_ASSETS_SORT" 
						multiSelect="false" textField="name" valueField="treeId" parentField="parentId"  required="true" value="${oaProductDef.treeId}"
						showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onClearTree" popupWidth="300" style="width:90%"/>

						</td>
						<th>名称 *：</th>
						<td><input name="name" value="${oaProductDef.name}" required="true"
							class="mini-textbox" vtype="maxLength:64" style="width: 90%" />

						</td>
					</tr>

					<tr>
						<th>描述 ：</th>
						<td colspan="3"><textarea name="desc" class="mini-textarea" vtype="maxLength:512" style="width: 90%">${oaProductDef.desc}</textarea></td>
					</tr>
				</table>
			</div>
		</form>
	<div class="mini-toolbar" style="padding:2px;">
		    <table style="width:100%;">
		        <tr>
		            <td style="width:100%;">
		             	<a class="mini-button" iconCls="icon-add" plain="true" onclick="onTreeKeyList">添加产品类型</a>
		                <span class="separator"></span>
			            <a class="mini-button" iconCls="icon-up" plain="true" onclick="upRow">向上</a>
			            <a class="mini-button" iconCls="icon-down" plain="true" onclick="downRow">向下</a>
		            </td>
		        </tr>
		    </table>
		</div>
					<!-- 字段属性配置 -->
		 <div class="mini-fit" style="border:0;padding:5px;">
			<div id="datagrid1" class="mini-datagrid"style="width: 100%;"
			height="auto" showPager="false" allowCellEdit="true" oncellbeginedit="costValueChanged" 
			allowCellSelect="true">
				<div property="columns">
					
					<div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
					<div field="name" width="80" headerAlign="center">产品类型
						<input property="editor" class="mini-textbox" required="true" value="25" style="width:100%;"/>
					</div>
					<div field="number" displayField="numbername" width="200" headerAlign="center" >产品属性
						<input property="editor" class="mini-combobox"  style="width:100%;" valueField="valueId" textField="name"  multiSelect="true" />
					</div>
				</div>
			</div>
		</div>
<script type="text/javascript">
		mini.parse();
		var grid=mini.get('datagrid1');
		/* grid.load(); */
		var form=new mini.Form('form1');
		
		//编辑
		function onActionRenderer(e) {
		    var record = e.record;
		    var uid = record._uid;
		    var s =  ' <span class="icon-remove" title="删除" onclick="delRow1(\'' + uid + '\')"></span>';
		    return s;
		}
		
		//上移一行
		function upRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index - 1);
            }
        }
		//下移一行
        function downRow() {
            var row = grid.getSelected();
            if (row) {
                var index = grid.indexOf(row);
                grid.moveRow(row, index + 2);
            }
        }
		
		//删除
		function delRow1(uid){
			var rs=grid.getRowByUID(uid);
			if(rs.number!=undefined && rs.number!=''){
				_SubmitJson({
					url:__rootPath+'/oa/product/oaProductDef/delValueId.do',
					data:{
						ids:rs.number
					},
					method:'POST'
				});
			}
			grid.removeRow(rs);
		}
		
		//保存
		function onSave(){
			form.validate();
			if(!form.isValid()){
				return;
			}
			var formData=$("#form1").serializeArray();
	        //加上租用Grid的属性
	        var grid1=mini.get('datagrid1');
	    	var data = grid1.getData();
	    	for(var i=0;i<data.length;i++){
				if(data[i].keyId!=null&&data[i].number==null){
					alert("请选择相对应的类型产品属性！");
					return;
				}
			}
            var json = mini.encode(data);
            
            formData.push({
            	name:'atts',
            	value:json
            });
	       
	        _SubmitJson({
	        	url:"${ctxPath}/oa/product/oaProductDef/save.do",
	        	method:'POST',
	        	data:formData,
	        	success:function(result){
	        		CloseWindow('ok');
	        	}
	        });
		}
		
		$(function(){
			var pkId="${param['pkId']}";
			
			_SubmitJson({
				
				url:__rootPath+"/oa/product/oaProductDef/getValueKeyIds.do",
				data:{
					pkId:pkId
				},
				method:'POST',
				success:function(result){
					var data=result.data;
					var grid=mini.get("datagrid1");
					 grid.setData(data);
				}
			});
		});

		
        function costValueChanged(e) {
            
            var record = e.record;
            var field = e.field, value = e.value;
            var editor = e.editor;
            if (field == "number") {
                var id = record.keyId;
                if (id) {
                    var url = "${ctxPath}/oa/product/oaProductDef/getByIsKeyId.do?keyId=" + id
                    editor.setUrl(url);
                } else {
                    e.cancel = true;
                }

            }
            
        }
        
		
		//关闭
		function onClose(){
			CloseWindow('close');
		}
		
		function onClearTree(e) {
	            var obj = e.sender;
	            obj.setText("");
	            obj.setValue("");
	    }

		
		function onTreeKeyList(){
			 _OpenWindow({
					url : "${ctxPath}" + "/oa/product/oaTreeKeyList.do?",
					title : "类型选择列表",
					iconCls:'icon-transmit',
					height : 550,
					width : 800,
	                onload: function () {
	                    var iframe = this.getIFrameEl();
	                },
	                ondestroy: function (action) {                    
	                    if (action == "ok") {
	                        var iframe = this.getIFrameEl();
	                        var grid=mini.get('datagrid1');
	                        var oldData=grid.getData();
	                        var newData = iframe.contentWindow.GetData();
	                        newData = mini.clone(newData);
	                        var showData=[];
	                        
	                        for(var i=0;i<oldData.length;i++)
	                        	showData.push(oldData[i]);
	                        
	                        for(var i=0;i<newData.length;i++){
	                        		var newFlag=true;
	                        		for(var j=0;j<oldData.length;j++){
	                        			if(newData[i].keyId==showData[j].keyId){
	                        				newFlag=false;
	                        				break;
	                        			}
	                        		}
	                        		if(newFlag){
	                        			showData.push(newData[i]);
	                        		}
	                        		
	                        	}

		                        grid.setData(showData);

	                    }
	                }
				}); 
		}
		
		</script>

		
		
</body>
</html>