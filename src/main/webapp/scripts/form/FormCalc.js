MathUtil={};

MathUtil.toNumber = function(x){
	if(x === null || x === undefined ||  x === '')
		return '';
	if(typeof x == "string"){
		x = x.replace(/,/g, "");
	}
	var match = x.toString().match(/([$|￥])\d+\.?\d*/);
	if(match){
		x = x.replace(match[1],'');
	}
	return Number(x);
};

MathUtil.max = function(ary){
	if(!ary || ary.length==0) return 0;
	var tmp,
		x = 0,
		size = ary.length;
	for(var i=0;i<size;i++){
		x = MathUtil.toNumber(ary[i]);
		if(isNaN(x))continue;
		if(tmp===undefined){
			tmp = x;
		}
		else{
			if(x>tmp)
				tmp = x;	
		}
	}
	tmp = MathUtil.toNumber(tmp);
	return tmp;
}

MathUtil.min = function(ary){
	if(!ary || ary.length==0) return 0;
	var tmp,
		x = 0,
		size = ary.length;
	for(var i=0;i<size;i++){
		x = MathUtil.toNumber(ary[i]);
		if(isNaN(x))continue;
		if(tmp===undefined){
			tmp = x;
		}
		else{
			if(x<tmp)
				tmp = x;	
		}
	}
	tmp = MathUtil.toNumber(tmp);
	return tmp;
}

MathUtil.avg = function(ary){
	if(!ary || ary.length==0) return 0;
	var tmp,
		x = 0,
		size = ary.length;
	for(var i=0;i<size;i++){
		x = MathUtil.toNumber(ary[i]);
		if(isNaN(x))continue;
		if(tmp===undefined){
			tmp = x;
		}
		else{
			tmp += x;
		}
	}
	tmp = MathUtil.toNumber(tmp/size);
	return tmp;
};

MathUtil.sum = function(ary){
	if(!ary || ary.length==0) return 0;
	var tmp,
		x = 0,
		size = ary.length;
	for(var i=0;i<size;i++){
		x = MathUtil.toNumber(ary[i]);
		if(isNaN(x))continue;
		if(tmp===undefined){
			tmp = x;
		}
		else{
			tmp += x;
		}
	}
	tmp = MathUtil.toNumber(tmp);
	return tmp;
};

/**
 * 四舍五入计算。
 * num 需要处理的数
 * fixed: 保留的小数位
 */
MathUtil.roundFixed=function(num,fixed){
	   var pos = num.toString().indexOf('.'),
        decimal_places = num.toString().length - pos - 1,
        _int = num * Math.pow(10, decimal_places),
        divisor_1 = Math.pow(10, decimal_places - fixed),
        divisor_2 = Math.pow(10, fixed);
	    return Math.round(_int / divisor_1) / divisor_2;
}

/**
 * 表单计算。
 * 
 * 1.计算数字
 * 2.计算日期
 * 
 * 参数:
 * formId : 表单ID
 * 
 * 使用方法：
 * var calc=new FormCalc("form1");
 * calc.parseFormula();
 * 
 * 当子表删除记录时调用方法。
 * 参数为子表名称。
 * calc.handGridChange("gridOrders")
 * 
 * 公式配置方法：
 * 
 * 子表小计：
 * <div name="subTotal" field="subTotal" width="100" headerAlign="center" 
 *	    data-options="{formula:'{amount}*{price}'}"  allowSort="true">小计
 *	</div> 
 * 公式为：
 *  {formula:'{amount}*{price}'}
 *  小计为数量乘价格。
 * 
 * 主表合计：
 * <input id="total" name="total" class="mini-textbox" 
				data-options="{formula:'{canfei} +{chailv} +{zhushu}+MathUtil.sum([{gridOrders.subTotal}])'}" 
				emptyText="请输入合计"  vtype="int"/>
 * 
 * 上面的公式代表：
 * 合计字段为：
 * canfei + chailv +zhushu + MathUtil.sum([{gridOrders.subTotal}])
 * 餐费 + 差旅 + 住宿 + 子表小计合计
 */
