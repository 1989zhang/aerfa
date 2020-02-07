$(function() {
	var options = {
            url: prefix+"/config",
            columns: [{
            	title: '属性',
	            field: 'parmCode',
	            width: '20%',
	            align: "center"
            },
            {
 	            title: '值',
            	field: 'parmValue',
 	            width: '30%',
 	            align: "center"
            },
            {
 	            title: '说明',
            	field: 'remark',
 	            width: '50%',
 	            align: "center"
            }]
        };
        $.table.init(options);
});