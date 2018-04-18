<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>知识管理系统主页</title>
<%@include file="/commons/list.jsp"%>
<link href="${ctxPath}/styles/css/search.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/kmsFindCss.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />

</head>
<body>
	<div class="container">
	  <div class="side">
	    <div id="leftMenu" class="menu">
	      
	    </div>
	    <div class="linkbtn"> <a href="#" onclick="newQue()" class="fl" style="margin-left: 25%;">我要提问</a></div>
	  </div>
	
	<div style="margin-left: 350px;"><a>首页</a>&gt;<a id="moreIndex">知识问答</a>&gt;${name}</div>
	<div style="margin-left: 350px;">
	<div class="selectBox">
		<div class="selectNumberScreen">
			<div id="selectList" class="screenBox screenBackground">
				<dl class="listIndex" attr="Q_status_S_EQ">
					<dt>状态：</dt>
					<dd>
						<a href="javascript:void(0)" values2="" values1="" attrval="SOLVED">已解决</a> <a href="javascript:void(0)" values2="" values1="" attrval="UNSOLVED">待解决</a> <!-- <span class=more><label>更多</label><em class=open></em></span> -->
					</dd>
				</dl>
				<dl class="listIndex" attr="Q_rewardScore_I">
					<dt>悬赏范围：</dt>
					<dd>
						<a href="javascript:void(0)" values2="15" values1="0" attrval="multiply">0-15</a> <a href="javascript:void(0)" values2="50" values1="20" attrval="multiply">20-50</a> <a href="javascript:void(0)" values2="100" values1="80" attrval="multiply">80-100</a>
					</dd>
				</dl>
				<dl class=" listIndex" attr="Q_fileIds_S">
					<dt>附件：</dt>
					<dd>
						<a href="javascript:void(0)" values2="" values1="0" attrval="yes">有</a> <a href="javascript:void(0)" values2="" values1="0" attrval="no">无</a>
					</dd>
				</dl>
				<dl class=" listIndex" attr="Q_replyType_S_EQ">
					<dt>回复者类型：</dt>
					<dd>
						<a href="javascript:void(0)" values2="" values1="" attrval="ALL">所有人</a> <a href="javascript:void(0)" values2="" values1="" attrval="EXPERT">专家个人</a><a href="javascript:void(0)" values2="" values1="" attrval="DOMAIN_EXPERT">领域专家</a>
					</dd>
				</dl>
			</div>
			<div class="hasBeenSelected">
				<dl>
					<dt>您已选择：</dt>
					<dd style="DISPLAY: none" class="clearDd">
						<div class="clearList"></div>
						<div style="DISPLAY: none" class="eliminateCriteria">清除筛选条件</div>
					</dd>
				</dl>
			</div>
		</div>
	</div>
	</div>

	<div id="border" style="display: none; border-bottom: 2px solid #6495ED; width: 795px; margin: 0 auto;"></div>
	<div>
		<div id="additionShow" class="closeAddition" style="margin-left:350px;height: 20px; width: 60px; background-color: #ccc; margin: -2px auto 0 auto; vertical-align: middle; text-align: center; color: #fff; cursor: pointer;">收起条件</div>
	</div>

	<div style="height: 50px;"></div>
	<div style="margin-left: 350px;height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width:1000px;margin-left: 50px;" height="auto" allowResize="false" url="${ctxPath}/kms/core/kdQuestion/getAllQuestion.do" idField="queId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" onrowdblclick="checkQuestion">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div type="indexcolumn" width="20">序号</div>
				<div field="subject" width="150" headerAlign="left" allowSort="true" align="left">问题标题</div>
				<div field="tags" width="80" headerAlign="left" allowSort="true" align="left">标签</div>
				<div field="rewardScore" width="50" headerAlign="center" allowSort="true" align="center">悬赏货币</div>
				<div field="replyType" width="50" headerAlign="center" allowSort="true" align="center">回复者类型</div>
				<div field="status" width="50" headerAlign="center" allowSort="true" align="center">问题状态</div>
				<div field="replyCounts" width="40" headerAlign="center" allowSort="true" align="center">回复数</div>
			</div>
		</div>
	</div>
	</div>



		<script type="text/javascript">
		//左边目录
		var leftMenu = '${leftMenu}';
		var menusArr = mini.decode(leftMenu);
		$(function() {
			var lMenus = "";
			for (var i = 0; i < menusArr.length; i++) {
				var secondMenus = menusArr[i].lowerMenu;
				if(secondMenus.length == 0){
					lMenus = lMenus + "<div><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />"+ menusArr[i].name +"</p>";
				}else{
					lMenus = lMenus + "<div><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />"+ menusArr[i].name +"</p><div class='secondmenu'>";
				}
				for (var j = 0; j < secondMenus.length; j++) {
					var thirdMenus = secondMenus[j].lowerMenu;
					lMenus = lMenus + "<div class='cate cat' id='" + secondMenus[j].id + "'>" + secondMenus[j].name + "</div><ul>";
					for (var k = 0; k < thirdMenus.length; k++) {
						lMenus = lMenus + "<li class='cat' id='" + thirdMenus[k].id + "'>" + thirdMenus[k].name + "</li>";
					}
					lMenus = lMenus + "</ul>";
				}
				lMenus = lMenus + "</div></div>";
			}
			$('#leftMenu').append(lMenus);
			$(".cat").on("click", function() {
				window.location.href = "${ctxPath}/kms/core/kdQuestion/find.do?i=3&catId=" + $(this).attr("id");
			});
		});
	</script>

	<script>
	mini.parse();
	var grid=mini.get("datagrid1");
	grid.load();
	
	
	grid.on("drawcell", function(e) {
		field = e.field, value = e.value;
		var s;
		if (field == 'status') {
			if (value == "UNSOLVED") {
				s = "<span style='color:orange'>待解决</span>";
			} else {
				s = "<span style='color:green'>已解决</span>";
			}
			e.cellHtml = s;
		}
		
		if (field == 'replyType') {
			if (value == "DOMAIN_EXPERT") {
				s = "领域专家";
			} else if(value == 'EXPERT'){
				s = "专家";
			}else{
				s="所有人";
			}
			e.cellHtml = s;
		}
		
		 if (field == 'subject') {
			s='<a href="${ctxPath}/kms/core/kdQuestion/get.do?i=3&pkId='+e.record.queId+'">'+value+'&nbsp;</a>';
			e.cellHtml = s;
		}
	});
	
	function newQue() {
		window.open("${ctxPath}/kms/core/kdQuestion/edit.do?i=3");
	}
	
	$(function(){
		var catId="${param['catId']}";
		if(catId==''){
			grid.setUrl('${ctxPath}/kms/core/kdQuestion/getAllQuestion.do?i=3');
			grid.load({});
		}
		else{
			grid.setUrl('${ctxPath}/kms/core/kdQuestion/getByPath.do');
			grid.load({treeId:catId,i:3});
		}
		
	});
	
    var dlNum  =$("#selectList").find("dl");
    for (var i = 0; i < dlNum.length; i++) {
        $(".hasBeenSelected .clearList").append("<div class=\"selectedInfor selectedShow\" style=\"display:none\"><span></span><label></label><em></em></div>");
    }
    
    $("a").css("cursor","pointer");
    $(".cat").click(function(){
		window.location.href="${ctxPath}/kms/core/kdQuestion/find.do?i=3&catId="+$(this).attr("id");
	});
    
    $("#moreIndex").click(function(){
		window.location.href="${ctxPath}/kms/core/kdQuestion/find.do?i=3";
	});
    
    
    
    var refresh = "true";
    
    $(".listIndex a ").on("click",function(){
        var text =$(this).text();
        var selectedShow = $(".selectedShow");
        var textTypeIndex =$(this).parents("dl").index();
        var textType =$(this).parent("dd").siblings("dt").text();
        index = textTypeIndex -(2);
        $(".clearDd").show();
        $(".selectedShow").eq(index).show();
        $(this).addClass("selected").siblings().removeClass("selected");
        selectedShow.eq(index).find("span").text(textType);
        selectedShow.eq(index).find("span").attr("key",$(this).parents("dl").attr("attr"));
        selectedShow.eq(index).find("label").text(text);
        selectedShow.eq(index).find("label").attr("value",$(this).attr("attrval"));
        if($(this).attr("attrval")=='multiply'){
        	 selectedShow.eq(index).find("label").attr("values1",$(this).attr("values1"));
        	 selectedShow.eq(index).find("label").attr("values2",$(this).attr("values2"));
        }
        if($(this).attr("attrval")=='yes'||$(this).attr("attrval")=='no'){
       	 	selectedShow.eq(index).find("label").attr("values1",$(this).attr("values1"));
       }
        var show = $(".selectedShow").length - $(".selectedShow:hidden").length;
        if (show > 1) {
            $(".eliminateCriteria").show();
        }
       var data=new Array();
        $(".clearDd").find(".selectedInfor").each(function(){
    	   if($(this).css('display')!='none'){
    		   var key;
    	   	   var value;
    	   	   var values1;
    	   	   var values2;
    	   	  
    	   	   key=$(this).find("span").attr("key");
    	   	   value=$(this).find("label").attr("value");
    	   	   if(value=='multiply'){
    	   		   var dataitem1={};
    	   		var dataitem2={};
    	   		   values1=$(this).find("label").attr("values1");
    	   		   values2=$(this).find("label").attr("values2");
    	   		   dataitem1['name']=key+'_GE';
    	   			dataitem1['value']=values1;
    	   			dataitem2['name']=key+'_LE';
    	   		   dataitem2['value']=values2;
    	   		data.push(dataitem1);
    	   		data.push(dataitem2);
    	   	   }else if(value=='yes'||value=='no'){
    	   		   var dataitem={};
    	   			values1=$(this).find("label").attr("values1");
    	   			if(value=='yes'){
    	    	   		   dataitem['name']=key+'_GT';
    	    	   			dataitem['value']=values1;
    	   			}
    	   			else if(value=='no'){
   	    	   		   dataitem['name']=key+'_EQ';
	    	   			dataitem['value']=values1;
    	   			}
    	   			data.push(dataitem);
    	   	   }
    	   	   else{
    	   		 	var dataitem={};
    	   		 	if(key!=null){
    	   		   dataitem['name']=key;
    	   		   dataitem['value']=value;
    	   		   data.push(dataitem);
    	   	   }
    	   	   }
    	   }
       }); 
       var catId="${param['catId']}";
       var formData={}; 
       grid.setUrl("${ctxPath}/kms/core/kdQuestion/getByAddition.do?i=3&catId="+catId);
       formData.filter = mini.encode(data);
       formData.pageIndex = grid.getPageIndex();
	   formData.pageSize = grid.getPageSize();
	   formData.sortField = grid.getSortField();
	   formData.sortOrder = grid.getSortOrder();
	   //alert(mini.encode(formData));
	   grid.load(formData);
    });
    
    $(".selectedShow em").on("click",function(){
        $(this).parents(".selectedShow").hide();
        var type=$(this).parent(".selectedInfor").find("span").attr("key");
        $(".listIndex[attr="+type+"]").find("a").removeClass("selected");
        
        if($(".listIndex .selected").length < 2){
            $(".eliminateCriteria").hide();
        }
        
         if($(".listIndex .selected").length < 1){
        	var catId="${param['catId']}";
    		if(catId==''){
    			grid.setUrl('${ctxPath}/kms/core/kdQuestion/getAllQuestion.do?i=3');
    			grid.load({});
    		}
    		else{
    			grid.setUrl('${ctxPath}/kms/core/kdQuestion/getByPath.do');
    			grid.load({treeId:catId,i:3});
    		}
        } 
        
    });
    
    $(".eliminateCriteria").on("click",function(){
        $(".selectedShow").hide();
        $(this).hide("fast");
        $(".listIndex a ").removeClass("selected");
        var catId="${param['catId']}";
		if(catId==''){
			grid.setUrl('${ctxPath}/kms/core/kdQuestion/getAllQuestion.do?i=3');
			grid.load({});
		}
		else{
			grid.setUrl('${ctxPath}/kms/core/kdQuestion/getByPath.do');
			grid.load({treeId:catId,i:3});
		}
    }); 
</script>

	<script type="text/javascript">
	$("#additionShow").click(function(){
		var cls=$(this).attr('class');
		if(cls=='closeAddition'){
			$(this).removeClass(cls);
			$(this).addClass("openAddition");
			$(".selectNumberScreen").hide("fast");
			$(this).html("打开条件");
			$("#border").css('display','');
		}
		else if(cls=='openAddition'){
			$(this).removeClass(cls);
			$(this).addClass("closeAddition");
			$(".selectNumberScreen").show("fast");
			$(this).html("收起条件");
			$("#border").css('display','none');
		}
	});
	
	function checkQuestion(e){
			var record = e.record;
			var pkId = record.pkId;
			window.open('${ctxPath}/kms/core/kdQuestion/get.do?i=3&pkId='+pkId, '_blank');
	}
</script>

<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>

</body>
</html>