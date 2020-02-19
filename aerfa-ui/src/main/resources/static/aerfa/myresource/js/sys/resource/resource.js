//初始化树形网格数据
$(function() {
	var options = {
        url: prefix+"/list",
        createUrl: prefix+ "/to_add/{id}",//新增地址，且传选中父级序号
        updateUrl: prefix+ "/to_edit/{id}",//修改地址
        removeUrl: prefix+ "/remove/{id}",//删除地址
        modalName: "资源",//提示信息要用到
        id: 'id',
        parentId: 'parentId',
        columns: [{
            field: 'resourceName',
        	title: '资源名称',
            width: '30%'
        },{
        	title: '资源类型',
            width: '10%',
            align: "center",
            formatter: function(value,item,index) {
                if (item.resourceType == 'system') {
                    return '<span class="label label-danger">系统</span>';
                }
                else if (item.resourceType == 'module') {
                    return '<span class="label label-warning">模块</span>';
                }
                else if (item.resourceType == 'menu') {
                    return '<span class="label label-success">菜单</span>';
                }
                else if (item.resourceType == 'button') {
                    return '<span class="label label-primary">按钮</span>';
                }
             }
        },{
        	title: '资源标识',
        	field: 'identify',
            width: '15%',
            align: "center"
        },{
        	field: 'url',
            title: '访问路径',
            width: '15%',
            align: "center"
        },{
            field: 'status',
            title: '资源状态',
            width: '10%',
            align: "center",
            formatter: function(value, row, index) {
                return $.table.formatDicLabel(statusDatas, value);
            }
        },{
        	 title: '操作',
             width: '20%',
             align: "center",
             formatter: function(value,row, index) {
                 var actions = [];
                 if(addFlag){
                     actions.push('<a class="btn btn-info btn-xs" href="#" onclick="$.operate.add(\'' + row.id + '\')"><i class="fa fa-plus"></i>新增</a> ');
                 }
                 if(editFlag){
                     actions.push('<a class="btn btn-success btn-xs" href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                 }
                 if(removeFlag){
                     actions.push('<a class="btn btn-danger btn-xs" href="#" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a>');
                 }
                 return actions.join('');
             }
        }]
    };
    $.treeTable.init(options);
});