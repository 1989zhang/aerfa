//校验和提交form数据方法
$("#form-resource-add").validate({
	rules:{
		identify:{
			required:true,
			remote: {
                url: prefix +"/check_resource_unique",
                type: "post",
                dataType: "json",
                data: {
                	//修改的时候要检测id不是他自己且名称相同才为重复
                	"id" : function() {
                        return $("input[name='id']").val();
                    },
                	"identify" : function() {
                        return $("input[name='identify']").val();
                    }
                },
                dataFilter: function(data, type) {
                    if ($.common.parseJson(data).code == 0) return true;
                    else return false;
                }
            }
		}
	},
	messages: {
        "identify": {
            remote: "资源标识已经存在"
        }
    },
	submitHandler:function(form){
		$.operate.save(prefix + "/save_add", $('#form-resource-add').serialize());
	}
});

//资源管理-新增-选择父资源树
function selectResourceTree() {
	var url = prefix+"/to_select_tree";
	$.modal.open("选择父级资源", url, '450', '400');
}