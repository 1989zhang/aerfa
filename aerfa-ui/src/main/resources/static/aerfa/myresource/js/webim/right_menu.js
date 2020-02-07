/** 主页面定义右键操作 */
function mainContextMenu(){
	//联系人的好友右键方法
    var my_spread = $('.layim-list-friend >li');
    my_spread.mousedown(function(e){
        var data = {
            contextItem: "context-friend", // 添加class
            target: function(ele) { // 当前元素
                $(".context-friend").attr("data-id",ele[0].id.replace(/[^0-9]/ig,"")).attr("data-name",ele.find("span").html());
                $(".context-friend").attr("data-img",ele.find("img").attr('src')).attr("data-type",'friend');
            },
            menu:[]
        };
        data.menu.push(menuChat());//好友:右键发送消息
        data.menu.push(menuInfo());//好友:右键查看资料
        data.menu.push(menuChatLog());//好友:右键聊天记录
        data.menu.push(menuNickName());//好友:右键修改好友备注
        //当至少有两个分组时,才能移动好友
        var currentGroupidx = $(this).find('h5').data('groupidx');//当前分组id
        if(my_spread.length >= 2){
            var html = '<ul>';
            for (var i = 0; i < my_spread.length; i++) {
                var groupidx = my_spread.eq(i).find('h5').data('groupidx');
                if (currentGroupidx != groupidx) {
                    var groupName = my_spread.eq(i).find('h5 span').html();
                    html += '<li class="ui-move-menu-item" data-groupidx="'+groupidx+'" data-groupName="'+groupName+'"><a href="javascript:void(0);"><span>'+groupName+'</span></a></li>'
                };
            };
            html += '</ul>';
            data.menu.push(menuMove(html));//好友:右键移动好友到分组       
        }
        data.menu.push(menuRemove());//好友:右键删除好友
        $(".layim-list-friend >li > ul > li").contextMenu(data);//好友右键事件
    });
    
    //联系人的群右键方法
    $(".layim-list-friend >li > h5").mousedown(function(e){
            var data = {
                contextItem: "context-mygroup", // 添加class
                target: function(ele) { // 当前元素
                	console.log(ele);
                    $(".context-mygroup").attr("data-id",ele.data('groupidx')).attr("data-name",ele.find("span").html());
                },
                menu: []                        
            };             
            data.menu.push(menuAddMyGroup());//好友群:新增自己好友群
            data.menu.push(menuRename());//好友群:重命名自己好友群
            data.menu.push(menuDelMyGroup());//删除我的好友群，后台判断第一个系统自建的'我的好友'不允许删除
            
        $(this).contextMenu(data);  //好友分组右键事件                                  
    });

    //群组普通群右键菜单
    $(".layim-list-group > li").mousedown(function(e){
            var data = {
                contextItem: "context-group", // 添加class
                target: function(ele) { // 当前元素
                    $(".context-group").attr("data-id",ele[0].id.replace(/[^0-9]/ig,"")).attr("data-name",ele.find("span").html())
                    .attr("data-img",ele.find("img").attr('src')).attr("data-type",'group')   
                },
                menu: []                        
            };             
            data.menu.push(menuChat());//普通群:右键发送消息
            data.menu.push(menuInfo());//普通群:右键查看群资料
            data.menu.push(menuChatLog());//普通群:右键聊天记录
            data.menu.push(menuQuitGroupBySelf());//普通群:退出该群                  
        $(this).contextMenu(data);  //面板群组右键事件                                 
    });
}

/** 群组普通群,聊天页面群组右键事件 */
function membersContextMenu(){
    var imCacheData =  layui.layim.cache();//im各种缓存信息
    var groupInfo = layui.layim.thisChat().data;//当前群组信息
    
    $('.layim-members-list > li').mousedown(function(e){
        var data = {
            contextItem: "context-group-member", // 添加class
            target: function(ele) { // 当前元素
                $(".context-group-member").attr("data-id",ele[0].id.replace(/[^0-9]/ig,""));
                $(".context-group-member").attr("data-img",ele.find("img").attr('src'));
                $(".context-group-member").attr("data-name",ele.find("cite").html());
                $(".context-group-member").attr("data-groupidx",groupInfo.id);
                $(".context-group-member").attr("data-type",'friend');
            },
            menu:[]
        };
        
        var _this = $(this);
        var memberId=Number(_this.attr('id').replace(/[^0-9]/ig,""));
        //非普通群的本人才可以如下
        if (imCacheData.mine.id !== memberId) {
            data.menu.push(menuChat());//聊天
            data.menu.push(menuInfo());//查看信息
            data.menu.push(menuAddFriend());//添加好友                                              
        }else{
        	//只有是本人才能修改所在群昵称
            data.menu.push(menuEditGroupNickName());//修改本人在此群昵称
        }
        
        //群主拥有者管理菜单
        if (groupInfo.owner == imCacheData.mine.id && imCacheData.mine.id !== memberId) {//是群主且操作的对象不是自己
            data.menu.push(menuEditGroupNickName());//群组修改别人在此群昵称
            data.menu.push(menuLeaveGroup());//剔除本群
            /*if (_gagTime < _time) {
                data.menu.push(menuGroupMemberGag());//禁言，后续开发
            }else{
                data.menu.push(menuLiftGroupMemberGag());//取消禁言，后续开发
            }*/    
        }
        $(".layim-members-list > li").contextMenu(data);    
    });
}

