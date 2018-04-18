<%-- 
    Document   : [KdDoc]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="rx" uri="http://www.redxun.cn/detailFun"%>
<%@taglib prefix="rxc" uri="http://www.redxun.cn/commonFun"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>查询页面</title>
<%@include file="/commons/dynamic.jspf" %>
<link href="${ctxPath}/styles/css/search.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/boot.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/share.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
</head>

<body style="min-width: 1600px;">
	<div class="container">
	  <div class="side">
	    <div id="leftMenu" class="menu">
	      
	    </div>
	    <div class="linkbtn"> <a href="#" onclick="newDoc()" class="fl" style="margin-left: 25%;">我要分享</a></div>
	  </div>
	<div style="margin-left: 350px;">
		<a style="color: black;">首页</a>&gt;<a style="color: black;" href="${ctxPath}/kms/core/kdDoc/mapIndex.do?i=2">知识地图</a>&gt;${name}
	</div>
	<div style="text-align: center; margin-left: 350px;">
		<div class="selectBox" style="text-align: left;">
			<div class="selectNumberScreen">
				<div id="selectList" class="screenBox screenBackground">
					<dl class="listIndex" attr="Q_subject_S_EQ">
						<dt>地图标题：</dt>
						<dd>
							<input id="searchSubject" style=""></input> <a name="search">搜索</a>
						</dd>
					</dl>
					<dl class="listIndex" attr="Q_authorType_S_EQ">
						<dt>作者类型：</dt>
						<dd>
							<a href="javascript:void(0)" values2="" values1="" attrval="INNER">内部</a> <a href="javascript:void(0)" values2="" values1="" attrval="OUTER">外部</a>
						</dd>
					</dl>
					<!-- <dl class=" listIndex" attr="Q_attFileids_S">
						<dt>是否附件：</dt>
						<dd>
							<a href="javascript:void(0)" values2="" values1="0" attrval="YES">有</a> <a href="javascript:void(0)" values2="" values1="0" attrval="NO">无</a>
						</dd>
					</dl> -->
					<!-- <dl class=" listIndex" attr="Q_isEssence_S_EQ">
						<dt>是否精华：</dt>
						<dd>
							<a href="javascript:void(0)" values2="" values1="" attrval="YES">是</a> <a href="javascript:void(0)" values2="" values1="" attrval="NO">否</a>
						</dd>
					</dl> -->
				</div>
				<div class="hasBeenSelected">
					<dl>
						<dt>您已选择：</dt>
						<dd style="DISPLAY: none" class=clearDd>
							<div class=clearList></div>
							<div style="DISPLAY: none" class="eliminateCriteria">清除筛选条件</div>
						</dd>
					</dl>
				</div>
			</div>
		</div>
	</div>
	
	<div style="margin-left: 350px;">
		<div id="border" style="margin-left: 50px; display: none; border-bottom: 2px solid #ccc; width: 1000px;"></div>
	</div>
	<div style="margin-left: 350px;">
		<div id="additionShow" class="closeAddition" style="height: 20px; width: 60px; margin-left: 40%; background-color: #ccc; vertical-align: middle; text-align: center; color: #fff; cursor: pointer;">收起条件</div>
	</div>

	<div style="height: 50px;"></div>
	<div style="margin-left: 400px; margin-right: 150px; height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 1000px; height: auto;" allowResize="false" url="" idField="docId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons" onrowdblclick="dbShowDoc">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="sysTree" width="120" headerAlign="center" allowSort="true">所属分类</div>
				<div field="subject" width="120" headerAlign="center" allowSort="true">地图标题</div>
				<div field="author" width="120" headerAlign="center" allowSort="true">作者</div>
				<div field="authorType" width="120" headerAlign="center" allowSort="true">作者类型</div>
				<div field="summary" width="120" headerAlign="center" allowSort="true">摘要</div>
				<div field="status" width="120" headerAlign="center" allowSort="true">地图状态</div>
				<div field="issuedTime" width="120" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">发布日期</div>
			</div>
		</div>
	</div>
	</div>


	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();

		//表格内容的显示
		grid.on("drawcell", function(e) {
			field = e.field, value = e.value;
			var s = null;
			if (field == 'status') {
				if (value == "DRAFT") {
					s = "草稿";
				} else if (value == "ISSUED") {
					s = "发布";
				}
				e.cellHtml = s;
			}

			if (field == 'authorType') {
				if (value == "INNER") {
					s = "内部";
				} else if (value == 'OUTER') {
					s = "外部";
				}
				e.cellHtml = s;
			}
			if (field == 'sysTree') {
				s = value.name;
				e.cellHtml = s;
			}

			if (field == 'subject') {
				s = '<a href="javascript:showDoc(\''+ e.record.docId +'\')">' + value + '&nbsp;</a>';
				e.cellHtml = s;
				//window.open('${ctxPath}/kms/core/kdDoc/show.do?docId=' + pkId, '_blank');
			}
		});

		//搜索框的显示
		var dlNum = $("#selectList").find("dl");
		for (var i = 0; i < dlNum.length; i++) {
			$(".hasBeenSelected .clearList").append("<div class=\"selectedInfor selectedShow\" style=\"display:none\"><span></span><label></label><em></em></div>");
		}

		//根据点击左边目录显示不同的分类
		$(function() {
			var catId = "${param['treeId']}";
			//alert(catId);
			//console.log(catId);
			if (catId == '') {
				grid.setUrl('${ctxPath}/kms/core/kdDoc/getMapByRightAndPath.do');
				grid.load({i:2});
			} else {
				grid.setUrl('${ctxPath}/kms/core/kdDoc/getMapByRightAndPath.do');
				grid.load({
					treeId : catId,
					i:2
				});
			}

		});
		//构造左边目录
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
				window.location.href = "${ctxPath}/kms/core/kdDoc/mapSearch.do?i=2&treeId=" + $(this).attr("id");
			});
		});

		//var refresh = "true";

		//点击选择框
		$(".listIndex a ").on("click", function() {
			var text=""; 
			if ($(this).attr('name') == 'search') {
				text = $('#searchSubject').val();
			} else {
				text = $(this).text();
			}
			//alert(text);
			var selectedShow = $(".selectedShow");
			var textTypeIndex = $(this).parents("dl").index();
			var textType = $(this).parent("dd").siblings("dt").text();
			index = textTypeIndex - (2);
			$(".clearDd").show();
			$(".selectedShow").eq(index).show();
			$(this).addClass("selected").siblings().removeClass("selected");
			selectedShow.eq(index).find("span").text(textType);
			selectedShow.eq(index).find("span").attr("key", $(this).parents("dl").attr("attr"));
			selectedShow.eq(index).find("label").text(text);
			if ($(this).attr('name') == 'search') {
				selectedShow.eq(index).find("label").attr("value", $('#searchSubject').val());
			} else {
				selectedShow.eq(index).find("label").attr("value", $(this).attr("attrval"));
			}
			/* if ($(this).attr("attrval") == 'yes' || $(this).attr("attrval") == 'no') {
				selectedShow.eq(index).find("label").attr("values1", $(this).attr("values1"));
			} */
			var show = $(".selectedShow").length - $(".selectedShow:hidden").length;
			if (show > 1) {
				$(".eliminateCriteria").show();
			}
			var data = new Array();
			$(".clearDd").find(".selectedInfor").each(function() {
				if ($(this).css('display') != 'none') {
					var key;
					var value;
					var values1;

					key = $(this).find("span").attr("key");
					value = $(this).find("label").attr("value");
					if (value == 'YES' || value == 'NO') {
						var dataitem = {};
						values1 = $(this).find("label").attr("values1");
						if (value == 'yes') {
							dataitem['name'] = key + '_GT';
							dataitem['value'] = values1;
						} else if (value == 'no') {
							dataitem['name'] = key + '_EQ';
							dataitem['value'] = values1;
						}
						data.push(dataitem);
					} else {
						var dataitem = {};
						if (key != null) {
							dataitem['name'] = key;
							dataitem['value'] = value;
							data.push(dataitem);
						}
					}
				}
			});
			var formData = {};
			var catId = "${param['catId']}";
			grid.setUrl("${ctxPath}/kms/core/kdDoc/getMapByAddition.do?i=2&treeId=" + catId);
			formData.filter = mini.encode(data);
			formData.pageIndex = grid.getPageIndex();
			formData.pageSize = grid.getPageSize();
			formData.sortField = grid.getSortField();
			formData.sortOrder = grid.getSortOrder();
			//alert(mini.encode(formData));
			//console.log(grid);
			grid.load(formData);
		});

		//点击已选搜索的x关闭按钮
		$(".selectedShow em").on("click", function() {
			$(this).parents(".selectedShow").hide();
			var type = $(this).parent(".selectedInfor").find("span").attr("key");
			$(".listIndex[attr=" + type + "]").find("a").removeClass("selected");

			if ($(".listIndex .selected").length < 2) {
				$(".eliminateCriteria").hide();
			}
			 if($(".listIndex .selected").length < 1){
		        	var catId="${param['treeId']}";
		    		if(catId==''){
		    			grid.setUrl('${ctxPath}/kms/core/kdDoc/getMapByRightAndPath.do');
		    			grid.load({i:2});
		    		}
		    		else{
		    			grid.setUrl('${ctxPath}/kms/core/kdDoc/getMapByRightAndPath.do');
		    			grid.load({treeId:catId,i:2});
		    		}
		        } 
			/* var data = new Array();
			$(".clearDd").find(".selectedInfor").each(function() {
				if ($(this).css('display') != 'none') {
					var key;
					var value;
					var values1;

					key = $(this).find("span").attr("key");
					value = $(this).find("label").attr("value");
					if (value == 'yes' || value == 'no') {
						var dataitem = {};
						values1 = $(this).find("label").attr("values1");
						if (value == 'yes') {
							dataitem['name'] = key + '_GT';
							dataitem['value'] = values1;
						} else if (value == 'no') {
							dataitem['name'] = key + '_EQ';
							dataitem['value'] = values1;
						}
						data.push(dataitem);
					} else {
						var dataitem = {};
						if (key != null) {
							dataitem['name'] = key;
							dataitem['value'] = value;
							data.push(dataitem);
						}
					}
				}
			});
			var formData = {};
			var catId = "${param['catId']}";
			grid.setUrl("${ctxPath}/kms/core/kdDoc/getMapByRightAndPath.do?treeId=" + catId);
			formData.filter = mini.encode(data);
			formData.pageIndex = grid.getPageIndex();
			formData.pageSize = grid.getPageSize();
			formData.sortField = grid.getSortField();
			formData.sortOrder = grid.getSortOrder();
			grid.load(formData); */

		});

		//点击清除所有查询
		$(".eliminateCriteria").on("click", function() {
			$(".selectedShow").hide();
			$(this).hide("fast");
			$(".listIndex a ").removeClass("selected");
			var catId="${param['treeId']}";
			if(catId==''){
				grid.setUrl('${ctxPath}/kms/core/kdDoc/getMapByRightAndPath.do');
				grid.load({i:2});
			}
			else{
				grid.setUrl('${ctxPath}/kms/core/kdDoc/getMapByRightAndPath.do');
				grid.load({treeId:catId,i:2});
			}
			/* var data = new Array();
			$(".clearDd").find(".selectedInfor").each(function() {
				if ($(this).css('display') != 'none') {
					var key;
					var value;
					var values1;

					key = $(this).find("span").attr("key");
					value = $(this).find("label").attr("value");
					if (value == 'yes' || value == 'no') {
						var dataitem = {};
						values1 = $(this).find("label").attr("values1");
						if (value == 'yes') {
							dataitem['name'] = key + '_GT';
							dataitem['value'] = values1;
						} else if (value == 'no') {
							dataitem['name'] = key + '_EQ';
							dataitem['value'] = values1;
						}
						data.push(dataitem);
					} else {
						var dataitem = {};
						if (key != null) {
							dataitem['name'] = key;
							dataitem['value'] = value;
							data.push(dataitem);
						}
					}
				}
			});
			var formData = {};
			var catId = "${param['catId']}";
			formData.filter = mini.encode(data);
			formData.pageIndex = grid.getPageIndex();
			formData.pageSize = grid.getPageSize();
			formData.sortField = grid.getSortField();
			formData.sortOrder = grid.getSortOrder();
			grid.setUrl("${ctxPath}/kms/core/kdDoc/getMapByRightAndPath.do");
			grid.load(formData); */
		});

		//打开和关闭搜索框
		$("#additionShow").click(function() {
			var cls = $(this).attr('class');
			if (cls == 'closeAddition') {
				$(this).removeClass(cls);
				$(this).addClass("openAddition");
				$(".selectNumberScreen").hide("fast");
				$(this).html("打开条件");
				$("#border").css('display', '');
			} else if (cls == 'openAddition') {
				$(this).removeClass(cls);
				$(this).addClass("closeAddition");
				$(".selectNumberScreen").show("fast");
				$(this).html("收起条件");
				$("#border").css('display', 'none');
			}
		});
		
		//新增地图
		function newDoc() {
			window.open("${ctxPath}/kms/core/kdDoc/mapNew1.do");
		}

		//双击表格显示内容
		function dbShowDoc(e) {
			var record = e.record;
			var pkId = record.pkId;
			window.open('${ctxPath}/kms/core/kdDoc/mapShow.do?i=2&docId=' + pkId, '_blank');
		}
		//点击标题跳转
		function showDoc(docId) {
			var pkId = docId;
			window.open('${ctxPath}/kms/core/kdDoc/mapShow.do?i=2&docId=' + pkId, '_blank');
		}
	</script>
<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
</body>
</html>