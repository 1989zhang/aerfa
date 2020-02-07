// 基于准备好的dom，初始化echarts图表
var myChart1 = echarts.init(document.getElementById('main1'));
var myChart2 = echarts.init(document.getElementById('main2'));
var myChart3 = echarts.init(document.getElementById('main3')); 
var timeTicket;
$(function(){ 
	/*先初始化基础数据数据*/
 	initAllData();
 	/**然后5s刷新一次数据**/
    clearInterval(timeTicket);
    timeTicket = setInterval(function (){
   		refreshAddData();
    }, 5000);
    /*命中率tips提示*/
    var tips;
    $("#hitRate").on({
        mouseenter:function(){
            var that = this;
            tips =layer.tips("<span style='color:#000;'>一个设计良好的系统，命中率可以达到95%以上。</span>",that,{tips:[2,'#fff'],time:0,area: 'auto',maxWidth:500});
        },
        mouseleave:function(){
            layer.close(tips);
        }
    });

});       

/*先初始化3个main的chart基础数据数据*/
function initAllData(){
    var dateTime = new Date();
    var initDataTime = dateTime.getHours()+":" + dateTime.getMinutes() +":" + dateTime.getSeconds();
    
    var option1 = {
    	    legend: {
    	        data:['最高值','当前值']
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            dataView : {show: true, readOnly: true},
    	            magicType : {show: true, type: ['line', 'bar']},
    	            restore : {show: true},
    	            saveAsImage : {show:true,name:'内存占用情况',title:'保存图片',type:'jpeg'}
    	        }
    	    },
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    xAxis : [
    	        {
    	            type : 'category',
    	            boundaryGap : false,
    	            data : [initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime]
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'value',
    	            axisLabel : {
    	                formatter: '{value} M'
    	            }
    	        }
    	    ],
    	    series : [
    	    	{
    	            name:'当前值',
    	            type:'line',
    	            data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    	        },
    	    	{
     	            name:'最高值',
     	            type:'line',
     	            data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
     	        }
    	    ]
    };
    var option2 = {
       	    title : {
       	        text: '每分查询率'
       	    },
       	    tooltip : {
       	        trigger: 'axis'
       	    },
       	    legend: {
       	        data:['QPS']
       	    },
       	    toolbox: {
       	        show : true,
       	        feature : {
       	            dataView : {show: true, readOnly: true},
       	            magicType : {show: true, type: ['line', 'bar']},
       	            restore : {show: true},
       	            saveAsImage : {show:true,name:'qps',title:'保存图片',type:'jpeg'}
       	        }
       	    },
       	    calculable : true,
       	    xAxis : [
       	        {
       	            type : 'category',
       	            boundaryGap : false,
       	            data : [initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime]
       	        }
       	    ],
       	    yAxis : [
       	        {
       	            type : 'value'
       	        }
       	    ],
       	    series : [
       	        {
       	            name:'QPS',
       	            type:'line',
       	            data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
       	        }
       	    ]
    };
    var option3 = {
   	    tooltip : {
   	        trigger: 'axis'
   	    },
   	    legend: {
   	        data:['used_cpu_sys']
   	    },
   	    toolbox: {
   	        show : true,
   	        feature : {
   	            dataView : {show: true, readOnly: true},
   	            magicType : {show: true, type: ['line', 'bar']},
   	            restore : {show: true},
   	            saveAsImage : {show:true,name:'cpu',title:'保存图片',type:'jpeg'}
   	        }
   	    },
   	    xAxis : [
   	        {
   	            type : 'category',
   	            boundaryGap : false,
   	            data : [initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime,initDataTime]
   	        }
   	    ],
   	    yAxis : [
   	        {
   	            type : 'value'
   	        }
   	    ],
   	    series : [
   	        {
   	            name:'used_cpu_sys',
   	            type:'line',
   	            data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
   	        }
   	    ]
    };
    
    // 为echarts对象加载数据 
    myChart1.setOption(option1); 
    myChart2.setOption(option2); 
    myChart3.setOption(option3); 
}

function refreshAddData(){
	var lastData = $("#totalCommandsProcessed").html();
	var datetime = new Date();
	var memoryArray=new Array();
	var cpuArray=new Array();
	var qpsArray=new Array();
	var nowMemoryDataArray=new Array();
	var maxMemoryDataArray=new Array();
	var cpuDataArray=new Array();
	var qpsDataArray=new Array();
	
	var datetime = new Date();
	var axisDataTime = datetime.getHours()+":" + datetime.getMinutes() +":" + datetime.getSeconds();
	// 动态数据接口 addData
	$.ajax({
	    type: "post",
	    url: prefix+"/status",
	    data:{lastData:lastData},
	    dataType: "json",
	    success: function (result) { 
	    	$("#usedMemory").html(result.used_memory+"M");
	    	$("#keys").html(result.keys);
	    	$("#connectedClients").html(result.connected_clients);
	    	$("#totalCommandsProcessed").html(result.total_commands_processed);
	    	$("#hitRate").html(result.hit_rate+"%");

	    	nowMemoryDataArray.push(0);// 系列索引
	    	nowMemoryDataArray.push(result.used_memory);// 新增数据
	    	nowMemoryDataArray.push(false);// 新增数据是否从队列头部插入
	    	nowMemoryDataArray.push(false);// 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
	    	nowMemoryDataArray.push(axisDataTime);// 坐标轴标签

	    	maxMemoryDataArray.push(1);
	    	maxMemoryDataArray.push(result.used_memory_peak);
	    	maxMemoryDataArray.push(false);
	    	maxMemoryDataArray.push(false);
	    	maxMemoryDataArray.push(axisDataTime);
	    	memoryArray.push(nowMemoryDataArray);memoryArray.push(maxMemoryDataArray);

	    	qpsDataArray.push(0);
	    	qpsDataArray.push(result.qps);
	    	qpsDataArray.push(false);
	    	qpsDataArray.push(false);
	    	qpsDataArray.push(axisDataTime);
	    	qpsArray.push(qpsDataArray);
	    	
	    	cpuDataArray.push(0);
	    	cpuDataArray.push(result.used_cpu_sys);
	    	cpuDataArray.push(false);
	    	cpuDataArray.push(false);
	    	cpuDataArray.push(axisDataTime);
	    	cpuArray.push(cpuDataArray);
	    	
	    	myChart1.addData(memoryArray);
	        myChart2.addData(qpsArray);
	        myChart3.addData(cpuArray);
	        
	    }
	});
}