/** 右键：发送消息 */
function menuChat(){
    return data = {
                    text: "发送消息",
                    icon: "&#xe63a;",
                    callback: function(ele) {
                        var othis = ele.parent(),type = othis.data('type'),
                            name = othis.data('name'),avatar = othis.data('img'),
                            id = othis.data('id');
                        layui.layim.chat({
                            name: name
                            ,type: type
                            ,avatar: avatar
                            ,id: id
                        });
                    }
                }       
}

/** 查看资料:包括个人好友群组等 */
function menuInfo(){
    return data = {
                    text: "查看资料",
                    icon: "&#xe62a;",
                    callback: function(ele) {
                        var othis = ele.parent(),type = othis.data('type'),id = othis.data('id');
                        getInformation({
                            id: id,
                            type:type
                        });                        
                    }
                }        
}

/** 资料显示页面 */
function getInformation(data){
	 var imCacheData =  layui.layim.cache();//im各种缓存信息
     var id = data.id || {},type = data.type || {};
     var index = layer.open({
         type: 2
         ,title: type  == 'friend'?(imCacheData.mine.id == id?'我的资料':'好友资料') :'群资料'
         ,shade: false
         ,maxmin: false
         ,closeBtn: 1
         ,area: ['400px', '650px']
         ,skin: 'layui-box layui-layer-border'
         ,resize: true
         ,content: baseUrl + '/webim/person/to_information'+'?id='+id+'&type='+type
     });           
}

/** 显示本人资料(socket-event)*/
function getMyInformation(){
	var imCacheData =  layui.layim.cache();//im各种缓存信息
	var myData={};
	myData.id=imCacheData.mine.id;
	myData.type='friend';
	getInformation(myData);
}

function menuChatLog(){
    return data =  {
                    text: "聊天记录",
                    icon: "&#xe60e;",
                    callback: function(ele) {
                        var othis = ele.parent(),type = othis.data('type'),name = othis.data('name'),
                        id = othis.data('id');
                        im.getChatLog({
                            name: name,
                            id: id,
                            type:type
                        });    
                    }
                }       
}

function menuNickName(){
    return data =  {
                    text: "修改好友备注",
                    icon: "&#xe6b2;",
                    callback: function(ele) {
                        var othis = ele.parent(),friend_id = othis.data('id'),friend_name = othis.data('name');
                        layer.prompt({title: '修改备注姓名', formType: 0,value: friend_name}, function(nickName, index){
                            $.get('class/doAction.php?action=editNickName',{nickName:nickName,friend_id:friend_id},function(res){
                                var data = eval('(' + res + ')');
                                if (data.code == 0) {                                              
                                    var friendName = $("#layim-friend"+friend_id).find('span');
                                    friendName.html(data.data);
                                    layer.close(index);
                                }
                                layer.msg(data.msg);
                            });
                        });     

                    }
                }     
}

function menuMove(html){
    return data = {
                    text: "移动联系人",
                    icon: "&#xe630;",
                    nav: "move",//子导航的样式
                    navIcon: "&#xe602;",//子导航的图标
                    navBody: html,//子导航html
                    callback: function(ele) {
                        var friend_id = ele.parent().data('id');//要移动的好友id
                        friend_name = ele.parent().data('name');
                        var avatar = '../uploads/person/'+friend_id+'.jpg';
                        var default_avatar = './uploads/person/empty2.jpg';
                        var signature = $('.layim-list-friend').find('#layim-friend'+friend_id).find('p').html();//获取签名
                        var item = ele.find("ul li");
                        item.hover(function() {
                            var _this = item.index(this);
                            var groupidx = item.eq(_this).data('groupidx');//将好友移动到分组的id
                            $.get('class/doAction.php?action=moveFriend',{friend_id:friend_id,groupidx:groupidx},function(res){
                                var data = eval('(' + res + ')');
                                if (data.code == 0) {
                                    conf.layim.removeList({//将好友从之前分组除去
                                        type: 'friend' 
                                        ,id: friend_id //好友ID
                                    });                                                          
                                    conf.layim.addList({//将好友移动到新分组
                                        type: 'friend'
                                        , avatar: im['IsExist'].call(this, avatar)?avatar:default_avatar //好友头像
                                        , username: friend_name //好友昵称
                                        , groupid: groupidx //所在的分组id
                                        , id: friend_id //好友ID
                                        , sign: signature //好友签名
                                    }); 
                                }
                                layer.msg(data.msg);
                            });                                                                                                                                        
                        });
                    }
                }       
}

