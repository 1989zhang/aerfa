/**
 * 登陆页面登陆校验账号密码方法
 */
function doLogin(){
	$.modal.loading("系统登录中，请稍后...");
    var account = $('#account').val().trim();
    var password = $('#password').val().trim();
    $.ajax({
    	url: baseUrl + "sys/login/check",
        type: "post",
	    dataType: "json",//期待返回的数据类型
        data: {
            "account": account,
            "password": password
        },
        success: function(result) {
        	$.modal.closeLoading();
        	if (result.code == 0) {
        		window.location.href= baseUrl + 'index';
        	}else{
        		$.modal.alertError(result.msg);
        	}
        }
    });
    return false;
}
/**
 * login_vXXX登陆页面登陆校验账号密码方法
 */
function doLoginVersion(){
	$.modal.loading("系统登录中，请稍后...");
	var account = $('#account').val().trim();
    var password = $('#password').val().trim();
    $.ajax({
        url: baseUrl + "sys/login/check",
        type: "post",
        dataType: "json",
        data: {
            "account": account,
            "password": password
        },
        success: function(result) {
        	$.modal.closeLoading();
        	if (result.code == 0) {
        		window.location.href= baseUrl + 'index';
        	}else{
        		$.modal.alertError(result.msg);
        	}
        }
    });
}