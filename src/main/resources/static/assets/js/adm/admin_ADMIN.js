//최고관리자용 스크립트 타입리프로 앞단 보안처리
$(function(){
    //내정보수정 팝업
    $('#myBtn').click(function(){
        var res = $ajax.postAjax('/adm/admin/' + myIdx);

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }
        res = $util.nullChkObj(res);

        var userNicknm = res.userNicknm.replace("관리자", "");

        var html =
            '<h4>내정보 수정</h4>' +
            '<div class="mb20"></div>' +
            '<form id="myUpdateForm">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>Id</th>' +
            '<td>'+ res.userId +'</td>' +
            '<th>Nickname</th>' +
            '<td class="text-center admin-after">' +
            '<input id="userNicknm" type="text" value="'+ userNicknm +'" maxlength="7">' +
            '<input id="userNicknmHidden" type="hidden" value="'+ res.userNicknm +'">' +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td><input id="userNm" type="text" value="'+ res.userNm +'" maxlength="10"></td>' +
            '<th>Tel</th>' +
            '<td><input id="userPhone" type="text" value="'+ res.userPhone +'" maxlength="11" oninput="this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*)\\./g, \'$1\');"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Email</th>' +
            '<td colspan="3" class="text-center">' +
            '<input id="userEmail" type="text" value="'+ res.userEmail +'">' +
            '<input id="userEmailHidden" type="hidden" value="'+ res.userEmail +'">' +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Memo</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="adminMemo" placeholder="관리자 전용으로 유저에게 출력되지 않습니다.">' + res.adminMemo + '</textarea></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="userPassword">PW변경</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';

        $popup.popupJs(html);
    });

    //관리자추가 팝업
    $('#addBtn').click(function(){
        var html =
            '<h4>관리자 추가</h4>' +
            '<div class="mb20"></div>' +
            '<form id="adminAddForm">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>Id</th>' +
            '<td><input id="userId" type="text" value="" maxlength="20" placeholder="영문소문자/숫자 가능, 5~20"></td>' +
            '<th>Nickname</th>' +
            '<td class="text-center admin-after">' +
            '<input id="userNicknm" type="text" value="" maxlength="7" placeholder="영문/한글/숫자 가능, 2~7">' +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<tr>' +
            '<th>Pw</th>' +
            '<td><input id="password" type="password" value="" maxlength="20" placeholder="영문/숫자/특수문자 모두조합, 8~16" autocomplete="new-password"></td>' +
            '<th>Pw Check</th>' +
            '<td><input id="passwordChk" type="password" value="" maxlength="20"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td><input id="userNm" type="text" value="" maxlength="10"></td>' +
            '<th>Tel</th>' +
            '<td><input id="userPhone" type="text" value="" maxlength="11" oninput="this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*)\\./g, \'$1\');"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Email</th>' +
            '<td colspan="3" class="text-center">' +
            '<input id="userEmail" type="text" value="">' +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Memo</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="adminMemo" placeholder="관리자 전용으로 유저에게 출력되지 않습니다."></textarea></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">추가</button>' +
            '</div>' +
            '</form>';

        $popup.popupJs(html);
    });

    // 관리자추가
    $(document).on("submit", "#adminAddForm", function(e) {
        e.preventDefault();

        if (submitBtn) {
            //빈값체크
            if ($event.validationFocus("userId")) return;
            if($('#userId').val().length < 5) {
                alert("아이디를 5자 이상입력해주세요.");
                return;
            }
            else if(!$util.isId($('#userId').val())) {
                alert("아이디는 영문 소문자, 숫자 만 사용가능합니다.");
                return;
            }
            else {
                var data = {
                    userId : $('#userId').val(),
                }

                var res1 = $ajax.postAjax('/checkId', data);

                if (res1 == "error") {
                    alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                    return;
                }
                else if (res1 == "fail") {
                    alert('이미 사용중인 아이디입니다.');
                    return;
                }
            }

            if ($event.validationFocus("userNicknm")) return;

            var userNicknm = {
                userNicknm: $('#userNicknm').val() + "관리자",
            }
            if ($('#userNicknm').val().length < 2) {
                alert("닉네임을 2자 이상 입력해주세요.");
                $('#userNicknm').focus();
                return;
            }
            else if ($('#userNicknm').val().indexOf("관리자") != -1) {
                alert("'관리자' 단어는 포함 할수 없습니다.");
                $('#userNicknm').focus();
                return;
            }
            else if ($('#userNicknm').val().indexOf("탈퇴") != -1) {
                alert("'탈퇴' 단어는 포함 할수 없습니다.");
                $('#userNicknm').focus();
                return;
            }
            else if (!$util.isNn($('#userNicknm').val())) {
                alert("닉네임은 한글, 영어, 숫자 만 사용가능합니다.");
                $('#userNicknm').focus();
                return;
            }
            else {
                var res2 = $ajax.postAjax('/checkNicknm', userNicknm);

                if (res2 == "error") {
                    alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                    return;
                } else if (res2 == "fail") {
                    alert("이미 사용중인 닉네임입니다.");
                    $('#userNicknm').focus();
                    return;
                }
            }

            if ($event.validationFocus("password")) return;
            if ($event.validationFocus("passwordChk")) return;

            if($('#password').val().length < 5) {
                alert("비밀번호를 5자 이상입력해주세요.");
                return;
            }
            else if(!$util.isPw($('#password').val())) {
                alert("비밀번호는 영문, 숫자, 특수문자 를 모두 포함해야합니다.");
                return;
            }
            else if($('#password').val() != $('#passwordChk').val()) {
                alert("비밀번호, 비밀번호 확인 이 일치하지 않습니다.");
                return;
            }

            var userEmail = {
                userEmail: $('#userEmail').val()
            }
            //이메일 유효성검사
            if (!$util.isEm(userEmail.userEmail)) {
                alert("유효하지 않은 이메일입니다.");
                return;
            }
            //이메일 중복확인 (이메일 등록은 차후에 할수 있도록 빈값 입력시 PASS)
            if(userEmail.userEmail != "") {
                var res3 = $ajax.postAjax('/checkEmail', userEmail);

                if (res3 == "error") {
                    alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                    return;
                } else if (res3 == "fail") {
                    alert("이미 사용중인 이메일입니다.");
                    $('#userEmail').focus();
                    return;
                }
            }

            var data = {
                userId: $('#userId').val(),
                userNicknm: $('#userNicknm').val()  + "관리자",
                password : $('#password').val(),
                userNm: $('#userNm').val(),
                userPhone: $('#userPhone').val(),
                userEmail: $('#userEmail').val(),
                adminMemo : $('#adminMemo').val(),
            };

            submitBtn = false;

            var res = $ajax.postAjax('/adm/admin', data);
            if (res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            } else if (res.result == "success") {
                alert("관리자를 추가하였습니다.");
                window.location.reload();
                submitBtn = true;
            } else if (res.result == "fail") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            }
        }
    });

    //관리자 수정
    $('[name="updateBtn"]').click(function(){
        var idx = $(this).data('val');
        var res = $ajax.postAjax('/adm/admin/' + idx);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }
        res = $util.nullChkObj(res);

        var userNicknm = res.userNicknm.replace("관리자", "");

        var html =
            '<h4>관리자 수정</h4>' +
            '<div class="mb20"></div>' +
            '<form id="adminUpdateForm">' +
            '<input id="idx" type="hidden" value="'+ res.idx +'">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>Id</th>' +
            '<td>'+ res.userId +'</td>' +
            '<th>Nickname</th>' +
            '<td class="text-center admin-after">' +
            '<input id="userNicknm" type="text" value="'+ userNicknm +'" maxlength="7" placeholder="영문/한글/숫자 가능, 2~7">' +
            '<input id="userNicknmHidden" type="hidden" value="'+ res.userNicknm +'">' +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td><input id="userNm" type="text" value="'+ res.userNm +'" maxlength="10"></td>' +
            '<th>Tel</th>' +
            '<td><input id="userPhone" type="text" value="'+ res.userPhone +'" maxlength="11" oninput="this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*)\\./g, \'$1\');"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Email</th>' +
            '<td colspan="3" class="text-center">' +
            '<input id="userEmail" type="text" value="'+ res.userEmail +'">' +
            '<input id="userEmailHidden" type="hidden" value="'+ res.userEmail +'">' +
            '</td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Memo</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="adminMemo" placeholder="관리자 전용으로 유저에게 출력되지 않습니다.">' + res.adminMemo + '</textarea></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="userResetPassword">PW초기화</button>\n' +
            '<button type="button" id="userDel">탈퇴</button>\n' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';

        $popup.popupJs(html);
    });

    // PW초기화
    $(document).on("click", "#userResetPassword", function(e) {
        var res1 = $ajax.postAjax('/adm/resetPassword');

        if(res1 == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }

        if(confirm("해당 회원의 비밀번호를 '"+ res1 +"' 로 초기화 하시겠습니까?")) {
            var idx = $('#idx').val();

            var res2 = $ajax.patchAjax('/adm/resetPassword/'+ idx);
            if(res2 == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res2.result == "success") {
                alert("해당 회원의 비밀번호를 초기화하였습니다.")
            }
            else if(res2.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 회원 강제탈퇴
    $(document).on("click", "#userDel", function(e) {
        if(confirm("해당 회원을 탈퇴시키겠습니까?")) {
            var idx = $('#idx').val();

            var res = $ajax.deleteAjax('/adm/admin/'+ idx);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("해당 회원을 탈퇴하였습니다.")
                window.location.reload();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 관리자 수정
    $(document).on("submit", "#adminUpdateForm", function(e) {
        e.preventDefault();
        if (submitBtn) {
            //빈값체크
            if ($event.validationFocus("userNicknm")) return;
            var userNicknm = {
                userNicknm: $('#userNicknm').val() + "관리자",
            }
            if(userNicknm.userNicknm != $('#userNicknmHidden').val()) {
                if ($('#userNicknm').val().length < 2) {
                    alert("닉네임을 2자 이상 입력해주세요.");
                    $('#userNicknm').focus();
                    return;
                } else if ($('#userNicknm').val().indexOf("관리자") != -1) {
                    alert("'관리자' 단어는 포함 할수 없습니다.");
                    $('#userNicknm').focus();
                    return;
                } else if ($('#userNicknm').val().indexOf("탈퇴") != -1) {
                    alert("'탈퇴' 단어는 포함 할수 없습니다.");
                    $('#userNicknm').focus();
                    return;
                } else if (!$util.isNn($('#userNicknm').val())) {
                    alert("닉네임은 한글, 영어, 숫자 만 사용가능합니다.");
                    $('#userNicknm').focus();
                    return;
                } else {
                    var res1 = $ajax.postAjax('/checkNicknm', userNicknm);

                    if (res1 == "error") {
                        alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                        return;
                    } else if (res1 == "fail") {
                        alert("이미 사용중인 닉네임입니다.");
                        $('#userNicknm').focus();
                        return;
                    }
                }
            }

            if($('#userEmail').val() != $('#userEmailHidden').val()) {
                var userEmail = {
                    userEmail: $('#userEmail').val()
                }


                //이메일 유효성검사
                if (!$util.isEm(userEmail.userEmail)) {
                    alert("유효하지 않은 이메일입니다.");
                    return;
                }

                if(userEmail.userEmail != "") {
                    //이메일 중복확인
                    var res2 = $ajax.postAjax('/checkEmail', userEmail);

                    if (res2 == "error") {
                        alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                        return;
                    } else if (res2 == "fail") {
                        alert("이미 사용중인 이메일입니다.");
                        $('#userEmail').focus();
                        return;
                    }
                }
            }

            var idx = $('#idx').val();

            var data = {
                userNicknm: $('#userNicknm').val() + "관리자",
                userNm : $util.cdIns($('#userNm').val()),
                userPhone: $('#userPhone').val(),
                userEmail: $('#userEmail').val(),
                adminMemo : $('#adminMemo').val(),
            };

            submitBtn = false;

            var res = $ajax.patchAjax('/adm/admin/' + idx, data);
            if (res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            } else if (res.result == "success") {
                alert("관리자 정보를 수정하였습니다.");
                window.location.reload();
                submitBtn = true;
            } else if (res.result == "fail") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            }
        }
    });
});