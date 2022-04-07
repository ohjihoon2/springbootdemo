$(function(){
    //게시판추가 팝업
    $('#addBtn').click(function(){
        var html = 
            '<h4>게시판 추가</h4>' +
            '<div class="mb20"></div>' +
            '<form id="boardAddForm">' +
            '<table>' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>Type</th>' +
            '<td class="text-center"><select id="boardType"><option value="general">일반게시판</option><option value="photo">포토게시판</option></select></td>' +
            '<th>Use</th>' +
            '<td class="text-center"><input id="useYn" type="checkbox" checked></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Id</th>' +
            '<td colspan="3"><input id="boardId" type="text" maxlength="15"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td colspan="3"><input id="boardNm" type="text" maxlength="15"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Desc</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="boardDesc"></textarea></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="2">File Attach</th>' +
            '<td colspan="2" class="text-center"><input id="fileAttachYn" type="checkbox" checked></td>' +
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

    // 게시판추가
    $(document).on("submit", "#boardAddForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("boardId")) return;

        if(!$util.isEnNu($('#boardId').val())) {
            alert("게시판 ID는 영문, 숫자만 입력가능합니다.");
            $('#boardId').focus();
            return;
        }
        if($event.validationFocus("boardNm")) return;

        var useYn;
        if($('#useYn').is(':checked')) {
            useYn = 'Y';
        }
        else {
            useYn = 'N';
        }

        var fileAttachYn;
        if($('#fileAttachYn').is(':checked')) {
            fileAttachYn = 'Y';
        }
        else {
            fileAttachYn = 'N';
        }


        var data = {
            boardType : $('#boardType').val(),
            useYn : useYn,
            boardId : $('#boardId').val(),
            boardNm : $('#boardNm').val(),
            boardDesc : $('#boardDesc').val(),
            fileAttachYn : fileAttachYn,
        };

        var res = $ajax.postAjax('/adm/boardMaster', data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("게시판을 추가하였습니다.")
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });

    //게시판추가 팝업
    $('[name="updateBtn"]').click(function(){
        var html =
            '<h4>게시판 수정</h4>' +
            '<div class="mb20"></div>' +
            '<form id="boardUpdateForm">' +
            '<input id="idx" type="hidden" value="">' +
            '<table>' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>Type</th>' +
            '<td class="text-center"><select id="boardType"><option value="general">일반게시판</option><option value="photo">포토게시판</option></select></td>' +
            '<th>Use</th>' +
            '<td class="text-center"><input id="useYn" type="checkbox" checked></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Id</th>' +
            '<td colspan="3"><input id="boardId" type="text" maxlength="15"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td colspan="3"><input id="boardNm" type="text" maxlength="15"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Desc</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="boardDesc"></textarea></td>' +
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
            '<button type="button" id="boardDel">삭제</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">추가</button>' +
            '</div>' +
            '</form>';

        $popup.popupJs(html);
    });
});