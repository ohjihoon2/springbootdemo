// 에디터용
var oEditors = [];

$(function() {
    oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef : oEditors,
        elPlaceHolder : "qnaContent",
        sSkinURI : "/js/externalLib/smarteditor2/SmartEditor2Skin.html",
        fCreator : "createSEditor2"
    });

    // 게시판 사진업로드 권한
    if(!editorLevel) {
        $("#qnaContent").next('iframe').one("load", function() {
            $(this).contents().find('#smart_editor2 .se2_text_tool .se2_multy').remove();
        })
    }

    //게시물 추가
    $(document).on("submit", "#qnaRegistForm", async function (e) {
        e.preventDefault();

        //에디터 내용 가져오기
        oEditors.getById["qnaContent"].exec("UPDATE_CONTENTS_FIELD", []);

        //빈값체크
        if ($event.validationFocus("qnaSubject")) return;

        if ($('#qnaContent').val() == "<p>&nbsp;</p>") {
            $('#qnaContent').val('');
        }
        if ($event.validationFocus("qnaContent")) return;

        var qaEmailRecvYn = 'N';
        if($('#qaEmailRecvYn').is(':checked')) {
            qaEmailRecvYn = 'Y';
        }
        var secretYn = 'N';
        if($('#secretYn').is(':checked')) {
            secretYn = 'Y';
        }

        var data = [
            {
                qaEmailRecvYn : qaEmailRecvYn,
                qaCategory : $('#qaCategory').val(),
                qaStatus : 'Q',
                secretYn : secretYn,
            },
            {
                parentIdx: null,
                qaSubject: $('#qnaSubject').val(),
                qaContent: $('#qnaContent').val(),
            }
        ];

        $ajax.postFileAjax('/qna/detail', data, 'files', '', 'QNA를 등록하였습니다.', '파일 업로드 중입니다.', '/qna');
    });

    //목록으로
    $('#goList').click(function () {
        history.back();
    });


});