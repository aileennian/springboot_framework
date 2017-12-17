$("#submitBtn").click(function(){
    var url = "/formdata";
    var data = new FormData(document.querySelector("form"));
    console.log(data.toString());
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        processData: false,  // 不处理数据
        contentType: false,   // 不设置内容类型
        success: function(msg){
            console.log('===' + msg);
        },
        error: function(msg){
            console.log('++++' + msg);
        }
    });
});
