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
      }
    }
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
  $.ajax({
    url: signinForm.attr("action"),
    type: signinForm.attr("method"),
    cache : false,
    data: signinForm.serialize(),
    dataType : "json",
    success: function (data) {
      layer.close(loadIndex);
    }
  });
}
