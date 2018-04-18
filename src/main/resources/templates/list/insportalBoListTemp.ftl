<div id="${bokey}" class="colId_<#noparse>${colId}</#noparse>" colId="<#noparse>${colId}</#noparse>">
	<div class="widget-box border " >
		<div class="widget-body">
			<div class="widget-scroller" >
				<dl class="modularBox">
					<dt>
						<h1><#noparse>${data.title}</#noparse></h1>
						<div class="icon">
							<input type="button" id="More" onclick="showMore(<#noparse>'${colId}','${data.title}','${data.url}'</#noparse>)"/>
							<input type="button" id="Refresh" onclick="refresh(<#noparse>'${colId}'</#noparse>)"/>
						</div>
						<div class="clearfix"></div>
					</dt>
					<#noparse><#list data.obj as obj></#noparse>
					<#list map?keys as key >
							<dd>
								<p><a href="<#noparse>${ctxPath}</#noparse>/sys/customform/sysCustomFormSetting/detail/${bokey}/<#noparse>${obj.ID_}</#noparse>.do" target="_blank"><#noparse>${obj.</#noparse>${map[key]}<#noparse>}</#noparse></a></p>
							</dd>
					</#list>
					<#noparse></#list><#noparse>
				</dl>
			</div>
		</div>
	</div>
</div>