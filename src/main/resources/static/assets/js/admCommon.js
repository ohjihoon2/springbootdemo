$(document).ready(function() {
    headerMenuControl();
});

//메뉴를 컨트롤한다
function headerMenuControl() {
    $('.header-list > ul > li').click(function(){
        if($(this).hasClass('on')) {
            $(this).addClass("off");
            $(this).removeClass("on");
        }
        else if($(this).hasClass('off')) {
            $(this).addClass("on");
            $(this).removeClass("off");
        }
        // else {
        //     location.href=$(this).data('val');
        // }
    });
}

