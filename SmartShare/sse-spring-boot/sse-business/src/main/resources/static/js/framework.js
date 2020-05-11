document.onkeydown = function(e) {
	var keycode = 0;
	var ev = e || event; // 获取event对象
	keycode = ev.keyCode || ev.which || ev.charCode; // 获取输入键值
	var obj = ev.target || ev.srcElement; // 获取事件源
	var t = obj.type || obj.getAttribute('type'); // 获取事件源类型
	if (keycode == 8 && obj.readOnly == true) {
		keycode = 0;
		event.returnValue = false;
		return false;
	}
	return true;
};

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

String.prototype.endwith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length) {
		return false;
	}
	if (this.substring(this.length - s.length) == s) {
		return true;
	} else {
		return false;
	}
	return true;
}

String.prototype.startwith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length) {
		return false;
	}
	if (this.substr(0, s.length) == s) {
		return true;
	} else {
		return false;
	}
	return true;
}

Array.prototype.remove = function(obj) {
	for (var i = 0; i < this.length; i++) {
		var temp = this[i];
		if (!isNaN(obj)) {
			temp = i;
		}
		if (temp == obj) {
			for (var j = i; j < this.length; j++) {
				this[j] = this[j + 1];
			}
			this.length = this.length - 1;
		}
	}
}

Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // Month
		"d+" : this.getDate(), // date
		"h+" : this.getHours(), // hours
		"m+" : this.getMinutes(), // minutes
		"s+" : this.getSeconds(), // seconds
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

(function($) {
	jQuery._evalUrl = function(url) {
		return jQuery.ajax({
			url : url,
			type : "GET",
			dataType : "script",
			async : false,
			global : false,
			"throws" : true,
			cache : true
		});
	};
})(jQuery);

function formatFileSize(fileSize) {
	var fileSizeByte = fileSize;
	var fileSizeMsg = "";
	if(fileSizeByte < 1048576) {
		if((fileSizeByte / 1024) < 0.005) {
			fileSizeMsg = fileSizeByte + "B";
		} else {
			fileSizeMsg = (fileSizeByte / 1024).toFixed(2) + "KB";
		}
	} else if(fileSizeByte == 1048576) {
		fileSizeMsg = "1MB";
	} else if(fileSizeByte > 1048576 && fileSizeByte < 1073741824) {
		if((fileSizeByte / (1024 * 1024)) < 0.005) {
			fileSizeMsg = (fileSizeByte / 1024).toFixed(2) + "KB"
		} else {
			fileSizeMsg = (fileSizeByte / (1024 * 1024)).toFixed(2) + "MB";
		}
	} else if(fileSizeByte > 1048576 && fileSizeByte == 1073741824) {
		fileSizeMsg = "1GB";
	} else if(fileSizeByte > 1073741824 && fileSizeByte < 1099511627776) {
		if((fileSizeByte / (1024 * 1024 * 1024)) < 0.005) {
			fileSizeMsg = (fileSizeByte / (1024 * 1024)).toFixed(2) + "MB";
		} else {
			fileSizeMsg = (fileSizeByte / (1024 * 1024 * 1024)).toFixed(2) + "GB";
		}
	} else {
		fileSizeMsg = "File over 1TB";
	}
	return fileSizeMsg;
}