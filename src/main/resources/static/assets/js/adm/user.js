// 에디터용
var oEditors = [];

//다중 통신 막기
submitBtn = true;

$(function(){
    //비밀번호 수정팝업
    // $(document).on("click", "#popubPw", function() {
    //     var idx = $('#idx').val();
    //     window.open("/adm/popupPw/"+idx,"","top=0, left=0, width=400, height=300, directories='no',location=no, menubar=no, resizable=no, status=yes, toolbar=no")  ;
    //     window.opener = "nothing";
    //     // window.open('', '_parent', '');
    //     // window.close();
    // });

    //연락처 자동 하이픈
    $util.phoneAutoHyphen("userPhone");

    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/adm/user', param)
    });

    //페이징
    $('#pagination ul li a').not( '.disable' ).click(function() {
        var param = {
            searchType : $('#hiddenSearchType').val(),
            searchKeyword : $('#hiddenSearchKeyword').val(),
            pageNum : $(this).data('val'),
        }
        $page.getGoPage('/adm/user', param);
    });
    
    //회원수정 팝업
    $('[name="updateBtn"]').click(function(){
        var idx = $(this).data('val');

        var res = $ajax.postAjax('/adm/user/' + idx);
        res = $util.nullChkObj(res);

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }
        var verificationYn ='';
        if(res.verificationYn == 'Y') {
            verificationYn = 'checked';
        }

        var marketingYn ='';
        if(res.marketingYn == 'Y') {
            marketingYn = 'checked';
        }

        var html =
            '<h4>회원 수정</h4>' +
            '<div class="mb20"></div>' +
            '<form id="userUpdateForm">' +
            '<input id="idx" type="hidden" value="'+ res.idx +'">' +
            '<table>' +
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
            '<th>Name</th>' +
            '<td class="text-center"><input id="userNm" type="text" value="'+ res.userNm +'" maxlength="10"></td>' +
            '</tr>' +
            '<tr>' +
            '<tr>' +
            '<th>Nickname</th>' +
            '<td class="text-center">' +
            '<input id="userNicknm" type="text" value="'+ res.userNicknm +'" maxlength="10">' +
            '<input id="userNicknmHidden" type="hidden" value="'+ res.userNicknm +'">' +
            '</td>' +
            '<th>Tel</th>' +
            '<td class="text-center"><input id="userPhone" type="text" value="'+ res.userPhone +'" maxlength="11" oninput="this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*)\\./g, \'$1\');"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Email</th>' +
            '<td class="text-center">' +
            '<input id="userEmail" type="text" value="'+ res.userEmail +'">' +
            '<input id="userEmailHidden" type="hidden" value="'+ res.userEmail +'">' +
            '</td>' +
            '<th>Verification</th>' +
            '<td class="text-center"><input id="verificationYn" type="checkbox"'+ verificationYn +'></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Memo</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="adminMemo" placeholder="관리자 전용으로 유저에게 출력되지 않습니다.">' + res.adminMemo + '</textarea></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="2">marketing</th>' +
            '<td class="text-center" colspan="2"><input id="marketingYn" type="checkbox"'+ marketingYn +' disabled></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="userResetPassword">PW초기화</button>\n' +
            '<button type="button" id="userDel">탈퇴</button>' +
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

            var res = $ajax.deleteAjax('/adm/user/'+ idx);
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

    // 회원 수정
    $(document).on("submit", "#userUpdateForm", function(e) {
        e.preventDefault();

        if (submitBtn) {
            //빈값체크
            if ($event.validationFocus("userNm")) return;
            if ($event.validationFocus("userNicknm")) return;

            if($('#userNicknm').val() != $('#userNicknmHidden').val()) {
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
                    var userNicknm = {
                        userNicknm: $('#userNicknm').val(),
                    }

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
            if ($event.validationFocus("userPhone")) return;
            if ($event.validationFocus("userEmail")) return;

            if($('#userEmail').val() != $('#userEmailHidden').val()) {
                var userEmail = {
                    userEmail: $('#userEmail').val()
                }


                //이메일 유효성검사
                if (!$util.isEm(userEmail.userEmail)) {
                    alert("유효하지 않은 이메일입니다.");
                    return;
                }
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

            var verificationYn = "N";
            if ($('#verificationYn').is(":checked")) verificationYn = "Y";

            var data = {
                userNm: $('#userNm').val(),
                userNicknm: $('#userNicknm').val(),
                userPhone: $('#userPhone').val(),
                userEmail: $('#userEmail').val(),
                verificationYn : verificationYn,
                adminMemo : $('#adminMemo').val(),
            };

            var idx = $('#idx').val();

            submitBtn = false;

            var res = $ajax.patchAjax('/adm/user/' + idx, data);
            if (res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            } else if (res.result == "success") {
                alert("회원을 수정하였습니다.");
                submitBtn = true;
            } else if (res.result == "fail") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            }
        }
    });
});