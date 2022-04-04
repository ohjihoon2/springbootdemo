var cnt = 1;
var loginBtn = true;

$(function(){
    // 로그인
    $('#loginForm').submit(function(event){
        event.preventDefault();

        //시큐리티로 인한 로그인로직 분리
        if(loginBtn) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            var data = $("form[name=loginForm]").serialize();

            $.ajax({
                type: "POST",
                url: '/loginAjax',
                data: data,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (response) {
                    if(response.result == "success") {
                        location.href = "/";
                    }
                    else{
                        $('#loginMsg').text(response.message + " (" + cnt + ")");
                        cnt++;
                        loginBtn = false;
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $('#loginMsg').text('네트워크 통신 실패, 관리자에게 문의해주세요.');
                    loginBtn = false;
                }
            });
        }
    });

    $('#loginForm input').focusin(function(){
        loginBtn = true;
    });

});
