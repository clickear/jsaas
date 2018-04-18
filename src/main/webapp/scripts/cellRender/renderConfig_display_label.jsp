<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>列表中的单元格的文本显示</title>
<%@include file="/commons/edit.jsp"%>
<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/colorpicker/jquery.colorpicker.js"></script> 
<style>
	body{
		background-color:#fff;
	}
</style>
</head>
<body>
	<div class="mini-toolbar">
		<a class="mini-button" iconCls="icon-save" onclick="CloseWindow('ok')">保存</a>
		<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	</div>
	<form id="miniForm">
		<table class="table-detail column_2_m" cellspacing="1" cellpadding="1">
			<tr>
				<th>标签分隔符</th>
				<td>
					<input class="mini-textbox" name="splitChar" /> 默认不填写为英文输入法的空格字符。
				</td>
				
			</tr>
			<tr>
				<th>
					标签背景颜色(*)
				</th>
				<td>
					<input type="text" id="bgcolor" name="bgcolor" style="height:26px;border-radius:4px;border:solid 1px #ccc"/>
				</td>
			</tr>
			<tr>
				<th>
					标签颜色(*)
				</th>
				<td>
					<input type="text" id="fgcolor" name="fgcolor" style="height:26px;border-radius:4px;border:solid 1px #ccc"/>
				</td>
			</tr>
			<tr>
				<th>背景颜色示例</th>
				<td>
					<span id="color_show" style="display:block;width:150px;height:26px;padding:6px;">内容标签</span>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		mini.parse();
		var form=new mini.Form('miniForm');
		
		$(function(){
			$("#bgcolor").colorpicker({
			    fillcolor:true,
			    success:function(o,color){
			        $('#color_show').css("background-color",color); 
			    }
			});
			$("#fgcolor").colorpicker({
			    fillcolor:true,
			    success:function(o,color){
			        $('#color_show').css("color",color); 
			    }
			});
		});
		
		function setData(data,fieldDts){
			form.setData(data);
			$("#bgcolor").val(data.bgcolor);
			$("#fgcolor").val(data.fgcolor);
			
			if(data.bgcolor){
				$('#color_show').css("background-color",data.bgcolor); 
			}
			
			if(data.fgcolor){
				$('#color_show').css("color",data.fgcolor); 
			}
		}
		
		function getData(){
			var formData=form.getData();
			formData.bgcolor=$("#bgcolor").val();
			formData.fgcolor=$("#fgcolor").val();
			return formData;
		}
	</script>				
</body>
</html>