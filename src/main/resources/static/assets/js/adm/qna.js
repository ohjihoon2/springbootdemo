// 에디터용
var oEditors = [];

//다중 통신 막기
submitBtn = true;

$(function(){
    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
            status : $('#status').val(),
        }
        $page.getGoPage('/adm/qna', param)
    });

    //페이지상태
    $('#status').change(function() {
        var param = {
            status : $('#status').val(),
        }
        $page.getGoPage('/adm/qna', param)
    });

    //페이징
    $('#pagination ul li a').not( '.disable' ).click(function() {
        var param = {
            searchType : $('#hiddenSearchType').val(),
            searchKeyword : $('#hiddenSearchKeyword').val(),
            pageNum : $(this).data('val'),
        }
        $page.getGoPage('/adm/user', param);
    });
    
    //Q&A 상세
    $('[name="detailBtn"]').click(function(){
        var qaIdx = $(this).data('val');

        var res = $ajax.postAjax('/adm/qna/' + qaIdx);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }


        res.config = $util.nullChkObj(res.config);
        for(var i = 0; i < res.resultList.length; i++) {
            res.resultList[i] = $util.nullChkObj(res.resultList[i]);
        }

        var secretYn ='';
        if(res.config.secretYn == 'Y') {
            secretYn = 'checked';
        }

        var qaEmailRecvYn ='';
        if(res.config.qaEmailRecvYn == 'Y') {
            qaEmailRecvYn = 'checked';
        }

        var html =
            '<h4>Q&A 상세</h4>' +
            '<div class="mb10"></div>' +
            '<form id="qnaDetailForm">' +
            '<input id="qaIdx" type="hidden" value="'+ res.config.qaIdx +'">' +
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
            '<td>'+ res.config.qaCategory +'</td>' +
            '<th>SecretYn</th>' +
            '<td class="text-center"><input type="checkbox"'+ secretYn +' disabled></td>' +
            '</tr>';
            for(var i = 0; i < res.resultList.length; i++) {
                var subject = '';
                if (i % 2 === 0) {
                    subject = "Question"
                }
                else {
                    subject = "Answer"
                }
                if (i > 1) {
                    subject = "Add " + subject;
                }

                var updateNicknm = res.resultList[i].updateNicknm;
                if (updateNicknm == "") {
                    updateNicknm = res.resultList[i].createNicknm;
                }
                if((res.config.qaStatus == 'A' || res.config.qaStatus == 'RA') && i == (res.resultList.length - 1)) {
                    html +=
                        '<tr><th colspan="4">' + subject + '' +
                        '<input id="idx" type="hidden" value="'+ res.resultList[i].idx +'">' +
                        '<input id="attachFileIdx" type="hidden" value="'+ res.resultList[i].attachFileIdx +'">' +
                        '</th></tr>' +
                        '<tr>' +
                        '<th>Title</th>' +
                        '<td colspan="3"><input type="text" id="qaSubject" value="'+ res.resultList[i].qaSubject +'"></td></th>' +
                        '</tr>' +
                        '<th>Upload</th>' +
                        '<td colspan="3"><input id="files" type="file" multiple></td></th>' +
                        '</tr>';
                    if(res.fileList[i].length > 0) {
                        html +=
                            '<tr>' +
                            '<th>file</th>' +
                            '<td colspan="3" class="file">';
                        for(var j=0; j < res.fileList[i].length; j++) {
                            html +=
                                '<p><a href="/file/download/'+ res.fileList[i][j].saveName +'" >'+ res.fileList[i][j].originalName +'</a><span data-val="'+ res.fileList[i][j].saveName +'">×</span></p>';
                        }
                        html +=
                            '</td>' +
                            '</tr>';
                    }
                    html +=
                        '<tr>' +
                        '<th>Detail</th>' +
                        '<td colspan="3"><textarea id="qaContent">'+ res.resultList[i].qaContent +'</textarea></td></tr>';
                }
                else {
                    html +=
                        '<tr><th colspan="4">' + subject + '</th></tr>' +
                        '<tr>' +
                        '<th>Title</th>' +
                        '<td colspan="3">' + res.resultList[i].qaSubject + '</td></th>' +
                        '</tr>';
                        if(res.fileList[i].length > 0) {
                            html +=
                                '<tr>' +
                                '<th>file</th>' +
                                '<td colspan="3" class="file">';
                                for(var j=0; j < res.fileList[i].length; j++) {
                                    html +=
                                    '<p><a href="/file/download/'+ res.fileList[i][j].saveName +'" >'+ res.fileList[i][j].originalName +'</a><span>×</span></p>';
                                }
                            html +=
                                '</td>' +
                                '</tr>';
                        }
                    html +=
                        '<tr>' +
                        '<th>Detail</th>' +
                        '<td colspan="3"><div class="table-scroll">' + res.resultList[i].qaContent + '</div></td></tr>';
                }
            }
            if(res.config.qaStatus == 'Q' || res.config.qaStatus == 'RQ') {
                var subject = 'Answer';
                if (res.config.qaStatus == 'RQ') {
                    subject = "Add " + subject;
                }

                html +=
                    '<tr><th colspan="4">' + subject + '</th></tr>' +
                    '<tr>' +
                    '<th>Title</th>' +
                    '<td colspan="3"><input type="text" id="qaSubject"></td></th>' +
                    '</tr>' +
                    '<th>Upload</th>' +
                    '<td colspan="3"><input id="files" type="file" multiple></td></th>' +
                    '</tr>' +
                    '<tr>' +
                    '<th>Detail</th>' +
                    '<td colspan="3"><textarea id="qaContent"></textarea></td></tr>';
            }
        html +=
            '<tr>' +
            '<th colspan="2">Email</th>' +
            '<td class="text-center" colspan="2"><input id="marketingYn" type="checkbox"'+ qaEmailRecvYn +' disabled></td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '<button type="button" id="qnaDel">삭제</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n';
        if(res.config.qaStatus == 'Q' || res.config.qaStatus == 'RQ') {
            html +=
                '<button type="submit">답변</button>';
        }
        else {
            html +=
                '<button type="button" id="updateBtn" class="color-primary">수정</button>';
        }
        html +=
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);

        oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef : oEditors,
            elPlaceHolder : "qaContent",
            sSkinURI : "/js/externalLib/smarteditor2/SmartEditor2Skin.html",
            fCreator : "createSEditor2"
        });
    });


    // Q&A 답변
    $(document).on("submit", "#qnaDetailForm", async function (e) {
        e.preventDefault();

        //에디터 내용 가져오기
        oEditors.getById["qaContent"].exec("UPDATE_CONTENTS_FIELD", []);

        //빈값체크
        if ($event.validationFocus("qaSubject")) return;
        if ($('#qaContent').val() == "<p>&nbsp;</p>") {
            $('#qaContent').val('');
        }
        if ($event.validationFocus("qaContent")) return;

        var data = {
            parentIdx: $('#qaIdx').val(),
            qaSubject: $('#qaSubject').val(),
            qaContent: $('#qaContent').val(),
        };

        $ajax.postFileAjax('/adm/qna', data, 'files', '', 'Q&A 답변을 완료하였습니다.', '파일 업로드 및 메일발송 중입니다.');
    });

    // 컨텐츠삭제
    $(document).on("click", "#qnaDel", function(e) {
        if(confirm("해당 Q&A를 삭제하시겠습니까?")) {
            var qaIdx = $('#qaIdx').val();

            var res = $ajax.deleteAjax('/adm/content/'+ qaIdx);
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

    // Q&A 답변수정
    $(document).on("click", "#updateBtn", function(e) {
        e.preventDefault();

        //에디터 내용 가져오기
        oEditors.getById["qaContent"].exec("UPDATE_CONTENTS_FIELD", []);

        //빈값체크
        if ($event.validationFocus("qaSubject")) return;
        if ($('#qaContent').val() == "<p>&nbsp;</p>") {
            $('#qaContent').val('');
        }
        if ($event.validationFocus("qaContent")) return;

        var data = {
            parentIdx: $('#qaIdx').val(),
            qaSubject: $('#qaSubject').val(),
            qaContent: $('#qaContent').val(),
            attachFileIdx: $('#attachFileIdx').val(),
        };

        var idx = $('#idx').val();
        $ajax.patchFileAjax('/adm/qna/' + idx, data, 'files', '', 'Q&A 답변을 완료하였습니다.', '파일 업로드 및 메일발송 중입니다.');
    });

    // 파일삭제
    $(document).on("click", ".file > p > span", function(e) {
        if(confirm("해당 파일을 삭제하시겠습니까?\n(파일은 즉시 삭제되며, 복구 할수 없습니다.)")) {
            var saveName = $(this).data('val');

            var res = $ajax.deleteAjax('/file/'+ saveName);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                removeFileHtml(this);
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });
});

// 파일 TR 또는 TD를 삭제한다.
function removeFileHtml(th) {
    if($(th).closest('.file').find('p').length > 1) {
        $(th).closest('p').remove();
    }
    else {
        $(th).closest('tr').remove();
    }
}