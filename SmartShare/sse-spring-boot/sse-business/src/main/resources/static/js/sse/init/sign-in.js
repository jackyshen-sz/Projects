$(function () {
  signinForm.submit(function (e) {
    console.log("submit...");
    debugger;
    if ($(this)[0].checkValidity() === false) {
      e.preventDefault();
      e.stopPropagation();
    }
    $(this).addClass("was-validated");
    layer.close(loadIndex);
    return false;
  });
  initkaptcha('#kaptchaImage');
});

function opSignin(index, layero) {
  loadIndex = layer.load(1);
  signinForm.submit();
}
