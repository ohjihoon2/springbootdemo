var cnt = 1;
var loginBtn = true;

$(function(){
    // 로그인
    $('#loginForm').submit(function(event){
        event.preventDefault();
        if(loginBtn) {

            /*var data = {
                userId : $('#userId').val(),
                userPwd : $('#userPwd').val(),
                rememberMe : $('#rememberMe').val()
            }*/

            var data = $("form[name=loginForm]").serialize();
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
