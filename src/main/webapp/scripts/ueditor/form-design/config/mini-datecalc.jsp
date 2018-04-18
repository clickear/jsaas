<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html >
<html>
<head>
<title>日期计算</title>
<%@include file="/commons/mini.jsp"%>

<style type="text/css">
	fieldset{height: 100%;border: 1px solid silver;}
	fieldset legend{font-weight: bold;}
	.droppable { width: 150px; cursor:pointer; height: 30px; padding: 0.5em;  margin: 5px;border: 1px dashed black; }
	.droppable.highlight{ background: orange;}
</style>
</head>
<body>
<script type="text/javascript">
var isMain=true;
var curEditEl=editor.editdom;
window.onload = function() {
	//加载树
	loadTree(editor,curEditEl);
	//初始化公式
	init();
}

function init(){
	var curEl=$(curEditEl);
	var options=curEl.attr("data-options") || "{}";
	var json=mini.decode ( options ) ;
	var setting=json.datecalc;
	if(setting){
		$("#start").text(setting.start);
		$("#end").text(setting.end);
		mini.get("dateUnit").setValue(setting.dateUnit);
	}
	
	$("div.droppable" ).click(function(){
		 $("div.droppable" ).removeClass("highlight");
		 $(this).addClass("highlight") ;
	});
}

function loadTree(editor,el){
	var data=getMetaData(editor,el);
	var treeObj=mini.get("fieldTree");
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
		var els=$("[plugins='mini-datepicker']:not(div.rx-grid [plugins='mini-datepicker'])",container);
		els.each(function(){
			var obj=$(this);
			var label=obj.attr("label");
			var name=obj.attr("name");
			var fieldObj={id: name, text: label,table_:false, main: true,pid:"root_"};   
			aryJson.push(fieldObj);
		});
	}
	else{
		$("[plugins='mini-datepicker']",grid).each(function(){
			var obj= $(this);
			var label=obj.attr("label");
			var name=obj.attr("name");
			var fieldObj={id: name, text: label,table_:false, main: false,pid:"root_"};  
			aryJson.push(fieldObj);
		});
	}
	var obj={aryData:aryJson,el:el};
	return obj;
	
}

function fieldClick(e){
	var node=e.node;
	if(node.id=="root_") return;
	
	var rtn=$("div.droppable" ).hasClass("highlight");
	if(!rtn) {
		alert("请选择开始或结束时间设置!");
		return ;	
	}
	var obj=$("div.droppable.highlight" );
	obj.text(node.id);
}

dialog.onok = function (){
	var curEl=$(curEditEl);
	var options=curEl.attr("data-options") || "{}";
	var json=mini.decode ( options ) ;
	var start=$("#start").text();
	var end=$("#end").text();
	var dateUnit=mini.get("dateUnit").getValue();
	if(!start || !end){
		alert("选择开始或结束字段");
		return;
	}
	if(start==end){
		alert("开始或结束字段不能为同一个字段");
		return;
	}
	json.datecalc={start:start,end:end,dateUnit:dateUnit};
	curEl.attr("data-options",mini.encode(json)) ;
}

dialog.oncancel = function (){
	var curEl=$(curEditEl);
	curEl.removeAttr("data-options") ;
}

</script>
<table style="width:100%;height:100%;">
	<tr style="height:40%;">
		<td width="30%" style="vertical-align: top;">
			<fieldset>
    			<legend>表单字段</legend>
    			<ul id="fieldTree" class="mini-tree" onnodeclick="fieldClick"  style="width:100%;padding:5px;height:175px;" 
	    			showTreeIcon="true"  resultAsTree="false"  allowDrag="true" >        
				</ul>
  			</fieldset>
		</td>
		<td  style="vertical-align: top;">
			<fieldset>
    			<legend>日期计算设置</legend>
				<table style="width:100%;">
					<tr>
						<th>开始日期</th>
						<td>
							<div class="droppable" id="start"></div>
						</td>
					</tr>
					<tr>
						<th>结束日期</th>
						<td>
							<div class="droppable" id="end"></div>
						</td>
					</tr>
					<tr>
						<th>单位</th>
						<td>
							<div id="dateUnit" class="mini-radiobuttonlist"   
	    						textField="text" valueField="id" value="3" 
	    						data="[{id:3,text:'天数'},{id:2,text:'小时'},{id:1,text:'分钟'}]" >
							</div>   
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
		
	</tr>
	
</table>
<div style="display:none" id="formulaHelp">

</div>
<script type="text/javascript">
mini.parse();
</script>
</body>
</html>