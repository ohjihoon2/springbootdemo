$(function(){
    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/adm/codeGroup', param);
    });


    

    //컨텐츠추가 팝업
    $('#addBtn').click(function(){
        var html = 
            '<h4>코드그룹추가</h4>' +
            '<div class="mb20"></div>' +
            '<form id="codeGroupAddForm">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>ID</th>' +
            '<td><input id="codeGroupId" type="text" maxlength="15"></td>' +
            '<th>Name</th>' +
            '<td><input id="codeGroupNm" type="text" maxlength="15"></td>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt20"></div>' +
            '<h4>코드추가</h4>' +
            '<div class="mb20"></div>' +
            '<div class="min-height350">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="45%">' +
            '<col width="45%">' +
            '<col width="10%">' +
            '</colgroup>' +
            '<thead>' +
            '<tr>' +
            '<th>ID</th>' +
            '<th>Name</th>' +
            '<th class="update-btn"><button type="button" id="codeRowAdd" class="color-primary">추가</button></th>' +
            '</tr>' +
            '</thead>' +
            '<tbody id="codeRow">' +
            '<tr>' +
            '<td><input type="text" maxlength="15"></td>' +
            '<td><input type="text" maxlength="15"></td>' +
            '<td class="text-center">-</td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '</div>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">추가</button>' +
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);
    });

    // 코드 로우추가
    $(document).on("click", "#codeRowAdd", function(e) {
        var html =
            '<tr>' +
            '<td><input type="text" maxlength="15"></td>' +
            '<td><input type="text" maxlength="15"></td>' +
            '<td class="update-btn text-center">' +
            '<button type="button" class="color-gray codeRemove">삭제</button></th>' +
            '</td>' +
            '</tr>';
        $('#codeRow').append(html);
    });

    // 코드 로우삭제
    $(document).on("click", ".codeRemove", function(e) {
        var html =
        $(this).closest('tr').remove();
    });

    // 코드그룹추가
    $(document).on("submit", "#codeGroupAddForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("codeGroupId")) return;

        if(!$util.isEnNuUnder($('#codeGroupId').val())) {
            alert("코드 ID는 영문, 숫자, _만 입력가능합니다.");
            $('#codeGroupId').focus();
            return;
        }

        var param = {
            codeGroupId : $('#codeGroupId').val()
        }

        var result = $ajax.postAjax('/adm/codeGroupId', param);

        if(result.result == 'success') {
            alert("이미 사용중인 코드 ID입니다.\n코드 ID는 중복 될 수 없습니다.");
            return;
        }

        if($event.validationFocus("codeGroupNm")) return;

        var tr = $('#codeRow').find('tr');
        var codeList = new Array();
        for(i = 0; i < tr.length; i++) {
            var obj = new Object();
            obj.code = tr.eq(i).find('td').eq(0).children('input[type="text"]').val();
            if(obj.code == '') {
                alert("코드ID는 필수항목입니다.");
                tr.eq(i).find('td').eq(0).children('input[type="text"]').focus();
                return;
            }
            obj.codeNm = tr.eq(i).find('td').eq(1).children('input[type="text"]').val();
            if(obj.codeNm == '') {
                alert("코드명은 필수항목입니다.");
                tr.eq(i).find('td').eq(1).children('input[type="text"]').focus();
                return;
            }
            codeList.push(obj);
        }

        var data = {
            codeGroupId : param.codeGroupId,
            codeGroupNm : $('#codeGroupNm').val(),
            codeList : codeList,
        };

        var res = $ajax.postAjax('/adm/codeGroup', data);

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("크드그룹을 추가하였습니다.")
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });

    //컨텐츠수정 팝업
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
            '<div class="mb20"></div>' +
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

        oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef : oEditors,
            elPlaceHolder : "contentHtml",
            sSkinURI : "/js/externalLib/smarteditor2/SmartEditor2Skin.html",
            fCreator : "createSEditor2"
        });
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