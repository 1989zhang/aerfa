/**
 * 我同意注册协议勾选框样式,radio变为方块
 */
$(document).ready(function () {
    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
});

/**
 * 注册提交方法
 */
function doRegister(){
    var account = $('#account').val().trim();
    var password = $('#password').val().trim();
    $.ajax({
        type: "post",
        url: baseUrl + "sys/register/create",
        data: {
            "account": account,
            "password": password
        },
        success: function(result) {
        	if (result.code == 0) {
        		 
        	}else{
        	
        	}
        }
    });
}