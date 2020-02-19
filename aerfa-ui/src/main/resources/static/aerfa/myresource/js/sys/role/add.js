//验证角色form
$("#form-role-add").validate({
    rules:{
        roleCode:{
            required:true,
            remote: {
                url: prefix + "/check_role_unique",
                type: "post",
                dataType: "json",
                data: {
                    "id" : function() {
                        return $("input[name='id']").val();
                    },
                    "account" : function() {
                        return $("input[name='roleCode']").val();
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
        "roleCode": {
            remote: "标识编码重复"
        }
    },
    submitHandler: function(form) {
        $.operate.save(prefix + "/save_add", $('#form-role-add').serialize());
    }
});
