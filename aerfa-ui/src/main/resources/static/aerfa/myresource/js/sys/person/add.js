//验证人员form
$("#form-person-add").validate({
	rules:{
		treeName:{
			required:true
		},
		account:{
			required:true,
			remote: {
                url: prefix + "/check_account_unique",
                type: "post",
                dataType: "json",
                data: {
                	"id" : function() {
                        return $("input[name='id']").val();
                    },
                	"account" : function() {
                        return $("input[name='account']").val();
                    }
                },
                dataFilter: function(data, type) {
                	if ($.common.parseJson(data).code == 0) return true;
                    else return false;
                }
            }
		},
		password:{
			required:true,
			minlength: 6,
			maxlength: 20
		},
		personName:{
			required:true
		}
	},
	messages: {
        "account": {
            remote: "账号已经存在"
        }
    },
	submitHandler:function(form){
		$.operate.save(prefix + "/save_add", $('#form-person-add').serialize());
	}
});

//人员管理理-新增-选择所属部门树
function selectDeptTree() {
	//不能用prefix,因为是个人,而这里要指向单位
	var url = "/sys/org"+"/to_select_tree";
	$.modal.open("选择部门", url, '480', '580');
}