function menuRemove(){
    return data = {
                text: "删除好友",
                icon: "&#xe640;",
                events: "removeFriends",
                callback: function(ele) {
                    var othis = ele.parent(),friend_id = othis.data('id'),username,sign;
                    layui.each(cachedata.friend, function(index1, item1){
                        layui.each(item1.list, function(index, item){
                            if (item.id === friend_id) {
                                username = item.username;
                                sign = item.sign;
                            }
                        });
                    });
                    layer.confirm('删除后对方将从你的好友列表消失，且以后不会再接收此人的会话消息。<div class="layui-layim-list"><li layim-event="chat" data-type="friend" data-index="0"><img src="./uploads/person/'+friend_id+'.jpg"><span>'+username+'</span><p>'+sign+'</p></li></div>', {
                        btn: ['确定','取消'], //按钮
                        title:['删除好友','background:#b4bdb8'],
                        shade: 0
                    }, function(){
                        im.removeFriends(friend_id); 
                    }, function(){
                        var index = layer.open();
                        layer.close(index);
                    });                                                    
                }
            }         
}

function menuAddMyGroup(){
    return  data =  { 
                    text: "添加分组",
                    icon: "&#xe654;",
                    callback: function(ele) {
                        im.addMyGroup();
                    }
                }

}

function menuRename(){
    return  data =  {
                text: "重命名",
                icon: "&#xe642;",
                callback: function(ele) {
                    var othis = ele.parent(),mygroupIdx = othis.data('id'),groupName = othis.data('name');
                    layer.prompt({title: '请输入分组名，并确认', formType: 0,value: groupName}, function(mygroupName, index){
                        if (mygroupName) {
                            $.get('class/doAction.php?action=editGroupName',{mygroupName:mygroupName,mygroupIdx:mygroupIdx},function(res){
                                var data = eval('(' + res + ')');
                                if (data.code == 0) {
                                    var friend_group = $(".layim-list-friend li");
                                    for(var j = 0; j < friend_group.length; j++){
                                        var groupidx = friend_group.eq(j).find('h5').data('groupidx');
                                        if(groupidx == mygroupIdx){//当前选择的分组
                                            friend_group.eq(j).find('h5').find('span').html(mygroupName);
                                        }
                                    }
                                    im.contextMenu();            
                                    layer.close(index);
                                }
                                layer.msg(data.msg);
                            });
                        }

                    });
                }

            }
}


function menuDelMyGroup(){
    return  data =  { 
                text: "删除该组",
                icon: "&#x1006;",
                callback: function(ele) {
                    var othis = ele.parent(),mygroupIdx = othis.data('id');
                    layer.confirm('<div style="float: left;width: 17%;margin-top: 14px;"><i class="layui-icon" style="font-size: 48px;color:#cc4a4a">&#xe607;</i></div><div style="width: 83%;float: left;"> 选定的分组将被删除，组内联系人将会移至默认分组。</div>', {
                        btn: ['确定','取消'], //按钮
                        title:['删除分组','background:#b4bdb8'],
                        shade: 0
                    }, function(){
                        im.delMyGroup(mygroupIdx); 
                    }, function(){
                        var index = layer.open();
                        layer.close(index);
                    });                      
                }
            }
}


function menuQuitGroupBySelf(){
    return  data =  {
                text: "退出该群",
                icon: "&#xe613;",
                callback: function(ele) {
                    var othis = ele.parent(),
                        group_id = othis.data('id'),  
                        groupname = othis.data('name');
                        avatar = othis.data('img');
                    layer.confirm('您真的要退出该群吗？退出后你将不会再接收此群的会话消息。<div class="layui-layim-list"><li layim-event="chat" data-type="friend" data-index="0"><img src="'+avatar+'"><span>'+groupname+'</span></li></div>', {
                        btn: ['确定','取消'], //按钮
                        title:['提示','background:#b4bdb8'],
                        shade: 0
                    }, function(){
                        var user = cachedata.mine.id;
                        var username = cachedata.mine.username;
                        im.leaveGroupBySelf(user,username,group_id);  
                    }, function(){
                        var index = layer.open();  
                        layer.close(index);
                    }); 
                }
            }
}

function menuAddFriend(){
    return  data =  { 
                        text: "添加好友",
                        icon: "&#xe654;",
                        callback: function(ele) {
                            var othis = ele;
                            addFriendGroup(othis,'friend');                                                   
                        }
                    }
}

function menuEditGroupNickName(){
    return  data =  {
                        text: "修改群名片",
                        icon: "&#xe60a;",
                        callback: function(ele) {
                            var othis = ele.parent();
                            im.editGroupNickName(othis);
                        }
                    }
}

/** 管理员从群踢出普通人员 */
function menuLeaveGroup(){
    return  data =  {
                        text: "踢出本群",
                        icon: "&#x1006;",
                        callback: function(ele) {
                            var othis = ele.parent();
                            var friend_id = ele.parent().data('id');//要禁言的id
                            var username = ele.parent().data('name');
                            var groupIdx = ele.parent().data('groupidx');
                            var list = new Array();
                            list[0] = friend_id;
                            im.leaveGroup(groupIdx,list,username)                                                          
                        }
                    }
}