 document.addEventListener("DOMContentLoaded", function() {
 document.querySelectorAll('.dropdown-item').forEach(function(item) {
        item.addEventListener('click', function(event) {
            event.preventDefault();

            // 현재 URL과 파라미터 가져오기
            var currentUrl = new URL(window.location.href);
            var params = new URLSearchParams(currentUrl.search);
            // 드롭다운 항목에서 데이터 추출
            var sort = this.getAttribute('data-sort');
            var sortDirection = this.getAttribute('data-sortDirection');
            var deliveryTip = this.getAttribute('data-delivery-tip');
            var rating = this.getAttribute('data-rating');
            var minOrder = this.getAttribute('data-min-order');

            // 쿼리 파라미터 설정
            if (sort) params.set('sort', sort);
            if (sortDirection) params.set('sortDirection',sortDirection);
            if (deliveryTip) params.set('deliveryTip', deliveryTip);
            if (rating) params.set('rating', rating);
            if (minOrder) params.set('minOrder', minOrder);

            params.set('page',0);
            var newUrl = new URL(currentUrl);
            newUrl = currentUrl.pathname+ '?' + params.toString();

            // 새 URL로 리다이렉트
            window.location.href = newUrl;
        });
    });
    document.querySelectorAll('.page-link').forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
                   var pageNumber = this.getAttribute('href').split('=')[1];
                   const isNumber = /^\d+$/.test(pageNumber);
                        if(!isNumber){
                            pageNumber = 1;
                        }
                   const url = new URL(pageNumber, window.location.origin);
                   const currentUrl = new URL(window.location.href);
                   const newUrl = new URL(currentUrl);
                   newUrl.searchParams.set('page', pageNumber);
                   window.location.href = newUrl.href;
        });
     });
  });