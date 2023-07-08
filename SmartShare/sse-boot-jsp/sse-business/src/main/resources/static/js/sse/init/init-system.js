$(function () {
  signinBtn.click(function () {
    $.get(signinUrl + Math.random(), function (_html) {
      $.layer.showForm({
        title: initSystemTitle.signinTitle,
        content: _html,
        formId: '#signinForm',
        postType: 'json',
        onPostSuccess: function (index, data) {
          $.layer.showAlert(data.message, function (index, layero) {
            window.location.href = contextPath;
          });
        }
      });
    });
  });
  signinBtn.click();
})
