<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>关系线定义</title>
    <%@include file="/commons/dynamic.jspf"%>
    <link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/orgchart/exts/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/orgchart/dist/css/jquery.orgchart.css">
 	<link rel="stylesheet" href="${ctxPath}/scripts/jquery/plugins/orgchart/exts/css/style.css">
 	
 	<link href="${ctxPath}/styles/commons.css" rel="stylesheet" type="text/css" />
	<link href="${ctxPath}/styles/icons.css" rel="stylesheet" type="text/css" />
	<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
	<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
	 <script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/orgchart/jquery-3.1.0.min.js"></script>
  	<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/orgchart/html2canvas.min.js"></script>
  	<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/orgchart/dist/js/jquery.orgchart.js"></script>
	
</head>
<body>
    
	  <div id="edit-panel" class="view-state">
	   <span  class="radio-panel">
	   关系定义【 ${osRelType.name}】
	   </span>
	    <label class="selected-node-group">选择关系方:</label>
	    <input type="text" id="selected-node" class="selected-node-group" readonly="readonly">
	    
	    <span id="node-type-panel" class="radio-panel">
	      
	    </span>
	 	<button type="button" id="btn-add-nodes" class="fa fa-plus-circle btn-inputs">添加下级</button>
	    <button type="button" id="btn-delete-nodes" class="fa fa-minus-circle btn-inputs">删除</button>
	    <button type="button" id="btn-delete-nodes" class="fa fa-minus-refresh btn-inputs" onclick="location.reload();">刷新</button>
	  </div>
 	  <div id="chart-container" style="height:560px;width:100px">
 		
 	  </div>
 
  <script type="text/javascript">
 	mini.parse();

 	'use strict';
	$(function(){
		resize();
	});
	$(window).resize(function(){
    	setTimeout('resize()',500);
    });
	function resize(){
		 var winHeight = $(window).height();
		 var winWidth=$(window).width();
		$('#chart-container').height(winHeight-120);
		$('#chart-container').width(winWidth-50);
	}
	
	var relType='${osRelType.relType}';
	var typeId='${osRelType.id}';
	
 	(function($){

 	  $(function() {
 	    $('#chart-container').orgchart({
 	      'data' : __rootPath+'/sys/org/osRelInst/getReportLines.do?typeId=${osRelType.id}',
 	      'exportButton': true,
 	     'nodeContent': 'title',
 	      'zoom':true,
 	      //'toggleSiblingsResp': true,
 	      'exportFilename': 'SportsChart',
 	      'parentNodeSymbol': 'fa-th-large',
 	      'createNode': function($node, data) {
 	    	
 	        $node[0].id = data.id;
 	        $node[0].instId=data.instId;
 	        $node.on('click', function(event) {
 	          if (!$(event.target).is('.edge')) {
 	            $('#selected-node').val(data.name).data('node', $node);
 	          }
 	        });
 	      }
 	    })
 	    .on('click', '.orgchart', function(event) {
 	      if (!$(event.target).closest('.node').length) {
 	        $('#selected-node').val('');
 	      }
 	    });

 	    /*
 	    $('input[name="chart-state"]').on('click', function() {
 	      $('#edit-panel, .orgchart').toggleClass('view-state');
 	      if ($(this).val() === 'edit') {
 	        $('.orgchart').find('tr').removeClass('hidden')
 	          .find('td').removeClass('hidden')
 	          .find('.node').removeClass('slide-up slide-down slide-right slide-left');
 	      } else {
 	        $('#btn-reset').trigger('click');
 	      }
 	    });*/

 	    $('#btn-add-nodes').on('click', function() {
 	    	var $node = $('#selected-node').data('node');
	 	      if (!$node) {
	 	        alert('请在图中选择一节点！');
	 	        return;
	 	      }
	 	      
	 	    var instId=$node[0].instId;
	 	   
	 	   var single=false;
	 	   
	 	   var nodeType ='children';

 	       if(relType=='GROUP-GROUP'){
	    		_GroupDlg(false,function(groups){
	    			var gIds=[];
	    			for(var i=0;i<groups.length;i++){
	    				gIds.push(groups[i].groupId);
	    			}
	    			//在后台生成关系
	 	    		_SubmitJson({
	 	    			url:__rootPath+'/sys/org/osRelInst/saveRelInsts.do',
	 	    			method:'POST',
	 	    			data:{
	 	    				typeId:typeId,
	 	    				groupIds:gIds.join(','),
	 	    				instId:instId
	 	    			},
	 	    			success:function(text){
	 	    				location.reload();
	 	    			}
	 	    		});
	 	    	});
	    	}else if(relType=='USER-USER'){
	    		var userIds=[];
	    		 _UserDlg(single,function(users){
	  	    		if(single){
	  	    			nodeVals.push({
	  	    				name:users.fullname,
	  	    				id:users.userId
	  	    			});
	  	    			userIds.push(users.userId);
	  	    		}else{
	 	 	    		for(var i=0;i<users.length;i++){
	 	 	    			userIds.push(users[i].userId);
	 	 	    		}
	  	    		}
	  	    		_SubmitJson({
 	 	    			url:__rootPath+'/sys/org/osRelInst/saveRelInsts.do',
 	 	    			data:{
 	 	    				typeId:typeId,
 	 	    				userIds:userIds.join(','),
 	 	    				instId:instId
 	 	    			},
 	 	    			success:function(text){
 	 	    				location.reload();
 	 	    			}
	 	 	    	});
	  	    	});
	    	}
 	    });
 	    /*
 	    $("#btn-add-top").on('click',function(){
 	    	if(relType=='GROUP-GROUP'){
 	    		_GroupDlg(true,function(group){
 	    			//在后台生成关系
 	 	    		_SubmitJson({
 	 	    			url:__rootPath+'/sys/org/osRelInst/saveRelInst.do',
 	 	    			data:{
 	 	    				relType:'GROUP-GROUP',
 	 	    				typeId:typeId,
 	 	    				groupId:group.groupId
 	 	    			},
 	 	    			success:function(text){
 	 	    				location.reload();
 	 	    			}
 	 	    		});
 	 	    	});
 	    	}else if(relType=='USER-USER'){
 	    		_UserDlg(true,function(user){
 	    			//在后台生成关系
 	 	    		_SubmitJson({
 	 	    			url:__rootPath+'/sys/org/osRelInst/saveRelInst.do',
 	 	    			data:{
 	 	    				relType:'USER-USER',
 	 	    				typeId:typeId,
 	 	    				userId:user.userId
 	 	    			},
 	 	    			success:function(text){
 	 	    				location.reload();
 	 	    			}
 	 	    		});
 	 	    	});
 	    		
 	    	}
 	    });*/

 	    $('#btn-delete-nodes').on('click', function() {
 	      var $node = $('#selected-node').data('node');
 	      if (!$node) {
 	        alert('请在图中选择一节点！');
 	        return;
 	      }
 	      var instId=$node[0].instId; 
 	      if(!instId || instId=='0'){
 	    	 $('#chart-container').orgchart('removeNodes', $node);
	   	     $('#selected-node').data('node', null);
	   	     alert('该节点不允许删除！');
	   	     return;
 	      }
 	      _SubmitJson({
 	    	  url:__rootPath+'/sys/org/osRelInst/delInst.do',
 	    	  data:{
 	    		  id:$node[0].id,
 	    		  typeId:typeId,
 	    		  instId:instId
 	    	  },
 	    	  success:function(){
 	   	      	location.reload();
 	    	  }
 	      });
 	    });

 	    /**
 	    $('#btn-reset').on('click', function() {
 	      $('.orgchart').trigger('click');
 	    
 	      $('#node-type-panel').find('input').prop('checked', false);
 	    });**/

 	  });

 	})(jQuery);
 	
 	function addOrgNode(nodeVals){
 		var $chartContainer = $('#chart-container');
	     
	      var $node = $('#selected-node').data('node');
	      
	      var nodeType = $('input[name="node-type"]:checked');
	      if (nodeType.val() !== 'parent' && !$node) {
	        alert('请选择节点');
	        return;
	      }
	      if (!nodeType.length) {
	        alert('请选择节点类型');
	        return;
	      }
	      
	      if (nodeType.val() === 'parent') {
	        $chartContainer.orgchart('addParent', $chartContainer.find('.node:first'), { 'name': nodeVals[0].name, 'Id': nodeVals[0].id });
	      } else if (nodeType.val() === 'siblings') {
	        $chartContainer.orgchart('addSiblings', $node,
	          { 'siblings': nodeVals.map(function(item) { return { 'name': item.name, 'relationship': '110', 'Id': item.id }; })
	        });
	      } else {
	        var hasChild = $node.parent().attr('colspan') > 0 ? true : false;
	        if (!hasChild) {
	          var rel = nodeVals.length > 1 ? '110' : '100';
	          $chartContainer.orgchart('addChildren', $node, {
	              'children': nodeVals.map(function(item) {
	                return { 'name': item.name, 'relationship': rel, 'Id': item.id};
	              })
	            }, $.extend({}, $chartContainer.find('.orgchart').data('options'), { depth: 0 }));
	        } else {
	          $chartContainer.orgchart('addSiblings', $node.closest('tr').siblings('.nodes').find('.node:first'),
	            { 'siblings': nodeVals.map(function(item) { return { 'name': item.name, 'relationship': '110', 'Id': item.id}; })
	          });
	        }
	      }
 	}
 	
 </script>

  </body>
</html>