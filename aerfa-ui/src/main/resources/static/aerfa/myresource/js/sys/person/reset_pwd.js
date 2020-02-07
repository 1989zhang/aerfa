//验证
$("#form-person-reset-pwd").validate({
	rules:{
		password:{
			required:true,
			minlength: 6,
			maxlength: 20
		}
	},
	submitHandler:function(form){
		$.operate.save(prefix + "/save_reset_info", $('#form-person-reset-pwd').serialize());
	}
});