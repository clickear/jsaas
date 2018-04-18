
function CustomQuery(formId){
	{
		/**
		 * 表单ID
		 */
		this.form=new mini.Form("#"+formId);   
		
		this.formId=formId;
	};
	
	/**
	 * 解析表单公式。
	 */
	this.parseQuery=function(){
		/**
		 * 解析主表
		 */
		this.parseMain();
		/**
		 * 解析子表。
		 */
		this.parseGrid();
	},
	
	
	/**
	 * 解析主表的公式。
	 */
	this.parseMain=function(){
		var self=this;
		var controls=mini.findControls(function(control){
			
			return self.isAllowController(control.type);
		});
		for(var i=0;i<controls.length;i++){
			var ctl=controls[i];
			if(!ctl.customquery) continue;
			this.handMainQuery(ctl);
		}
	},
	/**
	 * 
	 */
	this.handMainQuery=function(ctl){
		var aryQuery=ctl.customquery;
		for(var i=0;i<aryQuery.length;i++){
			var query=aryQuery[i];
			this.handFieldQuery(ctl,query);
		}
	},
	this.handFieldQuery=function(ctl,query){
		var self=this;
		var events=query.event;
		var aryEvent=events.split(",");
		var el=ctl.el;
		var parentEl=$(el).closest(".form-model");
		var id=$("[name='ID_']",parentEl).val();
		
		for(var i=0;i<aryEvent.length;i++){
			var event=aryEvent[i];
			if((event=="addinit" && !id) || (event=="editinit" && id) || (event=="detailinit" && id)){
				this.executeQuery(query);
			}
			else{
				ctl.on(event,function(e){
					self.executeQuery(query);
				})
			}
		}
	},
	/**
	 * 执行自定义查询。
	 */
	this.executeQuery=function(query){
		var customQuery=query.customquery;
		var gridInput=query.gridInput;
		var gridReturn=query.gridReturn;
		//构建输入参数
		var params={};
		var table=query.table;
		for(var i=0;i<gridInput.length;i++){
			var input=gridInput[i];
			var mode=input.mode;
			var name=input.name;
			var bindVal=input.bindVal;
			if(mode=="mapping"){
				var val=mini.getByName(bindVal).getValue();
				params[name]=val;
			}
			else{
				
				var func = new Function(bindVal);
				var val=func();
				params[name]=val;
			}
		}
		var self=this;
		//执行自定义查询
		doQuery(customQuery, params,function(data){
			var aryData=data.data;
			if(table=="main"){
				var obj={};
				if(aryData.length>0){
					obj=aryData[0];
				};
				var result=self.getResultRow(obj,gridReturn);
				var data=self.form.getData();
				$.extend(data, result);
				self.form.setData(data);
			}
			else{
				if(aryData.length==0) return;
				var rows=[];
				for(var i=0;i<aryData.length;i++){
					var rData=aryData[i];
					var row=self.getResultRow(rData,gridReturn);
					rows.push(row);
				}
				var grid=mini.get("grid_" + table);
				grid.setData(rows);
			}
		})
	},
	this.getResultRow=function(row,gridReturn){
		var result={};
		for(var i=0;i<gridReturn.length;i++){
			var o=gridReturn[i];
			var val=row[o.name];
			if(typeof val!="number" && !val ){
				val="";
			}
			result[o.mapVal]=val;
		}
		return result;
	}
	
	this.isAllowController=function(type){
		var ary=['textbox','buttonedit','groupcontrol','combobox','depcontrol','groupcontrol','usercontrol','rxcombobox'];
		for(var i=0;i<ary.length;i++){
			if(type==ary[i]) return true;
		}
		return false;
	},

	this.parseGrid=function(){
		var formulaObj={};
		var grids=mini.findControls(function(control){
			   if(control.type=="datagrid") return true;
		});
		
		for(var i=0;i<grids.length;i++){
			var grid=grids[i];
			var customQuerys=[];
			var cols=grid.columns;
			for(var j=0;j<cols.length;j++){
				var col= grid.getColumn(j);
				if(!col.customquery) continue;
				for(var m=0;m<col.customquery.length;m++){
					customQuerys.push(col.customquery[m]);
				}
			}
			if(customQuerys.length>0){
				grid.customQuerys=customQuerys;
			}
		}
		
		for(var i=0;i<grids.length;i++){
			var grid=grids[i];
			this.executeGridQuerys(grid);
		}
	},
	
	this.executeGridQuerys=function(grid){
		if(!grid.customQuerys) return;
		var self=this;
		grid.on("cellendedit",function(e){
			var grid=e.sender;
			var querys=grid.customQuerys;
			for(var i=0;i<querys.length;i++){
				var query=querys[i];
				self.executeGridQuery(grid,e,query);
			}
		});
	},
	
	this.executeGridQuery=function(grid,event,query){
		var row=event.record;
		var customQuery=query.customquery;
		var gridInput=query.gridInput;
		var gridReturn=query.gridReturn;
		//构建输入参数
		var params={};
		var table=query.table;
		for(var i=0;i<gridInput.length;i++){
			var input=gridInput[i];
			var mode=input.mode;
			var name=input.name;
			var bindVal=input.bindVal;
			if(mode=="mapping"){
				var val=row[bindVal];
				params[name]=val;
			}
			else{
				var func = new Function(bindVal);
				var val=func();
				params[name]=val;
			}
		}
		var self=this;
		//执行自定义查询
		doQuery(customQuery, params,function(data){
			var aryData=data.data;
			var result=self.getResultRow(aryData[0],gridReturn);
			grid.updateRow(row,result);
		})
	}
	
	
	
	
}