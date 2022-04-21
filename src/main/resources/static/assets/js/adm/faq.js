// 에디터용
var oEditors = [];

$(function(){
    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
            masterIdx : $('#faqNm').val(),
        }
        $page.getGoPage('/adm/faq', param)
    });

    //페이지상태
    $('#faqNm').change(function() {
        var param = {
            masterIdx : $('#faqNm').val(),
        }
        $page.getGoPage('/adm/faq', param)
    });

    //FAQ추가 팝업
    $('#addBtn').click(function(){
        var fm = $(this).data('val');
        var res = $ajax.postAjax('/adm/faq/faqNm');
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }
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
            '<th>Type</th>' +
            '<td>' +
            '<select id="masterIdx">' +
            '<option value="">타입 선택</option>';
        for (var i=0; i<res.length; i++) {
            var masterIdx = '';
            if(res[i].idx == fm) {
                masterIdx = 'selected';
            }
            html += '<option value="'+ res[i].idx +'"'+ masterIdx +'>'+ res[i].faqNm +'</option>';
        }
        html +=
            '</select>' +
            '<th>Rank</th>' +
            '<td class="text-center"><input id="faqOrder" type="text" maxlength="5" oninput="this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*)\\./g, \'$1\');"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td colspan="3"><input id="faqQuestion" type="text" maxlength="70"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Answer</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4" class="p0 pb10"><textarea id="faqAnswer"></textarea></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="2" class="mt10">Use</th>' +
            '<td colspan="2" class="text-center"><input id="useYn" type="checkbox" checked></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt5"></div>' +
            '<span class="text-color-primary">※ 같은 순위를 지정하면 등록순으로 출력됩니다.</span>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">추가</button>' +
            '</div>' +
            '</form>';
        $popup.popupJs(html);

        oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef : oEditors,
            elPlaceHolder : "faqAnswer",
            sSkinURI : "/js/externalLib/smarteditor2/SmartEditor2Skin.html",
            fCreator : "createSEditor2"
        });
    });

    // FAQ추가
    $(document).on("submit", "#faqAddForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("masterIdx")) return;
        if($event.validationFocus("faqQuestion")) return;

        //에디터 내용 가져오기
        oEditors.getById["faqAnswer"].exec("UPDATE_CONTENTS_FIELD", []);
        if($('#faqAnswer').val() == "<p>&nbsp;</p>") {
            $('#faqAnswer').val('');
        }
        if($event.validationFocus("faqAnswer")) return;

        var useYn = 'N';
        if($('#useYn').is(':checked')) {
            useYn = 'Y';
        }
        var faqOrder = null;
        if($('#faqOrder').val() != '') {
            faqOrder = $('#faqOrder').val();
        }

        var data = {
            masterIdx : $('#masterIdx').val(),
            faqOrder : faqOrder,
            faqQuestion : $('#faqQuestion').val(),
            faqAnswer : $('#faqAnswer').val(),
            useYn : useYn,
        };

        var res = $ajax.postAjax('/adm/faq', data);
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

        var res = $ajax.postAjax('/adm/faq/' + idx);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }

        res.faq = $util.nullChkObj(res.faq);

        var useYn ='';
        if(res.useYn == 'Y') {
            useYn = 'checked';
        }

        var html =
            '<h4>FAQ 수정</h4>' +
            '<div class="mb20"></div>' +
            '<form id="faqUpdateForm">' +
            '<input type="hidden" id="idx" value="'+ res.faq.idx +'">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>Type</th>' +
            '<td>' +
            '<select id="masterIdx">' +
            '<option value="">타입 선택</option>';
        for (var i=0; i<res.typeList.length; i++) {
            var masterIdx = '';
            if(res.typeList[i].idx == res.faq.masterIdx) {
                masterIdx = 'selected';
            }
            html += '<option value="'+ res.typeList[i].idx +'"'+ masterIdx +'>'+ res.typeList[i].faqNm +'</option>';
        }
        html +=
            '</select>' +
            '<th>Rank</th>' +
            '<td class="text-center"><input id="faqOrder" type="text" maxlength="5" value="'+ res.faq.faqOrder +'" oninput="this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*)\\./g, \'$1\');"></td>' +
            '</tr>' +
            '<tr>' +
            '<th>Name</th>' +
            '<td colspan="3"><input id="faqQuestion" type="text" maxlength="70" value="'+ res.faq.faqQuestion +'"></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="4">Answer</th>' +
            '</tr>' +
            '<tr>' +
            '<td colspan="4" class="p0 pb10"><textarea id="faqAnswer">'+ res.faq.faqAnswer +'</textarea></td>' +
            '</tr>' +
            '<tr>' +
            '<th colspan="2" class="mt10">Use</th>' +
            '<td colspan="2" class="text-center"><input id="useYn" type="checkbox" '+ useYn +'></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt5"></div>' +
            '<span class="text-color-primary">※ 같은 순위를 지정하면 등록순으로 출력됩니다.</span>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="faqDel">삭제</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';
        $popup.popupJs(html);

        oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef : oEditors,
            elPlaceHolder : "faqAnswer",
            sSkinURI : "/js/externalLib/smarteditor2/SmartEditor2Skin.html",
            fCreator : "createSEditor2"
        });
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

    // FAQ수정
    $(document).on("submit", "#faqUpdateForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("masterIdx")) return;
        if($event.validationFocus("faqQuestion")) return;

        //에디터 내용 가져오기
        oEditors.getById["faqAnswer"].exec("UPDATE_CONTENTS_FIELD", []);
        if($('#faqAnswer').val() == "<p>&nbsp;</p>") {
            $('#faqAnswer').val('');
        }
        if($event.validationFocus("faqAnswer")) return;

        var useYn = 'N';
        if($('#useYn').is(':checked')) {
            useYn = 'Y';
        }

        var faqOrder = null;
        if($('#faqOrder').val() != '') {
            faqOrder = $('#faqOrder').val();
        }

        var idx = $('#idx').val();
        var data = {
            masterIdx : $('#masterIdx').val(),
            faqOrder : faqOrder,
            faqQuestion : $('#faqQuestion').val(),
            faqAnswer : $('#faqAnswer').val(),
            useYn : useYn,
        };

        var res = $ajax.patchAjax('/adm/faq/'+ idx, data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("FAQ를 수정하였습니다.");
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });
});