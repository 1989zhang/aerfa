$("#form-dataDic-add").validate({
	rules:{
		fullName:{
			required:true,
		},
		code:{
			required:true,
			remote: {
	            url: prefix +"/check_data_dic_unique",
	            type: "post",
	            dataType: "json",
	            data: {
	            	//修改的时候要检测id不是他自己且名称相同才为重复
	            	"id" : function() {
	                    return $("input[name='id']").val();
	                },
	                "typeCode" : function() {
	                    return $("input[name='typeCode']").val();
	                },
	            	"code" : function() {
	                    return $("input[name='code']").val();
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
	    "code": {
	        remote: "字典编码已经存在"
	    }
	},
	submitHandler: function(form) {
		$.operate.save(prefix + "/save_add", $('#form-dataDic-add').serialize());
	}
});