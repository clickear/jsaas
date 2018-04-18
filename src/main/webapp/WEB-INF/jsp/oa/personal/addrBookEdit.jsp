<%-- 
    Document   : 联系人编辑页
    Created on : 2015-3-21, 0:11:48
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>联系人编辑</title>
<%@include file="/commons/edit.jsp"%>
</head>
<body>

		<rx:toolbar toolbarId="toolbar1" pkId="${addrBook.addrId}" excludeButtons="save">
			<div class="self-toolbar">
				<a class="mini-button" iconCls="icon-save" plain="true" onclick="saveAllData">保存</a>
			</div>
		</rx:toolbar>

	<div id="p1" class="form-outer shadowBox90">
		<form id="form1" method="post">
			<div class="form-inner">
				<input id="pkId" name="addrId" class="mini-hidden" value="${addrBook.addrId}" />
				<table class="table-detail column_1" cellspacing="1" cellpadding="0">
					<caption>联系人基本信息</caption>

					<tr>
						<th style="vertical-align: middle;">
							<span class="starBox">
								姓　　名 <span class="star">*</span>
							</span>
						</th>
						<td>
							<input 
								name="name" 
								value="${addrBook.name}" 
								class="mini-textbox" 
								vtype="maxLength:50" 
								style="width: 25%" 
								required="true" 
								emptyText="请输入姓名" 
							/>
						</td>
						<td rowspan="3" width="90" height="90">
							<input 
								id="photoId" 
								name="photoId" 
								value="${addrBook.photoId}" 
								class="mini-hidden" 
							/> 
							<img 
								src="${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId=${addrBook.photoId}" 
								class="upload-file" 
								width="90px" 
								height="90px" 
							/>
						</td>
					</tr>

					<tr>
						<th style="width: 10%; vertical-align: middle;">生　　日</th>
						<td>
							<input 
								name="birthday" 
								class="mini-datepicker" 
								format="yyyy-MM-dd" 
								value="<fmt:formatDate value="${addrBook.birthday}"  
								pattern="yyyy-MM-dd"/>" 
							/>
						</td>
					</tr>

					<tr>
						<th style="width: 10%; vertical-align: middle;">分　　组</th>
						<td><c:choose>
								<c:when test="${groupIds!=''&&groupNames!=''}">
									<input 
										id="tbl1" 
										class="mini-textboxlist" 
										name="groupId" 
										textName="" 
										required="true" 
										style="width: 200px;" 
										url="" 
										value="${groupIds}" 
										text="${groupNames}" 
										valueField="id" 
										textField="text" 
										allowInput="false" 
									/>
								</c:when>
								<c:otherwise>
									<input 
										id="tbl1" 
										class="mini-textboxlist" 
										name="groupId" 
										textName="" 
										style="width: 200px;" 
										url="" 
										value="" 
										text="" 
										valueField="id" 
										textField="text" 
										allowInput="false" 
									/>
								</c:otherwise>
							</c:choose><a class="mini-button" iconCls="icon-add" plain="true" onclick="showMenu"></a></td>
					</tr>

					<tr class="workunit">
						<th style="width: 10%;">工作单位 </th>
						<td colspan="2"><input name="company" value="${addrBook.company}" class="mini-textbox" vtype="maxLength:120" style="width: 20%" emptyText="公司" /> <input name="dep" value="${addrBook.dep}" class="mini-textbox" vtype="maxLength:50" style="width: 20%" emptyText="部门" /> <input name="pos" value="${addrBook.pos}" class="mini-textbox" vtype="maxLength:50" style="width: 20%" emptyText="职务" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addInfo"></a></td>
					</tr>

					<c:forEach items="${workunits}" var="workunit">
						<tr class="workunit">
							<th style="width: 10%;">其他工作单位</th>
							<td colspan="2"><input id="${workunit.contId}" name="ext0" value="${workunit.contact}" class="mini-textbox" vtype="maxLength:120" style="width: 20%" emptyText="公司" /> <input id="${workunit.contId}" name="ext1" value="${workunit.ext1}" class="mini-textbox" vtype="maxLength:50" style="width: 20%" emptyText="部门" /> <input id="${workunit.contId}" name="ext2" value="${workunit.ext2}" class="mini-textbox" vtype="maxLength:50" style="width: 20%" emptyText="职务" /> <a class="mini-button"
								iconCls="icon-remove" plain="true" onclick="removeInfo"></a></td>
						</tr>
					</c:forEach>

					<tr class="address">
						<th style="width: 10%;">地　　址</th>
						<td colspan="2"><input name="country" value="${addrBook.country}" class="mini-textbox" vtype="maxLength:32" style="width: 20%" emptyText="国家" /> <input name="sate" value="${addrBook.sate}" class="mini-textbox" vtype="maxLength:32" style="width: 20%" emptyText="省份" /> <input name="city" value="${addrBook.city}" class="mini-textbox" vtype="maxLength:32" style="width: 25%" emptyText="城市" /> <input name="street" value="${addrBook.street}" class="mini-textbox" vtype="maxLength:80"
							style="width: 35%" emptyText="街道" />&nbsp; <input name="zip" value="${addrBook.zip}" class="mini-textbox" vtype="maxLength:20" style="width: 30%" emptyText="邮编" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addInfo"></a></td>
					</tr>

					<c:forEach items="${addresses}" var="address">
						<tr class="address">
							<th style="width: 10%;">其他地址</th>
							<td colspan="2"><input id="${address.contId}" name="ext0" value="${address.contact}" class="mini-textbox" vtype="maxLength:32" style="width: 20%" emptyText="国家" /> <input id="${address.contId}" name="ext1" value="${address.ext1}" class="mini-textbox" vtype="maxLength:32" style="width: 20%" emptyText="省份" /> <input id="${address.contId}" name="ext2" value="${address.ext2}" class="mini-textbox" vtype="maxLength:32" style="width: 25%" emptyText="城市" /> <input id="${address.contId}"
								name="ext3" value="${address.ext3}" class="mini-textbox" vtype="maxLength:80" style="width: 35%" emptyText="街道" />&nbsp; <input id="${address.contId}" name="ext4" value="${address.ext4}" class="mini-textbox" vtype="maxLength:20" style="width: 30%" emptyText="邮编" /> <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeInfo"></a></td>
						</tr>
					</c:forEach>

					<tr class="mail">
						<th style="width: 10%;">主  邮  箱</th>
						<td colspan="2"><input name="mail" value="${addrBook.mail}" class="mini-textbox" vtype="maxLength:255" style="width: 50%" emptyText="邮箱" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addInfo"></a></td>
					</tr>

					<c:forEach items="${mails}" var="mail">
						<tr class="mail">
							<th style="width: 10%;">其他邮箱</th>
							<td colspan="2"><input id="${mail.contId}" name="ext0" value="${mail.contact}" class="mini-textbox" vtype="maxLength:255" style="width: 50%" emptyText="邮箱" /> <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeInfo"></a></td>
						</tr>
					</c:forEach>

					<tr class="mobile">
						<th style="width: 10%;">主  手  机</th>
						<td colspan="2"><input name="mobile" value="${addrBook.mobile}" class="mini-textbox" vtype="maxLength:16" style="width: 50%" emptyText="手机" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addInfo"></a></td>
					</tr>

					<c:forEach items="${mobiles}" var="mobile">
						<tr class="mobile">
							<th style="width: 10%;">其他手机</th>
							<td colspan="2"><input id="${mobile.contId}" name="ext0" value="${mobile.contact}" class="mini-textbox" vtype="maxLength:16" style="width: 50%" emptyText="手机" /> <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeInfo"></a></td>
						</tr>
					</c:forEach>

					<tr class="phone">
						<th style="width: 10%;">主  电  话</th>
						<td colspan="2"><input name="phone" value="${addrBook.phone}" class="mini-textbox" vtype="maxLength:16" style="width: 50%" emptyText="电话" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addInfo"></a></td>
					</tr>

					<c:forEach items="${phones}" var="phone">
						<tr class="phone">
							<th style="width: 10%;">其他电话</th>
							<td colspan="2"><input id="${phone.contId}" name="ext0" value="${phone.contact}" class="mini-textbox" vtype="maxLength:16" style="width: 50%" emptyText="电话" /> <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeInfo"></a></td>
						</tr>
					</c:forEach>

					<tr class="weixin">
						<th style="width: 10%;">主微信号</th>
						<td colspan="2"><input name="weixin" value="${addrBook.weixin}" class="mini-textbox" vtype="maxLength:80" style="width: 50%" emptyText="微信号" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addInfo"></a></td>
					</tr>

					<c:forEach items="${weixins}" var="weixin">
						<tr class="weixin">
							<th style="width: 10%;">其他微信号</th>
							<td colspan="2"><input id="${weixin.contId}" name="ext0" value="${weixin.contact}" class="mini-textbox" vtype="maxLength:80" style="width: 50%" emptyText="微信号" /> <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeInfo"></a></td>
						</tr>
					</c:forEach>

					<tr class="qq">
						<th style="width: 10%;">QQ　号</th>
						<td colspan="2"><input name="qq" value="${addrBook.qq}" class="mini-textbox" vtype="maxLength:32" style="width: 50%" emptyText="QQ" /> <a class="mini-button" iconCls="icon-add" plain="true" onclick="addInfo"></a></td>
					</tr>

					<c:forEach items="${qqs}" var="qq">
						<tr class="qq">
							<th style="width: 10%;">其他QQ号</th>
							<td colspan="2"><input id="${qq.contId}" name="ext0" value="${qq.contact}" class="mini-textbox" vtype="maxLength:32" style="width: 50%" emptyText="QQ" /> <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeInfo"></a></td>
						</tr>
					</c:forEach>

				</table>

				<ul id="contextMenu" class="mini-contextmenu">
					<c:forEach items="${groups}" var="group" varStatus="a">
						<li id="${group.groupId}" onclick="onItemClick">${group.name}</li>
					</c:forEach>
				</ul>

			</div>

		</form>
	</div>
	<rx:formScript formId="form1" baseUrl="oa/personal/addrBook" />

	<script type="text/javascript">
	mini.parse();
	addBody();
    $(function(){
		/**-- 用于上传图片 **/
	   $(".upload-file").on('click',function(){
			var img=this;
			_UserImageDlg(true,
				function(imgs){
					if(imgs.length==0) return;
					$(img).attr('src','${ctxPath}/sys/core/file/imageView.do?thumb=true&fileId='+imgs[0].fileId);
					$(img).siblings('input[type="hidden"]').val(imgs[0].fileId);
					$(img).attr('width','90');      //设置图片大小
					$(img).attr('height','90');
					var id=$(img).siblings('input[type="hidden"]').attr('id');
					mini.get(id).setValue(imgs[0].fileId);
				}
			);
		});	
	});
	
       /*添加额外多条信息*/	
		function addInfo(e){                           
			 var button = e.sender;
			 var el=button.getEl();

			 var vname=$(el).parent().find(".mini-textbox-input").attr("name");  //获取对应的类型
			 
				if(vname.indexOf("company")>-1)   //其他工作单位
					$(el).parent().parent().after("<tr class='workunit'><th style='width:10%;'>其他工作单位</th><td colspan='2'><input name='ext0' value='' class='mini-textbox' vtype='maxLength:120' style='width: 20%' emptyText='公司' />&nbsp;<input name='ext1' value='' class='mini-textbox' vtype='maxLength:50' style='width: 20%' emptyText='部门' />&nbsp;<input name='ext2' value='' class='mini-textbox' vtype='maxLength:50' style='width: 20%' emptyText='职务' />&nbsp;<a class='mini-button'  iconCls='icon-remove' plain='true' onclick='removeInfo'></a></td></tr>");
			 
			if(vname.indexOf("mail")>-1)  //其他邮箱
				$(el).parent().parent().after("<tr class='mail'><th style='width:10%;'>其他邮箱</th><td colspan='2'><input name='ext0' value='' class='mini-textbox' vtype='maxLength:255' style='width: 50%' emptyText='邮箱'  />&nbsp;<a class='mini-button'  iconCls='icon-remove' plain='true' onclick='removeInfo'></a></td></tr>");

			if(vname.indexOf("mobile")>-1) //其他手机
				$(el).parent().parent().after("<tr class='mobile'><th style='width:10%;'>其他手机</th><td colspan='2'><input name='ext0' value='' class='mini-textbox ext' vtype='maxLength:16' style='width: 50%' emptyText='手机' />&nbsp;<a class='mini-button'  iconCls='icon-remove' plain='true' onclick='removeInfo'></a></td></tr>");
			
			if(vname.indexOf("phone")>-1)  //其他电话
				$(el).parent().parent().after("<tr class='phone'><th style='width:10%;'>其他电话</th><td colspan='2'><input name='ext0' value='' class='mini-textbox  ext' vtype='maxLength:16' style='width: 50%' emptyText='电话' />&nbsp;<a class='mini-button'  iconCls='icon-remove' plain='true' onclick='removeInfo'></a></td></tr>");
		
			if(vname.indexOf("weixin")>-1)  //其他微信号
				$(el).parent().parent().after("<tr class='weixin'><th style='width:10%;'>其他微信号</th><td colspan='2'><input name='ext0' value='' class='mini-textbox ext' vtype='maxLength:80' style='width: 50%' emptyText='微信' />&nbsp;<a class='mini-button'  iconCls='icon-remove' plain='true' onclick='removeInfo'></a></td></tr>");
			
			if(vname.indexOf("qq")>-1)   //其他微信号
				$(el).parent().parent().after("<tr class='qq'><th style='width:10%;'>其他QQ号</th><td colspan='2'><input name='ext0' value='' class='mini-textbox ext' vtype='maxLength:32' style='width: 50%' emptyText='QQ' />&nbsp;<a class='mini-button'  iconCls='icon-remove' plain='true' onclick='removeInfo'></a></td></tr>");
			
 			if(vname.indexOf("country")>-1)  //其他地址
				$(el).parent().parent().after("<tr class='address'><th style='width:10%;'>其他地址</th><td colspan='2'><input name='ext0' value='' class='mini-textbox' vtype='maxLength:32' style='width: 20%' emptyText='国家' />&nbsp;<input name='ext1' value='' class='mini-textbox' vtype='maxLength:32' style='width: 20%' emptyText='省份' />&nbsp;<input name='ext2' value='' class='mini-textbox' vtype='maxLength:32' style='width: 25%' emptyText='城市' /><input name='ext3' value='' class='mini-textbox' vtype='maxLength:80' style='width: 35%' emptyText='街道' />&nbsp;&nbsp;<input name='ext4' value='' class='mini-textbox' vtype='maxLength:20' style='width: 30%' emptyText='邮编' />&nbsp;<a class='mini-button'  iconCls='icon-remove' plain='true' onclick='removeInfo'></a></td></tr>");
			
 			mini.parse();
		}
		
       /*移除一条额外信息*/
		function removeInfo(e){         
			 var button = e.sender;
			 var el=button.getEl();
			 $(el).parent().parent().remove();
		}
		
		var extType=['mail','mobile','phone','weixin','workunit','address','qq'];
		/*保存联系人信息*/
		function saveAllData(){                       
	        form.validate();
	        if (!form.isValid()) {
	            return;
	        }
			var extJson=[];
			for(var i=0;i<extType.length;i++){   //构建  name:value 的数组
				var vals=[];
				$("tr."+extType[i]).each(function(){
					var tr=$(this);
					tr.find('td').each(function(){
						var obj={};
						$(this).find('.mini-textbox-input').each(function(){
							var id=$(this).parent().parent().attr('id');
							var name=$(this).attr('name');
							var value=$(this).val();
							obj.id=id;
							obj[name]=value;
						});
						vals.push(obj);
					});
				});
				extJson.push({
					name:extType[i],
					vals:vals
				});
			}
			var formJson=_GetFormJson("form1");
			_SubmitJson({
				url:__rootPath+"/oa/personal/addrBook/saveAllData.do",
				data:{
					formData:mini.encode(formJson),
					exts:mini.encode(extJson)
				},
				method:'POST',
				success:function(){
					CloseWindow();
				}
			});
		}
		
		/*打开分组菜单*/
		function showMenu(e){                 
			var x=e.htmlEvent.clientX;         //当前鼠标位置
			var y=e.htmlEvent.clientY;
			var menu=mini.get("contextMenu");
			if(menu.items.length>0)
				menu.showAtPos(x, y);   //展示分组菜单
			else
				alert("你还没有分组！");
		}
		
		/*分组菜单点击事件处理*/
	       function onItemClick(e) {      
	            var item = e.sender;
	            var id=item.getId(); 
	            var name=item.getText();    
	            setValue(id,name);
	        }
	       
		/*分组显示*/
	       function setValue(id,name){     
	           var t = mini.get("tbl1");
	           var ids=t.getValue();
	           var texts=t.getText();
	           if(ids.indexOf(id)>-1)      
	        	   return;
	           else{
	        	   if(ids==""){
	           	   		t.setValue(id);
	           			t.setText(name);
	        	   }else{
	           	   		t.setValue(ids+","+id);
	           			t.setText(texts+","+name);
	        	   }
	           }
	       }
	</script>

</body>
</html>