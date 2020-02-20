//初始化参数和数据
$(function() {
    var options = {
        url: prefix + "/list",
        createUrl: prefix + "/to_add",
        updateUrl: prefix + "/to_edit/{id}",
        removeUrl: prefix + "/remove/{id}",
        modalName: "配置",
        id:'id',//id字段
        columns: [{
            checkbox: true
        },
            {
                field : 'dataCode',
                title : '参数编码',
                width: '20.0%',
                align: 'center',
            },
            {
                field : 'dataValue',
                title : '参数值',
                width: '20.0%',
                align: 'center',
            },
            {
                field : 'remark',
                title : '备注描述',
                width: '30.0%',
                align: 'center',
            },
            {
                field : 'orderNo',
                title : '排序号',
                width: '20.0%',
                align: 'center',
            },
            {
                title: '操作',
                align: 'center',
                width: '10%',
                formatter: function(value, row, index) {
                    var actions = [];
                    actions.push('<a class="btn btn-success btn-xs" href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                    actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a>');
                    return actions.join('');
                }
            }]
    };
    $.table.init(options);
});