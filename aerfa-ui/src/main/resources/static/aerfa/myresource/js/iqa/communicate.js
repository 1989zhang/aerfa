
//把信息添加append到对应的地方
function appendMessage(divId,location,userName,dateStr,content){
    if(location=="left"){
        $("#"+divId).append('<div class="left"><div class="author-name">'+userName+'&nbsp;<small class="chat-date">'+dateStr+'</small></div><div class="chat-message active">'+content+'</div></div>')
    }else{
        $("#"+divId).append('<div class="right"><div class="author-name">'+userName+'&nbsp;<small class="chat-date">'+dateStr+'</small></div><div class="chat-message">'+content+'</div></div>')
    }
}