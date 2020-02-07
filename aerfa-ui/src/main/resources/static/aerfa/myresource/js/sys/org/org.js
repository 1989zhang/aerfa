//初始化树形网格数据
$(function() {
	var options = {
            url: prefix+ "/list",//初始化获取数据地址
            createUrl: prefix+ "/to_add/{id}",//新增地址，且传选中父级序号
	        updateUrl: prefix+ "/to_edit/{id}",//修改地址
	        removeUrl: prefix+ "/remove/{id}",//删除地址
	        modalName: "部门",//提示信息要用到
            id: 'id',//树结构id字段
            parentId: 'parentId',//树结构父id字段
            columns: [{
	            field: 'fullName',
            	title: '名称',
	            width: '30%'
            },
            {
            	field: 'status',
 	            title: '状态',
 	            width: '10%',
 	            align: "center",
 	            formatter: function(value,row,index) {
 	            	if(row.status==1){
 	            		return "正常";
 	            	}else{
 	            		return "停用";
 	            	}
 	            }
            },
            {
            	field: 'createTime',
 	            title: '创建时间',
 	            width: '10%',
 	            align: "center"
            },{
	            title: '操作',
	            width: '50%',
	            align: 'center',
	            formatter: function(value,row,index) {
	                    var actions = [];
	                    actions.push('<a class="btn btn-info  btn-xs true " href="#" onclick="$.operate.add(\'' + row.id + '\')"><i class="fa fa-plus">新增</i></a> ');
	                    actions.push('<a class="btn btn-success btn-xs true " href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit">编辑</i></a> ');
	                    actions.push('<a class="btn btn-danger btn-xs false " href="#" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove">删除</i></a>');
	                    return actions.join('');
	                
                }
	        }]
        };
        $.treeTable.init(options);
});