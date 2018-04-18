<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<title></title>
<%@include file="/commons/mini.jsp"%>
<style type="text/css">
table tr.firstRow th{border-top-width:1px !important;}
caption{border:0px !important;}
html,body {
	font-family: "微软雅黑";
	color:#666;
	background: #f8f8f8
}

.shadowBox{
	width: 94%;
	margin-top: 20px;
}

.form-detail caption{
	padding: 10px 14px;
	color: #666;
}

.transparent>caption{
	padding-top: 2px;
}

.column-two,.column-four{
	width:100%;
	height:100%;
	margin: 0px auto;
}
.form-detail {
	border-collapse: collapse;
	border: solid 1px #ececec;
 	width:100%; 
	height:auto;
	margin:0 auto
}
.form-detail tr th{
	font-size:14px;
	font-weight:400;
	color:#444;
	line-height:30px;
	border: 1px solid #ececec;
	text-align:right;
	line-height:30px;
	height:30px;
	padding:6px;

}
.form-detail tr td{
	font-size:14px;
	font-weight:400;
	color:#444;
	line-height:30px;
	border: 1px solid #ececec;
	text-align: left;
	line-height:30px;
	height:30px;
	padding:6px;
}
.form-detail.column-two tr th {width:20%;}
.form-detail.column-two tr td {width:80%;}
.form-detail.column-four tr th {width:15%;}
.form-detail.column-four tr td {width:30%;}

.heightBox{
	height: 8px;
}

/*transparent 透明 */
.transparent{}

.transparent .heightBox{
	display: none;
}

#previewForm>table>caption{
	font-size: 18px;
	text-align: center;
}

/* blue 样式 */
 .blue caption{
	background: #a3e0ff;
}
.blue th{
	background: #d9edf7;
}
/* grey配色 */
 .grey caption{
	background: #ececec;
	color: black;
}
.grey th{
	background: #f0f0f0;
}
/* brown样式 */
 .brown caption{
	background: #e7bd9f;
}
.brown th{
	background: #e7d4c6;
}
/* green样式 */
 .green caption{
	background: #a4e8a4;
}
.green th{
	background: #E0EEE0;
}
</style>
</head>
<body>
<!-- 	<div style="height: 20px"></div> -->
	<div>
		<div class="form-outer">
				<form id="miniForm">
					<table class="table-detail column_2_m" cellspacing="0" cellpadding="1">
						<tr>
							<th style="background-color: white">列数</th>
							<td>
							<div id="column" name="column" class="mini-radiobuttonlist"  onvaluechanged="previewHtml()"
	  							  textField="text" valueField="id" value="column-four"  data="[{id:'column-two',text:'单列'},{id:'column-four',text:'双列'},{id:'column-six',text:'三列'},{id:'column-eight',text:'四列'}]" >
								</div>	
							</td>
							<th style="background-color: white">行数</th>
							<td>
								<input id="row" name="row" changeOnMousewheel="false" class="mini-spinner"  minValue="1" maxValue="50"   onvaluechanged="previewHtml()"/>
							</td>
						</tr>
						<tr>
						<th style="background-color: white">样式风格</th>
						<td colspan="3">
							<input name="color" type="radio" value="transparent" checked onclick="radioChange()"/><a style="color:#666;font-size: 14px">透明</a>
							<input name="color" type="radio" value="blue" onclick="radioChange()"/><a style="color:#a3e0ff;font-size: 14px">浅蓝</a>
							<input name="color" type="radio" value="green"  onclick="radioChange()"/><a style="color:#66CDAA;font-size: 14px">淡绿</a>
							<input name="color" type="radio" value="brown"  onclick="radioChange()"/><a style="color:#e7bd9f;font-size: 14px">树棕</a>
							<input name="color" type="radio" value="grey"  onclick="radioChange()"/><a style="color:#b4b4b4;font-size: 14px">银灰</a>
						</td>
						</tr>
						
					</table>
				</form>
			</div>
		</div>
		<div id="previewForm"></div>
	<script type="text/javascript">
		mini.parse();
		previewHtml();
        function buildHtml(isPreview){
    		var column=mini.get("column");//列数
    		var row=mini.get("row");//行数
    		var colorStyle=$("input[name='color']:checked").val();
    		var tr="";//行html
    		var oNode="<div class='shadowBox'>";
        	oNode+="<table cellspacing='1' cellpadding='0' class='form-detail ";
    		oNode+=column.getValue()+" ";
    		oNode+=colorStyle+"' ";
    		oNode+="cellspacing='1' cellpadding='0' >";
    		oNode+="<caption>标题</caption>";
    		oNode+="<tbody>";
    		if(column.getValue()=="column-two"){
    			tr="<tr><th>  </th><td>  </td></tr>";
    		}else if(column.getValue()=="column-four"){
    			tr="<tr><th>  </th><td>  </td><th>  </th><td>  </td></tr>";
    		}else if(column.getValue()=="column-six"){
    			tr="<tr><th>  </th><td>  </td><th>  </th><td>  </td><th>  </th><td>  </td></tr>";
    		}else if(column.getValue()=="column-eight"){
    			tr="<tr><th>  </th><td>  </td><th>  </th><td>  </td><th>  </th><td>  </td><th>  </th><td>  </td></tr>";
    		}
    		if(isPreview){
    			oNode+=(tr+tr);
    		}else{
    			for(var i=0;i<row.getValue();i++){
        			oNode+=tr;
        		}
    		}
    		
            oNode+="</tbody></table></div> ";
            return oNode;
        }
		
		function previewHtml(){
			$("#previewForm").html(buildHtml(true));
			$("#previewForm").hide();
			$("#previewForm").fadeIn();
		}
		
		function radioChange(){
			previewHtml();
		}
		
		
		//确认
		dialog.onok = function (){
	            editor.execCommand('insertHtml',buildHtml(false));
		};
		
	</script>
</body>
</html>
