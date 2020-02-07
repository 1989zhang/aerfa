//验证
$("#form-person-reset-own-pwd").validate({
	rules:{
		oldPassword:{
			required:true,
			remote: {
				//远程地址只能输出 "true" 或 "false"，不能有其他输出。
                url: prefix + "/check_old_password",
                type: "post",
                dataType: "json",
                data: {
                	oldPassword: function() {
                        return $("input[name='oldPassword']").val();
                    }
                }
            }
		},
		password: {
            required: true,
            minlength: 6,
			maxlength: 20
        },
        confirm: {
            required: true,
            equalTo: "#password"
        }
	},
	messages: {
        oldPassword: {
            required: "请输入原密码",
            remote: "原密码错误"
        },
        password: {
            required: "请输入新密码",
            minlength: "密码不能小于6个字符",
            maxlength: "密码不能大于20个字符"
        },
        confirm: {
            required: "请再次输入新密码",
            equalTo: "两次密码输入不一致"
        }

    },
	submitHandler:function(form){
		$.operate.save(prefix + "/save_reset_info", $('#form-person-reset-own-pwd').serialize());
	}
});