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
      },
      captcha: {
        validators: {
          notEmpty: {
            message: signinMessage.emailIsRequired
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
