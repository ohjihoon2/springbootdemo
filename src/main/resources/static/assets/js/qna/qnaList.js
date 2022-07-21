$(function() {
    //검색
    $('#qnaListForm').submit(function(e) {
        e.preventDefault();

        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/qna', param)
    });

    //페이징
    $('#pagination ul li a').not( '.on' ).click(function() {
        var param = {
            searchType : $('#hiddenSearchType').val(),
            searchKeyword : $('#hiddenSearchKeyword').val(),
            pageNum : $(this).data('val'),
        }
        $page.getGoPage('/qna', param);
    });
    
    //상세 이동(일반)
    $('#qnaTable tbody tr td').not('.off').click(function() {
        var idx = $(this).closest('tr').data('val');
        $page.goPage('/qna/detail/' + idx)
    });

    //글쓰기
    $('#qnaAdd').click(function() {
        $page.goPage('/qna/detail');
    });
});