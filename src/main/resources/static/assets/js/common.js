$(function(){
    //메뉴
    headerMenuControl();
});

//메뉴를 컨트롤한다
function headerMenuControl() {
    $('#headerMenu > ul > li > span').click(function () {
        if ($(this).parent('li').hasClass('on')) {
            $(this).parent('li').addClass("off");
            $(this).parent('li').removeClass("on");
        } else if ($(this).parent('li').hasClass('off')) {
            $(this).parent('li').addClass("on");
            $(this).parent('li').removeClass("off");
        }
        // else {
        //     location.href=$(this).data('val');
        // }
    });
    $('#headerMenu > ul > li > ul > li').click(function () {
        $(this).data('val')
        if ($(this).data('val') != '') {
            location.href = $(this).data('val');
        }
        // else {
        //     location.href=$(this).data('val');
        // }
    });
}