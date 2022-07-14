$(function(){
    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/adm/css', param)
    });
    

    //컨텐츠추가 팝업
    $('#addBtn').click(function(){
        var html = 
            '<h4>CSS 추가</h4>' +
            '<div class="mb10"></div>' +
            '<form id="cssAddForm">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td><input id="cssNm" type="text" maxlength="15"></td>' +
            '<th>Use</th>' +
            '<td class="text-center"><input id="useYn" type="checkbox" checked></td>' +
            '</tr>' +
            '<tr>' +
            '<th>ID</th>' +
            '<td>' +
            '<select id="cssFirstId">' + creatSelect() + '</select>' +
            '</td>' +
            '<th>Detail ID</th>' +
            '<td><input id="cssSecondId" type="text" maxlength="15" readonly placeholder="없음"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">CSS</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="cssCode"></textarea></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">추가</button>' +
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);
    });

    // 컨텐츠추가
    $(document).on("submit", "#cssAddForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("cssNm")) return;
        if($event.validationFocus("cssCode")) return;

        var useYn;
        if($('#useYn').is(':checked')) {
            useYn = 'Y';
        }
        else {
            useYn = 'N';
        }

        var data = {
            cssNm : $('#cssNm').val(),
            useYn : useYn,
            cssFirstId : $('#cssFirstId').val(),
            cssSecondId : $('#cssSecondId').val(),
            cssCode : $('#cssCode').val(),
        };

        var res = $ajax.postAjax('/adm/css', data);

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("CSS를 추가하였습니다.")
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });

    //CSS수정 팝업
    $('[name="updateBtn"]').click(function(){
        var idx = $(this).data('val');

        var res = $ajax.postAjax('/adm/css/' + idx);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }
        res = $util.nullChkObj(res);

        var useYn ='';
        if(res.useYn == 'Y') {
            useYn = 'checked';
        }

        var html =
            '<h4>CSS 수정</h4>' +
            '<div class="mb10"></div>' +
            '<form id="cssUpdateForm">' +
            '<input id="idx" type="hidden" value="'+ res.idx +'">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td><input id="cssNm" type="text" value="'+ res.cssNm +'" maxlength="15"></td>' +
            '<th>Use</th>' +
            '<td class="text-center"><input id="useYn" type="checkbox"'+ useYn +'></td>' +
            '</tr>' +
            '<tr>' +
            '<th>ID</th>' +
            '<td>' +
            '<select id="cssFirstId">' + creatSelect(res.cssFirstId) + '</select>' +
            '<th>Detail ID</th>' +
            '<td><input id="cssSecondId" type="text" value="'+ res.cssSecondId +'" maxlength="15"';
            if(!(res.cssFirstId == 'content' || res.cssFirstId == 'board')) {
                html += 'readonly ';
            }
            html +=
            'placeholder="없음"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">CSS</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="cssCode">' + res.cssCode + '</textarea></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="cssDel">삭제</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);
    });

    // 컨텐츠삭제
    $(document).on("click", "#cssDel", function(e) {
        if(confirm("해당 CSS를 삭제하시겠습니까?")) {
            var idx = $('#idx').val();

            var res = $ajax.deleteAjax('/adm/css/'+ idx);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("해당 CSS를 삭제하였습니다.")
                window.location.reload();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 컨텐츠수정
    $(document).on("submit", "#cssUpdateForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("cssNm")) return;
        if($event.validationFocus("cssCode")) return;

        var useYn;
        if($('#useYn').is(':checked')) {
            useYn = 'Y';
        }
        else {
            useYn = 'N';
        }

        var data = {
            cssNm : $('#cssNm').val(),
            useYn : useYn,
            cssFirstId : $('#cssFirstId').val(),
            cssSecondId : $('#cssSecondId').val(),
            cssCode : $('#cssCode').val(),
        };

        var idx = $('#idx').val();

        var res = $ajax.patchAjax('/adm/css/'+ idx, data);

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("CSS를 수정하였습니다.");
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });

    // 디테일 ID 오픈
    $(document).on("change", "#cssFirstId", function(e) {
        if($(this).val() == 'content' || $(this).val() == 'board') {
            $('#cssSecondId').attr('readonly', false);
        }
        else {
            $('#cssSecondId').val('')
            $('#cssSecondId').attr('readonly', true);
        }
    });

    //셀렉트박스를 만든다
    function creatSelect(cho='') {
        if(cho == '') {
            var html =
                '<option value="common">공통</option>' +
                '<option value="main">메인</option>' +
                '<option value="board">게시판</option>' +
                '<option value="content">컨텐츠</option>' +
                '<option value="qna">Q&A</option>' +
                '<option value="faq">FAQ</option>';
        }
        else {
            var res = {
                COMMON : '',
                MAIN : '',
                BOARD : '',
                CONTENT : '',
                QNA : '',
                FAQ : '',
            }
            res[cho] = 'selected';

            var html =
                '<option value="common"'+ res['common']+'>공통</option>' +
                '<option value="main"'+ res['main']+'>메인</option>' +
                '<option value="board"'+ res['board']+'>게시판</option>' +
                '<option value="content"'+ res['content']+'>컨텐츠</option>' +
                '<option value="qna"'+ res['qna']+'>Q&A</option>' +
                '<option value="faq"'+ res['faq']+'>FAQ</option>';
        }
        return html;
    }
});