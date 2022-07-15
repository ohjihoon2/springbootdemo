$(function(){
    //검색
    $('#searchBtn').click(function() {
        var param = {
            searchType : $('#searchType').val(),
            searchKeyword : $util.transferText($('#searchKeyword').val()),
        }
        $page.getGoPage('/adm/codeGroup', param);
    });


    

    //컨텐츠추가 팝업
    $('#addBtn').click(function(){
        var html = 
            '<h4>코드그룹추가</h4>' +
            '<div class="mb10"></div>' +
            '<form id="codeGroupAddForm">' +
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
            '<td><input id="codeGroupId" type="text" maxlength="15"></td>' +
            '<th>Name</th>' +
            '<td><input id="codeGroupNm" type="text" maxlength="15"></td>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt40"></div>' +
            '<h4 class="update-btn">코드추가<button type="button" id="codeRowAdd" class="color-primary float-right">추가</button></h4>' +
            '<div class="mb10"></div>' +
            '<div class="min-height350">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="42%">' +
            '<col width="42%">' +
            '<col width="16%">' +
            '</colgroup>' +
            '<thead>' +
            '<tr>' +
            '<th>ID</th>' +
            '<th>Name</th>' +
            '<th>Command</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody id="codeRow">' +
            '<tr>' +
            '<td><input type="text" maxlength="15"></td>' +
            '<td><input type="text" maxlength="15"></td>' +
            '<td class="text-center command-btn">' +
            '<button type="button" name="upMoveBtn">▲</button>\n' +
            '<button type="button" name="downMoveBtn">▼</button>\n' +
            '<button type="button" name="removeBtn">ㅡ</button>' +
            '</td>' +
            '</tr>' +
            '</tbody>' +
            '</table>' +
            '</div>' +
            '<div class="mt50"></div>' +
            '<div class="bot-btn-box">' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">추가</button>' +
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);
    });

    // 코드 로우추가
    $(document).on("click", "#codeRowAdd", function(e) {
        var html =
            '<tr>' +
            '<td><input type="text" maxlength="15"></td>' +
            '<td><input type="text" maxlength="15"></td>' +
            '<td class="text-center command-btn">' +
            '<button type="button" name="upMoveBtn">▲</button>\n' +
            '<button type="button" name="downMoveBtn">▼</button>\n' +
            '<button type="button" name="removeBtn">ㅡ</button>' +
            '</td>' +
            '</tr>';
        $('#codeRow').append(html);
    });

    // 코드 로우삭제
    $(document).on("click", "button[name='removeBtn']", function(e) {
        if(confirm("코드를 삭제하시겠습니까?")) {
            var tr = $('#codeRow').find('tr');
            console.log(tr.length);
            if(tr.length <= 1) {
                alert("하나의 코드는 필수입니다.");
            }
            else {
                $(this).closest('tr').remove();
            }
        }
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

    // 코드그룹추가
    $(document).on("submit", "#codeGroupAddForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("codeGroupId")) return;

        if(!$util.isEnNuUnder($('#codeGroupId').val())) {
            alert("코드그룹 ID는 영문, 숫자, _만 입력가능합니다.");
            $('#codeGroupId').focus();
            return;
        }

        var param = {
            codeGroupId : $('#codeGroupId').val()
        }

        var result = $ajax.postAjax('/adm/codeGroupId', param);

        if(result.result == 'success') {
            alert("이미 사용중인 코드 ID입니다.\n코드 ID는 중복 될 수 없습니다.");
            return;
        }

        if($event.validationFocus("codeGroupNm")) return;

        var tr = $('#codeRow').find('tr');
        var codeList = new Array();
        for(i = 0; i < tr.length; i++) {
            var obj = new Object();
            obj.code = tr.eq(i).find('td').eq(0).children('input[type="text"]').val();
            if(obj.code == '') {
                alert("코드ID는 필수항목입니다.");
                tr.eq(i).find('td').eq(0).children('input[type="text"]').focus();
                return;
            }
            if(!$util.isEnNuUnder(obj.code)) {
                alert("코드 ID는 영문, 숫자, _만 입력가능합니다.");
                tr.eq(i).find('td').eq(0).children('input[type="text"]').focus();
                return;
            }
            
            for (var j = 0; j < codeList.length; j++){
                if(codeList[j].code == obj.code) {
                    alert("코드ID는 중복될수 없습니다.");
                    tr.eq(i).find('td').eq(0).children('input[type="text"]').focus();
                    return;
                }
            }

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
            codeGroupId : $('#codeGroupId').val(),
            codeGroupNm : $('#codeGroupNm').val(),
            codeList : codeList,
        };

        var res = $ajax.postAjax('/adm/codeGroup', data);

        if(res == "error") {
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
        }
        else if(res.result == "success") {
            alert("코드그룹을 추가하였습니다.")
            window.location.reload();
        }
        else if(res.result == "fail"){
            alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
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
            '<input id="codeGroupId" type="text" maxlength="15" value="'+ res.codeGroup.codeGroupId +'">' +
            '<input id="codeGroupIdOrigin" type="hidden" value="'+ res.codeGroup.codeGroupId +'">' +
            '</td>' +
            '<th>Name</th>' +
            '<td><input id="codeGroupNm" type="text" maxlength="15" value="'+ res.codeGroup.codeGroupNm +'"></td>' +
            '</tbody>' +
            '</table>' +
            '<div class="mt40"></div>' +
            '<h4 class="update-btn">코드수정<button type="button" id="codeRowAdd" class="color-primary float-right">추가</button></h4>' +
            '<div class="mb10"></div>' +
            '<div class="min-height350">' +
            '<table class="table-top">' +
            '<colgroup>' +
            '<col width="42%">' +
            '<col width="42%">' +
            '<col width="16%">' +
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
                '<td><input type="text" maxlength="15" value="'+ res.codeList[i].code +'"></td>' +
                '<td><input type="text" maxlength="15" value="'+ res.codeList[i].codeNm +'"></td>' +
                '<td class="text-center command-btn">' +
                '<button type="button" name="upMoveBtn">▲</button>\n' +
                '<button type="button" name="downMoveBtn">▼</button>\n' +
                '<button type="button" name="removeBtn">ㅡ</button>' +
                '</td>' +
                '</tr>';
        }
        if(res.codeList.length == 0) {
            html +=
                '<tr data-val="">' +
                '<td><input type="text" maxlength="15"></td>' +
                '<td><input type="text" maxlength="15"></td>' +
                '<td class="text-center command-btn">' +
                '<button type="button" name="upMoveBtn">▲</button>\n' +
                '<button type="button" name="downMoveBtn">▼</button>\n' +
                '<button type="button" name="removeBtn">ㅡ</button>' +
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
            '<button type="button" id="codeGroupDel">삭제</button>' +
            '</div>' +
            '<button type="button" onclick="$popup.popupJsClose()">닫기</button>\n' +
            '<button type="submit">수정</button>' +
            '</div>' +
            '</form>';

        $popup.admPopupJs(html);
    });

    // 코드삭제
    $(document).on("click", "#codeGroupDel", function(e) {
        if(confirm("해당 코드그룹을 삭제하시겠습니까?")) {
            var idx = $('#groupIdx').val();

            var res = $ajax.deleteAjax('/adm/css/'+ idx);
            if(res == "error") {
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
            else if(res.result == "success") {
                alert("해당 CSS를 삭제하였습니다.")
                window.location.reload();
            }
            else if(res.result == "fail"){
                alert('네트워크 통신 실패, 관리자에게 문의해주세요.');
            }
        }
    });

    // 코드그룹 수정
    $(document).on("submit", "#codeGroupUpdateForm", function(e) {
        e.preventDefault();

        if($event.validationFocus("codeGroupId")) return;

        if(!$util.isEnNuUnder($('#codeGroupId').val())) {
            alert("코드그룹 ID는 영문, 숫자, _만 입력가능합니다.");
            $('#codeGroupId').focus();
            return;
        }

        if($('#codeGroupId').val() != $('#codeGroupIdOrigin').val()) {
            var param = {
                codeGroupId : $('#codeGroupId').val()
            }

            var result = $ajax.postAjax('/adm/codeGroupId', param);

            if(result.result == 'success') {
                alert("이미 사용중인 코드 ID입니다.\n코드 ID는 중복 될 수 없습니다.");
                return;
            }
        }

        if($event.validationFocus("codeGroupNm")) return;

        var tr = $('#codeRow').find('tr');
        var codeList = new Array();
        for(i = 0; i < tr.length; i++) {
            var obj = new Object();
            obj.idx = tr.eq(i).data('val') == "" ? null : tr.eq(i).data('val');
            obj.code = tr.eq(i).find('td').eq(0).children('input[type="text"]').val();
            if(obj.code == '') {
                alert("코드ID는 필수항목입니다.");
                tr.eq(i).find('td').eq(0).children('input[type="text"]').focus();
                return;
            }
            if(!$util.isEnNuUnder(obj.code)) {
                alert("코드 ID는 영문, 숫자, _만 입력가능합니다.");
                tr.eq(i).find('td').eq(0).children('input[type="text"]').focus();
                return;
            }

            for (var j = 0; j < codeList.length; j++){
                if(codeList[j].code == obj.code) {
                    alert("코드ID는 중복될수 없습니다.");
                    tr.eq(i).find('td').eq(0).children('input[type="text"]').focus();
                    return;
                }
            }

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
            codeGroupId : $('#codeGroupId').val(),
            codeGroupNm : $('#codeGroupNm').val(),
            codeList : codeList,
        };

        var idx = $('#idx').val();

        var res = $ajax.patchAjax('/adm/codeGroup/'+ idx, data);

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