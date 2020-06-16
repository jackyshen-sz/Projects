var loadIndex;
$(function() {
  layer.config({
    extend: 'sse/paradm-layer.css',
    skin: 'ss-layer',
    move: false,
    resize: false,
    btnAlign: 'c'
  });
});

$.layer = {
  show : function (showOpt) {
    var opt = $.extend({
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
          $.layer.postForm(showOpt, index, layero);
        }
      }
    }
    return this.show(showOpt);
  },
  postForm: function (showOpt, index, layero) {
    var postform = $(showOpt.formId);
    var validator = postform.data('bootstrapValidator');
    if (validator) {
      validator.validate();
      if (!validator.isValid()) {
        layer.close(loadIndex);
        return;
      }
    }
    if (showOpt.postType === 'multipart') {

    } else if (showOpt.postType === 'form') {
      postform.submit();
    } else {
      $.ajax({
        url: postform.attr('action'),
        type: postform.attr('method'),
        cache : false,
        data : $(this).serializeArray(),
        dataType : "json",
        success: function (data) {
          if (data.status === 'failed') {

          }
        }
      });
    }
  }
};
