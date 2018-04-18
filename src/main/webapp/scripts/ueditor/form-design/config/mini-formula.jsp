<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html >
<html>
<head>
<title>公式计算</title>
<%@include file="/commons/mini.jsp"%>
<style type="text/css">
	fieldset{height: 100%;border: 1px solid silver;}
	fieldset legend{font-weight: bold;}
	
	div.operatorContainer{
		padding: 5px;
		text-align: left;
	}
	
	div.operatorContainer > div {
	    border-radius: 5px;
	    border:1px solid silver;
	    display:inline-block;
	    background: #F0F0F0;
	    padding: 2px; 
	    line-height:32px;
	    text-align:center;
	    margin:4px;
	    width: 32px;
	    font-size:16px;
	    font-weight:bold;
	    height: 32px; 
	    
	}
	
	div.operatorContainer > div:hover{
	  background: white;
	}
	
	#divHelp{
		position: absolute;
	}
	
	span.heigh-light{
		color:red;
		font-weight:bold;
		font-size:16px;
	}
	
</style>
</head>
<body>
<script type="text/javascript">

var isMain=true;

var curEditEl=UE.plugins["formulaPlugin"].editdom;


window.onload = function() {
	//加载树
	loadTree(editor,curEditEl);
	//处理操作符
	handOperator();
	//初始化公式
	init();
	//初始化帮助
	initHelp();
}

function init(){
	var curEl=$(curEditEl);
	var formulaObj=document.getElementById("formula");
	var options=curEl.attr("data-options") || "{}";
	var json=mini.decode ( options ) ;
	formulaObj.value=json.formula || "";
}

function initHelp(){
	 $('#divHelp').data('powertip', $("#formulaHelp").html());
	 $('#divHelp').powerTip({
	     placement: 'e',
	     mouseOnToPopup: true
	 });
}

function handOperator(){
	$("div.operatorContainer div").click(function(e){
		var obj=$(this);
		var formulaObj=document.getElementById("formula");
		$.insertText(formulaObj,obj.attr("operator"));
	})
}

function loadTree(editor,el){
	var data=getMetaData(editor,el);
	var treeObj=mini.get("fieldTree");
	
	if(isMain){
		$(".collection").show();
	}
	else{
		$(".collection").hide();
	}
	treeObj.loadList(data.aryData);
}



function getMetaData(editor,el){
	var container=$(editor.getContent());
	var elObj=$(el);
	var grid=elObj.closest(".rx-grid");
	isMain=grid.length==0;
	var aryJson=[];
	
	var root={id: "root_", text: "根节点",table_:false, main: false,expanded: true};
	
	aryJson.push(root);
	
	if(isMain){
		var els=$("[datatype='number']:not(div.rx-grid [datatype='number'])",container);
		var grids=$("div.rx-grid",container);
		els.each(function(){
			var obj=$(this);
			var label=obj.attr("label");
			var name=obj.attr("name");
			var fieldObj={id: name, text: label,table_:false, main: true,pid:"root_"};   
			aryJson.push(fieldObj);
		});
		for(var i=0;i<grids.length;i++){
			var grid=$(grids[i]);
			parseGridMetaData(grid,aryJson);
		}
	}
	else{
		parseGridMetaData(grid,aryJson);
	}
	var obj={aryData:aryJson,el:el};
	return obj;
	
}

function parseGridMetaData(grid,aryJson){
	var label=grid.attr("label");
	var tableName=grid.attr("name");
	var tabObj={id: tableName, text: label,table_:true,pid:"root_",expanded: true};   
	aryJson.push(tabObj);
	
	$("input[datatype='number']",grid).each(function(){
		var obj= $(this);
		var label=obj.attr("label");
		var name=obj.attr("name");
		var fieldObj={id: name, text: label,table_:false, main: false,pid: tableName};  
		aryJson.push(fieldObj);
	});
}

function fieldClick(e){
	var node=e.node;
	if(node.id=="root_") return;
	var content="";
	if(isMain){
		if(node.table_){
			content="";
		}
		else if(node.main){
			content="{"+node.id+"}";
		}
		else{
			content="{"+node.pid +"." + node.id+"}";
		}
	}
	else{
		content="{"+node.id+"}";
	}
	var formulaObj=document.getElementById("formula");
	$.insertText(formulaObj,content);
	
}

dialog.onok = function (){
	var formulaObj=document.getElementById("formula");
	var curEl=$(curEditEl);
	var options=curEl.attr("data-options") || "{}";
	var json=mini.decode ( options ) ;
	json.formula=formulaObj.value;
	curEl.attr("data-options",mini.encode(json)) ;
}

</script>
<table style="width:100%;height:100%;">
	<tr style="height:40%;">
		<td width="50%" style="vertical-align: top;">
			<fieldset>
    			<legend>表单字段</legend>
    			<ul id="fieldTree" class="mini-tree" onnodeclick="fieldClick"  style="width:100%;padding:5px;height:175px;" 
	    			showTreeIcon="true"  resultAsTree="false"   >        
				</ul>
  			</fieldset>
		</td>
		<td>
			<fieldset>
    			<legend>运算符</legend>
    			<div class="operatorContainer">
    				<div operator="+">+</div>
	    			<div operator="-">-</div>
	    			<div operator="*">*</div>
	    			<div operator="/">/</div>
	    			<div operator="()">()</div>
	    			<div class="collection" operator="[]">[]</div>
	    			<div class="collection" operator="MathUtil.sum()">sum</div>
	    			<div class="collection" operator="MathUtil.avg()">avg</div>
	    			<div class="collection" operator="MathUtil.min()">min</div>
	    			<div class="collection" operator="MathUtil.max()">max</div>
    			</div>
    			
  			</fieldset>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<fieldset>
    			<legend>公式编辑 </legend>
    			<div style="position:relative;height: 20px;">
    				<div  class="iconfont icon-Help-configure" id="divHelp"  title="帮助"></div>
    			</div>
    			<textarea id="formula" style="width:98%;height:150px"></textarea>
  			</fieldset>
		</td>
	</tr>
</table>
<div style="display:none" id="formulaHelp">
<pre>
1.主表公式写法：
	1.1.主表字段计算
		{amount1} + {amount2}
	1.2.主表和子表数据统计
		{amount1} + {amount2} + MathUtil.sum(<span class="heigh-light">[</span>{amount}*{price}<span class="heigh-light">]</span>)
		说明:主表字段(amount1) +主表字段(amount2) + 子表的数量和价格相乘后合计。
		注意:子表字段需要使用"<span class="heigh-light">[]</span>"括起来。
		公式说明:
		MathUtil.sum:子表列合计  
		MathUtil.max:子表列最大值
		MathUtil.min:子表列最小值
		MathUtil.avg:子表列平均值
2.子表公式写法:
	{amount}*{price}
</pre>
</div>
<script type="text/javascript">
mini.parse();
</script>
</body>
</html>