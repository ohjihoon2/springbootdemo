$(function() {
    // 조회수
    boardHitUp();

    // 컨트롤버튼 컨트롤
    $('#controlBtn').click(function() {
        if($(this).next('ul').css('display') == 'none') {
            $(this).next('ul').show();
        }
        else {
            $(this).next('ul').hide();
        }
    });

    // 게시글 삭제
    $('#boardAdmDel').click(function() {
        $('#controlBtn').next('ul').hide();
        if(confirm("해당 게시물을 삭제하시겠습니까?\n(해당 게시물의 댓글까지 모두 삭제됩니다.)")) {
            $ajax.deleteAjax('/board/'+ boardId + '/detail/' + idx + '/admin', {}, true, "해당 게시물을 삭제하였습니다.", '/board/'+ boardId);
        }
    });

    //게시글 이동상세
    $('#boardMovePopup').click(function() {
        $('#controlBtn').next('ul').hide();

        var res = $ajax.postAjax('/board/boardMaster');
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }
        res = $util.nullChkObj(res);

        var codeBoardType = {
            GENERAL : '일반게시판',
            PHOTO : '사진게시판'
        }

        var html =
            '<h5>게시판 이동</h5>' +
            '<div class="mb5"></div>' +
            '<table id="boardMasterTable">' +
            '<colgroup>' +
            '<col width="25%">' +
            '<col width="55%">' +
            '<col width="20%">' +
            '</colgroup>' +
            '<thead>' +
            '<tr class="sel">' +
            '<th>유형</th>' +
            '<th>게시판명</th>' +
            '<th>사용유무</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>';
        for (var i = 0; i < res.length; i++) {
            var useYn = '';
            if(res[i].useYn == 'Y') {
                useYn = 'checked';
            }
            html +=
                '<tr data-val="'+ res[i].idx +'">' +
                '<td class="text-center">'+ codeBoardType[res[i].boardType] +'</td>' +
                '<td>'+ res[i].boardNm +'</td>' +
                '<td class="text-center"><input id="useYn" type="checkbox"'+ useYn +' disabled></td>' +
                '</tr>';
        }
        html +=
            '</tbody>' +
            '</table>';
        var bot =
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button id="boardMove" type="button">이동</button>';

        $popup.popupJs(html, bot);
    });

    // 게시글 선택
    $(document).on("click", "#boardMasterTable tbody tr", function(e) {
        $('#boardMasterTable tbody tr').removeClass('sel');
        $(this).addClass('sel');
    });

    // 게시글 이동
    $(document).on("click", "#boardMove", function(e) {
        var masterIdx = $('#boardMasterTable tbody .sel');

        if(masterIdx.length != 1) {
            alert("이동 할 게시판을 선택해주세요.");
        }
        else {
            var param = {
                masterIdx : $(masterIdx).data('val'),
            }

            // TODO moveBoards 쿼리 다중으로 되어 있어 에러뜸 - 단일
            var res = $ajax.patchAjax('/board/'+ boardId +'/detail/'+ idx +'/move', param);

            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("해당 게시물을 이동하였습니다.");
                window.location.reload();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 댓글영역 숨김
    $('#boardCommentBtn').click(function() {
        if($('#boardComment').css('display') == 'none') {
            $('#boardComment').show();
            $(this).addClass('on');
        }
        else {
            $('#boardComment').hide();
            $(this).removeClass('on');
        }
    });

    // 목록으로
    $('#listBtn').click(function() {
        $page.goPage('/board/'+ boardId);
    });
});

// 조회수를 올린다.
function boardHitUp() {
    $ajax.postAjax('/board/hit/'+ idx, {}, true);
}