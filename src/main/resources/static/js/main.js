$(document).ready(function() {
	jQuery.ajaxSetup({
		cache : false
	});
});

function addTab(title, href) {
	if ($('#workspace').tabs('exists', title)) {
		$('#workspace').tabs('select', title);
	} else {
		$('#workspace').tabs('add', {
			title : title,
			href : href,
			fit : true,
			closable : true
		});
	}
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

/**
 * 用于格式化datagrid中的available字段
 * 
 * @param value
 *            字段的值
 * @param rowData
 *            行的记录数据
 * @param rowIndex
 *            行的记录数据
 * @returns
 */
function availableFormatter(value, rowData, rowIndex) {
	if (value == true) {
		return "启用";
	}
	return "禁用";
}

/**
 * 用于格式化datagrid中的日期字段
 * 
 * @param value
 *            字段的值
 * @param rowData
 *            行的记录数据
 * @param rowIndex
 *            行的记录数据
 * @returns
 */
function dateFormatter(value, rowData, rowIndex) {
	if ((value == "") || (value == undefined)) {
		return "";
	}
	var dateValue = new Date(value);
	// return dateValue.toLocaleString();
	return dateValue.Format("yyyy-MM-dd");
}

/**
 * 用于格式化datagrid中的日期时间字段
 * 
 * @param value
 *            字段的值
 * @param rowData
 *            行的记录数据
 * @param rowIndex
 *            行的记录数据
 * @returns
 */
function dateTimeFormatter(value, rowData, rowIndex) {
	if ((value == "") || (value == undefined)) {
		return "";
	}
	var dateValue = new Date(value);
	return dateValue.Format("yyyy-MM-dd hh:mm:ss");
}

/**
 * 用于格式化datagrid中的操作栏位
 * 
 * @param value
 *            字段的值
 * @param rowData
 *            行的记录数据
 * @param rowIndex
 *            行的记录数据
 * @returns
 */
function actionColumnFormatter(value, row, index) {
	if (row.editing) {
		var saveBtn = '<a href="javascript:void(0)" onclick="saveRow(this)">保存</a> ';
		var cancelBtn = '<a href="javascript:void(0)" onclick="cancelRow(this)">取消</a>';
		return saveBtn + cancelBtn;
	} else {
		var editBtn = '<a href="javascript:void(0)" onclick="editRow(this)">编辑</a> ';
		var deleteBtn = '<a href="javascript:void(0)" onclick="deleteRow(this)">删除</a>';
		return editBtn + deleteBtn;
	}
}

//获取当前行的索引
function getRowIndex(target) {
	var tr = $(target).closest("tr.datagrid-row");
	return parseInt(tr.attr("datagrid-row-index"));
}

//打开修改密码对话框
function changePsd(userId, userName) {
	post_url = "/users/rest/" + userId + "/psd";
	$("#changePasswordDlg").dialog("open").dialog("center");
	$("#changePasswordForm").form("clear");
	$("#changePasswordDlg").form("load", {userName: userName});
	$("#changePasswordDlg").dialog("open").dialog("center");
}

//保存密码
function savePassword() {
	console.log(post_url);
	$("#changePasswordForm").form("submit", {
		url : post_url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(data, textStatus) {
			console.log(data);
			console.log(textStatus);
			var data = eval('(' + data + ')'); //将字符串转为JSON对象
			if (data.state == 0) {
				$.messager.alert("消息", "密码修改成功！", "info");
				$("#changePasswordDlg").dialog("close"); // close the dialog
			} else {
				$.messager.alert("错误", data.message, "error");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("status: " + textStatus);
			console.log("error: " + errorThrown);
		}
	});
}

//返回前一身份
function switchBack() {
    $.ajax({
        url : "/users/rest/switch-back",
        type : "GET",
        success : function(data, textStatus) {
            console.log(data);
            console.log(textStatus);
            if (data.state == 0) {
            	$.messager.confirm("信息", data.message + " 是否立即刷新页面？", function(r) {
                    if (r) {
                        window.location.reload();
                    }
                });
            } else {
                $.messager.alert("错误", data.message, "error");
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log("status: " + textStatus);
            console.log("error: " + errorThrown);
        }
    });
}

$.extend($.fn.datagrid.defaults.editors, {
	datebox : {
		init : function(container, options) {
			var input = $('<input type="text">').appendTo(container);
			input.datebox(options);
			return input;
		},
		destroy : function(target) {
			$(target).datebox('destroy');
		},
		getValue : function(target) {
			return $(target).datebox('getValue');
		},
		setValue : function(target, value) {
			$(target).datebox('setValue', dateFormatter(value));
		},
		resize : function(target, width) {
			$(target).datebox('resize', width);
		}
	},
	datetimebox : {
		init : function(container, options) {
			var input = $('<input type="text">').appendTo(container);
			input.datetimebox(options);
			return input;
		},
		destroy : function(target) {
			$(target).datetimebox('destroy');
		},
		getValue : function(target) {
			return $(target).datetimebox('getValue');
		},
		setValue : function(target, value) {
			$(target).datetimebox('setValue', dateTimeFormatter(value));
		},
		resize : function(target, width) {
			$(target).datetimebox('resize', width);
		}
	}
});

/**
 * 金额按千位逗号分割
 * 
 * @character_set UTF-8
 * @author Jerry.li(hzjerry@gmail.com)
 * @version 1.2014.08.24.2143 Example <code> 
 *      alert($.formatMoney(1234.345, 2)); //=>1,234.35 
 *      alert($.formatMoney(-1234.345, 2)); //=>-1,234.35 
 *      alert($.unformatMoney(1,234.345)); //=>1234.35 
 *      alert($.unformatMoney(-1,234.345)); //=>-1234.35 
 *  </code>
 */
!(function($) {
	$.extend({
		/**
		 * 数字千分位格式化
		 * 
		 * @public
		 * @param mixed
		 *            mVal 数值
		 * @param int
		 *            iAccuracy 小数位精度(默认为2)
		 * @return string
		 */
		formatMoney : function(mVal, iAccuracy) {
			var fTmp = 0.00;// 临时变量
			var iFra = 0;// 小数部分
			var iInt = 0;// 整数部分
			var aBuf = new Array(); // 输出缓存
			var bPositive = true; // 保存正负值标记(true:正数)
			/**
			 * 输出定长字符串，不够补0
			 * <li>闭包函数</li>
			 * 
			 * @param int
			 *            iVal 值
			 * @param int
			 *            iLen 输出的长度
			 */
			function funZero(iVal, iLen) {
				var sTmp = iVal.toString();
				var sBuf = new Array();
				for (var i = 0, iLoop = iLen - sTmp.length; i < iLoop; i++)
					sBuf.push('0');
				sBuf.push(sTmp);
				return sBuf.join('');
			}
			;

			if (typeof (iAccuracy) === 'undefined')
				iAccuracy = 2;
			bPositive = (mVal >= 0);// 取出正负号
			fTmp = (isNaN(fTmp = parseFloat(mVal))) ? 0 : Math.abs(fTmp);// 强制转换为绝对值数浮点
			// 所有内容用正数规则处理
			iInt = parseInt(fTmp); // 分离整数部分
			iFra = parseInt((fTmp - iInt) * Math.pow(10, iAccuracy) + 0.5); // 分离小数部分(四舍五入)
			do {
				aBuf.unshift(funZero(iInt % 1000, 3));
			} while ((iInt = parseInt(iInt / 1000)));
			aBuf[0] = parseInt(aBuf[0]).toString();// 最高段区去掉前导0
			return ((bPositive) ? '' : '-') + aBuf.join(',') + '.'
					+ ((0 === iFra) ? '00' : funZero(iFra, iAccuracy));
		},

		/**
		 * 将千分位格式的数字字符串转换为浮点数
		 * 
		 * @public
		 * @param string
		 *            sVal 数值字符串
		 * @return float
		 */
		unformatMoney : function(sVal) {
			var fTmp = parseFloat(sVal.replace(/,/g, ''));
			return (isNaN(fTmp) ? 0 : fTmp);
		},
	});
})(jQuery);
