$(function () {
  initkaptcha('#kaptchaImage');
});

function opSignin(index, layero) {
  loadIndex = layer.load(1);
  if (!checkSigninForm()) {
    layer.close(loadIndex);
    return;
  }
  $.ajax({
    url: "",
    type: "",
    success: function (data) {
      layer.close(loadIndex);
    }
  });
}

function checkSigninForm() {
  return false;
}
