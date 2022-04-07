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
            '<td class="text-center"><select><option value="general">일반게시판</option><option value="photo">포토게시판</option></select></td>' +
            '<th>Open</th>' +
            '<td class="text-center"><input id="useYn" type="checkbox" checked></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Id</th>' +
            '<td colspan="3"><input id="boardId" type="text"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td colspan="3"><input id="boardNm" type="text"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Desc</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea></textarea></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="2">File Attach</th>' +
            '<td colspan="2" class="text-center"><input type="checkbox" checked></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '</form>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<button type="submit">추가</button>\n' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>' +
            '</div>'

        $popup.popupJs(html);
    });

    // 게시판추가
    $('#boardAddForm').submit(function(event){
        event.preventDefault();
        if($event.validationFocus("boardId")) return;
        if($event.validationFocus("boardNm")) return;

        var useYn;
        if($('#useYn').is(':checked')) {
            useYn = 'Y';
        }
        else {
            useYn = 'N';
        }


        var data = {
            boardType : $('#boardType').val(),
            useYn : useYn
        };

        var res = $ajax.postAjax('/adm/menuTree', data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("메뉴를 저장하였습니다.")
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });
});