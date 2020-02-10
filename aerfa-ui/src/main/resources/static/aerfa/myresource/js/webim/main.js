$(function(){
	layui.use('layim', function(layim){ 		
	    var sidIdentityStr = websocketWebimToken+personId;
		//layim基础配置初始化配置
		layim.config({
			//,brief: true //是否简约模式（若开启则不显示主面板）
		    title: personNickName, //自定义主面板最小化时的标题(取网名)
		    min: true, //是否始终最小化主面板，默认false
		    isAudio: false,//开启聊天工具栏音频
		    isVideo: false, //开启聊天工具栏视频
		    initSkin: '2.jpg', //1-5 设置初始背景
		    right: '0px', //主面板相对浏览器右侧距离,aerfa\myresource\css\webim\main.css重新设置
			minRight: '0px', //聊天面板最小化时相对浏览器右侧距离aerfa\myresource\css\webim\main.css重新设置  	
			copyright: false,//是否显示'关于',显示更为好看,已经把内容该为webim信息等2.1.0
			
			//初始化接口
		    init: {
		        url: baseUrl + 'webim/init/user_data',
		        data: {"sid": personId}
		    }
	
		    //查看群员接口
		    ,members: {
		      url: baseUrl + 'webim/init/group_members',
		      data: {}
		    }
		    
		    //上传图片接口
		    ,uploadImage: {
		       url: '/upload/image' //（返回的数据格式见下文）
		       ,type: '' //默认post
		    } 
		    
		    //上传文件接口
		    ,uploadFile: {
		       url: '/upload/file' //（返回的数据格式见下文）
		       ,type: '' //默认post
		    }
		    
		    //扩展工具栏
		    ,tool: [{
		      alias: 'code'
		      ,title: '代码'
		      ,icon: '&#xe64e;'
		    }]
		    //,skin: ['aaa.jpg'] //新增皮肤
		    //,isfriend: false //是否开启好友
		    //,isgroup: false //是否开启群组
		    //,notice: false //是否开启桌面消息提醒，默认false
		    //,voice: false //声音提醒，默认开启，声音文件为：default.mp3
		    
		    //,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
		    ,msgbox: baseUrl + 'webim/tips_info/to_msgbox/'+personId
		    //,find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
		    ,find: baseUrl + 'webim/friend/to_find/'+personId
		    ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可
		  });
	
		  /*layim.chat({
		    name: '在线客服-小苍'
		    ,type: 'kefu'
		    ,avatar: 'http://tva3.sinaimg.cn/crop.0.0.180.180.180/7f5f6861jw1e8qgp5bmzyj2050050aa8.jpg'
		    ,id: -1
		  });
		  layim.chat({
		    name: '在线客服-心心'
		    ,type: 'kefu'
		    ,avatar: 'http://tva1.sinaimg.cn/crop.219.144.555.555.180/0068iARejw8esk724mra6j30rs0rstap.jpg'
		    ,id: -2
		  });
		  layim.setChatMin();*/
	
		  //初始化WebSocket 
		  var socket;
          if(typeof(WebSocket) == "undefined") {  
              console.log("您的浏览器不支持WebSocket");  
          }else{  
              //实现化WebSocket对象，指定要连接的服务器地址与端口 建立连接  
              //等同于socket = new WebSocket("ws://localhost:8083/checkcentersys/websocket/20");  
              socket = new WebSocket((baseUrl+"websocket/"+sidIdentityStr).replace("http","ws"));
              //打开事件  
              socket.onopen = function() {  
                  console.log("您的浏览器支持WebSocket且已打开");
              };  
              //获得消息事件  
              socket.onmessage = function(msg) {
            	  //layim.msgbox(1); //模拟消息盒子有新消息，实际使用时，一般是动态获得
            	  layim.getMessage(JSON.parse(msg.data)); 
              };
              //关闭事件  
              socket.onclose = function() {  
                  console.log("Socket已关闭");  
              };  
              //发生了错误事件  
              socket.onerror = function() {  
                  alert("Socket发生了错误");  
              }    
          }
          
		  //监听在线状态的切换事件
		  layim.on('online', function(data){
			  console.log(data);
		  });
		  
		  //监听签名修改
		  layim.on('sign', function(value){
		      console.log(value);
		  });
	
		  //监听自定义工具栏点击，以添加代码为例
		  layim.on('tool(code)', function(insert){
		    layer.prompt({
		      title: '插入代码'
		      ,formType: 2
		      ,shade: 0
		    }, function(text, index){
		      layer.close(index);
		      insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
		    });
		  });
		  
		  //监听layim建立就绪
		  layim.on('ready', function(res){
			  //console.log(res.mine);
			  mainContextMenu();//初始化右键菜单
		      
		    
		    layim.msgbox(5); //模拟消息盒子有新消息，实际使用时，一般是动态获得
		  
		    //添加好友（如果检测到该socket）
		    layim.addList({
		      type: 'group'
		      ,avatar: "http://tva3.sinaimg.cn/crop.64.106.361.361.50/7181dbb3jw8evfbtem8edj20ci0dpq3a.jpg"
		      ,groupname: 'Angular开发'
		      ,id: "12333333"
		      ,members: 0
		    });
		    layim.addList({
		      type: 'friend'
		      ,avatar: "http://tp2.sinaimg.cn/2386568184/180/40050524279/0"
		      ,username: '冲田杏梨'
		      ,groupid: 2
		      ,id: "1233333312121212"
		      ,remark: "本人冲田杏梨将结束AV女优的工作"
		    });
		    
		    setTimeout(function(){
		      //接受消息（如果检测到该socket）
		      layim.getMessage({
		        username: "Hi"
		        ,avatar: "http://qzapp.qlogo.cn/qzapp/100280987/56ADC83E78CEC046F8DF2C5D0DD63CDE/100"
		        ,id: "10000111"
		        ,type: "friend"
		        ,content: "临时："+ new Date().getTime()
		      });
		      
		      /*layim.getMessage({
		        username: "贤心"
		        ,avatar: "http://tp1.sinaimg.cn/1571889140/180/40030060651/1"
		        ,id: "100001"
		        ,type: "friend"
		        ,content: "嗨，你好！欢迎体验LayIM。演示标记："+ new Date().getTime()
		      });*/
		      
		    }, 3000);
		  });
	
		  //监听发送消息
		  layim.on('sendMessage', function(data){
			  socket.send(JSON.stringify({
        		  message:data
              }));
		  });
	
		  //监听查看群员
		  layim.on('members', function(membersData){
		      membersContextMenu();
		  });
		  
		  //监听聊天窗口的切换
		  layim.on('chatChange', function(res){
		      var type = res.data.type;
		      console.log(res.data.id)
		      if(type === 'friend'){
		          //模拟标注好友状态
		          //layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
		      } else if(type === 'group'){
		          //模拟系统消息
		          layim.getMessage({
		             system: true
		             ,id: res.data.id
		             ,type: "group"
		             ,content: '模拟群员'+(Math.random()*100|0) + '加入群聊'
		          });
		       }
		  });
		
		  //自定义socket-event事件,拓展点击事件
		  $('body').on('click', '*[socket-event]', function(e){
              var othis = $(this), methid = othis.attr('socket-event');
              var funContent=eval(methid);
              new funContent();
          }); 
	});
});

