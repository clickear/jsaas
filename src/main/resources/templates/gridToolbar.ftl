		<ul id="popupAddMenu" class="mini-menu" style="display:none;">
		 	<#if add=="true">
            <li iconCls="icon-create" onclick="add()">新建</li>
            </#if>
            <#if copyAdd=="true">
            <li iconCls="icon-copyAdd" onclick="copyAdd()">复制新建</li>
            </#if>
        </ul>
        <ul id="popupSettingMenu" class="mini-menu" style="display:none;">
            <li>
                <span iconCls="icon-download">导出</span>
                <ul id="popupExportMenu" class="mini-menu">
                	<#if exportCurPage=="true">
                    <li iconCls="icon-excel" onclick="exportCurPage()">导出当前页</li>
                    </#if>
                    <#if exportAllPage=="true">
                    <li iconCls="icon-excel" onclick="exportAllPage()">导出所有页</li>
                    </#if>
                </ul>
            </li>
        </ul>
                </ul>
        <ul id="popupSettingMenu" class="mini-menu" style="display:none;">
            <li>
                <span iconCls="icon-download">导出</span>
                <ul id="popupExportMenu" class="mini-menu">
                	<#if exportCurPage=="true">
                    <li iconCls="icon-excel" onclick="exportCurPage()">导出当前页</li>
                    </#if>
                    <#if exportAllPage=="true">
                    <li iconCls="icon-excel" onclick="exportAllPage()">导出所有页</li>
                    </#if>
                </ul>
            </li>
        </ul>
        <div class="titleBar">
			<ul>
				<#if detail=="true">
					<li>
						<a class="mini-button" iconCls="icon-detail" plain="true" onclick="detail()">明细</a>
					</li>
				</#if>
				<#if popupAddMenu=="true">
					<li>
						<a class="mini-menubutton" iconCls="icon-create" plain="true" menu="#popupAddMenu">增加</a>
					</li>
				</#if>
				<#if edit=="true">
					<li>
						<a class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
					</li>
				</#if>
				<#if remove=="true">
					<li>
						<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
					</li>
				</#if>
				<#if extToolbars??>
					${extToolbars}
				</#if>
				<li class="clearfix"></li>
			</ul>
			 <#if fieldSearch=="true">
				<div class="searchBox">
					<form id="searchForm" class="text-distance" style="display: none;">						
						<ul>
							<li>
								<span class="long"> 请选择查询字段：</span>
								<input id="fieldName" class="mini-combobox" textField="title" valueField="attrName" 
									   parentField="parentId"  onvaluechanged="onSearchFieldChanged" 
									   url="${rootPath}/ui/module/getModuleFields.do?entityName=${entityName}"/>
								<input id="fieldCompare" class="mini-combobox" textField="fieldOpLabel" valueField="fieldOp"/>
							</li>
							<li>
								<span id="fieldValContainer">
									<input id="fieldVal" class="mini-textbox mini-buttonedit" emptyText="请输入查询条件值" style="width:150px;" onenter="onKeyEnter"/>
								</span>
							</li>
							<#if selfSearch??>
								${selfSearch}
							</#if>
							<li>
								<div class="searchBtnBox">
									<input class="_search" type="button" value="搜索" onclick="search()" />
									<input class="_reset" type="reset"  value="清空" onclick="clearSearch()" />
								</div>
							</li>
							<li class="clearfix"></li>
						</ul>
					</form>	
					<span class="searchSelectionBtn" onclick="no_search(this ,'searchForm')">
						<i class="icon-sc-lower"></i>
					</span>
				</div>
			</#if>
		</div>