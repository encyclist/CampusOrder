$(document).ready(function () {
    querySubmit(1);
    $('#productImg').change(function () {
        uploadProductImg();
    });
});

// 清空所有查询框
function clearForm() {
    $('#div_searchUserList form')[0].reset();
    querySubmit(1);
}

/**
 * 查询分页
 * @param pageNum 页数
 */
function querySubmit(pageNum) {
    var pageData = {
        pageNum: pageNum,
        name: $('#input_productName').val()
    };
    var pageInfo = ajaxdata(REQUEST_URL + "/product/selectProduct", pageData).data;
    $("#div_productTable").html(productTable(pageInfo));
    pageInfoBar(pageInfo, "div_pageBar");
}

/**
 * 拼接查询餐品列表
 * @param pageInfo 分页信息
 */
function productTable(pageInfo) {
    var list = pageInfo.list;
    var table = [];
    table.push("<table class=\"table table-striped\">");
    var tableFirstTr = "<tr class=\"table_tr_title\">\n" +
        "                    <td>序号</td>\n" +
        "                    <td>餐品名</td>\n" +
        "                    <td>单价</td>\n" +
        "                    <td>描述</td>\n" +
        "                    <td>图片</td>\n" +
        "                    <td>操作</td>\n" +
        "                </tr>";
    table.push(tableFirstTr);
    for (var i = 0; i < list.length; i++) {
        var num = (pageInfo.pageNum - 1) * pageInfo.pageSize + i + 1;
        var tableTr = [];
        tableTr.push("<tr>");
        // 序号
        tableTr.push("<td>" + num + "</td>");
        // 餐品名
        tableTr.push("<td>" + list[i].name + "</td>");
        // 单价
        tableTr.push("<td>" + list[i].price + "</td>");
        // 描述
        var productDescription = list[i].description.length > 20 ? list[i].description.substring(0, 21) + "..." : list[i].description;
        tableTr.push("<td>" + productDescription + "</td>");
        // 图片
        tableTr.push("<td><img class='img_blogTypeImg' src='" + IMG_URL + list[i].img + "'></td>");
        // 操作
        tableTr.push("<td>" +
            "<button data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"updateProduct(" + list[i].id + ")\" class=\"btn btn-info\">编辑</button>&nbsp;&nbsp;" +
            "<button onclick=\"deleteProduct(" + list[i].id + "," + pageInfo.pageNum + ")\" class=\"btn btn-danger\">删除</button></td>");

        tableTr.push("</tr>");
        table.push(tableTr.join(""));
    }
    table.push("</table>");
    return table.join("");
}

function addProductButton() {
    $('#modal_title_product').html("添加餐品");
    clearProductForm();
}

function addProduct() {
    if (($('#input_productImg').val() === "" || $('#input_productImg').val() === null) && !checkFile()) {
        alert('请选择餐品图片');
        return false;
    }
    var product = {
        id: $('#productId').val(),
        name: $('#productName').val(),
        price: $('#productPrice').val(),
        img: $('#input_productImg').val(),
        description: $('#productDescription').val()
    };
    var data = ajaxdata(REQUEST_URL + "/product/addProduct", product);
    alert(data.msg);
    if (data.code === 0) {
        $(".btn_product_close").click();
        querySubmit(1);
    }
}

// 上传博客类型封面图片 上传成功返回true，上传失败返回false
function uploadProductImg() {
    var file = checkFile();
    if (!file) {
        alert('请选择博客类型封面');
        return false;
    }

    // 构建form数据
    var formFile = new FormData();
    //把文件放入form对象中
    formFile.append("file", file);

    // ajax提交
    $.ajax({
        url: REQUEST_URL + "/product/uploadImg",
        data: formFile,
        type: "POST",
        dataType: "JSON",
        processData: false,		//用于对data参数进行序列化处理 这里必须false
        contentType: false, 	//必须
        async: false,
        success: function (result) {
            if (result.code === 0) {
                $('#input_productImg').attr("value", file.name);
                $('#img_productImg').attr("src", IMG_URL + file.name);
            }
        },
        error: function (result) {
            alert("ajax error");
        }
    });
}

// 检测是否选择文件，如果选择，返回文件对象;如果没选择，返回false
function checkFile() {

    // 获取文件对象(该对象的类型是[object FileList]，其下有个length属性)
    var fileList = $('#productImg')[0].files;
    // 如果文件对象的length属性为0，就是没文件
    if (fileList.length === 0) {
        console.log('没选择文件');
        return false;
    }
    return fileList[0];
}

/**
 * 编辑餐品
 * @param id 餐品id
 */
function updateProduct(id) {
    clearProductForm();
    $('#modal_title_product').html("修改餐品");
    var pageData = {
        pageNum: 1,
        id: id
    };
    var pageInfo = ajaxdata(REQUEST_URL + "/product/selectProduct", pageData).data;
    var product = pageInfo.list[0];
    $('#productId').attr('value', product.id);
    $('#productName').attr('value', product.name);
    $('#productPrice').attr('value', product.price);
    $('#input_productImg').attr("value", product.img);
    $('#img_productImg').attr("src", IMG_URL + product.img);
    $('#productDescription').html(product.description);
}

/**
 * 清空用户表单
 */
function clearProductForm() {
    $('#div_detailType_body form')[0].reset();
    $('#productId').attr('value', "");
    $('#productName').attr('value', "");
    $('#productPrice').attr('value', "");
    $('#input_productImg').attr("value", "");
    $('#img_productImg').removeAttr("src");
    $('#productDescription').html("");
}

function deleteProduct(id, pageNum) {
    if (confirm("确认删除该餐品？")) {
        alert(ajaxdata(REQUEST_URL+"/product/deleteProduct", id).msg);
        querySubmit(pageNum);
    }
}





