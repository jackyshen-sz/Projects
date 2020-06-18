$(function () {
  signinBtn.click(function () {
    $.get(signinUrl + Math.random(), function (_html) {
      $.layer.showForm({
        title: initSystemTitle.signinTitle,
        content: _html,
        formId: '#signinForm',
        postType: 'json',
        onPostSuccess: function (index) {
          layer.alert('test', function (index) {
            window.location.href = contextPath;
          })
        }
      });
    });
  });
  signinBtn.click();
})
