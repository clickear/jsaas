/**
 * 关闭明细弹窗
 * @param detailId
 */
function closeFormDetail(detailId){
	 var win = mini.get("editWindow_"+detailId);
     win.hide();
}
/**
 * 保存明细至Grid
 */
function saveFormDetail(detailId){
	 var form=new mini.Form('editForm_'+detailId);
	 var win = mini.get("editWindow_"+detailId);
	 form.validate();
	 if(!form.isValid()){
		 return;
	 }
	 
	 var grid=mini.get("grid_" +detailId);
	 var type=grid.type;
	 
	 var uid=form.getData()._uid;
	 if(uid){
		 var row=grid.getRowByUID(uid);
		 if(row!=null){
			 grid.updateRow(row,form.getData());
		 }
	 }else{
		 var row=form.getData();
		 if(type=="treegrid"){
			 var node = grid.getSelectedNode();
			 var action=win.isSub?"add":"after";
			 grid.addNode(row, action, node);
		 }
		 else{
			 grid.addRow(row);
		 }
		 
	 }
     win.hide();
}


function _addGridRow(gridId,edittype,isSub){
	var grid=mini.get('grid_'+gridId);
	var isTree=grid.type=="treegrid";
	var rowData=_jsonInit['grid_'+gridId] || {};
	var row=$.clone(rowData);
	
	if(window._addRow){
		_addRow(gridId,row);
	}
	
	if(edittype=='openwindow'){
		 var win = mini.get("editWindow_"+gridId);
		 var form=new mini.Form('editForm_'+gridId);
		 form.setData(row);
		 var fields = form.getFields();
         for (var i = 0, l = fields.length; i < l; i++) {
             var c = fields[i];
             if (c.setReadOnly) c.setReadOnly(false);
             if (c.removeCls) c.removeCls("asLabel");
         }
         mini.repaint(document.body);
         win.isSub=isSub;
		 win.show();
	}else if(edittype=='editform'){
		var container=$("#gct_"+gridId);
		var fheight=container.attr('fheight');
		var fwidth=container.attr('fwidth');
		var formKey=container.attr('formkey');
		var formname=container.attr('formname');
		if(!fheight || fheight==0 || fheight==''){
			fheight=350;
		}
		if(!fwidth || fwidth==0 || fwidth==''){
			fwidth=800;
		}
		_OpenWindow({
			title:'添加记录-'+formname,
			url:__rootPath+'/bpm/form/bpmFormView/form/'+formKey+'.do',
			max:true,
			height:fheight,
			width:fwidth,
			onload:function(){
			},
			ondestroy:function(action){
				if(action!='ok'){
					return;
				}
				var win=this.getIFrameEl().contentWindow;
				var grid=mini.get('grid_'+gridId);
				if(win.getData){
					var data=win.getData();
					if(!isTree){
						grid.addRow(data);
					}
					else{
						var node = grid.getSelectedNode();
						var action=isSub?"add":"after";
						grid.addNode(row, action, node);
					}
				}
			}
		});
	}else{
		
		if(!isTree){
			grid.addRow(row);
		}
		else{
			var node = grid.getSelectedNode();
			var action=isSub?"add":"after";
			grid.addNode(row, action, node);
			
		}
		
	}
}

//编辑表单
function _editGridRow(gridId,editType){
	var grid=mini.get('grid_'+gridId);
	
	var row = grid.getSelected();
	if(!row){
		alert('请选择行!');
		return;
	}
	
	var dealwith=grid.editExist;
	
	if(dealwith!="true"  && row.ID_){
		alert('选择数据不允许编辑!');
		return;
	}
	
	
	if(editType=='editform'){
		var container=$("#gct_"+gridId);
		var fheight=container.attr('fheight');
		var fwidth=container.attr('fwidth');
		var formname=container.attr('formname');
		var formKey=container.attr('formkey');
		var grid=mini.get('grid_'+gridId);
		if(!fheight || fheight==0 || fheight==''){
			fheight=350;
		}
		if(!fwidth || fwidth==0 || fwidth==''){
			fwidth=800;
		}
		_OpenWindow({
			title:'添加记录-'+formname,
			url:__rootPath+'/bpm/form/bpmFormView/form/'+formKey+'.do',
			max:true,
			height:fheight,
			width:fwidth,
			onload:function(){
				var win=this.getIFrameEl().contentWindow;
				if(win.setData){
					var selectRow=grid.getSelected();
					win.setData(selectRow);
				}
			},
			ondestroy:function(action){
				if(action!='ok'){
					return;
				}
				var win=this.getIFrameEl().contentWindow;
				if(win.getData){
					var data=win.getData();
					var selectRow=grid.getSelected();
					grid.updateRow(selectRow,data);
				}
			}
		});
	}else{
		 var form=new mini.Form('editForm_'+gridId);
		 form.setData(row);
		 var fields = form.getFields();
         for (var i = 0, l = fields.length; i < l; i++) {
             var c = fields[i];
             if (c.setReadOnly) c.setReadOnly(false);
             if (c.removeCls) c.removeCls("asLabel");
         }
         mini.repaint(document.body);
		 var win = mini.get("editWindow_"+gridId);
		 win.show();
	}
}

