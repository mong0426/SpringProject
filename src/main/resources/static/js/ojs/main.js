 $(document).ready(function(){  //브랜드 가로 스크롤 버튼 이벤트
      const cardContainer = $('#cardContainer');
      const scrollStep = 600;
    $('#btnRight').click(function() {
      cardContainer.animate({scrollLeft: '+=' + scrollStep}, 'normal');
    });
    $('#btnLeft').click(function() {
      const scrollLeftPos = cardContainer.scrollLeft(); // 현재 스크롤 위치 가져오기
      cardContainer.animate({scrollLeft: scrollLeftPos - scrollStep}, 'normal');
    });

    const cardContainer2 = document.getElementById("cate-div2");
     $('#ate-div2-rightBtn').click(function() {
       cardContainer2.animate({scrollLeft: '+=' + scrollStep}, 'normal');
     });
     $('#ate-div2-leftBtn').click(function() {
       const scrollLeftPos = cardContainer.scrollLeft(); // 현재 스크롤 위치 가져오기
       cardContainer2.animate({scrollLeft: scrollLeftPos - scrollStep}, 'normal');
     });
 });

