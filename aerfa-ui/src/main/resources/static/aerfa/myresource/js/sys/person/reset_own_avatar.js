var cropper;
$(window).load(function() {
    var options = {
        thumbBox: '.thumbBox',
        spinner: '.spinner',
        imgSrc: $.common.isEmpty(avatar) ?  '/aerfa/img/profile_default.jpg' : avatar
    }
    cropper = $('.imageBox').cropbox(options);
    $('#avatar').on('change', function() {
        var reader = new FileReader();
        reader.onload = function(e) {
            options.imgSrc = e.target.result;
            //根据MIME判断上传的文件是不是图片类型
            if((options.imgSrc).indexOf("image/")==-1){
                $.modal.alertWarning("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
            } else {
                cropper = $('.imageBox').cropbox(options);
            }
        }
        reader.readAsDataURL(this.files[0]);
    })
    
	$('#btnCrop').on('click', function(){
		var img = cropper.getDataURL();
		$('.cropped').html('');
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:64px;margin-top:4px;border-radius:64px;box-shadow:0px 0px 12px #7E7E7E;" ><p>64px*64px</p>');
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:128px;margin-top:4px;border-radius:128px;box-shadow:0px 0px 12px #7E7E7E;"><p>128px*128px</p>');
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:180px;margin-top:4px;border-radius:180px;box-shadow:0px 0px 12px #7E7E7E;"><p>180px*180px</p>');
	})
	
	$('#btnZoomIn').on('click', function(){
		cropper.zoomIn();
	})
	
	$('#btnZoomOut').on('click', function(){
		cropper.zoomOut();
	})
});

function submitAvatarInfo() {
    var img = cropper.getBlob();
    var id = $("#id").val();
    var formdata = new FormData();
    formdata.append("id", id);
    formdata.append("avatarfile", img);
    $.ajax({
        url: prefix + "/update_avatar",
        data: formdata,
        type: "post",
        dataType: "json",
        processData: false,
        contentType: false,
        success: function(result) {
        	$.operate.saveSuccess(result);
        }
    })
}