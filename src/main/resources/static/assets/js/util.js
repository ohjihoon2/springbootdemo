(function(W, D) {
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
        postGoPage :function (data, url) {
            var form = document.createElement('form');
            form.id = 'postForm';

            for (var key in data) {
                var input = document.createElement('input');
                input.name = key;
                input.value = data[key];
                input.type = 'hidden';
                form.appendChild(input);
                $(document.body).append(form);
            }
            $("#postForm").attr('action', url);
            $("#postForm").attr('method', 'POST');
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

        //셀렉트형 이동
        goFamilyPage : function (e) {
            var site = $(e).val();
            if(site == '') {
                return;
            }
            else {
                location.href = site;
            }
        },
    },

    $ajax = {
        // patch 에이작스
        patchAjax : function (url, param, msg) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            $.ajax({
                type: "PATCH",
                url: url,
                data: JSON.stringify(param),
                contentType: "application/json",
                // 토큰 사용시
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (res) {
                    if(res == 1) {
                        alert(msg);
                        window.history.back();
                    }
                    else if(res == 0) {
                        alert("수정을 실패하였습니다.");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    return alert("네트워크 통신을 실패하였습니다.");
                }
            });
        },

        // delete 에이작스
        deleteAjax : function (url, msg) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            $.ajax({
                type: "DELETE",
                url: url,
                contentType: "application/json",
                // 토큰 사용시
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (res) {
                    if(res == 1) {
                        alert(msg);
                        window.history.back();
                    }
                    else if(res == 0) {
                        alert("삭제를 실패하였습니다.");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    return alert("네트워크 통신을 실패하였습니다.");
                }
            });
        },

        // post 에이작스
        postAjax : function (url, param, msg) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            var res;
            $.ajax({
                type: "POST",
                url: url,
                data: param,
                async: false,
                // contentType: "application/json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (response) {
                    res = response;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(msg == undefined) {
                        alert("네트워크 통신을 실패하였습니다.");
                    }
                    else {
                        alert(msg);
                    }
                }
            });
            return res;
        },
    }

    $event = {
        // 벨리데이션
        validationFocus: function (param) {
            var msg = {
                serialNo: '시리얼 넘버',
                deviceNm: '장치명',
                deviceLoc: '장치위치',
                volume: '용량',
                ipAddr: 'IP 주소',
                groupCode: '업체코드',
                groupName: '업체명',
                bizNo: '사업자번호',
                ceoNm: '대표자명',
                tel: '전화번호',
                groupType: '업종/업태',
                addr: '주소',
                sigungu: '주소',
                sido: '주소',
                dong: '주소',
                addrDetail: '상세주소'
            }

            for (var key in param) {
                if (!param[key].trim()) {
                    alert(msg[key] + ' 을(를) 입력해주세요.');
                    $('#' + key).focus();
                    return false;
                }
            }
            return true;
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

        //url 보이지 않게하기
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

        // null값 확인
        nullChk : function (str) {
            if(str == 'null' || str == null || typeof str == undefined || str == 'undefined') {
                return null;
            }
            else {
                return str;
            }
        },

        // 숫자 3자리 , 처리
        viewComma : function (num) {
            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
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
    $etc = {

    }

}(window, document));