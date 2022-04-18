// 에디터용
var oEditors = [];

//다중 통신 막기
submitBtn = true;

$(function(){

    // 비밀번호변경
    $(document).on("submit", "#passwordForm", function(e) {
        e.preventDefault();

        if (submitBtn) {
            //빈값체크
            if ($event.validationFocus("password")) return;
            if ($event.validationFocus("newPassword")) return;
            if ($event.validationFocus("newPasswordChk")) return;

            if($('#newPassword').val().length < 5) {
                alert("새 비밀번호를 5자 이상입력해주세요.");
                return;
            }
            else if(!$util.isPw($('#newPassword').val())) {
                alert("비밀번호는 영문, 숫자, 특수문자 를 모두 포함해야합니다.");
                return;
            }
            else if($('#newPassword').val() != $('#newPasswordChk').val()) {
                alert("새 비밀번호, 새 비밀번호 확인 이 일치하지 않습니다.");
                return;
            }
            else if($('#password').val() == $('#newPassword').val()) {
                alert("현재 비밀번호와 새 비밀번호는 동일할수 없습니다.");
            }

            var data = {
                password : $('#password').val(),
                newPassword : $('#newPassword').val(),
            };

            submitBtn = false;

            var res = $ajax.patchAjax('/adm/admin/password/' + myIdx, data);

            console.log(res);
            if (res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            } else if (res.result == "success") {
                alert("비밀번호를 변경하였습니다.");
                $popup.popupWindowClose();

            } else if (res.result == "fail") {
                alert('현재 비밀번호가 틀렸습니다.');
                submitBtn = true;
            }
        }
    });
});