$(function() {
    // 컨트롤버튼 컨트롤
    $('#controlBtn').click(function() {
        if($(this).next('ul').css('display') == 'none') {
            $(this).next('ul').show();
        }
        else {
            $(this).next('ul').hide();
        }
    });
});