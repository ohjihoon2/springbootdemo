// 에디터용
var oEditors = [];

$(function(){
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

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }
        var verification ='';
        if(res.verification == 'Y') {
            verification = 'checked';
        }

        var useYn ='';
        if(res.useYn == 'Y') {
            useYn = 'checked';
        }
        console.log(res);

        var html =
            '<h4>회원 수정</h4>' +
            '<div class="mb20"></div>' +
            '<form id="contentUpdateForm">' +
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
            '<td class="text-center"><input id="userNm" type="text" value="'+ res.userNm +'"></td>' +
            '</tr>' +
            '<tr>' +
            '<tr>' +
            '<th>Nickname</th>' +
            '<td class="text-center"><input id="userNicknm" type="text" value="'+ res.userNicknm +'"></td>' +
            '<th>Tel</th>' +
            '<td class="text-center"><input id="userPhone" type="text" value="'+ res.userPhone +'"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Email</th>' +
            '<td class="text-center"><input id="userEmail" type="text" value="'+ res.userEmail +'"></td>' +
            '<th>Verification</th>' +
            '<td class="text-center"><input id="verification" type="checkbox"'+ verification +'></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Memo</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="adminMemo">' + res.adminMemo + '</textarea></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="2">File Attach</th>' +
            '<td colspan="2" class="text-center"><input id="fileAttachYn" type="checkbox" checked></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="contentDel">탈퇴</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';

        $popup.popupJs(html);
    });

    // 회원 강제탈퇴
    $(document).on("click", "#contentDel", function(e) {
        if(confirm("해당 컨텐츠를 삭제하시겠습니까?")) {
            var idx = $('#idx').val();

            var res = $ajax.deleteAjax('/adm/content/'+ idx);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("해당 컨텐츠를 삭제하였습니다.")
                window.location.reload();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 회원 수정
    $(document).on("submit", "#contentUpdateForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("contentId")) return;

        if(!$util.isEnNu($('#contentId').val())) {
            alert("컨텐츠 ID는 영문, 숫자만 입력가능합니다.");
            $('#boardId').focus();
            return;
        }
        if($('#contentId').val() != $('#contentIdOrigin').val()) {
            var param = {
                contentId : $('#contentId').val()
            }

            var result = $ajax.postAjax('/adm/contentId', param);

            if(result.result == 'success') {
                alert("이미 사용중인 컨텐츠 ID입니다.\n컨텐츠 ID는 중복 될 수 없습니다.");
                return;
            }
        }

        if($event.validationFocus("contentNm")) return;

        var useYn;
        if($('#useYn').is(':checked')) {
            useYn = 'Y';
        }
        else {
            useYn = 'N';
        }

        var data = {
            contentId : $('#contentId').val(),
            useYn : useYn,
            contentNm : $('#contentNm').val(),
            contentHtml : $('#contentHtml').val(),
        };
        var idx = $('#idx').val();
        var res = $ajax.patchAjax('/adm/content/'+ idx, data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("컨텐츠를 수정하였습니다.");
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });
});