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
    <div id="main" style="height:280px"></div>
    <!-- ECharts单文件引入 -->
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts图表
        var myChart = echarts.init(document.getElementById('main')); 
        
        option = {
        	    title : {
        	        text: '2017年业务开通统计图',
        	        subtext: ''
        	    },
        	    tooltip : {
        	        trigger: 'axis'
        	    },
        	    legend: {
        	        data:['勘察单','开通单']
        	    },
        	    toolbox: {
        	        show : true,
        	        feature : {
        	            mark : {show: true},
        	            dataView : {show: true, readOnly: false},
        	            magicType : {show: true, type: ['line', 'bar']},
        	            restore : {show: true},
        	            saveAsImage : {show: true}
        	        }
        	    },
        	    calculable : true,
        	    xAxis : [
        	        {
        	            type : 'category',
        	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'value'
        	        }
        	    ],
        	    series : [
        	        {
        	            name:'勘察单',
        	            type:'bar',
        	            data:[2, 3, 7, 4, 5, 8, 12, 0, 0, 0, 0, 0],
        	            markPoint : {
        	                data : [
        	                    {type : 'max', name: '最大值'},
        	                    {type : 'min', name: '最小值'}
        	                ]
        	            },
        	            markLine : {
        	                data : [
        	                    {type : 'average', name: '平均值'}
        	                ]
        	            }
        	        },
        	        {
        	            name:'开通单',
        	            type:'bar',
        	            data:[3, 5, 9, 26, 28, 70, 175, 0, 0, 0, 0, 0],
        	            markPoint : {
        	                data : [
        	                    {name : '年最高', value : 182, xAxis: 7, yAxis: 183, symbolSize:18},
        	                    {name : '年最低', value : 3, xAxis: 11, yAxis: 3}
        	                ]
        	            },
        	            markLine : {
        	                data : [
        	                    {type : 'average', name : '平均值'}
        	                ]
        	            }
        	        }
        	    ]
        	};
        // 为echarts对象加载数据 
        myChart.setOption(option); 
    </script>

	</body>
</html>