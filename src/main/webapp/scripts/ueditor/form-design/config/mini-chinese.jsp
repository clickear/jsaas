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
var curEditEl=editor.editdom;
window.onload = function() {
	//加载树
	loadTree(editor,curEditEl);
	
}



function loadTree(editor,el){
	var data=getMetaData(editor,el);
	var treeObj=mini.get("fieldTree");
	treeObj.loadList(data.aryData);
}

function getMetaData(editor,el){
	var container=$(editor.getContent());
	var elObj=$(el);
	
	var aryJson=[];
	var root={id: "root_", text: "根节点",table_:false, main: false,expanded: true};
	
	aryJson.push(root);
	
	var els=$("[plugins='mini-textbox']:not(div.rx-grid [plugins='mini-textbox'])",container);
	getJson(els,aryJson);
	
	els=$("[plugins='mini-spinner']:not(div.rx-grid [plugins='mini-spinner'])",container);
	getJson(els,aryJson);
	
	var obj={aryData:aryJson,el:el};
	return obj;
	
}

function getJson(els,aryJson){
	els.each(function(){
		var obj=$(this);
		var label=obj.attr("label");
		var name=obj.attr("name");
		var fieldObj={id: name, text: label,table_:false, main: true,pid:"root_"};   
		aryJson.push(fieldObj);
	});
}

var selNode;

function fieldClick(e){
	var node=e.node;
	if(node.id=="root_") return;
	selNode=node;
}

dialog.onok = function (){
	if(!selNode || selNode.id=='root_'){
		alert("请选择一个需要转大写的字段!");
		return ;
	}
	var curEl=$(curEditEl);
	var options=curEl.attr("data-options") || "{}";
	var json=mini.decode ( options ) ;
	
	json.chineseup={field:selNode.id};
	
	curEl.attr("data-options",mini.encode(json)) ;
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
	</tr>
	
</table>

<script type="text/javascript">
mini.parse();
</script>
</body>
</html>