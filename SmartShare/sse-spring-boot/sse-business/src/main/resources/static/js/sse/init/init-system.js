$(function () {
  signinBtn.click(function () {
    $.get(signinUrl + Math.random(), function (_html) {
      $.layer.showForm({
        title: initSystemTitle.signinTitle,
        content: _html,
        formId: '#signinForm',
        postType: 'json',
        btn1: function (index, layero) {
          opSignin(index, layero);
          return;
        }
      });
    });
  });
  signinBtn.click();
})
