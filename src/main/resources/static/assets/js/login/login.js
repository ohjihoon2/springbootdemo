var cnt = 1;
var loginBtn = true;

$(function(){
    // 로그인
    $('#loginForm').submit(function(event){
        event.preventDefault();
        if(loginBtn) {
            var data = $("form[name=login-form]").serialize();

            var res = $ajax.postAjax('/loginAjax', data);

            if(res.result == "success") {
                location.href = "/";
            }
            else{
                $('#loginMsg').text(res.message + " (" + cnt + ")");
                cnt++;
                loginBtn = false;
            }
        }
    });

    $('#loginForm input').focusin(function(){
        loginBtn = true;
    });

});
