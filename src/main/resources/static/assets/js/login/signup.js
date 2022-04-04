var cnt = 1;
var signupBtn = false;

$(function(){
    //아이디 관련
    userIdChk();
    
    //패스워드 관련
    userPwChk();

    //닉네임 관련
    userNnChk();

    //이메일 관련
    userEmChk();

    // 약관동의
    $('#agreeAll').change(function () {
        if($('#agreeAll').is(":checked")) {
            $('#agreeBox input[type=checkbox]').prop("checked", true);
        }
        else {
            $('#agreeBox input[type=checkbox]').prop("checked", false);
        }
    });

    // 회원가입
    $('#signupForm').submit(function(event){
        event.preventDefault();
        if($event.validationFocus("userId")) return;
        if($event.validationFocus("userPwd")) return;
        if($event.validationFocus("userPwdChk")) return;
        if($event.validationFocus("userNm")) return;
        if($event.validationFocus("userNicknm")) return;
        if($event.validationFocus("userEmail1")) return;
        if($event.validationFocus("userEmail2")) return;
        if($event.validationFocus("userPhone1")) return;
        if($event.validationFocus("userPhone2")) return;
        if($event.validationFocus("userPhone3")) return;

        if(signupBtn) {
            var data = {
                userId : $('#userId').val(),
                userPwd : $('#userPwd').val()
            }
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
});

//유저 아이디 관련
function userIdChk() {
    $('#userId').keyup(function(){
        $('#idMsg').html("");
    });

    $('#userId').focusout(function(){
        if($('#idMsg').html() == "" && $('#userId').val() != "") {
            if($('#userId').val().length < 5) {
                $('#idMsg').html("<label class=\"mb10\"></label> 아이디를 5자 이상입력해주세요.");
            }
            else if(!$util.isId($('#userId').val())) {
                $('#idMsg').html("<label class=\"mb10\"></label> 아이디는 영문 소문자, 숫자 만 사용가능합니다.");
            }
            else {
                var data = {
                    userId : $('#userId').val(),
                }

                var res = $ajax.postAjax('/checkId', data);

                if (res == "fail") {
                    $('#idMsg').html("<label class=\"mb10\"></label> 이미 사용중인 아이디입니다.");
                }
            }
        }
    });
}

//유저 비밀번호 관련
function userPwChk() {
    $('#userPwd').keyup(function(){
        $('#pwMsg').html("");
    });

    $('#userPwd').focusout(function(){
        if($('#userPwd').html() == "" && $('#userPwd').val() != "") {
            if($('#userPwd').val().length < 5) {
                $('#pwMsg').html("<label class=\"mb10\"></label> 비밀번호를 5자 이상입력해주세요.");
            }
            else if(!$util.isPw($('#userPwd').val())) {
                $('#pwMsg').html("<label class=\"mb10\"></label> 비밀번호는 영문, 숫자, 특수문자 를 포함해야합니다.");
            }
        }
    });
}

//유저 닉네임 관련
function userNnChk() {
    $('#userNicknm').keyup(function(){
        $('#nnMsg').html("");
    });

    $('#userNicknm').focusout(function(){
        if($('#nnMsg').html() == "" && $('#userNicknm').val() != "") {
            if($('#userNicknm').val().length < 2) {
                $('#nnMsg').html("<label class=\"mb10\"></label> 닉네임을 2자 이상 입력해주세요.");
            }
            else if($('#userNicknm').val().indexOf("관리자") != -1) {
                $('#nnMsg').html("<label class=\"mb10\"></label> '관리자' 단어는 포함 할수 없습니다.");
            }
            else if(!$util.isNn($('#userNicknm').val())) {
                $('#nnMsg').html("<label class=\"mb10\"></label> 테스트");
            }
            else {
                var data = {
                    userNicknm : $('#userNicknm').val(),
                }

                var res = $ajax.postAjax('/checkNicknm', data);

                if (res == "fail") {
                    $('#nnMsg').html("<label class=\"mb10\"></label> 이미 사용중인 닉네임입니다.");
                }
            }
        }
    });
}

//유저 이메일 관련
function userEmChk() {
    $('#userEmail1').keyup(function(){
        $('#emMsg').html("");
    });

    $('#emailGroup').click(function(){
        $('#emMsg').html("");
    });

    $('#userEmail1').focusout(function(){
        ajaxuserEmChk()
    });

    $('#userEmail2').focusout(function(){
        ajaxuserEmChk()
    });

    // 이메일 선택
    $('#emailGroup').change(function () {
        $('#userEmail2').val($('#emailGroup').val());
        if($('#userEmail2').val() == "") {
            $("#userEmail2").attr("readonly",false);
        }
        else {
            $("#userEmail2").attr("readonly",true);
        }
        ajaxuserEmChk()
    });
}

// 이메일 중복확인
function ajaxuserEmChk() {
    if($('#userEmail1').val() != "" && $('#userEmail2').val() != "") {
        var data = {
            userEmail : $('#userEmail1').val() + "@" + $('#userEmail2').val(),
        }
        console.log(data);

        var res = $ajax.postAjax('/checkEmail', data);

        if (res == "fail") {
            $('#emMsg').html("<label class=\"mb10\"></label> 이미 사용중인 닉네임입니다.");
        }
    }
}