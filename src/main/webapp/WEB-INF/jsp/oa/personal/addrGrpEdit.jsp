<%-- 
    Document   : 联系人分组编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>联系人分组编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>

	<div id="toolbar1" class="mini-toolbar topBtn" style="padding: 2px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;">
					<a class="mini-button" iconCls="icon-save" plain="true" onclick="save">保存</a> 
					<a id="del" class="mini-button" iconCls="icon-remove" plain="true" onclick="delGrp">删除此分组</a>
				</td>
			</tr>
		</table>
	</div>

	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="groupId" class="mini-hidden" value="${addrGrp.groupId}" />
				<div style="padding: 6px 0;">

					分组名字 ： <input name="name" value="${addrGrp.name}" class="mini-textbox" vtype="maxLength:50" style="width: 50%" required="true" emptyText="请输入分组名字" />

				</div>
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<tr>
						<td style="height: 250px">
							<div id="leftInfo" class="mini-fit">
								<ul id="tree2" class="mini-tree" url="${ctxPath}/oa/personal/addrGrp/getAllGrpAndBook.do" style="width: 200px; padding: 5px;" showTreeIcon="false" textField="name" idField="gabId" parentField="parentId" resultAsTree="false" showCheckBox="true" checkRecursive="true" allowSelect="false" enableHotTrack="false" showTreeLines="true">
								</ul>
							</div>
						</td>

						<td style="width: 80px; text-align: center;"><input type="button" value=">" onclick="add()" style="width: 40px;"/></td>
						<td>
							<div class="mini-fit">
								<a class="mini-button" iconCls="icon-remove" plain="true" onclick="removes">移除联系人</a>
								<div id="listbox2" class="mini-listbox" style="width: 250px; height: 200px;" showCheckBox="true" multiSelect="true" url="${ctxPath}/oa/personal/addrBook/getAddrBookByGroupId.do?groupId=${param['pkId']}">
									<div property="columns">
										<div header="已有联系人" field="name"></div>
									</div>
								</div>
							</div>
						</td>
					</tr>


				</table>
			</div>

		</form>

	</div>
	<rx:formScript formId="form1" baseUrl="oa/personal/addrGrp" />
	<script type="text/javascript">
	addBody();
    mini.parse();
    var tree = mini.get("tree2");
    var listbox2 = mini.get("listbox2");
    var btnDelGrp=mini.get("del");       //删除分组按钮
    var id='${addrGrp.groupId}';

    /*判断是否屏蔽分组按钮*/
     $(function(){                    
    	if(id=='')
    		btnDelGrp.setVisible(false);
    	else
    		btnDelGrp.setVisible(true);
    }); 
    
    /*将左边的树的联系人添加到右边的listbox*/
    function add() {               
        var items = tree.getCheckedNodes(false);     //获取选中节点且不添加父节点到listbox
        for(var i=0;i<items.length;i++){
        	var flag=false;
        	if(items[i].parentId==null)       //如果ID为0，则为父节点 则不添加
        		continue;
        	var listBoxData=listbox2.getData();
        	if(listBoxData.length<=0)
        		listbox2.addItem(items[i]);
        	else{
	        	for(var j=0;j<listBoxData.length;j++){
	        		if(typeof(listBoxData[j].addrId)!="undefined"){
	        			if(listBoxData[j].addrId!=items[i].gabId)
		        			continue;
		        		else{
		        			flag=true;
		        			break;
		        		}
	        		}else{
		        		if(listBoxData[j].gabId!=items[i].gabId)
		        			continue;
		        		else{
		        			flag=true;
		        			break;
		        		}
	        		}
	        	}
	        	if(!flag){
	        		listbox2.addItem(items[i]);     //listbox添加元素
	        	}
        	}
        }
    }
    
    /*删除当前分组*/
    function delGrp(){                   
        if (confirm("确定当前分组？")) {
            _SubmitJson({
            	url:__rootPath+"/oa/personal/addrGrp/del.do",
            	data:{
            		ids:id
            	},
            	method:'POST',
            	success:function(){
            		CloseWindow('ok'); 
            	}
            });
            top['grp'].refreshMenu();         //刷新联系人分组
        }
    }
    
    /*关闭窗口*/
    function exit(){   
    	CloseWindow('canel'); 
    }

    /*保存listbox里面的联系人*/
    function save(){                      
        form.validate();
        if (!form.isValid()) {
            return;
        }
    	var formJson=_GetFormJson("form1");
    	var listBoxData=listbox2.getData();
      	_SubmitJson({
    		url:__rootPath+"/oa/personal/addrGrp/saveGrp.do",
    		data:{
    			formData:mini.encode(formJson),
    			listData:mini.encode(listBoxData)
    		},
    		method:'POST',
    		success:function(){
    			CloseWindow('ok'); 
    		}
    	});  
    }
    
    /*移除listbox内所有联系人*/
    function removes() {                  
        var items = listbox2.getSelecteds();
        listbox2.removeItems(items);
    }
    


	</script>

</body>
</html>