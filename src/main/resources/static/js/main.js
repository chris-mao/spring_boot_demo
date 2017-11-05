$(document).ready(function() {
	$('#dg').datagrid({
		checkOnSelect:false,
		selectOnCheck:false,
		rownnumbers:true,
	    pagination:true,
	    striped:true,
        method:"get",
        fit:true,
	    pageSize:20,
	    pageList: [10,20,50,100]
	});
	jQuery.ajaxSetup({
		cache : false
	});
});

function addTab(title, href) {
	if ($('#workspace').tabs('exists', title)) {
		$('#workspace').tabs('select', title);
//		refreshTab({
//			tabTitle : title,
//			url : href
//		});
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="' + href
				+ '" style="width:100%;height:100%;padding:5px;"></iframe>';
		$('#workspace').tabs('add', {
			title : title,
			content : content,
			closable : true
		});
	}
}

function refreshTab(cfg) {
	var refresh_tab = cfg.tabTitle ? $('#workspace').tabs('getTab',
			cfg.tabTitle) : $('#workspace').tabs('getSelected');
	if (refresh_tab && refresh_tab.find('iframe').length > 0) {
		var _refresh_ifram = refresh_tab.find('iframe')[0];
		var refresh_url = cfg.url ? cfg.url : _refresh_ifram.src;
		// _refresh_ifram.src = refresh_url;
		_refresh_ifram.contentWindow.location.href = refresh_url;
	}
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
function DateTimeFormatter(value, rowData, rowIndex) {
	if (value == undefined) {
		return "";
	}
	/* json格式时间转js时间格式 */
	value = value.substr(1, value.length - 2);
	var obj = eval('(' + "{Date: new " + value + "}" + ')');
	var dateValue = obj["Date"];
	if (dateValue.getFullYear() < 1900) {
		return "";
	}

	return dateValue.format("yyyy-MM-dd hh:mm:ss");
}

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
