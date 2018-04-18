<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
    <head>
        <title>编辑流程实例的业务数据</title>
        <%@include file="/commons/edit.jsp"%>
        <link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/jsoneditor/jsoneditor.css"/>
        <script src="${ctxPath}/scripts/jquery/plugins/jsoneditor/json2.js"></script>
    	<script src="${ctxPath}/scripts/jquery/plugins/jsoneditor/jquery.jsoneditor.js"></script>
    </head>
<body>
	<div id="toolbar1" class="mini-toolbar" >
	    <table style="width:100%;">
	        <tr>
	            <td style="width:100%;" id="toolbarBody">
	               	<a class="mini-button" iconCls="icon-save" onclick="onSave()">保存</a>
					<a class="mini-button" iconCls="icon-close" onclick="CloseWindow()">关闭</a>
	            </td>
	        </tr>
	    </table>
	</div>
	
	<div id="legend">
        <span id="expander">展开</span>
        <span class="array">array</span>
        <span class="object">object</span>
        <span class="string">string</span>
        <span class="number">number</span>
        <span class="boolean">boolean</span>
        <span class="null">null</span>
        <span>删除属性即删除项</span>
   	</div>
   	
	<div class="mini-fit">   	
		<div class="mini-tabs" activeIndex="0"  style="width:98%;height:100%;">
		    <div title="JSON编辑">
		        <div id="editor" class="json-editor"></div>
		    </div>
		    <div title="数据"   >
		        <textarea id="json" style="width:96%;height:250px;"></textarea>
		    </div>
		    
		</div>
	</div>
    
   

	<script type="text/javascript">
		var instId="${param.instId}";
		//需要保存的数据
		var jsonData;

		function updateJSON(data) {
			jsonData=JSON.stringify(data);
			$('#json').val(jsonData);
		}
		
		function init(){
			var url=__rootPath+'/bpm/core/bpmInst/getJsonByInstId.do?instId='+ instId;
			$.get(url,function(data){
				
				$('#editor').jsonEditor(data, {change: updateJSON});
				
				updateJSON(data);
			})
		}
		

		$(document).ready(function() {
			init();

		    $('#expander').click(function() {
		        var editor = $('#editor');
		        editor.toggleClass('expanded');
		        $(this).text(editor.hasClass('expanded') ? '收起' : '展开所有');
		    });
		    
		    $('#json').change(function() {
		        var val = $('#json').val();
		        if (val) {
		            try { json = JSON.parse(val); }
		            catch (e) { alert('Error in parsing json. ' + e); }
		        } else {
		            json = {};
		        }
		        $('#editor').jsonEditor(json, { change: updateJSON});
		    });
		  
		});

	
		mini.parse();

	  	function onSave(){
	  		_SubmitJson({
	  			url:__rootPath+'/bpm/core/bpmInst/saveFormData.do',
	  			method:'POST',
	  			data:{
	  				instId:instId,
	  				formData:jsonData
	  			},
	  			success:function(text){
	  				CloseWindow('ok');
	  			}
	  		});
	  	}
    </script>
	
</body>
</html>