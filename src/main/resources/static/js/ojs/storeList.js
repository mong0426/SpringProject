 document.addEventListener("DOMContentLoaded", function() {
 document.querySelectorAll('.dropdown-item').forEach(function(item) {
        item.addEventListener('click', function(event) {
            event.preventDefault();

            // 현재 URL과 파라미터 가져오기
            var currentUrl = new URL(window.location.href);
            var params = new URLSearchParams(currentUrl.search);
            console.log("currentURL ====================  "+currentUrl);
            console.log("params ====================  "+params);
            // 드롭다운 항목에서 데이터 추출
            var sort = this.getAttribute('data-sort');
            var deliveryTip = this.getAttribute('data-delivery-tip');
            var rating = this.getAttribute('data-rating');
            var minOrder = this.getAttribute('data-min-order');

            // 쿼리 파라미터 설정
            if (sort) params.set('sort', sort);
            if (deliveryTip) params.set('deliveryTip', deliveryTip);
            if (rating) params.set('rating', rating);
            if (minOrder) params.set('minOrder', minOrder);

            // 새 URL로 리다이렉트
            window.location.href = currentUrl.pathname + '?' + params.toString();
        });
    });
  });