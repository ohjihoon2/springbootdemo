var signupBtn = true;

$(function(){
    //아이디 관련
    userIdChk();
    
    //패스워드 관련
    userPwChk();

    //닉네임 관련
    userNnChk();

    //이메일 관련
    userEmChk();

    //연락처 관련
    userPhChk();

    // 약관동의 (전체선택)
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

        if(signupBtn) {
            //빈값체크
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

            //li별 msg를 통한 밸리데이션
            var msg = $('li.signup-msg').toArray();

            if(msg.length != 0) {
                var focus = {
                    idMsg : 'userId',
                    pwMsg : 'userPwd',
                    nnMsg : 'userNicknm',
                    emMsg : 'userEmail1',
                }
            }

            for(var i = 0; i < msg.length; i++) {
                if($(msg[i]).html() != '') {
                    $('#' + focus[$(msg[i]).attr('id')]).focus();
                    return;
                }
            }


            if($event.validationChk("agree1")) return;
            if($event.validationChk("agree2")) return;
            // if($event.validationChk("agree3")) return;

            var marketingYn = "N";
            if ($('#marketingYn').is(":checked")) marketingYn = "Y";

            //회원가입 신청
            var data = {
                userId : $('#userId').val(),
                userPwd : $('#userPwd').val(),
                userNm : $('#userNm').val(),
                userNicknm : $('#userNicknm').val(),
                userEmail : $('#userEmail1').val() + "@" + $('#userEmail2').val(),
                userPhone : $('#userPhone1').val() + "-" + $('#userPhone1').val() + "-" + $('#userPhone3').val(),
                roleType : "ROLE_USER",
                verificationYn : "N",
                marketingYn : marketingYn,
            }
            var res = $ajax.postAjax('/signup', data);
            if(res == "error") {
                // signupBtn = false;
                $('#signupMsg').text('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("회원가입이 완료, 메일인증 후 로그인해주세요.")
                location.href = "/";
            }
            else if(res.result == "fail"){
                $('#signupMsg').text('네트워크 통신 실패, 관리자에게 문의해주세요.');
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

                if (res == "error") {
                    alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                }
                else if (res == "fail") {
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
    $('#userPwdChk').keyup(function(){
        $('#pwMsg').html("");
    });

    $('#userPwd').focusout(function(){
        detailUserPwChk();
    });
    $('#userPwdChk').focusout(function(){
        detailUserPwChk();
    });
}
// 비밀번호 확인
function detailUserPwChk() {
    if($('#userPwd').html() == "" && $('#userPwd').val() != "") {
        if($('#userPwd').val().length < 5) {
            $('#pwMsg').html("<label class=\"mb10\"></label> 비밀번호를 5자 이상입력해주세요.");
        }
        else if(!$util.isPw($('#userPwd').val())) {
            $('#pwMsg').html("<label class=\"mb10\"></label> 비밀번호는 영문, 숫자, 특수문자 를 모두 포함해야합니다.");
        }
        else if($('#userPwdChk').val() != "") {
            if($('#userPwd').val() != $('#userPwdChk').val()) {
                $('#pwMsg').html("<label class=\"mb10\"></label> 비밀번호, 비밀번호 확인 이 일치하지 않습니다.");
            }
        }
    }
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
            else if($('#userNicknm').val().indexOf("탈퇴") != -1) {
                $('#nnMsg').html("<label class=\"mb10\"></label> '탈퇴' 단어는 포함 할수 없습니다.");
            }
            else if(!$util.isNn($('#userNicknm').val())) {
                $('#nnMsg').html("<label class=\"mb10\"></label> 닉네임은 한글, 영어, 숫자 만 사용가능합니다.");
            }
            else {
                var data = {
                    userNicknm : $('#userNicknm').val(),
                }

                var res = $ajax.postAjax('/checkNicknm', data);

                if (res == "error") {
                    alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                }
                else if (res == "fail") {
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

    $('#userEmail2').keyup(function(){
        $('#emMsg').html("");
    });

    $('#userEmail1').focusout(function(){
        detailUserEmChk();
    });

    $('#userEmail2').focusout(function(){
        detailUserEmChk();
    });

    // 이메일 선택
    $('#emailGroup').change(function () {
        $('#emMsg').html("");

        $('#userEmail2').val($('#emailGroup').val());
        if($('#userEmail2').val() == "") {
            $("#userEmail2").attr("readonly",false);
        }
        else {
            $("#userEmail2").attr("readonly",true);
        }
        detailUserEmChk();
    });
}

// 이메일 유효성검사, 중복확인
function detailUserEmChk() {
    if($('#userEmail1').val() != "" && $('#userEmail2').val() != "") {
        var data = {
            userEmail : $('#userEmail1').val() + "@" + $('#userEmail2').val(),
        }

        //이메일 유효성검사
        if (!$util.isEm(data.userEmail)) {
            $('#emMsg').html("<label class=\"mb10\"></label> 유효하지 않은 이메일입니다.");
            return;
        }

        //이메일 중복확인
        var res = $ajax.postAjax('/checkEmail', data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if (res == "fail") {
            $('#emMsg').html("<label class=\"mb10\"></label> 이미 사용중인 이메일입니다.");
        }
    }
}

// 유저 연락처 관련
function userPhChk() {
    $('#userPhone1').keyup(function () {
        $('#phMsg').html("");
    });
    $('#userPhone2').keyup(function () {
        $('#phMsg').html("");
    });
    $('#userPhone3').keyup(function () {
        $('#phMsg').html("");
    });

    $('#userPhone1').focusout(function () {
        detailUserPhChk();
    });
    $('#userPhone2').focusout(function () {
        detailUserPhChk();
    });
    $('#userPhone3').focusout(function () {
        detailUserPhChk();
    });
}

// 연락처 유효성검사
function detailUserPhChk() {
    if($('#userPhone1').val() != "" && $('#userPhone2').val() != "" && $('#userPhone3').val() != "") {
        var data = {
            userPhone : $('#userPhone1').val() + "-" + $('#userPhone2').val() + "-" + $('#userPhone3').val(),
        }

        if (!$util.isPh(data.userPhone)) {
            $('#phMsg').html("<label class=\"mb10\"></label> 유효하지 않은 연락처입니다.");
        }
    }
}