function FormCalc(formId){
	{
		/**
		 * 数据格式如下:
		 * {
		 * 		子表1:
		 * 		{目标字段1:
		 * 			{formula:公式,fields:[字段1,字段2]},
		 * 	 	 目标字段2:
		 * 			{formula:公式,fields:[字段1,字段2]}
		 * 		}
		 * }
		 */
		this.gridFormula={};
		
		/**
		 * 全局子表数据统计公式
		 * {
		 * 		子表1:
		 * 		{
		 * 			主表目标字段1:{formula:公式,fields:[字段1,字段2]}
		 * 		}
		 * }
		 */
		this.gridGlobal=[]
		
		/**
		 * 格式如下:
		 * {
		 * 	目标字段1:{fields:[field1,field2],formula:"公式1"} 
		 * }
		 */
		this.globalFormula={}
		
		/**
		 * 表单ID
		 */
		this.form=new mini.Form("#"+formId);   
		
		this.formId=formId;
	};
	
	/**
	 * 解析表单公式。
	 */
	this.parseFormula=function(){
		/**
		 * 解析主表
		 */
		this.parseMain();
		/**
		 * 解析子表。
		 */
		this.parseGrid();
		/**
		 * 解析日期计算。
		 */
		this.parseDateCalc();
		/**
		 * 解析子表表单级联
		 */
		this.parseComboboxGrid();
		//中文大写
		this.parseChineseup();
		//解析表单级联
		this.parseCascadeQuery();
	},
	
	/**
	 * 解析子表表单级联
	 */
	this.parseComboboxGrid=function(){
		var grids=mini.findControls(function(control){
			   if(control.type=="datagrid" && control.name.startWith("grid_")) {
				   return true;
			   }
		});
		for(var i=0;i<grids.length;i++){
			var comboboxSetting={};
			var grid=grids[i];
			var cols=grid.columns;
			for(var k=0;k<cols.length;k++){
				var col= grid.getColumn(k);
				var setting=col.sql;
				if(!setting || !setting.parent)continue;				
				var parentName = setting.parent;
				for(var j=0;j<cols.length;j++){
					if(cols[j].name==parentName){
						comboboxSetting[col.name]=parentName;
						grid.on("cellbeginedit",this.comboBoxSubTable);
					}
				}
			}
			grid.comboboxSetting=comboboxSetting;
		}
	}, 
	
	this.comboBoxSubTable=function(e){
		 var grid = e.sender;
         var record = e.record;
         var field = e.field;
         var editor = e.editor;
         var sql = editor.sql;
         var comboboxSetting = grid.comboboxSetting;
         for(var key in comboboxSetting){
        	 if(field != key) continue;	         
        	 var parent = comboboxSetting[key];
             var val = record[parent];
             if (id) {
                 var url = __rootPath +"/sys/db/sysSqlCustomQuery/queryForJson_" + sql.sql
 				+".do?params={" +sql.param +":\"" +val+"\"}";
                 url=encodeURI(url);
                 editor.setUrl(url);
             } else {
                 e.cancel = true;
             }
	     }
	},
	
	/**
	 * 解析日期计算。
	 */
	this.parseDateCalc=function(){
		this.parseMainDate();
		this.parseDateGrid();
		//
		this.parseMainDateStart();
		//
		this.parseDateGridStart();
	},
	/**
	 * 大写转换
	 */
	this.parseChineseup=function(){
		$("div.divContainer").each(function(){
			var obj=$(this);
			if(!obj.attr("data-options")) return true;
			var json=mini.decode(obj.attr("data-options"));
			//没有中文大写
			if(!json.chineseup) return true;
			var node=json.chineseup.field;
			var miniNode=mini.getbyName(node);
			
			var val=miniNode.getValue();
			if(val){
				var transVal=toChineseMoney(val);
				obj.text(transVal);
			}
			
			miniNode.targetDiv=obj;
			miniNode.on("valuechanged",function(e){
				var sender=e.sender;
				var val=sender.getValue();
				if(!val) return ;
				var transVal=toChineseMoney(val);
				sender.targetDiv.text(transVal);
			});
		});
	},
	
	/**
	 * 计算表格日期。
	 */
	this.parseDateGrid=function(){
		var formulaObj={};
		var grids=mini.findControls(function(control){
			   if(control.type=="datagrid" && control.id.startWith("grid_")) return true;
		});
		for(var i=0;i<grids.length;i++){
			var dateSetting={};
			var grid=grids[i];
			var cols=grid.columns;
			for(var n=0;n<cols.length;n++){
				var col= grid.getColumn(n);
				var setting=col.datecalc;
				if(!setting)continue;
				
				dateSetting[col.name]=setting;
			}
			//日期设定。
			grid.dateSetting=dateSetting;
			
			grid.on("cellendedit",this.calcDateSubTable);
		}
		
	},
	this.parseDateGridStart=function(){
		
		var grids=mini.findControls(function(control){
			   if(control.type=="datagrid" && control.id.startWith("grid_")) return true;
		});
		for(var i=0;i<grids.length;i++){
			var dateStart={};
			var grid=grids[i];
			var cols=grid.columns;
			for(var n=0;n<cols.length;n++){
				var col= grid.getColumn(n);
				var setting=col.startDate;
				if(!setting)continue;
				dateStart[col.name]=setting;
			}
			//日期设定。
			grid.dateStart=dateStart;
			
			grid.on("cellbeginedit",this.calcDateSubTableStart);
		}
	},
	this.calcDateSubTableStart=function(e){
		var grid=e.sender;
		var dateStart=grid.dateStart;
		var ctl=e.editor;
		//获取行数据。
		//startDate:{type:"today",compare:"GT,GTE"}
		//startDate:{type:"control",compare:"GT,GTE",name:"kssj"}
		var row=e.record;
		for(var key in dateStart){
			var setting=dateStart[key];
			
			if(setting.type=="today"){
				if(setting.compare=="GTE"){
					ctl.setMinDate(new Date().Min());
				}
				else if(setting.compare=="GT"){
					ctl.setMinDate(new Date());
				}
			}
			else if(setting.type=="control"){
				var name=setting.control;
				var val=row[name];
				if(!val) return;
				
				if(setting.compare=="GTE"){
					ctl.setMinDate(val.Min());
				}
				else if(setting.compare=="GT"){
					ctl.setMinDate(val);
				}
			}
		}
	},
	/**
	 * 计算子表内日期。
	 */
	this.calcDateSubTable=function(e){
		var grid=e.sender;
		var dateSetting=grid.dateSetting;
		
		//获取行数据。
		var row=grid.getRow(e.rowIndex);
		
		var obj={};
		
		for(var key in dateSetting){
			var setting=dateSetting[key];
			var fStart=setting["start"];
			var fEnd=setting["end"];
			var dateUnit=parseInt(setting["dateUnit"]);
			
			var start=row[fStart];
			var end=row[fEnd];
			
			if(!start || !end) continue;
			
			if(typeof start=="string"){
				start=parseDate(start);
			}
			if(typeof start=="string"){
				end=parseDate(end);
			}
			
			var val= end.subtract(start,dateUnit);
			
			obj[key]=val;
		}
		grid.updateRow(row,obj);
	},
	this.parseMainDateStart=function(){
		var controls=mini.findControls(function(control){
			if(control.type=="datepicker") return true;
				return false;
		});
		
		/**
		 * 处理日期。
		 */
		for(var i=0;i<controls.length;i++){
			var control=controls[i];
			if(!control.startDate) continue;
			//startDate:{type:"today",compare:"GT,GTE"}
			//startDate:{type:"control",compare:"GT,GTE",name:"kssj"}
			control.on("focus",function(e){
				var ctl=e.sender;
				var setting=ctl.startDate;
				if(setting.type=="today"){
					if(setting.compare=="GTE"){
						ctl.setMinDate(new Date().Max().sub("day",1));
					}
					else if(setting.compare=="GT"){
						ctl.setMinDate(new Date());
					}
				}
				else if(setting.type=="control"){
					var name=setting.control;
					var val=mini.getByName(name).getValue();
					if(!val) return;
					
					if(setting.compare=="GTE"){
						ctl.setMinDate(val.Min());
					}
					else if(setting.compare=="GT"){
						ctl.setMinDate(val);
					}
				}
				
			})
		}
	},
	
	/**
	 * 主表日期计算。
	 */
	this.parseMainDate=function(){
		var controls=mini.findControls(function(control){
			if(control.type=="textbox" || control.type=="spinner") return true;
			return false;
		});
		for(var i=0;i<controls.length;i++){
			var ctl=controls[i];
			if(!ctl.datecalc) continue;
			var setting=ctl.datecalc;
			
			var ctlStart=mini.getByName(setting.start);
			ctlStart.setting=setting;
			ctlStart.targetObj=ctl;
			
			var ctlEnd=mini.getByName(setting.end);
			ctlEnd.setting=setting;
			ctlEnd.targetObj=ctl;
			
			this.bindDateChange(ctlStart);
			this.bindDateChange(ctlEnd);
		}
		
	},
	
	/**
	 * 主表日期数据变化。
	 */
	this.bindDateChange=function(ctl){
		var self=this;
		ctl.on("valuechanged",function(e){
			var obj=e.sender;
			var val=obj.getValue();
			if(!val) return;
			var setting=obj.setting;
			var data=self.form.getData();
			var startDate=data[setting.start];
			var endDate=data[setting.end];
			if(!startDate || !endDate) return;
			obj.targetObj.setValue(endDate.subtract(startDate,parseInt( setting.dateUnit)));
			obj.targetObj.doValueChanged();
		})
	},
	
	/**
	 * 解析主表的公式。
	 */
	this.parseMain=function(){
		var controls=mini.findControls(function(control){
			if(control.type=="textbox" || control.type=="spinner") return true;
			return false;
		});
		for(var i=0;i<controls.length;i++){
			var ctl=controls[i];
			if(!ctl.formula) continue;
			
			var formula=ctl.formula;
			var regStr = /\[(.*?)\]/g;
			var name=ctl.name;
	
			//子表处理
			// 子表公式如下 sum([{grid.amount}]);
			// 子表公式如下 sum([{grid.amount}*{grid.price}]);
			if(regStr.test(formula)){
				this.parseGridGlobal(formula,name);
			}
			//主表字段变化
			this.parseMainField(formula,name);
		}
	},
	
	/**
	 * 解析主表的公式。
	 * {
	 * 	目标字段1:{fields:[field1,field2],formula:"公式1"} 
	 * }
	 * 主表公式格式：
	 * {(数量)amount}*{total}
	 */
	this.parseMainField=function(formula,name){
		var fields=this.parseFields(formula);
		for(var i=0;i<fields.length;i++){
			var field=fields[i];
			var obj=mini.getbyName(field);
			obj.container=this;
			obj.on("valuechanged",this.calcMain);
		}
		this.globalFormula[name]={fields:fields,formula:formula};
	},
	
	/**
	 * 抽取公式中的字段。
	 * 公式格式如下:
	 * {(数量)amount} * {price}
	 */
	this.parseFields=function(formula){
		var reg=/\{(\(.*?\)){0,1}(\w*?)\}/g;
		var match = reg.exec(formula);
		var fields=[];
		while (match != null) {
			//目标字段
			var field=match[2];
			fields.push(field);
			match = reg.exec(formula);
		}
		return fields;
	},
	
	/**
	 * 解析子表的公式。
	 * 公式类型如下
	 * 
	 */
	this.parseGridGlobal=function(formula,name){
		var regStr = /\[(.*?)\]/g;
		
		var matchSub = regStr.exec(formula);
		while(matchSub){
			var subFormula=matchSub[1];
			
			var reg=/\{(\(.*?\)){0,1}(\w*?)\.(\w*?)\}/g;
			
			var match = reg.exec(subFormula);
			var fields=[];
			var tableName="";
			var gridObj={};
			while (match != null) {
				//{子表1:{主表目标字段1:{formula:公式,fields:[字段1,字段2]}
				//获取表名
				var tableName=match[2];
				//目标字段
				var field=match[3];
				fields.push(field);
				match = reg.exec(subFormula);
				 
			}
			gridObj[tableName]={};
			gridObj[tableName][name]={formula:formula,fields:fields};
			matchSub=regStr.exec(formula);
			this.gridGlobal.push(gridObj);
		}
	}
	
	/**
	 * 解析子表的公式。
	 * data-options="{formula:'{amount}*{price}'}" 
	 * 
	 * {
	 * 		子表1:
	 * 		{目标字段1:
	 * 			{formula:公式,fields:[字段1,字段2]},
	 * 	 	 目标字段2:
	 * 			{formula:公式,fields:[字段1,字段2]}
	 * 		}
	 * }
	 */
	this.parseGrid=function(){
		var formulaObj={};
		var grids=mini.findControls(function(control){
			   if(control.type=="datagrid") return true;
		});
		for(var i=0;i<grids.length;i++){
			var grid=grids[i];
			var cols=grid.columns;
			var gridName=grid.getName();
			for(var j=0;j<cols.length;j++){
				var col= grid.getColumn(j);
				var formula=col.formula;
				if(!formula)continue;
				
				var fields= this.parseFields(formula);
				formulaObj[gridName]=formulaObj[gridName] || {};
				formulaObj[gridName][col.name]={formula:formula,fields:fields};
			}
			grid.formulas=formulaObj[gridName];
			grid.container=this;
			grid.on("cellendedit",this.calcSubTable);
		}
		this.gridFormula=formulaObj;
	},
	
	/**
	 * 判断字段是否在公式中存在。
	 */
	this.isFieldExist=function(field,formulas){
		var rtn=false;
		for(var key in formulas){
			//格式{fields:[field1,field2],formula:"公式"}
			var formulaObj=formulas[key];
			if(formulaObj.formula.indexOf(field)!=-1){
				rtn=true;
				break;
			}
		}
		return rtn;
	},
	
	/**
	 * 子表行内计算。
	 * 参数说明:
	 * data:一行数据
	 * obj:{formula:公式,fields:[field1,field2]}
	 */
	this.calcValue=function(data,obj){
		var formula=obj.formula;
		var fields=obj.fields;
		this.setFieldWhenEmpty(data,fields);
		var regStr = /\{(\(.*?\)){0,1}(.*?)\}/g;
		var formulaStr = formula.replace(regStr, function(a,b,c) {
			return "data." +c;
		});
		var val= eval(formulaStr);
		val=MathUtil.roundFixed(val,2);
		return val;
	},
	
	/**
	 * 计算主表字段。
	 */
	this.calcMainValue=function(data,obj){
		
		var formula=obj.formula;
		var fields=obj.fields;
		this.setFieldWhenEmpty(data,fields);
		var regStr = /\{(\(.*?\)){0,1}(\w*?)\}/g;
		
		//替换主表。
		var formulaStr = formula.replace(regStr, function(a,b,c) {
			return "parseFloat(data." +c +")";
		});
		
		var regStr = /\[(.*?)\]/g;
		//没有子表。
		var match = regStr.exec(formulaStr);
		if(!match){
			var val=eval(formulaStr);
			val=MathUtil.roundFixed(val,2);
			return val;
		}
		
		while (match != null) {
			var rowFormula=match[1];
			var regRowExp = /\{(\(.*?\)){0,1}(\w*?)\.(\w*?)\}/g;
			var rowMatch = regRowExp.exec(rowFormula);
			
			// 1.获取子表名
			var tableName="";
			var fields=[]
			while (rowMatch != null) {
				tableName=rowMatch[2];
				fields.push(rowMatch[3])
				rowMatch = regRowExp.exec(rowFormula);
			}
			//2.替换公式
			var regStr = /\[(.*?)\]/g;
			formulaStr = formulaStr.replace(match[0], function(a,b,c) {
				return tableName +"Ary";
			});
			
			//3.计算子表。
			var grid=mini.get("grid_" +tableName);
			
			
			var rowFormula = rowFormula.replace(regRowExp, function(a,b,c,d) {
			  return  "row." +d;
			});
			
			var gridData=grid.getData();
			var tmpAry=[];
			for(var i=0;i<gridData.length;i++){
				var row=gridData[i];
				var val=eval(rowFormula);
				tmpAry.push(val);
			}
			var tabVar="var " + tableName +"Ary=tmpAry" ;
			eval(tabVar);
			
			match = regStr.exec(formulaStr);
		}
		//计算子表。
		var val=eval(formulaStr);
		val=MathUtil.roundFixed(val,2);
		return val;
		
	},
	
	/**
	 * 处理行内运算。
	 * 参数:
	 * formulas:
	 * {目标字段1:
	 * 			{formula:公式,fields:[字段1,字段2]},
	 * 	目标字段2:
	 * 			{formula:公式,fields:[字段1,字段2]}
	 * }
	 * val:表单值
	 * field:字段名
	 * row:子表行数据
	 */
	this.handRowFormula=function(grid,formulas,val,field,row){
		//判断当前字段是否在公式中，不在则返回
		var isFieldExist=this.isFieldExist(field,formulas);
		if(!isFieldExist) return;
		
		val=parseFloat(val);
		row[field]=val;
		
		for(var key in formulas){
			var formulaObj=formulas[key];
			var val=this.calcValue(row,formulaObj);
			var obj={};
			obj[key]=val;
			grid.updateRow(row,obj);
		}
	},
	
	/**
	 * 处理主表数据变更。
	 * {(数量)amount}*{total}
	 * 
	 * 参数:
	 * 参数1:目标字段
	 * 关联对象:{fields:[field1,field2],formula:"公式1"}
	 */
	this.handGlobalFormula=function(targetField,obj){
		var data= this.form.getData();
		var val=this.calcMainValue(data,obj);
		var targetObj=mini.getbyName(targetField);
		targetObj.setValue(val);
		targetObj.doValueChanged();
	},
	
	/**
	 * 数组是否包含某个值。
	 */
	this.containVal=function(ary,val){
		var aryTmp=ary.filter(function(item,index,array){
			return item==val;
		});
		return aryTmp.length;
	},

	/**
	 * 如果公式中的字段值为空，那么给该值设置为0.
	 * data 
	 */
	this.setFieldWhenEmpty=function(data,fields){
		for(var i=0;i<fields.length;i++){
			var field=fields[i];
			var val=data[field] || 0;
			data[field]=parseFloat(val);
		}
	},
	
	/**
	 * 计算主表。
	 */
	this.calcMain=function(e){
		var obj=e.sender;
		var self=obj.container;
		var name=obj.name;
	 
		for(var key in self.globalFormula){
			var obj=self.globalFormula[key];
			var fields=obj.fields;
			var formula=obj.formula;
			//是否包含字段。
			if(!self.containVal(fields,name)) continue;
			self.handGlobalFormula(key,obj);
		}
	},
	
	/**
	 * 计算表单。
	 */
	this.calcSubTable=function(e){
		
		var grid=e.sender;
		//获取行数据。
		var row=grid.getRow(e.rowIndex);
		//获取行内公式。
		var formulas=grid.formulas;
		var self=grid.container;
		var field=e.field;
		
		var val=e.value || 0;
		//处理行内公式。
		self.handRowFormula(grid,formulas,val,field,row);
		//处理主表统计情况。
		setTimeout(function(){
			self.handGridChange(grid.id,10);	
		})
	},
	/**
	 * 处理子表数据删除时对总表进行重新计算。
	 * 统计公式如下:
	 * sum([{gridOrders.amount}*{gridOrders.amount}])
	 * 
	 * 		{
	 * 			主表目标字段1:{formula:公式,fields:[字段1,字段2]}
	 * 		}
	 */
	this.handGridChange=function(tableId){
		var tbName=tableId.replace("grid_","");
		//{主表目标字段1:{formula:公式,fields:[字段1,字段2]}}
		var formulaObjs=[];
		//找到所有子表中的目前子表
		var isCurTable=false;
		for(var i=0;i<this.gridGlobal.length;i++){
			for(var tbKey in this.gridGlobal[i]){
				if(tbName==tbKey){
					formulaObjs.push(this.gridGlobal[i][tbKey]);
//					break;
//					isCurTable=true;
				}
			}
			//if(isCurTable) break;
		}
		if(!formulaObjs) return;
		//获取子表数据。
		if(tableId.indexOf("grid_")==-1){
			tableId="grid_"+tableId;
		}
		//var gridData=mini.get(tableId).getData();
		//取主表数据
		for(var i=0;i<formulaObjs.length;i++){
			var formulaObj=formulaObjs[i];
			for(var targetField in formulaObj){
				var fo=formulaObj[targetField];
				this.handGridData(targetField,fo);
			}
		}
//		for(var targetField in formulaObjs){
//			var formulaObj=formulaObjs[targetField];
//			//this.handGridData(gridData,targetField,formulaObj);
//			this.handGridData(targetField,formulaObj);
//		}
	},
	/**
	 * 参数：
	 * gridData:表格数据
	 * targetField:目标字段
	 * formulaObj:公式对象 格式{formula:"公式",fields:[field1,field2]}
	 * 公式:格式
	 * {amount} + sum([{table1.amount) * {table1.amount}])
	 * 
	 * 处理步骤如下
	 * 1.计算子表数据
	 *  {table1.amount) * {table1.amount}
	 *  计算结果保存到aryData
	 * 2.将公式替换成如下形式
	 * 	{amount} + sum(aryData)
	 * 3.再次变换公式 
	 * 	{data.amount} + sum(aryData)
	 * 返回结果。
	 * 
	 */
	this.handGridData=function(targetField,formulaObj){
		
		var data=this.form.getData();
		var formula=formulaObj.formula;
		var regStr = /\{(\(.*?\)){0,1}(\w*?)\}/g;
		var fields=this.parseFields(formula);
		//替换主表。
		var formula = formula.replace(regStr, function(a,b,c) {
			return "parseFloat(data." +c +")";
		});
		var regStr = /\[(.*?)\]/g;
		var match = regStr.exec(formula);
		while(match){
			//{table1.amount) * {table1.amount}
			var rowFormula=match[1];
			var regRowExp = /\{(\(.*?\)){0,1}(\w*?)\.(\w*?)\}/g;
			var rowMatch = regRowExp.exec(rowFormula);
			var rowFormula = rowFormula.replace(regRowExp, function(a,b,c,d) {
			  return "parseFloat(row." +d +")";
			});
			var tableName=rowMatch[2];
			//1.遍历子表数据使用工具进行运算。
			var aryData=[];
			var gridData=mini.get("grid_"+tableName).getData();
			for(var i=0;i<gridData.length;i++){
				var row=gridData[i];
				var val=eval(rowFormula);
				aryData.push(val);
			}
			//2.替换公式{amount} + sum(aryDataTable)
			formula = formula.replace(match[0], function(a,b,c) {
				return "aryData"+tableName;
			});
			//为子表数据赋值
			var tabData="var aryData" + tableName +"=aryData" ;
			eval(tabData);
			for(var i=0;i<fields.length;i++){
				var field=fields[i];
				var tmp= data[field];
				tmp =tmp || 0;
				data[field]=parseFloat(tmp);
			}
			var regStr = /\[(.*?)\]/g;
			match=regStr.exec(formula);
		} 
		//最终进行运算得出结果。
		var val=eval(formula);
		val=MathUtil.roundFixed(val,2);
		var targetObj=mini.getbyName(targetField);
		targetObj.setValue(val);
		//值变后触发上面的valuechange事件。
		targetObj.doValueChanged();
	},
	/**
	 * 级联查询。
	 */
	this.parseCascadeQuery=function(){
		//处理主表的级联查询。
		this.parseMainCascadeQuery();
		//处理子表级联查询。
		this.parseGridCascadeQuery();
	},
	//处理主表级联查询。
	this.parseMainCascadeQuery=function(){
		var controls=mini.findControls(function(control){
			if(control.type=="combobox" && control.sql) return true;
			var el=$(control.el);
			//只取子表的combox控件。
			if(el.closest("div.rx-grid").length>0) return false;
			return false;
		});
		for(var i=0;i<controls.length;i++){
			var comboCtl=controls[i];
			var sqlJson=comboCtl.sql;
			if(!sqlJson.parent) continue;
			
			var parentObj=mini.getbyName(sqlJson.parent);
			if(!parentObj) continue;
			parentObj.targetCtl=comboCtl;
			
			this.handComboxChange(parentObj,comboCtl);
		}
	},
	//处理子表级联查询。
	this.parseGridCascadeQuery=function(){
		var aryWin=$("div.popup-window-d");
		for(var i=0;i<aryWin.length;i++){
			var win=aryWin[i];
			var ctls=mini.getChildControls(win);
			for(var m=0;m<ctls.length;m++){
				var ctl=ctls[m];
				if(ctl.type!='combobox') continue;
				this.handGridCombox(ctl,win);
			}
		}
	},
	//处理下拉框查询。
	this.handGridCombox=function(combox,win){
		if(!combox.sql) return;
		var sqlJson=combox.sql;
		if(!sqlJson.parent) {
			var url=__rootPath +"/sys/db/sysSqlCustomQuery/queryForJson_" + sqlJson.sql+".do";
			combox.load(url);
		}
		else{
			var parentObj=mini.getbyName(sqlJson.parent,win);
			this.handComboxChange(parentObj,combox);
		}
	},
	//处理单个控件变化。
	this.handComboxChange=function(parentObj,comboCtl){
		parentObj.targetCtl=comboCtl;
		parentObj.on("valuechanged",function(e){
			var sender=e.sender;
			var val=sender.getValue();
			if(!val) return;
			var target=sender.targetCtl;
			var sql=comboCtl.sql;
			var url=__rootPath +"/sys/db/sysSqlCustomQuery/queryForJson_" + sql.sql 
				+".do?params={" +sql.param +":\"" +val+"\"}";
			url=encodeURI(url);
			target.load(url);
			target.doValueChanged();
		});
	}
	
	
}