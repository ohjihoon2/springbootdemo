$(function(){
    //게시판 타입을 삭제한다.
    $(document).on("click", ".qa-category i span", function(e) {
        $(this).closest('i').remove();
    });

    //게시판 타입을 추가한다.
    $(document).on("click", "#qaCategoryAdd", function(e) {
        if($event.validationFocus("qaCategory")) return;
        var qaCategory = $('#qaCategory').val();
        var returnYn = false;
        $('.qa-category').find('i').each(function(index, item){
            if(qaCategory == $(this).find('span').data('val')) {
                alert("Q&A타입은 중복될수 없습니다.");
                $('#qaCategory').focus();
                return returnYn = true;
            }
        })
        if(returnYn) return;
        var html = '<i>'+ qaCategory +'<span data-val="'+ qaCategory +'">×</span></i>\n';
        $('#qaCategory').val('');
        $('.qa-category').append(html);
    });

    //설정을 저장한다.
    // 코드그룹 수정
    $(document).on("submit", "#configForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("homepageName")) return;
        if($event.validationFocus("resetPassword")) return;

        var qaCategoryList = "";
        $('.qa-category').find('i').each(function(index, item){
            if(index != 0) {
                qaCategoryList += ';';
            }
            qaCategoryList += $(this).find('span').data('val');
        })

        var data = {
            homepageName : $('#homepageName').val(),
            mainSkinType : $('input[name="mainSkinType"]:checked').val(),
            resetPassword : $('#resetPassword').val(),
            qaCategoryList : qaCategoryList,
            qaEditorLevel : $('#qaEditorLevel').val(),
        };

        var res = $ajax.patchAjax('/adm/config' , data);

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("기본환경 설정을 수정하였습니다.");
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });
});