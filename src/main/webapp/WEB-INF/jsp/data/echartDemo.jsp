<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<html>
	<head>
			<script src="${ctxPath}/scripts/echart/echarts.min.js"></script>
			<!-- 引入 shine 主题 -->
			<script src="${ctxPath}/scripts/echart/theme/shine.js"></script>
	</head>
	<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:220px"></div>
    <!-- ECharts单文件引入 -->
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts图表
        var myChart = echarts.init(document.getElementById('main')); 
        
        option = {
        	    title : {
        	        text: '某楼盘销售情况',
        	        subtext: '纯属虚构'
        	    },
        	    tooltip : {
        	        trigger: 'axis'
        	    },
        	    legend: {
        	        data:['意向','预购','成交']
        	    },
        	    toolbox: {
        	        show : true,
        	        feature : {
        	            mark : {show: true},
        	            dataView : {show: true, readOnly: false},
        	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
        	            restore : {show: true},
        	            saveAsImage : {show: true}
        	        }
        	    },
        	    calculable : true,
        	    xAxis : [
        	        {
        	            type : 'category',
        	            boundaryGap : false,
        	            data : ['周一','周二','周三','周四','周五','周六','周日']
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'value'
        	        }
        	    ],
        	    series : [
        	        {
        	            name:'成交',
        	            type:'line',
        	            smooth:true,
        	            itemStyle: {normal: {areaStyle: {type: 'default'}}},
        	            data:[10, 12, 21, 54, 260, 830, 710]
        	        },
        	        {
        	            name:'预购',
        	            type:'line',
        	            smooth:true,
        	            itemStyle: {normal: {areaStyle: {type: 'default'}}},
        	            data:[30, 182, 434, 791, 390, 30, 10]
        	        },
        	        {
        	            name:'意向',
        	            type:'line',
        	            smooth:true,
        	            itemStyle: {normal: {areaStyle: {type: 'default'}}},
        	            data:[1320, 1132, 601, 234, 120, 90, 20]
        	        }
        	    ]
        	};

        // 为echarts对象加载数据 
        myChart.setOption(option); 
    </script>

	</body>
</html>