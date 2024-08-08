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

