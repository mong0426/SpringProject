 $(document).ready(function(){  // 지금 할인 브랜드 가로 스크롤 버튼 이벤트
      const cardContainer = $('#cardContainer');
      const scrollStep = 600;
    $('#btnRight').click(function() {
      cardContainer.animate({scrollLeft: '+=' + scrollStep}, 'normal');
    });
    $('#btnLeft').click(function() {
      const scrollLeftPos = cardContainer.scrollLeft(); // 현재 스크롤 위치 가져오기
      cardContainer.animate({scrollLeft: scrollLeftPos - scrollStep}, 'normal');
    });
 });

  $(document).ready(function(){
      const searchForm = document.getElementById("searchForm");
      const searchBtn = document.getElementById("searchBtn");

      searchBtn.addEventListener("click", function(event){
      const searchText = document.getElementById("searchText").value.trim(); // .trim()을 사용하여 공백 제거
      if (searchText !== "") {
        searchForm.action = "/search?query=" + searchText;
        searchForm.method = "get";
        searchForm.submit();
      }else {
        event.preventDefault(); // 값이 비어 있을 경우 폼 제출 방지
      }
   });
  });