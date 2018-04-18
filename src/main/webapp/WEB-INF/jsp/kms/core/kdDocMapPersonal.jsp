<%-- 
    Document   : 知识仓库个人中心
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="redxun" uri="http://www.redxun.cn/gridFun"%>
<!DOCTYPE html>
<html>
<head>
<title>个人中心</title>
<%@include file="/commons/get.jsp"%>
<link href="${ctxPath}/styles/css/search.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/scripts/kms/shoufengqin/font-awesome.min.css" rel="stylesheet" />
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/scripts/kms/shoufengqin/shoufengqinstyle.css" rel="stylesheet" media="screen" type="text/css" />
<script src="${ctxPath}/scripts/kms/shoufengqin/index.js" type="text/javascript"></script>
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
</head>
<body style="min-width: 1600px;">

	<ul style="float: left; margin-left: 100px;" id="accordion" class="accordion">
		<li>
			<div class="link">
				<i class="fa"></i>个人资料<i class="fa fa-chevron-down"></i>
			</div>
			<ul class="submenu">
				<li><a href="${ctxPath}/kms/core/kdUser/personalGet.do?i=4&userId=${userId}">我的个人资料</a></li>
			</ul>
		</li>
		<li>
			<div class="link">
				<i class="fa"></i>我的知识文库<i class="fa fa-chevron-down"></i>
			</div>
			<ul class="submenu">
				<li><a href="${ctxPath}/kms/core/kdDoc/personal.do?i=4">我的知识文库</a></li>
				<li><a href="${ctxPath}/kms/core/kdQuestion/personal.do?i=4&tab=index&type=ALL">我的知识问答</a></li>
				<li><a href="${ctxPath}/kms/core/kdDoc/mapPersonal.do?i=4">我的知识地图</a></li>
				<li><a href="#" onclick="todo()">我的知识积分</a></li>
			</ul>
		</li>
	</ul>


	<div style="text-align: center; margin-top: 10px; margin-left: 350px;">
		<div class="selectBox" style="text-align: left;">
			<div class="selectNumberScreen">
				<div id="selectList" class="screenBox screenBackground">
					<dl class="listIndex" attr="Q_Type_S_EQ">
						<dt>我的知识地图：</dt>
						<dd>
							<a href="javascript:void(0)" values2="" values1="" attrval="myMap">我的地图</a> <a href="javascript:void(0)" values2="" values1="" attrval="myCmmt">我点评的</a> <a href="javascript:void(0)" values2="" values1="" attrval="myEdit">我可编辑的</a> <a href="javascript:void(0)" values2="" values1="" attrval="myFav">我收藏的</a>
						</dd>
					</dl>
					<dl class=" listIndex" attr="Q_status_S">
						<dt>文档状态：</dt>
						<dd>
							<a href="javascript:void(0)" values2="" values1="0" attrval="DRAFT">草稿</a> <a href="javascript:void(0)" values2="" values1="0" attrval="ISSUED">发布</a> <a href="javascript:void(0)" values2="" values1="0" attrval="abandon">废弃</a> <a href="javascript:void(0)" values2="" values1="0" attrval="pending">待审</a> <a href="javascript:void(0)" values2="" values1="0" attrval="archived">归档</a> <a href="javascript:void(0)" values2="" values1="0" attrval="NO">过期</a> <a href="javascript:void(0)" values2="" values1="0" attrval="overdue">驳回</a>
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
	<div style="margin-left: 350px;">
		<div id="border" style="margin-left: 15%; display: none; border-bottom: 2px solid #ccc; width: 795px;"></div>
	</div>
	<div style="margin-left: 350px;">
		<div id="additionShow" class="closeAddition" style="height: 20px; width: 60px; margin-left: 40%; background-color: #ccc; vertical-align: middle; text-align: center; color: #fff; cursor: pointer;">收起条件</div>
	</div>

	<div style="height: 50px;"></div>
	<div style="margin-left: 400px; margin-right: 150px; height: 100%;">
		<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: auto;" allowResize="false" url="${ctxPath}/kms/core/kdDoc/getMapByRightAndPath.do" idField="docId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons" onrowdblclick="showDoc">
			<div property="columns">
				<div type="checkcolumn" width="20"></div>
				<div field="sysTree" width="120" headerAlign="center" allowSort="true">所属分类</div>
				<div field="subject" width="120" headerAlign="center" allowSort="true">地图标题</div>
				<div field="issuedTime" width="120" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">发布日期</div>
				<div field="status" width="120" headerAlign="center" allowSort="true">地图状态</div>
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
			var s = "";
			if (field == 'status') {
				if (value == "DRAFT") {
					s = "<span style='color:orange'>草稿</span>";
				} else if (value == "ISSUED") {
					s = "<span style='color:green'>发布</span>";
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
				s = '<a href="${ctxPath}/kms/core/kdDoc/mapShow.do?docId=' + e.record.docId + '" target="_blank">' + value + '&nbsp;</a>';
				e.cellHtml = s;
				//window.open('${ctxPath}/kms/core/kdDoc/show.do?docId=' + pkId, '_blank');
			}
		});

		var dlNum = $("#selectList").find("dl");
		for (var i = 0; i < dlNum.length; i++) {
			$(".hasBeenSelected .clearList").append("<div class=\"selectedInfor selectedShow\" style=\"display:none\"><span></span><label></label><em></em></div>");
		}

		//点击选择框
		$(".listIndex a ").on("click", function() {
			var text = $(this).text();
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
			selectedShow.eq(index).find("label").attr("value", $(this).attr("attrval"));
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
			grid.setUrl("${ctxPath}/kms/core/kdDoc/mapPersonalSearchDoc.do?catId=" + catId);
			formData.filter = mini.encode(data);
			formData.pageIndex = grid.getPageIndex();
			formData.pageSize = grid.getPageSize();
			formData.sortField = grid.getSortField();
			formData.sortOrder = grid.getSortOrder();
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
			var data = new Array();
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
			var catId = "${param['treeId']}";
			grid.setUrl("${ctxPath}/kms/core/kdDoc/mapPersonalSearchDoc.do?treeId=" + catId);
			formData.filter = mini.encode(data);
			formData.pageIndex = grid.getPageIndex();
			formData.pageSize = grid.getPageSize();
			formData.sortField = grid.getSortField();
			formData.sortOrder = grid.getSortOrder();
			grid.load(formData);

		});

		//点击清除所有查询
		$(".eliminateCriteria").on("click", function() {
			$(".selectedShow").hide();
			$(this).hide("fast");
			$(".listIndex a ").removeClass("selected");
			var data = new Array();
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
			var catId = "${param['treeId']}";
			grid.setUrl("${ctxPath}/kms/core/kdDoc/mapPersonalSearchDoc.do?treeId=" + catId);
			formData.filter = mini.encode(data);
			formData.pageIndex = grid.getPageIndex();
			formData.pageSize = grid.getPageSize();
			formData.sortField = grid.getSortField();
			formData.sortOrder = grid.getSortOrder();
			grid.load(formData);
		});

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

		function showDoc(e) {
			var record = e.record;
			var pkId = record.pkId;
			window.open('${ctxPath}/kms/core/kdDoc/mapShow.do?docId=' + pkId, '_blank');
		}

		function todo() {
			alert("敬请期待！");
		}
	</script>
	<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
</body>
</html>