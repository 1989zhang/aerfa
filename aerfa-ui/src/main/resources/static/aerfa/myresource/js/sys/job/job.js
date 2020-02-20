//初始化参数和数据
$(function() {
    var options = {
        url: prefix + "/list",
        createUrl: prefix + "/to_add",
        updateUrl: prefix + "/to_edit/{id}",
        removeUrl: prefix + "/remove/{id}",
        modalName: "定时任务",
        id:'id',//id字段
        columns: [{
            checkbox: true
        },
            {
                field : 'jobName',
                title : '任务名称',
                width: '20%',
                align: 'center',
            },
            {
                field : 'invokeTarget',
                title : '调用目标字符串',
                width: '25%',
                align: 'center',
                formatter:formatTableHideContentMedium
            },
            {
                field : 'cronExpression',
                title : 'cron执行表达式',
                width: '10%',
                align: 'center',
            },
            {
                field : 'status',
                title : '状态',
                width: '5%',
                align: 'center',
                formatter: function(value, row, index) {
                    return $.table.formatDicLabel(statusDatas, value);
                }
            },
            {
                field : 'remark',
                title : '备注',
                width: '15%',
                align: 'center',
                formatter:formatTableHideContentShort
            },
            {
                title: '操作',
                align: 'center',
                width: '25%',
                formatter: function(value, row, index) {
                    var actions = [];
                    actions.push('<a class="btn btn-success btn-xs" href="#" onclick="jobRunOnce(\'' + row.id + '\')"><i class="fa fa-road"></i>运行一次</a> ');
                    if(row.status==0){
                        actions.push('<a class="btn btn-primary btn-xs" href="#" onclick="jobEnable(\'' + row.id + '\')"><i class="fa fa-forward"></i>恢复任务</a>');
                    }else{
                        actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="jobDisable(\'' + row.id + '\')"><i class="fa fa-pause"></i>暂停任务</a>');
                    }
                    actions.push('<a class="btn btn-info btn-xs" href="#" onclick="jobRunLog(\'' + row.id + '\')"><i class="fa fa-indent"></i>运行日志</a>');
                    return actions.join('');
                }
            }]
    };
    $.table.init(options);
});

//任务运行一次
function jobRunOnce(id){
    $.modal.confirm("确认要立即运行任务吗？", function() {
        $.operate.post(prefix + "/run_once", { "id": id});
    })
}
//任务恢复运行
function jobEnable(id){
    $.modal.confirm("确认要启用任务吗？", function() {
        $.operate.post(prefix + "/change_status", { "id": id, "status": 1 });
    })
}
//任务暂停运行
function jobDisable(id){
    $.modal.confirm("确认要停用任务吗？", function() {
        $.operate.post(prefix + "/change_status", { "id": id, "status": 0});
    })
}
//任务运行日志
function jobRunLog(id){
    var url =  '/sys/log' + '/to_job_log?jobId=' + id;
    createMenuItem(url, "任务日志");
}