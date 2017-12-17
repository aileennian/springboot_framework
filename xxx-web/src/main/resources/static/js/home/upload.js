$("#submitBtn").click(function(){
    var url = "/upload";
    var data = {
        name:"1233"
    };
    $("#submitForm").ajaxSubmit({
        type: "POST",
        url: url,
        data: data,
        success: function(msg){
        	$.alert(msg);
        },
        error: function(msg){
            $.alert("error.异常了！");
        }
    });
});