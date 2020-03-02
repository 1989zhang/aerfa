//校验和提交form数据方法
$("#form-standard-add").validate({
    rules:{
        questionContent:{
            required:true
        },
        answerContent:{
            required:true
        }
    },
    submitHandler:function(form){
        $.operate.save(prefix + "/save_standard", $('#form-standard-add').serialize());
    }
});

/** 点击选中知识分类 **/
function selectCategoryTree(){
    var url = prefix+"/to_select_category_tree";
    $.modal.open("选择所属知识分类", url, '450', '400');
}

//新增相似非标准问法
function addUnStandard(){
    var unStandardNumber=$("input[name='unStandardQuestionContent']").length;
    var addStr="<div class='form-group' id='unStandardQuestionContent"+(unStandardNumber+1)+"'><label class='col-sm-3 control-label'></label>"+
        "<div class='col-sm-6'><input class='form-control' type='text' name='unStandardQuestionContent'/></div>"+
        "<div class='col-sm-2'><button type='button' onclick='delUnStandard("+(unStandardNumber+1)+")' class='btn btn-warning'><i class='fa fa-times'></i>删除</button></div>"+
        "</div>";
    $("#unStandardQuestionContent1").after(addStr);
    unStandardQuestionNumber(unStandardNumber+1);
}

//删除非标准问法
function delUnStandard(index){
    var unStandardNumber=$("input[name='unStandardQuestionContent']").length;
    $("#unStandardQuestionContent"+index).remove();
    unStandardQuestionNumber(unStandardNumber-1);
}

//更新已有X条问法内容
function unStandardQuestionNumber(index){
    $("#unStandardQuestionNumber").html("共有"+index+"条相似问法");
}