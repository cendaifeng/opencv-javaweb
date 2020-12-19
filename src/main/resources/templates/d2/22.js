$("submit_btn").click(function () {
        var formData = [];
        var fileobjs = $("file")[0].files;  // $("#file")[0]将jquery对象转换为dom对象，使用jquery的方法.get(0)也可以
        for (var i = 0; i < fileobjs.length; i++) {
            formData.append("imgs", fileobjs[i]);  // append方法使用相同键追加元素，最后会被输出为MultipartFile数组
        }

        $.ajax({
            type: 'post',
            url: "/upload",
            async: false,  // 设置为同步执行
            data: formData,
            data: { userName: "zhang", passWord: "123"} ,
            processData : false,  // 禁止去处理发送的数据，对data参数进行序列化处理时须设置
            contentType : false,  // 禁止去设置Content-Type请求头
            success: function (res) {
                alert(res);
            },
            error: function (e) {
            }
        })
    });