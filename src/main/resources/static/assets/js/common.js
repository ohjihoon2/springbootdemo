$(function(){
    //메뉴
    headerMenuControl();
});

//메뉴를 컨트롤한다
function headerMenuControl() {
    $('#headerList > ul > li > span').click(function(){
        if($(this).data('val') === undefined) {
            if($(this).parent('li').hasClass('on')) {
                $(this).parent('li').addClass("off");
                $(this).parent('li').removeClass("on");
            }
            else if($(this).parent('li').hasClass('off')) {
                $(this).parent('li').addClass("on");
                $(this).parent('li').removeClass("off");
            }
        }
        else {
            location.href=$(this).data('val');
        }
    });
    $('#headerList > ul > li > ul > li').click(function () {
        if($(this).data('val') !== undefined) {
            location.href = $(this).data('val');
        }
        // else {
        //     location.href=$(this).data('val');
        // }
    });
}