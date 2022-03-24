$(function(){
    // 로그인
    $('#login-form').submit(function(event){
        event.preventDefault();

        var data = $("form[name=login-form]").serialize();

        var res = $ajax.postAjax('/loginAjax', data);

        if(res.result == "success") {
            location.href = "/";
        }
        else{
            alert(res.message);
        }
    });
});
