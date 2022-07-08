$(function() {
    // 관리자버튼 컨트롤
    $('#admBtn').click(function() {
        if($(this).next('ul').css('display') == 'none') {
            $(this).next('ul').show();
        }
        else {
            $(this).next('ul').hide();
        }
    });
    // 전체체크
    $('#checkAll').click(function() {
        if($(this).is(':checked')) {
            $('#boardTable tbody input[type="checkbox"]').prop('checked',true);
        }
        else {
            $('#boardTable tbody input[type="checkbox"]').prop('checked',false);
        }
    });
    
    // 게시글 삭제
    $('#boardDel').click(function() {
        var check;
        if(boardType == 'GENERAL') {
            check = $('#boardTable tbody input[type="checkbox"]:checked');
        }
        else if(boardType == 'PHOTO') {
            check = $('#boardPhoto ul input[type="checkbox"]:checked');
        }
        if(check.length == 0) {
            alert("삭제 할 게시물을 선택해주세요.");
        }
        else {
            if(confirm(check.length + " 개의 게시물을 삭제하시겠습니까?\n(해당 게시물의 댓글까지 모두 삭제됩니다.)")) {
                $('#admBtn').next('ul').hide();

                var idx = [];
                for(var i = 0; i < check.length; i++) {
                    if(boardType == 'GENERAL') {
                        idx.push($(check).eq(i).closest('tr').data('val'));
                    }
                    else if(boardType == 'PHOTO') {
                        idx.push($(check).eq(i).closest('li').data('val'));
                    }
                }

                var data = {
                    idx : idx,
                };

                var res = $ajax.deleteAjax('/board/'+ boardId, data);
                if(res == "error") {
                    alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                }
                else if(res.result == "success") {
                    alert(check.length + " 개의 게시물을 삭제하였습니다.")
                    window.location.reload();
                }
                else if(res.result == "fail"){
                    alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                }
            }
        }
    });
    
    //게시글 이동상세
    $('#boardMovePopup').click(function() {
        if(boardType == 'GENERAL') {
            var check = $('#boardTable tbody input[type="checkbox"]:checked');
        }
        else if(boardType == 'PHOTO') {
            var check = $('#boardPhoto ul input[type="checkbox"]:checked');
        }
        if(check.length == 0) {
            alert("이동 할 게시물을 선택해주세요.");
        }
        else {
            $('#admBtn').next('ul').hide();

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
        }
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
            var idx = [];

            if(boardType == 'GENERAL') {
                var check = $('#boardTable tbody input[type="checkbox"]:checked');
                for(var i = 0; i < check.length; i++) {
                    idx.push($(check).eq(i).closest('tr').data('val'));
                }
            }
            else if(boardType == 'PHOTO') {
                var check = $('#boardPhoto ul input[type="checkbox"]:checked');
                for(var i = 0; i < check.length; i++) {
                    idx.push($(check).eq(i).closest('li').data('val'));
                }
            }

            var data = {
                masterIdx : $(masterIdx).data('val'),
                idx : idx
            };

            var res = $ajax.patchAjax('/board/move', data);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert(check.length + " 개의 게시물을 이동하였습니다.");
                window.location.reload();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });
});