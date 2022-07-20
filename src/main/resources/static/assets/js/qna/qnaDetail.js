$(function() {
    // 조회수
    boardHitUp();

    // 컨트롤버튼 컨트롤
    $(document).on("click", ".control-btn", function() {
        if($(this).next('ul').css('display') == 'none') {
            $('.control-btn').next('ul').hide();
            $(this).next('ul').show();
        }
        else {
            $(this).next('ul').hide();
        }
    });

    // 게시글 삭제(유저)
    $('#boardUpdate').click(function() {
        $(this).closest('ul').hide();
        $page.goPage('/board/'+ boardId +'/detail/'+ idx);
    });

    // 게시글 삭제(유저)
    $('#boardDel').click(function() {
        $(this).closest('ul').hide();
        if(confirm("해당 게시물을 삭제하시겠습니까?")) {
            $ajax.deleteAjax('/board/'+ boardId + '/detail/' + idx + '/user', {}, true, "해당 게시물을 삭제하였습니다.", '/board/'+ boardId);
        }
    });

    // 게시글 삭제(관리자)
    $('#boardAdmDel').click(function() {
        $(this).closest('ul').hide();
        if(confirm("해당 게시물을 삭제하시겠습니까?\n(해당 게시물의 댓글까지 모두 삭제됩니다.)")) {
            $ajax.deleteAjax('/board/'+ boardId + '/detail/' + idx + '/admin', {}, true, "해당 게시물을 삭제하였습니다.", '/board/'+ boardId);
        }
    });

    //게시글 이동상세
    $('#boardMovePopup').click(function() {
        $(this).closest('ul').hide();

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

    // 첨부파일 숨김
    $('#boardFileBtn').click(function() {
        if($('#boardFile').css('display') == 'none') {
            $('#boardFile').show();
            $(this).addClass('on');
        }
        else {
            $('#boardFile').hide();
            $(this).removeClass('on');
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
            $('.control-btn').next('ul').hide();
        }
    });

    // 댓글등록 초기화
    $('#commentAddDel').click(function() {
        $('#commentContent').val('');
        $('#commentContent').css('height', '59px');
    });

    //댓글 추가
    $(document).on("submit", "#boardDetailForm", function(e) {
        e.preventDefault();

        //빈값체크
        if ($event.validationFocus("commentContent")) return;

        var data = {
            boardIdx: idx,
            parentIdx: null,
            commentContent: $util.transferTextarea($('#commentContent').val()),
        };

        var res = $ajax.postAjax('/board/comment', data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("댓글을 등록하였습니다.")
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });

    // 댓글 수정 작성창 열기
    $(document).on("click", ".comment-update", function() {
        $(this).closest('ul').hide();

        var commentIdx = $(this).closest('ul').data('val');
        var commentContent = $(this).closest('.comment-li').find('.comment-content').html();

        var html =
            '<div class="comment-update-div" data-val="'+ commentIdx +'">' +
            '<textarea class="comment-update-textarea" rows="5" maxlength="300" onkeydown="return $util.limitLines(this, event);" onkeyup="$util.resize(this);" placeholder="댓글 내용을 입력해주세요.">'+ $util.retainTextarea(commentContent) +'</textarea>' +
            '<div class="comment-update-btn-box">' +
            '<button class="comment-update-del" type="button">취소</button>\n' +
            '<button class="color-primary-border-none comment-update" type="button">수정</button>' +
            '</div>' +
            '</div>';
        $(this).closest('.comment-li').find('.comment-content').hide();
        $(this).closest('.comment-li').find('.reference').after(html);

        var textarea = $(this).closest('.comment-li').find('.comment-update-textarea').get(0);
        $util.resize(textarea);
    });

    // 댓글 수정 작성취소
    $(document).on("click", ".comment-update-del", function() {
        $(this).closest('.comment-li').find('.comment-content').show();
        $(this).closest('.comment-update-div').remove();
    });

    // 댓글 수정
    $(document).on("click", ".comment-update", function() {
        var commentContent = $(this).closest('.comment-update-div').find('textarea').val();
        var commentIdx = $(this).closest('.comment-update-div').data('val');

        //빈값체크
        if (commentContent == "") {
            alert("수정 내용을 입력해주세요.")
            return;
        }

        var data = {
            idx : commentIdx,
            commentContent: $util.transferTextarea(commentContent),
        };

        var referenceIdx = $(this).closest('li').data('referenceidx');
        if(referenceIdx != "") {
            data.referenceIdx = referenceIdx;
        }

        var res = $ajax.patchAjax('/board/comment', data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("답글을 수정하였습니다.")
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });

    // 댓글 삭제(유저)
    $('.comment-del').click(function() {
        $(this).closest('ul').hide();
        if(confirm("해당 댓글을 삭제하시겠습니까?")) {
            var data = {
                idx : $(this).closest('ul').data('val')
            }
            var res = $ajax.deleteAjax('/board/comment/user', data);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("댓글을 삭제하였습니다.")
                window.location.reload();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 댓글 삭제(관리자)
    $(document).on("click", ".comment-adm-del", function(e) {
        $(this).closest('ul').hide();
        if(confirm("해당 댓글을 삭제하시겠습니까?")) {
            var data = {
                idx : $(this).closest('ul').data('val')
            }

            var res = $ajax.deleteAjax('/board/comment/admin', data);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("댓글을 삭제하였습니다.")
                window.location.reload();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 대댓글작성
    $(document).on("click", ".recomment-btn", function(e) {
        if($(this).next('div').css('display') == "none") {
            $(this).next('div').show();
        }
        else {
            $(this).next('div').hide();
            $(this).next('div').find('textarea').val('');
        }
    });

    // 대댓글작성취소
    $(document).on("click", ".recomment-add-del", function(e) {
        $(this).closest('.recomment-div').hide();
        $(this).closest('.recomment-div').find('textarea').val('');
    });

    // 대댓글 등록
    $(document).on("click", ".recomment-add", function(e) {
        var commentContent = $(this).closest('.recomment-div').find('textarea').val();

        //빈값체크
        if (commentContent == "") {
            alert("답글 내용을 입력해주세요.")
            return;
        }

        var parentIdx = $(this).closest('li').data('val');

        var data = {
            boardIdx: idx,
            parentIdx: parentIdx,
            referenceIdx: null,
            commentContent: $util.transferTextarea(commentContent),
        };

        var referenceIdx = $(this).closest('li').data('referenceidx');
        if(referenceIdx != "") {
            data.referenceIdx = referenceIdx;
        }

        var res = $ajax.postAjax('/board/comment', data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("답글을 등록하였습니다.")
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });

    // 답글 더보기
    $('.recomment-view').click(function() {
        var parentIdx = $(this).closest('li').data('val');
        var res = $ajax.postAjax('/board/comment/' + parentIdx);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }

        for (var i = 0; i < res.length; i++){
            res[i] = $util.nullChkObj(res[i]);
        }

        for (var i = 0; i < res.length; i++){
            if(res[i].deleteYn == 'Y') {
                res[i].commentContent = '삭제된 댓글입니다.';
            }

            var html =
                '<li class="comment-li">' +
                '<div class="comment-list">' +
                '<img src="/img/common/no_profile.gif" >' +
                '<div>' +
                '<p class="weight600">'+ res[i].createNicknm +'<span class="weight400 ml5"><i class="fa fa-clock-o" aria-hidden="true"></i> '+ res[i].createDate +'</span></p>' +
                '<p>';
            if(res[i].referenceNicknm == '') {
                html +=
                    '<i class="reference"></i>'
            }
            else {
                html +=
                    '<i class="weight600 text-color-primary reference">@'+ res[i].referenceNicknm +' </i>'
            }
            html+=
                '<span class="comment-content">'+ res[i].commentContent +'</span></p>' +
                '</div>' +
                '</div>';
            if(admin || (res[i].createIdx == sessionIdx && res[i].deleteYn == "N")) {
                html +=
                    '<a class="control-box">' +
                    '<i class="fa fa-ellipsis-v control-btn" aria-hidden="true"></i>' +
                    '<ul style="display: none;" data-val="'+ res[i].idx +'">';
                if(res[i].deleteYn == "N") {
                    html +=
                        '<li><button class="comment-update" type="button"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 수정</button></li>';
                }
                if(!admin && res[i].createIdx == sessionIdx) {
                    html +=
                        '<li><button class="comment-del" type="button"><i class="fa fa-trash-o" aria-hidden="true"></i> 삭제</button></li>';
                }
                else if(admin) {
                    html +=
                        '<li><button class="comment-adm-del" type="button"><i class="fa fa-trash-o" aria-hidden="true"></i> 삭제</button></li>';
                }
                html +=
                    '</ul>' +
                    '</a>';
            }
            html +=
                '<ul class="ml62">' +
                '<li data-val="'+ res[i].parentIdx +'" data-referenceidx="'+ res[i].createIdx +'">\n';
            if(commentLevel && res[i].deleteYn == 'N') {
                html +=
                    '<button class="text-color-gray weight600 recomment-btn" type="button">답글</button>' +
                    '<div class="recomment-div" style="display: none;">' +
                    '<p class="weight600">'+ sessionUserNicknm +'</p>' +
                    '<textarea rows="5" maxlength="300" onkeydown="return $util.limitLines(this, event);" onkeyup="$util.resize(this);" placeholder="댓글 내용을 입력해주세요."></textarea>' +
                    '<div class="recomment-btn-box">' +
                    '<button class="recomment-add-del" type="button">취소</button>\n' +
                    '<button class="color-primary-border-none recomment-add" type="button">등록</button>' +
                    '</div>' +
                    '</div>';
            }
            html +=
                '</li>' +
                '</li>';
            $(this).next('ul').append(html);
            $(this).hide();
        }
    });

    // 목록으로
    $('#goList').click(function() {
        $page.goPage('/board/'+ boardId);
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

// 조회수를 올린다.
function boardHitUp() {
    $ajax.postAjax('/board/hit/'+ idx, {}, true);
}