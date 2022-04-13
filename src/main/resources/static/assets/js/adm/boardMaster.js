// 에디터용
var oEditors = [];

$(function(){
    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $('#searchKeyword').val(),
        }
        $page.getGoPage('/adm/boardMaster', param)
    });

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
            '<td class="text-center"><select id="boardType"><option value="GENERAL">일반게시판</option><option value="PHOTO">사진게시판</option></select></td>' +
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
        var param = {
            boardId : $('#boardId').val()
        }

        var result = $ajax.postAjax('/adm/boardId', param);

        if(result.result == 'success') {
            alert("이미 사용중인 게시판 ID입니다.\n게사판 ID는 중복 될 수 없습니다.");
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

    //게시판수정 팝업
    $('[name="updateBtn"]').click(function(){
        var idx = $(this).data('val');

        var res = $ajax.postAjax('/adm/boardMaster/' + idx);

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }

        var boardTypeGeneral = '';
        var boardTypePhoto = '';

        if(res.boardType == 'GENERAL') {
            boardTypeGeneral = 'selected';
        }
        else if(res.boardType == 'PHOTO') {
            boardTypePhoto = 'selected';
        }

        var useYn ='';
        if(res.useYn == 'Y') {
            useYn = 'checked';
        }

        var fileAttachYn = '';
        if(res.fileAttachYn == 'Y') {
            fileAttachYn = 'checked';
        }

        var html =
            '<h4>게시판 수정</h4>' +
            '<div class="mb20"></div>' +
            '<form id="boardUpdateForm">' +
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
            '<th>Type</th>' +
            '<td class="text-center"><select id="boardType"><option value="GENERAL"' + boardTypeGeneral + '>일반게시판</option><option value="PHOTO"' + boardTypePhoto + '>사진게시판</option></select></td>' +
            '<th>Use</th>' +
            '<td class="text-center"><input id="useYn" type="checkbox"'+ useYn +'></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Id</th>' +
            '<td colspan="3"><input id="boardId" type="text" value="'+ res.boardId +'" maxlength="15"><input id="boardIdOrigin" type="hidden" value="'+ res.boardId +'"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td colspan="3"><input id="boardNm" type="text" value="'+ res.boardNm +'" maxlength="15"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Desc</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="boardDesc">' + res.boardDesc + '</textarea></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="2">File Attach</th>' +
            '<td colspan="2" class="text-center"><input id="fileAttachYn" type="checkbox"'+ fileAttachYn +'></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="boardDel">삭제</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';

        $popup.popupJs(html);
    });

    // 게시판삭제
    $(document).on("click", "#boardDel", function(e) {
        if(confirm("해당 게시판을 삭제하시겠습니까?")) {
            var idx = $('#idx').val();

            var res = $ajax.deleteAjax('/adm/boardMaster/'+ idx);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("해당 게시판을 삭제하였습니다.")
                window.location.reload();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 게시판수정
    $(document).on("submit", "#boardUpdateForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("boardId")) return;

        if(!$util.isEnNu($('#boardId').val())) {
            alert("게시판 ID는 영문, 숫자만 입력가능합니다.");
            $('#boardId').focus();
            return;
        }
        if($('#boardId').val() != $('#boardIdOrigin').val()) {
            var param = {
                boardId : $('#boardId').val()
            }

            var result = $ajax.postAjax('/adm/boardId', param);

            if(result.result == 'success') {
                alert("이미 사용중인 게시판 ID입니다.\n게사판 ID는 중복 될 수 없습니다.");
                return;
            }
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
        var idx = $('#idx').val();
        var res = $ajax.patchAjax('/adm/boardMaster/'+ idx, data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("게시판을 수정하였습니다.");
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });
});