$(function() {
    // 조회수
    boardHitUp();

});

// 조회수를 올린다.
function boardHitUp() {
    $ajax.postAjax('/content/hit/'+ idx, {}, true);
}