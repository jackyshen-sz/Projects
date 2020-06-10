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
  showFormDialog: function (showOpt) {
    if (showOpt.btn && showOpt.btn.length > 1) {

    }
  }
};
