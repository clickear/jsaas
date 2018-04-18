/**
 * bpm实例里面流程图生成动画的js,页面需要传入
 * 陈茂昌
 * 20160413
 */

var index=0;//用来对生成动画的东西进行计数(用于时间停顿)
		var fnString="";//eval的内容
		 function addRoadPath(){
			 eval(fnString);
		} 
		
		//根据nodeId得到对应的pathId
		function findPathIdFromNodeId(nodeId){
			for(var i=0;i<list.length;i++){
				if(list[i].nodeId==nodeId){
					return list[i].pathId;
				}
			}
		}
		
		//根据给的节点sid判断有否子节点
		function haveChildren(divId,breakSign){
			var pathId;
			for(var j=0;j<list.length;j++){
				if(list[j].nodeId==divId){
					pathId=list[j].pathId;
				}
			}
			for(var i=0;i<list.length;i++){
				if(list[i].parentId==pathId){
					breakSign=1;
				}
			}
			return breakSign;
		}
		
		/*根据sid生成动画
		 * 
		 * */
		function cartoon(divId){
			var divPathId=findPathIdFromNodeId(divId);//找到这个pathId
			var breakSign=0;//如果没有子节点则跳出函数
			breakSign=haveChildren(divId,breakSign);
			if(breakSign==0){return;}
			index=index+1;
			for(var i=0;i<list.length;i++){//遍历所有节点
				if(list[i].parentId==divPathId){//如果节点的父节点pathId与本节点的path相等的话说明是本节点的子节点
					var drawId=divPathId+"_"+list[i].pathId;//绘制路径专属Id	
					var x= $("#"+divId+"").attr("xposition");//div元素对应的节点的位置,绘制人物的x位置
	        		var y= $("#"+divId+"").attr("yposition");//div元素对应的节点的位置,绘制人物的y位置
	        		//绘制人物div
	        		//$("#imgHref").after("<div id='"+drawId+"' style='position:absolute;left:"+x+";top:"+y+";'><img src='${ctxPath}/styles/images/bussinessMan.png' /></div>");
	        		var divStr="$('#imgHref').after('<div id=\\'"+drawId+"\\' style=\\\"position:absolute;left:"+x+";top:"+y+";\\\"><img src=\\'"+__rootPath+"/styles/images/bussinessMan.png\\' /></div>');";
	        		//绘制目标xy坐标
	        		var jqX=$("#"+list[i].nodeId+"").attr("xposition");
		        	var jqY=$("#"+list[i].nodeId+"").attr("yposition");
		        	//$("#"+drawId+"").animate({left:jqX,top:jqY},800);
					//绘制出一个隐藏的人物图片并固定在节点左顶点上
					var timeoutDraw="setTimeout(function(){$('#imgHref').after('<div id=\\'"+drawId+"\\' style=\\\"display:none;position:absolute;left:"+x+";top:"+y+";\\\"><img src=\\'"+__rootPath+"/styles/images/bussinessMan.png\\' /></div>');}, "+500*index+");";
					//将上述的图片进行移动动画
					 var timeoutMove='setTimeout(function(){$("#'+drawId+'").show().animate({left:"'+jqX+'",top:"'+jqY+'"},500);}, '+500*index+');';
					 //移除人物div
					 var timeoutRemove='setTimeout(function(){$("#'+drawId+'").remove();}, '+600*(index+1)+');';
					 fnString=fnString+timeoutDraw+timeoutMove+timeoutRemove;
    				cartoon(list[i].nodeId);
				}
			}
		}