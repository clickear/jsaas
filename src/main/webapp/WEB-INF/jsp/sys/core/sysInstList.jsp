<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ui" uri="http://www.redxun.cn/formUI" %>
<!DOCTYPE html>
<html>
    <head>
        <title>组织列表管理</title>
        <%@include file="/commons/list.jsp"%>
    </head>
    <body>
    	<div class="titleBar mini-toolbar" >
	         <ul>
				<li>
					<a class="mini-button" iconCls="icon-detail"  onclick="detail()">明细</a>
				</li>
				<li>
					<a class="mini-button" iconCls="icon-create"   onclick="add()">增加</a>
				</li>
				<li>
					<a class="mini-button" iconCls="icon-edit"  onclick="edit()">编辑</a>
				</li>
				<li>
					<a class="mini-button" iconCls="icon-remove"  onclick="remove()">删除</a>
				</li>
				<li>
					<a class="mini-button" iconCls="icon-unlock"  onclick="enable()">启用</a>
				</li>
				<li>
					<a class="mini-button" iconCls="icon-lock"  onclick="disable()">禁用</a>
				</li>
				<li class="clearfix"></li>
			</ul>
			<div class="searchBox">
				<form id="searchForm" class="search-form" style="display: none;">						
					<ul>
						<li>
							<span class="long">机构中文名：</span><input class="mini-textbox" name="Q_nameCn_S_LK"/>
						</li>
						<li>
							<span class="long">机构英文名：</span><input class="mini-textbox" name="Q_nameEn_S_LK"/>
						</li>
						<li>
							<span class="text">机构域名：</span><input class="mini-textbox" name="Q_domain_S_LK"/>
						</li>
						<li>
							<span class="text">机构编号：</span><input class="mini-textbox" name="Q_instNo_S_LK"/>
						</li>
						<li>
							<span class="text">状态：</span> 
        					<input 
        						name="Q_status_S_EQ" 
        						class="mini-combobox" 
        						data="[{id:'DISABLED',text:'禁用'},{id:'ENABLED',text:'启用'},{id:'INIT',text:'初始化'}]"
        					 	value="" 
        					 	showNullItem="true" 
        					 	nullItemText="所有.."
       					 	/>
						</li>
						<li class="searchBtnBox">
							<a class="mini-button _search" onclick="searchForm(this)" >搜索</a>
							<a class="mini-button _reset" onclick="onClearList(this)">清空</a>
						</li>
						<li class="clearfix"></li>
					</ul>
				</form>	
				<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
					<i class="icon-sc-lower"></i>
				</span>
			</div>
	     </div>
        
        
        
        
        <div class="mini-fit rx-grid-fit">
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;border" allowResize="false"
                 url="${ctxPath}/sys/core/sysInst/listData.do"  idField="instId" multiSelect="true" showColumnsMenu="true"
                 sizeList="[5,10,20,50,100,200,500]" pageSize="20" allowAlternating="true" pagerButtons="#pagerButtons">
                <div property="columns">
                    <div type="checkcolumn" width="20"></div>
                    <div name="action" cellCls="actionIcons" width="22" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>        
                    <div field="nameCn" width="120" headerAlign="center" allowSort="true">机构中文名</div>    
                    <div field="instType" width="100" headerAlign="center" allowSort="true">机构类型</div>
                    <div field="domain" width="100" headerAlign="center" allowSort="true">域名</div>
                    <div field="instNo" width="100" headerAlign="center" allowSort="true">机构编号</div>
                    <div field="status" width="60" headerAlign="center" allowSort="true" renderer="onStatusRenderer">状态</div>
                </div>
            </div>
        </div>
        <script src="${ctxPath}/scripts/common/list.js" type="text/javascript"></script>
        <redxun:gridScript gridId="datagrid1" entityName="com.redxun.sys.core.entity.SysInst" 
           winHeight="450" winWidth="800" tenantId="${param['tenantId']}"
           entityTitle="组织" baseUrl="sys/core/sysInst"/>
        <script type="text/javascript">
        	//编辑
	        function onActionRenderer(e) {
	            var record = e.record;
	            var pkId = record.pkId;
	            var uid=record._uid;
	            var s = '<span class="icon-org-set" title="组织机构管理" onclick="orgSetRow(\'' + uid + '\')"></span>'
	            		+ ' <span class="icon-detail" title="明细" onclick="detailRow(\'' + pkId + '\')"></span>'
	                    + ' <span class="icon-edit" title="编辑" onclick="editRow(\'' + pkId + '\')"></span>';
	                    if(record.status=='INIT'){
	                    	s = s + ' <span class="icon-remove" title="删除" onclick="delRow(\'' + pkId + '\')"></span>';
	                    }
	            return s;
	        }
        	//启用
        	function enable(){
        		changeStatus('ENABLED');
        	}
        	//禁用
        	function disable(){
        		changeStatus('DISABLED');
        	}
        	
        	function changeStatus(status){
        		var ids=_GetGridIds('datagrid1');
        		if(ids.length==0) return;
        		_SubmitJson({
        			url:'${ctxPath}/sys/core/sysInst/enable.do',
        			data:{ids:ids.join(','),enable:status},
        			method:'POST',
        			success:function(result){
        				grid.load();
        			}
        		});
        	}
        	
        	//设置某租用账户下的组织机构
        	function orgSetRow(uid){
        		var row= grid.getRowByUID(uid);
        		console.info(row.instType);
        		top['index'].showTabPage({
						title:row.nameCn+'-组织架构',
						url:'/sys/org/sysOrg/mgr.do?tenantId='+row.instId + "&instType=" + row.instType,
						showType:'url',
						iconCls:'icon-org-set',
						menuId:'sys_'+row.instId
				});
        	}
        	
        	
        	function onStatusRenderer(e) {
                var record = e.record;
                var status = record.status;
                
                var arr = [ {'key' : 'ENABLED','value' : '启用','css' : 'green'}, 
       		                {'key' : 'DISABLED','value' : '禁止','css' : 'red'},
       		                {'key' : 'INIT','value' : '初始化','css' : 'blue'} ];
       		
       			return $.formatItemValue(arr,status);
            }
        	
        
        	
        </script>
    </body>
</html>