$(function(){
    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/adm/content', param)
    });

    //컨텐츠추가 팝업
    $('#addBtn').click(function(){
        var html = 
            '<h4>CSS 추가</h4>' +
            '<div class="mb20"></div>' +
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
            '<select id="cssFirstId">' +
            '<option value="common">공통</option>' +
            '<option value="main">메인</option>' +
            '<option value="board">게시판</option>' +
            '<option value="qna">Q&A</option>' +
            '<option value="faq">FAQ</option>' +
            '</select>' +
            '</td>' +
            '<th>Detail ID</th>' +
            '<td><input id="cssSecondId" type="text" maxlength="15" readonly placeholder="없음"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">CSS</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4" class="p0"><textarea id="cssCode"></textarea></td>' +
            '</tr>' +
            '<tr>' +
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

        console.log(res);
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

    //컨텐츠수정 팝업
    $('[name="updateBtn"]').click(function(){
        var idx = $(this).data('val');

        var res = $ajax.postAjax('/adm/content/' + idx);
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
            '<h4>컨텐츠 수정</h4>' +
            '<div class="mb20"></div>' +
            '<form id="contentUpdateForm">' +
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
            '<th>Id</th>' +
            '<td><input id="contentId" type="text" value="'+ res.contentId +'" maxlength="15"><input id="contentIdOrigin" type="hidden" value="'+ res.contentId +'"></td>' +
            '<th>Use</th>' +
            '<td class="text-center"><input id="useYn" type="checkbox"'+ useYn +'></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td colspan="3"><input id="contentNm" type="text" value="'+ res.contentNm +'" maxlength="15"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Html</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4"><textarea id="contentHtml">' + res.contentHtml + '</textarea></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="contentDel">삭제</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);

        oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef : oEditors,
            elPlaceHolder : "contentHtml",
            sSkinURI : "/js/externalLib/smarteditor2/SmartEditor2Skin.html",
            fCreator : "createSEditor2"
        });
    });

    // 컨텐츠삭제
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

    // 컨텐츠수정
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

        oEditors.getById["contentHtml"].exec("UPDATE_CONTENTS_FIELD", []);

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
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });
});