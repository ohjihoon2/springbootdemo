var cnt = 1;
var loginBtn = true;

$(function(){
    // 메뉴저장
    $('#menuTreeForm').submit(function(event){
        event.preventDefault();

        var tr = $('#menuTreeTable tbody').find('tr');
        var parent = 1;
        var data = new Array();
        for(i = 0; i < tr.length; i++) {
            var obj = new Object();
            obj.idx = (i + 1);
            obj.lvl = tr.eq(i).data('val');
            if(obj.lvl == 1) {
                parent = obj.idx;
            }
            obj.parentIdx = parent;
            obj.name = tr.eq(i).find('td').eq(1).children('input[type="text"]').val();
            if(obj.name == '') {
                alert(" Name은 필수항목입니다.");
                tr.eq(i).find('td').eq(1).children('input[type="text"]').focus();
                return;
            }
            obj.link = tr.eq(i).find('td').eq(2).children('input[type="text"]').val();
            if(tr.eq(i).find('td').eq(4).children('input[type="checkbox"]').is(':checked')) {
                obj.openYn = 'Y';
            }
            else {
                obj.openYn = 'N';
            }
            data.push(obj);
        }

        var res = $ajax.postAjax('/adm/menuTree', data);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            location.href = "/";
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });

    // 메뉴추가
    $('#addBtn').click(function(){
        menuTreeAdd();
    });

    // 전체취소
    $('#cancelBtn').click(function(){
        window.location.reload();
    });
    
    // 위로올리기
    $(document).on("click", "[name='upMoveBtn']", function() {
        var tr = $(this).closest('tr');
        if($(tr).data('val') == '1') {
            var standardTr = tr;
            while (true) {
                if($(standardTr).prev('tr').length == 0) {
                    return alert("더이상 이동할 수 없습니다.");
                }
                standardTr = $(standardTr).prev('tr');
                if($(standardTr).data('val') == 1) {
                    break;
                }
            };
            var trAll = $(tr).nextAll('tr');

            $(standardTr).before(tr);

            for (var i = 0; i < trAll.length; i++) {
                if($(trAll[i]).data('val') == 2) {
                    $(standardTr).before(trAll[i]);
                }
                else {
                    break;
                }
            };
        }
        else if($(tr).data('val') == '2') {
            if($(tr).prev('tr').data('val') == '1' || $(tr).prev('tr').length == 0) {
                alert("더이상 이동할  수 없습니다.");
            }
            else {
                $(tr).prev('tr').before(tr);
            }
        }
    });

    // 아래로 내리기
    $(document).on("click", "[name='downMoveBtn']", function() {
        var tr = $(this).closest('tr');
        if($(tr).data('val') == '1') {
            var standardTr = tr;
            var cnt = 0;
            if($(standardTr).next('tr').data('val') == 1) {
                cnt++;
            }
            while (true) {
                if(cnt == 2) {
                    break;
                }
                standardTr = $(standardTr).next('tr');
                if(cnt == 0 && $(standardTr).next('tr').length == 0) {
                    return alert("더이상 이동할 수 없습니다.");
                }
                else if($(standardTr).next('tr').data('val') == 1 || $(standardTr).next('tr').length == 0) {
                    cnt++;
                }
            };
            var trAll = $(tr).nextAll('tr');
            var trCh = new Array();

            for (var i = 0; i < trAll.length; i++) {
                if($(trAll[i]).data('val') == 2) {
                    trCh[i] = trAll[i];
                }
                else {
                    break;
                }
            }
            for (var i = (trCh.length - 1); i >= 0; i--) {
                $(standardTr).after(trCh[i]);
            }

            $(standardTr).after(tr);
        }
        else if($(tr).data('val') == '2') {
            if($(tr).next('tr').data('val') == '1' || $(tr).next('tr').length == 0) {
                alert("더이상 이동할  수 없습니다.");
            }
            else {
                $(tr).next('tr').after(tr);
            }
        }
    });

    // 로우삭제
    $(document).on("click", "[name='removeBtn']", function() {
        var tr = $(this).closest('tr');
        if($(tr).data('val') == '1') {
            if(confirm("메뉴를 삭제하시겟습니까?\n(해당 하위메뉴도 모두 삭제됩니다.)")) {
                while (true) {
                    if($(tr).next('tr').data('val') == '1' || $(tr).next('tr').length == 0) {
                        $(tr).remove();
                        break;
                    }
                    $(tr).next('tr').remove();
                };
            }
        }
        else if($(tr).data('val') == '2') {
            if(confirm("하위메뉴를 삭제하시겟습니까?")) {
                $(tr).remove();
            }
        }
    });
    
    // 하위추가
    $(document).on("click", "[name='downAddBtn']", function() {
        var tr = $(this).closest('tr');
        while (true) {
          if($(tr).next('tr').data('val') == '1' || $(tr).next('tr').length == 0) {
            break;
          }
          tr = $(tr).next('tr');
        };
        menuTreeDownAdd(tr);
    });
});

//메뉴추가
function menuTreeAdd() {
    var html =
        '<tr data-val="1">' +
        '<td class="text-center">Menu</td>' +
        '<td><input type="text" value=""></td>' +
        '<td><input type="text" value=""></td>' +
        '<td class="text-center command-btn">' +
        '<button type="button" name="upMoveBtn">▲</button>\n' +
        '<button type="button" name="downMoveBtn">▼</button>\n' +
        '<button type="button" name="removeBtn">ㅡ</button>' +
        '</td>' +
        '<td class="text-center"><input type="checkbox" checked="checked"></td>' +
        '<td class="text-center add-btn">' +
        '<button type="button" name="downAddBtn">하위추가</button>' +
        '</td>' +
        '</tr>';
    $('#menuTreeTable tbody').append(html);
}

//하위메뉴추가
function menuTreeDownAdd(tr) {
    var html =
        '<tr data-val="2">' +
        '<td class="text-center">┗</td>' +
        '<td><input type="text" value=""></td>' +
        '<td><input type="text" value=""></td>' +
        '<td class="text-center command-btn">' +
        '<button type="button" name="upMoveBtn">▲</button>\n' +
        '<button type="button" name="downMoveBtn">▼</button>\n' +
        '<button type="button" name="removeBtn">ㅡ</button>' +
        '</td>' +
        '<td class="text-center"><input type="checkbox" checked="checked"></td>' +
        '<td class="text-center add-btn"></td>' +
        '</tr>';
    tr.after(html);
}
