$(function() {
    // 관리자버튼 컨트롤
    $('#admBtn').click(function() {
        if($(this).next('ul').css('display') == 'none') {
            $(this).next('ul').show();
        }
        else {
            $(this).next('ul').hide();
        }
    });
    // 전체체크
    $('#checkAll').click(function() {
        if($(this).is(':checked')) {
            $('#boardTable tbody input[type="checkbox"]').prop('checked',true);
        }
        else {
            $('#boardTable tbody input[type="checkbox"]').prop('checked',false);
        }
    });
    // 게시글삭제
    $('#boardDel').click(function() {
        var check = $('#boardTable tbody input[type="checkbox"]:checked');
        if(check.length == 0) {
            alert("삭제 할 게시글을 선택해주세요.");
        }
        else {
            $('#admBtn').next('ul').hide();

            var data = {
                boardType : $('#boardType').val(),
            };

            var res = $ajax.postAjax('/adm/boardMaster', data);
        }
    });
});