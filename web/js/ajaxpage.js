function ajaxdata(action, datastr) {
    var adata;
    $.ajax({
        async: false, // 同步
        url: action,
        data: JSON.stringify(datastr),
        contentType: 'application/json; charset=UTF-8',
        type: "POST",
        dataType: "json",
        success: function (data) {
            adata = data;
        },
        error: function (data) {
            if (data.status === 401) {
                console.log("未登录");
                window.parent.location.href = "/";
            } else {
                alert("ajax error");
            }
        }
    });
    return adata;
}