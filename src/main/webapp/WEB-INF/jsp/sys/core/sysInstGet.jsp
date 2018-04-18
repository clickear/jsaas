<%-- 
    Document   : 租用机构管理
    Created on : 2015-3-28, 17:42:57
    Author     : csx
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <title>租用机构明细</title>
  <%@include file="/commons/get.jsp"%>
    </head>
    <body>
<%--         <rx:toolbar toolbarId="toolbar1"/> --%>
        <div id="form1" class="form-outer shadowBox" style="margin-top: 20px;">
             <div style="padding:5px;">
                    <table style="width:100%" class="table-detail column_2" cellpadding="0" cellspacing="1">
                    	<caption>机构基本信息</caption>
                        <tr>
                            <th>机构中文名</th>
                            <td>    
                                ${sysInst.nameCn}
                            </td>
                            <th>机构英文名</th>
                            <td>    
                                ${sysInst.nameEn}
                            </td>
                        </tr>
                        <tr>
                            <th>组织机构编号</th>
                            <td>    
                                ${sysInst.instNo}
                            </td>
                            <th>营业执照编号</th>
                            <td>    
                               ${sysInst.busLiceNo}
                            </td>
                        </tr>
                        <tr>
                        	<th>
                        		机构域名
                        	</th>
                        	<td>
                        		${sysInst.domain}
                        	</td>
                        	 <th>机构法人</th>
                            <td>    
                               ${sysInst.legalMan}
                            </td>
                        </tr>
                        <tr>
                            <th>机构简称(中文)</th>
                            <td>    
                                ${sysInst.nameCnS}
                            </td>
                            <th>机构简称(英文)</th>
                            <td>${sysInst.nameEnS}</td>
                        </tr>
                        <tr>
                        	<th>组织机构附件</th>
                        	<td colspan="3">
                        		<img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&view=true&fileId=${sysInst.regCodeFileId}" class="view-img" id="${sysInst.regCodeFileId}"/>
                        	</td>
                        </tr>
                        <tr>
                        	<th>营业执照附件</th>
                        	<td colspan="3">
                        		<img src="${ctxPath}/sys/core/file/imageView.do?thumb=true&view=true&fileId=${sysInst.busLiceFileId}" class="view-img" id="${sysInst.busLiceFileId}"/>
                        	</td>
                        </tr>
                        <tr>
                            <th>机构描述</th>
                            <td colspan="3">    
                               ${sysInst.descp}
                            </td>
                        </tr>
                    </table>
                 </div>
                <div style="padding:5px;">
                    <table class="table-detail column_2" cellpadding="0" cellspacing="1">
                    	<caption>联系信息</caption>
                        <tr>
                            <th>联  系  人</th>
                            <td>                        
                                ${sysInst.contractor}
                            </td>
                            <th>邮　　箱</th>
                            <td >    
                                ${sysInst.email}
                            </td>
                        </tr>
                        <tr>
                            <th>电　　话</th>
                            <td>                        
                                ${sysInst.phone}
                            </td>
                            <th>传　　真</th>
                            <td>                        
                               ${sysInst.fax}
                            </td>
                        </tr>
                        <tr>
                            <th>地　　址</th>
                            <td colspan="3">    
                                ${sysInst.address}
                            </td>
                        </tr>
                    </table>            
				</div>
            
			<div style="padding:5px">
				 <table class="table-detail column_2" cellpadding="0" cellspacing="1">
				 	<caption>更新信息</caption>
					<tr>
						<th>创  建  人</th>
						<td><rxc:userLabel userId="${sysInst.createBy}"/></td>
						<th>创建时间</th>
						<td><fmt:formatDate value="${sysInst.createTime}" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr>
						<th>更  新  人</th>
						<td><rxc:userLabel userId="${sysInst.updateBy}"/></td>
						<th>更新时间</th>
						<td><fmt:formatDate value="${sysInst.updateTime}" pattern="yyyy-MM-dd" /></td>
					</tr>
				</table>
        	</div>
        	</div>
        <rx:detailScript baseUrl="sys/core/sysInst" formId="form1" entityName="com.redxun.sys.core.entity.SysInst"/>
        <script type="text/javascript">
        	addBody();
        	$(function(){
        		//$(".view-img").css('cursor','pointer');
        		$(".view-img").on('click',function(){
        			var fileId=$(this).attr('id');
        			if(fileId=='')return;
        			_ImageViewDlg(fileId);
        		});
        	});
        </script>
    </body>
</html>
