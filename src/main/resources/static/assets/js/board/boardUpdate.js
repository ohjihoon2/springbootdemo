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

    // 썸네일 삭제
    $(document).on("click", "#thumbnailDel", function(e) {
        if(confirm("해당 FAQ를 삭제하시겠습니까?")) {
            var res = $ajax.deleteAjax('/board/thumbnail/'+ idx);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("해당 FAQ를 삭제하였습니다.");
                $(this).closest('.file-list').remove();
                $('#thumb').show();

            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 게시판 수정
    $(document).on("submit", "#boardUpdateForm", async function (e) {
        e.preventDefault();

        //에디터 내용 가져오기
        oEditors.getById["boardContent"].exec("UPDATE_CONTENTS_FIELD", []);

        //빈값체크
        if ($event.validationFocus("boardSubject")) return;

        if ($('#boardContent').val() == "<p>&nbsp;</p>") {
            $('#boardContent').val('');
        }
        if ($event.validationFocus("boardContent")) return;

        var data = {
            masterIdx: masterIdx,
            boardSubject: $('#boardSubject').val(),
            boardContent: $('#boardContent').val(),
            attachFileIdx: $('#attachFileIdx').val()
        };

        // $ajax.patchFileAjax('/board/'+ boardId +'/detail/' + idx, data, 'files', 'thumb', '게시물을 수정하였습니다.', '파일 업로드 중입니다.');
    });

});