function _detailGridRow(gridId,editType){
	var grid=mini.get('grid_'+gridId);
	var row = grid.getSelected();
	if(!row){
		alert('请选择行!');
		return;
	}
	if(editType=='editform'){
		var container=$("#gct_"+gridId);
		var fheight=container.attr('fheight');
		var fwidth=container.attr('fwidth');
		var formname=container.attr('formname');
		var formKey=container.attr('formkey');
		var grid=mini.get('grid_'+gridId);
		if(!fheight || fheight==0 || fheight==''){
			fheight=350;
		}
		if(!fwidth || fwidth==0 || fwidth==''){
			fwidth=800;
		}
		_OpenWindow({
			title:'记录明细-'+formname,
			url:__rootPath+'/bpm/form/bpmFormView/form/'+formKey+'.do',
			max:true,
			height:fheight,
			width:fwidth,
			onload:function(){
				var win=this.getIFrameEl().contentWindow;
				if(win.setData){
					var selectRow=grid.getSelected();
					win.setData(selectRow);
				}
				if(win.setFieldsLabelMode){
					win.setFieldsLabelMode();
				}
			}
		});
	}else{
		 var form=new mini.Form('editForm_'+gridId);
		 form.setData(row);
		 //setReadOnly
		 var win = mini.get("editWindow_"+gridId);
		 var fields = form.getFields();                
         for (var i = 0, l = fields.length; i < l; i++) {
             var c = fields[i];
             if (c.setReadOnly) c.setReadOnly(true);     //只读
             if (c.setIsValid) c.setIsValid(true);      //去除错误提示
             if (c.addCls) c.addCls("asLabel");          //增加asLabel外观
         }
		 win.show();
	}
}

//删除
function _delGridRow(gridId){
	var grid=mini.get('grid_'+gridId);
	var type=grid.type;
	
	var allowDel=grid.delExist;
	
	
	var selecteds=grid.getSelecteds();
	if(allowDel=="true"){
		grid.removeRows(selecteds);
	}
	else{
		var calDels=[];
		for(var i=0;i<selecteds.length;i++){
			var o=selecteds[i];
			//表示ID存在
			if(o.ID_) continue;
			calDels.push(o);
		}
		grid.removeRows(calDels);
	}
	
	if(formulaCalc_){
		formulaCalc_.handGridChange('grid_' +gridId)
	}
	
	/**
	 * 删除行
	 */
	if(window._removeRows){
		window._removeRows(gridId);
	}
}


//上移
function _upGridRow(gridId){
	moveRow(gridId,true);
} 
//下移
function _downGridRow(gridId){
	moveRow(gridId,false);
}

function moveRow(gridId,isUp){
	var grid=mini.get(gridId);
	var type=grid.type;
	if(type!="treegrid"){
		moveGridRow(grid,isUp);
	}
	else{
		moveTreeRow(grid,isUp);
	}
}

function moveGridRow(grid,isUp){
	var row = grid.getSelected();
    if (row) {
        var index = grid.indexOf(row);
        grid.moveRow(row, index + 2);
    }
}

/**
 * 移动treegrid 数据。
 * @param grid
 * @param isUp
 * @returns
 */
function moveTreeRow(grid,isUp){
	var curNode=grid.getSelectedNode();
	if(!curNode)  return;

	var node=getBrotherNode(grid,isUp);
	if(!node){
		var msg=isUp?"已经到了最前节点!":"没有后续的节点!"
		alert(msg);
		return;
	}
	var action=isUp?"before":"after";
	grid.moveNode(curNode, node, action)
}


/**
 * 移动treegrid 数据。
 * @param grid
 * @param isUp
 * @returns
 */
function moveTreeRow(grid,isUp){
	var curNode=grid.getSelectedNode();
	if(!curNode)  return;

	var node=getBrotherNode(grid,isUp);
	if(!node){
		var msg=isUp?"已经到了最前节点!":"没有后续的节点!"
		alert(msg);
		return;
	}
	var action=isUp?"before":"after";
	grid.moveNode(curNode, node, action)
}

/**
 * 获取兄弟节点。
 * @param grid
 * @param isPre
 * @returns
 */
function getBrotherNode(grid,isPre){
	var node=grid.getSelectedNode();
	var parentNode=grid.getParentNode(node);
	var nodes=(!parentNode)?grid.getRootNode():grid.getChildNodes(parentNode);
	for(var i=0;i<nodes.length;i++){
		var tmp=nodes[i];
		if(tmp!=node) continue;
		if(isPre){
			if(i==0) return null;
			return nodes[i-1];
		}
		else{
			if(i==nodes.length-1) return null;
			return nodes[i+1];
		}
	}
	return null;
}


function onCellValidation(e){
	
}