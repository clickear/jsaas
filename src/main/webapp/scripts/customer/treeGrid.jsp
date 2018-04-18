<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="/jsaas/scripts/mini/boot.js" type="text/javascript"></script>
<script type="text/javascript">
	function getData(){
		var grid=mini.get("treegrid1");
		console.info(grid.getData());
	}
	
	function addData(){
		var grid=mini.get("treegrid1");
		 var node = grid.getSelectedNode();
		 grid.addNode({sn:1}, "after", node);
	}
	
	function addSubData(){
		var grid=mini.get("treegrid1");
		var node = grid.getSelectedNode();
		grid.addNode({sn:1}, "add", node);
	}
	
	function moveUp(){
		move(true)
	}
	
	function move(isUp){
		var grid=mini.get("treegrid1");
		var curNode=grid.getSelectedNode();
		if(!curNode) {
			var msg="请选中一个节点进行移动!"
			alert(msg);
			return;
		}
		var node=getBrotherNode(isUp);
		if(!node){
			var msg=isUp?"已经到了最前节点!":"没有后续的节点!"
			alert(msg);
			return;
		}
		var action=isUp?"before":"after";
		grid.moveNode(curNode, node, action)
	}
	
	function getBrotherNode(isPre){
		var grid=mini.get("treegrid1");
		var node=grid.getSelectedNode();
		var parentNode=grid.getParentNode(node);
		var nodes=null;
		if(!parentNode){
			nodes=grid.getRootNode();
		}
		else{
			nodes=grid.getChildNodes(parentNode);
		}
		for(var i=0;i<nodes.length;i++){
			var tmp=nodes[i];
			if(tmp==node){
				if(isPre){
					if(i==0){
						return null;
					}
					else{
						return nodes[i-1];
					}
				}
				else{
					if(i==nodes.length-1){
						return null;
					}
					else{
						return nodes[i+1];
					}
				}
			}
		}
		return null;
		
	}
	
	function moveDown(){
		move(false)
	}
</script>
</head>
<body>
<a class="mini-button"   onclick="getData">获取数据</a>
<a class="mini-button"   onclick="addData">添加数据</a>
<a class="mini-button"   onclick="addSubData">添加子数据</a>
<a class="mini-button"   onclick="moveUp">上移</a>
<a class="mini-button"   onclick="moveDown">下移</a>
<div id="treegrid1"  style="width:600px;height:500px;"     
      allowCellEdit="true" allowCellSelect="true"
    url="data.txt"
    class="mini-treegrid" showtreeicon="false" resultastree="false"  expandonload="true"
    treeColumn="taskname" idField="UID" parentField="ParentTaskUID" 
>
    <div property="columns">
        <div type="indexcolumn"></div>
        <div name="taskname" field="Name" width="200">任务名称
        	<input property="editor" class="mini-textbox" style="width:100%;" />
        </div>
        <div field="Duration" width="100">工期
        	<input property="editor" class="mini-textbox" style="width:100%;" />
        </div>
      
    </div>
</div>
<script type="text/javascript">
	mini.parse();
	$(function(){
		var grid=mini.get("treegrid1");
		
		var json=[{
		    "UID": "1",
		    "Name": "项目范围规划A",
		    "Duration": 8,
		    "ParentTaskUID": "-1",
		},
		{
		    "UID": "2",
		    "Name": "确定项目范围1",
		    "Duration": 1,
		    "ParentTaskUID": "1"
		},
		{
		    "UID": "5",
		    "Name": "确定项目范围2",
		    "Duration": 1,
		    "ParentTaskUID": "1"
		},
		{
		    "UID": "3",
		    "Name": "项目范围规划B",
		    "Duration": 8,
		    "ParentTaskUID": "-1",
		},
		{
		    "UID": "4",
		    "Name": "确定项目范围3",
		    "Duration": 1,
		    "ParentTaskUID": "3"
		},
		{
		    "UID": "6",
		    "Name": "确定项目范围4",
		    "Duration": 1,
		    "ParentTaskUID": "3"
		}
		];
		
		var result=transToTree(json,"UID","ParentTaskUID");
			
		grid.setData(result);
	})
	
	function transToTree(json,id,pid){
		var jsonObj={};
		for(var i=0;i<json.length;i++){
			var o=json[i];
			var key=o[id];
			jsonObj[key]=o;
		}
		for(var key in jsonObj){
			var o=jsonObj[key];
			var obj=jsonObj[o[pid]];
			if(obj){
				obj.children =obj.children ||[];
				obj.children.push(o);
				o.deleteTag=1;
			}
		}
		for(var key in jsonObj){
			var o=jsonObj[key];
			if(o.deleteTag==1){
				delete jsonObj[key]
			}
		}
		var result=[];
		for(var key in jsonObj){
			var o=jsonObj[key];
			result.push(o);
		}
		return result;
	}
	
</script>
</body>
</html>