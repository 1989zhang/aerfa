(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define('ChineseDistricts', [], factory);
    } else {
        // Browser globals.
        factory();
    }
})(function () {
	 
	var ChineseDistricts = {};
	$.ajax({
		url: "/comm/division" + "/picker_data",
        type: "post",
        dataType: "text",
        async:false,
        success: function(result) {
        	ChineseDistricts=eval('(' + result + ')');
        }
	});		
	
	if (typeof window !== 'undefined') {
        window.ChineseDistricts = ChineseDistricts;
    }

    return ChineseDistricts;
});