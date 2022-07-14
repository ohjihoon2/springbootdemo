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
            '<div class="mb10"></div>' +
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
            '<button type="button" id="userPassword">PW변경</button>\n' +
            '<button type=\"button\" id=\"userDel\">탈퇴</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);
    });

    // 회원탈퇴
    $(document).on("click", "#userDel", function(e) {
        if(confirm("탈퇴하시키겠습니까?\n(탈퇴 후 복구되지 않습니다.)")) {
            var res = $ajax.deleteAjax('/adm/user/'+ myIdx);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("탈퇴하였습니다.")
                $util.goHomePage();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    //관리자 상세(매니저관리자용)
    $('[name="detailBtn"]').click(function(){
        var idx = $(this).data('val');
        var res = $ajax.postAjax('/adm/admin/' + idx);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }
        res = $util.nullChkObj(res);

        var html =
            '<h4>관리자 수정</h4>' +
            '<div class="mb10"></div>' +
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
            '<td>' + res.userNicknm + '</td>' +
            '</tr>' +
            '<tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td>' + res.userNm + '</td>' +
            '<th>Tel</th>' +
            '<td>' + res.userPhone +'</td>' +
            '</tr>' +
            '<tr>' +
            '<th>Email</th>' +
            '<td colspan="3">' + res.userEmail + '</td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Memo</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="adminMemo" disabled>' + res.adminMemo + '</textarea></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);
    });
});