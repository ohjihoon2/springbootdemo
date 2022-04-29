$(function() {
    //검색
    $('#boardListForm').submit(function(e) {
        e.preventDefault();

        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/board/' + boardId, param)
    });

    //페이징
    $('#pagination ul li a').not( '.on' ).click(function() {
        var param = {
            searchType : $('#hiddenSearchType').val(),
            searchKeyword : $('#hiddenSearchKeyword').val(),
            pageNum : $(this).data('val'),
        }
        $page.getGoPage('/board/' + boardId, param);
    });
    
    //상세 이동(일반)
    $('#boardTable tbody tr td').not('.off').click(function() {
        var idx = $(this).closest('tr').data('val');
        $page.goPage('/board/' + boardId + '/' + idx)
    });

    //상세 이동(포토)
    $('#boardPhoto ul li img, #boardPhoto ul li div span').click(function() {
        var idx = $(this).closest('li').data('val');
        $page.goPage('/board/' + boardId + '/' + idx)
    });

    //글쓰기
    $('#boardAdd').click(function() {
        $page.goPage('/board/' + boardId + '/detail')
    });
});