//校验和提交form数据方法
$("#form-org-add").validate({
	rules:{
		fullName:{
			required:true,
			remote: {
                url: prefix +"/check_org_unique",
                type: "post",
                dataType: "json",
                data: {
                	//修改的时候要检测id不是他自己且名称相同才为重复
                	"id" : function() {
                        return $("input[name='id']").val();
                    },
                	"fullName" : function() {
                        return $("input[name='fullName']").val();
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
        "fullName": {
            remote: "部门已经存在"
        }
    },
	submitHandler:function(form){
		$.operate.save(prefix + "/save_add", $('#form-org-add').serialize());
	}
});

//部门管理-新增-选择父部门树
function selectOrgTree() {
	var url = prefix+"/to_select_tree";
	$.modal.open("选择部门", url, '450', '400');
}