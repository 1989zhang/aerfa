/**
 * 通用方法封装处理
 */
(function ($) {
    $.extend({
    	// 表格封装处理
    	table: {
    		_option: {},
            _params: {},
            // 初始化表格
            init: function(options) {
                $.table._option = options;
                $.table._params = $.common.isEmpty(options.queryParams) ? $.table.queryParams : options.queryParams;
                _sortOrder = $.common.isEmpty(options.sortOrder) ? "asc" : options.sortOrder;
                _sortName = $.common.isEmpty(options.sortName) ? "" : options.sortName;
                $('#bootstrap-table').bootstrapTable({
                    url: options.url,                                   // 请求后台的URL
                	idField:$.common.defaultIdField(options.id),        //id列
                    contentType: "application/x-www-form-urlencoded",   // 编码类型
                    method: 'post',                                     // 请求方式
                    cache: false,                                       // 是否使用缓存
                    sortable: true,                                     // 是否启用排序
                    sortStable: true,                                   // 设置为 true 将获得稳定的排序
                    sortName: _sortName,                                // 默认初始的排序列名称
                    sortOrder: _sortOrder,                              // 默认初始的排序方式  asc 或者 desc
                    pagination: $.common.defaultTrue(options.pagination), // 是否显示分页，默认分页
                    pageNumber: 1,                                      // 初始化加载第一页，默认第一页
                    pageSize: $.common.defaultTen(options.pageSize),    // 每页的记录行数
                    pageList: [10, 25, 50,'All'],                       // 可供选择的每页的行数，All为显示所有为了导出
                    iconSize: 'outline',                                // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
        	        toolbar: $.common.defaultToolbarId(options.toolbarId),//不带'#'指定toolbar的id,即把新增、修改、删除等按钮用一行放到'导出'按钮前
        	        toolbarAlign: $.common.defaultToolbarAlign(options.toolbarAlign),//指定toolbar默认分布在表格左边，如果是右边则在'导出'后面
        	        sidePagination: "server",                           // 启用服务端分页 
                    search: false,                                      // 是否显示搜索框功能
                    showRefresh: $.common.defaultTrue(options.showRefresh), // 是否显示刷新按钮
        			showColumns: $.common.defaultTrue(options.showColumns), // 是否显示隐藏某列下拉框
        			showToggle: $.common.defaultTrue(options.showToggle),   // 是否显示详细视图和列表视图的切换按钮
        			showExport: $.common.defaultTrue(options.showExport),   // 是否支持导出文件
        			exportOptions:{fileName: $.common.defaultExportFileName(options.exportFileName)},//导出文件名
        			exportDataType: 'all',                                   //支持导出方式: 'basic','all','selected'，因为后台分页所以就只支持all
        			exportTypes:[ 'csv', 'txt', 'sql'],                      //导出文件类型
        			queryParams: $.table._params,                            // 传递参数
                    columns: options.columns,                                // 显示列信息
					onLoadSuccess: options.onLoadSuccess                     //数据加载完成后执行的方法
                });
            },
            // 查询分页往后台传参数条件封装
            queryParams: function(params) {
            	return {            // 传递参数查询参数
        			pageSize:       params.limit,
        			pageNum:        params.offset / params.limit + 1,
        			searchValue:    params.search,
        			orderByColumn:  params.sort,
        			isAsc:          params.order
        		}; 
            },
            // 搜索
            search: function(form) {
    		    var params = $("#bootstrap-table").bootstrapTable('getOptions');
    		    params.queryParams = function(params) {
    		        var search = {};
    		        $.each($("#" + form).serializeArray(), function(i, field) {
    		            search[field.name] = field.value;
    		        });
    		        search.pageSize = params.limit;
    		        search.pageNum = params.offset / params.limit + 1;
    		        search.searchValue = params.search;
    		        search.orderByColumn = params.sort;
    		        search.isAsc = params.order;
    		        return search;
    		    }
    		    $("#bootstrap-table").bootstrapTable('refresh', params);
    		},
            // 选中多列值
            selectColumns: function(column) {
            	return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
        	        return row[column];
        	    });
            },
            // 选中首列值
            selectFirstColumns: function() {
            	return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
        	        return row[$.table._option.columns[1].field];
        	    });
            },
            // 操作后刷新
            refresh: function() {
                $("#bootstrap-table").bootstrapTable('refresh', {
                    url: $.table._option.url,
                    silent: true
                });
            },
            //格式化bootstrap-table的数据字典列标签,是table格式且其他调用也可以放这里合适
            formatDicLabel:function(_datas, _value) {
            	var actions = [];
                $.each(_datas, function(index, _dic) {
                    if (_dic.code == _value) {
                    	actions.push(_dic.fullName);
                        return false;
                    }
                });
                return actions.join('');
            }
    	},
        // 表格树封装处理
        treeTable: {
        	_option: {},
            _treeTable: {},
            // 初始化表格
            init: function(options) {
                $.table._option = options;
                var treeTable = $('#bootstrap-treeTable').bootstrapTreeTable({
        	        url: options.url,                                       // 请求后台数据的URL（*）
                	id:$.common.defaultIdField(options.id),                 //id列,用于设置父子关系
        		    parentId : options.parentId,                            //用于设置父子关系
        	    	type: 'get',                                            // 请求方式（*）
        	        ajaxParams : {},                                        // 请求数据的ajax的data属性
        			expandColumn:$.common.defaultZero(options.expandColumn),// 在哪一列上面显示展开按钮,有checkbox要加1
        			striped : false,                                        // 是否各行渐变色
        			bordered : true,                                        // 是否显示边框
					showRefresh:$.common.defaultTrue(options.showRefresh),  //是否展示刷新按钮
					showColumns:$.common.defaultTrue(options.showColumns),  //是否展示内容列下拉框
        			expandAll : $.common.defaultTrue(options.expandAll),    // 是否全部展开
        	        columns: options.columns,                               //加载展示列
					onLoadSuccess: options.onLoadSuccess                    //数据加载完成后执行的方法
        	    });
                $.treeTable._treeTable = treeTable;
            },
            // 条件查询
            search: function(form) {
            	var params = {};
            	$.each($("#" + form).serializeArray(), function(i, field) {
            		params[field.name] = field.value;
		        });
            	$.treeTable._treeTable.bootstrapTreeTable('refresh', params);
            },
            // 操作后刷新
            refresh: function() {
            	$.treeTable._treeTable.bootstrapTreeTable('refresh');
            }
        },
        // 操作封装处理
        operate: {
        	//定义ajax提交方法：请求默认异步、默认请求方法post、默认返回数据json、data为{a:aa}类型
            sendAjax: function(url,requestAsync,httpMethodType,responseDataType,data,callBack) {
            	 var requestAsyncDefault = true; 
            	 var httpMethodTypeDefault = 'post';
            	 var responseDataTypeDefault = 'json';
            	 if(requestAsync != undefined){requestAsyncDefault = requestAsync;}
            	 if(httpMethodType != undefined && httpMethodType != ""){httpMethodTypeDefault = httpMethodType;}
        	     if(responseDataType != undefined && responseDataType != ""){responseDataTypeDefault = responseDataType;}
        	     $.ajax({ 
        	         url:url, 
        	         async:requestAsyncDefault,
        	         type:httpMethodTypeDefault,
        	         dataType:responseDataTypeDefault,          
        	         data:data, 
        	         success:callBack
        	     }); 
            },
        	// 提交数据
        	submit: function(url, type, dataType, data) {
        		$.modal.loading("正在处理中，请稍后...");
            	var config = {
        	        url: url,
        	        type: type,
        	        dataType: dataType,
        	        data: data,
        	        success: function(result) {
        	        	$.operate.ajaxSuccess(result);
        	        }
        	    };
        	    $.ajax(config)
            },
            //提交带文件的form数据
            submitFile: function(url, formId) {
				$.modal.loading("正在处理中，请稍后...");
				var formFileData = new FormData(document.getElementById(formId));
            	var config = {
        	        url: url,
        	        type: "post",
        	        dataType: "json",
        	        processData:false,//需设置为false，因为data值是FormData对象，不需要对数据做处理
        	        contentType:false,//需设置为false，因为是FormData对象，默认属性enctype="multipart/form-data"
        	        data: formFileData,
        	        success: function(result) {
        	        	$.operate.saveSuccess(result);
        	        }
        	    };
        	    $.ajax(config)
            },
            // post请求传输
            post: function(url, data) {
            	$.operate.submit(url, "post", "json", data);
            },
        	// 添加信息
            add: function(id) {
            	var url = $.table._option.createUrl.replace("{id}", id);
                $.modal.open("添加" + $.table._option.modalName, url);
            },
            // 添加信息 全屏
            addFull: function(id) {
            	var url = $.table._option.createUrl.replace("{id}", id);
                $.modal.openFull("添加" + $.table._option.modalName, url);
            },
            // 修改信息
            edit: function(id) {
            	var url = $.table._option.updateUrl.replace("{id}", id);
            	$.modal.open("修改" + $.table._option.modalName, url);
            },
            // 修改勾选中的信息
            editSelected: function() {
            	var rows = $.table.selectColumns($.common.defaultIdField($.table._option.id));
        		if (rows.length == 0) {
        			$.modal.alertWarning("请勾选一条记录修改");
        			return;
        		}
        		$.operate.edit(rows[0]);
            },
            // 修改信息 全屏
            editFull: function(id) {
            	var url = $.table._option.updateUrl.replace("{id}", id);
            	$.modal.openFull("修改" + $.table._option.modalName, url);
            },
            // 查看信息
            view: function(id) {
            	var url = $.table._option.viewUrl.replace("{id}", id);
            	$.modal.open("查看" + $.table._option.modalName, url);
            },
            // 查看信息 全屏
            viewFull: function(id) {
            	var url = $.table._option.viewUrl.replace("{id}", id);
            	$.modal.openFull("查看" + $.table._option.modalName, url);
            },
            // 删除列表信息
            remove: function(id) {
            	$.modal.confirm("确定删除该条" + $.table._option.modalName + "信息吗？", function() {
	            	var url = $.table._option.removeUrl.replace("{id}", id);
	            	var data = { "ids": id };
	            	$.operate.submit(url, "post", "json", data);
            	});
            },
            // 删除普通信息
            removeCommon: function(name,url,id) {
            	$.modal.confirm("确定删除" + name + "吗？", function() {
	            	var delUrl = url.replace("{id}", id);
	            	var data = { "ids": id };
	            	$.operate.submit(delUrl, "post", "json", data);
            	});
            },
            // 批量删除信息
            batRemove: function() {
        		var rows = $.table.selectColumns($.common.defaultIdField($.table._option.id));
        		if (rows.length == 0) {
        			$.modal.alertWarning("请至少勾选一条记录");
        			return;
        		}
        		$.modal.confirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
        			var data = { "ids": rows.join() };
        			var url = $.table._option.removeUrl.replace("{id}", rows.join());
        			$.operate.submit(url, "post", "json", data);
        		});
            },
            // 根据id和不同地址方法提交到后台(地址,唯一标记,操作提示)
            manage: function(url,id,tips,dataUrl) {
            	$.modal.confirm($.common.trim(tips), function() {
            		var data = { "id": id };
            		var subUrl=url;
            		if(!$.common.isEmpty(id)){
            			subUrl= url +"/"+id;
            		}
	            	$.ajaxSettings.async = false; 
	            	$.operate.submit(subUrl, "post", "json", data);
	            	//执行刷新指定页面方法
	            	if(!$.common.isEmpty(dataUrl)){
	            		refreshMenuItem(dataUrl)
	            	}
            	});
            },
			//多个参数组装成对象：并执行确认提交刷新的方法
			manageCommon:function(url,data,tips,dataUrl) {
				$.modal.confirm($.common.trim(tips), function() {
					$.ajaxSettings.async = false;
					$.operate.submit(url, "post", "json", data);
					//执行刷新指定页面方法
					if(!$.common.isEmpty(dataUrl)){
						refreshMenuItem(dataUrl)
					}
				});
			},
            // 保存信息
            save: function(url, data) {
            	$.modal.loading("正在处理中，请稍后...");
            	var config = {
        	        url: url,
        	        type: "post",
        	        dataType: "json",
        	        data: data,
        	        success: function(result) {
        	        	$.operate.saveSuccess(result);
        	        }
        	    };
        	    $.ajax(config)
            },
            // 保存结果弹出msg刷新table表格
            ajaxSuccess: function (result) {
            	if (result.code == web_status.SUCCESS) {
            		$.modal.msgRenovate(result.data, modal_status.SUCCESS);
                } else {
                	$.modal.alertError(result.msg);
                }
            	$.modal.closeLoading();
            },
            // 保存结果提示msg
            saveSuccess: function (result) {
            	if (result.code == web_status.SUCCESS) {
            		$.modal.msgReload(result.data, modal_status.SUCCESS);
                } else {
                	$.modal.alertError(result.msg);
                }
            	$.modal.closeLoading();
            }
        },
        // 弹出层封装处理
    	modal: { 
    		// 显示图标
    		icon: function(type) {
            	var icon = "";
        	    if (type == modal_status.WARNING) {
        	        icon = 0;
        	    } else if (type == modal_status.SUCCESS) {
        	        icon = 1;
        	    } else if (type == modal_status.FAIL) {
        	        icon = 2;
        	    } else {
        	        icon = 3;
        	    }
        	    return icon;
            },
    		// 消息提示
            msg: function(content, type) {
            	if (type != undefined) {
                    layer.msg(content, { icon: $.modal.icon(type), time: 1000, shift: 5 });
                } else {
                    layer.msg(content);
                }
            },
            // 错误消息，不点确认自动消失
            msgError: function(content) {
            	$.modal.msg(content, modal_status.FAIL);
            },
            // 成功消息，不点确认自动消失
            msgSuccess: function(content) {
            	$.modal.msg(content, modal_status.SUCCESS);
            },
            // 警告消息，不点确认自动消失
            msgWarning: function(content) {
            	$.modal.msg(content, modal_status.WARNING);
            },
    		// 弹出提示
            alert: function(content, type) {
        	    layer.alert('<font style="color:#333">'+content+'</font>', { 
        	        icon: $.modal.icon(type),
        	        title: "系统提示",
        	        btn: ['确认'],
        	        btnclass: ['btn btn-primary'],
        	    });
            },
            // 消息提示并刷新父窗体
            msgReload: function(msg, type) {
            	layer.msg(msg, {
            	    icon: $.modal.icon(type),
            	    time: 500,
            	    shade: [0.1, '#8F8F8F']
            	},
            	function() {
            	    $.modal.reload();
            	});
            },
            // 消息提示并刷新本窗体
            msgRenovate: function(msg, type) {
            	layer.msg(msg, {
            	    icon: $.modal.icon(type),
            	    time: 500,
            	    shade: [0.1, '#8F8F8F']
            	},
            	function() {
            	    $.modal.renovate();
            	});
            },
            // 错误提示，要点确认
            alertError: function(content) {
            	$.modal.alert(content, modal_status.FAIL);
            },
            // 成功提示，要点确认
            alertSuccess: function(content) {
            	$.modal.alert(content, modal_status.SUCCESS);
            },
            // 警告提示，要点确认
            alertWarning: function(content) {
            	$.modal.alert(content, modal_status.WARNING);
            },
            // 关闭窗体
            close: function () {
            	var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },
            // 确认窗体
            confirm: function (content, callBack) {
            	layer.confirm(content, {
        	        icon: 3,
        	        title: "系统提示",
        	        btn: ['确认', '取消'],
        	        btnclass: ['btn btn-primary', 'btn btn-danger'],
        	    }, function (index) {
        	    	layer.close(index);
        	        callBack(true);
        	    });
            },
            // 弹出层指定宽度
            open: function (title, url, width, height) {
            	if ($.common.isEmpty(title)) {
                    title = false;
                };
                if ($.common.isEmpty(url)) {
                    url = "/404";
                };
                if ($.common.isEmpty(width)) {
                	width = 800;
                };
                if ($.common.isEmpty(height)) {
                	height = ($(window).height() - 50);
                };
            	layer.open({
            		type: 2,
            		area: [width + 'px', height + 'px'],
            		fix: false,
            		//不固定
            		maxmin: true,
            		shade: 0.3,
            		title: title,
            		content: url
            	});
            },
            // 弹出层全屏
            openFull: function (title, url, width, height) {
            	if ($.common.isEmpty(title)) {
                    title = false;
                };
                if ($.common.isEmpty(url)) {
                    url = "/404";
                };
                if ($.common.isEmpty(width)) {
                	width = 800;
                };
                if ($.common.isEmpty(height)) {
                	height = ($(window).height() - 50);
                };
                var index = layer.open({
            		type: 2,
            		area: [width + 'px', height + 'px'],
            		fix: false,
            		//不固定
            		maxmin: true,
            		shade: 0.3,
            		title: title,
            		content: url
            	});
                layer.full(index);
            },
            // 打开遮罩层
            loading: function (message) {
            	$.blockUI({ message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>' });
            },
            // 关闭遮罩层
            closeLoading: function () {
            	setTimeout(function(){
            		$.unblockUI();
            	}, 50);
            },
            // 重新加载父窗体
            reload: function () {
            	parent.location.reload();
            },
            // 重新刷新本窗体
            renovate:function () {
            	location.reload();
            }
    	},
        // 通用方法封装处理
    	common: {
    		// 默认字段为id字符判断
    	    defaultIdField:function (value) {
                if (value == null || this.trim(value) == "") {
                    return "id";
                }
                return value;
            },
            //指定默认工作栏div的id为'toolbar'
            defaultToolbarId:function (value) {
            	 if (value == null || this.trim(value) == "") {
                     return "#toolbar";
                 }
                 return "#"+value;
            },
            //指定toolbar默认分布在表格左边
            defaultToolbarAlign:function (value) {
            	 if (value == null || this.trim(value) == "") {
                     return "left";
                 }
                 return value;
            },
    		// 默认为true判断
    	    defaultTrue:function (value) {
                if (value == null || this.trim(value) == "") {
                    return true;
                }
                return value;
            },
    		// 默认为false判断
            defaultFalse:function (value) {
                if (value == null || this.trim(value) == "") {
                    return false;
                }
                return value;
            },
			// 默认为0值返回
			defaultZero:function (value) {
				if (value == null || this.trim(value) == "") {
					return 0;
				}
				return value;
			},
			// 默认为10值返回,用于分页
			defaultTen:function (value) {
				if (value == null || this.trim(value) == "") {
					return 10;
				}
				return value;
			},
            // 默认导出文件名
            defaultExportFileName: function (value) {
                if (value == null || this.trim(value) == "") {
                	var date = new Date();
                    return date.getFullYear()+"-"+(date.getMonth()+1)+ "-"+date .getDate();
                }
                return value.toString();
            },
            // 判断字符串是否为空
            isEmpty: function (value) {
                if (value == null || this.trim(value) == "") {
                    return true;
                }
                return false;
            },
            // 空格截取
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            //转JSON的方法
            parseJson: function (data) {
            	return jQuery.parseJSON(data);
            },
            //毫秒数时间转化为普通日期格式例如：1578498572762 转化为 2020-01-08
            parseNormalDate: function (longTypeDate) {
            	var normalTypeDate = "";    
        	    var date = new Date();    
        	    date.setTime(longTypeDate);    
        	    normalTypeDate += date.getFullYear();   //年    
        	    //月
        	    var month = date.getMonth() + 1; //getMonth()得到的月份是0-11    
        	    if(month<10){    
        	        month = "0" + month;    
        	    }    
        	    normalTypeDate += "-" + month;//月
        	    //日
        	    var day = date.getDate();    
        	    if(day<10){    
        	        day = "0" + day;    
        	    }
        	    normalTypeDate += "-" + day;//日    
        	    return normalTypeDate;
            },
            //毫秒数时间转化位日期格式例如：1578498572762 转化为 2020-01-08 23:49:32
            parseFullDate: function (longTypeDate) {
            	var fullTypeDate = "";    
         	    var date = new Date();    
         	    date.setTime(longTypeDate);    
         	    fullTypeDate += $.common.parseNormalDate(longTypeDate);
         	    //小时
         	    var hours = date.getHours();  
       	        if(hours<10){    
       	            hours = "0" + hours;    
       	        }
       	        fullTypeDate += " " + hours;
       	        //分
         	    var minute = date.getMinutes();  
       	        if(minute<10){    
       	        	minute = "0" + minute;    
       	        }
       	        fullTypeDate += ":" + minute;
       	        //秒
         	    var second = date.getSeconds();  
       	        if(second<10){    
       	        	second = "0" + second;    
       	        }
       	        fullTypeDate += ":" + second;
       	        return fullTypeDate;
            },
            //获取当前普通日期,例如：2020-01-08
            currentNormalDate:function(){
            	return $.common.parseNormalDate(new Date().getTime());
            },
            //获取当前全面日期,例如：2020-01-08 23:49:32
            currentFullDate:function(){
            	return $.common.parseFullDate(new Date().getTime());
            },
            // 指定随机数返回
            random: function (min, max) {
                return Math.floor((Math.random() * max) + min);
            },
            //生成随机大小字符加数字字符串
            randomString: function (len) {
            	len = len || 32;
                var defaultChars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';//默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
                var maxPos = defaultChars.length;
                var retRandomString = '';
                for (i = 0; i < len; i++) {
                	retRandomString += defaultChars.charAt(Math.floor(Math.random() * maxPos));
                }
                return retRandomString;
            }
    	}
    });
})(jQuery);

//消息状态码
web_status = {
    SUCCESS: 0
};

//弹窗状态码 
modal_status = {
    SUCCESS: "success",
    FAIL: "error",
    WARNING: "warning"
};