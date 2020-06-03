var loadIndex;
$(function() {
  layer.config({
    extend: 'sse/paradm-layer.css',
    skin: 'ss-layer',
    move: false,
    resize: false,
    btnAlign: 'c'
  });
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $.ajaxSetup({
    beforeSend : function(xhr) {
      xhr.setRequestHeader(header, token);
    }
  });
});

$.layer = {
  show : function (showOpt) {
    var opt = $.extend({
      area: '600px',
      offset: '30px',
    }, showOpt);
    return layer.open(opt);
  }
};

String.prototype.endWith = function (s) {
  if (s == null || s === "" || this.length === 0 || s.length > this.length) {
    return false;
  }
  return this.substring(this.length - s.length) === s;
}

String.prototype.startWith = function (s) {
  if (s == null || s === "" || this.length === 0 || s.length > this.length) {
    return false;
  }
  return this.substr(0, s.length) === s;
}

Array.prototype.remove = function (obj) {
  for (var i = 0; i < this.length; i++) {
    var temp = this[i];
    if (!isNaN(obj)) {
      temp = i;
    }
    if (temp === obj) {
      for (var j = i; j < this.length; j++) {
        this[j] = this[j + 1];
      }
      this.length = this.length - 1;
    }
  }
}

Date.prototype.format = function (fmt) {
  var o = {
    "M+": this.getMonth() + 1, // Month
    "d+": this.getDate(), // date
    "h+": this.getHours(), // hours
    "m+": this.getMinutes(), // minutes
    "s+": this.getSeconds(), // seconds
    "q+": Math.floor((this.getMonth() + 3) / 3),
    "S": this.getMilliseconds()
    // 毫秒
  };
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  }
  for (var k in o) {
    if (new RegExp("(" + k + ")").test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    }
  }
  return fmt;
}

function formatFileSize(fileSize) {
  var fileSizeByte = fileSize;
  var fileSizeMsg = "";
  if (fileSizeByte < 1048576) {
    if ((fileSizeByte / 1024) < 0.005) {
      fileSizeMsg = fileSizeByte + "B";
    } else {
      fileSizeMsg = (fileSizeByte / 1024).toFixed(2) + "KB";
    }
  } else if (fileSizeByte === 1048576) {
    fileSizeMsg = "1MB";
  } else if (fileSizeByte > 1048576 && fileSizeByte < 1073741824) {
    if ((fileSizeByte / (1024 * 1024)) < 0.005) {
      fileSizeMsg = (fileSizeByte / 1024).toFixed(2) + "KB"
    } else {
      fileSizeMsg = (fileSizeByte / (1024 * 1024)).toFixed(2) + "MB";
    }
  } else if (fileSizeByte > 1048576 && fileSizeByte === 1073741824) {
    fileSizeMsg = "1GB";
  } else if (fileSizeByte > 1073741824 && fileSizeByte < 1099511627776) {
    if ((fileSizeByte / (1024 * 1024 * 1024)) < 0.005) {
      fileSizeMsg = (fileSizeByte / (1024 * 1024)).toFixed(2) + "MB";
    } else {
      fileSizeMsg = (fileSizeByte / (1024 * 1024 * 1024)).toFixed(2) + "GB";
    }
  } else {
    fileSizeMsg = "File over 1TB";
  }
  return fileSizeMsg;
}

function initkaptcha(selector) {
  var kaptchaUrl = contextPath + 'kaptcha/image?data=';
  $(selector).attr('src', kaptchaUrl + Math.floor(Math.random() * 100)).click(function(evt) {//generate captcha
    $(this).hide().prop('src', kaptchaUrl + Math.floor(Math.random() * 100)).fadeIn();
    evt.cancelBubble = true;
  });
}
