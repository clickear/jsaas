<%@ tag language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="formModels"  type="java.util.List" description="表单列表"%>
<c:if test="${not empty formModels}">
	<c:set var="formModel" value="${formModels[0]}"></c:set>
	<c:choose>
		<c:when test="${formModel.type=='ONLINE-DESIGN'}">
			<div class="customform" style="width: 1080px" id="form-panel">
				<c:choose>
				   <c:when test="${fn:length(formModels)==1}">  
				        <div class="form-model" id="formModel1"  boDefId="${formModel.boDefId}" formKey="${formModel.formKey}">
				        	${formModel.content}
				        </div>
				   </c:when>
				   <c:otherwise> 
						<div class="mini-tabs" activeIndex="0" style="width:100%;">
							<c:forEach var="model" items="${formModels}" varStatus="i" >
							    <div title="${model.description}">
							    	<div class="form-model" id="formModel${i.index}" boDefId="${model.boDefId}" formKey="${model.formKey}">
							        	${model.content}
							        </div>
							    </div>
							</c:forEach>
						</div>
				   </c:otherwise>
				</c:choose>
			</div>
		</c:when>
		<c:otherwise>
			<iframe src="${formModel.content}" style="width:100%;" id="formFrame" frameborder="0" ></iframe>
		</c:otherwise>
	</c:choose>
</c:if>