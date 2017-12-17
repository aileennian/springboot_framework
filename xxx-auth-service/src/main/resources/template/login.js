(function() {
	$("#loginform_submit").click(function() {
		if (checkInput()) {

			$("form").action("/loginServlet");
		} else {
			return false;
		}
	});
});

$("#validationCode_img").click(
		function() {

			$("#validationCode_img").attr("src",
					"/userLogin/sample/validateCode?" + Math.random());
		});
function checkInput() {
	// 判断用户名
	if ($("input[name=username]").val() == null
			|| $("input[name=username]").val() == "") {
		alert("用户名不能等于空");
		$("input[name=username]").focus();
		return false;
	}
	// 判断密码
	if ($("input[name=password]").val() == null
			|| $("input[name=password]").val() == "") {
		alert("密码不能等于空");
		$("input[name=password]").focus();
		return false;
	}
	// 判断验证码
	if ($("input[name=validateCode]").val() == null
			|| $("input[name=validateCode]").val() == "") {
		alert("验证码不能为空");
		$("input[name=validateCode]").focus();
		return false;
	}
	return false;
}