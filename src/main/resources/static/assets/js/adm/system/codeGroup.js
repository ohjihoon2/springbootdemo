$(function(){
    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/adm/codeGroup', param);
    });

    // 코드 로우 위로올리기
    $(document).on("click", "button[name='upMoveBtn']", function(e) {
        var tr = $(this).closest('tr');
        if($(tr).prev('tr').length == 0) {
            return alert("더 이상 이동할 수 없습니다.");
        }
        else {
            var standardTr = $(tr).prev('tr');
            $(standardTr).before(tr);
        }
    });

    // 코드 로우 아래로 내리기
    $(document).on("click", "button[name='downMoveBtn']", function(e) {
        var tr = $(this).closest('tr');
        if($(tr).next('tr').length == 0) {
            return alert("더 이상 이동할 수 없습니다.");
        }
        else {
            var standardTr = $(tr).next('tr');
            $(standardTr).after(tr);
        }
    });

    // 컨텐츠수정 팝업
    $('[name="updateBtn"]').click(function(){
        var idx = $(this).data('val');

        var res = $ajax.postAjax('/adm/codeGroup/' + idx);
        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            return;
        }

        res.codeGroup = $util.nullChkObj(res.codeGroup);
        res.codeList = $util.nullChkObj(res.codeList);


        var html =
            '<h4>코드그룹수정</h4>' +
            '<div class="mb10"></div>' +
            '<form id="codeGroupUpdateForm">' +
            '<input id="idx" type="hidden" value="'+ res.codeGroup.idx +'">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="15%">' +
            '<col width="35%">' +
            '<col width="15%">' +
            '<col width="35%">' +
            '</colgroup>' +
            '<tbody>' +
            '<tr>' +
            '<th>ID</th>' +
            '<td>' +
            res.codeGroup.codeGroupId +
            '</td>' +
            '<th>Name</th>' +
            '<td>' +
            res.codeGroup.codeGroupNm +
            '</tbody>' +
            '</table>' +
            '<div class="mt40"></div>' +
            '<h4 class="update-btn">코드수정</h4>' +
            '<div class="mb10"></div>' +
            '<div class="min-height350">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="43%">' +
            '<col width="43%">' +
            '<col width="14%">' +
            '</colgroup>' +
            '<thead>' +
            '<tr>' +
            '<th>ID</th>' +
            '<th>Name</th>' +
            '<th>Command</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody id="codeRow">';
        for (var i =0; i < res.codeList.length; i++) {
            html +=
                '<tr data-val="'+ res.codeList[i].idx +'">' +
                '<td>'+ res.codeList[i].code +'</td>' +
                '<td><input type="text" maxlength="15" value="'+ res.codeList[i].codeNm +'"></td>' +
                '<td class="text-center command-btn">' +
                '<button type="button" name="upMoveBtn">▲</button>\n' +
                '<button type="button" name="downMoveBtn">▼</button>\n' +
                '</td>' +
                '</tr>';
        }
        html +=
            '</tbody>' +
            '</table>' +
            '</div>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<div class="left">' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);
    });

    // 코드그룹 수정
    $(document).on("submit", "#codeGroupUpdateForm", function(e) {
        e.preventDefault();

        var tr = $('#codeRow').find('tr');
        var codeList = new Array();
        for(i = 0; i < tr.length; i++) {
            var obj = new Object();
            obj.idx = tr.eq(i).data('val') == "" ? null : tr.eq(i).data('val');
            obj.codeNm = tr.eq(i).find('td').eq(1).children('input[type="text"]').val();
            if(obj.codeNm == '') {
                alert("코드명은 필수항목입니다.");
                tr.eq(i).find('td').eq(1).children('input[type="text"]').focus();
                return;
            }

            obj.sortOrdr = (i + 1);
            codeList.push(obj);
        }

        var data = {
            codeList : codeList,
        };

        var res = $ajax.patchAjax('/adm/code/' , data);

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("코드그룹을 수정하였습니다.");
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
    });
});