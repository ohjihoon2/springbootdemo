$(function() {

    // 컨트롤버튼 컨트롤
    $(document).on("click", "#controlBtn", function() {
        if($(this).next('ul').css('display') == 'none') {
            $('.control-btn').next('ul').hide();
            $(this).next('ul').show();
        }
        else {
            $(this).next('ul').hide();
        }
    });

    // 목록으로
    $('#goList').click(function() {
        $page.goPage('/qna');
    });

    // QNA 수정, 추가질문
    $('#qnaUpdate, #additionalQuestion').click(function() {
        $(this).closest('ul').hide();
        $page.goPage('/qna/detail/'+ idx);
    });

    // QNA 삭제
    $('#qnaDel').click(function() {
        $(this).closest('ul').hide();
        if(confirm("해당 게시물을 삭제하시겠습니까?")) {
            $ajax.deleteAjax('/board/'+ boardId + '/detail/' + idx + '/user', {}, true, "해당 게시물을 삭제하였습니다.", '/board/'+ boardId);
        }
    });

    // 첨부파일 숨김
    $('.qna-file-btn').click(function() {
        var li = $(this).closest('li').next('li');
        if(li.css('display') == 'none') {
            li.show();
            $(this).addClass('on');
        }
        else {
            li.hide();
            $(this).removeClass('on');
        }
    });
});
