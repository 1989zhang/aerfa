//初始化选择资源树形数据
$(function() {
	var defaultData;
	$.ajax({
	    type: "post",
	    url: prefix+"/tree",
	    dataType: "json",
	    success: function (result) { 
	    	defaultData=result;
		    $('#treeview').treeview({
		        data: defaultData
		    });
		    $("#collapse").hide();
	    }
	});
});
//树的查询显示高亮的方法
function searchNode(){
	var keyword = $.common.trim($('#keyword').val());
	var ret=$('#treeview').treeview('search', [ keyword, {
		  ignoreCase: true,     // 忽略大小写
		  exactMatch: false,    // 完全匹配
		  revealResults: true,  // 展现展开匹配的数据
		}]);
}

//把选中树节点的名称和id传回到父界面
function loadTreeToParent(){
	var treeObj=$('#treeview').treeview('getSelected');
	if(treeObj.length==0){
		$.modal.alertWarning("请先选中一个节点！");
		return ;
	}
	var treeId = treeObj[0].id;
	var treeName = treeObj[0].text;
	parent.$("#treeId").val(treeId);
	parent.$("#treeName").val(treeName);
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}

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