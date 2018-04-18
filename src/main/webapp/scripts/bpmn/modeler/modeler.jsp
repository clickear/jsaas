<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

  <title>BPMN流程设计</title>

  <style type="text/css">
    html, body, #canvas {
      height: 100%;
      padding: 0;
    }

    #save-button {
      position: absolute;
      top: 20px;
      right: 20px;

      border: solid 5px green;
    }
  </style>
  <link rel="stylesheet" href="${ctxPath}/scripts/bpmn/bpmnjs/assets/diagram-js.css">
  <link rel="stylesheet" href="${ctxPath}/scripts/bpmn/bpmnjs/assets/bpmn-font/css/bpmn-embedded.css">
</head>
<body>
  <!-- element to draw bpmn diagram in -->
  <div id="canvas"></div>
  
  <script>
  	var modelId='${param.modelId}';
  </script>

  <button id="save-button">保存</button>

  <!-- scripts -->
  <!-- bpmn-js modeler -->
  <script src="${ctxPath}/scripts/bpmn/bpmnjs/bpmn-modeler.js"></script>

  <!-- application -->
  <script src="${ctxPath}/scripts/bpmn/modeler/modeler.js"></script>
</body>
</html>