var cnt = 1;
var signupBtn = true;

$(function(){
    //아이디 중복확인 관련
    userIdChk();

    // 이메일 선택
    $('#emailGroup').change(function () {
        $('#userEmail2').val($('#emailGroup').val());
        if($('#userEmail2').val() == "") {
            $("#userEmail2").attr("readonly",false);
        }
        else {
            $("#userEmail2").attr("readonly",true);
        }
    })
    
    // 약관동의
    $('#agreeAll').change(function () {
        if($('#agreeAll').is(":checked")) {
            $('#agreeBox input[type=checkbox]').prop("checked", true);
        }
        else {
            $('#agreeBox input[type=checkbox]').prop("checked", false);
        }
    });

    // 로그인
    $('#signupForm').submit(function(event){
        event.preventDefault();
        if(signupBtn) {
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

    $('#login-form input').focusin(function(){
        loginBtn = true;
    });
});

//유저 아이디 관련
function userIdChk() {
    $('#userId').keyup(function(){
        $('#idMsg').html("");
    });

    $('#userId').focusout(function(){
        if($('#idMsg').html() == "" && $('#userId').val() != "") {
            var data = {
                userId : $('#userId').val(),
            }

            var res = $ajax.postAjax('/checkId', data);

            if (res == "fail") {
                $('#idMsg').html("<label class=\"mb10\"></label> 이미 사용중인 아이디입니다.");
            }
        }
    });
}
