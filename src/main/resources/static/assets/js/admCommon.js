$(function(){
    //메뉴
    headerMenuControl();

    //상단으로 버튼
    $("#topBtn").on("click", function() {
        $("html, body").animate({scrollTop:0}, '500');
        return false;
    });

    $(window).scroll(function(){
        if($(window).scrollTop() == 0) {
            $('#topBtn').hide();
        }
        else {
            $('#topBtn').show();
        }
    });
});

//메뉴를 컨트롤한다
function headerMenuControl() {
    $('.header-list > ul > li > span').click(function(){
        if($(this).parent('li').hasClass('on')) {
            $(this).parent('li').addClass("off");
            $(this).parent('li').removeClass("on");
        }
        else if($(this).parent('li').hasClass('off')) {
            $(this).parent('li').addClass("on");
            $(this).parent('li').removeClass("off");
        }
        // else {
        //     location.href=$(this).data('val');
        // }
    });
    $('.header-list > ul > li > ul > li').click(function(){
        $(this).data('val')
        if($(this).data('val') != '') {
            location.href=$(this).data('val');
        }
        // else {
        //     location.href=$(this).data('val');
        // }
    });
}

