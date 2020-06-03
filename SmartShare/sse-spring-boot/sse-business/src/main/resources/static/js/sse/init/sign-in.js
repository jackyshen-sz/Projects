$(function () {
  signinForm.submit(function () {
    console.log("submit...");
    return false;
  });
  initkaptcha('#kaptchaImage');
});

function opSignin(index, layero) {
  loadIndex = layer.load(1);
  signinForm.submit();
}
