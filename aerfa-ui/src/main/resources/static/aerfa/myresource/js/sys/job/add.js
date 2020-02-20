$("#form-job-add").validate({
    rules:{
        jobName:{
            required:true
        },
        invokeTarget:{
            required:true
        },
        cronExpression:{
            required:true,
            remote: {
                url: prefix + "/check_expression_valid",
                type: "post",
                dataType: "json",
                data: {
                    "cronExpression" : function() {
                        return $("input[name='cronExpression']").val();
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
        "cronExpression": {
            remote: "cron表达式不正确"
        }
    },
    submitHandler: function(form) {
        $.operate.save(prefix + "/save_add", $('#form-job-add').serialize());
    }
});