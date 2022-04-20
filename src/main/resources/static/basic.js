let targetId;

$(document).ready(function () {
    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        // if (e.key == 'Enter') {
        //     execSearch();
        // }
    });
    // $('#write').on('click', function () {
    //     location.href = "/post";
    // })

    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click', function () {
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    showBoardList();
})

function showBoardList() {
    // 1. GET /board 요청
    $.ajax({
        type: 'GET',
        url: '/board',
        success: function (response) {
            console.log(response)
            // 2. for 문마다 게시글 HTML 만들어서 목록에 붙이기
            for (let i = 0; i < response.length; i++) {
                let board = response[i];
                let tempHtml = makePostList(board);
                $('#list-container').append(tempHtml);
            }
        }
    })
}

function makePostList(board) {
    // link, image, title, lprice, myprice 변수 활용하기
    // <img src="${board.image}"
    //return `<!--<div class="post" onclick="location.href='/board/${board.id}'">-->
    // return `<!--<div class="post" onclick="location.href='/viewPost/${board.id}'"> -->
    return `<div class="post" onclick="location.href='/viewPost?id=${board.id}'"> 
                <div class="title">
                    ${board.title}
                </div>
                <div class="nameDate">
                    <span>${board.name}</span> ${board.dateWrite}
                </div>
            </div>`;
}

