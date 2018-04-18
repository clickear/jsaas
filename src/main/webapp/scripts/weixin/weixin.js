/**
 * 微信,显示用户文章对话框
 * 
 * @param tenantId
 *            当前用户为指定的管理机构下的用户,才可以查询到指定的租用机构下的用户
 * @param single
 * @param callback
 *            回调函数，返回选择的用户信息，当为单选时， 返回单值，当为多选时，返回多个值
 */
function _wxPSaasArticleListDialog(tenantId, single, callback) {
	_OpenWindow({
		url : __rootPath + '/wx/p/wxPSaasArticle/listDialog.do?single=' + single + '&tenantId=' + tenantId,
		height : 350,
		width : 780,
		title : '文章选择',
		ondestroy : function(action) {
			if (action != 'ok')
				return;
			var iframe = this.getIFrameEl();
			var articles = iframe.contentWindow.getArticles();
			if (callback) {
				if (single && articles.length > 0) {
					callback.call(this, articles[0]);
				} else {
					callback.call(this, articles);
				}
			}
		}
	});
}

/**
 * 微信,显示文章对话框
 * 
 * @param tenantId
 *            当前用户为指定的管理机构下的用户,才可以查询到指定的租用机构下的用户
 * @param single
 * @param callback
 *            回调函数，返回选择的用户信息，当为单选时， 返回单值，当为多选时，返回多个值
 */
function _wxPArticleListDialog(tenantId, single, callback) {
	// alert("llll");
	_OpenWindow({
		url : __rootPath + '/wx/p/wxPArticle/listDialog.do?single=' + single + '&tenantId=' + tenantId,
		height : 350,
		width : 780,
		title : '文章选择',
		ondestroy : function(action) {
			if (action != 'ok')
				return;
			var iframe = this.getIFrameEl();
			var articles = iframe.contentWindow.getArticles();
			if (callback) {
				if (single && articles.length > 0) {
					callback.call(this, articles[0]);
				} else {
					callback.call(this, articles);
				}
			}
		}
	});
}

/**
 * 显示机构对话框
 * 
 * @param single
 * @param callback
 *            回调函数，返回选择的用户信息，当为单选时， 返回单值，当为多选时，返回多个值
 */

function _TenantDialog(single, callback) {

	_OpenWindow({
		url : __rootPath + '/sys/core/sysInst/listDialog.do?single=' + single,
		height : 350,
		width : 780,
		title : '机构选择',
		ondestroy : function(action) {
			if (action != 'ok')
				return;
			var iframe = this.getIFrameEl();
			var articles = iframe.contentWindow.getArticles();
			if (callback) {
				if (single && articles.length > 0) {
					callback.call(this, articles[0]);
				} else {
					callback.call(this, articles);
				}
			}
		}
	});
}
