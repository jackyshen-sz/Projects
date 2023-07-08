var loadIndex;
$(function() {
  layer.config({
    extend: 'sse/paradm-layer.min.css',
    skin: 'ss-layer',
    move: false,
    resize: false,
    btnAlign: 'c'
  });
});

$.form = {
  postForm: function (showOpt, index, layero) {
    console.log("postForm...");
    var postForm = $(showOpt.formId);
    var validator = postForm.data('bootstrapValidator');
    if (validator) {
      validator.validate();
      if (!validator.isValid()) {
        layer.close(loadIndex);
        return;
      }
    }
    if (showOpt.postType === 'multipart') {

    } else if (showOpt.postType === 'form') {
      postForm.submit();
    } else {
      $.ajax({
        url: postForm.attr('action'),
        type: postForm.attr('method'),
        cache : false,
        data : postForm.serializeArray(),
        dataType : "json",
        success: function (data) {
          layer.close(loadIndex);
          if (data.status === 'failed') {
            if ('N' === data.kaptchaValid) {
              postForm.bootstrapValidator("updateStatus", "captcha", "INVALID");
            } else {
              $.layer.showFail(data.message);
            }
          } else {
            $.layer.showSuccess(data.message);
            if (!showOpt.onPostSuccess) {
              layer.close(index);
            } else {
              showOpt.onPostSuccess(index, data);
            }
          }
        },
        error: function (jqXHR, statusText, error) {
          layer.close(loadIndex);
          $.layer.showFail(globalMessage.contactAdmin);
        }
      });
    }
  }
};

$.layer = {
  show : function (showOpt) {
    var opt = $.extend({
      type: 1,
      area: '600px',
      offset: '30px',
    }, showOpt);
    return layer.open(opt);
  },
  showForm: function (showOpt) {
    if (showOpt.readonly) {
      showOpt.btn = globalBtn.cancelBtn;
    } else {
      if (!showOpt.btn) {
        showOpt.btn = [globalBtn.okBtn, globalBtn.cancelBtn];
      }
      showOpt.btnCls = ['blue'];
      if (!showOpt.btn1) {
        showOpt.btn1 = function (index, layero) {
          loadIndex = layer.load(1);
          $.form.postForm(showOpt, index, layero);
        }
      }
    }
    return this.show(showOpt);
  },
  showMsg: function (content, showOpt) {
    var opt = $.extend({
      offset: '100px',
    }, showOpt);
    layer.msg(content, opt);
  },
  showSuccess: function (content) {
    var showOpt = {
      skin: 'ss-layer-msg success'
    };
    return this.showMsg(content, showOpt);
  },
  showFail: function (content) {
    var showOpt = {
      skin: 'ss-layer-msg fail',
    };
    return this.showMsg(content, showOpt);
  },
  showAlert: function (content, yes) {
    var opt = {
      offset: '30px',
      btn: globalBtn.okBtn,
      btnCls: 'blue'
    };
    layer.alert(content, opt, yes);
  }
};
