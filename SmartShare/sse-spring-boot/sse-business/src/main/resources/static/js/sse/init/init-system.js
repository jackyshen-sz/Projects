$(function () {
  signinBtn.click(function () {
    $.get(signinUrl + Math.random(), function (_html) {
      $.layer.show({
        title: initSystemTitle.signinTitle,
        content: _html,
        btn: [globalBtn.okBtn, globalBtn.cancelBtn],
        btn1: function (index, layero) {
          opSignin(index, layero);
          return false;
        }
      });
    });
  });
  signinBtn.click();
})
