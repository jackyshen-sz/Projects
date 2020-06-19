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
            message: signinMessage.emailIsInvalid
          }
        }
      },
      captcha: {
        message: globalMessage.kaptchIsInvalid,
        validators: {
          notEmpty: {
            message: globalMessage.kaptchIsInvalid
          },
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
