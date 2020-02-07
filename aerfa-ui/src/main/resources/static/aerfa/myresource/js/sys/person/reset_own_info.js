$(document).ready(function () {
    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
});

/*用户管理-头像*/
function avatar() {
    var url = prefix + '/to_reset_own_avatar';
    $.modal.open("修改头像", url);
}
//重置基本信息验证
$("#form-person-reset-own-info").validate({
	rules:{
		personName:{
			required:true
		},
		email:{
			email:true
		}
	},
	submitHandler:function(form){
		//刷新当前页面不是父页面
		var url=prefix + "/save_reset_info";
		var data=$('#form-person-reset-own-info').serialize();
		$.operate.submit(url,"post","json",data);
	}
});
//重置密码验证
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
		//刷新当前页面不是父页面
		$.operate.submit(prefix + "/save_reset_info","post","json",$('#form-person-reset-own-pwd').serialize());
	}
});

//个人联系地址维护
function addressManage(personId){
	var url = "/sys/person_address" +'/to_person_address/' + personId;
	createMenuItem(url, "地址维护");
}