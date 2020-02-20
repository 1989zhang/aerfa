//初始化参数和数据
$(function() {
    var options = {
        url: prefix + "/list_type",
        createUrl: prefix + "/to_add_type",
        updateUrl: prefix + "/to_edit_type/{id}",
        removeUrl: prefix + "/remove/{id}",
        modalName: "数据字典",
        id:'id',//id字段
        columns: [{
            checkbox: true
        },
		{
			field : 'typeName', 
			title : '类型名称',
			align: 'center'
		},
		{
			field : 'typeCode', 
			title : '类型编码',
			align: 'center'
		},
		{
			field : 'status', 
			title : '状态',
			align: 'center',
			formatter: function(value, row, index) {
				return $.table.formatDicLabel(statusDatas, value);
			}
		},
		{
			field : 'remark', 
			title : '备注',
			align: 'center'
		},
		{
			field : 'createTime', 
			title : '创建时间',
			align: 'center'
		},
        {
            title: '操作',
            align: 'center',
            formatter: function(value, row, index) {
            	var actions = [];
            	actions.push('<a class="btn btn-success btn-xs" href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
            	actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a>');
            	actions.push('<a class="btn btn-info btn-xs" href="#" onclick="detail(\'' + row.id + '\')"><i class="fa fa-list-ul"></i>字典列表</a> ');
            	return actions.join('');
            }
        }]
    };
    $.table.init(options);
});

// 字典列表-详细
function detail(dictId) {
    var url = prefix + '/to_dic_data/' + dictId;
    createMenuItem(url, "字典列表");
}