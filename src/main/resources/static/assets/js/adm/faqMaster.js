// 에디터용
var oEditors = [];

$(function(){
    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/adm/content', param)
    });

    //FAQ 카테고리 추가 팝업
    $('#addBtn').click(function(){
        var html = 
            '<h4>FAQ 추가</h4>' +
            '<div class="mb20"></div>' +
            '<form id="faqAddForm">' +
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
            '<td colspan="3"><input id="faqNm" type="text" maxlength="15"></td>' +
            '</tr>' +
            '<tr>' +
            '<tr>' +
            '<th>Rank</th>' +
            '<td><input id="fmOrder" type="text" maxlength="5" oninput="this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*)\\./g, \'$1\');"></td>' +
            '<th>Use</th>' +
            '<td class="text-center"><input id="useYn" type="checkbox" checked></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt5"></div>' +
            '<span class="text-color-primary">※ 같은 순위를 지정하면 등록순으로 출력됩니다.</span>' +
            '<div class="mt400"></div>' +
            '<div class="bot-btn-box">' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">추가</button>' +
            '</div>' +
            '</form>';

        $popup.popupJs(html);
    });

    // 컨텐츠추가
    $(document).on("submit", "#faqAddForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("faqNm")) return;

        var useYn = 'N';
        if($('#useYn').is(':checked')) {
            useYn = 'Y';
        }

        var data = {
            faqNm : $('#faqNm').val(),
            fmOrder : $('#fmOrder').val(),
            useYn : useYn,
        };

        var res = $ajax.postAjax('/adm/faqMaster', data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("FAQ를 추가하였습니다.")
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });

    //컨텐츠수정 팝업
    $('[name="updateBtn"]').click(function(){
        var idx = $(this).data('val');

        var res = $ajax.postAjax('/adm/faqMaster/' + idx);
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
            '<h4>FAQ 추가</h4>' +
            '<div class="mb20"></div>' +
            '<form id="faqAddForm">' +
            '<input type="hidden" id="'+ res.idx +'">' +
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
            '<td colspan="3"><input id="faqNm" type="text" maxlength="15" value="'+ res.faqNm +'"></td>' +
            '</tr>' +
            '<tr>' +
            '<tr>' +
            '<th>Rank</th>' +
            '<td><input id="fmOrder" type="text" maxlength="5" value="'+ res.fmOrder +'" oninput="this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*)\\./g, \'$1\');"></td>' +
            '<th>Use</th>' +
            '<td class="text-center"><input id="useYn" type="checkbox" '+ useYn +'></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt5"></div>' +
            '<span class="text-color-primary">※ 같은 순위를 지정하면 등록순으로 출력됩니다.</span>' +
            '<div class="mt400"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="faqDel">삭제</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">추가</button>' +
            '</div>' +
            '</input>';

        $popup.popupJs(html);
    });

    // 컨텐츠삭제
    $(document).on("click", "#faqDel", function(e) {
        if(confirm("해당 FAQ를 삭제하시겠습니까?")) {
            var idx = $('#idx').val();

            var res = $ajax.deleteAjax('/adm/faq/'+ idx);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("해당 FAQ를 삭제하였습니다.")
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