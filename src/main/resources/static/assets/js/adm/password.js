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
            if ($event.validationFocus("userPrevPwd")) return;
            if ($event.validationFocus("userNewPwd")) return;
            if ($event.validationFocus("userNewPwdChk")) return;

            if($('#userNewPwd').val().length < 5) {
                alert("새 비밀번호를 5자 이상입력해주세요.");
                return;
            }
            else if(!$util.isPw($('#userNewPwd').val())) {
                alert("비밀번호는 영문, 숫자, 특수문자 를 포함해야합니다.");
                return;
            }
            else if($('#userNewPwd').val() != "") {
                if($('#userNewPwd').val() != $('#userNewPwdChk').val()) {
                    alert("새 비밀번호, 새 비밀번호 확인 이 일치하지 않습니다.");
                    return;
                }
            }

            var data = {
                password : $('#userPrevPwd').val(),
                newPassword : $('#userNewPwd').val(),
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
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                submitBtn = true;
            }
        }
    });
});