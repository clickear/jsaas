<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
 <script type="text/javascript">
		var  __rootPath="/jsaas";
	</script>

<script src="/jsaas/scripts/mini/boot.js" type="text/javascript"></script>
<link href="css/querybuild.css" rel="stylesheet" type="text/css" />
<link href="css/query-builder.default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../jquery-1.11.3.js"></script>
<script type="text/javascript" src="js/query-builder.standalone.min.js"></script>
<script type="text/javascript" src="i18n/query-builder.zh-CN.js"></script>
<script src="/jsaas/scripts/common/baiduTemplate.js" type="text/javascript"></script>
<script src="/jsaas/scripts/share.js" type="text/javascript"></script>
<script type="text/javascript" src="../customer/mini-custom.js"></script>

</head>
<body>

<div id="builder"></div>

<div class="btn-group">
      <button id="btn-reset" class="btn btn-warning reset" data-target="basic">Reset</button>
      <button id="btn-set" class="btn btn-success set-json" data-target="basic">Set rules</button>
      <button id="btn-get" class="btn btn-primary parse-json" data-target="basic">Get rules</button>
</div>

<script>

function getUser(id,lebel){
	var obj={
		    id: id,
		    label: lebel,
		    type: 'string',
		    input:function(rule, name) {
		        var $container = rule.$el.find('.rule-value-container');
		        $container.on('click', '.icon-user', function(){
		        	_UserDialog({callback:function(data){
		        		var span=$('span[name$=_text]',$container);
		        		var input=$('input[name$=_val]',$container);
		        		var aryId=[];
		        		var aryName=[];
		        		for(var i=0;i<data.length;i++){
		        			var user=data[i];
		        			aryId.push(user.userId);
		        			aryName.push(user.fullname);
		        		}
		        		var userNames=aryName.join(",");
		        		var userIds=aryId.join(",");
		        		span.html(userNames);
		        		input.val(userIds);
		        		input.trigger('change');
		        	}})
		        });
		        return '<span   name="'+name+'_text"  /><input type="hidden" name="'+name +'_val" /> <a class="iconfont size-18 icon-user" ></a>';
		    },
		    valueGetter: function(rule) {
		    	  var txt=rule.$el.find('.rule-value-container [name$=_text]').text();
		    	  var val=rule.$el.find('.rule-value-container [name$=_val]').val();
		    	
		    	  var obj={id:val,text:txt};
		    	  return JSON.stringify(obj);
		    },
		    valueSetter: function(rule, value) {
		    	  var obj = mini.decode(value);
		          rule.$el.find('.rule-value-container [name$=_text]').html(obj.text);
		          rule.$el.find('.rule-value-container [name$=_val]').val(obj.id);
		    }
		  }
	
	return obj;
}

function getGroup(id,lebel){
	var obj={
		    id: id,
		    label: lebel,
		    type: 'string',
		    input:function(rule, name) {
		        var $container = rule.$el.find('.rule-value-container');
		        $container.on('click', '.icon-dep', function(){
		        	_DepDlg(false,function(data){
		        		var span=$('span[name$=_text]',$container);
		        		var input=$('input[name$=_val]',$container);
		        		var aryId=[];
		        		var aryName=[];
		        		for(var i=0;i<data.length;i++){
		        			var group=data[i];
		        			aryId.push(group.groupId);
		        			aryName.push(group.name);
		        		}
		        		var names=aryName.join(",");
		        		var ids=aryId.join(",");
		        		span.html(names);
		        		input.val(ids);
		        		input.trigger('change');
		        	})
		        });
		        return '<span   name="'+name+'_text"  /><input type="hidden" name="'+name +'_val" /> <a class="iconfont size-18 icon-dep" ></a>';
		    },
		    valueGetter: function(rule) {
		    	  var txt=rule.$el.find('.rule-value-container [name$=_text]').text();
		    	  var val=rule.$el.find('.rule-value-container [name$=_val]').val();
		    	
		    	  var obj={id:val,text:txt};
		    	  return JSON.stringify(obj);
		    },
		    valueSetter: function(rule, value) {
		    	  var obj = mini.decode(value);
		          rule.$el.find('.rule-value-container [name$=_text]').html(obj.text);
		          rule.$el.find('.rule-value-container [name$=_val]').val(obj.id);
		    }
		  }
	
	return obj;
}

function getFilters(){
	
	var ary=[
	{
	    id: 'name',
	    label: '名称',
	    type: 'string'
	  }, {
	    id: 'category',
	    label: '分类',
	    type: 'integer',
	    input: 'checkbox',
	    values: {
	      1: 'Books',
	      2: 'Movies',
	      3: 'Music',
	      4: 'Goodies'
	    },
	    operators: ['equal', 'not_equal', 'in', 'not_in', 'is_null', 'is_not_null']
	  }, {
	    id: 'in_stock',
	    label: 'In stock',
	    type: 'integer',
	    input: 'radio',
	    values: {
	      1: 'Yes',
	      0: 'No'
	    },
	    operators: ['equal']
	  }]
	var user=getGroup("price","价格");
	
	ary.push(user);
	
	return ary;
	
}



  
  var rules_plugins = {
		  condition: 'AND',
		  rules: [{
		    id: 'name',
		    operator: 'equal',
		    value: 'Mistic'
		  }, {
		    condition: 'OR',
		    rules: [{
		      id: 'category',
		      operator: 'in',
		      value: [1, 2]
		    }, {
		      id: 'in_stock',
		      operator: 'equal',
		      value: 0
		    }]
		  }]
		};
		var filters=getFilters();
		$('#builder').queryBuilder({
			lang_code:"zh-CN",
			filters:filters
		});
		
		
		$('#btn-reset').on('click', function() {
			  $('#builder').queryBuilder('reset');
			});

			$('#btn-set').on('click', function() {
				var rules={"condition":"AND","rules":[{"id":"price","field":"price","type":"string","operator":"equal","value":"{\"id\":\"2400000000131459,2400000000131023\",\"text\":\"何晓丽,刘凯军\"}"}],"valid":true};
				
				$('#builder').queryBuilder('setRules', rules);
			});

			$('#btn-get').on('click', function() {
			  var result = $('#builder').queryBuilder('getRules');
			  var str=JSON.stringify(result);
			  console.info(str);
		});
</script>

</body>
</html>