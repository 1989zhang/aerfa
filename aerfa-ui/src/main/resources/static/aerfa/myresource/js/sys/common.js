        
/** 创建选项卡:J_menuTabs添加tab */
function createMenuItem(dataUrl, menuName) {
    dataIndex = $.common.random(1,100),
    flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.J_menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
             	// 显示tab对应的内容区
                $('.J_mainContent .J_iframe', topWindow).each(function() {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.J_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.J_menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.J_mainContent', topWindow).find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);

        // 添加选项卡
        $('.J_menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}

/** 刷新打开的选项卡:content-main刷新iframe */
function refreshMenuItem(dataUrlId) {
	var contentmain = window.parent.document.getElementById('content-main');
	var iframeList=contentmain.getElementsByTagName('iframe');
	for (i = 0;i <iframeList.length;i++){
		if(iframeList[i].dataset.id==dataUrlId){
			iframeList[i].src=dataUrlId;
		}
	}
}

/** 关闭选项卡:J_menuTabs关闭tab */
function closeMenuItem() {
	var topWindow = $(window.parent.document);
	var panelUrl = window.frameElement.getAttribute('data-panel');
	$('.page-tabs-content .active i', topWindow).click();
	if($.common.isNotEmpty(panelUrl)){
		$('.J_menuTab[data-id="' + panelUrl + '"]', topWindow).addClass('active').siblings('.J_menuTab').removeClass('active');
		$('.J_mainContent .J_iframe', topWindow).each(function() {
            if ($(this).data('id') == panelUrl) {
                $(this).show().siblings('.J_iframe').hide();
                return false;
            }
		});
	}
}

/** bootstrap-table列内容过长的隐藏内容显示100字符，hove显示全内容 */
function formatTableHideContentLong(value,row,index) {
	if($.common.isEmpty(value)){return "";}
	var outValue="";
	if(value.length>100){
		outValue=value.substr(0, 100)+"......";
	}else{
		outValue=value;
	}
    var span=document.createElement('span');
    span.setAttribute('title',value);
    span.innerHTML = outValue;
    return span.outerHTML;
}

/** bootstrap-table列内容过长的隐藏内容显示50字符，hove显示全内容 */
function formatTableHideContentMedium(value,row,index) {
	if($.common.isEmpty(value)){return "";}
	var outValue="";
	if(value.length>50){
		outValue=value.substr(0, 50)+"......";
	}else{
		outValue=value;
	}
    var span=document.createElement('span');
    span.setAttribute('title',value);
    span.innerHTML = outValue;
    return span.outerHTML;
}

/** bootstrap-table列内容过长的隐藏内容显示25字符，hove显示全内容 */
function formatTableHideContentShort(value,row,index) {
	if($.common.isEmpty(value)){return "";}
	var outValue="";
	if(value.length>25){
		outValue=value.substr(0, 25)+"......";
	}else{
		outValue=value;
	}
    var span=document.createElement('span');
    span.setAttribute('title',value);
    span.innerHTML = outValue;
    return span.outerHTML;
}

/** 可编辑表格的格式化type:text,textarea,select等等 */
function createEditTable(type,title,dicType) {
	if(type !="select" || $.common.isEmpty(dicType)){
    	return {
    		type:type,
    		title:title
    	};
	}else{
		return {
    		type:type,
    		title:title,
    		source: function () {
    			var result = [];
                var url= baseUrl +'/sys/dic/dic';
                $.operate.sendAjax(url,false,"post","json",{type:dicType}, function (data) {
                	$.each(data, function (index,obj) {	
                        result.push({value:obj.code, text:obj.fullName });
                    });
                });
                return JSON.stringify(result);
            }
    	};
	}
};

/** 根据数字顺序转化为字母顺序，为了excel列好看展示，例如1转A,2转B等 */
function changeNumberOrderToCharOrder(order) {
    var result = String.fromCharCode(order%26 + 65);
    while( order >= 26 ){
       order = order/26 - 1;
       result =  String.fromCharCode(order%26 + 65) + result;
    }
    return result;
}

/** 根据cookie的名字获取cookie的值 */
function getStorageCookieByName(cookieName){
    var strcookie = document.cookie;//获取cookie字符串
    var arrcookie = strcookie.split("; ");//分割
    //遍历匹配
    for ( var i = 0; i < arrcookie.length; i++) {
        var arr = arrcookie[i].split("=");
        if (arr[0] == cookieName){
            return arr[1];
        }
    }
    return "";
}

/** 获取系统默认存储的cookie的aerfatoken值 */
function getCookieAerfaToken(){
	return getStorageCookieByName("aerfatoken");
}

/** 字符前面补充多少位某字符 */
function fillFrontWord(baseWord,fillWord,allLength){
	var baseLength = (baseWord+'').length;
	var retStr = '';
	var fillLength=allLength-baseLength;
	for(var i=0; i<fillLength; i++){
		retStr += fillWord;
    }
	return retStr+baseWord;
}

/** 字符后面补充多少位某字符 */
function fillBackWord(baseWord,fillWord,allLength){
	var baseLength = (baseWord+'').length;
	var retStr = '';
	var fillLength=allLength-baseLength;
	for(var i=0; i<fillLength; i++){
		retStr += fillWord;
    }
	return baseWord+retStr;
}
