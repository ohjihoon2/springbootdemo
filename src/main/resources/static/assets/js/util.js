
/* Name util.js
** Developer BOGUM KIM
** Create 2021. 07. 12
** Update 2022. 04. 18
*/

(function(W, D) {

    //인풋숫자만 입력 정규식
    // oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"

    //유틸시작
    $(function() {

    });
    $page = {
        // 공통페이지 이동함수
        goPage: function (url) {
            location.href = url;
        },

        // get 페이지 이동함수
        getGoPage: function (url, param) {
            var int = 0;
            var res = "";
            for (var key in param) {
                res += (int == 0 ? '?' : '&');
                res += key + "=" + param[key];
                int++;
            }
            location.href = url + res;
        },

        // post 페이지 이동 (폼방식)
        postGoPage :function (url, data) {
            var form = document.createElement('form');
            form.id = 'postForm';
            console.log(data);

            for (var key in data) {
                var input = document.createElement('input');
                input.name = key;
                input.value = data[key];
                input.type = 'hidden';
                form.appendChild(input);
                $(document.body).append(form);
            }
            $("#postForm").attr('action', url);
            $("#postForm").attr('method', 'get');
            $("#postForm").attr('enctype', 'application/json');
            $("#postForm").submit();
        },

        //뒤로가기
        goBack : function() {
            window.history.back();
        },

        //홈으로
        goHomePage : function () {
            location.href = '/';
        },
    },

    $ajax = {
        // patch 에이작스
        patchAjax : function (url, param = {}, async=false) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            var res;

            $.ajax({
                type: "PATCH",
                url: url,
                data: JSON.stringify(param),
                async: async,
                contentType: "application/json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (response) {
                    res = response;
                },
                error: function (XMLHttpRequest, textStatus) {
                    res = textStatus;
                }
            });
            return res;
        },

        // delete 에이작스
        deleteAjax : function (url, param, async=false) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            var res;

            $.ajax({
                type: "DELETE",
                url: url,
                data: JSON.stringify(param),
                async: async,
                contentType: "application/json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (response) {
                    alert(response);
                    console.log(response);
                    res = response;
                },
                error: function (XMLHttpRequest, textStatus) {
                    res = textStatus;
                }
            });
            return res;
        },

        // post 에이작스
        postAjax : function (url, param = {}, async=false) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var contentType = "application/json";

            var res;

            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(param),
                async: async,
                processData: false,
                contentType: contentType,
                cache: false,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (response) {
                    res = response;
                },
                error: function (XMLHttpRequest, textStatus) {
                    res = textStatus;
                }
            });
            return res;
        },

        // post 로딩에이작스 , 파일첨부
        // 로딩으로 인해 동기화를 기본으로 실행한다.
        postFileAjax : function (url, param = {}, files='', success = '', loding = '') {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var contentType = "application/json";

            if(files != '') {
                contentType = false;
                var data = new FormData();
                data.append("param", new Blob([JSON.stringify(param)], {type: "application/json"}));
                for(var i=0; i < $('#' + files)[0].files.length; i++) {
                    data.append('files',$('#' + files)[0].files[i]);
                }
                param = data;
            }
            else {
                param = JSON.stringify(param);
            }

            var res;

            $.ajax({
                type: "POST",
                url: url,
                data: param,
                async: true,
                processData: false,
                contentType: contentType,
                cache: false,
                beforeSend: function (xhr) {
                    if(loding != '') {
                        $popup.LoadingWithMask(loding);
                    }
                    xhr.setRequestHeader(header, token);
                },
                success: function (response) {
                    if (response == "error") {
                        alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                    }
                    else {
                        alert(success);
                        window.location.reload();
                    }
                },
                error: function () {
                    alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
                },
                complete: function () {
                    if(loding != '') {
                        $popup.closeLoadingWithMask();
                    }
                }
            });
            return res;
        },
    }

    $event = {
        // 인풋 빈값 벨리데이션
        validationFocus: function (id) {
            var msg = {
                userId: '아이디',
                userPwd: '비밀번호',
                userPwdChk: '비밀번호 확인',
                userNm: '이름',
                userNicknm: '닉네임',
                userEmail: '이메일',
                userEmail1: '이메일',
                userEmail2: '이메일',
                userPhone: '연락처',
                userPhone1: '연락처',
                userPhone2: '연락처',
                userPhone3: '연락처',
                boardNm: '게시판명',
                boardId: '게시판ID',
                contentId: '컨텐츠ID',
                contentNm: '컨텐츠명',
                password: '비밀번호',
                passwordChk: '비밀번호 확인',
                newPassword: '새 비밀번호',
                newPasswordChk: '새 비밀번호 확인',
                qaSubject: 'Q&A답변 제목',
                qaContent: 'Q&A답변 내용',
                faqNm: 'FAQ타입명',
                masterIdx: 'FAQ타입',
                faqQuestion: 'FAQ명',
                faqAnswer: 'FAQ답변',
            }

            if ($('#' + id).val() == '') {
                alert(msg[id] + ' 을(를) 입력해주세요.');
                $('#' + id).focus();
                return true;
            }
        },
        // 체크박스 빈값 벨리데이션
        validationChk: function (id) {
            var msg = {
                agree1: '이메일 인증메일 수신동의',
                agree2: '이용 약관',
                agree3: ' 개인정보 수집 및 이용 동의',
            }

            if (!$('#' + id).is(":checked")) {
                alert(msg[id] + ' 을(를) 체크해주세요.');
                return true;
            }
        },
    }

    $util = {
        //url param값 개별 가져오기
        getParameterByName : function(name) {
            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
            return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        },

        //url Get보이지 않게하기
        paramViewNone : function(){
            history.replaceState({}, null, location.pathname);
        },

        //특수문자 치환
        transferText(str){
            var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi;
            if(regExp.test(str)){
                var t = str.replace(regExp, "");
                str = t;
            }
            return str;
        },

        //아이디 영문 숫자만
        isId(asValue) {
            var regExp = /^[a-z0-9+]*$/;
            return regExp.test(asValue);
        },

        //비밀번호 최소 8 자, 하나 이상의 문자와 하나의 숫자
        isPw(asValue) {
            var regExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
            return regExp.test(asValue);
        },

        //닉네임 한글(자음, 모음 제외), 영문, 숫자만 가능하며 2-10자리
        isNn(asValue) {
            var regExp = /^[가-힣|a-z|A-Z|0-9|]+$/;
            return regExp.test(asValue);
        },

        //이메일 유효성 검사
        isEm(asValue) {
            if(asValue == "") {
                return true;
            }
            var regExp = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
            return regExp.test(asValue);
        },

        //연락처 유효성 검사
        isPh(asValue) {
            var regExp = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;
            return regExp.test(asValue);
        },

        //영문숫자만 가능
        isEnNu(asValue) {
            var regExp = /[a-zA-Z0-9]/;
            return regExp.test(asValue);
        },

        // null값 확인
        nullChk : function (str) {
            if(str == 'null' || str == null || typeof str == undefined || str == 'undefined') {
                return null;
            }
            else {
                return str;
            }
        },
        // 배열 null치환
        nullChkObj : function (obj) {
            for (var key in obj){
                if(obj[key] == 'null' || obj[key] == null || typeof obj[key] == undefined || obj[key] == 'undefined') {
                    obj[key] = "";
                }
            }
            return obj;
        },

        // 연속된 공백을 하나의 공백으로 치환
        cdIns : function (str) {
            str = str.replace(/ +/g, " ");
            return str;
        },

        // 숫자 3자리 , 처리
        viewComma : function (num) {
            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        },

        // 연락처 자동 하이픈
        phoneAutoHyphen : function (ph) {
            $(document).on("focusin", "#" + ph, function() {
                $(this).val($(this).val().replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1')) ;
            });
            $(document).on("focusout", "#" + ph, function() {
                $(this).val($(this).val().replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/\-{1,2}$/g, "")) ;
            });
        },

        // 머였지..
        resize : function (obj) {
            obj.style.height = '0px';
            obj.style.height = (obj.scrollHeight - 3) + 'px';
        },

        // 머였지..
        limitLines : function(obj, cnt) {
            var tempText = obj.value;
            var lineSplit = tempText.split("\n");

            // 최대라인수 제어
            if (tempText.length > 200) {
                event.preventDefault ();
            }
            // 최대라인수 제어
            else if (lineSplit.length > cnt && event.keyCode == 13) {
                event.preventDefault ();
            }
        }
    }
    $popup = {
        popupJs : function(html) {
            var backHeight = $(document).height(); //뒷 배경의 상하 폭
            var backWidth = window.document.body.clientWidth; //뒷 배경의 좌우 폭
            var popupCover = "<div class='background'></div>"; //뒷 배경을 감쌀 커버
            var popup = '';
            popup +=
                "<div class='popup-js'>" +
                "<div class='popup-js-close' onclick='$popup.popupJsClose();'>" +
                "<i class=\"fa fa-times\"></i>" +
                "</div>" +
                "<div class=popup-js-content>" +
                html +
                "</div>" +
                "</div>";
            $('body').append(popupCover).append(popup);
            $('.background').css({ 'width': backWidth, 'height': backHeight, 'opacity': '0.3' });
        },
        popupJsClose : function() {
            $('.background').remove();
            $('.popup-js').remove();
        },
        popupWindowClose : function() {
            window.close();
        },

        LoadingWithMask : function(html) {
        var maskHeight = window.document.body.clientHeight;
        var maskWidth  = window.document.body.clientWidth;

        var mask ="<div id='mask' style='position:fixed; z-index:9000; background-color:#000000; display:none; left:0; top:0;'>";
        mask +="</div>";
        var loadingImg = "<div id='loadingImg' style='position:fixed; top:0; z-index:8999; width: 100%; margin: 0px auto; text-align: center;'>";
        loadingImg +=" <img src='/img/common/rolling.gif' width='60px;' style='margin-bottom: 10px;'/>";
        loadingImg +=" <p>"+ html +"</p>";
        loadingImg +="</div>";


        $('body').append(mask);
        $('body').append(loadingImg);

        $('#mask').css({
            'width' : maskWidth
            ,'height': '100%'
            ,'opacity' :'0.3'
        });

        $('#loadingImg').css({
            'margin-top' : (maskHeight/2-80)
            ,'opacity' :'1'
        });

        //마스크 표시
        $('#mask').show();

        //로딩중 이미지 표시
        $('#loadingImg').show();

        return true;
    },

    closeLoadingWithMask : function() {
        $('#mask, #loadingImg').hide();
        $('#mask, #loadingImg').remove();
    }
    },

    $etc = {

    }

}(window, document));