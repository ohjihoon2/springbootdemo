// 에디터용
var oEditors = [];

//다중 통신 막기
var submitBtn = true;

$(function(){
    //연락처 자동 하이픈
    $util.phoneAutoHyphen("userPhone");

    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/adm/admin', param)
    });

    //페이징
    $('#pagination ul li a').not( '.disable' ).click(function() {
        var param = {
            searchType : $('#hiddenSearchType').val(),
            searchKeyword : $('#hiddenSearchKeyword').val(),
            pageNum : $(this).data('val'),
        }
        $page.getGoPage('/adm/admin', param);
    });

    // PW변경
    $(document).on("click", "#userPassword", function(e) {
        window.open("/adm/admin/password","","top=0, left=0, width=465, height=350, directories='no',location=no, menubar=no, resizable=no, status=yes, toolbar=no")  ;
        window.opener = "nothing";
    });

    // 회원 수정
    $(document).on("submit", "#myUpdateForm", function(e) {
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
            if ($event.validationFocus("userNm")) return;
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

            var data = {
                userNicknm: $('#userNicknm').val() + "관리자",
                userNm : $util.cdIns($('#userNm').val()),
                userPhone: $('#userPhone').val(),
                userEmail: $('#userEmail').val(),
                adminMemo : $('#adminMemo').val(),
            };

            submitBtn = false;

            var res = $ajax.patchAjax('/adm/user/' + myIdx, data);
            if (res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            } else if (res.result == "success") {
                alert("내정보를 수정하였습니다.");
                window.location.reload();
                submitBtn = true;
            } else if (res.result == "fail") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            }
        }
    });
});