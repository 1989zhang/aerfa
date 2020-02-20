$("#form-configData-add").validate({
    rules:{
        dataCode:{
            required:true,
            remote: {
                url: prefix + "/check_data_code_unique",
                type: "post",
                dataType: "json",
                data: {
                    "id" : function() {
                        return $("input[name='id']").val();
                    },
                    "dataCode" : function() {
                        return $("input[name='dataCode']").val();
                    }
                },
                dataFilter: function(data, type) {
                    if ($.common.parseJson(data).code == 0) return true;
                    else return false;
                }
            }
        },
    },
    messages: {
        "dataCode": {
            remote: "参数编码已经存在"
        }
    },
    submitHandler: function(form) {
        $.operate.save(prefix + "/save_add", $('#form-configData-add').serialize());
    }
});
