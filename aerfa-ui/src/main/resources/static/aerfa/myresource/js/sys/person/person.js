//初始化
$(function() {
    var defaultData;
	$.ajax({
	    type: "post",
	    url: "/sys/org/tree",
	    dataType: "json",
	    success: function (result) { 
		   defaultData=result;
		   $('#treeview').treeview({
			   data: defaultData,
			   onNodeSelected:  function(event, data) {//树的点击事件
				   $("#orgId").val(data.id);
				   $.table.search($('form').attr('id'));
			   }
		   });
		   $("#collapse").hide();
	    }
	});
	 
	var options = {
	      url: prefix+"/list",
	      modalName: "个人",
	      createUrl: prefix+"/to_add/{id}",//传的是orgid，因为替换原有必须为{id}
	      updateUrl: prefix+"/to_edit/{id}",
	      removeUrl: prefix+"/remove/{id}",
	      id:'id',//id字段
	      sortOrder:'desc',//默认排序规则设置
	      columns: [{
	      	 checkbox: true
	      },{
	      	title: '人员名称',
	        field: 'personName',
	        width: '13%',
	        align: "center"
	      },{
	      	field: 'cardNo',
	        title: '证件号码',
	        width: '15%',
	        align: "center"
	      },{
	      	field: 'phoneNo',
	        title: '联系电话',
	        width: '15%',
	        align: "center"
		  },{
          	field: 'status',
            title: '人员状态',
            width: '10%',
            align: "center",
			formatter: function(value, row, index) {
          		return $.table.formatDicLabel(statusDatas, value);
			}

          },{
	      	field: 'createTime',
	        title: '创建时间',
	        width: '15%',
	        align: "center",
	        sortable: true
	      },{
	       title: '操作',
	       width: '30%',
	       align: "center",
	       formatter: function(value,row,index) {
				var actions = [];
			    if(editFlag){
					actions.push('<a class="btn btn-success btn-xs true " href="#" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit">编辑</i></a> ');
				}
			    if(removeFlag){
					actions.push('<a class="btn btn-danger btn-xs false " href="#" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove">删除</i></a> ');
				}
			    if(resetPwdFlag){
					actions.push('<a class="btn btn-info  btn-xs true " href="#" onclick="resetPwd(\'' + row.id + '\')"><i class="fa fa-key">重置</i></a> ');
				}
	            return actions.join('');
	       }
	    }]
	};
	$.table.init(options);
});

//展开树
function expanTreeAll(){
	$('#treeview').treeview('expandAll', { silent: true });
	$("#expan").hide();
	$("#collapse").show();
}
//折叠树
function collapseTreeAll(){
    $('#treeview').treeview('collapseAll', { silent: true });
    $("#collapse").hide();
    $("#expan").show();
}

//新增个人，要选中的单位id传过去
function addPerson(){
	var orgId=0;
	var treeObj=$('#treeview').treeview('getSelected');
	if(treeObj.length>0){
		orgId = treeObj[0].id;
	}
	$.operate.add(orgId);
}

//用户管理-重置密码
function resetPwd(personId) {
    var url = prefix +'/to_reset_pwd/' + personId;
    $.modal.open("重置密码", url);
}
