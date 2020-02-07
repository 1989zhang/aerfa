 $(function() {
	var options = {
            url: prefix+"/storage",
            createUrl: prefix+"/to_add_storage",
  	        updateUrl: prefix+"/to_edit_storage/{id}",
  	        removeUrl: prefix+"/remove_storage/{id}",
  	      	id:'parmCode',//id字段
  	        modalName: "redis缓存",
            showExport:false,//不开启导出功能
            columns: [{
    	      	checkbox: true
  	        },{
            	title: 'key键',
	            field: 'parmCode',
	            width: '25%',
	            align: "center"
            },
            {
 	            title: 'value值',
            	field: 'parmValue',
 	            width: '45%',
 	            align: "center",
 	            formatter:formatTableHideContentLong
            },{
 	            title: '值类型',
            	field: 'type',
 	            width: '5%',
 	            align: "center"
            },{
 	            title: '剩余过期时间秒',
            	field: 'expireTime',
 	            width: '10%',
 	            align: "center"
            },
            {
 	            title: '操作',
 	            width: '15%',
 	            align: "center",
 	            formatter: function(value,row,index) {
                    var actions = [];
                    actions.push('<a class="btn btn-success btn-xs true " href="#" onclick="$.operate.edit(\'' + row.parmCode + '\')"><i class="fa fa-edit">编辑</i></a> ');
                    actions.push('<a class="btn btn-danger btn-xs false " href="#" onclick="$.operate.remove(\'' + row.parmCode + '\')"><i class="fa fa-remove">删除</i></a>');
                    return actions.join('');
                
                }
            }]
        };
        $.table.init(options);
});
function cleanUpRedis(){
	var url = prefix+"/clean_up";
	$.operate.manage(url,"","确定执行清除redis缓存操作吗？清除缓存可能造成雪崩效应。");
}