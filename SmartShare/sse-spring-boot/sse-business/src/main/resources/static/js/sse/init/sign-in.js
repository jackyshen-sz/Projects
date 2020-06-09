$(function () {
  initkaptcha('#kaptchaImage');
  signinForm.bootstrapValidator({
    fields: {
      'paradmCompanyModel.companyName': {
        validators: {
          notEmpty: {
            message: signinMessage.companyNameIsRequired
          }
        }
      },
      'userModel.loginName': {
        validators: {
          notEmpty: {
            message: signinMessage.emailIsRequired
          },
          emailAddress: {
            message: signinMessage.emailIsValid
          }
        }
      }
    }
  }).on('success.form.bv', function (e) {
    console.log("success.form.bv");
  }).on('error.form.bv', function (e) {
    console.log("error.form.bv");
    layer.close(loadIndex);
  });
});

function opSignin(index, layero) {
  loadIndex = layer.load(1);
  var validator = signinForm.data('bootstrapValidator');
  if (validator) {
    validator.validate();
    if (!validator.isValid()) {
      layer.close(loadIndex);
      return;
    }
  }
  // check email config
  $.ajax({
    url: contextPath + 'init/system/check',
    cache : false,
    async : false,
    data : $(this).serialize(),
    success: function (data) {
      if (data.status === 'failed') {

      }
    }
  })
}
