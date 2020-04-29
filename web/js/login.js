function userLogin() {
    var user = {
        userName: $("#form_login input[name='userName']").val(),
        password: $("#form_login input[name='password']").val()
    };
    console.log(JSON.stringify(user));
    var ajaxData = ajaxdata(REQUEST_URL + "/login", user);
    if (ajaxData.state === "success") {
        window.parent.location.href = "index.html";
    } else {
        alert(ajaxData.msg);
    }
}