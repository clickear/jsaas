<%-- 
    Document   : [KdDoc]明细页
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>查询页面</title>
<%@include file="/commons/get.jsp"%>
<link href="${ctxPath}/styles/css/search.css" rel="stylesheet" type="text/css" />
<script src="${ctxPath}/scripts/jquery/plugins/jQuery.download.js" type="text/javascript"></script>
<link href="${ctxPath}/styles/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctxPath}/styles/css/zszk.css" rel="stylesheet" type="text/css" />
</head>

<body style="min-width: 1600px;">
	<div class="container">
		<div class="side">
			<div id="leftMenu" class="menu"></div>
			<div class="linkbtn">
				<a href="#" class="fl" style="margin-left: 25%;" onclick="newDoc()">我要分享</a>
			</div>
		</div>

		<div class="main">
			<div style="margin-top:10px;margin-left:10px;">
				<a style="color: black;">首页</a>&gt;<a style="color: black;" href="${ctxPath}/kms/core/kdDoc/index.do">知识仓库</a>&gt;${name}
			</div>
			<div style="text-align: center;margin-top:10px;">
				<div class="selectBox" style="text-align: left;">
					<div class="selectNumberScreen">
						<div id="selectList" class="screenBox screenBackground">
							<dl class="listIndex" attr="Q_subject_S_EQ">
								<dt>文章标题：</dt>
								<dd>
									<input id="searchSubject" style="margin-top: 5px;"></input> <a href="javascript:void(0)" name="search">搜索</a>
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
							<dl class=" listIndex" attr="Q_isEssence_S_EQ">
								<dt>是否精华：</dt>
								<dd>
									<a href="javascript:void(0)" values2="" values1="" attrval="YES">是</a> <a href="javascript:void(0)" values2="" values1="" attrval="NO">否</a>
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
			<div style="">
				<div id="border" style="margin-left: 50px; display: none; border-bottom: 2px solid #ccc; width: 1000px;"></div>
			</div>
			<div style="">
				<div id="additionShow" class="closeAddition" style="height: 20px; width: 60px; margin-left: 40%; background-color: #ccc; vertical-align: middle; text-align: center; color: #fff; cursor: pointer;">收起条件</div>
			</div>

			<div style="height: 50px;"></div>
			<div style="margin-left: 50px; margin-right: 150px; height: 100%;">
				<div id="datagrid1" class="mini-datagrid" style="width: 1000px; height: 100%;" allowResize="false" url="${ctxPath}/kms/core/kdDoc/getAllDoc.do" idField="docId" multiSelect="true" showColumnsMenu="true" sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons" onrowdblclick="dbShowDoc">
					<div property="columns">
						<div type="checkcolumn" width="20"></div>
						<div field="sysTree" width="50" headerAlign="center" allowSort="true">所属分类</div>
						<div field="subject" width="120" headerAlign="center" allowSort="true">文档标题</div>
						<div field="author" width="40" headerAlign="center" allowSort="true">作者</div>
						<div field="summary" width="120" headerAlign="center" allowSort="true">摘要</div>
						<div field="status" width="50" headerAlign="center" allowSort="true">文档状态</div>
						<div field="issuedTime" width="80" headerAlign="center" allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">发布日期</div>
					</div>
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
				s = '<a href="#" onclick="showDoc(\'' + e.record.docId + '\')">' + value + '&nbsp;</a>';
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
			var catId = "${param['catId']}";
			if (catId == '') {
				grid.setUrl('${ctxPath}/kms/core/kdDoc/getAllDoc.do');
				grid.load({});
			} else {
				grid.setUrl('${ctxPath}/kms/core/kdDoc/getByPath.do');
				grid.load({
					catId : catId
				});
			}

		});
		//左边目录
		var leftMenu = '${leftMenu}';
		var menusArr = mini.decode(leftMenu);
		$(function() {
			var lMenus = "";
			//lMenus = lMenus + "<div class='sidebar'><div id='allSearch' class='sidebar_top sidebar_top_tc'>分类导航</div><div class='sidebar_con'><dl class='sidebar_item'>";
			for (var i = 0; i < menusArr.length; i++) {
				var secondMenus = menusArr[i].lowerMenu;
				if (secondMenus.length == 0) {
					lMenus = lMenus + "<div ><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />" + menusArr[i].name + "</p>";
				} else {
					lMenus = lMenus + "<div ><p class='cat' id='" + menusArr[i].id + "'><img src='${ctxPath}/styles/images/knowl/menu1.png' />" + menusArr[i].name + "</p><div class='secondmenu'>";
				}
				for (var j = 0; j < secondMenus.length; j++) {
					var thirdMenus = secondMenus[j].lowerMenu;
					lMenus = lMenus + "<div class='cate cat' id='" + secondMenus[j].id + "'>" + secondMenus[j].name + "</div><ul>";
					for (var k = 0; k < thirdMenus.length; k++) {
						lMenus = lMenus + "<li class='cat' id='" + thirdMenus[k].id + "' >" + thirdMenus[k].name + "</li>";
					}
					lMenus = lMenus + "</ul>";
				}
				lMenus = lMenus + "</div></div>";
			}
			$('#leftMenu').append(lMenus);
			$(".cat").on("click", function() {
				window.location.href = "${ctxPath}/kms/core/kdDoc/search.do?i=1&catId=" + $(this).attr("id");
			});
		});

		//点击选择框
		$(".listIndex a ").on("click", function() {
			//判断是点击搜索标题还是点击其他搜索
			if ($(this).attr('name') == 'search') {
				var text = $('#searchSubject').val();
			} else {
				var text = $(this).text();
			}

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

			if ($(this).attr('name') == 'search') {//如果是搜索标题
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

					key = $(this).find("span").attr("key");
					value = $(this).find("label").attr("value");
					/* if (value == 'yes' || value == 'no') {
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
					} else  */{
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
			grid.setUrl("${ctxPath}/kms/core/kdDoc/getByPath.do?catId=" + catId);
			formData.filter = mini.encode(data);
			formData.pageIndex = grid.getPageIndex();
			formData.pageSize = grid.getPageSize();
			formData.sortField = grid.getSortField();
			formData.sortOrder = grid.getSortOrder();
			console.log(formData);
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
			var catId = "${param['catId']}";
			grid.setUrl("${ctxPath}/kms/core/kdDoc/getByPath.do?catId=" + catId);
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
			var catId = "${param['catId']}";
			formData.filter = mini.encode(data);
			formData.pageIndex = grid.getPageIndex();
			formData.pageSize = grid.getPageSize();
			formData.sortField = grid.getSortField();
			formData.sortOrder = grid.getSortOrder();
			grid.setUrl("${ctxPath}/kms/core/kdDoc/getByPath.do?catId=" + catId);
			grid.load(formData);
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

		//双击表格显示内容
		function dbShowDoc(e) {
			var record = e.record;
			var pkId = record.pkId;
			window.open('${ctxPath}/kms/core/kdDoc/show.do?docId=' + pkId, '_blank');
		}
		//点击标题跳转
		function showDoc(docId) {
			var pkId = docId;
			window.open('${ctxPath}/kms/core/kdDoc/show.do?docId=' + pkId, '_blank');
		}
		
		//新增知识
		function newDoc() {
			window.open("${ctxPath}/kms/core/kdDoc/new1.do");
		}
	</script>
	<script src="${ctxPath}/scripts/knowl/knowl.js" type="text/javascript"></script>
</body>
</html>