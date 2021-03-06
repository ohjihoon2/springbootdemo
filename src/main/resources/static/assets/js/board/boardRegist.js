// 에디터용
var oEditors = [];

$(function() {
    oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef : oEditors,
        elPlaceHolder : "boardContent",
        sSkinURI : "/js/externalLib/smarteditor2/SmartEditor2Skin.html",
        fCreator : "createSEditor2"
    });

    // 게시판 사진업로드 권한
    if(!editorLevel) {
        $("#boardContent").next('iframe').one("load", function() {
            $(this).contents().find('#smart_editor2 .se2_text_tool .se2_multy').remove();
        })
    }

    //게시물 추가
    $(document).on("submit", "#boardRegistForm", async function (e) {
        e.preventDefault();

        //에디터 내용 가져오기
        oEditors.getById["boardContent"].exec("UPDATE_CONTENTS_FIELD", []);

        //빈값체크
        if ($event.validationFocus("boardSubject")) return;

        if ($('#boardContent').val() == "<p>&nbsp;</p>") {
            $('#boardContent').val('');
        }
        if ($event.validationFocus("boardContent")) return;

        var thumbnailYn = 'N';
        if($('#thumb')[0].files.length > 0) {
            thumbnailYn = 'Y';
        }

        var noticeYn = 'N';
        if($('#noticeYn').is(':checked')) {
            noticeYn = 'Y';
        }

        var data = {
            boardSubject: $('#boardSubject').val(),
            boardContent: $('#boardContent').val(),
            thumbnailYn: thumbnailYn,
            noticeYn: noticeYn,
        };

        $ajax.postFileAjax('/board/'+ boardId +'/detail', data, 'files', 'thumb', '게시물을 등록하였습니다.', '파일 업로드 중입니다.', '/board/'+ boardId);
    });

    //목록으로
    $('#goList').click(function () {
        history.back();
    });


});