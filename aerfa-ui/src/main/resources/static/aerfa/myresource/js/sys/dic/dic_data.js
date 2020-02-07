//初始化参数和数据
$(function() {
    var options = {
        url: prefix + "/list_data",
        createUrl: prefix + "/to_add_data/{id}",//传的是字典类型id，因为替换原有必须为{id}
        updateUrl: prefix + "/to_edit_data/{id}",
        removeUrl: prefix + "/remove/{id}",
        queryParams: queryParams,
        modalName: "数据字典",
        id:'id',//id字段
        columns: [{
            checkbox: true
        },
        {
			field : 'fullName', 
			title : '字典名称',
			width: '15%',
			align: 'center'
		},
		{
			field : 'code', 
			title : '字典编码',
			width: '15%',
			align: 'center'
		},
		{
			field : 'status', 
			title : '状态',
			width: '5%',
			align: 'center',
			formatter: function(value,row,index) {
            	if(row.status==1){
            		return "正常";
            	}else if(row.status==0){
            		return "停用";
            	}
            }
		},
		{
			field : 'isDefault', 
			title : '是否默认',
			width: '10%',
			align: 'center'
		},
		{
			field : 'orderNo', 
			title : '排序号',
			width: '5%',
			align: 'center'
		},
		{
			field : 'remark', 
			title : '备注描述',
			width: '15%',
			align: 'center'
		},
		{
			field : 'createTime', 
			title : '创建时间',
			width: '15%',
			align: 'center'
		},
        {
            title: '操作',
            align: 'center',
            width: '20%',
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
//触发数据字典类型TypeCode查询条件
function queryParams(params) {
	var search=$.table.queryParams(params);
	search.typeCode=$("#typeCode").val();
	return search;
}

//新增个人，要选中的单位id传过去
function addDicData(){
	var typeCode=$("#typeCode").val();
	$.operate.add(typeCode);
}