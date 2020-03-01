//初始化参数和数据
$(function() {
    var options = {
        url: prefix + "/list",
        createUrl: prefix + "/to_add",
        updateUrl: prefix + "/to_edit/{id}",
        removeUrl: prefix + "/remove/{id}",
        modalName: "角色",
        id:'id',//id字段
        columns: [{
            checkbox: true
        },
        {
            field : 'roleName',
            title : '角色名称',
            align: 'center'
        },
        {
            field : 'roleCode',
            title : '角色编码',
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
            field : 'orderNo',
            title : '排序号',
            align: 'center'
        },
        {
            field : 'remark',
            title : '备注',
            align: 'center'
        },
        {
            title: '操作',
            align: 'center',
            formatter: function(value, row, index) {
                var actions = [];
                if(editFlag){
                    actions.push('<a class="btn btn-success btn-xs" href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                    actions.push('<a class="btn btn-primary btn-xs" href="#" onclick="manageRoleResource(\'' + row.id + '\')"><i class="fa fa-inbox"></i>角色绑定资源</a> ');
                    actions.push('<a class="btn btn-primary btn-xs" href="#" onclick="manageDataPermission(\'' + row.id + '\')"><i class="fa fa-inbox"></i>绑定数据权限</a> ');
                }
                if(removeFlag){
                    actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a> ');
                }
                return actions.join('');
            }
        }]
    };
    $.table.init(options);
});